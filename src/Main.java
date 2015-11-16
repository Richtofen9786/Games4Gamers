public class Main
{
	public static void main(String[] args)
	{
		char Player1 = 'R', Player2 = 'Y';
		Connect4 Game = new Connect4();
		Game.initialiseBoard();
		Game.printBoard();
		Game.enterMove(1, Player1);
		Game.enterMove(2, Player2);
		Game.printBoard();
		Game.enterMove(2, Player1);
		Game.enterMove(1, Player2);
		Game.printBoard();
		Game.enterMove(3, Player1);
		Game.enterMove(4, Player2);
		Game.printBoard();
		Game.enterMove(4, Player1);
		Game.enterMove(3, Player2);
		Game.printBoard();
		Game.enterMove(3, Player1);
		Game.enterMove(4, Player2);
		Game.printBoard();
		Game.enterMove(4, Player1);
		Game.enterMove(3, Player2);
		Game.printBoard();
		if (Game.checkVictory(Player1))
		{
			System.out.println("Congratulations " + Player1);
		}
		else
		{
			System.out.println("I'm retarded");
		}
	}
}
