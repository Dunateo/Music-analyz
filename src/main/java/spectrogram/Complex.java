package spectrogram;

public class Complex {

    private final double re, im;

    //Init de Complex vide
    public Complex() { this(0, 0); }

    //Init de Complex parametré
    public Complex(double real, double imag) { re = real; im = imag; }

    //Addition de 2 Complex
    public Complex add(Complex b) {
        return new Complex(this.re + b.re, this.im + b.im);
    }

    //Soustraction de 2 Complex
    public Complex sub(Complex b) { return new Complex(this.re - b.re, this.im - b.im); }

    //Multiplication de 2 Complex
    public Complex mult(Complex b) {
        return new Complex(this.re * b.re - this.im * b.im, this.re * b.im + this.im * b.re);
    }

    @Override
    //Affichage de System.out.println
    public String toString() { return String.format("(%f,%f)", re, im); }
}
