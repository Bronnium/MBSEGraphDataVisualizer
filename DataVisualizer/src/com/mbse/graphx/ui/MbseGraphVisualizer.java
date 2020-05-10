package com.mbse.graphx.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxEdgeLabelLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.layout.mxPartitionLayout;
import com.mxgraph.layout.mxStackLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.orthogonal.mxOrthogonalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxLayoutManager;

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
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

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

		JButton btnZoomIn = new JButton(new ImageIcon("icons/zoom_in.png"));
		btnZoomIn.setToolTipText("Zoom In");
		btnZoomIn.addActionListener(this::btnZoomListener);
		toolBar.add(btnZoomIn);

		JButton btnZoomOut = new JButton(new ImageIcon("icons/zoom_out.png"));
		btnZoomOut.setToolTipText("Zoom Out");
		toolBar.add(btnZoomOut);

		JButton btnZoomFit = new JButton( new ImageIcon("icons/zoom_fit.png") );
		btnZoomFit.setToolTipText("Fit Zoom");
		toolBar.add(btnZoomFit);

		toolBar.addSeparator(new Dimension(100, 10));

		// mxCircleLayout
		JButton btnLayoutCircle = new JButton(new ImageIcon("icons/circle.png"));
		btnLayoutCircle.setToolTipText("Circular Layout");
		btnLayoutCircle.addActionListener(this::circularLayoutListener);
		toolBar.add(btnLayoutCircle);
		
		//mxCompactTreeLayout
		JButton btnLayoutCompactTree = new JButton(new ImageIcon("icons/circle.png"));
		btnLayoutCompactTree.setToolTipText("Compact Tree Layout");
		btnLayoutCompactTree.addActionListener(this::compactTreeLayoutListener);
		toolBar.add(btnLayoutCompactTree);
		
		//mxEdgeLabelLayout
		// TODO: ne fonctionne pas
		JButton btnLayoutEdgeLabel = new JButton(new ImageIcon("icons/circle.png"));
		btnLayoutEdgeLabel.setToolTipText("Edge Label Layout");
		btnLayoutEdgeLabel.addActionListener(this::edgeLabelLayoutListener);
		toolBar.add(btnLayoutEdgeLabel);
		
		//mxFastOrganicLayout
		JButton btnLayoutFastOrganic = new JButton(new ImageIcon("icons/circle.png"));
		btnLayoutFastOrganic.setToolTipText("Fast Organic Layout");
		btnLayoutFastOrganic.addActionListener(this::fastOrganicLayoutListener);
		toolBar.add(btnLayoutFastOrganic);
		
		//mxOrganicLayout
		JButton btnLayoutOrganic = new JButton(new ImageIcon("icons/circle.png"));
		btnLayoutOrganic.setToolTipText("Organic Layout");
		btnLayoutOrganic.addActionListener(this::organicLayoutListener);
		toolBar.add(btnLayoutOrganic);
		
		//mxParralelEdgeLayout
		// TODO: ne fonctionne pas
		JButton btnLayoutParralelEdge = new JButton(new ImageIcon("icons/circle.png"));
		btnLayoutParralelEdge.setToolTipText("Parralel Edge Layout");
		btnLayoutParralelEdge.addActionListener(this::parralelEdgeLayoutListener);
		toolBar.add(btnLayoutParralelEdge);
		
		//mxPartitionLayout
		// TODO: ne fonctionne pas
		JButton btnLayoutPartition = new JButton(new ImageIcon("icons/circle.png"));
		btnLayoutPartition.setToolTipText("Partition Layout");
		btnLayoutPartition.addActionListener(this::partitionLayoutListener);
		toolBar.add(btnLayoutPartition);
		
		//mxStackLayout
		// TODO: fonctionne mais pas espacé
		JButton btnLayoutStack = new JButton(new ImageIcon("icons/circle.png"));
		btnLayoutStack.setToolTipText("Stack Layout");
		btnLayoutStack.addActionListener(this::stackLayoutListener);
		toolBar.add(btnLayoutStack);
		
		//mxHierarchicalLayout
		JButton btnLayoutHierachical = new JButton( new ImageIcon("icons/structured.png"));
		btnLayoutHierachical.setToolTipText("Hierarchical Layout");
		btnLayoutHierachical.addActionListener(this::hierarchicalLayoutListener);
		toolBar.add(btnLayoutHierachical);
		
		//mxOrthogonalLayout
		// TODO: ne fonctionne pas
		JButton btnLayoutOrthogonal = new JButton(new ImageIcon("icons/org_unit.png"));
		btnLayoutOrthogonal.setToolTipText("Orthogonal Layout");
		btnLayoutOrthogonal.addActionListener(this::othogonalLayoutListener);
		toolBar.add(btnLayoutOrthogonal);
		

		
		// Additional layout to be added...
		
		toolBar.addSeparator(new Dimension(100, 10));
		
		JButton btnSaveDiagram = new JButton("Save diagram");
		btnSaveDiagram.setToolTipText("Save diagram");
		btnSaveDiagram.addActionListener(this::saveDiagramListener);
		toolBar.add(btnSaveDiagram);

		
		return toolBar;
	}
	
	private void btnZoomListener( ActionEvent event ) {
		
    }
	private void circularLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxCircleLayout layout = new mxCircleLayout(graph); 
		layout.execute(parent);
		graph.repaint();
    }
	//compactTreeLayoutListener
	private void compactTreeLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxCompactTreeLayout layout = new mxCompactTreeLayout(graph); 
		layout.execute(parent);
		graph.repaint();
    }
	
	private void edgeLabelLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxEdgeLabelLayout layout = new mxEdgeLabelLayout(graph); 
		layout.execute(parent);
		graph.repaint();
    }
	private void fastOrganicLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxFastOrganicLayout layout = new mxFastOrganicLayout(graph); 
		layout.execute(parent);
		graph.repaint();
    }

	private void organicLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxOrganicLayout layout = new mxOrganicLayout(graph); 
		layout.execute(parent);
		graph.repaint();
    }
	private void parralelEdgeLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxParallelEdgeLayout layout = new mxParallelEdgeLayout(graph); 
		layout.execute(parent);
		graph.repaint();
    }
	private void partitionLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxPartitionLayout layout = new mxPartitionLayout(graph); 
		layout.execute(parent);
		graph.repaint();
    }
	private void stackLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxStackLayout layout = new mxStackLayout(graph); 
		layout.execute(parent);
		graph.repaint();
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
