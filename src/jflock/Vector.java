/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jflock;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

/**
 *
 * @author alorenzo
 */
public class Vector {

    public static final String I = "i"; // "\u00EE"; // i^
    public static final String J = "j"; // "\u0135"; // j^
//    public static final String K = "k"; // "k\0302"; // k^

    public double x;
    public double y;
//    public double z;

    /**
     * Constructor to create a null vector i.e. 0i+0j+0k
     */
    public Vector() {
        this(0, 0/*, 0*/);
    }

    /**
     * Constructor to create a vector with the given values of i, j and k
     */
    public Vector(double x, double y/*, double z*/) {
        this.x = x;
        this.y = y;
//        this.z = z;
    }

    /**
     * Return the string notation of the Vector.Reduced it if any of x, y and z
     * are integers
     */
    @Override
    public String toString() {
        StringBuilder notation = new StringBuilder();
        DecimalFormat formatter = new DecimalFormat("0000.00");      
        notation.append(formatter.format(Math.abs(this.x)))
                .append("i,")
                .append(formatter.format(Math.abs(this.y)))
                .append("j");

        return notation.toString();
    }

    public static Vector parseVector(String s) {
        return new Vector();
    }

    /**
     * Gives the length of the vector
     */
    public double modulus() {
//        return Math.hypot(x, Math.hypot(y, z));
        return Math.hypot(x, y);
    }

    /**
     * Check if two vectors are parallel or not
     */
    public boolean isParallel(Vector v) {
        if (modulus() == 0 || v.modulus() == 0) {
            return true;
        }
        if ((x == 0) != (v.x == 0) || (y == 0) != (v.y == 0)/* || (z == 0) != (v.z == 0)*/) {
            return false;
        }
        double ratio = 0;
        if (x != 0) {
            ratio = v.x / x;
        }
        if (y != 0) {
            if (ratio == 0) {
                ratio = v.y / y;
            } else {
                if (v.y / y != ratio) {
                    return false;
                }
            }
        }
//        if (z != 0) {
//            if (ratio != 0 && v.y / y != ratio) {
//                return false;
//            }
//        }
        return true;
    }

    /**
     * Returns the unit vector in the direction of the Calling vector object
     */
    public Vector unit(double magnitude) {
        double mod = modulus();
        if (mod == 0 || mod == 1) {
            return this;
        }
        return new Vector(x * magnitude / mod, y * magnitude / mod/*, z * magnitude/ mod*/);
    }

    public Vector unit() {
        return unit(1);
    }

    /**
     * Computes and returns the dot product between two vector
     */
    public double dot(Vector v) {
        return x * v.x + y * v.y/* + z * v.z*/;
    }

    /**
     * Compute and returns the angle between two vectors in radians
     */
    public double angle(Vector v) {
        double dotPro = dot(v);
        double modObj = modulus();
        double modV = v.modulus();
        double angle = Math.acos(dotPro / (modObj * modV));
        return angle;
    }

    public double degAngle(Vector v) {
        double angle = Math.toDegrees(angle(v));
        return angle;
    }

//    /**
//     * Computes and returns the cross product of the two vector
//     */
//    public Vector cross(Vector v) {
//        double resx = (y * v.z) - (v.y * z);
//        double resy = -((x * v.z) - (v.x * z));
//        double resz = (x * v.y) - (v.x * y);
//        return new Vector(resx, resy, resz);
//    }
    public Vector randomize() {
        return new Vector().randomize(-2, 2);
    }

    public Vector randomize(int min, int max) {
//        double min = -2d, max = 2d;
        Random random = new Random();
        Vector vector = new Vector();
        vector.x = min + (max - min) * random.nextDouble();
        vector.y = min + (max - min) * random.nextDouble();
//        v.z = min + (max - min) * random.nextDouble();
        return vector;
    }

    Vector add(Vector addend) {
        return new Vector(this.x + addend.x, this.y + addend.y);
    }

    Vector subs(Vector v) {
        Vector subs = new Vector();
        if (v != null) {
            subs.x = this.x - v.x;
            subs.y = this.y - v.y;
//            subs.z = this.z + v.z;
        }
        return subs;
    }

    Vector div(int localFlockii) {
        return new Vector(this.x / localFlockii, this.y / localFlockii);
//        this.z = this.z / localFlockii;
    }

    double distance(Vector other) {
        double a, b, c;
        a = this.x - other.x;
        b = this.y - other.y;
        /*c = this.z - other.z;*/
        return Math.sqrt(a * a + b * b/* + c * c*/);
    }

    void set(double d) {
        this.x = d;
        this.y = d;
//        this.z = d;
    }

}
