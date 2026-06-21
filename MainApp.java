import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp extends JFrame {
    private LogisticsManager manager;
    private JTextArea outputArea;
    private JButton btnAction;
    private JLabel lblStatus; 
    private int currentStep = 1;

    public MainApp() {
        
        setTitle("Green Logistics System");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        manager = new LogisticsManager();

        // Top Section
        lblStatus = new JLabel("System Ready. Please click 'Start Process' to begin.", JLabel.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 14));
        lblStatus.setForeground(new Color(43, 108, 176)); 
        lblStatus.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        //(Middle Section
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setBackground(new Color(248, 249, 250)); 
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        // Bottom Section
        btnAction = new JButton("Start Process");
        btnAction.setFont(new Font("Arial", Font.BOLD, 13));
        btnAction.setFocusPainted(false);
        btnAction.setBackground(new Color(72, 187, 120)); 
        btnAction.setForeground(Color.WHITE);
        btnAction.setPreferredSize(new Dimension(150, 40));

        JPanel panelButang = new JPanel();
        panelButang.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelButang.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelButang.add(btnAction);

        // Assemble Layout
        setLayout(new BorderLayout());
        add(lblStatus, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButang, BorderLayout.SOUTH);

        // --- MICRO-STEP CONTROLLER ---
        btnAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (currentStep) {
                    case 1:
                        lblStatus.setText("STATUS: Reading data from manifest text file...");
                        lblStatus.setForeground(new Color(217, 119, 6)); 

                        // Execute Phase 1 
                        String resultPhase1 = manager.readManifestFile("supply_chain_manifest.txt");
                        outputArea.append(resultPhase1);
                        manager.distributeCarriersLogic(); 

                        lblStatus.setText("STATUS: Phase 1 Complete. Ready to process Queue 1.");
                        lblStatus.setForeground(new Color(22, 163, 74)); 
                        btnAction.setText("Next Queue");
                        btnAction.setBackground(new Color(66, 153, 225)); // Corporate Blue
                        currentStep = 2;
                        break;

                    case 2:
                        lblStatus.setText("STATUS: Processing and isolating carriers into Queue 1...");
                        lblStatus.setForeground(new Color(217, 119, 6));

                        // Output Queue 1 
                        String resultQ1 = manager.getQueue1Display();
                        outputArea.append(resultQ1);

                        lblStatus.setText("STATUS: Queue 1 loaded. Ready to process Queue 2.");
                        lblStatus.setForeground(new Color(22, 163, 74));
                        currentStep = 3;
                        break;

                    case 3:
                        lblStatus.setText("STATUS: Processing and isolating carriers into Queue 2...");
                        lblStatus.setForeground(new Color(217, 119, 6));

                        // Output Queue 2
                        String resultQ2 = manager.getQueue2Display();
                        outputArea.append(resultQ2);

                        lblStatus.setText("STATUS: Queue 2 loaded. Ready to process Queue 3.");
                        lblStatus.setForeground(new Color(22, 163, 74));
                        currentStep = 4;
                        break;

                    case 4:
                        lblStatus.setText("STATUS: Processing and isolating carriers into Queue 3...");
                        lblStatus.setForeground(new Color(217, 119, 6));

                        // Output Queue 3 
                        String resultQ3 = manager.getQueue3Display();
                        outputArea.append(resultQ3);

                        lblStatus.setText("STATUS: All Queues successfully populated. Ready for final settlement.");
                        lblStatus.setForeground(new Color(22, 163, 74));
                        btnAction.setText("Finish");
                        btnAction.setBackground(new Color(229, 62, 62)); // Warning Red
                        currentStep = 5;
                        break;

                    case 5:
                        lblStatus.setText("STATUS: Dispatching elements via Stack (Cyclic Batch-of-5)...");
                        lblStatus.setForeground(new Color(217, 119, 6));

                        // Execute Phase 3
                        String resultSettlement = manager.processSettlement();
                        outputArea.append(resultSettlement);

                        lblStatus.setText("STATUS: SYSTEM RUN COMPLETE. Terminal Departure Log fully generated.");
                        lblStatus.setForeground(new Color(22, 163, 74));
                        
                        // Exit Button
                        btnAction.setText("Exit");
                        btnAction.setBackground(new Color(26, 32, 44)); // Dark Carbon Black
                        currentStep = 6;
                        break;

                    case 6:
                        // system exit
                        System.exit(0);
                        break;
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainApp().setVisible(true);
            }
        });
    }
}