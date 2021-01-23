package tk.fishfish.simple.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.test.context.junit4.SpringRunner;
import tk.fishfish.simple.Application;

import java.util.Collections;

/**
 * lua脚本测试
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class LuaScriptTest {

    private final Logger logger = LoggerFactory.getLogger(LuaScriptTest.class);

    private static final String LUA = "local key = KEYS[1] " +
            "local current = tonumber(redis.call('get', key)) " +
            "local res = 0 " +
            "if current > 0 then " +
            "   redis.call('decr', key) " +
            "   res = 1 " +
            "end " +
            "return res ";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void execLua() {
        Long result = redisTemplate.execute(new DefaultRedisScript<>(LUA, Long.class), Collections.singletonList("simple:lua"), Collections.emptyList());
        logger.info("result: {}", result);
    }

}
