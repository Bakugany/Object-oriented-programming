package Orders;

import Investors.Investor;

public
class timeLimited extends Order{
    private int time;

    @Override
    public
    boolean isValid(int dayOfSimulation) {
        if(time + postedInDay >= dayOfSimulation){
            return true;
        }
        return false;
    }

    public
    timeLimited(TypeOfOrder type, String id, int numberOfVolumes, int priceLimit, Investor investor, int time) {
        super(type, id, numberOfVolumes, priceLimit, investor);
        this.time = time;
    }
}
