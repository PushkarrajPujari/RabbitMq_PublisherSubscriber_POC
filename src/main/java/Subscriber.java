import com.rabbitmq.client.*;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * @Author : Pushkarraj Pujari
 * @Since : 04/09/2017
 */
public class Subscriber {
    /**
     *
     * Stage 2 - This is the Subscriber where we will see how to consume messages through a fanout Exchange
     *
     * */
    public final static String EXCHANGE_TYPE = "fanout";
    public final static String EXCHANGE_NAME = "EX1";
    public static String Queue_Name;

    public static void main(String[] args) {
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            Connection connection = connectionFactory.newConnection(getInput("Enter Connection Name"));
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            Queue_Name = channel.queueDeclare().getQueue();
            channel.queueBind(Queue_Name, EXCHANGE_NAME, "");
            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                }
            };
            channel.basicConsume(Queue_Name,true,consumer);
            getInput("Press Enter to Exit");
            channel.close();
            connection.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public static String  getInput(String string){
        System.out.println(string);
        return new Scanner(System.in).nextLine();
    }
}
