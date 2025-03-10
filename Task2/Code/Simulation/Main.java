package Simulation;

import java.io.File;
import java.security.InvalidParameterException;

public
class Main {
    public static
    void main(String[] args) {

        // We check if the number of arguments is valid, if not we throw exception.
        if(args.length != 2){
            throw new InvalidParameterException("Wrong number of arguments");
        }

        File data = new File(args[0]);
        int daysOfSimulation = Integer.valueOf(args[1]);

        Simulation simulation = new Simulation(daysOfSimulation, data);

        simulation.makeSimulation();
    }
}