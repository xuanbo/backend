package tk.fishfish.simple.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import tk.fishfish.simple.entity.City;

/**
 * 城市服务
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@CacheConfig(cacheNames = "simple:city")
public interface CityService {

    /**
     * 插入
     *
     * @param city 城市
     * @return 城市
     */
    @CachePut(key = "'id:' + #p0.id")
    City insert(City city);

    /**
     * 更新
     *
     * @param city 城市
     * @return 城市
     */
    @CachePut(key = "'id:' + #p0.id")
    City update(City city);

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 城市
     */
    @Cacheable(key = "'id:' + #p0")
    City findById(String id);

    /**
     * 根据主键删除
     *
     * @param id 主键
     */
    @CacheEvict(key = "'id:' + #p0")
    void deleteById(String id);

}
