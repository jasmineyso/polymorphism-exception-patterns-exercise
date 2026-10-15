package model;

import strategy.*;
import exception.*;

public abstract class Parcel {
    private final String trackingNumber;
    private final String receiverName;
    private final double weight;
    private DeliveryStrategy deliveryService;
    private ParcelStatus status;

    protected Parcel(String trackingNumber, String receiverName, double weight)
            throws InvalidParcelException {
        if (trackingNumber == null || !trackingNumber.matches("ABC\\d{4}")) {
            throw new InvalidParcelException("Tracking number must be 'ABC' followed by 4 digits (e.g. ABC0001).");
        }
        if (receiverName == null || receiverName.trim().isEmpty()) {
            throw new InvalidParcelException("Receiver name cannot be empty.");
        }
        if (!(weight > 0)) {
            throw new InvalidParcelException("Weight must be greater than 0.");
        }
        if (weight > getMaxWeight()) {
            throw new InvalidParcelException("Weight cannot exceed " + getMaxWeight() + " kg for this parcel type.");
        }
        this.trackingNumber = trackingNumber;
        this.receiverName = receiverName.trim();
        this.weight = weight;
        this.deliveryService = new StandardDelivery();
        this.status = ParcelStatus.PENDING;
    }

    public abstract String getTypeName();
    public abstract double getMaxWeight();
    public abstract double getChargeableWeight();
    public abstract double getBaseFee();

    public double getDeliveryFee() {
        return deliveryService.calculateFee(getBaseFee());
    }

    public void setDeliveryService(DeliveryStrategy service) throws InvalidDeliveryServiceException {
        if (!service.isAllowed(getChargeableWeight())) {
            throw new InvalidDeliveryServiceException(service.getRestrictionMessage());
        }
        this.deliveryService = service;
    }

    public void markDelivered() { 
        this.status = ParcelStatus.DELIVERED; 
    }
    public boolean isDelivered() { 
        return status == ParcelStatus.DELIVERED; 
    }

    public String getTrackingNumber() { 
        return trackingNumber; 
    }
    public String getReceiverName() { 
        return receiverName; 
    }
    public double getWeight() { 
        return weight; 
    }
    public DeliveryStrategy getDeliveryService() { 
        return deliveryService; 
    }
    public ParcelStatus getStatus() { 
        return status; 
    }
}