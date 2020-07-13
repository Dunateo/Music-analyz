package spectrogram;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class wavfft {

    public static void main(String[] args) {

        AudioReader sampleReader = null;

        try {
            //Lis le fichier .wav
            sampleReader=new AudioReader(new File("src/tonalite.wav"));

            int rate= sampleReader.getSampleRate(); // échantillonage float to int
            System.out.println("Echantillonage = " + rate);

            double[] signal = sampleReader.readDoubleSamples(rate);
            System.out.println("Longueur du signal = " + signal.length);
            for (int i=0;i<signal.length;i++) {
                System.out.println(i+" : "+signal[i]);
            }

            //Fais la fft du signal
            Complex[] cinput = new Complex[signal.length];
            for (int i = 0; i < signal.length; i++){
                cinput[i] = new Complex(signal[i], 0.0);
            }

            FastFourierTransform fft= new FastFourierTransform();
            cinput=fft.fft(cinput);

			/*System.out.println("Results:");
			for (int i=0;i<cinput.length;i++) {
				System.out.println(i+" : "+cinput[i]);
			}*/

        } catch (UnsupportedAudioFileException e) {
            System.out.println("Erreur : UnsupportedAudioFileException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erreur : IOException");
            e.printStackTrace();
        }
    }
}
