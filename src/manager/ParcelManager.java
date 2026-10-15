package manager;

import java.util.ArrayList;
import java.util.List;
import model.Parcel;
import strategy.DeliveryStrategy;
import exception.*;

public class ParcelManager {
    private static ParcelManager instance;
    private final List<Parcel> parcels = new ArrayList<>();

    private ParcelManager() { }

    public static ParcelManager getInstance() {
        if (instance == null) {
            instance = new ParcelManager();
        }
        return instance;
    }

    public boolean exists(String trackingNumber) {
        for (Parcel p : parcels) {
            if (p.getTrackingNumber().equalsIgnoreCase(trackingNumber)) return true;
        }
        return false;
    }

    public void addParcel(Parcel parcel) throws DuplicateTrackingNumberException {
        if (exists(parcel.getTrackingNumber())) {
            throw new DuplicateTrackingNumberException("Tracking number already exists!");
        }
        parcels.add(parcel);
    }

    public Parcel findParcel(String trackingNumber) throws ParcelNotFoundException {
        for (Parcel p : parcels) {
            if (p.getTrackingNumber().equalsIgnoreCase(trackingNumber)) return p;
        }
        throw new ParcelNotFoundException("Parcel not found!");
    }

    public List<Parcel> getAllParcels() {
        return new ArrayList<>(parcels);
    }

    public void changeService(String trackingNumber, DeliveryStrategy service)
            throws ParcelNotFoundException, ParcelAlreadyDeliveredException, InvalidDeliveryServiceException {
        Parcel p = findParcel(trackingNumber);
        if (p.isDelivered()) {
            throw new ParcelAlreadyDeliveredException("Parcel already delivered!");
        }
        p.setDeliveryService(service);
    }

    public void markDelivered(String trackingNumber)
            throws ParcelNotFoundException, ParcelAlreadyDeliveredException {
        Parcel p = findParcel(trackingNumber);
        if (p.isDelivered()) {
            throw new ParcelAlreadyDeliveredException("Parcel was already delivered.");
        }
        p.markDelivered();
    }
}