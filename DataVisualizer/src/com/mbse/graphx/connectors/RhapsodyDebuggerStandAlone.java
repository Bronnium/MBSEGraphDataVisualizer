package com.mbse.graphx.connectors;

import com.telelogic.rhapsody.core.IRPApplication;
import com.telelogic.rhapsody.core.RhapsodyAppServer;

public class RhapsodyDebuggerStandAlone {

	public static void main(String[] args) {
		//create an instance of my plug-in
		RhapsodyConnector myPlugin = new RhapsodyConnector();
		//get Rhapsody application that is currently running
		IRPApplication app = RhapsodyAppServer.getActiveRhapsodyApplication();
		//init the plug-in
		myPlugin.RhpPluginInit(app);
		
		/*
		for (Object obj : app.getSelectedGraphElements().toList())
		{
			IRPCollection gps = ((IRPGraphElement ) obj).getAllGraphicalProperties();
	        for (int i=0; i < gps.getCount(); i++) {
	            IRPGraphicalProperty gp = (IRPGraphicalProperty) gps.getItem(i);
	            if (gp != null)
	            {// Null graphical properties may exist
	                System.out.println(gp.getKey() + "\t" + gp.getValue());
	            }

	        }
	        String polygonValue = "4,329,238,363,238,363,449,397,449";
			((IRPGraphEdge ) obj).setGraphicalProperty("Polygon", polygonValue );
		}
		*/	

		
		//simulate a call to the plug-in
		myPlugin.OnMenuItemSelect("Rearrange diagram");
	}
}
