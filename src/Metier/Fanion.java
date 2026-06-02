package Metier;

public class Fanion
{
	private int num; 
	private String couleur; 
	private boolean estJoker;

	public Fanion(int num, String couleur, boolean estJoker)
	{
		this.num = num;
		this.couleur = couleur;
		this.estJoker = estJoker;
	}

	public int getNum()
	{
		return this.num;
	}
	public String getCouleur()
	{
		return this.couleur;
	}
	public boolean getEstJoker()
	{
		return this.estJoker;
	}

}
