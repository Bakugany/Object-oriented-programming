package Orders;

import Investors.Investor;

public class ExecuteOrCancel extends Order {
    // TODO: Implement this type of order.

    @Override
    public
    boolean isValid(int dayOfSimulation) {
        if(dayOfSimulation == postedInDay){
            return true;
        }
        return false;
    }

    public
    ExecuteOrCancel(TypeOfOrder type, String id, int numberOfVolumes, int priceLimit, Investor investor) {
        super(type, id, numberOfVolumes, priceLimit, investor);
    }
}
