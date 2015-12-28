import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public class SerialiseConverter
{
	/*
	 * This method converts an inputed string back to a generic object variable.
	 * This object can be casted to the original class after you've called this method.
	*/

	public Object StringToObject(String s) throws IOException, ClassNotFoundException
	{
		byte[] data = Base64.getDecoder().decode(s);
		ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(data));
		Object reconstructedObject = objectIn.readObject();
		objectIn.close();
		return reconstructedObject;
	}

	/*
	 * This method converts an inputed object into a serialised string which denotes that object
	 * This string can be converted back into an object by passing it to the method above
	*/

	public String ObjectToString(Serializable o) throws IOException, ClassNotFoundException
	{
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		ObjectOutputStream ObjectOut = new ObjectOutputStream(byteArray);
		ObjectOut.writeObject(o);
		ObjectOut.close();
		return Base64.getEncoder().encodeToString(byteArray.toByteArray());
	}
}
