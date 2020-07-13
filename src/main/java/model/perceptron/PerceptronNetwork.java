package model.perceptron;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
public class PerceptronNetwork implements Serializable {

    private static final long serialVersionUID = -3218257472479217100L;

    private ArrayList<Perceptron> perceptrons;

    public void analyseFrequency( double frequency) {

        double[] tempory = new double[1];
        tempory[0] = frequency;
        boolean findNote = false;

        for ( Perceptron p : perceptrons ) {
            p.metAJour( tempory);
            if( Double.compare( p.getSortie(), 1.d) == 0 ) {
                System.out.println( "frequency -> " + frequency + " musical note -> " + p.getNote().getMusicalNote());
                findNote = true;
                break;
            }
        }

        if( !findNote)
            System.out.println( "frequency -> " + frequency + " is not a musical note");
    }


}
