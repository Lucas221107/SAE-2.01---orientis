package sae.metier;

public class test
{
	public static void main(String[] args)
	{
		Plateau plateau = new Plateau(7, 7, 7, 27);
		plateau.genererPlateau();
		System.out.println(plateau.toString());
	}
}