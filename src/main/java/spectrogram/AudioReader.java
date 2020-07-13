package spectrogram;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class AudioReader {
    private AudioInputStream signedBigEndianInputStream = null;
    private AudioInputStream originalAudioInputStream = null;

    public AudioReader(File inputfile) throws UnsupportedAudioFileException, IOException {
        originalAudioInputStream = AudioSystem.getAudioInputStream(inputfile);
        AudioFormat destaf = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                originalAudioInputStream.getFormat().getSampleRate(),
                16, originalAudioInputStream.getFormat().getChannels(),
                16 / 8 * originalAudioInputStream.getFormat().getChannels(),
                originalAudioInputStream.getFormat().getSampleRate(), true);
        signedBigEndianInputStream = AudioSystem.getAudioInputStream(destaf, originalAudioInputStream);
    }

    /**
     * Lis n échantillons du fichier MONOPHONIQUE (MONO).
     * Retourne double [].
     */
    public double[] readDoubleSamples(int n) throws IOException {
        int channels = signedBigEndianInputStream.getFormat().getChannels();
        int ss = signedBigEndianInputStream.getFormat().getSampleSizeInBits();
        int ssB = ss / 8; // in bytes
        //		System.out.println(String.format("fl: %d, ss: %d, ssB: %d, samplerate: %f",
        //						fl, ss, ssB, signedBigEndianInputStream.getFormat().getSampleRate()));
        byte[] b = new byte[n * channels * ssB];
        int read = signedBigEndianInputStream.read(b);
        double[] res = new double[read / ssB / channels];
        for (int i = 0; i < res.length; i++)
            res[i] = 0;

        for (int i = 0; i < res.length * channels; i++) {
            double val = 0;
            switch (ss) {
                case 8:
                    val = ((b[i * ssB] & 0xFF) - 128) / 128.0;
                    break;
                case 16:
                    val = ((b[i * ssB + 0] << 8) | (b[i * ssB + 1] & 0xFF)) / 32768.0;
                    break;
                case 24:
                    val = ((b[i * ssB + 0] << 16) | ((b[i * ssB + 1] & 0xFF) << 8)
                            | (b[i * ssB + 2] & 0xFF)) / 8388606.0;
                    break;
                case 32:
                    val = ((b[i * ssB + 0] << 24) | ((b[i * ssB + 1] & 0xFF) << 16)
                            | ((b[i * ssB + 2] & 0xFF) << 8) | (b[i * ssB + 3] & 0xFF)) / 2147483648.0;
                    break;
            }
            res[i/channels] += val / channels;
        }
        return res;
    }

    public int getSampleRate() {
        float a = signedBigEndianInputStream.getFormat().getSampleRate();
        int rate= Math.round(a);
        return rate;

    }
}
