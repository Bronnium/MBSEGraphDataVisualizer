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
		//simulate a call to the plug-in
		myPlugin.OnMenuItemSelect("Rearrange diagram");
	}
}
