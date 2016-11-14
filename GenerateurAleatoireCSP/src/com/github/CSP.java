package com.github;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

		int nbMaxContraintes = nbVariables * (nbVariables - 1) / 2;
		int nbContraintes = densite * nbMaxContraintes / 100;
		this.lstContrainte = new ArrayList<Contrainte>();
		for (int i = 1; i <= nbVariables; i++) {
			for (int j = i + 1; j <= nbVariables; j++) {
				Variable var1 = lstVariable.get(i - 1);
				Variable var2 = lstVariable.get(j - 1);
				lstContrainte.add(new Contrainte(var1, var2, 0));
			}
		}

		while (lstContrainte.size() > nbContraintes) {
			int indexToRemove = generateRandomFrom0ToMax(lstContrainte.size());
			lstContrainte.remove(indexToRemove);
		}

		for (int i = 0; i < lstContrainte.size(); i++) {
			Contrainte contrainte = lstContrainte.get(i);
			contrainte.initMap(durete);
		}
	}

	@Override
	public String toString() {
		return "CSP [lstVariable=" + lstVariable + ", \nlstContrainte=" + lstContrainte + ", \nnbVariables="
				+ nbVariables + "]";
	}

	private int generateRandomFrom0ToMax(int max) {
		Random r = new Random();
		int min = 0;
		int result = r.nextInt(max - min) + min;
		return result;
	}

}
