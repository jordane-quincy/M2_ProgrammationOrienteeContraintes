package com.github;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainResoudreCSP {

	public static void main(String[] args) {
		int nbVariables = 3;
		int tailleMaxDomaine = 4;
		int densite = 80;
		int durete = 50;
		int connectivite = 3;
		CSP csp = new CSP(nbVariables, tailleMaxDomaine, densite, durete, connectivite);
		System.out.println("On essaye de r�soudre le csp suivant :");
		System.out.println(csp);

		Map<String, List<Solution>> map = new HashMap<String, List<Solution>>();
		map.put("Back Tracking", Solver.resolveByBacktracking(csp));
		map.put("Back Jumping", Solver.resolveByBackJumping(csp));
		map.put("Forward Checking", Solver.resolveByForwardChecking(csp));

		for (String nomMethodeResolution : map.keySet()) {
			List<Solution> allSolutions = map.get(nomMethodeResolution);
			if (allSolutions.size() == 0) {
				System.out.println("Pas de solution pour ce csp");
			} else {
				System.out.println(
						"\nSolution par " + nomMethodeResolution + " : ( " + allSolutions.size() + " solutions)\n");
			}
			for (Solution solution : allSolutions) {
				System.out.println(solution);
			}
		}
		// System.out.println("*********************");
		//
		// List<Solution> allSolutionsByBT = Solver.resolveByBacktracking(csp);
		// if (allSolutionsByBT.size() == 0) {
		// System.out.println("Pas de solution pour ce csp");
		// } else {
		// System.out.println("\nSolution par BT : ( " + allSolutionsByBT.size()
		// + " solutions)\n");
		// }
		// for (int i = 0; i < allSolutionsByBT.size(); i++) {
		// System.out.println(allSolutionsByBT.get(i));
		// }
		//
		// List<Solution> allSolutionsByBJ = Solver.resolveByBackJumping(csp);
		// if (allSolutionsByBJ.size() == 0) {
		// System.out.println("Pas de solution pour ce csp");
		// } else {
		// System.out.println("\nSolution par BJ : (" + allSolutionsByBJ.size()
		// + " solutions)\n");
		// }
		// for (int i = 0; i < allSolutionsByBJ.size(); i++) {
		// System.out.println(allSolutionsByBJ.get(i));
		// }
		//
		// List<Solution> allSolutionsByFC =
		// Solver.resolveByForwardChecking(csp);
		// if (allSolutionsByFC.size() == 0) {
		// System.out.println("Pas de solution pour ce csp");
		// } else {
		// System.out.println("\nSolution par FC : (" + allSolutionsByFC.size()
		// + " solutions)\n");
		// }
		// for (int i = 0; i < allSolutionsByFC.size(); i++) {
		// System.out.println(allSolutionsByFC.get(i));
		// }

	}

}
