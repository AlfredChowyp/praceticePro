package activemqdemo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Sender {

    public static void main(String[] args) throws Exception {
        // 1.建立工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new
                ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://localhost:61616"
        );
        // 2.从工厂里拿一个链接
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 3.从连接中获取Session（会话）
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 从会话中获取目的地（Destination）消费者会从这个目的地获取消息
        Queue queue = session.createQueue("demo");

        // 从会话中创建消息提供者

        MessageProducer producer = session.createProducer(queue);
        // 从会话中创建文本消息(也可以创建其它类型的消息体)

        for (int i = 0; i < 100; i++) {
            TextMessage message = session.createTextMessage("msg:" + i);
            // 通过消息提供者发送消息到ActiveMq
            Thread.sleep(1000);
            producer.send(message);
        }

        // 关闭连接
        connection.close();
        System.out.println("exit");
    }
}
