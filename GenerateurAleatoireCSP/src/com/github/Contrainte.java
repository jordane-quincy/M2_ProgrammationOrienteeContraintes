package com.github;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Contrainte {

	Variable variable1;
	Variable variable2;

	List<CoupleValeur> lstCouple;

	public Contrainte(Variable variable1, Variable variable2, int connectivite) {
		this.variable1 = variable1;
		this.variable2 = variable2;
		this.lstCouple = new ArrayList<CoupleValeur>();
	}

	public void initMap(int dureteMax) {
		// produit cartesien
		for (Integer var1 : variable1.getDomaine()) {
			for (Integer var2 : variable2.getDomaine()) {
				lstCouple.add(new CoupleValeur(var1, var2));
			}
		}

		// on retire pour obtenir le nombre de couple souhaite
		int nbCoupleMax = variable1.getDomaine().size() * variable2.getDomaine().size();
		int nbCouple = (int) Math.ceil((double) generateRandom(dureteMax, 100) * nbCoupleMax / 100);

		// System.err.println("nbCoupleMax :" + nbCoupleMax + ", dureteMax : " +
		// dureteMax + ", nbCouple : " + nbCouple
		// + ", lstCouple.size() :" + lstCouple.size());

		while (lstCouple.size() > nbCouple) {
			int indexToRemove = generateRandom(0, lstCouple.size() - 1);
			lstCouple.remove(indexToRemove);
		}

	}

	private int generateRandom(int min, int max) {
		Random r = new Random();
		int result = r.nextInt(max - min + 1) + min;
		return result;
	}

	@Override
	public String toString() {
		return "\n" + "Contrainte [" + variable1 + ", " + variable2 + " : " + lstCouple + "]";
	}

	public Variable getVariable1() {
		return variable1;
	}

	public Variable getVariable2() {
		return variable2;
	}
}
