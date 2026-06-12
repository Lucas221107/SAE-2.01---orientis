package Metier;

public class FanionJoker extends Fanion
{
	private int numero;
	public FanionJoker(boolean estFoncer)
	{
		super(estFoncer);
		this.numero = 0;
	}
	public boolean correspond(int numeroBalise)
	{
		return true;
	}

	public String toString()
	{
		return "La Carte est le joker " + (super.estFoncer() ? "foncé" : "clair");
	}
}
