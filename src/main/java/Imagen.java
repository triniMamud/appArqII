import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

class Imagen extends JPanel {
    private java.awt.Image imagen;
    private int anchoPantalla;

    // permite hacer dibujos sobre una lamina y colocar imagen
    public void paintComponent(Graphics graficos) {
        super.paintComponent(graficos);
        anchoPantalla = Toolkit.getDefaultToolkit().getScreenSize().width;

        // ruta de la imagen
        File rutaImagen = new File("src/logo.jpeg");
        try {
            imagen = ImageIO.read(rutaImagen);
        } catch (IOException e) {
            System.out.println("No se encontro imagen");
        }

        // dibujamos la imagen
        graficos.drawImage(imagen, anchoPantalla / 10, 0, null);
    }
}
