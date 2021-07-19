import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class App {

    public static void main(String[] args) {

        MqttSuscriber mqttSuscriber = new MqttSuscriber();
        mqttSuscriber.subscribe();

        MqttPublisher mqttPublisher = new MqttPublisher();
        mqttPublisher.mqttPublish();
    }
}

