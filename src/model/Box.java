package model;

import exception.InvalidParcelException;

public class Box extends Parcel {
    private final int length;
    private final int width;
    private final int height;

    public Box(String trackingNumber, String receiverName, double weight,
               int length, int width, int height) throws InvalidParcelException {
        super(trackingNumber, receiverName, weight);
        if (length <= 0 || width <= 0 || height <= 0) {
            throw new InvalidParcelException("Length, width, and height must be greater than 0.");
        }
        this.length = length;
        this.width = width;
        this.height = height;
    }

    @Override 
    public String getTypeName() { 
        return "Box"; 
    }
    @Override 
    public double getMaxWeight() { 
        return 50; 
    }

    public double getVolumetricWeight() {
        return ((double) length * width * height) / 3500.0;
    }

    @Override
    public double getChargeableWeight() {
        return Math.ceil(Math.max(getWeight(), getVolumetricWeight()));
    }

    @Override 
    public double getBaseFee() { 
        return 40 * getChargeableWeight(); 
    }

    public int getLength() { 
        return length; 
    }
    public int getWidth() { 
        return width; 
    }
    public int getHeight() { 
        return height; 
    }
}