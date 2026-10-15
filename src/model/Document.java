package model;

import exception.InvalidParcelException;

public class Document extends Parcel {
    private final int pages;

    public Document(String trackingNumber, String receiverName, double weight, int pages)
            throws InvalidParcelException {
        super(trackingNumber, receiverName, weight);
        if (pages < 1 || pages > 100) {
            throw new InvalidParcelException("Number of pages must be between 1 and 100.");
        }
        this.pages = pages;
    }

    @Override 
    public String getTypeName() { 
        return "Document"; 
    }
    @Override 
    public double getMaxWeight() { 
        return 0.5; 
    }
    @Override 
    public double getChargeableWeight() { 
        return getWeight(); 
    }
    @Override 
    public double getBaseFee() { 
        return 50; 
    }

    public int getPages() { 
        return pages; 
    }
}