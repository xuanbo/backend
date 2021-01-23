package tk.fishfish.simple.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import tk.fishfish.simple.listener.PrintingMessageListener;

/**
 * Redis消息配置
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
@Configuration
public class RedisMessageConfig {

    @Bean
    public RedisMessageListenerContainer redisMsgListenContainer(@Autowired RedisConnectionFactory connectFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        Topic topic = new PatternTopic("simple:topic:*");
        container.addMessageListener(new PrintingMessageListener(), topic);
        container.setConnectionFactory(connectFactory);
        return container;
    }

}
