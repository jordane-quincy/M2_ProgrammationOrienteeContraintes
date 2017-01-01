package com.github;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solver {
	
	public static List<Solution> resolveByBacktracking (CSP csp, List<Integer> nbrOfVisitedNoeud) {
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
			nbrOfVisitedNoeud.set(0, nbrOfVisitedNoeud.get(0) + 1);
			if (!ok) {
				//Si on n'a pas trouvé de valeur compatible avec la solution courante
				//On reset le Domaine de la variable et on repasse à la variable précédente
				solForVarI.setDomaine(new ArrayList<Integer>(csp.getLstVariable().get(i - 1).getDomaine()));
				s.setValueToVariable(i, -1);
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
	
	public static List<Solution> resolveFirstByBacktracking (CSP csp, List<Integer> nbrOfVisitedNoeud) {
		List<Solution> firstSolution = new ArrayList<Solution>();
		Solution s = new Solution(csp);
		//System.out.println(csp);
		//procédure BT
		//début
		int i = 1;
		boolean firstSolutionFoud = false;
		while (i <= csp.getNbVariables() && i >= 1 && !firstSolutionFoud) {
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
			nbrOfVisitedNoeud.set(0, nbrOfVisitedNoeud.get(0) + 1);
			if (!ok) {
				//Si on n'a pas trouvé de valeur compatible avec la solution courante
				//on repasse à la variable précédente
				s.setValueToVariable(i, -1);
				i--;
			}
			else {
				if (i == csp.getNbVariables()) {
					//Dans ce cas on a une solution, il faut la save
					//Puis enlevé la valeur dans la solution en cours
					//pour continuer le parcours
					firstSolution.add(new Solution(s));
					firstSolutionFoud = true;
				}
				else {
					i++;
					s.getSolution().get(i).setDomaine(new ArrayList<Integer>(csp.getLstVariable().get(i - 1).getDomaine()));
				}
			}
		}
		//finProcédure
		return firstSolution;
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
	
	
	private static List<Integer> getAllParents (int variable, CSP csp) {
		List<Integer> parents = new ArrayList<Integer>();
		//On parcours toutes les contraintes
		for (Contrainte c : csp.getLstContrainte()) {
			//Si la deuxième variable d'une contrainte est égal à notre variable
			if (c.getVariable2().getIdInt() == variable) {
				//Alors la première variable de la contrainte est un parent de notre variable
				//Ajout à la liste des contraintes
				parents.add(c.getVariable1().getIdInt());
			}
		}	
		Collections.sort(parents);
		return parents;
	}	
	
	private static Integer getNearestParent(List<Integer> parents) {
		int nearestParent = 0;
		if (parents.size() > 0) {
			nearestParent = parents.get(parents.size() - 1);
		}
		return nearestParent;
	}
	
	private static List<Integer> unionOfParentsWithoutNewI(List<Integer> parents1, List<Integer> parents2, int newI) {
		List<Integer> unionParents = new ArrayList<Integer>(parents2);
		for (int i = 0; i < parents1.size(); i++) {
			if (parents1.get(i) != newI && !unionParents.contains(parents1.get(i))) {
				unionParents.add(parents1.get(i));
			}
		}
		Collections.sort(unionParents);
		return unionParents;
	}
	
	public static List<Solution> resolveByBackJumping (CSP csp, List<Integer> nbrOfVisitedNoeud) {	
		//initialisation des solutions
		List<Solution> allSolutions = new ArrayList<Solution>();		
		
		Map<Integer, List<Integer>> parentsDesVariables = new HashMap<Integer, List<Integer>>();
		
		//Calcul des parents de toutes les variables
		for (int i = 1; i <= csp.nbVariables; i++) {
			parentsDesVariables.put(i, getAllParents(i, csp));
		}				
		int i = 1;
		Solution s = new Solution(csp);		
		boolean solutionWasFound = false;	
		List<Integer> currentParentsList = new ArrayList<Integer>(parentsDesVariables.get(i));
		while (i >= 1 && i <= csp.getNbVariables()) {
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
			nbrOfVisitedNoeud.set(0, nbrOfVisitedNoeud.get(0) + 1);
			if (!ok) {
				//On réalise le backjumping
				if (solutionWasFound){
					s.setValueToVariable(i, -1);
					if (i > 1 && s.getSolution().get(i - 1).getDomaine().size() > 0) {
						solutionWasFound = false;
					}
					i--;
					if (i > 0) {
						currentParentsList.clear();
						currentParentsList = new ArrayList<Integer>(parentsDesVariables.get(i));
					}
				}
				else {
					int prevI = i;
					i = getNearestParent(currentParentsList);					
					if (i > 0) {
						//We have to clear all selected values for variables in solution for variable k >= i				
						for (int k = i; k <= prevI; k++) {
							s.setValueToVariable(k, -1);
						}
						currentParentsList = unionOfParentsWithoutNewI(currentParentsList, parentsDesVariables.get(i), i);
					}					
				}
			}
			else {
				if (i == csp.getNbVariables()) {
					//Dans ce cas on a une solution, il faut la save
					//Puis enlevé la valeur dans la solution en cours
					//pour continuer le parcours
					allSolutions.add(new Solution(s));
					s.setValueToVariable(i, -1);
					if (domaine.size() == 0) {
						solutionWasFound = true;	
					}
				}
				else {
					i++;
					s.getSolution().get(i).setDomaine(new ArrayList<Integer>(csp.getLstVariable().get(i - 1).getDomaine()));
					currentParentsList.clear();
					currentParentsList = new ArrayList<Integer>(parentsDesVariables.get(i));
				}
			}
		}		
		return allSolutions;
	}	
	
	public static List<Solution> resolveFirstByBackJumping (CSP csp, List<Integer> nbrOfVisitedNoeud) {	
		//initialisation des solutions
		List<Solution> firstSolution = new ArrayList<Solution>();				
		Map<Integer, List<Integer>> parentsDesVariables = new HashMap<Integer, List<Integer>>();		
		//Calcul des parents de toutes les variables
		for (int i = 1; i <= csp.nbVariables; i++) {
			parentsDesVariables.put(i, getAllParents(i, csp));
		}				
		int i = 1;
		Solution s = new Solution(csp);		
		boolean solutionFound = false;
		List<Integer> currentParentsList = new ArrayList<Integer>(parentsDesVariables.get(i));
		while (i >= 1 && i <= csp.getNbVariables() && !solutionFound) {
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
			nbrOfVisitedNoeud.set(0, nbrOfVisitedNoeud.get(0) + 1);
			if (!ok) {
				//On réalise le backjumping
				int prevI = i;
				i = getNearestParent(currentParentsList);
				if (i > 0) {
					//We have to clear all selected values for variables in solution for variable k >= i				
					for (int k = i; k <= prevI; k++) {
						s.setValueToVariable(k, -1);
					}
					currentParentsList = unionOfParentsWithoutNewI(currentParentsList, parentsDesVariables.get(i), i);
				}
			}
			else {
				if (i == csp.getNbVariables()) {
					//On a trouvé une solution
					firstSolution.add(new Solution(s));
					solutionFound = true;
				}
				else {
					i++;
					s.getSolution().get(i).setDomaine(new ArrayList<Integer>(csp.getLstVariable().get(i - 1).getDomaine()));
					currentParentsList.clear();
					currentParentsList = new ArrayList<Integer>(parentsDesVariables.get(i));
				}
			}
		}		
		return firstSolution;
	}
	
	private static Map<Integer, List<CoupleValueDomain>> saveCurrentDomain(int i,
			Map<Integer, List<CoupleValueDomain>> savedDomaines, Solution s, CSP csp) {
		
		List<CoupleValueDomain> domaines = new ArrayList<CoupleValueDomain>();
		for (int j = 1; j <= csp.getNbVariables(); j++) {
			//On récupère tous les domaine actuels			
			domaines.add(new CoupleValueDomain(s.getSolution().get(j)));
		}
		//On les ajoutes dans nos savedDomaines
		savedDomaines.put(i, domaines);
		
		return savedDomaines;
	}	
	
	public static List<Solution> resolveByForwardChecking(CSP csp, List<Integer> nbrOfVisitedNoeud) {
		//initialisation des solutions
		List<Solution> allSolutions = new ArrayList<Solution>();
		Solution s = new Solution(csp);
		//Declaration liste des domaines qu'on doit garder pour revenir en arrière
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
						//On revise les domaines de définition de k
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
			nbrOfVisitedNoeud.set(0, nbrOfVisitedNoeud.get(0) + 1);
			if (!ok) {
				//backtrack
				//reset des Domaines si i > 1
				if (i > 1) {
					for (int k = i; k <= csp.getNbVariables(); k++) {
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
					//Puis enlevé la valeur dans la solution en cours
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
	
	public static List<Solution> resolveFirstByForwardChecking(CSP csp, List<Integer> nbrOfVisitedNoeud) {
		//initialisation des solutions
		List<Solution> firstSolution = new ArrayList<Solution>();
		Solution s = new Solution(csp);
		//Declaration liste des domaines qu'on doit garder pour revenir en arrière
		Map<Integer, List<CoupleValueDomain>> savedDomaines = new HashMap<Integer, List<CoupleValueDomain>>();
		boolean firstSolutionFound = false;
		int i = 1;
		while (i >= 1 && i <= csp.getNbVariables() && !firstSolutionFound) {
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
						//On revise les domaines de définition de k
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
			nbrOfVisitedNoeud.set(0, nbrOfVisitedNoeud.get(0) + 1);
			if (!ok) {
				//backtrack
				//reset des Domaines si i > 1
				if (i > 1) {
					for (int k = i; k <= csp.getNbVariables(); k++) {
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
					//Puis enlevé la valeur dans la solution en cours
					//pour continuer le parcours
					firstSolution.add(new Solution(s));
					firstSolutionFound = true;
				}
				else {
					//step forward
					i++;
				}
			}
		}
		return firstSolution;		
	}	
}
