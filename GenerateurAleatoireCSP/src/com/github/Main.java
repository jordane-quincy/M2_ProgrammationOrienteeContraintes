package com.github;

public class Main {

	public static void main(String[] args) {
		int nbVariables = 6;
		int tailleMaxDomaine = 6;
		int densite = 40;
		int durete = 50;
		int connectivite = 2;
		// CSP csp = new CSP(nbVariables, tailleMaxDomaine, densite, durete,
		// connectivite);
		CSP csp = new CSP(4);
		System.out.println(csp);
	}

}
