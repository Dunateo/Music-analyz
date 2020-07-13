package test;

public class Neurone {
    // Coefficient générique de mise à jour des poids, commun à tous les neurones
    private static float ETA = 0.1f;
    // Tolérance générique permettant d'accepter la sortie d'un neurone comme valable
    public static float ToleranceSortie = 95.e-2f;
    // Tableau des points synaptiques d'un neurone
    private float[] weight;
    // Valeur de sortie d'un neurone (à "Not A Number" par défaut)
    private float sortie = Float.NaN;
    // Fonction d'activation d'un neurone (peut facilement être modifiée par héritage)
    public float activation(final float valeur) {
        float result = sigmoide(valeur);
        if(result > ToleranceSortie){
            return 1.f;
        }else {
            return 0.f;
        }
    }
    public float sigmoide( final float valeur ) {
        return 1 / ( 1 + (float)Math.exp( -valeur));
    }
    // Constructeur d'un neurone
    public Neurone(final int nbEntrees)
    {
        // Le tableau des poids synaptiques est une case plus grand que le nombre
        // de synapses, car la case en plus joue alors le rôle de "biais"
        weight = new float[nbEntrees+1];
        // On initialise tous les poids de manière alétoire, biais compris
        for (int i = 0; i < nbEntrees+1; ++i)
            weight[i] = (float)(Math.random()*2.-1.);
    }
    // Accesseur pour la valeur de sortie
    public float sortie() {return sortie;}
    // Donne accès aux valeurs des poids synaptiques et au biais
    public float[] synapses() {return weight;}
    // Calcule la valeur de sortie en fonction des entrées, des poids synaptiques,
// du biais et de la fonction d'activation
    public void metAJour(final float[] entrees)
    {
        // On démarre en extrayant le biais
        float somme = weight[weight.length-1];
        // Puis on ajoute les produits entrée-poids synaptique
        for (int i = 0; i < weight.length-1; ++i)
            somme += entrees[i] * weight[i];
        // On fixe la sortie du neurone relativement à la fonction d'activation
        sortie = this.activation(somme);
        //System.out.println("SIGMOIDE -> " + this.sigmoide( somme));
    }
    // Fonction d'apprentissage permettant de mettre à jour les valeurs des
// poids synaptiques ainsi que du biais en fonction de données supervisées
    public void apprentissage(final float[][] entrees, final float[] resultats)
    {
        boolean apprentissageFini = false;
        while ( !apprentissageFini ) {
            apprentissageFini = true;
            for (int i = 0; i < entrees.length; i++) {
                this.metAJour(entrees[i]);
                if ( Float.compare( sortie, resultats[i]) != 0 ) {
                    apprentissageFini = false;
                    weight[weight.length - 1] = this.computeBia(weight[weight.length - 1], sortie, resultats[i]);
                    for (int j = 0; j < weight.length - 1; j++)
                        weight[j] = this.computeWeight(entrees[i][j], weight[j], sortie, resultats[i]);
                }
            }
        }
    }
    private float computeWeight( float x, float weight, float result, float trueResult) {
        return weight + ( ETA * x * ( trueResult - result));
    }
    private  float computeBia( float biais, float result, float trueResult) {
        return biais + ( ETA * ( trueResult - result));
    }

    public static void main(String[] args)
    {
        // Tableau des entrées de la fonction ET (-1 = faux, 1 = vrai)
        final float[][] entrees = {{0,0.40f}, {0,0.2f}, {1,0.80f}, {1,1}};

        // Tableau des sorties de la fonction ET
        final float[] resultats = {0, 0, 1, 1};

        // On crée un neurone taillé pour apprendre la fonction ET
        final Neurone n = new Neurone(entrees[0].length);

        // On lance l'apprentissage de la fonction ET sur ce neurone
        n.apprentissage(entrees, resultats);

        // On affiche les valeurs des synapses et du biais
        System.out.println("Synapses :");
        for (float f : n.synapses())
            System.out.print(f+" ");
        System.out.println();

        // On affiche chaque cas appris
        System.out.println("Taille entrees : "+entrees.length);
        for (int i = 0; i < entrees.length; ++i)
        {
            // Pour une entrée donnée
            final float[] entree = entrees[i];

            // On met à jour la sortie du neurone
            n.metAJour(entree);

            // On affiche cette sortie
            System.out.println("Entree "+i+" : "+n.sortie());
        }
    }
}

