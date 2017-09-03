import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * @Author : Pushkarraj Pujari
 * @Since  : 03/09/2017
 */
public class Publisher {
    /**
     * Stage 1 - Publisher program which illustrates the concept of Exchanges using Fanout As Exchange Type
     * */
    public final static String EXCHANGE_TYPE = "fanout";
    public final static String EXCHANGE_NAME = "EX1";
    public static String message ;
    public static void main(String[] args) {
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            Connection connection = connectionFactory.newConnection("Publisher");
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            for(int i = 0; i <100 ;i++){
                message = String.valueOf(i);
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
                Thread.currentThread().sleep(100);
            }
            channel.close();
            connection.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
