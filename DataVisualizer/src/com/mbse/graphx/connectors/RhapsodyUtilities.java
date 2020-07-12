package com.mbse.graphx.connectors;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.mbse.graphx.Port;
import com.mxgraph.util.mxPoint;

public class RhapsodyUtilities {

	public static void positionPort(ArrayList<Port> listOfPorts) {
		double spacing = 0;

		spacing = 1.0 / (listOfPorts.size());

		double Yposition=0;
		for (int i = 0; i < listOfPorts.size(); i++) {
			if (i==0) {
				Yposition = spacing/2.0;
			}
			else
			{
				Yposition = Yposition + spacing;
			}
			System.out.println("Placed: "+Yposition);
			listOfPorts.get(i).getGeometry().setY(Yposition);
		}


	}
	
	public static void sortIRPCollection() {
		
	}
	
	public static String convertToPointsToString(List<mxPoint> points) {
		String stringPoints = "";
		//number of points
		stringPoints+=points.size();

		//loop over points
		for (mxPoint point : points) 
		{
			stringPoints+=","+(int) point.getX()+","+(int) point.getY();
		}

		return stringPoints;
	}

    
	public static String convertRGBToHex(String rgbColor) {
		
		
		System.out.println("color: "+rgbColor);
		String[] values = rgbColor.split(",");
		
        int red = Integer.parseInt(values[0]);
        int green = Integer.parseInt(values[1]);
        int blue = Integer.parseInt(values[2]);
		
        
        Color color = new Color(red, green, blue);
        String hexValue = Integer.toHexString(color.getRGB() & 0x00ffffff);
        
        int numberOfZeroesNeededForPadding = 6 - hexValue.length();
        String zeroPads = "";
        for (int i = 0; i < numberOfZeroesNeededForPadding; i++) {
            zeroPads += "0";
        }
        hexValue = "#" + zeroPads + hexValue;
        
		System.out.println("Color: "+hexValue);

		return hexValue;
	}

	
}


