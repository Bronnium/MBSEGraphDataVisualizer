package com.mbse.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.mbse.layout.MbseLayout;
import com.mbse.layout.RateauLayout;
import com.mbse.model.MbseGraphModel;
import com.mbse.view.MbseGraphView;

public class MbseGraphController {

	private MbseGraphView view;
	private MbseGraphModel model;
	private ActionListener actionListener;
	private ChangeListener changeListener;
	
	
	private String[] availableLayouts = new String[] {"Effective Java", "Head First Java",
            "Thinking in Java", "Java for Dummies"};
	//private LinkedList availableLayouts;

	public MbseGraphController(MbseGraphModel mbseGraphModel, MbseGraphView mbseGraphView) {
		model = mbseGraphModel;
		view = mbseGraphView;


		view.addMbseGraphComponent(model);
		addViewControls();
		for (String item : availableLayouts) {
			view.getLayoutSelection().addItem(item);
		}

	}

	public void displayView() {
		model.getAppliedLayout().execute(model.getDefaultParent());
		
		view.setVisible(true);
	}

	/**
	 * Allows to create all listeners and pass for the view.
	 */
	public void addViewControls() {
		actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {                  
				linkBtnAndLabel(actionEvent);
			}
		};                
		
		
		changeListener = new ChangeListener(){
			public void stateChanged(ChangeEvent event) {
				changedState(event);
			}
		};
		
		view.addInputControl(actionListener, changeListener);
	}

	private void changedState(ChangeEvent event) {
		System.out.println("change listnener triggered"+event.getSource());
		if (event.getSource() instanceof JSlider) {
			JSlider slider = (JSlider) event.getSource();
			
			
			// spacing value is applied only when slider is released
			if (slider.getValueIsAdjusting())
				return;
			
			int spacing = slider.getValue();
			
			if (slider.getName().equals("HorizontalSpacing")) 
			{
				//((MbseLayout) currentAppliedLayout).setHorizontalSpacing(spacing);
				model.getAppliedLayout().setHorizontalSpacing(spacing);

			}
			else // verticalSpacing
			{
				model.getAppliedLayout().setVerticalSpacing(spacing);
				//((MbseLayout) currentAppliedLayout).setVerticalSpacing(spacing);
			}
			
			model.getAppliedLayout().execute(model.getDefaultParent());
		}
	}
	
	private void linkBtnAndLabel(ActionEvent event){
		//model.incX();                
		//view.setText(Integer.toString(model.getX()));
		System.out.println("action listnener triggered"+event.getSource());
		
		if (event.getSource() instanceof JMenuItem) {
			JMenuItem leaf = (JMenuItem) event.getSource();
			RateauLayout layout = new RateauLayout();
			//model.setAppliedLayout(layout);
			model.getAppliedLayout().execute(model.getDefaultParent());
		}
		/*public void setSpacing(ChangeEvent event) {
			JSlider slider = (JSlider) event.getSource();
			
			// spacing value is applied only when slider is released
			if (slider.getValueIsAdjusting())
				return;
			
			int spacing = slider.getValue();
			
			if (slider.getName().equals("HorizontalSpacing")) 
			{
				((MbseLayout) currentAppliedLayout).setHorizontalSpacing(spacing);

			}
			else // verticalSpacing
			{
				((MbseLayout) currentAppliedLayout).setVerticalSpacing(spacing);
			}

			model.executeLayout();
			
		}
		*/
	} 

}
