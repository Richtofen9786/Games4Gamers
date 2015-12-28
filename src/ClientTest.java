import java.io.IOException;
import java.util.Scanner;

/*
 * This entire class was used for testing purposes at the beginning of the project but is now defunct and not used anywhere.
 * It is however a very good little tester to send and receive a message from a jms server.
 * 
 * The user is prompted with instructions to wither send, recieve and a test of the serialisation converter on the gamserverstatus.
 * The inputs are then passed on as arguments to the messenger class
 */

public class ClientTest
{
	public static void main(String[] args)
	{
		//variable that is only set to false when quitting
		boolean loopguard = true;
		//various strings for names and queues
		String s, UserID, queueselection, message;
		//scanner for user input
		Scanner scan = new Scanner(System.in);
		//Messenger object for communicating with jms server
		Messenger serverConnection = new Messenger();
		//Serialastion converter to covert objects to strings and vice versa
		SerialiseConverter serialiser = new SerialiseConverter();

		//main loop body for user input
		while (loopguard == true)
		{
			//Initial server message
			System.out
			        .println("Type Send if you wish to send a Message to a Queue\nType Recieve if you wish to Recieve a message from a Queue\nType GetServerStatus to get the status of the game Server\nType Quit to Exit");
			s = scan.nextLine();
			//If the user selects send, a name, queue and message are read in. A method is called to send the users message to that queue under the users id
			if (s.equals("Send"))
			{
				System.out.println("Please enter a user ID");
				UserID = scan.nextLine();
				System.out.println("Please enter the name of the Queue you wish to send to");
				queueselection = scan.nextLine();
				System.out.println("Please enter a message to send");
				message = scan.nextLine();
				serverConnection.SendMessage(message, UserID, queueselection);
			}
			//If the user selects recieve, a name, queue are read in. A method is called to the message from that queue under the users id
			else if (s.equals("Recieve"))
			{
				System.out.println("Please enter a user ID");
				UserID = scan.nextLine();
				System.out.println("Please enter the name of the Queue you wish to Recieve From");
				queueselection = scan.nextLine();
				message = serverConnection.RecieveMessage(UserID, queueselection, true);
				System.out.println("Your message is " + message);
			}
			//Testing to recieve the gameserverstatus, print the string form, convert it back to an object and display a status list of all the servers games.
			else if (s.equals("GetServerStatus"))
			{
				System.out.println("Please enter a user ID");
				UserID = scan.nextLine();
				message = serverConnection.RecieveMessage(UserID, "GameServerStatus", false);
				try
				{
					System.out.println(message);
					GameServerStatus currentStatus = GameServerStatus.class.cast(serialiser.StringToObject(message));
					System.out.println(currentStatus.GetGamesList());
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (ClassNotFoundException f)
				{
					System.out.println("Class Not Found");
				}

			}
			//If the user quits, the program terminates
			else if (s.equals("Quit"))
			{
				loopguard = false;
				System.out.println("Quitting");
				System.exit(0);
			}
			//Safeguard to ensure that the loop doesnt get stuck in an infinite loop. Also prompts the user that their input was wrong.
			else
			{
				System.out.print("Incorrect Input\n\n");
			}
		}
		scan.close();
	}
}
