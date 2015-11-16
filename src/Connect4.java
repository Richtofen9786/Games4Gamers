public class Connect4
{
	static char[][] gameboard = new char[6][7];
	private int column_move = -1, row_move = -1;

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

	public boolean enterMove(int column, char Player)
	{
		column--;
		if (gameboard[5][column] == 'O')
		{
			for (int i = 0; i < 6; i++)
			{
				if (gameboard[i][column] == 'O')
				{
					row_move = i;
					column_move = column;
					gameboard[i][column] = Player;
					return true;
				}
			}
		}
		return false;

	}

	public void printBoard()
	{
		for (int i = 5; i >= 0; i--)
		{
			for (int j = 0; j < 7; j++)
			{
				System.out.print("\t" + gameboard[i][j]);
			}
			System.out.print("\n\n");
		}
		System.out.println("________________________________________________________________________\n\n\n");
	}

	public boolean checkVictory(char Player)
	{
		int count = 1, offset = 0;

		//check pieces below
		while ((row_move - offset) > 0)
		{
			offset++;
			if (gameboard[row_move - offset][column_move] == Player)
			{
				count++;
				System.out.println(count);
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
		while ((column_move - offset) > 0)
		{
			offset++;
			if (gameboard[row_move][column_move - offset] == Player)
			{
				count++;
				System.out.println(count);
				if (count == 4)
					return true;
			}
			else
				break;
		}
		//check right
		offset = 0;
		while ((column_move + offset) < 6)
		{
			offset++;
			if (gameboard[row_move][column_move + offset] == Player)
			{
				count++;
				System.out.println(count);
				if (count == 4)
					return true;
			}
			else
				break;
		}

		count = 1;
		offset = 0;
		//check Top-Left
		while (((row_move + offset) < 5) && ((column_move - offset) > 0))
		{
			offset++;
			if (gameboard[row_move + offset][column_move - offset] == Player)
			{
				count++;
				System.out.println(count);
				if (count == 4)
					return true;
			}
			else
				break;
		}
		//check Bottom-Right
		offset = 0;
		while (((row_move - offset) > 0) && ((column_move + offset) < 6))
		{
			offset++;
			if (gameboard[row_move - offset][column_move + offset] == Player)
			{
				count++;
				System.out.println(count);
				if (count == 4)
					return true;
			}
			else
				break;
		}

		count = 1;
		offset = 0;
		//check Top-Right
		while (((row_move + offset) < 5) && ((column_move + offset) < 6))
		{
			offset++;
			if (gameboard[row_move + offset][column_move + offset] == Player)
			{
				count++;
				System.out.println(count);
				if (count == 4)
					return true;
			}
			else
				break;
		}
		//check Bottom-Left
		offset = 0;
		while (((row_move - offset) > 0) && ((column_move - offset) > 0))
		{
			offset++;
			if (gameboard[row_move - offset][column_move - offset] == Player)
			{
				count++;
				System.out.println(count);
				if (count == 4)
					return true;
			}
			else
				break;
		}
		return false;
	}
}
