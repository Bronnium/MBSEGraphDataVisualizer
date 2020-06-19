package com.mbse.graphx;

import java.awt.event.ActionEvent;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import com.mbse.graphx.layout.FunctionalBreakdownStructureLayout;
import com.mbse.graphx.layout.MbseLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.model.mxGraphModel;

public class MbseGraphController {

	private MbseModel model;
	private mxGraphLayout currentAppliedLayout;

	public MbseGraphController(MbseModel dataModel) {
		// TODO Auto-generated constructor stub
		this.model = dataModel;
		
		currentAppliedLayout = new FunctionalBreakdownStructureLayout(dataModel);
		
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
			((FunctionalBreakdownStructureLayout) currentAppliedLayout).setHorizontalSpacing(spacing);

		}
		else // verticalSpacing
		{
			((FunctionalBreakdownStructureLayout) currentAppliedLayout).setVerticalSpacing(spacing);
		}

		model.executeLayout();
		
	}

	public void setOperateur(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
