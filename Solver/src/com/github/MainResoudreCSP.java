package com.github;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainResoudreCSP {

	public static void main(String[] args) {
		int nbVariables = 3;
		int tailleMaxDomaine = 4;
		int densite = 80;
		int durete = 50;
		int connectivite = 3;
		CSP csp = new CSP(4);// CSP(nbVariables, tailleMaxDomaine, densite,
								// durete, connectivite);
		System.out.println("On essaye de résoudre le csp suivant :");
		System.out.println(csp);

		Map<String, List<Solution>> map = new LinkedHashMap<String, List<Solution>>();
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

	}

}
