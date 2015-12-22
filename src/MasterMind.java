import java.util.Scanner;

public class MasterMind {
	private String secretword;
	private int secretwordlength;
	private int guesses = 0;
	Scanner scanner = new Scanner(System.in);
	public MasterMind(){	
		chooseWord();
		if(makeGuesses()){
			System.out.println("Player 2 Wins!");
		}
		else System.out.println("Player 1 Wins!");
	}
	private boolean makeGuesses(){
		while(guesses<10){
			String currentanswer = attemptGuess();
			System.out.println(currentanswer);
			if(correctguess(currentanswer)){
				return true;
			}
			guesses++;
		}
		return false;
		
	}
	private boolean correctguess(String guess){
		return(secretword.equals(guess));
	}
	private String attemptGuess(){
		System.out.println("Guess the Secret Word: ");
		String guess = scanner.nextLine();
		if(guess.length()==secretwordlength){
			return guessWord(guess);
		}
		else{
			System.out.println("Incorrect length, secret word length is: "+secretwordlength);
			return attemptGuess();
		}
	}
	private void chooseWord(){
		System.out.println("Enter the Secret Word: ");
		secretword=scanner.nextLine();
		secretwordlength = secretword.length();
	}
	private String guessWord(String guess){
		String[] guessresult=new String[secretwordlength];
		String guessreturn="";
		for(int i=0;i<secretwordlength;i++){
			guessresult[i]="_";
		}
		String[] guessarray = guess.split("");
		String[] secretwordarray = secretword.split("");
		for(int i=0;i<secretword.length();i++){
			for(int j=0;j<secretword.length();j++){
				if(guessarray[i].equals(secretwordarray[j])){
					if(i==j){
						guessresult[i]=guessarray[i];
					}
				}
			}
		}
		for(int i=0;i<secretwordlength;i++){
			guessreturn += guessresult[i];
		}
		return guessreturn;
	}
	public static void main(String[] args){
		MasterMind mm = new MasterMind();
		
	}
}
