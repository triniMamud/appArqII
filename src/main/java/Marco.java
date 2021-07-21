import javax.swing.*;
import java.awt.*;

class Marco extends JFrame {

    public Marco() {

        // obtenemos informacion de la pantalla para crear una ventana centrada
        int anchoPantalla = Toolkit.getDefaultToolkit().getScreenSize().width;
        int altoPantalla = Toolkit.getDefaultToolkit().getScreenSize().height;

        // establecemos el tamaño de la ventana y la ubicacion
        setBounds(anchoPantalla / 4, altoPantalla / 4, anchoPantalla/2 + 300, altoPantalla / 2 + 30);
        // hacemos visible el marco
        setVisible(true);
        setTitle("Cliente MQTT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // bloqueamos que el frame se pueda redimensionar
        // setResizable(false);

        // agregamos la lamina al frame
        Botones laminaBotones = new Botones();
        laminaBotones.initializeButtons();
        Imagen imagen = new Imagen();
        laminaBotones.setBackground(Color.white); // pintamos la lamina
        imagen.setBackground(Color.white);
        add(laminaBotones, BorderLayout.SOUTH);
        add(imagen);
    }
}
