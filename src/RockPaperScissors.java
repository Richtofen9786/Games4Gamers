import java.io.Serializable;

/*
 * 	Game class representing rock paper scissors
 */

public class RockPaperScissors extends SuperGame implements Serializable
{
	private static final long serialVersionUID = -8718442722996512110L;

	//LastMove is initailly empty since there is no last move on turn 1
	private String gameIntro = "Welcome to Rock Paper Scissors\n\n", LastMove = "";
	//These strings denote the users input. selection 0 refers to the first players input and selection 1 denotes the second players input
	private String[] Selection = { "empty", "empty" };

	//Method to get the games opening introduction string
	public String getGameIntro()
	{
		return gameIntro;
	}

	//method to get the last input by the other user
	public void printLastMove()
	{
		System.out.println(LastMove);
	}

	//method to get the string printed before a player enters a move
	public String getStringToPlay(int player)
	{
		return "Player " + (player + 1) + ", select either rock, paper or scissors.\n\n";
	}

	//method to enter a move. returns true if move is valid, otherwise false
	public boolean enterMove(String input, int player)
	{
		//returns true if the selection is valid, otherwise false
		if ((input.toLowerCase().equals("rock")) || (input.toLowerCase().equals("paper")) || (input.toLowerCase().equals("scissors")))
		{
			Selection[player] = input;
			LastMove = "";
			return true;
		}
		else
		{
			System.out.println("Incorrect Input. Try again");
			return false;
		}
	}

	//boolean to check if this player has won
	//true if the player has won the game, otherwise false
	public boolean checkVictory(int player)
	{
		int other_player;
		if (player == 0)
		{
			other_player = 1;
		}
		else
		{
			other_player = 0;
		}
		//true If neither of the users inputs are empty.
		//Ensures that input has been collected by both users.
		if ((!Selection[player].equals("empty")) && (!Selection[other_player].equals("empty")))
		{
			//Update last move accordingly
			LastMove = "Player 1 entered " + Selection[0] + "\nPlayer 2 entered " + Selection[1] + "\n";
			//checks if the current players selection has beaten the opponents
			if (((Selection[player].toLowerCase().equals("rock")) && (Selection[other_player].toLowerCase().equals("scissors")))
			        || ((Selection[player].toLowerCase().equals("scissors")) && (Selection[other_player].toLowerCase().equals("paper")))
			        || ((Selection[player].toLowerCase().equals("paper")) && (Selection[other_player].toLowerCase().equals("rock"))))
			{
				//Print the last move made and return true
				this.printLastMove();
				return true;
			}
			//If theres a tie, re-initialise the selections to empty. The game then goes back around and starts again
			else if (Selection[player].toLowerCase().equals((Selection[other_player].toLowerCase())))
			{
				Selection[0] = "empty";
				Selection[1] = "empty";
				LastMove += "There has been a tie. Another round is starting:\n";
				this.printLastMove();
			}
			//If you didn't win or tie the game, that means you lost. Therefore we set it so the opponenenet enter move phase is skipped.
			//This allows the opponenet to get to the check victory mehtod without having to enter a move.
			else
			{
				this.enableSkipMove();
			}
		}
		return false;
	}
}
