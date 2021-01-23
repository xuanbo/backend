package tk.fishfish.simple.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * redis sub
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public class PrintingMessageListener implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(PrintingMessageListener.class);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] channel = message.getChannel();
        byte[] body = message.getBody();
        logger.info("pattern: {}, channel: {}, body:{}", new String(pattern), new String(channel), new String(body));
    }

}
