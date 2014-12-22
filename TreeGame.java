package Assignment3;
/*
*Sahil Sathe - 1252972
*Ricky Dua - 1243947
*Equal Contribution
*/
import java.util.Random;
import java.util.Scanner;

import Assignment3.BigBangElement.InvalidElementException;
import Assignment3.ElementTree.InvalidExpressionException;

public class TreeGame {
	BigBangElement goal;
	ElementTree tree;
	String expression;
	Scanner scanner;
	Scanner intScanner;//Separate Scanner for integers.
	private boolean isRunning;

	public TreeGame(){
		goal = new BigBangElement();
		expression = new String();
		scanner = new Scanner(System.in);
		intScanner = new Scanner(System.in);
		isRunning = true;
		Random random = new Random();
		//Goal Generation
		goal.evaluate(random.nextInt(5), random.nextInt(5));
	}

	public static void main(String[] args) {
		TreeGame game = new TreeGame();
		game.startGame();
	}

	public boolean executeCommand(){

		System.out.println("Next Command : ");
		String input = scanner.nextLine();
		if(!validateCommand(input)){
			System.err.println("Invalid Command");
			return false;
		}
		
		if(input.equalsIgnoreCase("up")){
			if(!tree.moveUp()){
				System.err.println("Already at root");
				return false;
			}
			return true;
		}
		else if(input.equalsIgnoreCase("left")){
			if(!tree.moveLeft()){
				System.err.println("Cannot move left, left child is leaf");
				return false;
			}
			return true;
		}
		else if(input.equalsIgnoreCase("right")){
			if(!tree.moveRight()){
				System.err.println("Cannot move right, right child is leaf");
				return false;
			}
			return true;
		}
		else if(input.equalsIgnoreCase("flip")){
			tree.flip();
			return true;
		}
		else if(input.equalsIgnoreCase("rotateleft")){
			if(!tree.rotateLeft()){
				System.err.println("Cannot rotate, right child is leaf");
				return false;
			}
			return true;
		}
		else if(input.equalsIgnoreCase("rotateright")){
			if(!tree.rotateRight()){
				System.err.println("Cannot rotate, left child is leaf");
				return false;
			}
			return true;
		}
		else if(input.equalsIgnoreCase("exit")){
			isRunning = false;
			return false;
		}
		return false;
	}

	private boolean validateCommand(String commandToCheck){
		String[] validCommands = {"left","right","up","flip","rotateright","rotateleft","exit"};
		for(String command : validCommands){
			if(command.equalsIgnoreCase(commandToCheck))
				return true;
		}
		return false;
	}

	public void startGame(){
		System.out.println("Welcome to Big Bang Theory Elements"+"\n"+"Your Goal is "+goal.toString()+"\n");

		System.out.println("Choose Difficulty Level"+"\n"+"0 - No Diffculty Level"
				+"\n"+"1 - Easy"+"\n"+"2 - Medium"+"\n"+"3 - Hard");

		int mode = intScanner.nextInt();
		while(!gameMode(mode)){
			if(mode > 3){
				//System.out.println("Please Choose a Valid Mode");
				mode = intScanner.nextInt();
			}else
				System.err.println("Expression doesn't satisfy the difficulty level");
		}
		
		try {
			tree = new ElementTree(expression);
		} catch (InvalidElementException | InvalidExpressionException e) {
			e.printStackTrace();
		}
		currentGameState();
		while(isRunning){
			if(tree.evaluateResult().equals(goal.getElement())){
				System.out.println("Great You Win!");
				break;
			}

			if(executeCommand()){
				currentGameState();
			}
		}
		scanner.close();
		System.exit(0);
	}

	public boolean gameMode(int mode){
		int openBracket = 0;
		switch(mode){
		case 0:
			System.out.println("No Difficulty Chosen, No Restriction");
			break;
		case 1:
			System.out.println("Easy Difficulty Chosen with Commutative Restriction (a+(b+c))");
			break;
		case 2:
			System.out.println("Medium Difficulty Chosen with Commutative Restriction ((a+b)+(c+d))");
			break;
		case 3:
			System.out.println("Hard Difficulty Chosen with Commutative Restriction (((a+b)+c)+(d+e))");
			break;
		default:
			System.err.println("Invalid Mode");
			return false;
		}
		System.out.println("Input your infix expression : ");
		expression = scanner.nextLine();

		for(char c : expression.toCharArray()){
			if(c == '('){
				openBracket++;
			}
		}

		if(openBracket != mode+1)
			return false;
		else
			return true;
	}
	public void currentGameState(){
		System.out.println("Current Tree : "+tree);
		System.out.println("Current Result : "+tree.evaluateResult());
		System.out.println("==================================");
	}
}
