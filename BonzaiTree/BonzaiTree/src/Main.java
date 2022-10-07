import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TreeFrame();
        });
    }
}

class TreeFrame extends JFrame {
    public TreeFrame() {
        setSize(new Dimension(1024, 768));
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void paint(Graphics g) {
        super.paint(g);
        // Punkt startowy
        int xFrom = 512;
        int yFrom = 768;
        // Długość gałęzi
        int radius = 100;
        // Początkowy kąt
        double angle = Math.PI;

        // nieskończona ilość drzewek
        while (true) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File("background.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Background
            g.drawImage(image,0,0, 1024, 768, null);
            // Rysowanie
            draw(g, xFrom, yFrom, angle-90, 10);

            //podkład pod nowe drzewko gdy pętla nieskończoność
            g.setColor(Color.WHITE);
            g.fillRect(0,0,1920,1080);
        }
    }
    private void draw(Graphics g, int xFrom, int yFrom, double angle, int poziom) {

        //Koniec rysowania gałezi gdy zespawnuje daną ilośc poziomów
        if(poziom == 0) {
            return;
        }
        Random random = new Random();
                                                                    //  długość branch + skala            randomowy kąt (od -15 do 15)
        int leftEndX = (int) (xFrom + (Math.cos(Math.toRadians(angle)) * poziom * 10) + (random.nextInt(30)-15));
        int leftEndY = (int) (yFrom + (Math.sin(Math.toRadians(angle)) * poziom * 10) + (random.nextInt(30)-15));

        // Randomowy kolor linii
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        Color color = new Color(red,green,blue);
        g.setColor(color);

        // Grubość linii
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(random.nextInt(poziom + poziom)));

        // No paint first branch
        if(poziom != 10) {
            g2d.drawLine(xFrom, yFrom, leftEndX, leftEndY);
        }

        //Animacja
        try {
            Thread.sleep(10);
            draw(g, leftEndX, leftEndY, angle - 20 + random.nextInt(5), poziom - 1);
            Thread.sleep(10);
            draw(g, leftEndX, leftEndY, angle + 20 + random.nextInt(5), poziom - 1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
