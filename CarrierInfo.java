import java.util.*;

public class CarrierInfo {
    private String carrierId;
    private String carrierName;
    private String fleetType;
    private LinkedList<ShipmentInfo> shipments;

    public CarrierInfo(String carrierId, String carrierName, String fleetType) {
        this.carrierId = carrierId;
        this.carrierName = carrierName;
        this.fleetType = fleetType;
        this.shipments = new LinkedList<>();
    }

    public String getCarrierId() { 
        return carrierId; 
    }

    public String getCarrierName() { 
        return carrierName; 
    }

    public String getFleetType() { 
        return fleetType; 
    }

    public LinkedList<ShipmentInfo> getShipments() { 
        return shipments; 
    }

    public void addShipment(ShipmentInfo shipment) {
        this.shipments.add(shipment);
    }
}