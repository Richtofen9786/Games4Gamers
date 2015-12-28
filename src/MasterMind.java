import java.io.Serializable;

/*
 * 	Game class representing mastermind
 */

public class MasterMind extends SuperGame implements Serializable
{
	private static final long serialVersionUID = -1407593597075931240L;

	private String secretword, gameIntro = "Player 1 inputs a word. Player 2 must guess that word.\n\n", lastMove = "";
	private int secretwordlength;
	private int guesses = 10;

	//Since mastermind requires one person to enter a secret word and the othe person has attempts to guess that word, some alternate mthods were needed.

	//Boolean to make a guess.The users input is a parameter
	private boolean makeGuesses(String input)
	{
		//decrease guesses
		guesses--;

		//returns a hint of the answer to the user. Any incorrect letters are marked with an underscore. If the guess was not the right length, return blank string
		String currentanswer = attemptGuess(input);

		//if hint is not blank, print hint
		if (!currentanswer.equals(""))
		{
			System.out.println("Secret word hint: <" + currentanswer + ">");
		}
		//If the hint is completly revealed, then return true
		if (correctguess(currentanswer))
		{
			return true;
		}
		//If guesses is 0 and you havnt won, your turn is up so return true. also skips your opponents move phase so they can check victory and win
		if (guesses == 0)
		{
			this.enableSkipMove();
			return true;
		}
		//else print message saying your answer was false and try again. return false for incorrect move
		else
		{
			System.out.println("Your guess was incorrect. Guesses left: " + guesses + "\nGuess again:\n");
			return false;
		}
	}

	//returns true if answer is correct
	private boolean correctguess(String guess)
	{
		return (secretword.equals(guess));
	}

	//returns a string of the secret hint
	private String attemptGuess(String guess)
	{
		//if the length is correct, write the secret hint
		if (guess.length() == secretwordlength)
		{
			return guessWord(guess);
		}
		//else display a message prompting the user their answer was wrong
		else
		{
			System.out.println("Incorrect length, secret word length is: " + secretwordlength);
			return "";
		}
	}

	//Method for revealing which characters you got right
	private String guessWord(String guess)
	{
		String[] guessresult = new String[secretwordlength];
		String guessreturn = "";
		//hide all the characters in the hint
		for (int i = 0; i < secretwordlength; i++)
		{
			guessresult[i] = "_";
		}
		//split the guess and secret phrase into individual words
		String[] guessarray = guess.split("");
		String[] secretwordarray = secretword.split("");
		//iterate through the secret word and the guess word
		for (int i = 0; i < secretword.length(); i++)
		{
			for (int j = 0; j < secretword.length(); j++)
			{
				//if the characters in the guess is equal to the characters in the secret phrase, set the hint to be equal to that.
				if (guessarray[i].equals(secretwordarray[j]))
				{
					if (i == j)
					{
						guessresult[i] = guessarray[i];
					}
				}
			}
		}
		//set guessreturn equal to guessresult
		for (int i = 0; i < secretwordlength; i++)
		{
			guessreturn += guessresult[i];
		}
		return guessreturn;
	}

	//Method to get the games opening introduction string
	public String getGameIntro()
	{
		return gameIntro;
	}

	//method to get the last input by the other user
	public void printLastMove()
	{
		System.out.println(lastMove);
	}

	//method to get the string printed before a player enters a move
	//One player enters the secret word, the other player is prompted to guess
	public String getStringToPlay(int player)
	{
		if (player == 0)
		{
			return "Player " + (player + 1) + ", Enter the secret word:\n\n";
		}
		else
		{
			return "Player " + (player + 1) + ", Enter your Guess. Guesses left: 10\n";
		}
	}

	//method to enter a move. returns true if move is valid, otherwise false
	//the first player inputs the secret word
	//the second player guesses, if the second player runs out of guesses, returns true since their go is over
	public boolean enterMove(String input, int player)
	{
		if (player == 0)
		{
			secretword = input;
			secretwordlength = input.length();
			return true;
		}
		else if (guesses > 0)
		{
			return this.makeGuesses(input);
		}
		else
		{
			return true;
		}
	}

	//boolean to check if this player has won
	public boolean checkVictory(int player)
	{
		//if its the player who inputted the secret phrase and your opponent has no guesses left you win
		if ((player == 0) && (guesses == 0))
		{
			//Print out a message before for yourself and store a message fr your opponent
			System.out.println("Your opponenet has run out of guesses");
			lastMove = "\nYou have no guesses remaining\nThe Secret word was \"" + secretword + "\"";
			return true;
		}
		//The second player can only get out of make a move eithe rby guessing correctly or running out of guesses.
		//if the number of guesses is greater than 0, that player has won
		else if ((player == 1) && (guesses > 0))
		{
			System.out.println("You have correctly guessed the secret word");
			lastMove = "\nYour opponent has correctly guessed the word with " + guesses + " guesses remaining\n\n";
			return true;
		}
		//if neither of these is true, return false and swap control to other player
		else
		{
			return false;
		}
	}
}
