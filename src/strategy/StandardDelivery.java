package strategy;

public class StandardDelivery implements DeliveryStrategy {
    @Override 
    public String getName() { 
        return "Standard"; 
    }
    @Override 
    public double 
    calculateFee(double baseFee) { 
        return baseFee; 
    }
    @Override 
    public boolean isAllowed(double chargeableWeight) { 
        return true; 
    }
    @Override 
    public String getRestrictionMessage() { 
        return ""; 
    }
}