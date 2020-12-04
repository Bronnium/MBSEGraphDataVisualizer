package com.mbse.layout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.view.mxGraph;

public class CircularLayoutButton extends JButton implements ActionListener {

	private mxGraph applicableGraph;
	/**
	 * 
	 */
	public CircularLayoutButton(mxGraph graph) {
		// mxCircleLayout
		super(new ImageIcon("icons/circlar.png"));
		this.setToolTipText("Circular Layout");
		
		this.applicableGraph = graph;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("actionPerformed...");
		Object parent = applicableGraph.getDefaultParent();
		final mxCircleLayout layout = new mxCircleLayout(applicableGraph); 
		layout.execute(parent);
		applicableGraph.repaint();
	}

}
