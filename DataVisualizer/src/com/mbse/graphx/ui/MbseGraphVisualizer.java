package com.mbse.graphx.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.mbse.graphx.connectors.RhapsodyConnector;
import com.mbse.graphx.layout.CallStackLayout;
import com.mbse.graphx.layout.FunctionalBehaviorLayout;
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
		contentPane.add( this.createToolBar(), BorderLayout.NORTH );

		contentPane.add( this.createSecondBar(), BorderLayout.EAST );

	}

	private Component createSecondBar() {
		// TODO Auto-generated method stub
		JToolBar toolBar = new JToolBar("Layout Properties",JToolBar.VERTICAL);

		// empeche la barre d'etre bougée
		toolBar.setFloatable(false);

		toolBar.add(new JLabel("Set horizontal spacing"));
		JSlider horizontalSpacingSlide = new JSlider(JSlider.HORIZONTAL, 0, 30, 15);
		horizontalSpacingSlide.setMaximum(100);
		horizontalSpacingSlide.setMinimum(0);
		horizontalSpacingSlide.setValue(20);
		horizontalSpacingSlide.setPaintTicks(true);
		horizontalSpacingSlide.setPaintLabels(true);
		horizontalSpacingSlide.setMinorTickSpacing(10);
		horizontalSpacingSlide.setMajorTickSpacing(20);
		horizontalSpacingSlide.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent event){
				//label.setText("Valeur actuelle : " + ((JSlider)event.getSource()).getValue());
				if (horizontalSpacingSlide.getValueIsAdjusting()) {
		            return;
		        }
				spacing = ((JSlider)event.getSource()).getValue();
				if (currentAppliedLayout instanceof ProductBreakdownStructureLayout) {
					ProductBreakdownStructureLayout pbsLayout = (ProductBreakdownStructureLayout) currentAppliedLayout;
					pbsLayout.setNodeDistance(spacing);
					pbsLayout.execute(graph.getDefaultParent());
				}
				else if (currentAppliedLayout instanceof FunctionalBehaviorLayout) {
					FunctionalBehaviorLayout fbdLayout = (FunctionalBehaviorLayout) currentAppliedLayout;
					fbdLayout.setInterRankCellSpacing(spacing);
					//fbdLayout.set
					fbdLayout.execute(graph.getDefaultParent());
				}
			}
		}); 

		toolBar.add(horizontalSpacingSlide);

		toolBar.add(new JLabel("Set vertical spacing"));
		JSlider verticalSpacingSlide = new JSlider(JSlider.HORIZONTAL, 0, 30, 15);
		verticalSpacingSlide.setMaximum(100);
		verticalSpacingSlide.setMinimum(0);
		verticalSpacingSlide.setValue(10);
		verticalSpacingSlide.setPaintTicks(true);
		verticalSpacingSlide.setPaintLabels(true);
		verticalSpacingSlide.setMinorTickSpacing(10);
		verticalSpacingSlide.setMajorTickSpacing(20);
		verticalSpacingSlide.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent event){
				//label.setText("Valeur actuelle : " + ((JSlider)event.getSource()).getValue());
				if (verticalSpacingSlide.getValueIsAdjusting()) {
		            return;
		        }
				spacing = ((JSlider)event.getSource()).getValue();
				if (currentAppliedLayout instanceof ProductBreakdownStructureLayout) {
					ProductBreakdownStructureLayout pbsLayout = (ProductBreakdownStructureLayout) currentAppliedLayout;
					pbsLayout.setLevelDistance(spacing);
					pbsLayout.execute(graph.getDefaultParent());
				}
				else if (currentAppliedLayout instanceof FunctionalBehaviorLayout) {
					FunctionalBehaviorLayout fbdLayout = (FunctionalBehaviorLayout) currentAppliedLayout;
					//fbdLayout.setInterHierarchySpacing(spacing); // don't work
					fbdLayout.setIntraCellSpacing(spacing);
					fbdLayout.execute(graph.getDefaultParent());
				}
			}
		}); 

		toolBar.add(verticalSpacingSlide);

		JCheckBox checkbox = new JCheckBox("Display last children over");
		checkbox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				JCheckBox cbLog = (JCheckBox) e.getSource();
				if (currentAppliedLayout instanceof ProductBreakdownStructureLayout) {
					ProductBreakdownStructureLayout pbsLayout = (ProductBreakdownStructureLayout) currentAppliedLayout;
					
					pbsLayout.setLeafOver(cbLog.isSelected());
					pbsLayout.execute(graph.getDefaultParent());
				}

			}
		});
		toolBar.add(checkbox);

		JButton btnSaveDiagram = new JButton("Save diagram");
		//btnSaveDiagram.setText("<html><color=black><b>Save diagram</b></font></html>");
		//btnSaveDiagram.setBorderPainted(true);
		//btnSaveDiagram.setBorder(BorderFactory.createLineBorder(Color.blue));
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

		btnZoomFit = new JButton(new ImageIcon("icons/zoom_fit.png"));
		btnZoomFit.setToolTipText("Fit Zoom");
		btnZoomFit.addActionListener(this::btnZoomListener);
		toolBar.add(btnZoomFit);

		return toolBar;
	}

	private void btnZoomListener( ActionEvent event ) {

		this.graphComponent.setCenterZoom(true);
		graphComponent.setKeepSelectionVisibleOnZoom(true);
		graphComponent.setCenterPage(true);

		JButton selectedZoom = (JButton) event.getSource();
		if (selectedZoom.equals(btnZoomIn)) {
			graphComponent.zoomIn();
		}
		else if (selectedZoom.equals(btnZoomOut)) {
			graphComponent.zoomOut();
		}
		else if (selectedZoom.equals(btnZoomFit)) {
			graphComponent.zoomActual();
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
					System.out.println("X:"+cellGeometry.getX()+"Y:"+cellGeometry.getY()+"/"+graph.getEdges(cell).length);
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
