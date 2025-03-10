package Tests;

import Simulation.Simulation;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.security.InvalidParameterException;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoadingTest {

    // This tests checks if I catch invalid input.
    @Test
    public  void testInvestor() {
        int daysOfSimulation = 100;
        File data = new File("src/Tests/WrongNameOfInvestors.txt");
        Simulation simulation = new Simulation(daysOfSimulation, data);
        assertThrows(InvalidParameterException.class, () -> simulation.makeSimulation());
    }
    @Test
    public  void testTooMuchLines() {
        int daysOfSimulation = 100;
        File data = new File("src/Tests/TooMuchLines.txt");
        Simulation simulation = new Simulation(daysOfSimulation, data);
        assertThrows(InvalidParameterException.class, () -> simulation.makeSimulation());
    }

    @Test
    public void testTooLongNameOfCompany(){
        int daysOfSimulation = 100;
        File data = new File("src/Tests/TooLongNameForCompany.txt");
        Simulation simulation = new Simulation(daysOfSimulation, data);
        assertThrows(InvalidParameterException.class, () -> simulation.makeSimulation());
    }

    @Test
    public void testNoSuchCompany(){
        int daysOfSimulation = 100;
        File data = new File("src/Tests/NoSuchCompany.txt");
        Simulation simulation = new Simulation(daysOfSimulation, data);
        assertThrows(InvalidParameterException.class, () -> simulation.makeSimulation());
    }

}