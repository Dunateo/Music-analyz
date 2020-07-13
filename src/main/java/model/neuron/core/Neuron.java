package model.neuron.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Neuron implements Serializable {

    public static final double ETA = 0.1d;
    private static final long serialVersionUID = 2812396548489432808L;

    private ArrayList<Connection> weight = new ArrayList<>(); // poids
    private double output = 0.d; // result after transfer function
    private double gradient = 0.0d; //gradent
    private Neuron neuronBia; //neurone contenant le bia


    private double sigmoide( double value ) {
        return 1 / ( 1 + Math.exp( -value ));
    }

    public double activation( double value) {
        return this.sigmoide( value);
    }

    public void addInputConnection( ArrayList<Neuron> neurons) {

        for ( Neuron n: neurons ) {
            Connection connection = new Connection( n, this);
            weight.add( connection);
        }
    }

    public void addBiasConnection() {
        Neuron neuron = new Neuron();
        System.out.println( neuron);
        neuron.setOutput( -1d);
        Connection connection = new Connection( neuron, this);
        neuronBia = neuron;
        weight.add( connection);
        System.out.println("Bias -> " + neuron + " | " + weight.get(0).getPrevious());
    }

    public void computeOutput() {
        double output = 0;

        // ajout du bia
        output += weight.get(0).getWeight();
        System.out.println( "result bias -> " + weight.get(0).getWeight() );
        // somme des poids * l'entrée
        for( int i = 1 ; i < weight.size() ; i++ ) {
            System.out.println("result -> " + weight.get( i).getWeight() + " * " + weight.get( i).getPrevious().getOutput());
            output += weight.get( i).getWeight() * weight.get( i).getPrevious().getOutput();
        }
        System.out.println(" result before -> " + output);
        this.output = this.activation( output);

    }

}
