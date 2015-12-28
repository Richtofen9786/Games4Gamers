import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/*
 *	This class communicates to and from the jms server.
 *	There are three methods - a method for sending, recieveing and clearing a queue.
 */

public class Messenger
{
	/*	
	 * This method sends a message to a queue on the server. This achived by creating a connection to the server
	 * A producer is then created along with a session and a message is sent.
	 * Standard jms protocol that was covered in the lectures and the notes.
	*/
	public void SendMessage(String message, String ID, String Queue)
	{
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

		try
		{
			Connection connection = connectionFactory.createConnection();
			connection.setClientID(ID);
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(Queue);
			MessageProducer messageProducer = session.createProducer(queue);

			TextMessage textMessage = session.createTextMessage(message);
			messageProducer.send(textMessage);
			connection.close();
		}
		catch (JMSException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * 	Same as the above method, only for recieveing instead.
	 * 	This method however also takes a boolean.
	 * 	This boolean will notify whether or not to acknowledge that a messsage has been recieved or not.
	 * 	If we don't acknowledge the message, it won't be cosumed from the queue.
	 * 	This allows us to read a message and leave it remaining on the queue.
	 */
	public String RecieveMessage(String ID, String Queue, Boolean acknowledge)
	{
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		String recievedMessage = null;
		try
		{
			Connection connection = connectionFactory.createConnection();
			connection.setClientID(ID);
			Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			Queue queue = session.createQueue(Queue);
			MessageConsumer messageConsumer = session.createConsumer(queue);
			connection.start();

			Message message = messageConsumer.receive();
			if (message instanceof TextMessage)
			{
				//If true, message will be acknowlegded and dropped from the queue
				if (acknowledge)
				{
					message.acknowledge();
				}
				recievedMessage = ((TextMessage) message).getText();
			}

			connection.close();

		}
		catch (JMSException e)
		{
			e.printStackTrace();
		}

		return recievedMessage;
	}

	//This method simply clears a queue using an admin ID
	public void ClearQueue(String Queue)
	{
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		try
		{
			Connection connection = connectionFactory.createConnection();
			connection.setClientID("admin");
			Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			Queue queue = session.createQueue(Queue);
			MessageConsumer messageConsumer = session.createConsumer(queue);
			QueueBrowser browser = session.createBrowser(queue);

			connection.start();
			Enumeration<?> enumerator = browser.getEnumeration();

			//The method enumerates over and consumes all the messages in that queue
			while (enumerator.hasMoreElements())
			{
				Message message = (TextMessage) enumerator.nextElement();
				messageConsumer.receive();
				message.acknowledge();
			}

			connection.close();
		}
		catch (JMSException e)
		{
			e.printStackTrace();
		}
	}

}
