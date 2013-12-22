package be.ipl.finito.util;


public final class Util {
	public static final int MAX_JOUEURS = 3;
	public static final int MIN_JOUEURS = 2;
	public static final int TEMPS_DEBUT_PARTIE = 15;
	public static final int TEMPS_INACTIVITE = 30;
	
	public static void checkObject(final Object o) {
		if (o == null) {
			throw new NullPointerException();
		}
	}
	
	public static void checkString(final String s) {
		checkObject(s);
		if (s.trim().equals("")) {
			throw new IllegalArgumentException();
		}
	}
	public static void checkNegativeOrZero(final double d) {
		if (d > 0.0) {
			throw new IllegalArgumentException();
		}
	}
	public static void checkPositiveOrZero(final double d) {
		if (d < 0) {
			throw new IllegalArgumentException();
		}
	}
	public static void checkPositive(final double d) {
		if (d<=0.00001) {
			throw new IllegalArgumentException();
		}
	}
	
	public static void checkPositive(final int i) {
		if (i<=0) {
			throw new IllegalArgumentException();
		}
	}
}
