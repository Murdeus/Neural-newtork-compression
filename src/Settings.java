import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Jama.Matrix;

/**
 * Created by smile on 14-Dec-15.
 */
public class Settings extends JFrame {
    private int w = 8;
    private int h = 8;
    private int p = 64;
    private int n;
    private double z;
    private int l;
    private double error = 1000.0;
    private JTextField wText;
    private JTextField hText;
    private JTextField pText;
    private JTextField nText;
    private JTextField zText;
    private JTextField lText;
    private JTextField eText;
    private Matrix matrix;
    private Task task;

    public Settings() {
        this.setSize(250, 270);
        this.setResizable(false);
        this.setLocation(430, 120);
        JPanel panel = new JPanel();
        this.setContentPane(panel);
        addContent();
    }

    public void addContent() {
        JLabel wLabel = new JLabel("W= ");
        wText = new JTextField("" + w, 18);
        this.getContentPane().add(wLabel);
        this.getContentPane().add(wText);
        JLabel hLabel = new JLabel("H= ");
        hText = new JTextField("" + h, 18);
        this.getContentPane().add(hLabel);
        this.getContentPane().add(hText);
        JLabel pLabel = new JLabel("P= ");
        pText = new JTextField("" + p, 18);
        this.getContentPane().add(pLabel);
        this.getContentPane().add(pText);
        JLabel nLabel = new JLabel("N= ");
        nText = new JTextField("" + w * h * 3, 18);
        nText.setEditable(false);
        this.getContentPane().add(nLabel);
        this.getContentPane().add(nText);
        JLabel zLabel = new JLabel("Z= ");
        zText = new JTextField("", 18);
        zText.setEditable(false);
        this.getContentPane().add(zLabel);
        this.getContentPane().add(zText);
        JLabel lLabel = new JLabel("L= ");
        lText = new JTextField("", 18);
        lText.setEditable(false);
        this.getContentPane().add(lLabel);
        this.getContentPane().add(lText);
        JLabel eLabel = new JLabel("E= ");
        eText = new JTextField("" + error, 18);
        this.getContentPane().add(eLabel);
        this.getContentPane().add(eText);
        JButton butt = new JButton("Обновить значения");
        butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
        this.getContentPane().add(butt);
        JButton butt1 = new JButton("Применить");
        butt1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accept();
            }
        });
        this.getContentPane().add(butt1);
    }

    public void update() {
        task = new Task(Integer.parseInt(wText.getText()), Integer.parseInt(hText.getText()), Integer.parseInt(pText.getText()), Double.parseDouble(eText.getText()));

        w = Integer.parseInt(wText.getText());
        h = Integer.parseInt(hText.getText());
        p = Integer.parseInt(pText.getText());
        n = Integer.parseInt(wText.getText()) * 3
                * Integer.parseInt(hText.getText());
        l = task.decomposeImage();
        nText.setText(""+ n);
        lText.setText("" + l);
        z = (n*l)/((n+l)*p +2.);
        zText.setText("" + z);
        error = Double.parseDouble(eText.getText());
    }


    public void accept() {
        this.setVisible(false);
    }

    public Task getTask(){
        return this.task;
    }
}
