package model.audio;

import lombok.Getter;
import lombok.Setter;
import model.math.FFTCplx;
import model.math.MathFFT;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Sound {

    private int frequency;
    private float[] data;

    public Sound(String fileName){
        try
        {
            // Ouvrir le fichier comme une source audio
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
            // Obtenir des informations sur cette source
            AudioFormat af = ais.getFormat();

            if (af.getChannels() == 1 &&	// Si le signal est monophonique
                    af.getEncoding() == AudioFormat.Encoding.PCM_SIGNED &&	// et qu'il est en Pulse Code Modulation signé
                    af.getSampleSizeInBits() == 16)	// et que les échantillons sont sur 16 bits
            {
                final int NombreDonnees = ais.available();	// Combien d'octets constituent les données
                final byte[] bufferOctets = new byte[NombreDonnees];	// Préparer un buffer pour lire tout le flux du fichier
                ais.read(bufferOctets);	// Lire le fichier dans le buffer d'octets
                ais.close();	// On peut fermer le flux du fichier
                ByteBuffer bb = ByteBuffer.wrap(bufferOctets);	// Prépare le travail sur le buffer
                bb.order(ByteOrder.LITTLE_ENDIAN);	// Indique le format des données lues dans le WAV
                ShortBuffer donneesAudio = bb.asShortBuffer();	// Préparer un buffer pour interpréter les données en tableau de short
                this.data = new float[donneesAudio.capacity()];
                for (int i = 0; i < this.data.length; ++i)
                    this.data[i] = (float)donneesAudio.get(i);
                // Récupérer la fréquence du fichier audio
                this.frequency = (int)af.getSampleRate();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private float[] getSample(int min, int max){

        int range = max-min;
        if(range%2 != 0){
            if (min > 0)
                min -= 1;
            else
                max -=1;
            range = max-min;
        }
        int log_Range = (int) Math.ceil(MathFFT.log2(range));
        int length = (int) Math.pow(2, log_Range);
        int k = (length - range)/2;

        float[] sample = new float[length];

        for (int i = min-k, j= 0; i < max+k ; i++, j++) {
            if (i >= min && i < max && i < this.data.length){
                sample[j] = this.data[i];
            }
        }
        return sample;
    }

    public List<NoteFrequency> getMusicalNote(){

        List<Integer> indexList = this.detectSample();

        List<float[]> arraySample = new ArrayList<>();
        for (int i = 0; i < indexList.size(); i++) {
            if(i+1 < indexList.size()){
                arraySample.add(this.getSample(indexList.get(i), indexList.get(i+1)));
            } else {
                arraySample.add(this.getSample(indexList.get(i), this.data.length));
            }
        }


        // FFT

        List<NoteFrequency> listResult = new ArrayList<>();

        for (int i = 0; i < arraySample.size(); i++) {
            NoteFrequency note = new NoteFrequency();
            note.setData(FFTCplx.mainFFT(arraySample.get(i)));
            note.setStart((double) indexList.get(i)/this.frequency);
            if(i+1 < indexList.size()) {
                note.setStop((double) indexList.get(i + 1)/this.frequency);
            } else {
                note.setStop((double) this.data.length/this.frequency);
            }
            note.computeFrequency(this.getFrequency(), indexList.get(i));
            listResult.add(note);
        }

        return listResult;

    }

    private List<Integer> detectSample(){

        //////////////////////////// Calcul de l'écart type ////////////////////////////

        // Moyenne
        double avg = 0, ecart_type = 0;
        for (int i = 0; i < this.data.length; i++) {
            avg += Math.abs(this.data[i]);
        }
        avg = avg/this.data.length;
        System.out.println("avg : "+avg);

        // Ecart type
        double min = 0, max = 0;
        for (int i = 0; i < this.data.length; i++) {
            /*
            if (Math.abs(this.data[i]) > max)
                max = Math.abs(this.data[i]);
            else if(min > Math.abs(this.data[i]))
                min = Math.abs(this.data[i]);
            */
            ecart_type += Math.pow((Math.abs(this.data[i]) - avg),2);
        }
        ecart_type = Math.sqrt(ecart_type/this.data.length);
        //System.out.println("min : "+ min);
        //System.out.println("max : "+ max);
        System.out.println("ecart type : "+ecart_type);

        //////////////////////////// Détection de zone à traiter ////////////////////////////
        int seuil = (int) Math.ceil((double) this.data.length*0.008);
        List<Integer> indexList = new ArrayList<Integer>();
        double avg1 = 0;
        for (int i = 0; i < this.data.length-seuil; i+=seuil) {
            double avg2 = 0;
            for (int j = i; j < i+seuil; j++) {
                avg2 += Math.abs(this.data[j]);
            }
            avg2 = avg2/seuil;
            if(Math.abs(avg2-avg1) > ecart_type){
                avg1 = avg2;
                indexList.add(i);
                System.out.println(i);
            }
        }

        return indexList;
    }


}
