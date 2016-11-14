package com.github;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Contrainte {

	Variable variable1;
	Variable variable2;

	Map<Integer, Integer> map;

	public Contrainte(Variable variable1, Variable variable2, int connectivite) {
		this.variable1 = variable1;
		this.variable2 = variable2;
		this.map = new HashMap<Integer, Integer>();
	}

	public Map<Integer, Integer> getMap() {
		return map;
	}

	public void initMap(int dureteMax) {
		// produit cartesien
		for (Integer var1 : variable1.getDomaine()) {
			for (Integer var2 : variable2.getDomaine()) {
				map.put(var1, var2);
			}
		}

		int nbCoupleMax = variable1.getDomaine().size() * variable2.getDomaine().size();
		int nbCouple = generateRandom(dureteMax, 100) * nbCoupleMax / 100;
		while (map.size() > nbCouple) {
			int indexToRemove = generateRandom(0, map.size() - 1);
			map.remove(indexToRemove);
		}

	}

	private int generateRandom(int min, int max) {
		Random r = new Random();
		int result = r.nextInt(max - min + 1) + min;
		return result;
	}

	@Override
	public String toString() {
		return "Contrainte [variable1=" + variable1 + ", variable2=" + variable2 + ", map=" + map + "]";
	}
}
