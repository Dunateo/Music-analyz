package model.neuron.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Connection implements Serializable {

    private static final double ETA = 0.1d;

    private static final long serialVersionUID = 5305225763401060771L;

    private double weight;
    private Neuron previous;
    private Neuron following;

    public Connection( Neuron previous, Neuron following) {
        this.weight = (Math.random()*2.-1.);
        this.previous = previous;
        this.following = following;

        System.out.println( weight + " | " + this.previous + " | " + this.following);
    }


}

