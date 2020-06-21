package com.mbse.graphx.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.mbse.graphx.MbseGraphController;
import com.mbse.graphx.MbseModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

/**
 * Main class for MBSE Graph Visualization
 * @author
 *
 */
public class MbseGraphVisualizerUI extends JFrame {

	private JPanel contentPane;

	private JButton btnZoomIn;
	private JButton btnZoomOut;
	private JButton btnZoomFit;
	private JSlider horizontalSpacingSlide, verticalSpacingSlide;
	
	protected mxGraphComponent graphComponent;

	// controller
	private MbseGraphController mbseGraphController;


	public MbseGraphVisualizerUI(MbseModel dataModel, MbseGraphController controller) {
		super("MBSE Graph Visualizer");
		// sets size
		this.setSize(800, 600);
		// centers the frame
		this.setLocationRelativeTo(null);
		// define behavior of close button
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.mbseGraphController = controller;
		this.graphComponent = new mxGraphComponent(dataModel);

		initUI();
		
		this.setVisible(true);
	}

	public MbseGraphVisualizerUI(MbseModel dataModel, MbseGraphController controller, String title) {

		super(title);
		// sets size
		this.setSize(800, 600);
		// centers the frame
		this.setLocationRelativeTo(null);
		// define behavior of close button
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.mbseGraphController = controller;
		

		this.setContentPane(this);

		initUI();

		this.setVisible(true);
	}

	private void initUI() {
		// Construction et injection de la barre d'outils
		contentPane = (JPanel) this.getContentPane();

		contentPane.add(createMainToolBar(), BorderLayout.NORTH);
		contentPane.add(createSecondaryToolBar(), BorderLayout.EAST);

		contentPane.add(graphComponent);

	}

	class MbseToolListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			mbseGraphController.setOperateur(e);
		}           
	}


	class MbseChangeListener implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent event) {
			mbseGraphController.setSpacing(event);
		}           
	}

	private Component createSecondaryToolBar() {
		JToolBar toolBar = new JToolBar("Layout Properties",JToolBar.VERTICAL);

		// empeche la barre d'etre bougée
		toolBar.setFloatable(false);

		//Nous utiliserons le même listener pour tous les opérateurs
		MbseChangeListener changeListener = new MbseChangeListener();
		MbseToolListener actionListener = new MbseToolListener();

		toolBar.add(new JLabel("Set horizontal spacing"));

		horizontalSpacingSlide = new JSlider(JSlider.HORIZONTAL,5,100,20);
		horizontalSpacingSlide.setPaintTicks(true);
		horizontalSpacingSlide.setPaintLabels(true);
		horizontalSpacingSlide.setMinorTickSpacing(10);
		horizontalSpacingSlide.setMajorTickSpacing(20);
		horizontalSpacingSlide.setName("HorizontalSpacing");
		horizontalSpacingSlide.addChangeListener(changeListener);
		toolBar.add(horizontalSpacingSlide);

		toolBar.add(new JLabel("Set vertical spacing"));

		verticalSpacingSlide = new JSlider(JSlider.HORIZONTAL,5,100,20);
		verticalSpacingSlide.setPaintTicks(true);
		verticalSpacingSlide.setPaintLabels(true);
		verticalSpacingSlide.setMinorTickSpacing(10);
		verticalSpacingSlide.setMajorTickSpacing(20);
		verticalSpacingSlide.setName("VerticalSpacing");
		verticalSpacingSlide.addChangeListener(changeListener);
		toolBar.add(verticalSpacingSlide);

		JButton btnSaveDiagram = new JButton("Save diagram");
		btnSaveDiagram.addActionListener(actionListener);
		toolBar.add(btnSaveDiagram);

		return toolBar;
	}

	private Component createMainToolBar() {
		// La barre d'outils à proprement parler
		JToolBar toolBar = new JToolBar();

		MbseToolListener actionListener = new MbseToolListener();

		// empeche la barre d'etre bougée
		toolBar.setFloatable(false);

		btnZoomIn = new JButton("ZoomIn", new ImageIcon("icons/zoom_in.png"));
		btnZoomIn.addActionListener(actionListener);
		toolBar.add(btnZoomIn);

		btnZoomOut = new JButton("ZoomOut", new ImageIcon("icons/zoom_out.png"));
		btnZoomOut.addActionListener(actionListener);
		toolBar.add(btnZoomOut);

		btnZoomFit = new JButton("ZoomFit", new ImageIcon("icons/zoom_fit.png"));
		btnZoomFit.addActionListener(actionListener);
		toolBar.add(btnZoomFit);

		return toolBar;
	}

	public void updateView(mxGraph graphData) {
		graphComponent.setGraph(graphData);
	}

}
