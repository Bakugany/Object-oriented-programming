package Investors;
import Orders.*;
import Market.Stock;


import java.util.Random;

import static Orders.TypeOfOrder.disposal;
import static Orders.TypeOfOrder.purchase;


public
class RandomInvestor extends Investor {

    // In my simulation random investors have 20% not to make an order to make it more interesting.
    @Override
    public
    boolean wantsToAdd(Stock market) {
        Random rand = new Random();
        int result = rand.nextInt(0, 100);
        if (result >= 80) {
            return false;
        }
        return true;
    }

    public
    RandomInvestor(int id) {
        super(id);
    }

    // Random investor doesn't have a strategy, so we just leave empty function.
    // (I hope it's correct, cause strategy might be important for new investors which might be added in futuere).
    @Override
    public
    void strategy(Stock market) {

    }

    @Override
    public
    Order placeOrder(Stock market) {
        Random rand = new Random();

        // First we generate if it's purchase or disposal.
        // 1 will be purchase and 2 will be disposal.
        int number = rand.nextInt(2);
        TypeOfOrder type;
        if (number == 1) {
            type = purchase;
        }
        else {
            type = disposal;
        }

        // Now we generate share of which company we want to buy/sell.
        Object[] values = shares.keySet().toArray();
        Object randomValue = values[rand.nextInt(values.length)];
        String chosenCompany = randomValue.toString();

        // Now we generate amount of shares which we want to buy/sell.
        // I decided to generate from 1 to value which was at the
        // beginning in each investor wallet, cause
        // generating to MAX_INT for small data doesn't make sense.
        int chosenNumber = rand.nextInt(1, this.startingShares.get(chosenCompany));

        // Now we generate price for which we want to buy/sell (it is in range +/- 10) from last share from this company.
        int price = Math.max(1, market.getLastPrice(chosenCompany) + rand.nextInt(-11, 10));

        // Now we generate which type of order will it be.
        // 1- immediate, 2-indefinite, 3- time limited,
        int whichOrder = rand.nextInt(3);


        switch (whichOrder) {
            case 1:
                return new Immediate(type, chosenCompany, chosenNumber, price, this);
            case 2:
                return new Indefinite(type, chosenCompany, chosenNumber, price, this);
            default:
                // I decided that timeLimited Order might be valid for 100 turns.
                int validity = rand.nextInt(1, 100);
                return new timeLimited(type, chosenCompany, chosenNumber, price, this, validity);
        }

    }
}
