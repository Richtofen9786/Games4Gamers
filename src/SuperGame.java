import java.io.Serializable;

/*
 * 	Super game class. contains methods and variables in common to all the game classes
 */

public class SuperGame implements Serializable
{
	private static final long serialVersionUID = -4340471563133618242L;

	//booleans for denoting if the game is over, if its the first turn or or whther to skip the turn.
	private boolean gameOver = false, firstTurn = true, skip_move = false;

	//methods for accessing and chaning the variables values
	public boolean isfirstTurn()
	{
		return firstTurn;
	}

	public boolean isSkipMove()
	{
		return skip_move;
	}

	public void enableSkipMove()
	{
		skip_move = true;
	}

	public void disableSkipMove()
	{
		skip_move = false;
	}

	public void nolongerfirstTurn()
	{
		firstTurn = false;
	}

	public void changeGameStatus()
	{
		gameOver = true;
	}

	public boolean getGameStatus()
	{
		return gameOver;
	}

	//These methods are in common but are defined differently in each class or maybe even left blank as they are if they are not needed

	//Method to get the games opening introduction string
	public String getGameIntro()
	{
		return "";
	}

	//method to get the string printed before a player enters a move
	public String getStringToPlay(int player)
	{
		return "";
	}

	//method to initailise the board if there is one
	public void initialiseBoard()
	{
	}

	//method to enter a move. returns true if move is valid, otherwise false
	public boolean enterMove(String input, int player)
	{
		return false;
	}

	//method to get the last input by the other user
	public void printLastMove()
	{
	}

	//method to print the board
	public void printBoard()
	{
	}

	//boolean to check if this player has won
	public boolean checkVictory(int player)
	{
		return false;
	}
}
