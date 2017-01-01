package com.github;

import java.util.ArrayList;
import java.util.List;

public class MainResoudreCSP {

	public static void main(String[] args) {
		int nbVariables = 10;
		int tailleMaxDomaine = 10;
		int densite = 50;
		int durete = 60;
		int connectivite = 10;
		//Pour les n reines ici 8 reines
		CSP csp = new CSP(8); 
		//Pour un CSP aléatoire
		//CSP csp = new CSP(nbVariables, tailleMaxDomaine, densite, durete, connectivite);
		System.out.println("Résolution du csp :");
		//System.out.println(csp);
		long startTime;
		long endTime;
		
		List<Solution> allSolutions = new ArrayList<Solution>();
		List<Integer> nbrOfVisitedNoeud = new ArrayList<Integer>();
		nbrOfVisitedNoeud.add(0);
		System.out.println("**********************************************************");
		System.out.println("******** Partie Recherche de toutes les solutions ********");
		System.out.println("**********************************************************");

		//All solution BT
		System.out.println("Recherche de solution avec le BackTracking en cours...");
		startTime = System.currentTimeMillis();
		allSolutions = Solver.resolveByBacktracking(csp, nbrOfVisitedNoeud);
		endTime = System.currentTimeMillis();
		if (allSolutions.size() == 0) {
			System.out.println("Pas de solution pour ce csp (" + nbrOfVisitedNoeud.get(0) + " noeuds) " + (endTime - startTime) + " ms");
		} else {
			System.out.println(						
					"\nSolutions trouvées par BackTracking en " + (endTime - startTime) + " millisecondes et en parcourant " + nbrOfVisitedNoeud.get(0) + " noeuds ( " + allSolutions.size() + " solutions)\n");
		}		
		allSolutions.clear();
		nbrOfVisitedNoeud.set(0, 0);
		
		//All solutions BJ
		System.out.println("Recherche de solution avec le BackJumping en cours...");
		startTime = System.currentTimeMillis();
		allSolutions = Solver.resolveByBackJumping(csp, nbrOfVisitedNoeud);
		endTime = System.currentTimeMillis();
		if (allSolutions.size() == 0) {
			System.out.println("Pas de solution pour ce csp (" + nbrOfVisitedNoeud.get(0) + " noeuds) " + (endTime - startTime) + " ms");
		} else {
			System.out.println(						
					"\nSolutions trouvées par BackJumping en " + (endTime - startTime) + " millisecondes et en parcourant " + nbrOfVisitedNoeud.get(0) + " noeuds ( " + allSolutions.size() + " solutions)\n");
		}
		allSolutions.clear();
		nbrOfVisitedNoeud.set(0, 0);
				
		
		//All solutions FC
		System.out.println("Recherche de solution avec le ForwardChecking en cours...");
		startTime = System.currentTimeMillis();
		allSolutions = Solver.resolveByForwardChecking(csp, nbrOfVisitedNoeud);
		endTime = System.currentTimeMillis();
		if (allSolutions.size() == 0) {
			System.out.println("Pas de solution pour ce csp (" + nbrOfVisitedNoeud.get(0) + " noeuds) " + (endTime - startTime) + " ms");
		} else {
			System.out.println(						
					"\nSolutions trouvées par ForwardChecking en " + (endTime - startTime) + " millisecondes et en parcourant " + nbrOfVisitedNoeud.get(0) + " noeuds ( " + allSolutions.size() + " solutions)\n");
		}
		allSolutions.clear();
		nbrOfVisitedNoeud.set(0, 0);
		
		System.out.println("**********************************************************");
		System.out.println("******** Partie Recherche de la première solution ********");
		System.out.println("**********************************************************");
		
		//ONE solution BT
		System.out.println("Recherche d'une solution avec le BackTracking en cours...");
		startTime = System.currentTimeMillis();
		allSolutions = Solver.resolveFirstByBacktracking(csp, nbrOfVisitedNoeud);
		endTime = System.currentTimeMillis();
		if (allSolutions.size() == 0) {
			System.out.println("Pas de solution pour ce csp (" + nbrOfVisitedNoeud.get(0) + " noeuds) " + (endTime - startTime) + " ms");
		} else {
			System.out.println(						
					"\nSolution trouvée par BackTracking en " + (endTime - startTime) + " millisecondes et en parcourant " + nbrOfVisitedNoeud.get(0) + " noeuds ( " + allSolutions.size() + " solutions)\n");
			for (Solution solution : allSolutions) {
				System.out.println(solution);
			}
		}		
		allSolutions.clear();
		nbrOfVisitedNoeud.set(0, 0);
		
		//ONE solution BJ
		System.out.println("Recherche d'une solution avec le BackJumping en cours...");
		startTime = System.currentTimeMillis();
		allSolutions = Solver.resolveFirstByBackJumping(csp, nbrOfVisitedNoeud);
		endTime = System.currentTimeMillis();
		if (allSolutions.size() == 0) {
			System.out.println("Pas de solution pour ce csp (" + nbrOfVisitedNoeud.get(0) + " noeuds) " + (endTime - startTime) + " ms");
		} else {
			System.out.println(						
					"\nSolution trouvée par BackJumping en " + (endTime - startTime) + " millisecondes et en parcourant " + nbrOfVisitedNoeud.get(0) + " noeuds ( " + allSolutions.size() + " solutions)\n");
			for (Solution solution : allSolutions) {
				System.out.println(solution);
			}
		}
		allSolutions.clear();
		nbrOfVisitedNoeud.set(0, 0);
		
		
		
		
		//ONE solution FW
		System.out.println("Recherche d'une solution avec le ForwardChecking en cours...");
		startTime = System.currentTimeMillis();
		allSolutions = Solver.resolveFirstByForwardChecking(csp, nbrOfVisitedNoeud);
		endTime = System.currentTimeMillis();
		if (allSolutions.size() == 0) {
			System.out.println("Pas de solution pour ce csp (" + nbrOfVisitedNoeud.get(0) + " noeuds) en " + (endTime - startTime) + " ms");
		} else {
			System.out.println(						
					"\nSolution trouvée par ForwardChecking en " + (endTime - startTime) + " millisecondes et en parcourant " + nbrOfVisitedNoeud.get(0) + " noeuds ( " + allSolutions.size() + " solutions)\n");
			for (Solution solution : allSolutions) {
				System.out.println(solution);
			}
		}
		allSolutions.clear();
		nbrOfVisitedNoeud.set(0, 0);
	}

}
