import lombok.*;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

@Data
class Botones extends JPanel {

    private JTextField escrituraTopico;
    private JTextField escrituraMensaje;
    private JLabel lecturaMensaje;
    MqttSuscriptor mqttSuscriptor = new MqttSuscriptor();

    public void initializeButtons() {

        Timer timerLectura = new Timer(5000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // debemos recibir la informacion del topico suscripto y
                // setear el valor en interfaz
                lecturaMensaje.setText(mqttSuscriptor.getMensaje());
            }
        });

        timerLectura.start();
        timerLectura.setRepeats(true);

        JLabel texto1 = new JLabel("Topico ");
        texto1.setFont(new Font("Arial", Font.BOLD, 20));
        escrituraTopico = new JTextField(20);

        JLabel texto2 = new JLabel("Topico ");
        texto2.setFont(new Font("Arial", Font.BOLD, 20));
        escrituraTopico = new JTextField(20);

        JLabel mensajeRecibido = new JLabel("Mensaje recibido: ");
        lecturaMensaje = new JLabel("");
        mensajeRecibido.setFont(new Font("Arial", Font.BOLD, 20));
        lecturaMensaje.setFont(new Font("Italic", Font.BOLD, 13));
        escrituraMensaje = new JTextField(20);

        JLabel mensajePublicar = new JLabel("Mensaje a Publicar: ");
        lecturaMensaje = new JLabel("");
        mensajePublicar.setFont(new Font("Arial", Font.BOLD, 20));
        lecturaMensaje.setFont(new Font("Italic", Font.BOLD, 13));

        //seteamos el campo de escritura mensaje para que solo reciba numeros del 0 al 255 (valores de señal PWM)
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0); //valor mínimo
        formatter.setMaximum(255); //valor máximo
        formatter.setAllowsInvalid(false);
        escrituraMensaje = new JFormattedTextField(formatter);

        EventoPublicar eventoPublicar = new EventoPublicar();
        JButton publicar = new JButton("Publicar");
        publicar.addActionListener(eventoPublicar);

        setLayout(new GridLayout(3,6));

        add(texto2);
        add(new JLabel("/IoT/Wemos/señalPWM"));
        add(new JLabel());
        add(texto1);
        add(new JLabel(mqttSuscriptor.getTopico()));

        add(mensajePublicar);
        add(escrituraMensaje);
        add(new JLabel());
        add(mensajeRecibido);
        add(lecturaMensaje);

        add(publicar);
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());

    }

    private class EventoPublicar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // agregamos la comunicacion con la clase que publica en el MQTT
            mqttSuscriptor.suscribir();
            MqttPublicador mqttPublicador = new MqttPublicador();
            mqttPublicador.publicar(escrituraMensaje.getText());
        }
    }

}
