package com.mbse.layout;

import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.sun.beans.introspect.PropertyInfo.Name;

public class RateauLayout implements mxIGraphLayout, MbseLayout {

	@Override
	public void execute(Object parent) {
		
		System.out.println("Applying "+getClass().getName());
		System.out.println(parent);

		
	}

	@Override
	public void moveCell(Object arg0, double arg1, double arg2) {
		
		
	}

	@Override
	public void setHorizontalSpacing(int spacing) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVerticalSpacing(int spacing) {
		// TODO Auto-generated method stub
		
	}

}
