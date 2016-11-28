package com.github;

import java.util.List;

public class Solver {
	public static Solution resolveByBacktracking (CSP csp) {
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
				i--;
			}
			else {
				i++;
			}
		}
		if (i == 0) {
			//Il n'y a pas de solution
			return null;
		}
		//finProcédure
		return s;
	}
}
