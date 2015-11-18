public class User
{
	private static String name, game_to_play;
	private static int uniqueId;

	public void setGame(String game)
	{
		game_to_play = game;
	}

	public String getGameToPlay()
	{
		return game_to_play;
	}

	public void setname(String user)
	{
		name = user;
	}

	public String getname()
	{
		return name;
	}

	public void setId(int identifier)
	{
		uniqueId = identifier;
	}

	public int getId()
	{
		return uniqueId;
	}
}
