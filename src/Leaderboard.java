import java.util.HashMap;
import java.util.Map;

public class Leaderboard {
	HashMap<String, Integer> rankings = new HashMap<String, Integer>();
	public Leaderboard(){
	}
	
	public void update(String winner, String loser){
		if(rankings.containsKey(winner)){
			rankings.put(winner, rankings.get(winner) + 1);
		}
		else{
			rankings.put(winner, 1);
		}
		if(rankings.containsKey(loser)){
			rankings.put(loser, rankings.get(loser) - 1);
		}
		else{
			rankings.put(loser, -1);
		}	
	}
	public int returnScore(String player){
		return rankings.get(player);
	}
	public void printRankings(){
		for(String string: rankings.keySet()){
			System.out.println(string.toString()+" "+ rankings.get(string).toString());
		}
		System.out.println("\n");
	}
	public static void main(String[] args){
		Leaderboard leaderboard = new Leaderboard();
		leaderboard.update("charles","tom");
		leaderboard.printRankings();
		leaderboard.update("charles","tom");
		leaderboard.printRankings();
		leaderboard.update("charles","tom");
		leaderboard.printRankings();
		leaderboard.update("charles","tom");
		leaderboard.printRankings();
		leaderboard.update("charles","tom");
		leaderboard.printRankings();
		leaderboard.update("charles","denise");
		leaderboard.printRankings();
		leaderboard.update("denise","tom");
		leaderboard.update("sean", "barry");
		leaderboard.printRankings();
	}
}
