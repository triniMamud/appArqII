#include <ESP8266WiFi.h>  // Importa a Biblioteca ESP8266WiFi

/* 

Autores: Martin Vega, Trinidad Mamud, Randy Mendoza, Gaston Marino
Fecha: 15/07/2021
Objetivos: 
Mostrar el estado del boton y led por el monitor serie y por la terminal MQTT.


Hardware: Placa NODEMCU

*/

/*Esta biblioteca proporciona un cliente para realizar mensajes simples de publicación / suscripción con un servidor que admita MQTT.*/
#include <PubSubClient.h> // Importa la libreria PbSubClient

//Definicion de topicos : suscriptores y publicadores
#define TOPICO_SUBSCRIBE "RandyTinchoTriniSuscriptor"     // recibimos mensaje de la interfaz
#define TOPICO_PUBLISH   "RandyTinchoTriniPublicador"    // publicamos el mensaje que se vera en la interfaz
                                                                                                      
// Indicamos en que pin se ubicara el led
const int Led = 2; 

// indicamos en que pin se conectara el potenciometro
const int potenciometro = 0;

// variable para la intensidad del brillo del led
int intensidad;


//CONFIGURACION WIFI NOMBRE DE RED Y CONTRASEÑA
const char* nameRedWifi = "";         
const char* password = "";        
  
//CONFIGURACION MQTT

const char* BROKER_MQTT = "tcp://test.mosquitto.org:1883"; //URL de broker MQTT mosquitto
int BROKER_PORT = 1883; // Puerto del Broker MQTT

WiFiClient Cliente; //objeto espClient
PubSubClient MQTT(Cliente); // Instancia Cliente MQTT 
int estadoLed = LOW;  //variable del estado del boton

//Declaracion de funciones a utilizar
void conectarWIFI();
void conectarMQTT();
void mqtt_callback(char* topic, byte* payload, unsigned int length);

void setup() {
    //Configuramos entradas y salida al led y boton
    pinMode(Led, OUTPUT); // El LED integrado está conectado al pin 2.
    

    //serial
    Serial.begin(115200);

    //wifi
    delay(10);
    Serial.println("Conectando a la red: "); 
    Serial.print(nameRedWifi); 
    reconectWiFi();

    //MQTT
    MQTT.setServer(BROKER_MQTT, BROKER_PORT);   //Se configura el servidor con broker + puerto del servidor
    MQTT.setCallback(mqtt_callback);            //se asigna la funcion de callback
}

void conectarWIFI() {
  if (WiFi.status() == WL_CONNECTED)
    return;
  
  WiFi.begin(nameRedWifi, password);    //Inicializamos  la conexión del chip ESP8266 con la red WiFi 
    
  while (WiFi.status() != WL_CONNECTED) {  // Verifica el estado de la conexión del NodeMCU cada 0,5s hasta que conecta 
    delay(500);
    Serial.print(".");                    
  }
  
  Serial.println("");
  Serial.println("Nombre de la red a la que nos conectamos con la NodeMCU");        // Indica que el NodeMCU conectado con la red WiFi 
  Serial.println("Ip obtenida: "); 
  Serial.print(WiFi.localIP());   
}
  
void mqtt_callback(char* topic, byte* payload, unsigned int length) {
    String msg;
 
    //Obtencion de mensaje payload
    for(int i = 0; i < length; i++) {
       char c = (char)payload[i];
       msg += c;
    }
    
    // tenemos el mensaje en msg, lo pasamos a int para modificar la intensidad led 
     analogWrite(Led,msg.toInt());
     
}
  
void conectarMQTT() {
    while (!MQTT.connected()) {
      Serial.print("Intentando conectar al Broker: ");
      Serial.println(BROKER_MQTT);
      if (MQTT.connected()){
        Serial.println("Se conecto exitosamente al Broker MQTT!");
        MQTT.subscribe(TOPICO_SUBSCRIBE); 
      }
      else{
        Serial.println("Falla en la coneccion....Reintentando en 2s"); //sino se conecta reintenta en 2 seg.
        delay(2000);
      }
    }
}


void loop() {   
    //Conexion a WIFI y a MQTT:
    if (!MQTT.connected()){ //Si no hay conexion, se reconecta
      conectarMQTT(); 
    }
    conectarWIFI(); // Conectar WIFI

    // Envio de estado del OUTPUT al brooker
    // esta bien usar un potenciometro?
    int estadoSensorAnalogico = analogRead(potenciometro); //leo estado del potenciometro
    String estado = String(estadoSensorAnalogico)
    MQTT.publish(TOPICO_PUBLISH, estado);  
    Serial.println("- Datos publicados en el topico:" + estado);
    delay(1000);
    
    MQTT.loop();
}
