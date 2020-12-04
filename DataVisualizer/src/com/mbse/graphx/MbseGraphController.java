package com.mbse.graphx;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import com.mbse.layout.MbseLayout;
import com.mbse.model.MbseModel;
import com.mxgraph.layout.mxGraphLayout;

public class MbseGraphController {

	private MbseModel model;
	private mxGraphLayout currentAppliedLayout;

	public MbseGraphController(MbseModel dataModel) {
		// TODO Auto-generated constructor stub
		this.model = dataModel;
		
		currentAppliedLayout = model.appliedLayout;
		
		model.setAppliedLayout(currentAppliedLayout);
		model.executeLayout();
		
	}

	/**
	 * Spacing is handled with JSlider (vertical and horizontal)
	 * @param event
	 */
	public void setSpacing(ChangeEvent event) {
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

	public synchronized void setOperateur(ActionEvent event) {
		JButton button = (JButton) event.getSource();

		notify();
		notifyAll();
		
		System.out.println("test");
		
	}


}
