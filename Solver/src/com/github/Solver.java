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
		//proc�dure BT
		//d�but
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
				//Si on n'a pas trouv� de valeur compatible avec la solution courante
				//On reset le Domaine de la variable et on repasse � la variable pr�c�dente
				solForVarI.setDomaine(new ArrayList<Integer>(csp.getLstVariable().get(i - 1).getDomaine()));
				s.setValueToVariable(i, -1);
				i--;
			}
			else {
				if (i == csp.getNbVariables()) {
					//Dans ce cas on a une solution, il faut la save
					//Puis enlev� la valeur dans la solution en cours
					//pour continuer le parcours
					allSolutions.add(new Solution(s));
					s.setValueToVariable(i, -1);
				}
				else {
					i++;
				}
			}
		}
		//finProc�dure
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
				//On r�alise le backjumping
				solForVarI.setDomaine(new ArrayList<Integer>(csp.getLstVariable().get(i - 1).getDomaine()));
				s.setValueToVariable(i, -1);
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
					//Puis enlev� la valeur dans la solution en cours
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
			//Si la deuxi�me variable d'une contrainte est �gal � notre variable
			if (c.getVariable2().getIdInt() == variable) {
				//Alors la premi�re variable de la contrainte est un parent de notre variable
				//Ajout � la liste des contraintes
				if (plusProcheParent < c.getVariable1().getIdInt()) {
					plusProcheParent = c.getVariable1().getIdInt();
				}
			}
		}		
		return plusProcheParent;
	}
	
	public static List<Solution> resolveByForwardChecking(CSP csp) {
		//initialisation des solutions
		List<Solution> allSolutions = new ArrayList<Solution>();
		Solution s = new Solution(csp);
		//Declaration liste des domaines qu'on doit garder pour revenir en arri�re
		Map<Integer, List<CoupleValueDomain>> savedDomaines = new HashMap<Integer, List<CoupleValueDomain>>();
		
		int i = 1;
		while (i >= 1 && i <= csp.getNbVariables()) {
			boolean ok = false;
			int x;
			CoupleValueDomain solForVarI = s.getSolution().get(i);
			List<Integer> domaine = solForVarI.getDomaine();
			while (!ok && domaine.size() > 0) {
				x = domaine.get(0);
				domaine.remove(0);
				//save the domain for the future
				savedDomaines = saveCurrentDomain(i, savedDomaines, s, csp);			
				s.setValueToVariable(i, x);
				boolean domaineVide = false;
				for (int k = i + 1; k <= csp.getNbVariables(); k++) {
					if (!domaineVide) {	
						//On revise les domaines de d�finition de k
						CoupleValueDomain solForVarK = s.getSolution().get(k);
						List<Integer> domaineDeK = solForVarK.getDomaine();					
						for (int h = domaineDeK.size() - 1; h >= 0; h--) {
							int y = domaineDeK.get(h);
							if (!s.authorizedValueForVariable(k, y)) {
								domaineDeK.remove(h);
							}
						}
						if (domaineDeK.size() == 0) {
							domaineVide = true;
						}
					}
				}
				if (domaineVide) {
					//on restaure les Dk
					for (int k = i + 1; k <= csp.getNbVariables(); k++) {
						CoupleValueDomain solForVarK = s.getSolution().get(k);
						CoupleValueDomain previousSolForVarK = savedDomaines.get(i).get(k - 1);
						solForVarK.setDomaine(new CoupleValueDomain(previousSolForVarK).getDomaine());
					}
				}
				else {
					ok = true;
				}
			}
			if (!ok) {
				//backtrack
				//reset des Domaines si i > 1
				if (i > 1) {
					for (int k = 1; k <= csp.getNbVariables(); k++) {
						CoupleValueDomain solForVarK = s.getSolution().get(k);
						CoupleValueDomain previousSolForVarK = savedDomaines.get(i - 1).get(k - 1);
						solForVarK.setDomaine(new CoupleValueDomain(previousSolForVarK).getDomaine());
					}
				}				
				s.setValueToVariable(i, -1);
				i--;
			}
			else {
				if (i == csp.getNbVariables()) {
					//Dans ce cas on a une solution, il faut la save
					//Puis enlev� la valeur dans la solution en cours
					//pour continuer le parcours
					allSolutions.add(new Solution(s));
					s.setValueToVariable(i, -1);
				}
				else {
					//step forward
					i++;
				}
			}
		}
		return allSolutions;		
	}

	private static Map<Integer, List<CoupleValueDomain>> saveCurrentDomain(int i,
			Map<Integer, List<CoupleValueDomain>> savedDomaines, Solution s, CSP csp) {
		
		List<CoupleValueDomain> domaines = new ArrayList<CoupleValueDomain>();
		for (int j = 1; j <= csp.getNbVariables(); j++) {
			//On r�cup�re tous les domaine actuels			
			domaines.add(new CoupleValueDomain(s.getSolution().get(j)));
			
			
		}
		//On les ajoutes dans nos savedDomaines
		savedDomaines.put(i, domaines);
		
		return savedDomaines;
	}	
	
}
