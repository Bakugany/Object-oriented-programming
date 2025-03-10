package Investors;

import Orders.*;
import Market.Stock;

import java.util.*;

import static Orders.TypeOfOrder.disposal;
import static Orders.TypeOfOrder.purchase;

public
class SMA extends Investor {
    private static final int firstSMA = 5;
    private static final int secondSMA = 10;
    private Map<String, List<Integer>> SMA = new HashMap<String, List<Integer>>();
    // Unlike Random SMA every turn when stategising chooses company.
    private String companySMAIsInterestedIn;

    public
    SMA(int id) {
        super(id);
    }

    @Override
    public
    void strategy(Stock market) {
        for(Map.Entry<String, Integer> entry: shares.entrySet()) {
            String company = entry.getKey();
            int lastPrice = market.getLastPrice(company);

            // If it's first day we nedd to initialize SMA.
            if (market.getActualDayOfSimulation() == 1) {
                List<Integer> start = new LinkedList<>();
                start.addLast(lastPrice);
                SMA.put(company, start);
            }
            else {
                if (market.getActualDayOfSimulation() <= 11) {
                    List<Integer> sma = new LinkedList<>();
                    sma = SMA.get(company);
                    sma.addLast(lastPrice);
                    SMA.put(company, sma);
                }
                else {
                    List<Integer> sma = new LinkedList<>();
                    sma = SMA.get(company);
                    sma.removeFirst();
                    sma.addLast(lastPrice);
                    SMA.put(company, sma);
                }

            }
        }
        // Now he also chooses in which company he is interested;
        Random rand = new Random();
        Object [] values = shares.keySet().toArray();
        Object randomValue = values[rand.nextInt(values.length)];
        String chosenCompany = randomValue.toString();
        companySMAIsInterestedIn = chosenCompany;
    }

    // He check if he should buy shares of that company.
    private boolean shouldBuy(){
        List<Integer> sma = new LinkedList<>();
        sma = SMA.get(companySMAIsInterestedIn);
        int oldSumFirstSMA = 0;
        int oldSumSecondSMA = 0;
        for(int i = firstSMA; i < secondSMA; i ++){
            oldSumFirstSMA += sma.get(i);
        }
        for(int i = 0; i < secondSMA; i ++){
            oldSumSecondSMA += sma.get(i);
        }
        int newSumFirstSMA = 0;
        int newSumSecondSMA = 0;
        for(int i = firstSMA + 1; i < secondSMA + 1; i ++){
            oldSumFirstSMA += sma.get(i);
        }
        for(int i = 1; i < secondSMA + 1; i ++){
            oldSumSecondSMA += sma.get(i);
        }
        if(secondSMA/ firstSMA * oldSumFirstSMA <= oldSumSecondSMA && secondSMA/ firstSMA * newSumFirstSMA >= newSumSecondSMA){
            return true;
        }
        return false;
    }

    // He checks if he should sell volumins of that company.
    private boolean shouldSell(){
        List<Integer> sma = new LinkedList<>();
        sma = SMA.get(companySMAIsInterestedIn);
        int oldSumFirstSMA = 0;
        int oldSumSecondSMA = 0;
        for(int i = firstSMA; i < secondSMA; i ++){
            oldSumFirstSMA += sma.get(i);
        }
        for(int i = 0; i < secondSMA; i ++){
            oldSumSecondSMA += sma.get(i);
        }
        int newSumFirstSMA = 0;
        int newSumSecondSMA = 0;
        for(int i = firstSMA + 1; i < secondSMA + 1; i ++){
            oldSumFirstSMA += sma.get(i);
        }
        for(int i = 1; i < secondSMA + 1; i ++){
            oldSumSecondSMA += sma.get(i);
        }
        if(secondSMA/ firstSMA * oldSumFirstSMA >= oldSumSecondSMA && secondSMA/ firstSMA * newSumFirstSMA <= newSumSecondSMA){
            return true;
        }
        return false;
    }


    @Override
    public boolean wantsToAdd(Stock market) {
        if(market.getActualDayOfSimulation() < 11 || (! shouldBuy() && ! shouldSell()) ){
            return false;
        }
        return true;
    }

    @Override
    public
    Order placeOrder(Stock market) {
        Random rand = new Random();

        // Now we check if he wants to buy or sell.
        TypeOfOrder type;
        if(shouldBuy() ){
            type = purchase;
        }
        else{
            type = disposal;
        }

        // Now we generate price for which we want to buy/sell (it is in range +/- 10) from last share from this company.
        int price = Math.max(1, market.getLastPrice(companySMAIsInterestedIn) + rand.nextInt(-11, 10));

        // Now we generate amount of shares which we want to buy/sell.
        // I decided to generate from 1 to value which was at the
        // beginning in each investor wallet, cause
        // generating to MAX_INT for small data doesn't make sense.
        int chosenNumber = rand.nextInt(1, this.startingShares.get(companySMAIsInterestedIn));

        // Now we generate which type of order will it be.
        // 1- immediate, 2-indefinite, 3- time limited
        int whichOrder = rand.nextInt(3);


        switch (whichOrder) {
            case 1:
                return new Immediate(type, companySMAIsInterestedIn, chosenNumber, price, this);
            case 2:
                return new Indefinite(type, companySMAIsInterestedIn, chosenNumber, price, this);
            default:
                // I decided that timeLimited Order might be valid for 100 turns.
                int validity = rand.nextInt(1, 100);
                return new timeLimited(type, companySMAIsInterestedIn, chosenNumber, price, this, validity);
        }
    }
}
