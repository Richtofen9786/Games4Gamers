import java.io.Serializable;

/*
 * 	A game class representing Connect4
 */

public class Connect4 extends SuperGame implements Serializable
{
	private static final long serialVersionUID = 7691596250836151216L;

	//Gameboard variable
	private char[][] gameboard = new char[6][7];
	private Player CurrentPlayers[] = { new Player('R'), new Player('Y') };
	private String gameIntro = "Player 1 is red (R)\nPlayer 2 is yellow (Y)\n\n", LastMove = "";

	//when connect 4 is started, the board should be empty
	public Connect4()
	{
		initialiseBoard();
	}

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
		return "Player " + (player + 1) + ", please enter which column you'd like to drop a counter into\n";
	}

	/*
	 * 	This is a private inner class denoting some information relating to the players pieces and their last moves
	 * 	There some variables and basic accessor methods
	 */

	private class Player implements Serializable
	{
		private static final long serialVersionUID = -8464197155875672721L;

		private int last_row_move;
		private int last_column_move;
		private char player_token;

		private Player(char a)
		{
			player_token = a;
		}

		private char getPlayerToken()
		{
			return player_token;
		}

		private void set_last_move(int x, int y)
		{
			last_row_move = x;
			last_column_move = y;
		}

		private int get_last_row_move()
		{
			return last_row_move;
		}

		private int get_last_column_move()
		{
			return last_column_move;
		}
	}

	//method to initailise the board if there is one
	public void initialiseBoard()
	{
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 7; j++)
			{
				//each piece is set to O to denote an empty circle on the board
				gameboard[i][j] = 'O';
			}
		}
	}

	//method to enter a move. returns true if move is valid, otherwise false
	public boolean enterMove(String input, int player)
	{
		int column;
		//parse the number to an int. catch th excpetion if it occurs and print a message
		try
		{
			column = Integer.parseInt(input);
		}
		catch (NumberFormatException ex)
		{
			System.out.println("Invalid Move. Try Again");
			return false;
		}
		//since the gameboard is one off what the player see, subtract one from the user input
		column--;
		//check if its within range
		if ((column > 6) || (column < 0))
		{
			System.out.println("Invalid Move. Try Again");
			return false;
		}
		//If the top of the column is empty, that means a piece is placeable into that column
		else if (gameboard[5][column] == 'O')
		{
			//iterate up through that column
			for (int i = 0; i < 6; i++)
			{
				//the first empty space to be found
				if (gameboard[i][column] == 'O')
				{
					//update the last move and change the current space the the players token
					this.CurrentPlayers[player].set_last_move(i, column);
					gameboard[i][column] = this.CurrentPlayers[player].getPlayerToken();
					if (player == 0)
					{
						LastMove = "Player 1 dropped a Red piece into column " + (column + 1);
					}
					else
					{
						LastMove = "Player 2 dropped a Yellow piece into column " + (column + 1);
					}
					return true;
				}
			}
		}
		//if the column is full, the move is invalid and a different column must be picked
		System.out.println("Invalid Move. Try Again");
		return false;
	}

	//method to print the board
	public void printBoard()
	{
		//starting at the top row
		for (int i = 5; i >= -1; i--)
		{
			//leftmost column
			for (int j = 0; j < 7; j++)
			{
				//if the row is in range
				if (i != -1)
				{
					//if its the start of a row, print the name of that row
					if (j == 0)
					{
						System.out.print("Row [" + (i + 1) + "]\t");
					}
					//print the token for that location
					System.out.print("\t" + gameboard[i][j]);
				}
				//if the row is out of range, print the column numbers below that column
				else
				{
					System.out.print("\t[" + (j + 1) + "]");
				}
			}
			System.out.print("\n\n");
			if (i == 0)
			{
				System.out.print("Column\t");
			}
		}
		System.out.println("________________________________________________________________________\n\n\n");
	}

	//boolean to check if this player has won
	public boolean checkVictory(int player)
	{
		//count is 1 since the user just inputted a piece. offset is how far away we are from the orginal piece entered
		int count = 1, offset = 0;

		//check if the pieces below the last entered piece belong to the current user
		//since we decreasing the y value, offset is subracted
		//while the position were checking is within bounds
		while ((this.CurrentPlayers[player].get_last_row_move() - offset) > 0)
		{
			offset++;
			if (gameboard[this.CurrentPlayers[player].get_last_row_move() - offset][this.CurrentPlayers[player].get_last_column_move()] == this.CurrentPlayers[player]
			        .getPlayerToken())
			{
				count++;
				//if theres 4, then you've won
				if (count == 4)
					return true;
			}
			else
				break;
		}

		//Reset the checks since were now checking a different direction
		count = 1;
		offset = 0;
		//we check the counters to the left of the piece
		//since we are decreasing x, we subtract the offset
		while ((this.CurrentPlayers[player].get_last_column_move() - offset) > 0)
		{
			offset++;
			if (gameboard[this.CurrentPlayers[player].get_last_row_move()][this.CurrentPlayers[player].get_last_column_move() - offset] == this.CurrentPlayers[player]
			        .getPlayerToken())
			{
				count++;
				if (count == 4)
					return true;
			}
			else
				break;
		}
		//We now have to check the counters to the right. we dont set count back to zero however since were just checking the pieces on the same line
		//since we increasing y, we add the offset
		offset = 0;
		while ((this.CurrentPlayers[player].get_last_column_move() + offset) < 6)
		{
			offset++;
			if (gameboard[this.CurrentPlayers[player].get_last_row_move()][this.CurrentPlayers[player].get_last_column_move() + offset] == this.CurrentPlayers[player]
			        .getPlayerToken())
			{
				count++;
				if (count == 4)
					return true;
			}
			else
				break;
		}

		//Reset the checks since were now checking a different direction
		count = 1;
		offset = 0;
		//we check the counters left and up from the piece
		//We are decreasing x but increasing y so we set quantifier(addition or subtraction in this case) accordingly
		while (((this.CurrentPlayers[player].get_last_row_move() + offset) < 5) && ((this.CurrentPlayers[player].get_last_column_move() - offset) > 0))
		{
			offset++;
			if (gameboard[this.CurrentPlayers[player].get_last_row_move() + offset][this.CurrentPlayers[player].get_last_column_move() - offset] == this.CurrentPlayers[player]
			        .getPlayerToken())
			{
				count++;
				if (count == 4)
					return true;
			}
			else
				break;
		}
		//check Bottom-Right. Don't need to reset checks since the pieces are just going the opposite direction.
		//We are increasing x but decreasing y.
		offset = 0;
		while (((this.CurrentPlayers[player].get_last_row_move() - offset) > 0) && ((this.CurrentPlayers[player].get_last_column_move() + offset) < 6))
		{
			offset++;
			if (gameboard[this.CurrentPlayers[player].get_last_row_move() - offset][this.CurrentPlayers[player].get_last_column_move() + offset] == this.CurrentPlayers[player]
			        .getPlayerToken())
			{
				count++;
				if (count == 4)
					return true;
			}
			else
				break;
		}

		//Reset the checks. This is the reverse of checking the previous diagonal.
		count = 1;
		offset = 0;
		//check Top-Right
		while (((this.CurrentPlayers[player].get_last_row_move() + offset) < 5) && ((this.CurrentPlayers[player].get_last_column_move() + offset) < 6))
		{
			offset++;
			if (gameboard[this.CurrentPlayers[player].get_last_row_move() + offset][this.CurrentPlayers[player].get_last_column_move() + offset] == this.CurrentPlayers[player]
			        .getPlayerToken())
			{
				count++;
				if (count == 4)
					return true;
			}
			else
				break;
		}
		//check Bottom-Left
		offset = 0;
		while (((this.CurrentPlayers[player].get_last_row_move() - offset) > 0) && ((this.CurrentPlayers[player].get_last_column_move() - offset) > 0))
		{
			offset++;
			if (gameboard[this.CurrentPlayers[player].get_last_row_move() - offset][this.CurrentPlayers[player].get_last_column_move() - offset] == this.CurrentPlayers[player]
			        .getPlayerToken())
			{
				count++;
				if (count == 4)
					return true;
			}
			else
				break;
		}
		//if there are not 4 pieces in a row, you have not won yet.
		return false;
	}
}
