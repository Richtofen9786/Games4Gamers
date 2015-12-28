import java.io.Serializable;
import java.util.HashMap;

/*
 * 	The leaderboard keeps track of the players scores from the games they have played.
 */

public class Leaderboard implements Serializable
{
	private static final long serialVersionUID = -1166975781334654847L;

	//Leaderboard uses a HashMap of names to numbers for records of the game winnings
	HashMap<String, Integer> rankings = new HashMap<String, Integer>();

	public Leaderboard()
	{
	}

	//Method to update the leaderboard after a game
	//You gain a point by winning and are deducted a point if you lose a game
	public void update(String winner, String loser)
	{
		if (rankings.containsKey(winner))
		{
			rankings.put(winner, rankings.get(winner) + 1);
		}
		else
		{
			rankings.put(winner, 1);
		}
		if (rankings.containsKey(loser))
		{
			rankings.put(loser, rankings.get(loser) - 1);
		}
		else
		{
			rankings.put(loser, -1);
		}
	}

	//basic method for accessing a players score
	public int returnScore(String player)
	{
		return rankings.get(player);
	}

	//method to print the rankings to the user
	public void printRankings()
	{
		if (rankings.keySet().size() == 0)
		{
			System.out.println("There are no results as of yet\n");
			return;
		}
		for (String string : rankings.keySet())
		{
			System.out.println(string.toString() + " " + rankings.get(string).toString());
		}
		System.out.println("\n");
	}
}