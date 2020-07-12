package com.mbse.graphx.connectors;

import java.util.Iterator;
import java.util.List;

import com.mbse.graphx.MbseGraphVisualizerApplication;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.telelogic.rhapsody.core.IRPApplication;
import com.telelogic.rhapsody.core.IRPCollection;
import com.telelogic.rhapsody.core.IRPDiagram;
import com.telelogic.rhapsody.core.IRPGraphElement;
import com.telelogic.rhapsody.core.IRPModelElement;
import com.telelogic.rhapsody.core.IRPProject;
import com.telelogic.rhapsody.core.RPUserPlugin;

public class RhapsodyConnector extends RPUserPlugin {

	protected IRPApplication m_rhpApplication = null;
	private IRPDiagram diagram;

	// called when the plug-in is loaded
	public void RhpPluginInit(final IRPApplication rpyApplication) {
		// keep the application interface for later use
		m_rhpApplication = rpyApplication;

		//graphicalInterface  = new MbseGraphVisualizer();
		String msg = "MBSE Graph Visualizer plug-in: Loaded test.\n";
		m_rhpApplication.writeToOutputWindow("Log" , msg);
	}

	// called when the plug-in menu item under the "Tools" menu is selected	
	public void RhpPluginInvokeItem() {
		// show the active project overridden properties
				String projectProps = new String();
				IRPProject prj = m_rhpApplication.activeProject();
				if (prj != null) {
					IRPCollection propsCollection = prj.getOverriddenProperties(0);
					if (propsCollection != null && propsCollection.getCount() > 0) {
						Iterator proprsIter = propsCollection.toList().iterator();
						while (proprsIter.hasNext()) {
							projectProps += proprsIter.next() + "\n";
						}
					} else {
						projectProps = "No overriden properties";
		
					}
				} else {
					projectProps = "No active project";
				}
				//JOptionPane.showMessageDialog(null,"Hello world from SimplePlugin.RhpPluginInvokeItem.\n Your active project overridden properties are: \n"+ projectProps);
				String msg = "Hello world from SimplePlugin.RhpPluginInvokeItem.\n Your active project overridden properties are: \n"+ projectProps + "\n";
				m_rhpApplication.writeToOutputWindow("Simple Plugin" , msg);
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
			diagram = (IRPDiagram) element;
			
			System.out.println(diagram.getUserDefinedMetaClass());

			RhapsodyMbseModel rhapsodyMbseModel = new RhapsodyMbseModel(diagram, diagram.getUserDefinedMetaClass());

			MbseGraphVisualizerApplication application = new MbseGraphVisualizerApplication(rhapsodyMbseModel);

			// les différents layouts sont testés dans le preview
			// on recupere le resultat du graph de donnée
			synchronized (application.dataController) {
				try {
					application.dataController.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// récupération des position des objects
			//getGraphicalPosition(rhapsodyMbseModel);

			// fermeture de l'application
			application.close();
		}

	}

	/**
	 * 
	 * @param rhapsodyMbseModel
	 */
	private void getGraphicalPosition(RhapsodyMbseModel rhapsodyMbseModel) {

		List<IRPGraphElement> elements = diagram.getGraphicalElements().toList();

		
		// nodes first then edges
		for (IRPGraphElement graphElement : elements) 
		{
			String graphicalGUID = graphElement.getGraphicalProperty("GUID").getValue();
			
			mxCell cell = (mxCell) ((mxGraphModel) rhapsodyMbseModel.getModel()).getCell(graphicalGUID);
			
			if(cell==null)
				continue;
			if (cell.isVisible())
			{
				if (cell.isVertex())
				{
					System.out.println("vertex: "+cell.getGeometry());
					graphElement.setGraphicalProperty("Position", (int) cell.getGeometry().getX()+","+ (int) cell.getGeometry().getY());
					
					graphElement.setGraphicalProperty("Height", ""+cell.getGeometry().getHeight());
					graphElement.setGraphicalProperty("Width", ""+cell.getGeometry().getWidth());
					
					graphElement.setGraphicalProperty("TextFontSize", "11");
					System.out.println("vertex position: "+(int) cell.getGeometry().getX()+","+ (int) cell.getGeometry().getY());
				}
				else
				{
					String str = RhapsodyUtilities.convertToPointsToString(cell.getGeometry().getPoints());
					graphElement.setGraphicalProperty("Polygon", str);
				}
				
				
			}

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
