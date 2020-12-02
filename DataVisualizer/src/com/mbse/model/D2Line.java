package com.mbse.model;

public class D2Line extends D2Element {

	protected String sourceGUID;
	
	protected String targetGUID;

	/**
	 * 
	 * @param GUID
	 * @param Label
	 * @param sourceGUID
	 * @param targetGUID
	 */
	public D2Line(String GUID, String Label, String sourceGUID, String targetGUID) {
		super(GUID, Label);
		
		this.sourceGUID = sourceGUID;
		this.targetGUID = targetGUID;
	}

	public String getSourceGUID() {
		return sourceGUID;
	}

	public void setSourceGUID(String sourceGUID) {
		this.sourceGUID = sourceGUID;
	}

	public String getTargetGUID() {
		return targetGUID;
	}

	public void setTargetGUID(String targetGUID) {
		this.targetGUID = targetGUID;
	}

}
