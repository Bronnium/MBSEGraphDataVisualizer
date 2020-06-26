package com.mbse.graphx;

import java.util.ArrayList;
import java.util.List;

import com.mxgraph.model.mxCell;

public class GraphCallOperation {

	private static final double portSpacing = 24.0;
	private static final double portYOffset = 10.0;
	
	protected mxCell representedmxCell;
	List<mxCell> listOfInputs = new ArrayList<mxCell>();
	List<mxCell>  listOfOutputs = new ArrayList<mxCell>();
	
	public GraphCallOperation(mxCell cell) {		
		representedmxCell = cell;
	}

	public void updateSize()
	{
		updateHeight();
		positionPort(listOfInputs);
		positionPort(listOfOutputs);
	}
	
	public void addInput(mxCell e)
	{
		listOfInputs.add(e);
	}
	
	public void addOutput(mxCell e)
	{
		listOfOutputs.add(e);
	}
	
	private void updateHeight() {
	
		int portNumber = Math.max(listOfInputs.size(), listOfOutputs.size());
		
		double computedHeight = (portNumber-1.0)*portSpacing + 2.0*portYOffset + portNumber * 12.0;
		
		representedmxCell.getGeometry().setHeight(computedHeight);		
	}
	
	private void positionPort(List<mxCell> listOfInPorts) {
		double spacing = 0;

		spacing = 1.0 / (listOfInPorts.size());

		double Yposition=0;
		for (int i = 0; i < listOfInPorts.size(); i++) {
			if (i==0) {
				Yposition = spacing/2.0;
			}
			else
			{
				Yposition = Yposition + spacing;
			}
			System.out.println("Placed: "+Yposition);
			listOfInPorts.get(i).getGeometry().setY(Yposition);
		}


	}

}
