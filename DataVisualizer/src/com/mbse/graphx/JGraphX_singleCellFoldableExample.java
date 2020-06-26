package com.mbse.graphx;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class JGraphX_singleCellFoldableExample extends JFrame {

	mxGraph graph=null;
	mxGraphLayout layout=null;
    final Map<String,Boolean> foldRegistry=new HashMap<String,Boolean>();
	
	public static void main(String[] args) {
			JGraphX_singleCellFoldableExample frame = new JGraphX_singleCellFoldableExample();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(400, 320);
			frame.setVisible(true);
	}
	
	public JGraphX_singleCellFoldableExample() {

		graph = new mxGraph() {

			public boolean isCellFoldable(Object cell, boolean collapse)
			{
				mxICell c=(mxICell)cell;
				
				boolean result=c.getChildCount()!=0 && c!=c.getParent();
				String s=(String)((mxICell)cell).getValue();
				
				if(foldRegistry.containsKey(s)) {
					result=foldRegistry.get(s);
				}
				return result;
			}

		};

		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		
		try
		{
	
			mxICell containerNode=(mxICell)graph.insertVertex(parent, null, "a", 10, 10, 100, 100, "");
			mxICell sampleNode=(mxICell)graph.insertVertex(parent, null, "s", 200, 10, 100, 100, "");
			graph.insertEdge(parent, null, null, containerNode, sampleNode);
			

			mxCell titleCell = new mxCell("Title",new mxGeometry(20,60,60,30),"");
			titleCell.setVertex(true);
			
			mxCell title2Cell = new mxCell("Title2",new mxGeometry(20,60,60,30),"");
			title2Cell.setVertex(true);

			
			graph.addCell(titleCell, containerNode);
			graph.addCell(title2Cell, sampleNode);
	       
		}
		finally
		{
			graph.getModel().endUpdate();
		}

		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
					String key=new String(new char[] {e.getKeyChar()});
					
					Boolean status=foldRegistry.get(key);
					if(status==null) {
						status=Boolean.TRUE;
					}
					foldRegistry.put(key, Boolean.valueOf(!status.booleanValue()));

					graph.repaint();
					return false;
		      }
		});

		

		getContentPane().add(graphComponent);
		graphComponent.doLayout();

	}
	
}