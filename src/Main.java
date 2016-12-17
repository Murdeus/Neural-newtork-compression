import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by smile on 14-Dec-15.
 */
public class Main extends JFrame{
    static BufferedImage DEFAULT_IMAGE;
    private BufferedImage NEW_IMAGE;
    private Settings sett = new Settings();
    private ScrollPane scrol;

    public static void main(String[] args) {
        Main m = new Main();
    }

    public Main() {
        try {
            DEFAULT_IMAGE = ImageIO.read(new File(
                    "way.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setSize(800, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(200, 100);
        JMenuBar menuBar = new JMenuBar();
        JMenu start = new JMenu("Начать");
        JMenuItem startItem = new JMenuItem("Начать");
        start.add(startItem);
        JMenu remove = new JMenu("Стереть");
        JMenuItem removeItem = new JMenuItem("Стереть");
        remove.add(removeItem);
        JMenu settings = new JMenu("Настроить");
        JMenuItem settItem = new JMenuItem("Настроить");
        settings.add(settItem);
        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    remove(scrol);
                    setVisible(false);
                    setVisible(true);
                } catch (Exception ex) {

                }

            }
        });
        settItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                sett.setVisible(true);
            }
        });
        startItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    Task task = sett.getTask();
                    if (task != null) {
                        System.out.println("Таска создана");
                    }
                    task.doAction();
                    NEW_IMAGE = task.createOutputImage();
                    setNewView();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Ошибка! Не установлены параметры");
                }
            }
        });
        menuBar.add(start);
        menuBar.add(settings);
        menuBar.add(remove);
        this.setJMenuBar(menuBar);
        this.setLayout(null);
        setImageView();
        this.setVisible(true);
    }

    public void setImageView() {
        ScrollPane scroll = new ScrollPane();
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(DEFAULT_IMAGE));
        scroll.setBounds(30, 20, 310, 400);
        scroll.add(label);
        this.add(scroll);
    }

    public void setNewView() {
        scrol = new ScrollPane();
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(NEW_IMAGE));
        scrol.setBounds(370, 20, 310, 400);
        scrol.add(label);
        this.add(scrol);
        this.setVisible(false);
        this.setVisible(true);
    }
}
