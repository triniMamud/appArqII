import lombok.Data;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Data
public class MqttSuscriptor implements MqttCallback {
    /** The broker url. */
    private static final String brokerUrl ="tcp://test.mosquitto.org:1883";
    /** The client id. */
    private static final String idCliente = "suscriberClient";
    /** The topic. */
    private String topico = "/IoT/Wemos/sensorAnalogico";
    private String mensaje;

    public void suscribir() {
        MemoryPersistence persistencia = new MemoryPersistence();
        try
        {
            MqttClient cliente = new MqttClient(brokerUrl, idCliente, persistencia);
            MqttConnectOptions opcionesConexion = new MqttConnectOptions();
            opcionesConexion.setCleanSession(true);
            System.out.println("Verificando ...");
            System.out.println("Mqtt Conectandose al broker: " + brokerUrl + " ...");
            cliente.connect(opcionesConexion);
            System.out.println("Mqtt Conectado");
            cliente.setCallback(this);
            cliente.subscribe(topico);
            System.out.println("Suscripto al topico: " + topico);
            System.out.println("Escuchando ...");

        } catch (MqttException me) {
            System.out.println(me);
        }
    }

    //Se llama cuando el cliente pierde conexion con el broker
    public void connectionLost(Throwable arg0) {
    }

    //Se llama cuando la publicacion saliente es completada
    public void deliveryComplete(IMqttDeliveryToken arg0) {
    }

    //Funcion callback que se llama siempre que se escuche un mensaje nuevo en el topico suscripto
    public void messageArrived(String topic, MqttMessage message) {
        System.out.println("Topic:" + topic);
        System.out.println("Message: " + message);
        this.mensaje = message.toString();
    }

    public String getMensaje() {
        return this.mensaje;
    }
}
