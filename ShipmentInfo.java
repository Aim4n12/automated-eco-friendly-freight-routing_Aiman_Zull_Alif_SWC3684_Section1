public class ShipmentInfo {
    private String shipmentId;
    private String packageType;
    private int ecoPriorityScore;
    private String dispatchDate;
    private int estimatedTransitTime;
    private double carbonTaxCost;

    public ShipmentInfo(String shipmentId, String packageType, int ecoPriorityScore, 
    String dispatchDate, int estimatedTransitTime, double carbonTaxCost) {
        this.shipmentId = shipmentId;
        this.packageType = packageType;
        this.ecoPriorityScore = ecoPriorityScore;
        this.dispatchDate = dispatchDate;
        this.estimatedTransitTime = estimatedTransitTime;
        this.carbonTaxCost = carbonTaxCost;
    }

    public String getShipmentId() { 
        return shipmentId; 
    }

    public String getPackageType() { 
        return packageType; 
    }

    public int getEcoPriorityScore() { 
        return ecoPriorityScore; 
    }

    public String getDispatchDate() { 
        return dispatchDate; 
    }

    public int getEstimatedTransitTime() { 
        return estimatedTransitTime; 
    }

    public double getCarbonTaxCost() { 
        return carbonTaxCost; 
    }
}