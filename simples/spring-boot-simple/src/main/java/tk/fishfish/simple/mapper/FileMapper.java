package tk.fishfish.simple.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tk.fishfish.simple.entity.File;

/**
 * 文件数据库操作
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@Mapper
public interface FileMapper {

    /**
     * 数据库插入
     *
     * @param file 文件
     */
    @Insert("INSERT INTO file (id, name, size, createTime) VALUES(#{id}, #{name}, #{size}, #{createTime})")
    void insert(File file);

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 文件
     */
    @Select("SELECT id, name, size, createTime FROM file WHERE id = #{id}")
    File findById(String id);

}
