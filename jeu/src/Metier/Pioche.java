package Metier;

import java.util.ArrayList;
import java.util.Collections;

public class Pioche 
{
	
	private ArrayList<Fanion> fanions;
	
	public Pioche() 
	{
		this.fanions = new ArrayList<>();
	}
	
	public void melanger() 
	{
		Collections.shuffle(this.fanions);
	}
	
	public Fanion piocher() 
	{
		if (estVide()) 
		{
			return null;
		}
		return this.fanions.remove(this.fanions.size() - 1);
	}
	
	public boolean estVide() 
	{
		return this.fanions.isEmpty();
	}
	
	public void reconstituer() 
	{
		this.fanions.clear();
		
		for (int i = 1; i <= 10; i++) 
		{
			if(i%2 == 0)
				this.fanions.add(new FanionNumerote( false, (int) i/2+1));
			else
				this.fanions.add(new FanionNumerote( true, (int) i/2+1));
		}
		this.fanions.add(new FanionJoker(false));
		this.fanions.add(new FanionJoker(true));
		melanger();
	}
}