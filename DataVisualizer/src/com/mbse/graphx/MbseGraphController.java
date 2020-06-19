package com.mbse.graphx;

import java.awt.event.ActionEvent;

import javax.swing.event.ChangeEvent;

import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.model.mxGraphModel;

public class MbseGraphController {

	private MbseModel model;
	private mxGraphLayout currentAppliedLayout;

	public MbseGraphController(MbseModel dataModel) {
		// TODO Auto-generated constructor stub
		this.model = dataModel;
	}

	public void setOperateur(ChangeEvent event) {
		System.out.println(event.getSource());
		//label.setText("Valeur actuelle : " + ((JSlider)event.getSource()).getValue());
		/*
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
		else if (currentAppliedLayout instanceof FunctionalBreakdownStructureLayout) {
			FunctionalBreakdownStructureLayout fbsLayout = (FunctionalBreakdownStructureLayout) currentAppliedLayout;
			fbsLayout.setLevelDistance(spacing);
			fbsLayout.execute(graph.getDefaultParent());
		}
		*/
		
	}

	public void setOperateur(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
