package tk.fishfish.simple.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import tk.fishfish.simple.Application;

import java.util.List;

/**
 * pipeline测试
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class PipelineTest {

    private final Logger logger = LoggerFactory.getLogger(LuaScriptTest.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void pipeline() {
        List<Object> values = redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                StringRedisConnection sc = (StringRedisConnection) connection;
                sc.get("simple:lua");
                sc.get("simple:lua");
                sc.get("simple:lua");
                sc.get("simple:lua");
                sc.get("simple:lua");
                return null;
            }
        });
        logger.info("values: {}", values);
    }

}
