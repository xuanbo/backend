package tk.fishfish.simple.service.impl;

import org.springframework.stereotype.Service;
import tk.fishfish.simple.entity.City;
import tk.fishfish.simple.mapper.CityMapper;
import tk.fishfish.simple.service.CityService;

import java.util.UUID;

/**
 * 城市服务
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@Service
public class CityServiceImpl implements CityService {

    private final CityMapper cityMapper;

    public CityServiceImpl(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Override
    public City insert(City city) {
        // 指定一个ID
        city.setId(UUID.randomUUID().toString());
        cityMapper.insert(city);
        return city;
    }

    @Override
    public City update(City city) {
        cityMapper.update(city);
        return city;
    }

    @Override
    public City findById(String id) {
        return cityMapper.findById(id);
    }

    @Override
    public void deleteById(String id) {
        cityMapper.deleteById(id);
    }

}
