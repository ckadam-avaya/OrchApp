package com.avaya.oa.model;

public class Build {

	private String createdDate;

	private String buildName;
	
	public Build() {
		super();
	}

	public Build(String createdDate, String buildName) {
		super();
		this.createdDate = createdDate;
		this.buildName = buildName;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}


}
