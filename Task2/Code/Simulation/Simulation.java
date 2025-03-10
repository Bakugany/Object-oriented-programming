package Simulation;

import Investors.Investor;
import Investors.RandomInvestor;
import Investors.SMA;
import Market.Stock;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.*;

public
class Simulation {
    private int daysOfSimulation;
    private File data;
    private List<Investor> investors = new ArrayList<>();;
    private Stock stock = new Stock();

    public Simulation(int dayOfSimulation, File data) {
        this.daysOfSimulation = dayOfSimulation;
        this.data = data;
    }

    public void makeSimulation(){
        loadData();
        System.out.println("Wallets at the beginning:");
        printWallets();
        runSimulation();
        closingTheStock();
        System.out.println("Wallets at the end:");
        printWallets();
    }

    // We load data from to file to simulation class.
    private void loadData(){
        try {
            Scanner sc = new Scanner(this.data);
            int whichLine = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (line.charAt(0) == '#') {
                    continue;
                }
                whichLine++;

                // We get first line with investors.
                if(whichLine == 1){
                    String[] parse = line.split(" ");
                    for(int i = 0; i < parse.length; i ++){
                        switch(parse[i]){
                            case "R":
                                investors.add(new RandomInvestor(i));
                                break;
                            case "S":
                                investors.add(new SMA(i));
                                break;
                            default:
                                throw new InvalidParameterException("Invalid investor type.");
                        }
                    }
                }

                // We get second line with list of companies and price of last transcation.
                if(whichLine == 2){
                    String[] parse = line.split(" ");
                    for(int i = 0; i < parse.length; i ++){
                        try {
                            String[] secondParse = parse[i].split(":");
                            if(secondParse.length != 2){
                                throw new InvalidParameterException("Invalid parameters for company.");
                            }
                            if (secondParse[0].length() > 5) {
                                throw new InvalidParameterException("Too long name for company.");
                            }
                            stock.addCompanytoStock(secondParse[0], Integer.valueOf(secondParse[1]));
                        } catch (NumberFormatException e){
                            System.out.println("Price is not an integer.");
                            throw e;
                        }
                    }
                }

                // We get third line with starting wallet.
                if(whichLine == 3){
                    try{
                        String[] parse = line.split(" ");
                        int cash = Integer.valueOf(parse[0]);
                        for(Investor j : investors){
                            j.addCash(cash);
                        }
                        for(int i = 1; i < parse.length; i++){
                            String[] secondParse = parse[i].split(":");
                            if(secondParse.length != 2){
                                throw new InvalidParameterException("Invalid parameters for investors wallets.");
                            }
                            if(! stock.isSuchCompanyOnStock(secondParse[0])){
                                throw new InvalidParameterException("Investors wants shares that are not available.");
                            }
                            else{
                                for(Investor j : investors){
                                    j.addStartingShares(secondParse[0], Integer.valueOf(secondParse[1]));
                                    j.addShares(secondParse[0], Integer.valueOf(secondParse[1]));
                                }
                            }
                        }
                    } catch (NumberFormatException e){
                        System.out.println("Price is not an integer.");
                        throw e;
                    }
                }
                if(whichLine > 3){
                    throw new InvalidParameterException("Too much lines with data.");
                }
            }
            if(whichLine != 3){
                throw  new InvalidParameterException("Too little lines with data.");
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File hasn't been found.");
            e.printStackTrace();
        }
    }

    private void runSimulation(){
        for(int i = 0; i < daysOfSimulation; i ++){
            // We inform stock that it's next day of simulation.
            stock.nextDay();

            // We let investors make their strategy if they have one.
            for(Investor j: investors){
                j.strategy(stock);
            }

            // We need to ask investors in random order, so we shuffle list and add their orders.
            Collections.shuffle(investors);
            for(Investor j: investors){
                if(j.wantsToAdd(stock)) {
                    stock.addOrder(j);
                }
            }
            stock.makeTransactions();
        }
    }

    // After the simulation days ended we cancel all orders which are on stock.
    private void closingTheStock(){
        stock.cancelAll();
    }

    // We print Wallets of all investors.
    private void printWallets(){
        investors.sort(Investor::compareTo);
        for(Investor i: investors){
            i.printWallet();
        }
    }
}



