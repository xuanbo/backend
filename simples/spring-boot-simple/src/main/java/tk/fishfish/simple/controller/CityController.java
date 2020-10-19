package tk.fishfish.simple.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.fishfish.simple.domain.ApiResult;
import tk.fishfish.simple.entity.City;
import tk.fishfish.simple.mapper.CityMapper;

/**
 * 城市接口
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@RestController
@RequestMapping("/v1/city")
public class CityController {

    private final CityMapper cityMapper;

    public CityController(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    /**
     * 保存
     *
     * @param city 城市
     */
    @PostMapping
    public ApiResult<City> insert(@RequestBody City city) {
        cityMapper.insert(city);
        return ApiResult.ok(city);
    }

    /**
     * 主键查询
     *
     * @param id 主键
     * @return 城市
     */
    @GetMapping("/{id}")
    public ApiResult<City> insert(@PathVariable Long id) {
        City city = cityMapper.findById(id);
        return ApiResult.ok(city);
    }

}
