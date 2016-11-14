package com.github;

public class Main {

	public static void main(String[] args) {
		int nbVariables = 4;
		int tailleMaxDomaine = 6;
		int densite = 50;
		int durete = 50;
		int connectivite = 2;

		CSP csp = new CSP(nbVariables, tailleMaxDomaine, densite, durete, connectivite);

		System.out.println(csp);
	}

}
