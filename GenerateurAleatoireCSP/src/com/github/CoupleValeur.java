package com.github;

public class CoupleValeur {

	int valeur1;
	int valeur2;

	public int getValeur1() {
		return valeur1;
	}

	public void setValeur1(int valeur1) {
		this.valeur1 = valeur1;
	}

	public int getValeur2() {
		return valeur2;
	}

	public void setValeur2(int valeur2) {
		this.valeur2 = valeur2;
	}

	public CoupleValeur(int valeur1, int valeur2) {
		this.valeur1 = valeur1;
		this.valeur2 = valeur2;
	}

	@Override
	public String toString() {
		return "(" + valeur1 + "," + valeur2 + ")";
	}
}
