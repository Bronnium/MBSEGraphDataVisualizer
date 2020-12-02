package com.mbse.model;

/**
 * 
 * @author lucas
 *
 */
public class D2Element {

	protected String guid;
	
	protected String label;
	
	public D2Element(String GUID, String Label) {
		this.guid = GUID;
		this.label = Label;
	}
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
