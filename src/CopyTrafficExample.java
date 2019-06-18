import java.awt.*;
import java.awt.event.*;
import java.text.*;

import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.data.xy.*;


//import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
 
//import java.text.*;

@SuppressWarnings("serial")
public class CopyTrafficExample extends JPanel implements ActionListener{
	
	//Values for the fields
	private int trials = 1000;
	private int cars = 100;
	private int spaces = 1000;
	private int vMax = 5;
	private double pCrit = (1/3);
	
	//Labels to identify the fields
	private JLabel trialLabel;
	private JLabel carLabel;
	private JLabel spaceLabel;
	private JLabel vMaxLabel;
	private JLabel pCritLabel;
		
	//FIelds for data entry
	private JFormattedTextField trialField;
	private JFormattedTextField carField;
	private JFormattedTextField spaceField;
	private JFormattedTextField vMaxField;
	private JFormattedTextField pCritField;
	
	private NumberFormat numFormat;
	
	private XYSeries series;
	private XYSeriesCollection dataset;
	private JFreeChart chart;
	
	public CopyTrafficExample() {
		super(new BorderLayout());
        numFormat = NumberFormat.getNumberInstance();
		/*
        //Initialize Window
        JFrame window = new JFrame();
        window.setTitle("Nagel-Schkreckenberg Traffic");
        window.setSize(2000, 1600);
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		*/
        //Run Button
        JButton startButton = new JButton("Start");
        JPanel topPanel = new JPanel();
        topPanel.add(startButton);
        add(topPanel, BorderLayout.NORTH);
        
        //Create Graph
        series = new XYSeries("Traffic Modeling Readings");
        dataset = new XYSeriesCollection(series);
        chart = ChartFactory.createScatterPlot("Nagel-Schkreckenberg Traffic",
        		"Distance (space)", "Time (seconds)", dataset);
        add(new ChartPanel(chart), BorderLayout.CENTER);
                
        
        //TextBoxes
        //JScrollBar scrollBar = new JScrollBar();
        trialLabel = new JLabel("Trials :");
        JFormattedTextField trialField = new JFormattedTextField(numFormat);
        trialField.setValue(new Integer(100));;
        trialField.setColumns(10);
        trialLabel.setLabelFor(trialField);
        
        carLabel = new JLabel("Cars :");
        JFormattedTextField carField = new JFormattedTextField(numFormat);
        carField.setValue(new Integer(100));;
        carField.setColumns(10);
        carLabel.setLabelFor(carField);

        spaceLabel = new JLabel("Spaces :");
        JFormattedTextField spaceField = new JFormattedTextField(numFormat);
        spaceField.setValue(new Integer(1000));;
        spaceField.setColumns(10);
        spaceLabel.setLabelFor(spaceField);
        
        vMaxLabel = new JLabel("Max Speed :");
        JFormattedTextField vMaxField = new JFormattedTextField(numFormat);
        vMaxField.setValue(new Integer(5));;
        vMaxField.setColumns(10);
        vMaxLabel.setLabelFor(vMaxField);
        
        pCritLabel = new JLabel("Critical Prob. :");
        JFormattedTextField pCritField = new JFormattedTextField(numFormat);
        pCritField.setValue(new Double(1/3));;
        pCritField.setColumns(10);
        pCritLabel.setLabelFor(pCritField);
        
        //textTrials.addPropertyChangeListener("value", this);
        JPanel labelPanel = new JPanel(new GridLayout(0, 1));
        labelPanel.add(trialLabel);
        labelPanel.add(carLabel);
        labelPanel.add(spaceLabel);
        labelPanel.add(vMaxLabel);
        labelPanel.add(pCritLabel);
        add(labelPanel, BorderLayout.EAST);
        
        JPanel fieldPanel = new JPanel(new GridLayout(0, 1));
        fieldPanel.add(trialLabel);
        fieldPanel.add(carLabel);
        fieldPanel.add(spaceLabel);
        fieldPanel.add(vMaxLabel);
        fieldPanel.add(pCritLabel);
        add(fieldPanel, BorderLayout.LINE_END);
    }

	 
    /** Called when a field's "value" property changes. */
    public void propertyChange(PropertyChangeEvent e) {
        Object source = e.getSource();
        if (source == trialField) {
            trials = ((Number)trialField.getValue()).intValue();
        } else if (source == carField) {
            cars = ((Number)carField.getValue()).intValue();
        } else if (source == spaceField) {
            spaces = ((Number)spaceField.getValue()).intValue();
        } else if (source == vMaxField) {
            vMax = ((Number)vMaxField.getValue()).intValue();
        } else if (source == pCritField) {
            pCrit = ((Number)pCritField.getValue()).doubleValue();
        }
    }
    
    /** Create the Graph after Start Button Pressed */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getActionCommand();
		if ("Start".equals(source)) {
			simulate(trials, cars, spaces, vMax, pCrit);
		}
	}
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Nagel-Schkreckenberg Traffic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new CopyTrafficExample());
        frame.setSize(2000, 1600);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
            UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
        
    } 
    
    public void simulate(int Trial, int cars, int spaces, int maxVelocity, double pCrit) {

    		int vMax = maxVelocity;
    		int MSpaces = spaces;
    		int NCars = cars;
    		int Trials = Trial;
    		double pCritical = pCrit;
    		
    		int positionArray[] = new int[NCars];
	        int velocityCurrentArray[] = new int[NCars];
	        int velocityPreviousArray[] = new int[NCars];
	        
	        int initPos = 1;
	        boolean possibleRepeat;

	        for(int i=0; i < positionArray.length; i++) {
	        	possibleRepeat = true;
	        	while (possibleRepeat) {
	                initPos = (int) Math.max(((Math.random()) * MSpaces), 1);
	                possibleRepeat = false;
	                for (int j = 0; j < positionArray.length; j++) {
	                	if (positionArray[j] == initPos) {
	                        possibleRepeat = true;
	                    }
	                }
	            }
	            positionArray[i] = initPos;
	            //positionArray[i] = ((i+1)*10);
	            velocityPreviousArray[i] = 0;
	        }
	        
	        
			//int distance;
			int difValue;
			int minDist;

			for (int n=1; n<Trials; n++) {
				for(int i=0; i<positionArray.length; i++) {
					series.add(positionArray[i], (Trials - n + 1));
					velocityCurrentArray[i] = Math.min((velocityPreviousArray[i]+1), vMax);
					minDist = Math.abs(positionArray[0] - positionArray[i]);
					for (int j=1; j<positionArray.length; j++) {
						if (j==i) {
							j++;
							if (j==positionArray.length) {
								break;
							}
						}
						difValue = Math.abs(positionArray[j] - positionArray[i]);
						if (difValue < minDist) {
							if (positionArray[j] - positionArray[i] > 0) {
								minDist = difValue;
							}
						}
					}      
					velocityCurrentArray[i] = Math.min((velocityCurrentArray[i]), (minDist-1));
					if (Math.random() >= pCritical) {
						velocityCurrentArray[i] = Math.max(0, (velocityCurrentArray[i]-1));
					}
					positionArray[i] = positionArray[i] + velocityCurrentArray[i];
					
					if (positionArray[i] > MSpaces) {
						positionArray[i] = positionArray[i] - MSpaces;
					}
				}
				velocityPreviousArray = velocityCurrentArray.clone();
			}
    }
	
}
