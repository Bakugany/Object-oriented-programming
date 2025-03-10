package Orders;

import Investors.Investor;

public abstract
class Order {
    protected TypeOfOrder type;
    protected String id;
    protected int numberOfVolumes;
    protected int priceLimit;
    protected Investor investor;
    protected int postedInDay;
    protected int numberOfOrderThatDay;

    public abstract boolean isValid(int dayOfSimulation);

    public void decreaseNumberOfVolumes(int n ){
        numberOfVolumes -= n;
    }

    public
    Order(TypeOfOrder type, String id, int numberOfVolumes, int priceLimit, Investor investor) {
        this.type = type;
        this.id = id;
        this.numberOfVolumes = numberOfVolumes;
        this.priceLimit = priceLimit;
        this.investor = investor;
    }

    public
    TypeOfOrder getType() {
        return type;
    }
    public int value(){
        return numberOfVolumes * priceLimit;
    }

    public
    String getId() {
        return id;
    }


    public
    void setPostedInDay(int postedInDay) {
        this.postedInDay = postedInDay;
    }

    public
    void setNumberOfOrderThatDay(int numberOfOrderThatDay) {
        this.numberOfOrderThatDay = numberOfOrderThatDay;
    }

    public
    Investor getInvestor() {
        return investor;
    }

    public
    int getNumberOfVolumes() {
        return numberOfVolumes;
    }

    public
    int getPriceLimit() {
        return priceLimit;
    }

    public
    int getPostedInDay() {
        return postedInDay;
    }

    public
    int getNumberOfOrderThatDay() {
        return numberOfOrderThatDay;
    }

    public int compareToSellers(Order otherOrder){
        // First we sort orders by company.
        int result = this.id.compareTo(otherOrder.getId());
        if(result != 0){
            return result;
        }

        // Now we sort orders by increasing price.
        result = Integer.compare(this.priceLimit, otherOrder.getPriceLimit());
        if(result != 0) {
            return result;
        }

        // Now we sort by day in which it was posted.
        result = Integer.compare(this.postedInDay, otherOrder.getPostedInDay());
        if(result != 0) {
            return result;
        }

        // And finally if all of this wasn't conclusive.
        return Integer.compare(this.numberOfOrderThatDay, otherOrder.getNumberOfOrderThatDay());
    }
    public int compareToBuyers(Order otherOrder){
        // First we sort orders by company.
        int result = this.id.compareTo(otherOrder.getId());
        if(result != 0){
            return result;
        }

        // Now we sort orders by decreasing price.
        result = - Integer.compare(this.priceLimit, otherOrder.getPriceLimit());
        if(result != 0) {
            return result;
        }

        // Now we sort by day in which it was posted.
        result = Integer.compare(this.postedInDay, otherOrder.getPostedInDay());
        if(result != 0) {
            return result;
        }

        // And finally if all of this wasn't conclusive.
        return Integer.compare(this.numberOfOrderThatDay, otherOrder.getNumberOfOrderThatDay());
    }


}
