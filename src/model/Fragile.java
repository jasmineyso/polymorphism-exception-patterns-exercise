package model;

import exception.InvalidParcelException;

public class Fragile extends Box {
    private final String handlingNote;

    public Fragile(String trackingNumber, String receiverName, double weight,
                   int length, int width, int height, String handlingNote)
            throws InvalidParcelException {
        super(trackingNumber, receiverName, weight, length, width, height);
        if (handlingNote == null || handlingNote.trim().isEmpty()) {
            throw new InvalidParcelException("Handling note cannot be empty.");
        }
        this.handlingNote = handlingNote.trim();
    }

    @Override 
    public String getTypeName() { 
        return "Fragile"; 
    }
    @Override 
    public double getMaxWeight() { 
        return 30; 
    }
    @Override 
    public double getBaseFee() { 
        return super.getBaseFee() + 100; 
    }

    public String getHandlingNote() { 
        return handlingNote; 
    }
}