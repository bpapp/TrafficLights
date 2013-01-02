/*
 * Készíts egy programot, amely egy ablakba kirajzol egy közúti jelzőlámpát. Legyen továbbá az
 * ablakban egy gomb, amelynek nyomogatásával a jelzőlámpa állapotai között lehet lépkedni
 * (piros -> piros-sárga -> zöld -> sárga -> piros).
 */
package trafficlights;

import java.awt.Button;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class TrafficLights {

    private final MyPanel myPanelRed = new MyPanel();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                TrafficLights trafficL = new TrafficLights();
                trafficL.createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {
       
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridLayout experimentLayout = new GridLayout(2, 1);
        f.setLayout(experimentLayout);
        f.add(myPanelRed);

        Button switchButton = new Button("Switch");

        //Add action listener to button
        switchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                myPanelRed.setNextState();
                myPanelRed.repaint();
            }
        });

        f.add(switchButton);
        f.pack();
        f.setVisible(true);
    }
}

class MyPanel extends JPanel {

    public enum EnumColor {

        INIT, RED, RED_YELLOW, GREEN, YELLOW
    }
    public EnumColor eColor = EnumColor.INIT;
    Shape piros = new Ellipse2D.Double(20, 20, 50, 50);
    Shape sarga = new Ellipse2D.Double(20, 80, 50, 50);
    Shape zold = new Ellipse2D.Double(20, 140, 50, 50);

    @Override
    public void repaint() {
        super.repaint();
    }

    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    /**
     * There are 4 legal state not five, what are used in lighting.
     * Init state can occure only at start.
     * We take the next enum state from an array.
     * Mod 4 enables 4 states, and +1 excludes INIT state.
     */
    public void setNextState() {
        int k = eColor.ordinal() % 4 + 1;

        // next trafficlights state
        eColor = EnumColor.values()[k];
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250, 200);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        switch (eColor) {
            case INIT:
                g2.setColor(Color.BLACK);
                g2.draw(piros);
                g2.draw(sarga);
                g2.draw(zold);
                break;
            case RED:
                g2.setColor(Color.RED);
                g2.fill(piros);
                g2.setColor(Color.BLACK);
                g2.draw(sarga);
                g2.setColor(Color.BLACK);
                g2.draw(zold);
                break;
            case RED_YELLOW:
                g2.setColor(Color.RED);
                g2.fill(piros);
                g2.setColor(Color.YELLOW);
                g2.fill(sarga);
                g2.setColor(Color.BLACK);
                g2.draw(zold);
                break;
            case GREEN:
                g2.setColor(Color.BLACK);
                g2.draw(piros);
                g2.setColor(Color.BLACK);
                g2.draw(sarga);
                g2.setColor(Color.GREEN);
                g2.fill(zold);
                break;
            case YELLOW:
                g2.setColor(Color.BLACK);
                g2.draw(piros);
                g2.setColor(Color.YELLOW);
                g2.fill(sarga);
                g2.setColor(Color.BLACK);
                g2.draw(zold);
                break;
            default:
                break;
        }
    }
}