public class Connect4
{
	private static char[][] gameboard = new char[6][7];
	private static boolean gameOver = false;
	private Player CurrentPlayers[] = { new Player('R'), new Player('Y') };

	public void changeGameStatus()
	{
		gameOver = true;
	}

	public boolean getGameStatus()
	{
		return gameOver;
	}

	public Connect4()
	{
		initialiseBoard();
	}

	private class Player
	{
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

	public void initialiseBoard()
	{
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 7; j++)
			{
				gameboard[i][j] = 'O';
			}
		}
	}

	public boolean enterMove(int column, int player)
	{
		column--;
		if ((column > 6) || (column < 0))
		{
			return false;
		}
		else if (gameboard[5][column] == 'O')
		{
			for (int i = 0; i < 6; i++)
			{
				if (gameboard[i][column] == 'O')
				{
					this.CurrentPlayers[player].set_last_move(i, column);
					gameboard[i][column] = this.CurrentPlayers[player].getPlayerToken();
					return true;
				}
			}
		}
		return false;

	}

	public void printBoard()
	{
		for (int i = 5; i >= -1; i--)
		{
			for (int j = 0; j < 7; j++)
			{
				if (i != -1)
				{
					if (j == 0)
					{
						System.out.print("Row [" + (i + 1) + "]\t");
					}
					System.out.print("\t" + gameboard[i][j]);
				}
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

	public boolean checkVictory(int player)
	{
		int count = 1, offset = 0;

		//check pieces below
		while ((this.CurrentPlayers[player].get_last_row_move() - offset) > 0)
		{
			offset++;
			if (gameboard[this.CurrentPlayers[player].get_last_row_move() - offset][this.CurrentPlayers[player].get_last_column_move()] == this.CurrentPlayers[player]
			        .getPlayerToken())
			{
				count++;
				if (count == 4)
					return true;
			}
			else
				break;
		}

		//Reset the checks
		count = 1;
		offset = 0;
		//check left
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
		//check right
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

		count = 1;
		offset = 0;
		//check Top-Left
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
		//check Bottom-Right
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
		return false;
	}
}
