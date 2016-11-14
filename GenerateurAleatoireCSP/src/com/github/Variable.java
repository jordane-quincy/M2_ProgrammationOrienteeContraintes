package com.github;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Variable {

	String id = null;
	List<Integer> domaine = new ArrayList<Integer>();

	public Variable(int numero, int tailleMaxDomaine) {
		this.id = "X" + numero;

		int tailleDomaine = generateRandomFrom1ToMax(tailleMaxDomaine);

		this.domaine = new ArrayList<Integer>();
		for (int i = 1; i <= tailleMaxDomaine; i++) {
			domaine.add(i);
		}

		while (domaine.size() > tailleDomaine) {
			int indexToRemove = generateRandomFrom1ToMax(domaine.size()) - 1;
			domaine.remove(indexToRemove);
		}

	}

	private int generateRandomFrom1ToMax(int max) {
		Random r = new Random();
		int min = 1;
		int result = r.nextInt(max - min) + min;
		return result;
	}

	@Override
	public String toString() {
		return "Variable [id=" + id + ", domaine=" + domaine + "]";
	}

}
