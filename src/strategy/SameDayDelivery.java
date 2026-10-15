package strategy;

public class SameDayDelivery implements DeliveryStrategy {
    private static final double MAX_CHARGEABLE_WEIGHT = 10;

    @Override 
    public String getName() { 
        return "Same Day"; 
    }
    @Override 
    public double calculateFee(double baseFee) { 
        return (baseFee * 2) + 100; 
    }
    @Override 
    public boolean isAllowed(double chargeableWeight) {
        return chargeableWeight <= MAX_CHARGEABLE_WEIGHT;
    }
    @Override public String getRestrictionMessage() {
        return "Same Day delivery is only for parcels up to 10 kg!";
    }
}