package strategy;

public interface DeliveryStrategy {
    String getName();
    double calculateFee(double baseFee);
    boolean isAllowed(double chargeableWeight);
    String getRestrictionMessage();
}