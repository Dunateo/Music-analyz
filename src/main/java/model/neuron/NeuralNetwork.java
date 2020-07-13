package model.neuron;

import lombok.Getter;
import model.neuron.core.Connection;
import model.neuron.core.Layer;
import model.neuron.core.Neuron;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

@Getter
public class NeuralNetwork implements Serializable {

    private static final long serialVersionUID = 3269645033640567293L;

    private ArrayList<Layer> layers = new ArrayList<>();
    private static float ToleranceSortie = 95.e-2f;
    private double condition = 0.001d;
    private Random rand = new Random();
    private double minError = 10000000000d;


    public NeuralNetwork(int[] neurons) {
        this.initializeLayers( neurons);
    }

    /**
     * initialise le reseau de neurone en fonction du nombre de neurone par couche
     * @param neurons
     */
    private void initializeLayers( int[] neurons) {

        for ( int i = 0 ;  i < neurons.length ; i++) {
            Layer layer = new Layer();

            if( i == 0 ) {
                for ( int j = 0 ; j < neurons[i] ; j++) {
                    Neuron neuron = new Neuron();
                    layer.addNeuron( neuron);
                }
            } else {

                for (int j = 0; j < neurons[i]; j++) {
                    Neuron neuron = new Neuron();
                    neuron.addBiasConnection();
                    neuron.addInputConnection(layers.get(i - 1).getNeurons());
                    layer.addNeuron(neuron);
                }
            }
            System.out.println( layer.getNeurons());
            layers.add( layer);
        }
    }

    /**
     * propage l'entrée des neurones jusqu'a la output layer afin d'obtenir des resultat
     */
    private void feedForward() {
        for ( int i = 1 ; i < layers.size() ; i++ ) {
            for ( int j = 0 ; j < layers.get(i).getNeurons().size() ; j++ ) {
                layers.get(i).getNeurons().get( j).computeOutput();
                System.out.println("[OUTPUT " + i + " | " + j + " ] " + layers.get(i).getNeurons().get( j).getOutput() );
            }
        }
    }

    /**
     * corrige les poids de chaque entréé pour chaque neurone present dans les hidden layers
     * @param expectedResult resultat attendu
     */
    private void backpropagation( double[] expectedResult) {

        // tolerence
        Layer outPutLayer = layers.get( layers.size() - 1);

        // output layers
        for( int i = 0 ; i < outPutLayer.getNeurons().size() ; i++ ) {

            Neuron n = outPutLayer.getNeurons().get(i);

            n.setGradient( (expectedResult[i] - n.getOutput() ) * n.getOutput() * ( 1 - n.getOutput()));
            //ArrayList<Connection> connections = n.getWeight();
            System.out.println("[OUTPUT LAYER GRADIENT] -> " + n.getGradient());

            //Connection con = connections.get(j);
            for (Connection con : n.getWeight()) {
                System.out.println( con.getWeight() + " * ( " + Neuron.ETA + " * " + n.getGradient() + " * " + con.getPrevious().getOutput() + " )");
                double newWeight = con.getWeight() + (Neuron.ETA * n.getGradient() * con.getPrevious().getOutput());
                con.setWeight(newWeight);
                System.out.println("[OUTPUT LAYER] " + con.getWeight());
            }
        }

        for ( int i = layers.size() - 2 ; i > 0 ; i-- ) {

            Layer layer = layers.get(i);

            for ( int j = 0 ;  j < layer.getNeurons().size() ; j++ ) {

                Neuron neuron = layer.getNeurons().get(j);

                double somme = this.hiddentLayerGradient( i + 1, neuron);
                System.out.println("[HIDDEN LAYER GRADIENT] " + neuron.getOutput() + " * ( 1 - " + neuron.getOutput() + " ) * " + somme);
                double gradient = neuron.getOutput() * ( 1 - neuron.getOutput() ) * somme;
                //calcul du gradient des hidden layers
                neuron.setGradient( gradient);
                System.out.println( "[HIDDEN LAYER GRADIENT ] " + gradient);
                //ArrayList<Connection> connections = neuron.getWeight();

                for (int k = 0; k < neuron.getWeight().size(); k++) {
                    //Connection con = connections.get(k);
                    Connection con = neuron.getWeight().get( k);
                    double newWeight = con.getWeight() + (Neuron.ETA * neuron.getGradient() * con.getPrevious().getOutput());
                    System.out.println("[HIDDEN LAYER WEIGHT] " + con.getWeight()  + " + ( " + Neuron.ETA +  " * " + neuron.getGradient() + " * " + con.getPrevious().getOutput() + " )");
                    con.setWeight(newWeight);
                    System.out.println( "[HIDDEN LAYER WEIGHT] " + con.getWeight());
                }
            }
        }

    }

    /**
     * calcul le gradient pour les hidden Layer
     * @param indexLayer
     * @param neuron
     * @return
     */
    private double hiddentLayerGradient( int indexLayer, Neuron neuron) {

        Layer layer = layers.get( indexLayer);
        double somme = 0;

        for( int i = 0 ; i < layer.getNeurons().size() ; i++ ) {
            Neuron n = layer.getNeurons().get(i);
            //ArrayList<Connection> connections = n.getWeight();

            for (Connection con : n.getWeight()) {

                if (con.getPrevious() == neuron) {
                    System.out.println("[HIDDEN LAYER SOMME ] " + con.getWeight() + " * " + n.getGradient());
                    somme += con.getWeight() * n.getGradient();
                    break;
                }
            }
        }
        return somme;
    }

    /**
     * Insert les donnée dans les neurones de l'INPUT LAYER
     * @param input données à inserer
     */
    public void setInput( double[] input) {

        for( int i = 0 ; i < layers.get(0).getNeurons().size() ; i++ ) {
            Neuron n = layers.get(0).getNeurons().get(i);
            n.setOutput( input[i]);
        }

    }

    /**
     * recupere les output de la ouput layer dans l'ordre de génération de la couche
     * @return valeur de sortie sur la couche de sortie de l'output layer
     */
    public double[] getOutput() {
        Layer layer = layers.get( layers.size() - 1);
        double[] resultat = new double[ layer.getNeurons().size()];

        for ( int i = 0 ; i < layer.getNeurons().size() ; i++ ) {
            resultat[i] = layer.getNeurons().get(i).getOutput();
            System.out.println( resultat[i]);
        }
        return resultat;
    }

    //TO DO
    /**
     * fonction d'apprentissage
     */
    public void train( double[][] entrees, double[][] sorties) {
        double error = 0d;
        int nbApp = 0;

        while (error <= condition){
            int i = rand.nextInt(entrees.length) % entrees.length;

            setInput(entrees[i]);

            feedForward();
            double[] outPut = getOutput();
            System.out.println( "result ->" + getOutput().length);

            error = 0d;

            for(int j = 1; j < sorties[i].length; ++j){
                double err = Math.pow(outPut[j]-sorties[i][j],2);
                System.out.println( err + " " + outPut[j] + " + " + sorties[i][j]);
                error += err;
            }

            minError = Math.min(minError,error);

            backpropagation(sorties[i]);
            nbApp +=1;
        }
    }

    //TO DO

    /**
     * fonction à utiliser apres apprentissage du reseau de neurone
     */
    public void executre(double[] input, double[] expected) {
        System.out.println("> Train ended");

        //on insert les valeurs d'entée
        this.setInput( input);

        //on calcul la sortie des output layer
        this.feedForward();

        //TO DO regarder si le resultat attendu correspond au resultat donnée par le reseau de neurone

        //on fait une retro-propagation pour lui apprendre la donnée
        this.backpropagation( expected);
    }


}
