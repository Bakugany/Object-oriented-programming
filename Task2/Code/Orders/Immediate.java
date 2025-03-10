package Orders;

import Investors.Investor;

public
class Immediate extends Order{

    @Override
    public
    boolean isValid(int dayOfSimulation) {
        if(dayOfSimulation == postedInDay){
            return true;
        }
        return false;
    }

    public
    Immediate(TypeOfOrder type, String id, int numberOfVolumes, int priceLimit, Investor investor) {
        super(type, id, numberOfVolumes, priceLimit, investor);
    }
}
