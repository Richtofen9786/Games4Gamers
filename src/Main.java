import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		int current_Player = 0;
		Connect4 Game = new Connect4();
		Scanner scan = new Scanner(System.in);
		System.out.println("Player 1 is red (R)\nPlayer 2 is yellow (Y)");
		Game.printBoard();
		while (!Game.getGameStatus())
		{
			int i = -1;
			String s = null;
			System.out.println("Player " + (current_Player + 1) + ", please enter which column you'd like to drop a counter into");
			while (true)
			{
				try
				{
					s = scan.nextLine();
					i = Integer.parseInt(s);
					if (!Game.enterMove(i, current_Player))
					{
						System.out.println("Invalid Move. Try Again");
					}
					else
					{
						break;
					}
				}
				catch (NumberFormatException ex)
				{
					System.out.println("Invalid Move. Try Again");
				}
			}
			Game.printBoard();
			if (Game.checkVictory(current_Player))
			{
				System.out.println("Congradulations Player " + (current_Player + 1));
				Game.changeGameStatus();
			}
			if (current_Player == 0)
			{
				current_Player = 1;
			}
			else
			{
				current_Player = 0;
			}

		}
		scan.close();
	}
}
