package tk.fishfish.simple.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tk.fishfish.simple.domain.ApiResult;
import tk.fishfish.simple.entity.File;
import tk.fishfish.simple.mapper.FileMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 文件接口
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@RestController
@RequestMapping("/v1/file")
public class FileController {

    private static final String USER_AGENT = "User-Agent";
    private static final String USER_AGENT_IE = "MSIE";

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileMapper fileMapper;

    @Value("${simple.uploadDir:./upload}")
    private String uploadDir;

    public FileController(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    /**
     * 文件上传
     *
     * @param multipartFile MultipartFile
     * @return 文件元数据
     */
    @PostMapping
    public ApiResult<File> upload(@RequestPart("file") MultipartFile multipartFile) {
        // 生成ID
        String id = UUID.randomUUID().toString();
        try {
            FileCopyUtils.copy(multipartFile.getInputStream(), Files.newOutputStream(Paths.get(uploadDir, id)));
        } catch (IOException e) {
            logger.warn("文件上传失败", e);
            return ApiResult.fail("文件上传失败");
        }
        File file = insert(id, multipartFile);
        return ApiResult.ok(file);
    }

    private File insert(String id, MultipartFile multipartFile) {
        File file = new File();
        file.setId(id);
        file.setName(multipartFile.getOriginalFilename());
        file.setSize(multipartFile.getSize());
        file.setCreateTime(LocalDateTime.now());
        fileMapper.insert(file);
        return file;
    }

    /**
     * 文件下载
     *
     * @param id   文件ID
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     */
    @GetMapping("/{id}/download")
    public void download(@PathVariable String id, HttpServletRequest req, HttpServletResponse resp) {
        File file = fileMapper.findById(id);
        if (file == null) {
            // 找不到资源
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 本地资源是否存在
        Path path = Paths.get(uploadDir, id);
        boolean exists = Files.exists(path);
        if (!exists) {
            // 找不到资源
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 多浏览器下载文件时不乱码
        setAttachment(file.getName(), req, resp);
        try {
            FileCopyUtils.copy(Files.newInputStream(path), resp.getOutputStream());
        } catch (IOException e) {
            logger.warn("文件下载错误", e);
            // 服务器错误
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void setAttachment(String name, HttpServletRequest req, HttpServletResponse resp) {
        String userAgent = req.getHeader(USER_AGENT);
        if (StringUtils.isEmpty(userAgent) || userAgent.contains(USER_AGENT_IE)) {
            // IE浏览器
            try {
                name = URLEncoder.encode(name, StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                // ignore
            }
        } else {
            // 谷歌、火狐等现代浏览器
            name = new String(name.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"");
    }

}
