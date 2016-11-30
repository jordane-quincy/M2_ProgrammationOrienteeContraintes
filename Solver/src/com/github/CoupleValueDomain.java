package com.github;

import java.util.ArrayList;
import java.util.List;

public class CoupleValueDomain {
	private int value;
	private List<Integer> domaine = new ArrayList<Integer>();
	
	public CoupleValueDomain (CoupleValueDomain cvd) {
		this.value = cvd.getValue();
		this.domaine = new ArrayList<Integer>(cvd.getDomaine());
	}
	
	public CoupleValueDomain(int value, List<Integer> domaine) {
		this.value = value;
		this.domaine = domaine;
	}

	@Override
	public String toString() {
		return "" + value;
	}

	public int getValue() {
		return value;
	}

	public List<Integer> getDomaine() {
		return domaine;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setDomaine(List<Integer> domaine) {
		this.domaine = domaine;
	}
	
	
	
}
