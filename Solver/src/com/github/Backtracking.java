package com.github;

import java.util.List;

public class Backtracking {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int nbVariables = 6;
		int tailleMaxDomaine = 5;
		int densite = 100;
		int durete = 100;
		int connectivite = 5;
		CSP csp = new CSP(nbVariables, tailleMaxDomaine, densite, durete, connectivite);
		System.out.println("On essaye de résoudre le csp suivant :");
		System.out.println(csp);
		List<Solution> allSolutions = Solver.resolveByBacktracking(csp);
		for (int i = 0; i < allSolutions.size(); i++) {
			System.out.println(allSolutions.get(i));
		}
		
	}

}
