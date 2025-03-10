package Market;

import Investors.Investor;
import Orders.Order;

import java.util.*;

import static Orders.TypeOfOrder.disposal;
import static Orders.TypeOfOrder.purchase;

public
class Stock {
    private int actualDayOfSimulation = 0;
    private Map<String, Integer> companies = new HashMap<>();
    private int numberOfOrderThatDay;
    private List<Order> buyers = new LinkedList<>();
    private List<Order> sellers = new LinkedList<>();

    public int getLastPrice(String company) {
        return this.companies.get(company);
    }

    public boolean isSuchCompanyOnStock(String company){
        return companies.containsKey(company);
    }

    public void addCompanytoStock(String company, int lastPrice){
        companies.put(company, lastPrice);
    }

    public
    int getActualDayOfSimulation() {
        return actualDayOfSimulation;
    }


    public void nextDay(){
        actualDayOfSimulation ++;
        numberOfOrderThatDay = 0;
    }
    public void addOrder(Investor investor){
        Order order = investor.placeOrder(this);
        if(order.getType() == purchase  && investor.reduceCash(order.value())){
            order.setPostedInDay(actualDayOfSimulation);
            order.setNumberOfOrderThatDay(numberOfOrderThatDay);
            numberOfOrderThatDay ++;
            this.buyers.add(order);
        }
        if(order.getType() == disposal && investor.reduceShares(order.getId(), order.getNumberOfVolumes())){
            order.setPostedInDay(actualDayOfSimulation);
            order.setNumberOfOrderThatDay(numberOfOrderThatDay);
            numberOfOrderThatDay ++;
            this.sellers.add(order);
        }
    }


    public void makeTransactions(){
        buyers.sort(Order::compareToBuyers);
        int numberOfOrdersPurchase = buyers.size();

        // Now we look at each order.
        for(int i = 0; i < numberOfOrdersPurchase; i ++) {
            sellers.sort(Order::compareToSellers);
            Order firstBuyer = buyers.getFirst();
            buyers.removeFirst();
            // If order expired we just remove it.
            if (!firstBuyer.isValid(actualDayOfSimulation) || firstBuyer.getNumberOfVolumes() == 0) {
                firstBuyer.getInvestor().addCash(firstBuyer.value());
                continue;
            }

            int numberOfOrdersDisposal = sellers.size();
            for (int j = 0; j < numberOfOrdersDisposal; j++) {
                Order firstSeller = sellers.getFirst();
                sellers.removeFirst();
                // If order expired we just remove it.
                if (!firstSeller.isValid(actualDayOfSimulation) || firstSeller.getNumberOfVolumes() == 0) {
                    firstSeller.getInvestor().addShares(firstSeller.getId(), firstSeller.getNumberOfVolumes());
                    continue;
                }
                // If order seller and buyer cannot make a deal selling order comes back to list.
                if (!firstBuyer.getId().equals(firstSeller.getId()) || firstBuyer.getPriceLimit() < firstSeller.getPriceLimit() ||
                        firstBuyer.getInvestor() == firstSeller.getInvestor()) {
                    sellers.addLast(firstSeller);
                    continue;
                }
                // Otherwise they make a deal.
                if (firstBuyer.getNumberOfVolumes() <= firstSeller.getNumberOfVolumes()) {

                    // First we check what the price of the purchase will be.
                    // If buyer posted first his price will be taken.
                    if (firstBuyer.getPostedInDay() < firstSeller.getPostedInDay() ||
                            (firstBuyer.getPostedInDay() == firstSeller.getPostedInDay() &&
                                    firstBuyer.getNumberOfOrderThatDay() < firstSeller.getNumberOfOrderThatDay())) {
                        // We change value of last transaction of that company.
                        companies.put(firstBuyer.getId(), firstBuyer.getPriceLimit());
                        // We decrease number which seller have to offer.
                        firstSeller.decreaseNumberOfVolumes(firstBuyer.getNumberOfVolumes());
                        // We add cash to seller.
                        firstSeller.getInvestor().addCash(firstBuyer.value());
                        // We add shares to buyer.
                        firstBuyer.getInvestor().addShares(firstBuyer.getId(), firstBuyer.getNumberOfVolumes());
                        // We add sellers offer to list
                        sellers.addLast(firstSeller);
                        // This Order is finished so we decrease.
                        firstBuyer.decreaseNumberOfVolumes(firstBuyer.getNumberOfVolumes());
                        break;
                    }
                    // Otherwise sellers price will be taken.
                    else {
                        // We change value of last transaction of that company.
                        companies.put(firstSeller.getId(), firstSeller.getPriceLimit());
                        // We decrease number which seller have to offer.
                        firstSeller.decreaseNumberOfVolumes(firstBuyer.getNumberOfVolumes());
                        // We add cash to seller.
                        firstSeller.getInvestor().addCash(firstSeller.getPriceLimit() * firstBuyer.getNumberOfVolumes());
                        // Buyer bought for less than he prepaid so we give him change.
                        int change = firstBuyer.getPriceLimit() - firstSeller.getPriceLimit();
                        firstBuyer.getInvestor().addCash(firstBuyer.getNumberOfVolumes() *
                                change);
                        // We add shares to buyer.
                        firstBuyer.getInvestor().addShares(firstBuyer.getId(), firstBuyer.getNumberOfVolumes());
                        // We add sellers offer to list
                        sellers.addLast(firstSeller);
                        firstBuyer.decreaseNumberOfVolumes(firstBuyer.getNumberOfVolumes());
                        break;
                    }
                } // Else he wants to buy more than seller have.
                else {
                    // If buyer posted first his price will be taken.
                    if (firstBuyer.getPostedInDay() < firstSeller.getPostedInDay() ||
                            (firstBuyer.getPostedInDay() == firstSeller.getPostedInDay() &&
                                    firstBuyer.getNumberOfOrderThatDay() < firstSeller.getNumberOfOrderThatDay())) {
                        // We change value of last transaction of that company.
                        companies.put(firstBuyer.getId(), firstBuyer.getPriceLimit());
                        // We decrease number which buyer wants.
                        firstBuyer.decreaseNumberOfVolumes(firstSeller.getNumberOfVolumes());
                        // We add shares to buyer.
                        firstBuyer.getInvestor().addShares(firstBuyer.getId(), firstSeller.getNumberOfVolumes());
                        // We add cash to seller.
                        firstSeller.getInvestor().addCash(firstBuyer.getPriceLimit() * firstSeller.getNumberOfVolumes());
                    }
                    // Otherwise sellers price will be taken.
                    else {
                        // We change value of last transaction of that company.
                        companies.put(firstSeller.getId(), firstSeller.getPriceLimit());
                        // We decrease number which buyer wants.
                        firstBuyer.decreaseNumberOfVolumes(firstSeller.getNumberOfVolumes());
                        // We add cash to seller.
                        firstSeller.getInvestor().addCash(firstSeller.value());
                        // We add shares to buyer.
                        firstBuyer.getInvestor().addShares(firstBuyer.getId(), firstSeller.getNumberOfVolumes());
                        // We give change to the buyer.
                        int change = firstBuyer.getPriceLimit() - firstSeller.getPriceLimit();
                        firstBuyer.getInvestor().addCash(firstSeller.getNumberOfVolumes() * change);
                    }
                }

            }
            if (firstBuyer.getNumberOfVolumes() > 0) {
                buyers.addLast(firstBuyer);
            }
        }
    }

    public void cancelAll() {
        for (Order i : buyers) {
            i.getInvestor().addCash(i.value());
        }
        for (Order i : sellers) {
            i.getInvestor().addShares(i.getId(), i.getNumberOfVolumes());
        }
    }

}
