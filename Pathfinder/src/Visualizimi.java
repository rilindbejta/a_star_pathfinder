
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class Visualizimi extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    static Visualizimi instanca;
    static boolean isUserActionEnabled = true;

    int gjatesia = 20;
    int gjeresia = 20;
    boolean kaCaktuarFillimin, kaCaktuarFundin;

    Node[][] nyjet = new Node[50][50];
    Node fillimi, end;
    Algoritmi algoritmi = new Algoritmi();

    public static Visualizimi getinstanca() {
        if (instanca == null)
            instanca = new Visualizimi();
        return instanca;
    }

    private Visualizimi() {

        this.setBackground(new Color(224, 252, 214));
        this.setFocusable(true);
        this.requestFocusInWindow();

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

        //initialize nyjet
        for (int r = 0; r < nyjet.length; r++) {
            for (int c = 0; c < nyjet[r].length; c++) {
                nyjet[r][c] = new Node(r, c);
            }
        }
        // getEdges
        for (int r = 0; r < nyjet.length; r++) {
            for (int c = 0; c < nyjet[r].length; c++) {
                nyjet[r][c].merrSkajet(nyjet);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int rreshti = 0; rreshti < nyjet.length; rreshti++) {
            for (int kolona = 0; kolona < nyjet[rreshti].length; kolona++) {
                if (nyjet[rreshti][kolona].eshteVizituar && !nyjet[rreshti][kolona].neRruge) {
                    g2d.setColor(new Color(128, 166, 244));
                    g2d.fillRoundRect(kolona * gjeresia, rreshti * gjatesia, gjeresia, gjatesia, 0, 0);
                }
                if (nyjet[rreshti][kolona].listaHapur && !nyjet[rreshti][kolona].eshteVizituar) {
                    g2d.setColor(new Color(10,186,181));
                    g2d.fillRoundRect(kolona * gjeresia +
                                    (gjeresia * 1/5), rreshti * gjatesia + (gjatesia * 1/5),
                            (int)(gjeresia * 3/5), (int)(gjatesia * 3/5),12,12);
                }
                if (nyjet[rreshti][kolona].eshteFillimi) {
                    g2d.setColor(new Color(80, 226, 80));
                    g2d.fillRoundRect(kolona * gjeresia, rreshti * gjatesia, gjeresia, gjatesia,12,12);
                }
                if (nyjet[rreshti][kolona].eshteFundi) {
                    g2d.setColor(new Color(198, 48, 48));
                    g2d.fillRoundRect(kolona * gjeresia, rreshti * gjatesia, gjeresia, gjatesia, 12, 12);
                }
                if (nyjet[rreshti][kolona].eshtePengese) {
                    g2d.setColor(new Color(0, 0, 0));
                    g2d.fillRect(kolona * gjeresia, rreshti * gjatesia, gjeresia, gjatesia);
                }
                if (nyjet[rreshti][kolona].neRruge) {
                    g2d.setColor(new Color(45, 70, 247, 255));
                    g2d.fillRoundRect(kolona * gjeresia, rreshti * gjatesia, gjeresia, gjatesia, 12, 12);
                }
            }
        }

        // paint grid
        g2d.setColor(new Color(7,7,7));
        for (int r = 0; r < nyjet.length; r++)
            g2d.drawLine(0, r * gjatesia, 800, r * gjatesia);
        for (int c = 0; c < nyjet[0].length; c++)
            g2d.drawLine(c * gjeresia, 0, c * gjeresia, 800);
    }


    public void restarto() {

        isUserActionEnabled = true;

        for (int r = 0; r < nyjet.length; r++) {
            for (int c = 0; c < nyjet[r].length; c++) {
                nyjet[r][c] = new Node(r, c);
            }
        }
        // getEdges
        for (int r = 0; r < nyjet.length; r++) {
            for (int c = 0; c < nyjet[r].length; c++) {
                nyjet[r][c].merrSkajet(nyjet);
            }
        }
        fillimi = null;
        end = null;
        kaCaktuarFillimin = false;
        kaCaktuarFundin = false;
        algoritmi = new Algoritmi();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int rreshti = e.getY() / gjatesia;
        int kolona = e.getX() / gjeresia;

        if (e.getButton() == MouseEvent.BUTTON1 && isUserActionEnabled &&
                rreshti >= 0 && rreshti < nyjet.length &&
                kolona >= 0 && kolona < nyjet[0].length) {

            if (!kaCaktuarFillimin) {
                fillimi = nyjet[rreshti][kolona];
                fillimi.eshteFillimi = true;
                kaCaktuarFillimin = true;
            } else if (!kaCaktuarFundin && !nyjet[rreshti][kolona].eshteFillimi && !fillimi.eshteAferMe(nyjet[rreshti][kolona])) {
                end = nyjet[rreshti][kolona];
                end.eshteFundi = true;
                kaCaktuarFundin = true;
            }
        }
        repaint();

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int r = e.getY() / gjatesia;
        int c = e.getX() / gjeresia;

        //check for fillimi and end tiles
        if (!kaCaktuarFillimin) {
            fillimi = nyjet[r][c];
            fillimi.eshteFillimi = true;
            kaCaktuarFillimin = true;
        } else if (!kaCaktuarFundin && !nyjet[r][c].eshteFillimi && !fillimi.eshteAferMe(nyjet[r][c])) {
            end = nyjet[r][c];
            end.eshteFundi = true;
            kaCaktuarFundin = true;
        }


        //check for obstacle tiles
        if (kaCaktuarFillimin && kaCaktuarFundin &&
                !nyjet[r][c].eshteFillimi && !nyjet[r][c].eshteFundi &&
                !nyjet[r][c].eshtePengese && isUserActionEnabled)
            nyjet[r][c].eshtePengese = true;

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && isUserActionEnabled) {
            algoritmi.A_Star(fillimi, end);
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            restarto();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}
