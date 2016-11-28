package com.github;

public class Backtracking {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int nbVariables = 6;
		int tailleMaxDomaine = 6;
		int densite = 40;
		int durete = 50;
		int connectivite = 2;
		CSP csp = new CSP(nbVariables, tailleMaxDomaine, densite, durete, connectivite);
		Solution s = new Solution(csp);
		System.out.println(csp);
		System.out.println(s.authorizedValueForVariable(csp.lstVariable.get(0), 1));
		
	}

}
