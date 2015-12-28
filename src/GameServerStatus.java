import java.io.Serializable;
import java.util.ArrayList;

/*
 *	GameServerStatus retains information relating to how many people want to play what game and what games are currently being played.
 *	It does this by setting each game as an object of an inner class and storing them in an array of that object type.
 *	There are various methods to access this information since all the values are private 
 */

public class GameServerStatus implements Serializable
{
	private static final long serialVersionUID = 4422048905664139423L;

	//An array of the inner class objects
	private GameType[] gametype;

	private class GameType implements Serializable
	{
		/*
		 * This class stores all the information needed in relation to a specific game
		 */
		private static final long serialVersionUID = 1352800851262127669L;

		int players_in_game = 0;

		//boolean to denote if a game is being played or not
		boolean game_status = false;

		//String holding the game name
		String game_name;

		//An arraylist representing the queue of players currently playing or wanting to play that game
		ArrayList<String> player_queue = new ArrayList<String>();

		//These are basic access methods for setting and getting the values of variables
		public GameType(String name)
		{
			game_name = name;
		}

		public String GetName()
		{
			return game_name;
		}

		public boolean Game_Status()
		{
			return game_status;
		}

		public void Set_Game_Status_True()
		{
			game_status = true;
		}

		public void Set_Game_Status_False()
		{
			game_status = false;
		}

		//When a player joins, they are added to the arraylist
		public void PlayerJoinsGame(String user)
		{
			player_queue.add(user);
		}

		//When a player leaves, they are removed from the arraylist. The list updates automatically pushing the reamining elements left by 1.
		public void PlayerLeavesGame(String user)
		{
			player_queue.remove(user);
		}

		public int SizeofQueue()
		{
			return player_queue.size();
		}

		//when a game is finished, the numbers of players currently in a game decreases by 1
		public void Gamefinished()
		{
			players_in_game--;
		}

		//When a person starts a game, the number of players in the game increases by 1
		public void GameStart()
		{
			players_in_game++;
		}

		public int NumOfPlayersInGame()
		{
			return players_in_game;
		}
	}

	//This method initalises the inner class with an array of strings denoting which games the server is hosting
	public void initialise(String[] GameNames)
	{
		gametype = new GameType[GameNames.length];
		for (int i = 0; i < GameNames.length; i++)
		{
			gametype[i] = new GameType(GameNames[i]);
		}
	}

	//method to get the name of a specific game number
	public String GetGameName(int game_number)
	{
		return gametype[game_number].GetName();
	}

	//returns the total number of games
	public int NumOfGames()
	{
		return gametype.length;
	}

	//returns a string containing a list of the games the server offers as well how many people are in the queue and if theres a game being played
	public String GetGamesList()
	{
		String gameslist = "";
		for (int i = 0; i < gametype.length; i++)
		{
			gameslist += "Game Number " + i + "\t" + gametype[i].GetName() + "\n";
			if (gametype[i].Game_Status())
			{
				gameslist += "There is currently a game in progress\n";
			}
			else
			{
				gameslist += "No game is being played at the moment\n";
			}

			if (gametype[i].SizeofQueue() >= 2)
			{
				gameslist += "Number of players in queue:\t" + gametype[i].SizeofQueue() + "\n\n";
			}
			else if (gametype[i].SizeofQueue() == 1)
			{
				gameslist += "There is currently one player waiting to play a game\n\n";
			}
			else
			{
				gameslist += "There is currently nobody waiting to play a game\n\n";
			}
		}
		return gameslist;
	}

	//When a player joins a game, add them to the appropiate queue and return their index in the queue
	public int JoinGameQueue(int game_number, String user)
	{
		gametype[game_number].PlayerJoinsGame(user);
		return gametype[game_number].player_queue.indexOf(user);
	}

	//Gets the index of the player in a game queue
	public int gameindex(int game_number, String user)
	{
		return gametype[game_number].player_queue.indexOf(user);
	}

	//boolean method to check if a game can be started
	public Boolean ChecktoStartGame(int index, int game_number)
	{
		//if a game is not being played, and you are in position 0 or 1 in the queue then...
		if ((gametype[game_number].Game_Status() == false) && ((index == 0) || (index == 1)))
		{
			//...increase the number of players in game by 1
			gametype[game_number].GameStart();
			//if there are two players in the game, set game status to true to denote a game is being played
			if (gametype[game_number].NumOfPlayersInGame() == 2)
			{
				gametype[game_number].Set_Game_Status_True();
			}
			return true;
		}
		return false;
	}

	//returns the name of the person at the index passed in
	public String NameofIndex(int index, int game_number)
	{
		return gametype[game_number].player_queue.get(index);
	}

	//returns the index of the name that was passed in
	public int IndexofName(String name, int game_number)
	{
		return gametype[game_number].player_queue.indexOf(name);
	}

	//method to get the number of players in a game
	public int NumPlayersInGame(int game_number)
	{
		return gametype[game_number].NumOfPlayersInGame();
	}

	//method to get the gamestatus
	public boolean getGameStatus(int game_number)
	{
		return gametype[game_number].Game_Status();
	}

	//mehtod to remove a player from the queue
	public void RemovePlayer(int game_number, String user)
	{
		gametype[game_number].PlayerLeavesGame(user);
	}

	//Method called when a game finishes
	public void FinishedAGame(int game_number, String user)
	{
		//remove user from the queue
		gametype[game_number].PlayerLeavesGame(user);
		//remove the user from the game
		gametype[game_number].Gamefinished();
		//if there is noone left in the game, set the gamestatus to false to denote that the game is over and both players have left
		if (gametype[game_number].NumOfPlayersInGame() == 0)
		{
			gametype[game_number].Set_Game_Status_False();
		}
	}
}
