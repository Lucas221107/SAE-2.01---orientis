public class Controleur 
{
	
	private FramePrincipale ihm;
	private String[]        tabBiomes;
	
	public Controleur ()
	{
		this.ihm = new FramePrincipale( this);
	}

	public String[] initTabBiomes ()
	{
		this.tabBiomes = new String[] { "Forêt dense"  , 
		                                "Forêt claire" , 
										"Prairie"      , 
										"Bruyère"      , 
										"Marais"       , 
										"Zone rocheuse", 
										"Montagne"     ,
			                            "Dune"         , 
										"Urbain"       , 
										"Industriel" 	};

		return this.tabBiomes;
	}









	public static void main ( String[] args)
	{
		new Controleur();
	}
}
