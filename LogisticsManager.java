import java.io.*;
import java.util.*;

public class LogisticsManager {
    private LinkedList<CarrierInfo> carrierList;
    private Queue<CarrierInfo> queue1; 
    private Queue<CarrierInfo> queue2; 
    private Queue<CarrierInfo> queue3; 
    private Stack<CarrierInfo> dispatchedStack;

    public LogisticsManager() {
        this.carrierList = new LinkedList<>();
        this.queue1 = new LinkedList<>();
        this.queue2 = new LinkedList<>();
        this.queue3 = new LinkedList<>();
        this.dispatchedStack = new Stack<>();
    }

    // PHASE 1: Reading Manifest
    public String readManifestFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== PHASE 1: READING DATA FROM MANIFEST FILE ===\n");
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",");
                if (st.countTokens() < 9) continue;

                String carrierId = st.nextToken();
                String carrierName = st.nextToken();
                String fleetType = st.nextToken();
                String shipmentId = st.nextToken();
                String packageType = st.nextToken();
                int ecoPriority = Integer.parseInt(st.nextToken());
                String date = st.nextToken();
                int transitTime = Integer.parseInt(st.nextToken());
                double carbonTax = Double.parseDouble(st.nextToken());

                ShipmentInfo newShipment = new ShipmentInfo(shipmentId, packageType, ecoPriority, date, transitTime, carbonTax);

                CarrierInfo existingCarrier = null;
                for (int i = 0; i < carrierList.size(); i++) {
                    if (carrierList.get(i).getCarrierId().equals(carrierId)) {
                        existingCarrier = carrierList.get(i);
                        break; 
                    }
                }

                if (existingCarrier == null) {
                    CarrierInfo newCarrier = new CarrierInfo(carrierId, carrierName, fleetType);
                    newCarrier.addShipment(newShipment);
                    carrierList.add(newCarrier);         
                } else {
                    existingCarrier.addShipment(newShipment);
                }
            }
            br.close(); 
            sb.append("Success! ").append(carrierList.size()).append(" unique carriers successfully loaded into the system.\n\n");

        } catch (IOException e) {
            sb.append("Error: Issue reading file ").append(fileName).append("\n");
        }
        return sb.toString();
    }

    // PHASE 2
    public void distributeCarriersLogic() {
        boolean alternateToQ1 = true; 
        for (int i = 0; i < carrierList.size(); i++) {
            CarrierInfo carrier = carrierList.get(i);
            int totalShipments = carrier.getShipments().size(); 

            if (totalShipments <= 3) {
                if (alternateToQ1) {
                    queue1.add(carrier);      
                    alternateToQ1 = false;    
                } else {
                    queue2.add(carrier);      
                    alternateToQ1 = true;     
                }
            } else {
                queue3.add(carrier);
            }
        }
    }

    //Display Queue 1
    public String getQueue1Display() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== PHASE 2 (PART 1): REGIONAL MICRO-DISTRIBUTION QUEUE ===\n");
        sb.append(getQueueContentsString(queue1));
        return sb.toString();
    }

    //Display Queue 2 
    public String getQueue2Display() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== PHASE 2 (PART 2): CROSS-BORDER EXPRESS QUEUE ===\n");
        sb.append(getQueueContentsString(queue2));
        return sb.toString();
    }

    //Display Queue 3 
    public String getQueue3Display() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== PHASE 2 (PART 3): INDUSTRIAL BULK LOGISTICS FLEET ===\n");
        sb.append(getQueueContentsString(queue3));
        return sb.toString();
    }

    // PHASE 3: Settlement processing
    public String processSettlement() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== PHASE 3: CYCLIC BATCH-OF-5 DISPATCH PROCESS ===\n");

        while (!queue1.isEmpty() || !queue2.isEmpty() || !queue3.isEmpty()) {
            for (int i = 0; i < 5; i++) {
                if (!queue1.isEmpty()) dispatchedStack.push(queue1.poll());
                else break;
            }
            for (int i = 0; i < 5; i++) {
                if (!queue2.isEmpty()) dispatchedStack.push(queue2.poll());
                else break;
            }
            for (int i = 0; i < 5; i++) {
                if (!queue3.isEmpty()) dispatchedStack.push(queue3.poll());
                else break;
            }
        }
        sb.append("All distribution pipelines processed. Moving elements into Stack memory...\n\n");
        sb.append("=================== TERMINAL DEPARTURE LOG ===================\n");
        
        while (!dispatchedStack.isEmpty()) {
            CarrierInfo poppedCarrier = dispatchedStack.pop();
            double totalCarbonTax = 0;
            for (int i = 0; i < poppedCarrier.getShipments().size(); i++) {
                totalCarbonTax += poppedCarrier.getShipments().get(i).getCarbonTaxCost();
            }

            sb.append("Carrier Name  : ").append(poppedCarrier.getCarrierName()).append("\n");
            sb.append("Fleet Type    : ").append(poppedCarrier.getFleetType()).append("\n");
            sb.append("Total Shipments Handled : ").append(poppedCarrier.getShipments().size()).append("\n");
            sb.append("Total Combined Carbon Tax: RM ").append(totalCarbonTax).append("\n");
            sb.append("--------------------------------------------------------------\n");
        }
        return sb.toString();
    }

    private String getQueueContentsString(Queue<CarrierInfo> q) {
        StringBuilder sb = new StringBuilder();
        if (q.isEmpty()) {
            sb.append("(This lane is currently empty)\n");
            return sb.toString();
        }

        for (CarrierInfo carrier : q) {
            sb.append("-> Carrier Name: ").append(carrier.getCarrierName()).append("\n");
            sb.append("   Fleet Classification: ").append(carrier.getFleetType()).append("\n");
            sb.append("   Assigned Physical Shipments:\n");

            double totalTax = 0;
            for (int i = 0; i < carrier.getShipments().size(); i++) {
                ShipmentInfo s = carrier.getShipments().get(i);
                sb.append("     * [").append(s.getShipmentId()).append("] ").append(s.getPackageType()).append(" (Tax: RM ").append(s.getCarbonTaxCost()).append(")\n");
                totalTax += s.getCarbonTaxCost(); 
            }
            sb.append("   Total Lane Processing Carbon Tax: RM ").append(totalTax).append("\n");
            sb.append("---------------------------------------------------------\n");
        }
        return sb.toString();
    }
}