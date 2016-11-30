package com.github;

public class Backtracking {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int nbVariables = 6;
		int tailleMaxDomaine = 5;
		int densite = 40;
		int durete = 50;
		int connectivite = 2;
		CSP csp = new CSP(nbVariables, tailleMaxDomaine, densite, durete, connectivite);
		System.out.println("On essaye de résoudre le csp suivant :");
		System.out.println(csp);
		System.out.println(Solver.resolveByBacktracking(csp));
		
	}

}
