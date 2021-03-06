package com.github;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CSP {

	List<Variable> lstVariable = null;
	List<Contrainte> lstContrainte = null;
	int nbVariables;

	public CSP(int nbDeReines) {
		this.nbVariables = nbDeReines;

		List<Integer> domaineDeChaqueReine = new ArrayList<Integer>();
		for (int i = 1; i <= nbVariables; i++) {
			domaineDeChaqueReine.add(i);
		}

		this.lstVariable = new ArrayList<Variable>();
		for (int i = 1; i <= nbVariables; i++) {
			lstVariable.add(new Variable(i, domaineDeChaqueReine));
		}

		List<CoupleValeur> lstCouple = new ArrayList<CoupleValeur>();
		for (int i = 1; i <= nbVariables; i++) {
			for (int j = 1; j <= nbVariables; j++) {
				// On prend tous les couples possibles
				lstCouple.add(new CoupleValeur(i, j));
			}
		}

		this.lstContrainte = new ArrayList<Contrainte>();
		for (int i = 1; i <= nbVariables; i++) {
			for (int j = i + 1; j <= nbVariables; j++) {
				Variable var1 = lstVariable.get(i - 1);
				Variable var2 = lstVariable.get(j - 1);
				Contrainte contrainte = new Contrainte(var1, var2, -1);
				List<CoupleValeur> lstCoupleTmp = new ArrayList<CoupleValeur>(lstCouple);
				// Retirer mauvais couple en fonction de i et j
				for (int d = 1; d <= this.nbVariables; d++) {
					for (int k = lstCoupleTmp.size() - 1; k >= 0; k--) {
						CoupleValeur tmpCV = lstCoupleTmp.get(k);
						int diffIAndJ = j - i;
						if (tmpCV.getValeur1() == tmpCV.getValeur2()
								|| tmpCV.getValeur2() + diffIAndJ <= this.nbVariables
										&& tmpCV.getValeur1() == tmpCV.getValeur2() + diffIAndJ
								|| tmpCV.getValeur2() - diffIAndJ >= 1
										&& tmpCV.getValeur1() == tmpCV.getValeur2() - diffIAndJ) {
							lstCoupleTmp.remove(k);
						}
					}
				}
				contrainte.setLstCouple(new ArrayList<CoupleValeur>(lstCoupleTmp));
				lstContrainte.add(contrainte);
			}
		}

	}

	public CSP(int nbVariables, int tailleMaxDomaine, int densite, int durete, int connectivite) {
		this.nbVariables = nbVariables;

		this.lstVariable = new ArrayList<Variable>();
		for (int i = 1; i <= nbVariables; i++) {
			lstVariable.add(new Variable(i, tailleMaxDomaine));
		}

		int nbMaxContraintes = nbVariables * (nbVariables - 1) / 2;
		int nbContraintes = densite * nbMaxContraintes / 100;

		int nbContraintesMaxViaConnectivite = (int) Math.floor(nbVariables * ((double) connectivite / 2));
		if (nbContraintesMaxViaConnectivite < nbContraintes) {
			throw new UnsupportedOperationException(
					"La connectivit� n'est pas assez grande par rapport au nbr de contraintes voulu (densit�)\n"
							+ "nbContraintes avec densit� : " + nbContraintes + ", nbrContraintes avec connectivit� : "
							+ nbContraintesMaxViaConnectivite + "\n"
							+ "Vous devez augment� la connectivit� ou diminuer la densit�");
		}

		this.lstContrainte = new ArrayList<Contrainte>();
		for (int i = 1; i <= nbVariables; i++) {
			for (int j = i + 1; j <= nbVariables; j++) {
				Variable var1 = lstVariable.get(i - 1);
				Variable var2 = lstVariable.get(j - 1);
				lstContrainte.add(new Contrainte(var1, var2, 0));
			}
		}

		List<Contrainte> lstContrainteOk = new ArrayList<Contrainte>();
		while (!lstContrainte.isEmpty()) {
			int indexContrainte = generateRandomFrom0ToMax(lstContrainte.size());
			Contrainte contrainte = lstContrainte.get(indexContrainte);

			Variable var1 = contrainte.getVariable1();
			Variable var2 = contrainte.getVariable2();

			if (var1.getConnectivite() < connectivite && var2.getConnectivite() < connectivite) {
				lstContrainteOk.add(contrainte);

				// incrementation de la connectivite
				var1.updateConnectivite(1);
				var2.updateConnectivite(1);
			}

			// on la vire
			lstContrainte.remove(indexContrainte);

		}

		// on remplace
		lstContrainte = lstContrainteOk;

		// si jamais on n'en a toujours trop, on en retire au hasard quelque
		// soit la connectivite
		while (lstContrainte.size() > nbContraintes) {
			int indexToRemove = generateRandomFrom0ToMax(lstContrainte.size());
			Contrainte contrainte = lstContrainte.get(indexToRemove);
			Variable variable1 = contrainte.getVariable1();
			Variable variable2 = contrainte.getVariable2();

			variable1.updateConnectivite(-1);
			variable2.updateConnectivite(-1);

			lstContrainte.remove(contrainte);
		}

		for (int i = 0; i < lstContrainte.size(); i++) {
			Contrainte contrainte = lstContrainte.get(i);
			contrainte.initMap(durete);
		}
	}

	@Override
	public String toString() {
		return "CSP : \n" + "lstVariable (" + lstVariable.size() + ") =" + lstVariable + "\n" + "lstContrainte ("
				+ lstContrainte.size() + ") =" + "\n" + lstContrainte;
	}

	private int generateRandomFrom0ToMax(int max) {
		Random r = new Random();
		int min = 0;
		int result = r.nextInt(max - min) + min;
		return result;
	}

	private boolean isAllConnectiviteOk(int connectivite) {
		for (Variable var : lstVariable) {
			if (var.getConnectivite() > connectivite) {
				return false;
			}
		}
		return true;
	}

	public List<Variable> getLstVariable() {
		return lstVariable;
	}

	public List<Contrainte> getLstContrainte() {
		return lstContrainte;
	}

	public int getNbVariables() {
		return nbVariables;
	}

}
