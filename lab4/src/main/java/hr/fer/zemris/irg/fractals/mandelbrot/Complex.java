package hr.fer.zemris.irg.fractals.mandelbrot;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Implementation of complex numbers. All methods that modify the current number
 * return the new instance of class. Perform basic operation such as adding,
 * multiplying, etc. For full list of operations see method summary and details.
 *
 * @author Dominik Stanojevic
 * @version 1.0
 *
 */
public class Complex {
    /**
     * number zero
     */
    public static final Complex ZERO = new Complex(0, 0);
    /**
     * number one
     */
    public static final Complex ONE = new Complex(1, 0);
    /**
     * number minus one
     */
    public static final Complex ONE_NEG = new Complex(-1, 0);
    /**
     * number 0 + 1i
     */
    public static final Complex IM = new Complex(0, 1);
    /**
     * number 0 - 1i
     */
    public static final Complex IM_NEG = new Complex(0, -1);

    /**
     * the real part of the complex number
     */
    private double real;

    /**
     * @return the real
     */
    public double getReal() {
        return real;
    }

    /**
     * @return the imaginary
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * the imaginary part of the complex number
     */
    private double imaginary;

    /**
     * Creates new complex number equal to zero.
     */
    public Complex() {
    }

    /**
     * Creates new complex number with given real and imaginary part.
     *
     * @param re
     *            the real part of the complex number
     * @param im
     *            the imaginary part of the complex number
     */
    public Complex(double re, double im) {
        real = re;
        imaginary = im;
    }

    /**
     * Returns new instance of complex number from given magnitude and angle.
     *
     * @param magnitude
     *            magnitude of the complex number
     * @param angle
     *            angle of the complex number
     * @return new complex number from given arguments
     */
    private static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
        double real = magnitude * Math.cos(angle);
        double imaginary = magnitude * Math.sin(angle);
        return new Complex(real, imaginary);
    }

    /**
     * Returns module of the complex number.
     *
     * @return module of the complex number
     */
    public double module() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    /**
     * Multiplies two complex number with each other and returns result as a new
     * instance.
     *
     * @param c
     *            one factor of multiplication
     * @return result of a multiplication, as a new instance
     */
    public Complex multiply(Complex c) {
        ensureComplex(c);

		/*
		 * z1 = a + bi; z2 = c + di; z = z1 * z2 -> Re{z} = a*c - b*d; Im{z} =
		 * a*d + b*c
		 */
        double real = this.real * c.real - this.imaginary * c.imaginary;
        double imaginary = this.real * c.imaginary + this.imaginary * c.real;
        return new Complex(real, imaginary);
    }

    /**
     * Divides one complex number with another and returns result as a new
     * instance.
     *
     * @param c
     *            divisor
     * @return result of a division, as a new instance
     * @exception IllegalArgumentException
     *                if divisor is zero
     */
    public Complex divide(Complex c) {
        ensureComplex(c);
        if (c.real == 0 && c.imaginary == 0) {
            throw new IllegalArgumentException("Cannot divide by zero.");
        }

		/*
		 * z = z1 / z2; z3 = conjugated(z2); z = z1 * z3 / (magnitude(z2))^2
		 */
        Complex conjugate = c.conjugation();
        double magnitude = c.module();
        double denominator = Math.pow(magnitude, 2);
        Complex nominator = this.multiply(conjugate);

        double real = nominator.real / denominator;
        double imaginary = nominator.imaginary / denominator;
        return new Complex(real, imaginary);
    }

    /**
     * Adds one complex number to another and returns sum as a new instance of
     * complex number.
     *
     * @param c
     *            complex number to be added to this instance of a complex
     *            number
     * @return sum of two complex numbers as a new instance of this class
     */
    public Complex add(Complex c) {
        ensureComplex(c);

        double real = this.real + c.real;
        double imaginary = this.imaginary + c.imaginary;
        return new Complex(real, imaginary);
    }

    /**
     * Adds one complex number with another and returns result as a new instance
     * of complex number.
     *
     * @param c
     *            subtrahend in this operation
     * @return difference of two complex number, as a new instance
     */
    public Complex sub(Complex c) {
        ensureComplex(c);

        return add(c.negate());
    }

    /**
     * Negates this instance of complex number and returns the negation as a new
     * instance.
     *
     * @return the negated complex number
     */
    public Complex negate() {
        return new Complex(-real, -imaginary);
    }

    /**
     * Calculates and returns power of the complex number, using De Moivre's
     * formula. nth power argument must be a non-negative integer.
     *
     * @param n
     *            nth power of a complex number, non-negative integer
     * @return power of a complex number
     * @exception IllegalArgumentException
     *                if negative integer was given as an argument
     */
    public Complex power(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Power should be a non-negative number.");
        }

        double magnitude = Math.pow(this.module(), n);
        double angle = n * Math.atan2(imaginary, real);
        return fromMagnitudeAndAngle(magnitude, angle);
    }

    /**
     * Calculates and returns all roots of a complex number, using De Moivre's
     * formula. nth root argument must be positive integer.
     *
     * @param n
     *            nth root of a complex number
     * @return array containing all roots of a complex number
     * @exception IllegalArgumentException
     *                if root argument is not positive integer
     */
    public List<Complex> root(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Root should be a postive value.");
        }
        List<Complex> roots = new ArrayList<>(n);

        double magnitude = Math.pow(this.module(), 1. / n);
        double angle = Math.atan2(imaginary, real);
        for (int i = 0; i < n; i++) {
            double rootAngle = (angle + 2 * i * Math.PI) / n;
            roots.add(Complex.fromMagnitudeAndAngle(magnitude, rootAngle));
        }

        return roots;
    }

    @Override
    public String toString() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat realFormat = new DecimalFormat("+ #.####;- #.####", symbols);
        DecimalFormat imaginaryFormat = new DecimalFormat("+ #.####i;- #.####i", symbols);

        return realFormat.format(real) + " " + imaginaryFormat.format(imaginary);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(imaginary);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(real);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Complex))
            return false;
        Complex other = (Complex) obj;
        if (Double.doubleToLongBits(imaginary) != Double.doubleToLongBits(other.imaginary))
            return false;
        if (Double.doubleToLongBits(real) != Double.doubleToLongBits(other.real))
            return false;
        return true;
    }

    /**
     * @return conjugated complex number
     */
    private Complex conjugation() {
        return new Complex(real, -imaginary);
    }

    /**
     * Ensures that given argument is not null.
     *
     * @param c
     *            complex number passed as an argument
     * @exception IllegalArgumentException
     *                if complex number is null
     */
    private void ensureComplex(Complex c) {
        if (c == null) {
            throw new IllegalArgumentException("Complex number cannot have null value.");
        }
    }
}