import java.io.IOException;
import java.util.Scanner;

/*
 * 	Main class that will run the client code
 * 	The reason the class isnt broken in seperate methods is because I didn't expect it to get so long
 */
public class Client
{
	public static void main(String[] args)
	{
		//boolean for the loop for user input
		boolean loopguard = true;

		//String and scanner for user iput
		String input;
		Scanner scan = new Scanner(System.in);

		//Various objects we need for the clients to communicate with server
		Messenger serverConnection = new Messenger();
		SerialiseConverter serialiser = new SerialiseConverter();
		GameServerStatus currentStatus = new GameServerStatus();

		//basic introdcution text to the system
		System.out.println("Welcome to Games4Gamers game management system.\nPlease enter a username:");
		String UserID = scan.nextLine();
		System.out.print("\n");

		//A list of games is gotten from the server
		//The boolean value thats passed is set to false
		//We are only reading the list of games so theres no need to empty the queue.
		String message = serverConnection.RecieveMessage(UserID, "GameServerStatus", false);
		try
		{
			currentStatus = (GameServerStatus) serialiser.StringToObject(message);
			System.out.println(currentStatus.GetGamesList());
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		catch (ClassNotFoundException f)
		{
			System.out.println("Class Not Found");
			System.exit(-2);
		}

		//Main body of program. Will ask user for input until they quit.
		while (loopguard == true)
		{
			//Text containing information on how to use our system
			System.out
			        .println("To get a list of games, type GamesList\nTo join a queue for a game, please enter the number of that game\nTo see the leaderboard, type LeaderBoard\nType Quit to Exit\n");
			input = scan.nextLine();
			System.out.print("\n");
			//print a list of games and the size of their queues
			if (input.toLowerCase().equals("gameslist"))
			{
				message = serverConnection.RecieveMessage(UserID, "GameServerStatus", false);
				try
				{
					currentStatus = (GameServerStatus) serialiser.StringToObject(message);
					System.out.println(currentStatus.GetGamesList());
				}
				catch (IOException e)
				{
					e.printStackTrace();
					System.exit(-1);
				}
				catch (ClassNotFoundException f)
				{
					System.out.println("Class Not Found");
					System.exit(-2);
				}
			}
			//print the leaderboard results
			else if (input.toLowerCase().equals("leaderboard"))
			{
				message = serverConnection.RecieveMessage(UserID, "LeaderBoard", false);
				try
				{
					Leaderboard currentBoard = (Leaderboard) serialiser.StringToObject(message);
					currentBoard.printRankings();
				}
				catch (IOException e)
				{
					e.printStackTrace();
					System.exit(-1);
				}
				catch (ClassNotFoundException f)
				{
					System.out.println("Class Not Found");
					System.exit(-2);
				}
			}
			//exit the system
			else if (input.toLowerCase().equals("quit"))
			{
				loopguard = false;
				System.out.println("Quitting");
			}
			//all other input
			else
			{
				//try parsing that input to a number
				try
				{
					int game_number = Integer.parseInt(input);
					//join the game queue of that number if it exists
					if (game_number < currentStatus.NumOfGames())
					{
						//message is dequeued from the server
						message = serverConnection.RecieveMessage(UserID, "GameServerStatus", true);
						String GameName = "";
						currentStatus = (GameServerStatus) serialiser.StringToObject(message);
						int player_number = currentStatus.JoinGameQueue(game_number, UserID), count = 0;
						//after currentGameStatus has been updated, we immediately post this back to the server to update the gameserverstatus
						message = serialiser.ObjectToString(currentStatus);
						serverConnection.SendMessage(message, UserID, "GameServerStatus");

						//the user wants to play a game
						boolean playing_game = true;
						while (playing_game)
						{
							//recieve the gameserverstatus. since we wil be editing it, boolean for cosume message is true.
							message = serverConnection.RecieveMessage(UserID, "GameServerStatus", true);
							currentStatus = (GameServerStatus) serialiser.StringToObject(message);

							//If you end up waiting to play a game, theres a counter and a sleep command that is run. This message should only display about every 10 seconds
							//The message lets you know your current position in the queue. This will change as games finish and your position in the queue advances.
							if (count == 0)
							{
								//get the current index
								player_number = currentStatus.IndexofName(UserID, game_number);
								//print this to the user
								System.out.println("You are in postion " + (player_number + 1) + " in the queue for " + currentStatus.GetGameName(game_number)
								        + "\n");
							}
							//if a game is not being played and you are either position 0 or 1, returns true
							//Check to start game also increases the players in game by 1 and sets the game status of that game to true if the number of players is 2
							//and there currently isnt an active on going game being played
							if (currentStatus.ChecktoStartGame(player_number, game_number))
							{
								//update gameserverstatus
								message = serialiser.ObjectToString(currentStatus);
								serverConnection.SendMessage(message, UserID, "GameServerStatus");

								//boolean for your turn or not
								boolean your_turn;
								System.out.println("You have joined a game. Currently waiting for an opponent\n");
								//opponents player number. player 0 goes first and player 1 goes second
								int opponent_player_number;
								if (player_number == 0)
								{
									opponent_player_number = 1;
									your_turn = true;
								}
								else
								{
									opponent_player_number = 0;
									your_turn = false;
								}
								//if the user wants to play a game but there are not 2 players in the game, throw them into a a sleeping loop
								while ((currentStatus.NumPlayersInGame(game_number) < 2) && (playing_game))
								{
									try
									{
										//after 10 seconds, ask if they want to continue to wait or leave
										if (count == 10)
										{
											count = 0;
											System.out.println("Would you like to continue waiting for an opponent.\nPress Y to confirm\n");
											String s = scan.nextLine();
											//if they didnt select yes, the user leaves the game
											if (!s.toLowerCase().equals("y"))
											{
												playing_game = false;
												System.out.println("Returning to main menu\n");
												//exist both inner and outer while loops
												continue;
											}
											else
											{
												System.out.print("Waiting...\n\n");
											}
										}
										else
										{
											Thread.sleep(1000);
											count++;
										}
									}
									catch (java.lang.InterruptedException ie)
									{
										System.out.println(ie);
										System.exit(-3);
									}
									//update gameserverstatus in case anyone joins the queue
									message = serverConnection.RecieveMessage(UserID, "GameServerStatus", false);
									currentStatus = (GameServerStatus) serialiser.StringToObject(message);
								}
								//if they decided to not bother waiting, exits the loop
								if (playing_game == false)
									continue;

								//update gameserverstatus now that a game has started
								message = serverConnection.RecieveMessage(UserID, "GameServerStatus", false);
								currentStatus = (GameServerStatus) serialiser.StringToObject(message);
								//display what game there playing and the name of the opponent
								System.out.print("You are now playing a game of " + currentStatus.GetGameName(game_number) + "\nYou are Player "
								        + (player_number + 1) + "\nYour opponents Name is " + currentStatus.NameofIndex(opponent_player_number, game_number)
								        + "\n");

								//Set which object type it is depending on the game being played.
								//From this point onwards, we communicate with a queue for a specific game rather than using the general gameserverstatus queue.
								SuperGame currentGame = new SuperGame();
								if (currentStatus.GetGameName(game_number).equals("Connect4"))
								{
									currentGame = new Connect4();
									GameName = "Connect4";
								}
								else if (currentStatus.GetGameName(game_number).equals("Mastermind"))
								{
									currentGame = new MasterMind();
									GameName = "MasterMind";
								}
								else
								{
									currentGame = new RockPaperScissors();
									GameName = "RockPaperScissors";
								}

								//Print the game intro message
								System.out.print(currentGame.getGameIntro());
								String s = null;
								//while the game is not over
								while (!currentGame.getGameStatus())
								{
									//print the board if there is one
									currentGame.printBoard();
									//if its your go and not the first turn, print the last players move
									if (your_turn)
									{
										if (currentGame.isfirstTurn())
										{
											currentGame.nolongerfirstTurn();
										}
										else
										{
											currentGame.printLastMove();
										}
										//sometimes the win condition needs to be checked by the opposing player
										//this method allows the user to skip the input step of their turn if needed be
										if (currentGame.isSkipMove() == false)
										{
											//tells the user what to input
											System.out.print(currentGame.getStringToPlay(player_number));
											//keep scanning until the users input is correct
											do
											{
												s = scan.nextLine();
												System.out.println("");
											}
											while (!(currentGame.enterMove(s, player_number)));
										}
										//if the current player has won, update the leaderboard
										//Also prints a congradulations message and ends the game loop
										if (currentGame.checkVictory(player_number))
										{
											currentGame.printBoard();
											System.out.println("Congradulations Player " + (player_number + 1) + "\n" + "You have won the match\n");
											currentGame.changeGameStatus();
											Leaderboard TempBoard = new Leaderboard();
											message = serverConnection.RecieveMessage(UserID, "LeaderBoard", true);
											TempBoard = (Leaderboard) serialiser.StringToObject(message);
											TempBoard.update(UserID, currentStatus.NameofIndex(opponent_player_number, game_number));
											message = serialiser.ObjectToString(TempBoard);
											serverConnection.SendMessage(message, UserID, "LeaderBoard");
											playing_game = false;
										}
										//otherwise, update the currentGame with the moves you made
										//set it so that its not your turn
										message = serialiser.ObjectToString(currentGame);
										serverConnection.SendMessage(message, UserID, GameName);
										your_turn = false;
									}
									//if its not your go
									else
									{
										//f your opponent is not skipping their makeMove step, print that you are waiting for their input
										if (currentGame.isSkipMove() == false)
										{
											System.out.println("Its your oppoents Move. Waiting for reply\n");
										}
										//Recieve Gameupdate
										message = serverConnection.RecieveMessage(UserID, GameName, true);
										if (GameName.equals("Connect4"))
										{
											currentGame = (Connect4) serialiser.StringToObject(message);
										}
										else if (GameName.equals("MasterMind"))
										{
											currentGame = (MasterMind) serialiser.StringToObject(message);
										}
										else
										{
											currentGame = (RockPaperScissors) serialiser.StringToObject(message);
										}
										//If the game is over, you have lost
										//print the board and the opponents move
										//exit the loop
										if (currentGame.getGameStatus())
										{
											currentGame.printBoard();
											currentGame.printLastMove();
											System.out.println("You have lost the match\n");
											playing_game = false;
										}
										your_turn = true;
									}
								}
							}
							//if you want to play a game and are not at the top of the queue, you are put into a waiting loop exactly the same as above if your the only person in a game waiting for soemone to play against
							else
							{
								//update gameserverstatus
								message = serialiser.ObjectToString(currentStatus);
								serverConnection.SendMessage(message, UserID, "GameServerStatus");
								try
								{
									//after 10 seconds of waiting to join a game, prompt the user if they would like to continue waiting.
									if (count == 10)
									{
										count = 0;
										System.out.println("Would you like to continue waiting for a game.\nPress Y to confirm");
										String s = scan.nextLine();
										//if they didnt select yes
										if (!s.toLowerCase().equals("y"))
										{
											playing_game = false;
											System.out.println("Returning to main menu\n");
											//return to main menu
											//exit the gaming loop
											continue;
										}
									}
									else
									{
										Thread.sleep(1000);
										count++;
									}
								}
								catch (java.lang.InterruptedException ie)
								{
									System.out.println(ie);
									System.exit(-3);
								}
							}
						}
						//After you have exited a queue, we switch back to gameserverstatus queue
						message = serverConnection.RecieveMessage(UserID, "GameServerStatus", true);
						currentStatus = (GameServerStatus) serialiser.StringToObject(message);
						//If you were either player 0 or 1, remove you from that game and queue
						if ((player_number == 0) || (player_number == 1))
						{
							currentStatus.FinishedAGame(game_number, UserID);
						}
						//else just remove you from the queue
						else
						{
							currentStatus.RemovePlayer(game_number, UserID);
						}
						message = serialiser.ObjectToString(currentStatus);
						serverConnection.SendMessage(message, UserID, "GameServerStatus");
					}
					//loop back around for new input
					else
					{
						System.out.print("Incorrect Input\n\n");
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
					System.exit(-1);
				}
				catch (ClassNotFoundException f)
				{
					System.out.println("Class Not Found");
					System.exit(-2);
				}
				catch (NumberFormatException e)
				{
					System.out.print("Incorrect Input\n\n");
				}
			}
		}
		//close scanner. very important
		scan.close();
	}
}
