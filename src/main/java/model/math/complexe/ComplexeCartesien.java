package model.math.complexe;

public class ComplexeCartesien implements Complexe{

    private double reel;
    private double imag;

    public ComplexeCartesien(double reel, double imag)
    {
        this.reel = reel;
        this.imag = imag;
    }

    public double reel() {return reel;}
    public double imag() {return imag;}
    public double mod() {return Math.sqrt(reel*reel+imag*imag);}
    public double arg() {return Math.atan2(imag, reel);}

    public Complexe add(final Complexe complexe)
    {
        this.reel += complexe.reel();
        this.imag += complexe.imag();
        return this;
    }

    public Complexe sub(final Complexe complexe)
    {
        this.reel -= complexe.reel();
        this.imag -= complexe.imag();
        return this;
    }

    public Complexe mul(final Complexe complexe)
    {
        double rtemp = reel*complexe.reel()-imag*complexe.imag();
        imag = reel*complexe.imag()+imag*complexe.reel();
        reel = rtemp;
        return this;
    }

    public Complexe plus(final Complexe complexe)
    {
        return new ComplexeCartesien(reel(), imag()).add(complexe);
    }

    public Complexe moins(final Complexe complexe)
    {
        return new ComplexeCartesien(reel(), imag()).sub(complexe);
    }

    public Complexe fois(final Complexe complexe)
    {
        return new ComplexeCartesien(reel(), imag()).mul(complexe);
    }

}
