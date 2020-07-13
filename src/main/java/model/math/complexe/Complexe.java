package model.math.complexe;

public interface Complexe {

    double reel();
    double imag();
    double mod();
    double arg();

    // Opérations modifiant l'objet
    Complexe add(final Complexe complexe);
    Complexe sub(final Complexe complexe);
    Complexe mul(final Complexe complexe);
    // Opérations ne modifiant pas l'objet (renvoi d'une copie modifiée)
    Complexe plus(final Complexe complexe);
    Complexe moins(final Complexe complexe);
    Complexe fois(final Complexe complexe);


}
