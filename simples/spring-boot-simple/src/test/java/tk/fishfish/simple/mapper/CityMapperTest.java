package tk.fishfish.simple.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.fishfish.simple.Application;
import tk.fishfish.simple.entity.City;

/**
 * 测试 {@link CityMapper }
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CityMapperTest {

    private final Logger logger = LoggerFactory.getLogger(CityMapperTest.class);

    @Autowired
    private CityMapper cityMapper;

    @Test
    public void insert() {
        City city = new City();
        city.setName("San Francisco");
        city.setState("CA");
        city.setCountry("US");
        cityMapper.insert(city);
        logger.info("city: {}", city);
    }

    @Test
    public void findById() {
        City city = cityMapper.findById(1L);
        logger.info("city: {}", city);
    }

}

