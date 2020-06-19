package com.mbse.graphx.rhapsody;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import com.mbse.graphx.FoldableTree;
import com.mbse.graphx.MbseGraphVisualizerApplication;
import com.mbse.graphx.layout.FunctionalBreakdownStructureLayout;
import com.mbse.graphx.layout.ProductBreakdownStructureLayout;
import com.mbse.graphx.ui.MbseGraphVisualizerUI;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxEdgeStyle;
import com.mxgraph.view.mxGraph;
import com.telelogic.rhapsody.core.IRPApplication;
import com.telelogic.rhapsody.core.IRPClass;
import com.telelogic.rhapsody.core.IRPCollection;
import com.telelogic.rhapsody.core.IRPDependency;
import com.telelogic.rhapsody.core.IRPDiagram;
import com.telelogic.rhapsody.core.IRPGraphEdge;
import com.telelogic.rhapsody.core.IRPGraphElement;
import com.telelogic.rhapsody.core.IRPGraphNode;
import com.telelogic.rhapsody.core.IRPGraphicalProperty;
import com.telelogic.rhapsody.core.IRPModelElement;
import com.telelogic.rhapsody.core.IRPNode;
import com.telelogic.rhapsody.core.IRPOperation;
import com.telelogic.rhapsody.core.IRPProject;
import com.telelogic.rhapsody.core.RPOperation;
import com.telelogic.rhapsody.core.RPUserPlugin;

public class RhapsodyConnector extends RPUserPlugin {

	protected IRPApplication m_rhpApplication = null;

	// called when the plug-in is loaded
	public void RhpPluginInit(final IRPApplication rpyApplication) {
		// keep the application interface for later use
		m_rhpApplication = rpyApplication;

		//graphicalInterface  = new MbseGraphVisualizer();
		String msg = "MBSE Graph Visualizer plug-in: Loaded.\n";
		m_rhpApplication.writeToOutputWindow("Log" , msg);
	}

	// called when the plug-in menu item under the "Tools" menu is selected	
	public void RhpPluginInvokeItem() {
		// show the active project overridden properties
		//		String projectProps = new String();
		//		IRPProject prj = m_rhpApplication.activeProject();
		//		if (prj != null) {
		//			IRPCollection propsCollection = prj.getOverriddenProperties(0);
		//			if (propsCollection != null && propsCollection.getCount() > 0) {
		//				Iterator proprsIter = propsCollection.toList().iterator();
		//				while (proprsIter.hasNext()) {
		//					projectProps += proprsIter.next() + "\n";
		//				}
		//			} else {
		//				projectProps = "No overriden properties";
		//
		//			}
		//		} else {
		//			projectProps = "No active project";
		//		}
		//		//JOptionPane.showMessageDialog(null,"Hello world from SimplePlugin.RhpPluginInvokeItem.\n Your active project overridden properties are: \n"+ projectProps);
		//		String msg = "Hello world from SimplePlugin.RhpPluginInvokeItem.\n Your active project overridden properties are: \n"+ projectProps + "\n";
		//		m_rhpApplication.writeToOutputWindow("Simple Plugin" , msg);
	}

	// called when the plug-in pop-up menu (if applicable) is selected
	public void OnMenuItemSelect(String menuItem) {

		IRPModelElement element = m_rhpApplication.getSelectedElement();
		
		switch (menuItem) {
		case "Rearrange diagram":
			prepareGraphModelData(menuItem, element);
			break;

		default:
			break;
		}
	}

	private void prepareGraphModelData(String menuItem, IRPModelElement element) {
		
		if (element instanceof IRPDiagram) {
			IRPDiagram diagram = (IRPDiagram) element;
			
			RhapsodyMbseModel rhapsodyMbseModel = new RhapsodyMbseModel();
			
			MbseGraphVisualizerApplication application = new MbseGraphVisualizerApplication(rhapsodyMbseModel);
			
			application.update();
			
			
		}

	}


	// called when the plug-in popup trigger (if applicable) fired
	public void OnTrigger(String trigger) {
		//show the trigger string
		//JOptionPane.showMessageDialog(null, "Hello world from SimplePlugin.OnTrigger " + trigger);
		String msg = "Hello world from SimplePlugin.OnTrigger " + trigger + "\n"; 
		m_rhpApplication.writeToOutputWindow("Simple Plugin" , msg);
	}

	// called when the project is closed - if true is returned the plugin will
	// be unloaded
	public boolean RhpPluginCleanup() {
		//JOptionPane.showMessageDialog(null,		"Hello world from SimplePlugin.RhpPluginCleanup");
		//cleanup
		m_rhpApplication = null;
		//return true so the plug-in will be unloaded now (on project close)
		return true;
	}

	// called when Rhapsody exits
	public void RhpPluginFinalCleanup() {
		//JOptionPane.showMessageDialog(null,	"Hello world from SimplePlugin.RhpPluginFinalCleanup");
	}
}
