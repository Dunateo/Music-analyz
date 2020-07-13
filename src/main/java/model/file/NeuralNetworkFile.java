package model.file;

import lombok.NoArgsConstructor;
import model.neuron.NeuralNetwork;

import java.io.*;

@NoArgsConstructor
public class NeuralNetworkFile {

    public static void saveNeuralNetwork( NeuralNetwork neuralNetwork, String fileName) {

        try {
            FileOutputStream fileOutputStream = new FileOutputStream( fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream( fileOutputStream);

            objectOutputStream.writeObject( neuralNetwork);
            objectOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static NeuralNetwork loadNeuralNetwork( String fileName) {

        NeuralNetwork neuralNetwork = null;
        try {

            FileInputStream fileInputStream = new FileInputStream( fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream( fileInputStream);

            neuralNetwork = (NeuralNetwork) objectInputStream.readObject();
            objectInputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return neuralNetwork;
    }
}
