/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jflock;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author alorenzo
 */
public class Flocki {

    Vector p;
    Vector v;
    Vector a;
    private int WIDTH = 20;
    private int HEIGHT = 10;
    private double PERCEPTION = 1000;
    private double MAX_ACCELERATION = 4;
    private double MAX_SPEED = 4;

    public Flocki() {
        Random random = new Random();
        double x = 0 + (Space.CANVAS_WIDTH - 0) * random.nextDouble();
        double y = 0 + (Space.CANVAS_HEIGHT - 0) * random.nextDouble();
        double z = 0 + (Space.CANVAS_DEPTH - 0) * random.nextDouble();
        p = new Vector(x, y);
        v = new Vector().randomize();
        a = new Vector().randomize();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.p);
        return hash;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        Path2D path = new Path2D.Double();

        double[] vertexX = new double[]{
            p.x + Math.cos(0) * WIDTH,
            p.x - Math.signum(Math.cos(120)) * WIDTH,
            p.x,
            p.x - Math.signum(Math.cos(120)) * WIDTH};
        double[] vertexY = new double[]{
            p.y + Math.sin(0),
            p.y - Math.signum(Math.sin(120)) * HEIGHT,
            p.y,
            p.y + Math.signum(Math.sin(120)) * HEIGHT};
        path.moveTo(vertexX[0], vertexY[0]);
        for (int i = 1; i < vertexX.length; ++i) {
            path.lineTo(vertexX[i], vertexY[i]);
        }
        path.lineTo(vertexX[0], vertexY[0]);
        g2d.draw(path);
        //Centro
        g2d.setColor(Color.black);
        g2d.draw(new Line2D.Double(p.x, p.y, p.x, p.y));
        //Velocidad
        g2d.setColor(Color.green);
        g2d.draw(new Line2D.Double(p.x, p.y, p.x + 20 * v.x, p.y + 20 * v.y));
        //Aceleración
        g2d.setColor(Color.blue);
        g2d.draw(new Line2D.Double(p.x, p.y, p.x + 20 * a.x, p.y + 20 * a.y));
        //Percepción
        g2d.setColor(Color.cyan);
        g2d.draw(new Ellipse2D.Double(p.x - PERCEPTION / 2, p.y - PERCEPTION / 2, PERCEPTION, PERCEPTION));
    }

    Vector align(ArrayList<Flocki> localFlock) {
        int localFlockii = 0;
        Vector steering = new Vector();

        for (Flocki flocki : localFlock) {
            if (distance(flocki) < PERCEPTION && !this.equals(flocki)) {
                localFlockii++;
                steering = steering.add(flocki.v);
            }
        }
        if (localFlockii > 0) {
            steering = steering.div(localFlockii);
            steering.subs(this.v);
            steering = steering.unit(MAX_ACCELERATION);
        }

        return steering;
    }

    Vector cohesion(ArrayList<Flocki> localFlock) {
        int localFlockii = 0;
        Vector steering = new Vector();

        for (Flocki flocki : localFlock) {
            if (distance(flocki) < PERCEPTION && !this.equals(flocki)) {
                localFlockii++;
                steering = steering.add(flocki.p);
            }
        }

        if (localFlockii > 0) {
            steering = this.p.subs(steering);
            steering = steering.div(localFlockii + 1);
            steering = steering.unit(-1);
        }

        return steering;
    }

    void flock(ArrayList<Flocki> localFlock) {
//        Vector alignment = align(localFlock);
        Vector alignment = cohesion(localFlock);
        this.a = alignment;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Flocki other = (Flocki) obj;
        if (!Objects.equals(this.p, other.p)) {
            return false;
        }
        return true;
    }

    public void update() {
        if (v != null) {
            p = p.add(v);
        }
        checkEdges();
        if (a != null) {
            v = v.add(a);
            v = v.unit(MAX_SPEED);
        }

    }

    private void checkEdges() {
        if (this.p.x < 0) {
            this.p.x = Space.CANVAS_WIDTH;
        } else if (this.p.x > Space.CANVAS_WIDTH) {
            this.p.x = 0;
        }
        if (this.p.y < 0) {
            this.p.y = Space.CANVAS_HEIGHT;
        } else if (this.p.y > Space.CANVAS_HEIGHT) {
            this.p.y = 0;
        }
    }

    private double distance(Flocki other) {
        return this.p.distance(other.p);
    }

    void resetAcceleration() {
        this.a = new Vector().randomize();
    }

}
