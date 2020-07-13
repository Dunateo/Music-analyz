package model.neuron.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Layer implements Serializable {

    private static final long serialVersionUID = -1090918642988882351L;

    private ArrayList<Neuron> neurons = new ArrayList<>();

    public void addNeuron(Neuron neuron) {
        neurons.add( neuron);
    }
}