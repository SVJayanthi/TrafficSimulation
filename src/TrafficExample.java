import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;

import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.*;

public class TrafficExample{
	
	public static void main(String[] args){
		
        //Initialize Window
        JFrame window = new JFrame();
        window.setTitle("Nagel-Schkreckenberg Traffic");
        window.setSize(3400, 2000);
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        //Run Button
        JButton startButton = new JButton("Start");
        JButton clearButton = new JButton("Clear");
        JPanel topPanel = new JPanel();
        topPanel.add(startButton);
        topPanel.add(clearButton);
        window.add(topPanel, BorderLayout.NORTH);
        
        //Create Graph
        Shape shape = new Ellipse2D.Double(0, 0, 2, 2);
        XYSeries series = new XYSeries("Traffic Modeling Readings");
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createScatterPlot("Nagel-Schkreckenberg Traffic",
        		"Distance (space)", "Time (seconds)", dataset);
        window.add(new ChartPanel(chart), BorderLayout.CENTER);
        XYPlot plot = (XYPlot)chart.getPlot();
        XYItemRenderer renderer = plot.getRenderer();
        renderer.setBaseShape(shape);
        renderer.setBasePaint(Color.red);
        renderer.setSeriesShape(0, shape);
        plot.setBackgroundPaint(Color.white);
                
        //Thread Operating
		final Thread thread = new Thread() {
			@Override public void run() {
    			startButton.setText("Running");
				//Variable Instantiation
    	        int vMax = 80;
    	        int MSpaces = 1000;
    	        int NCars = 100;
    	        int Trials = 1000;
    	        double pCrit = 0.0;
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
    	            velocityPreviousArray[i] = 1;
    	        }
    	        
    			int difValue;
    			int minDist;
    for (int n=1; n<Trials; n++) {
        for(int i=0; i<positionArray.length; i++) {
            series.add(positionArray[i], (Trials - n));
            velocityCurrentArray[i] = Math.min((velocityPreviousArray[i]+1), vMax);
            if (positionArray[0] < positionArray[i]) {
            	minDist = (positionArray[0] - positionArray[i]) + MSpaces;
            } else if (positionArray[0] == positionArray[i]) {
            	minDist = Math.abs(positionArray[(i+1)] - positionArray[i]);
            } else {
                minDist = Math.abs(positionArray[0] - positionArray[i]);
            }
            for (int j=1; j<positionArray.length; j++) {
            	if (positionArray[j] < positionArray[i]) {
            		difValue = Math.abs(positionArray[j] - positionArray[i] + MSpaces);
            	} else if (positionArray[j] == positionArray[i]){
                	if ((j+1)==positionArray.length) {break;}
                	difValue = Math.abs(positionArray[(j+1)] - positionArray[i]);
            	} else {
                    difValue = Math.abs(positionArray[j] - positionArray[i]);            		
            	}
            	if (difValue < minDist) {
                		minDist = difValue;
            	}
            }
            velocityCurrentArray[i] = Math.min((velocityCurrentArray[i]), (minDist-1));
            double prop = Math.random();
            if (prop >= pCrit) {
                velocityCurrentArray[i] = Math.max(0, (velocityCurrentArray[i]-1));
            }
            positionArray[i] = positionArray[i] + velocityCurrentArray[i];
            if (positionArray[i] > MSpaces) {
                positionArray[i] = positionArray[i] - MSpaces;
            }
        }
        velocityPreviousArray = Arrays.copyOf(velocityCurrentArray, velocityCurrentArray.length);
    }
	startButton.setText("Start");
			}
		};

      //Configure Start Button
        startButton.addActionListener(new ActionListener() {
        	@Override public void actionPerformed(ActionEvent argo0) {
        		if(startButton.getText().equals("Start")) {
        			
        			thread.start();
        			
        		}
        	}
        });
       
        clearButton.addActionListener(new ActionListener() {
        	@Override public void actionPerformed(ActionEvent argo0) {
        		if(clearButton.getText().equals("Clear")) {
        			/*if (thread.isAlive()) {
        				thread.interrupt();
        			}*/
        			series.clear();
        		}
        	}
        });
        
        //Show the window
        window.setVisible(true);
    }
}
