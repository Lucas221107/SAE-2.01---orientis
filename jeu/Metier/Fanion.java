package Metier;

public abstract class Fanion
{
	private boolean estFoncer;

	public Fanion(boolean estFoncer)
	{
		this.estFoncer = estFoncer;
	}

	public boolean estFoncer()
	{
		return this.estFoncer;
	}
	public abstract boolean correspond(int indice);

	public String toString()
	{
		return "" + this.estFoncer;
	}

}
