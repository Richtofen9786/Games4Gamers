import java.io.IOException;

/*
 * This class is used to initialise and clean all of the queues on the server
*/

public class InitialiseServerQueues
{
	public static void main(String[] args)
	{
		GameServerStatus currentStatus = new GameServerStatus();
		Leaderboard currentleaderboard = new Leaderboard();

		//List of games available to the server
		String[] gamesList = { "Connect4", "Mastermind", "Rock Paper Scissors" };
		//Games are loaded into the gameserverstatus object
		currentStatus.initialise(gamesList);

		//System.out.println(currentStatus.GetGamesList());

		SerialiseConverter serialiser = new SerialiseConverter();
		String GamesListAsString = "", LeaderBoardAsString = "";
		try
		{
			//The current status and the leaderboard is serialised to a String
			GamesListAsString = serialiser.ObjectToString(currentStatus);
			LeaderBoardAsString = serialiser.ObjectToString(currentleaderboard);
			//System.out.print("\n\nHere is the GameServerStatus Serialised to a String\n" + GamesListAsString + "\n");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException f)
		{
			System.out.println("Class Not Found");
		}

		//A new messenger object to communicate with server is created
		Messenger connectionToServer = new Messenger();

		//All the queues are emptied and the gameserverstatus and leaderboard sent to the server.
		connectionToServer.ClearQueue("GameServerStatus");
		connectionToServer.ClearQueue("LeaderBoard");
		connectionToServer.ClearQueue("Connect4");
		connectionToServer.ClearQueue("MasterMind");
		connectionToServer.ClearQueue("RockPaperScissors");
		connectionToServer.SendMessage(GamesListAsString, "Admin", "GameServerStatus");
		connectionToServer.SendMessage(LeaderBoardAsString, "Admin", "LeaderBoard");
	}
}
