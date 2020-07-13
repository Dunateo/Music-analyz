package model.math.complexe;

public class ComplexePolaire implements Complexe{

    private double mod;
    private double arg;

    public ComplexePolaire(double mod, double arg)
    {
        this.mod = mod;
        this.arg = arg;
    }

    public double reel() {return mod*Math.cos(arg);}
    public double imag() {return mod*Math.sin(arg);}
    public double mod() {return mod;}
    public double arg() {return arg;}

    public Complexe add(final Complexe complexe)
    {
        Complexe moi = new ComplexeCartesien(this.reel(), this.imag());
        moi.add(complexe);
        this.mod = moi.mod();
        this.arg = moi.arg();
        return this;
    }

    public Complexe sub(final Complexe complexe)
    {
        Complexe moi = new ComplexeCartesien(this.reel(), this.imag());
        moi.sub(complexe);
        this.mod = moi.mod();
        this.arg = moi.arg();
        return this;
    }

    public Complexe mul(final Complexe complexe)
    {
        mod *= complexe.mod();
        arg += complexe.arg();
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
        return new ComplexePolaire(mod(), arg()).mul(complexe);
    }

}
