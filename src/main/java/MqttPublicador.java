import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Data
public class MqttPublicador {

    public void publicar(String content) {
        int qos = 2;
        String broker = "tcp://test.mosquitto.org:1883";
        String idCliente = "publisherClient";
        String topico = "/IoT/Wemos/sensorAnalogico";
        MemoryPersistence persistencia = new MemoryPersistence();
        try {
            MqttClient cliente = new MqttClient(broker, idCliente, persistencia);
            MqttConnectOptions opcionesConexion = new MqttConnectOptions();
            opcionesConexion.setCleanSession(true);
            System.out.println("Conectando al broker: " + broker + " ...");
            cliente.connect(opcionesConexion);
            System.out.println("Conectado");
            System.out.println("Publicando mensaje ...");
            MqttMessage mensaje = new MqttMessage(content.getBytes());
            mensaje.setQos(qos);
            cliente.publish(topico, mensaje);
            System.out.println("Mensaje publicado con exito");
            cliente.disconnect();
            System.out.println("Publisher desconectado");
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("mensaje "+me.getMessage());
            System.out.println("mensaje localizado "+me.getLocalizedMessage());
            System.out.println("causa "+me.getCause());
            System.out.println("excepcion "+me);
            me.printStackTrace();
        }
    }
}
