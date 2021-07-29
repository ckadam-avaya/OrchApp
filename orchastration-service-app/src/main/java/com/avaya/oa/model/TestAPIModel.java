package com.avaya.oa.model;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

public class TestAPIModel<T> {

	//@NotNull
	private String methodType;

	private Map<String, String> headerJson = new LinkedHashMap<>();

	private Map<String, T> bodyJson = new LinkedHashMap<>();

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public Map<String, T> getBodyJson() {
		return bodyJson;
	}

	public void setBodyJson(Map<String, T> bodyJson) {
		this.bodyJson = bodyJson;
	}

	public Map<String, String> getHeaderJson() {
		return headerJson;
	}

	public void setHeaderJson(Map<String, String> headerJson) {
		this.headerJson = headerJson;
	}

}
