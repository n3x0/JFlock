/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jflock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author alorenzo
 */
public class Space extends JPanel implements KeyListener, MouseListener {

    private final static int KEYCODE_UP = 38;
    private final static int KEYCODE_DOWN = 40;
    private final static int KEYCODE_LEFT = 37;
    private final static int KEYCODE_RIGHT = 39;
    private final static int KEYCODE_SPACE = 32;
    private final static int KEYCODE_CTRL = 17;

    public static final int CANVAS_HEIGHT = 1000;
    public static final int CANVAS_WIDTH = 1920;
    public static final int CANVAS_DEPTH = 0;

    public static final int FLOCK = 2;
    Timer timer;
    ArrayList<Flocki> flock;
    Flocki selected;

    public Space() {
        initializeSpace();
        initializeFlock();
    }

    private void initializeSpace() {
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        jFrame.addKeyListener(this);
        jFrame.addMouseListener(this);

        jFrame.add(this);

        setVisible(true);
        setBackground(Color.red);
        jFrame.setVisible(true);
    }

    private void initializeFlock() {
        Flocki flocki;
        flock = new ArrayList();

        for (int i = 0; i < FLOCK; i++) {
            flocki = new Flocki();
            flock.add(flocki);
        }

        if (timer == null) {
            timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (Flocki flocki : flock) {
                        flocki.flock(flock);
                        flocki.update();
                    }
                    repaint();
                }
            });
            timer.start();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Iterator<Flocki> iter = flock.iterator();

        while (iter.hasNext()) {
            Flocki flocki = iter.next();
            flocki.draw(g);
//            iter.remove();
        }
        
        if (selected!=null){
            g.setFont(new Font("Monospaced", Font.PLAIN, 40)); 
            char[] chars = ("P: " + selected.p.toString()).toCharArray();
            g.drawChars(chars, 0, chars.length, 20, 40);
            chars = ("V: " + selected.v.toString()).toCharArray();
            g.drawChars(chars, 0, chars.length, 20, 70);
            chars = ("A: " + selected.a.toString()).toCharArray();
            g.drawChars(chars, 0, chars.length, 20, 100);
        }

//        g.drawPolygon(new int[]{10, 20, 30}, new int[]{100, 20, 100}, 3);
    }

    void input(int keycode) {
        switch (keycode) {
            case KEYCODE_UP:
//                    flocki.v = new Vector(0, -1, 0);
                break;
            case KEYCODE_DOWN:
//                    flocki.v = new Vector(0, 1, 0);
                break;
            case KEYCODE_LEFT:
//                    flocki.v = new Vector(-1, 0, 0);
                break;
            case KEYCODE_RIGHT:
//                    flocki.v = new Vector(1, 0, 0);
                break;
            case KEYCODE_SPACE:
                reset();
                break;
            case KEYCODE_CTRL:
                resetAcceleration();
                break;

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keycode = e.getKeyCode();
        input(keycode);
    }

    private void reset() {
        flock.clear();
        initializeFlock();

    }

    private void resetAcceleration() {
        for (Flocki flocki : flock) {
            flocki.resetAcceleration();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (Flocki flocki : flock) {
            if (e.getPoint().getLocation().distance(new Point((int)flocki.p.x, (int)flocki.p.y)) < 50){
                this.selected = flocki;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
