package model.math;

import model.math.complexe.Complexe;
import model.math.complexe.ComplexeCartesien;
import model.math.complexe.ComplexePolaire;

public class FFTCplx {

    private int taille;

    // Indiquer la taille dans le constructeur permettra des optimisations par la suite :
    // on pourra facilement transformer les méthodes statiques en méthodes
    // d'instance, et optimiser l'objet en fonction de la taille indiquée dans
    // le constructeur
    public FFTCplx(int taille)
    {
        this.taille = taille;
    }


    // Sous-signal obligatoirement découpé par pas de deux
    // La méthode est statique car c'est plutôt une fonction dans notre cas,
    // et privée car elle n'a de réel intérêt qu'à l'intérieur de la classe
    private static Complexe[] demiSignal(Complexe[] signal, int depart)
    {
        Complexe[] sousSignal = new Complexe[signal.length/2];
        for (int i = 0; i < sousSignal.length; ++i)
            sousSignal[i] = signal[depart+2*i];
        return sousSignal;
    }

    // La méthode est statique car c'est plutôt une fonction dans notre cas ;
    // on pourra la rendre méthode d'instance dès qu'on voudra optimiser le code
    // et tenir compte de la taille, ce qui permettra d'éviter de refaire
    // certains calculs à chaque nouvel appel de appliqueSur()
    public static Complexe[] appliqueSur(Complexe[] signal)
    {
        // On crée la donnée renvoyée en retour
        Complexe[] trSignal = new Complexe[signal.length];
        if (signal.length == 1)	// Cas trivial de la FFT d'une valeur unique
            trSignal[0] = new ComplexeCartesien(signal[0].reel(), signal[0].imag());
        else	// Si le cas n'est pas trivial
        {
            // Découpage des deux sous-groupes de données sur lesquels on applique la FFT
            // (voir définition de cours)
            final Complexe[] P0 = appliqueSur(demiSignal(signal, 0));
            final Complexe[] P1 = appliqueSur(demiSignal(signal, 1));
            // On applique le regroupement "papillon" pour créer le résultat final
            for (int k = 0; k < signal.length/2; ++k)
            {
                final ComplexePolaire expo = new ComplexePolaire(1., -2.*Math.PI*k/signal.length);
                final Complexe temp = P0[k];
                trSignal[k] = temp.plus(expo.fois(P1[k]));
                trSignal[k+signal.length/2] = temp.moins(expo.fois(P1[k]));
            }
        }
        return trSignal;
    }

    public static Complexe[] mainFFT(float[] data){
        Complexe[] signalTest = new Complexe[data.length];
        for (int i = 0; i < data.length; ++i){
            signalTest[i] = new ComplexeCartesien(data[i], 0);
        }
        Complexe[] resultat;
        resultat = FFTCplx.appliqueSur(signalTest);
        // On affiche les valeurs du résultat

		/*
		for (int i = 0; i < resultat.length; ++i) {
			System.out.print(i+" : ("+(float)resultat[i].reel()+" ; "+(float)resultat[i].imag()+"i)");
			System.out.println(", ("+(float)resultat[i].mod()+" ; "+(float)resultat[i].arg()+" rad)");
		}

		 */

        return resultat;
    }

}
