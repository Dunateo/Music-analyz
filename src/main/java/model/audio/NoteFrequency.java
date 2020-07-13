package model.audio;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.math.complexe.Complexe;

@Getter
@Setter
@NoArgsConstructor
public class NoteFrequency {

    private double start;
    private double stop;
    private double value;
    private double time;
    private Complexe[] data;

    public void computeFrequency(int frequency, int statSample){
        double maxVal = 0;
        int index = 0;
        for(int i = 0; i < this.data.length/2; i++) {
            if(this.data[i].mod() > maxVal){
                index = i;
                maxVal = this.data[i].mod();
            }
        }
        this.setValue((double) frequency*index/this.data.length);
        this.setTime((double) (index+statSample)/frequency);
    }

}
