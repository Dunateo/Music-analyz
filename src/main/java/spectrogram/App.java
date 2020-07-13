package spectrogram;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        String filepath = "src/tonalite.wav";
        try {

            //Récupération des donnees brute en double array du fichier .WAV
            readWAV2Array audioTest = new readWAV2Array(filepath, true);
            double[] rawData = audioTest.getByteArray();
            int length = rawData.length;

            //Initialise les parametres de la FFT
            int WS = 2048;
            int OF = 8;
            int windowStep = WS/OF;

            //Calcul les parametres de la FFT
            double SR = audioTest.getSR();// SR = sampleReader (48 000 par exemple)
            double time_resolution = WS/SR;
            double frequency_resolution = SR/WS;
            double highest_detectable_frequency = SR/2.0;
            double lowest_detectable_frequency = 5.0*SR/WS;

            System.out.println("Temps d'une période:		" + time_resolution*1000 + " ms");
            System.out.println("Fréquence du signal:		" + frequency_resolution + " Hz");
			/*System.out.println("Plus haute fréquence détectable: " + highest_detectable_frequency + " Hz");
			System.out.println("Plus basse fréquence détectable:  " + lowest_detectable_frequency + " Hz");*/

            //Initialisation de plotData array
            int nX = (length-WS)/windowStep;
            int nY = WS/2 + 1;//int nY = WS;
            double[][] plotData = new double[nX][nY];

            //Mis dans la FFT pour trouver les amplitudes MIN et MAX
            double maxAmp = Double.MIN_VALUE;
            double minAmp = Double.MAX_VALUE;

            double amp_square;

            double[] inputImag = new double[length];

            for (int i = 0; i < nX; i++){
                Arrays.fill(inputImag, 0.0);
                FFTBase FFT=new FFTBase();
                double[] WS_array = FFT.fft(Arrays.copyOfRange(rawData, i*windowStep, i*windowStep+WS), inputImag, true);
                for (int j = 0; j < nY; j++){
                    amp_square = (WS_array[2*j]*WS_array[2*j]) + (WS_array[2*j+1]*WS_array[2*j+1]);
                    if (amp_square == 0.0){
                        plotData[i][j] = amp_square;
                    }
                    else{
                        //ne pas avoir de freq negatif
                        double threshold = 1.0;
                        plotData[i][nY-j-1] = 10 * Math.log10(Math.max(amp_square,threshold));
                    }

                    //Trouve les amplitudes MIN et MAX
                    if (plotData[i][j] > maxAmp)
                        maxAmp = plotData[i][j];
                    else if (plotData[i][j] < minAmp)
                        minAmp = plotData[i][j];

                    //System.out.println(plotData[i][j]);

                }
            }

            System.out.println("---------------------------------------------------");
            System.out.println("Amplitude maximum: " + maxAmp);
            System.out.println("Amplitude minimum: " + minAmp);
            System.out.println("---------------------------------------------------");

            //Mise en forme
            double diff = maxAmp - minAmp;
            for (int i = 0; i < nX; i++){
                for (int j = 0; j < nY; j++){
                    plotData[i][j] = (plotData[i][j]-minAmp)/diff;
                }
            }

            //Image relative
            BufferedImage theImage = new BufferedImage(nX, nY, BufferedImage.TYPE_INT_RGB);
            double ratio;
            for(int x = 0; x<nX; x++){
                for(int y = 0; y<nY; y++){
                    ratio = plotData[x][y];

                    //theImage.setRGB(x, y, new Color(red, green, 0).getRGB());
                    Color newColor = getColor(1.0-ratio);
                    if( plotData[x][y] > 0.85d) {
                        System.out.println(newColor + " | " + x + " " + y + " | " + plotData[x][y]);
                        newColor = Color.BLACK;
                    }
                    theImage.setRGB(x, y, newColor.getRGB());
                }
            }
            File outputfile = new File("save.png");
            ImageIO.write(theImage, "png", outputfile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Color getColor(double power) {
        double H = power * 0.4; // Teinte (0.4 = vert)
        double S = 1.0; // Saturation
        double B = 1.0; // Luminosité

        return Color.getHSBColor((float)H, (float)S, (float)B);
    }
}
