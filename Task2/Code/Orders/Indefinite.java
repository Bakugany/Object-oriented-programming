package Orders;

import Investors.Investor;

public
class Indefinite extends Order {

    // This order is always valid.
    @Override
    public
    boolean isValid(int dayOfSimulation) {
        return true;
    }

    public
    Indefinite(TypeOfOrder type, String id, int numberOfVolumes, int priceLimit, Investor investor) {
        super(type, id, numberOfVolumes, priceLimit, investor);
    }

}
