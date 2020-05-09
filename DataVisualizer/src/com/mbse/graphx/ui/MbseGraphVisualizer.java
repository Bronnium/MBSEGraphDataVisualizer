package com.mbse.graphx.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.orthogonal.mxOrthogonalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

/**
 * Main class for MBSE Graph Visualization
 * @author
 *
 */
public class MbseGraphVisualizer extends JFrame {
	
	private double zoom_scaling = 1.0;
	private mxGraph graph;
	
	public MbseGraphVisualizer() {
		super("MBSE Graph Visualizer");
		// sets size
		this.setSize(800, 600);
		// centers the frame
		this.setLocationRelativeTo(null);
		// define behavior of close button
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		initUI();
	}

	private void initUI() {
		// Construction et injection de la barre d'outils
		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.add( this.createToolBar(), BorderLayout.NORTH );

	}

	private Component createToolBar() {
		// La barre d'outils à proprement parler
		JToolBar toolBar = new JToolBar();

		// empeche la barre d'etre bougée
		toolBar.setFloatable(false);

		JButton btnZoomIn = new JButton(new ImageIcon("C:\\Users\\Utilisateur\\eclipse_workspace\\DataVisualizer\\src\\icons\\zoom_in.png"));
		btnZoomIn.setToolTipText("Zoom In");
		btnZoomIn.addActionListener(this::btnZoomListener);
		toolBar.add(btnZoomIn);

		JButton btnZoomOut = new JButton(new ImageIcon("C:\\Users\\Utilisateur\\eclipse_workspace\\DataVisualizer\\src\\icons\\zoom_out.png"));
		btnZoomOut.setToolTipText("Zoom Out");
		toolBar.add(btnZoomOut);

		JButton btnZoomFit = new JButton( new ImageIcon("C:\\Users\\Utilisateur\\eclipse_workspace\\DataVisualizer\\src\\icons\\zoom_fit.png") );
		btnZoomFit.setToolTipText("Fit Zoom");
		toolBar.add(btnZoomFit);

		toolBar.addSeparator();

		JButton btnLayoutHierachical = new JButton( new ImageIcon("icons/copy.png"));
		btnLayoutHierachical.setToolTipText("Hierarchical Layout");
		btnLayoutHierachical.addActionListener(this::hierarchicalLayoutListener);
		toolBar.add(btnLayoutHierachical);

		JButton btnLayoutOrthogonal = new JButton(new ImageIcon("icons/cut.png"));
		btnLayoutOrthogonal.setToolTipText("Orthogonal Layout");
		btnLayoutOrthogonal.addActionListener(this::othogonalLayoutListener);
		toolBar.add(btnLayoutOrthogonal);
		
		toolBar.addSeparator();
		JButton btnSaveDiagram = new JButton("Save diagram");
		btnSaveDiagram.setToolTipText("Save diagram");
		btnSaveDiagram.addActionListener(this::saveDiagramListener);
		toolBar.add(btnSaveDiagram);

		// Additional layout to be added...
		return toolBar;
	}
	
	private void btnZoomListener( ActionEvent event ) {
		//event.getSource() == btnZoomIn

    }
	private void hierarchicalLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxHierarchicalLayout layout = new mxHierarchicalLayout(graph); 
		layout.execute(parent);
    }	
	private void othogonalLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxOrthogonalLayout layout = new mxOrthogonalLayout(graph); 
		layout.execute(parent);
    }
	
	private synchronized void saveDiagramListener( ActionEvent event ) {
		//graph.get
		this.setVisible(false);

		
		notifyAll();
    }

	public void setGraphData(mxGraph graphData) {
		// TODO Auto-generated method stub
		this.graph = graphData; 
		
	    mxGraphComponent graphComponent = new mxGraphComponent(graphData);
	    getContentPane().add(graphComponent);
		
	    this.setVisible(true);
	}

	public mxGraph getGraphModel() {
		// TODO Auto-generated method stub
		return graph;
	}
}
