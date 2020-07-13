import model.audio.NoteFrequency;
import model.audio.Sound;
import model.file.PerceptronnetworkFile;
import model.perceptron.PerceptronNetwork;


public class Main {

    public static void main(String[] args) {

        // on lit le fichier audio
        // on analyse le signal
        Sound sound = new Sound("src/main/resources/audio/sample.wav");
        System.out.println(sound.getFrequency());

        PerceptronNetwork perceptronNetwork = PerceptronnetworkFile.loadPerceptronNetwork("src/main/resources/perceptron/save.ser");

        for (NoteFrequency item : sound.getMusicalNote()) {

            System.out.println("frequency :"+item.getValue());
            System.out.println("time :"+item.getTime());

            perceptronNetwork.analyseFrequency( item.getValue());
        }
    }
}
