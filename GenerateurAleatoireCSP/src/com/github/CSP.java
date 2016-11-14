package com.github;

import java.util.ArrayList;
import java.util.List;

public class CSP {

	List<Variable> lstVariable = null;
	List<Contrainte> lstContrainte = null;
	int nbVariables;

	public CSP(int nbVariables, int tailleMaxDomaine, int densite, int durete) {
		this.nbVariables = nbVariables;

		this.lstVariable = new ArrayList<Variable>();
		for (int i = 1; i <= nbVariables; i++) {
			lstVariable.add(new Variable(i, tailleMaxDomaine));
		}

		this.lstContrainte = new ArrayList<Contrainte>();
	}

	@Override
	public String toString() {
		return "CSP [lstVariable=" + lstVariable + ", lstContrainte=" + lstContrainte + ", nbVariables=" + nbVariables
				+ "]";
	}

}
