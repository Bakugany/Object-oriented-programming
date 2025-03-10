package Investors;

import Orders.Order;
import Market.Stock;

import java.util.HashMap;
import java.util.Map;

public abstract
class Investor {
    // I gave my investors id's for debugging but I think it's great feature.
    protected int id;
    protected int cash;
    protected Map<String, Integer> shares = new HashMap<>();
    protected Map<String, Integer> startingShares = new HashMap<>();




    public
    Investor(int id) {
        this.id = id;
    }

    public abstract Order placeOrder(Stock market);

    // Investors might have their strategy to play.
    public abstract void strategy(Stock market);

    // Some investors may be ready to play only after certain situations.
    public abstract boolean wantsToAdd(Stock market);

    public void printWallet(){
            System.out.print(id +" " + cash + " ");
            for (Map.Entry<String, Integer> mapElement: shares.entrySet()){
                String key = mapElement.getKey();
                Integer value = mapElement.getValue();
                System.out.print(key+ ":"+ value+ " ");
            }
            System.out.println();
    }

    public boolean reduceCash(int howMany){
        if(cash >= howMany){
            cash -= howMany;
            return true;
        }
        return false;
    }

    public void addCash(int howMany){
        cash += howMany;
    }

    public void addShares(String company, int howMany){
        if(shares.containsKey(company)) {
            int before = shares.get(company);
            int after = before + howMany;
            shares.remove(company);
            shares.put(company, after);
            return;
        }
        shares.put(company, howMany);
    }

    public void addStartingShares(String company, int howMany){
        startingShares.put(company, howMany);
    }

    public boolean reduceShares(String company, int howMany){
        int before = shares.get(company);
        if(before >= howMany){
            int after = before - howMany;
            shares.remove(company);
            shares.put(company, after);
            return true;
        }
        return false;
    }

    public
    int getId() {
        return id;
    }

    public int compareTo(Investor otherInvestor){
        return Integer.compare(this.id, otherInvestor.getId());
    }
}
