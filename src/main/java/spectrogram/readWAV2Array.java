package spectrogram;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class readWAV2Array {

    private byte[] entireFileData;

    //Récupération d'Echantillonage
    public double getSR(){
        ByteBuffer wrapped = ByteBuffer.wrap(Arrays.copyOfRange(entireFileData, 24, 28)); // big-endian by default
        double SR = wrapped.order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
        return SR;
    }

    //Lecture du fichier .WAV
    public readWAV2Array(String filepath, boolean print_info) throws IOException {
        Path path = Paths.get(filepath);
        this.entireFileData = Files.readAllBytes(path);

        if (print_info){

            //Lecture du format
            String format = new String(Arrays.copyOfRange(entireFileData, 8, 12), "UTF-8");

            //Lecture du nombre de chaines audio
            int noOfChannels = entireFileData[22];
            String noOfChannels_str;
            if (noOfChannels == 2)
                noOfChannels_str = "2 (stereo)";
            else if (noOfChannels == 1)
                noOfChannels_str = "1 (mono)";
            else
                noOfChannels_str = noOfChannels + "(Plus de 2 canaux)";

            //Voir fonction ci-dessus
            int SR = (int) this.getSR();

            //Récupération du nb de Bits Per Second
            int BPS = entireFileData[34];

            System.out.println("---------------------------------------------------");
            System.out.println("Chemin du fichier:			" + filepath);
            System.out.println("Format du fichier:			" + format);
            System.out.println("Nombre de canaux: 			" + noOfChannels_str);
            System.out.println("Taux d'echantillonnage:		" + SR);
            System.out.println("Bits par sec:				" + BPS);
            System.out.println("---------------------------------------------------");

        }
    }

    //Récupération des données
    public double[] getByteArray (){
        byte[] data_raw = Arrays.copyOfRange(entireFileData, 44, entireFileData.length);
        int totalLength = data_raw.length;

        //Declaration de double array pour MONO
        int new_length = totalLength/4;
        double[] data_mono = new double[new_length];

        double left, right;
        for (int i = 0; 4*i+3 < totalLength; i++){
            left = (short)((data_raw[4*i+1] & 0xff) << 8) | (data_raw[4*i] & 0xff);
            right = (short)((data_raw[4*i+3] & 0xff) << 8) | (data_raw[4*i+2] & 0xff);
            data_mono[i] = (left+right)/2.0;
        }
        return data_mono;
    }
}
