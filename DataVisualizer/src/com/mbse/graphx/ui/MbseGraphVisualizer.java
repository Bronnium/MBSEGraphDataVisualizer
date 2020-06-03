package com.mbse.graphx.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.mbse.graphx.connectors.RhapsodyConnector;
import com.mbse.graphx.layout.CallStackLayout;
import com.mbse.graphx.layout.ProductBreakdownStructureLayout;
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
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;
import com.mxgraph.view.mxLayoutManager;

/**
 * Main class for MBSE Graph Visualization
 * @author
 *
 */
public class MbseGraphVisualizer extends JFrame {

	private double zoom_scale = 1.0;
	private mxGraph graph;
	private JPanel contentPane;
	private JButton btnZoomIn;
	private JButton btnZoomOut;
	private JButton btnZoomFit;
	private mxGraphComponent graphComponent;
	public mxGraphLayout currentAppliedLayout;
	
	public int spacing=0;

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
	
	public MbseGraphVisualizer(String title) {
		super(title);
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
		contentPane = (JPanel) this.getContentPane();
		//contentPane.add( this.createToolBar(), BorderLayout.NORTH );

		contentPane.add( this.createSecondBar(), BorderLayout.EAST );
		
	}

	private Component createSecondBar() {
		// TODO Auto-generated method stub
		JToolBar toolBar = new JToolBar("Layout Properties",JToolBar.VERTICAL);
		
		// empeche la barre d'etre bougée
		toolBar.setFloatable(false);
		
		
		JSlider horizontalSpacingSlide = new JSlider(JSlider.HORIZONTAL, 0, 30, 15);
		horizontalSpacingSlide.setMaximum(100);
		horizontalSpacingSlide.setMinimum(0);
		horizontalSpacingSlide.setValue(30);
		horizontalSpacingSlide.setPaintTicks(true);
		horizontalSpacingSlide.setPaintLabels(true);
		horizontalSpacingSlide.setMinorTickSpacing(10);
		horizontalSpacingSlide.setMajorTickSpacing(20);
		horizontalSpacingSlide.addChangeListener(new ChangeListener(){
	      public void stateChanged(ChangeEvent event){
	        //label.setText("Valeur actuelle : " + ((JSlider)event.getSource()).getValue());
	    	  //System.out.println(((JSlider)event.getSource()).getValue());
	    	  spacing = ((JSlider)event.getSource()).getValue();
	    	  System.out.println("applied:" +currentAppliedLayout);
	    	  if (currentAppliedLayout instanceof ProductBreakdownStructureLayout) {
	    		  ProductBreakdownStructureLayout pbsLayout = (ProductBreakdownStructureLayout) currentAppliedLayout;
	    		  pbsLayout.setNodeDistance(spacing);
	    		  pbsLayout.execute(graph.getDefaultParent());
			}
	      }
	    }); 

		toolBar.add(horizontalSpacingSlide);
		
		JSlider verticalSpacingSlide = new JSlider(JSlider.HORIZONTAL, 0, 30, 15);
		verticalSpacingSlide.setMaximum(100);
		verticalSpacingSlide.setMinimum(0);
		verticalSpacingSlide.setValue(30);
		verticalSpacingSlide.setPaintTicks(true);
		verticalSpacingSlide.setPaintLabels(true);
		verticalSpacingSlide.setMinorTickSpacing(10);
		verticalSpacingSlide.setMajorTickSpacing(20);
		verticalSpacingSlide.addChangeListener(new ChangeListener(){
	      public void stateChanged(ChangeEvent event){
	        //label.setText("Valeur actuelle : " + ((JSlider)event.getSource()).getValue());
	    	  //System.out.println(((JSlider)event.getSource()).getValue());
	    	  spacing = ((JSlider)event.getSource()).getValue();
	    	  System.out.println("applied:" +currentAppliedLayout);
	    	  if (currentAppliedLayout instanceof ProductBreakdownStructureLayout) {
	    		  ProductBreakdownStructureLayout pbsLayout = (ProductBreakdownStructureLayout) currentAppliedLayout;
	    		  pbsLayout.setLevelDistance(spacing);
	    		  pbsLayout.execute(graph.getDefaultParent());
			}
	      }
	    }); 

		toolBar.add(verticalSpacingSlide);
		
		JButton btnSaveDiagram = new JButton("Save diagram");
		btnSaveDiagram.setText("<html><color=blue><b>Edit</b></font></html>");
		btnSaveDiagram.setBorderPainted(true);
		btnSaveDiagram.setBorder(BorderFactory.createLineBorder(Color.blue));
		//btnSaveDiagram.setToolTipText("Save diagram");
		btnSaveDiagram.addActionListener(this::saveDiagramListener);
		toolBar.add(btnSaveDiagram);
		
		return toolBar;
	}

	private Component createToolBar() {
		// La barre d'outils à proprement parler
		JToolBar toolBar = new JToolBar();

		// empeche la barre d'etre bougée
		toolBar.setFloatable(false);

		btnZoomIn = new JButton(new ImageIcon("icons/zoom_in.png"));
		btnZoomIn.setToolTipText("Zoom In");
		btnZoomIn.addActionListener(this::btnZoomListener);
		toolBar.add(btnZoomIn);

		btnZoomOut = new JButton(new ImageIcon("icons/zoom_out.png"));
		btnZoomOut.setToolTipText("Zoom Out");
		btnZoomOut.addActionListener(this::btnZoomListener);
		toolBar.add(btnZoomOut);

		btnZoomFit = new JButton( new ImageIcon("icons/zoom_fit.png") );
		btnZoomFit.setToolTipText("Fit Zoom");
		toolBar.add(btnZoomFit);

		//final mxGraphView view = graph.getView();

		toolBar.addSeparator(new Dimension(100, 10));

		// mxCircleLayout
		JButton btnLayoutCircle = new JButton(new ImageIcon("icons/circular.png"));
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




		return toolBar;
	}

	private void btnZoomListener( ActionEvent event ) {

		this.graphComponent.setCenterZoom(true);
		graphComponent.setKeepSelectionVisibleOnZoom(true);
		
		JButton selectedZoom = (JButton) event.getSource();
		if (selectedZoom.equals(btnZoomIn)) {
			zoom_scale+=0.1;
			//((mxGraphComponent) graph).zoomIn();
			graphComponent.zoomIn();

			//graphComponent.zoomTo(zoom_scale, graphComponent.isCenterZoom());
		}
		else if (selectedZoom.equals(btnZoomOut)) {
			zoom_scale-=0.1;

			graphComponent.zoomOut();
			//graphComponent.zoomTo(zoom_scale, graphComponent.isCenterZoom());
		}
		else if (selectedZoom.equals(btnZoomFit)) {
			//graphComponent.setZoomPolicy(mxGraphComponent.ZOOM_POLICY_PAGE);
			graphComponent.zoomActual();
			zoom_scale = 1;
		}
		else {
			new JOptionPane("Zoom not handled");
			return;
		}
	}

	private void circularLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		
		graph.getModel().beginUpdate();
		try
		{
			// Creates a layout algorithm to be used
			// with the graph
			final mxCircleLayout circleLayout = new mxCircleLayout(graph);
			currentAppliedLayout= circleLayout;
			circleLayout.setResetEdges(true);
			circleLayout.execute(parent);
			
		}
		finally
		{
			// Default values are 6, 1.5, 20
			mxMorphing morph = new mxMorphing(graphComponent, 20,1.2, 20);
			// tester avec (graph, 10, 1.7, 20);
						
			morph.addListener(mxEvent.DONE, new mxIEventListener()
			{

				public void invoke(Object sender, mxEventObject evt)
				{
					graph.getModel().endUpdate();
				}

			});

			morph.startAnimation();

		}
		
		
	}
	//compactTreeLayoutListener
	private void compactTreeLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxCompactTreeLayout layout = new mxCompactTreeLayout(graph);
		currentAppliedLayout= layout;
		layout.setHorizontal(true);
		layout.execute(parent);
	}

	private void edgeLabelLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxEdgeLabelLayout layout = new mxEdgeLabelLayout(graph); 
		currentAppliedLayout= layout;
		layout.execute(parent);
		graph.repaint();
	}
	private void fastOrganicLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxFastOrganicLayout layout = new mxFastOrganicLayout(graph); 
		currentAppliedLayout= layout;
		layout.execute(parent);
		graph.repaint();
	}

	private void organicLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxOrganicLayout layout = new mxOrganicLayout(graph);
		currentAppliedLayout= layout;
		layout.execute(parent);
		graph.repaint();
	}
	private void parralelEdgeLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxParallelEdgeLayout layout = new mxParallelEdgeLayout(graph); 
		currentAppliedLayout= layout;
		layout.execute(parent);
		graph.repaint();
	}
	private void partitionLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxPartitionLayout layout = new mxPartitionLayout(graph);
		currentAppliedLayout= layout;
		layout.execute(parent);
		graph.repaint();
	}
	private void stackLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();
		final mxStackLayout layout = new mxStackLayout(graph, false, 10, 10, 10, 10);
		currentAppliedLayout= layout;
		layout.execute(parent);
		graph.repaint();
	}
	private void hierarchicalLayoutListener( ActionEvent event ) {
		Object parent = graph.getDefaultParent();

		
		graph.getModel().beginUpdate();
		try
		{
			// Creates a layout algorithm to be used
			// with the graph
			final mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
			currentAppliedLayout= layout;
			//
			System.out.println(spacing);
			layout.execute(parent);
			
		}
		finally
		{
			// Default values are 6, 1.5, 20
			mxMorphing morph = new mxMorphing(graphComponent, 20,1.2, 20);
			// tester avec (graph, 10, 1.7, 20);
						
			morph.addListener(mxEvent.DONE, new mxIEventListener()
			{

				public void invoke(Object sender, mxEventObject evt)
				{
					graph.getModel().endUpdate();
				}

			});

			morph.startAnimation();

		}
		
		
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

	public mxGraphComponent setGraphData(mxGraph graphData) {
		// TODO Auto-generated method stub
		this.graph = graphData; 

		graphComponent = new mxGraphComponent(graphData);
		
		getContentPane().add(graphComponent);
		
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
		{
		
			public void mouseReleased(MouseEvent e)
			{
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());
				
				if (cell != null)
				{
					mxGeometry cellGeometry = graph.getCellGeometry(cell);
					System.out.println("cell="+graph.getLabel(cell));
					System.out.println("X:"+cellGeometry.getX()+"Y:"+cellGeometry.getY());
				}
			}
		});


		this.setVisible(true);
		return graphComponent;
	}

	public mxGraph getGraphModel() {
		// TODO Auto-generated method stub
		return graph;
	}

	public void connect(RhapsodyConnector rhapsodyConnector, Class<ProductBreakdownStructureLayout> class1) throws InstantiationException, IllegalAccessException {
		ProductBreakdownStructureLayout pbsLayout = class1.newInstance();
		
	}
}
