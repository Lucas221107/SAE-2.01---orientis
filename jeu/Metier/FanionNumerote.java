package Metier;

public class FanionNumerote extends Fanion
{
	private int numero;
	

	public FanionNumerote(boolean estFoncer, int numero)
	{
		super(estFoncer);
		this.numero = numero;
	}
	public boolean correspond(int numeroBalise)
	{
		return this.numero == numeroBalise;
	}

	public String toString()
	{
		return "La Carte est le " + this.numero + " " + (super.estFoncer() ? "foncé" : "clair");
	}
}
