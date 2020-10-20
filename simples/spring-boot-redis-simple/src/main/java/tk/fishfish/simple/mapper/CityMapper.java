package tk.fishfish.simple.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
    @Insert("INSERT INTO city (id, name, state, country) VALUES(#{id}, #{name}, #{state}, #{country})")
    void insert(City city);

    /**
     * 数据库更新
     *
     * @param city 城市
     */
    @Update("UPDATE city SET name = #{name}, state = #{state}, country = #{country} WHERE id = #{id}")
    void update(City city);

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 城市
     */
    @Select("SELECT id, name, state, country FROM city WHERE id = #{id}")
    City findById(String id);

    /**
     * 根据主键删除
     *
     * @param id 主键
     */
    @Delete("DELETE FROM city WHERE id = #{id}")
    void deleteById(String id);

}
