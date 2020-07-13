package model.file;

import lombok.NoArgsConstructor;
import model.perceptron.PerceptronNetwork;

import java.io.*;

@NoArgsConstructor
public class PerceptronnetworkFile {

    public static void savePerceptronNetwork(PerceptronNetwork perceptronNetwork, String fileName) {

        try {
            FileOutputStream fileOutputStream = new FileOutputStream( fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream( fileOutputStream);

            objectOutputStream.writeObject( perceptronNetwork);
            objectOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PerceptronNetwork loadPerceptronNetwork( String fileName) {

        PerceptronNetwork perceptronNetwork = null;
        try {

            FileInputStream fileInputStream = new FileInputStream( fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream( fileInputStream);

            perceptronNetwork = (PerceptronNetwork) objectInputStream.readObject();
            objectInputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return perceptronNetwork;
    }
}
