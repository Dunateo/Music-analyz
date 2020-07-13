package test;

/**
 * Class generant une erreur detection des limites d'un perceptron mono-cocuhe
 */
public class OneEntryShort {

    enum notes {DO, LA, RE, SOL};
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
        if (result > ToleranceSortie) {
            return 1.f;
        } else {
            return 0.f;
        }
    }

    public float sigmoide(final float valeur) {
        return 1 / (1 + (float) Math.exp(-valeur));
    }

    // Constructeur d'un neurone
    public OneEntryShort(final int nbEntrees) {
        // Le tableau des poids synaptiques est une case plus grand que le nombre
        // de synapses, car la case en plus joue alors le rôle de "biais"
        weight = new float[nbEntrees + 1];
        // On initialise tous les poids de manière alétoire, biais compris
        for (int i = 0; i < nbEntrees + 1; ++i)
            weight[i] = (float) (Math.random() * 2. - 1.);
    }

    // Accesseur pour la valeur de sortie
    public float sortie() {
        return sortie;
    }

    // Donne accès aux valeurs des poids synaptiques et au biais
    public float[] synapses() {
        return weight;
    }

    // Calcule la valeur de sortie en fonction des entrées, des poids synaptiques,
// du biais et de la fonction d'activation
    public void metAJour(final float[] entrees) {
        // On démarre en extrayant le biais
        float somme = weight[weight.length - 1];
        // Puis on ajoute les produits entrée-poids synaptique
        for (int i = 0; i < weight.length - 1; ++i)
            somme += entrees[i] * weight[i];
        // On fixe la sortie du neurone relativement à la fonction d'activation
        sortie = this.activation(somme);
        //System.out.println("SIGMOIDE -> " + this.sigmoide( somme));
    }

    // Fonction d'apprentissage permettant de mettre à jour les valeurs des
// poids synaptiques ainsi que du biais en fonction de données supervisées
    public void apprentissage(final float[][] entrees, final float[] resultats) {
        boolean apprentissageFini = false;
        while (!apprentissageFini) {
            apprentissageFini = true;
            for (int i = 0; i < entrees.length; i++) {
                this.metAJour(entrees[i]);
                if (Float.compare(sortie, resultats[i]) != 0) {
                    apprentissageFini = false;
                    weight[weight.length - 1] = this.computeBia(weight[weight.length - 1], sortie, resultats[i]);
                    for (int j = 0; j < weight.length - 1; j++)
                        weight[j] = this.computeWeight(entrees[i][j], weight[j], sortie, resultats[i]);
                }
            }
        }
    }

    private float computeWeight(float x, float weight, float result, float trueResult) {
        return weight + (ETA * x * (trueResult - result));
    }

    private float computeBia(float biais, float result, float trueResult) {
        return biais + (ETA * (trueResult - result));
    }

    public static void main(String[] args) {
        // Tableau des entrées de la fonction ET (-1 = faux, 1 = vrai)
        final float[][] entrees = {{65.41f},{73.42f},{98.f},{110.f}};

        // Tableau des sorties de la fonction ET
        final float[] resultats = {1,0,0,0};
        final float[] resultats2 = {0,1,0,1};
        //final float[] resultats3 = {0,0,1,0};

        // On crée un neurone taillé pour apprendre la fonction ET
        final OneEntryShort n = new OneEntryShort(1);
        final OneEntryShort n2 = new OneEntryShort(1);
        //final OneEntryShort n3 = new OneEntryShort(1);

        // On lance l'apprentissage de la fonction ET sur ce neurone
        n.apprentissage(entrees, resultats);
        System.out.println("n OK\n");
        n2.apprentissage(entrees, resultats2);
        System.out.println("n2 OK\n");
        //n3.apprentissage(entrees, resultats3);
        //System.out.println("n3 OK\n");


        // On affiche les valeurs des synapses et du biais
        System.out.println("Synapses :");
        for (float f : n.synapses())
            System.out.print(f + " ");
        System.out.println();

        System.out.println("Synapses2 :");
        for (float h : n2.synapses())
            System.out.print(h + " ");
        System.out.println();

        // On affiche chaque cas appris
        System.out.println("Taille entrees : " + entrees.length);
        for (int i = 0; i < entrees.length; ++i) {
            // Pour une entrée donnée
            final float[] entree = entrees[i];

            // On met à jour la sortie du neurone
            n.metAJour(entree);

            // On affiche cette sortie
            if (n.sortie == 1) {
                System.out.println("Entree" + i + " : " + OneEntryShort.notes.DO);
            } else {
                System.out.println("Entree " + i + " : " + n.sortie());
            }
        }

        for (int i = 0; i < entrees.length; ++i) {
            // Pour une entrée donnée
            final float[] entree = entrees[i];

            // On met à jour la sortie du neurone
            n2.metAJour(entree);

            // On affiche cette sortie
            if (n2.sortie == 1) {
                System.out.println("Entree " + i + " : " + OneEntryShort.notes.LA);
            } else {
                System.out.println("Entree " + i + " : " + n2.sortie());
            }

        }

        //Test après apprentissage
        //final float[] entrees3 = {45f, 23};
        //n2.metAJour(entrees3);
        //System.out.println(n2.sortie);
    }
}
