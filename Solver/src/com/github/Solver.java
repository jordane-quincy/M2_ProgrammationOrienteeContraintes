package com.github;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solver {
	
	public static List<Solution> resolveByBacktracking (CSP csp) {
		List<Solution> allSolutions = new ArrayList<Solution>();
		Solution s = new Solution(csp);
		//System.out.println(csp);
		//procédure BT
		//début
		int i = 1;
		while (i <= csp.getNbVariables() && i >= 1) {
			int x;
			boolean ok = false;
			CoupleValueDomain solForVarI = s.getSolution().get(i);
			List<Integer> domaine = solForVarI.getDomaine();
			while (!ok && domaine.size() > 0) {
				x = domaine.get(0);
				domaine.remove(0);
				solForVarI.setDomaine(domaine);
				if (s.authorizedValueForVariable(i, x)) {
					s.setValueToVariable(i, x);
					ok = true;
				}				
			}
			if (!ok) {
				//Si on n'a pas trouvé de valeur compatible avec la solution courante
				//On reset le Domaine de la variable et on repasse à la variable précédente
				solForVarI.setDomaine(new ArrayList<Integer>(csp.getLstVariable().get(i - 1).getDomaine()));
				i--;
			}
			else {
				if (i == csp.getNbVariables()) {
					//Dans ce cas on a une solution, il faut la save
					//Puis enlevé la valeur dans la solution en cours
					//pour continuer le parcours
					allSolutions.add(new Solution(s));
					s.setValueToVariable(i, -1);
				}
				else {
					i++;
				}
			}
		}
		//finProcédure
		return allSolutions;
	}

	public static List<Solution> resolveByBackJumping (CSP csp) {	
		//initialisation des solutions
		List<Solution> allSolutions = new ArrayList<Solution>();
		
		Map<Integer, Integer> parentsDesVariables = new HashMap<Integer, Integer>();
		
		//Calcul des parents de toutes les variables
		for (int i = 1; i <= csp.nbVariables; i++) {
			parentsDesVariables.put(i, getPlusProcheParents(i, csp));
		}
				
		int i = 1;
		Solution s = new Solution(csp);
		
		int plusProcheParentDeI;
		
		boolean solutionWasFound = false;
		
		while (i >= 1 && i <= csp.getNbVariables()) {
			plusProcheParentDeI = parentsDesVariables.get(i);
			int x;
			boolean ok = false;
			CoupleValueDomain solForVarI = s.getSolution().get(i);
			List<Integer> domaine = solForVarI.getDomaine();
			while (!ok && domaine.size() > 0) {
				x = domaine.get(0);
				domaine.remove(0);
				solForVarI.setDomaine(domaine);
				if (s.authorizedValueForVariable(i, x)) {
					s.setValueToVariable(i, x);
					ok = true;
				}	
			}
			if (!ok) {
				//On réalise le backjumping
				solForVarI.setDomaine(new ArrayList<Integer>(csp.getLstVariable().get(i - 1).getDomaine()));
				if (solutionWasFound){
					solutionWasFound = false;
					i--;
				}
				else {
					i = plusProcheParentDeI;
				}
			}
			else {
				if (i == csp.getNbVariables()) {
					//Dans ce cas on a une solution, il faut la save
					//Puis enlevé la valeur dans la solution en cours
					//pour continuer le parcours
					allSolutions.add(new Solution(s));
					s.setValueToVariable(i, -1);
					solutionWasFound = true;
				}
				else {
					i++;
				}
			}
		}		
		return allSolutions;
	}
	
	public static Integer getPlusProcheParents (int variable, CSP csp) {
		int plusProcheParent = -1;
		//On parcours toutes les contraintes
		for (Contrainte c : csp.getLstContrainte()) {
			//Si la deuxième variable d'une contrainte est égal à notre variable
			if (c.getVariable2().getIdInt() == variable) {
				//Alors la première variable de la contrainte est un parent de notre variable
				//Ajout à la liste des contraintes
				if (plusProcheParent < c.getVariable1().getIdInt()) {
					plusProcheParent = c.getVariable1().getIdInt();
				}
			}
		}		
		return plusProcheParent;
	}
	
}
