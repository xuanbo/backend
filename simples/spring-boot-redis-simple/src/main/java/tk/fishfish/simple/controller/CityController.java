package tk.fishfish.simple.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.fishfish.simple.domain.ApiResult;
import tk.fishfish.simple.entity.City;
import tk.fishfish.simple.service.CityService;

/**
 * 城市接口
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@RestController
@RequestMapping("/v1/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * 保存
     *
     * @param city 城市
     */
    @PostMapping
    public ApiResult<City> insert(@RequestBody City city) {
        return ApiResult.ok(cityService.insert(city));
    }

    /**
     * 更新
     *
     * @param city 城市
     */
    @PutMapping
    public ApiResult<City> update(@RequestBody City city) {
        return ApiResult.ok(cityService.update(city));
    }

    /**
     * 主键查询
     *
     * @param id 主键
     * @return 城市
     */
    @GetMapping("/{id}")
    public ApiResult<City> findById(@PathVariable String id) {
        City city = cityService.findById(id);
        return ApiResult.ok(city);
    }

    /**
     * 主键查询
     *
     * @param id 主键
     * @return 城市
     */
    @DeleteMapping("/{id}")
    public ApiResult<Void> insert(@PathVariable String id) {
        cityService.deleteById(id);
        return ApiResult.ok();
    }

}
