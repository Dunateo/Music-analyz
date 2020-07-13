package model.perceptron;

import lombok.Getter;
import model.MusicalNote;

import java.io.Serializable;

@Getter
public class Perceptron implements Serializable {

    private static final long serialVersionUID = 6549315049357253453L;

    private MusicalNote note;
    // Coefficient générique de mise à jour des poids, commun à tous les neurones
    private static double ETA = 0.1f;
    // Tolérance générique permettant d'accepter la sortie d'un neurone comme valable
    public static double ToleranceSortie = 95.e-2f;
    // Tableau des points synaptiques d'un neurone
    private double[] weight;
    // Valeur de sortie d'un neurone (à "Not A Number" par défaut)
    private double sortie = Double.NaN;
    // Fonction d'activation d'un neurone (peut facilement être modifiée par héritage)
    public double activation(final double valeur) {
        double result = sigmoide(valeur);
        if(result > ToleranceSortie){
            return 1.f;
        }else {
            return 0.f;
        }
    }
    public double sigmoide( final double valeur ) {
        return 1 / ( 1 + Math.exp( -valeur));
    }
    // Constructeur d'un neurone
    public Perceptron(final int nbEntrees, MusicalNote note)
    {
        this.note = note;
        // Le tableau des poids synaptiques est une case plus grand que le nombre
        // de synapses, car la case en plus joue alors le rôle de "biais"
        weight = new double[nbEntrees+1];
        // On initialise tous les poids de manière alétoire, biais compris
        for (int i = 0; i < nbEntrees+1; ++i)
            weight[i] = (float)(Math.random()*2.-1.);
    }
    // Accesseur pour la valeur de sortie
    public double sortie() {return sortie;}
    // Donne accès aux valeurs des poids synaptiques et au biais
    public double[] synapses() {return weight;}
    // Calcule la valeur de sortie en fonction des entrées, des poids synaptiques,
// du biais et de la fonction d'activation
    public void metAJour(final double[] entrees)
    {
        // On démarre en extrayant le biais
        double somme = weight[weight.length-1];
        // Puis on ajoute les produits entrée-poids synaptique
        for (int i = 0; i < weight.length-1; ++i)
            somme += entrees[i] * weight[i];
        // On fixe la sortie du neurone relativement à la fonction d'activation
        sortie = this.activation(somme);
        //System.out.println("SIGMOIDE -> " + this.sigmoide( somme));
    }
    // Fonction d'apprentissage permettant de mettre à jour les valeurs des
// poids synaptiques ainsi que du biais en fonction de données supervisées
    public void apprentissage(final double[][] entrees, final double[] resultats)
    {
        boolean apprentissageFini = false;
        while ( !apprentissageFini ) {
            apprentissageFini = true;
            for (int i = 0; i < entrees.length; i++) {
                this.metAJour(entrees[i]);
                if ( Double.compare( sortie, resultats[i]) != 0 ) {
                    apprentissageFini = false;
                    weight[weight.length - 1] = this.computeBia(weight[weight.length - 1], sortie, resultats[i]);
                    for (int j = 0; j < weight.length - 1; j++)
                        weight[j] = this.computeWeight(entrees[i][j], weight[j], sortie, resultats[i]);
                }
            }
        }
    }
    private double computeWeight( double x, double weight, double result, double trueResult) {
        return weight + ( ETA * x * ( trueResult - result));
    }
    private  double computeBia( double biais, double result, double trueResult) {
        return biais + ( ETA * ( trueResult - result));
    }
}
