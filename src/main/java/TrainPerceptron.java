import model.MusicalNote;
import model.file.PerceptronnetworkFile;
import model.neuron.NeuralNetwork;
import model.perceptron.Perceptron;
import model.perceptron.PerceptronNetwork;

import java.util.ArrayList;

public class TrainPerceptron {

    public static void main(String[] args) {

        Perceptron laDieseOctaveQuatre = new Perceptron( 1, MusicalNote.LA_DIESE_OCTAVE_4);
        Perceptron doOvtaveCinq = new Perceptron( 1, MusicalNote.DO_OCTAVE_5);
        Perceptron solDieseOctaveQuatre = new Perceptron(1, MusicalNote.SOL_OCTAVE_4);
        Perceptron reDieseOctaveCinq = new Perceptron(1, MusicalNote.RE_DIESE_OCTAVE_5);
        Perceptron reDieseOctaveQuatre = new Perceptron(1, MusicalNote.RE_DIESE_OCTAVE_4);

        double[][] entreeLaDise = { {926.9}, {927.00001}, {932.98} , {936.9}};
        double[] sortieLaDiese = { 0, 1, 1, 1};

        laDieseOctaveQuatre.apprentissage( entreeLaDise, sortieLaDiese);

        double[][] entreeDoCinq = { {1044.95}, {1044.95}, {1045.00001}, {1055.0}};
        double[] sortieDoCinq = { 0, 0, 1, 1};

        doOvtaveCinq.apprentissage( entreeDoCinq, sortieDoCinq);

        double[][] entreeSolDieseQuatre = { {820.95}, {824.95}, {825.00001}, {835.0}};
        double[] sortieSolDieseQuatre = { 0, 0, 1, 1};

        solDieseOctaveQuatre.apprentissage( entreeSolDieseQuatre, sortieSolDieseQuatre);

        double[][] entreeReDisesCinq = { {1225.95}, {1234.95}, {1235.00001}, {1245.0}};
        double[] sortiereDisesCinq = { 0, 0, 1, 1};

        reDieseOctaveCinq.apprentissage( entreeReDisesCinq, sortiereDisesCinq);

        double[][] entreeReDisesQuatre = { {615.95}, {617.95}, {618.00001}, {627.0}};
        double[] sortiereDisesQuatre = { 0, 0, 1, 1};

        reDieseOctaveQuatre.apprentissage( entreeReDisesQuatre, sortiereDisesQuatre);

        PerceptronNetwork perceptronNetwork = new PerceptronNetwork();

        ArrayList<Perceptron> perceptrons = new ArrayList<>();

        perceptrons.add( reDieseOctaveCinq);
        perceptrons.add( doOvtaveCinq);
        perceptrons.add( laDieseOctaveQuatre);
        perceptrons.add( solDieseOctaveQuatre);
        perceptrons.add( reDieseOctaveQuatre);

        perceptronNetwork.setPerceptrons( perceptrons);

        PerceptronnetworkFile.savePerceptronNetwork( perceptronNetwork, "src/main/resources/perceptron/save.ser");
    }
}
