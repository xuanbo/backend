package tk.fishfish.simple.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import tk.fishfish.simple.entity.City;

/**
 * 城市数据库操作
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@Mapper
public interface CityMapper {

    /**
     * 数据库插入
     *
     * @param city 城市
     */
    @Insert("INSERT INTO city (name, state, country) VALUES(#{name}, #{state}, #{country})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(City city);

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 城市
     */
    @Select("SELECT id, name, state, country FROM city WHERE id = #{id}")
    City findById(long id);

}
