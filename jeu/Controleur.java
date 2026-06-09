
public class Controleur
{

	private FramePrincipale ihm;
	

	public Controleur()
	{
		this.ihm = new FramePrincipale ( this );
	}





	public static void main (String[] args)
	{
		new Controleur();
	}
}