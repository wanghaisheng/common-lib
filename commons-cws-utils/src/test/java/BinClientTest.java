import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.Event;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by chengweisen on 2015/3/27.
 */
public class BinClientTest {

    @Test
    public void clientTest() {
        BinaryLogClient client = new BinaryLogClient("192.168.158.94", 3358, "aos2", "aos2");
        client.registerEventListener(new BinlogEventTest());
        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class BinlogLifecyEventTest implements BinaryLogClient.LifecycleListener {

        @Override
        public void onConnect(BinaryLogClient client) {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAa");
        }

        @Override
        public void onCommunicationFailure(BinaryLogClient client, Exception ex) {
            System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        }

        @Override
        public void onEventDeserializationFailure(BinaryLogClient client, Exception ex) {
            System.out.println("ccccccccccccccccccccc");
        }

        @Override
        public void onDisconnect(BinaryLogClient client) {
            System.out.println("ddddddddddddddddddddddddddddddd");
        }
    }

    class BinlogEventTest implements BinaryLogClient.EventListener {

        @Override
        public void onEvent(Event event) {
            System.out.println(event);
            System.out.println("===============================================");
        }
    }

}
