
/*
*Sahil Sathe - 1252972
*Ricky Dua - 1243947
*Equal Contribution
*/
package Assignment3;
public class BigBangElement {
	Element[][] table;
	Element element;
	public static final int NUMBER_OF_ROWS = 5;
	public static final int NUMBER_OF_COLOUMS = 5;

	public BigBangElement() {
		table = new Element[NUMBER_OF_ROWS][NUMBER_OF_COLOUMS];
		table[Element.Rock.ordinal()][Element.Rock.ordinal()] = Element.Rock;
		table[Element.Rock.ordinal()][Element.Paper.ordinal()] = Element.Paper;
		table[Element.Rock.ordinal()][Element.Scissors.ordinal()] = Element.Rock;
		table[Element.Rock.ordinal()][Element.Spock.ordinal()] = Element.Spock;
		table[Element.Rock.ordinal()][Element.Lizard.ordinal()] = Element.Rock;

		table[Element.Paper.ordinal()][Element.Rock.ordinal()] = Element.Paper;
		table[Element.Paper.ordinal()][Element.Paper.ordinal()] = Element.Paper;
		table[Element.Paper.ordinal()][Element.Scissors.ordinal()] = Element.Scissors;
		table[Element.Paper.ordinal()][Element.Spock.ordinal()] = Element.Paper;
		table[Element.Paper.ordinal()][Element.Lizard.ordinal()] = Element.Lizard;
		
		table[Element.Scissors.ordinal()][Element.Rock.ordinal()] = Element.Rock;
		table[Element.Scissors.ordinal()][Element.Paper.ordinal()] = Element.Scissors;
		table[Element.Scissors.ordinal()][Element.Scissors.ordinal()] = Element.Scissors;
		table[Element.Scissors.ordinal()][Element.Spock.ordinal()] = Element.Spock;
		table[Element.Scissors.ordinal()][Element.Lizard.ordinal()] = Element.Scissors;

		table[Element.Spock.ordinal()][Element.Rock.ordinal()] = Element.Spock;
		table[Element.Spock.ordinal()][Element.Paper.ordinal()] = Element.Paper;
		table[Element.Spock.ordinal()][Element.Scissors.ordinal()] = Element.Spock;
		table[Element.Spock.ordinal()][Element.Spock.ordinal()] = Element.Spock;
		table[Element.Spock.ordinal()][Element.Lizard.ordinal()] = Element.Lizard;
		
		table[Element.Lizard.ordinal()][Element.Rock.ordinal()] = Element.Rock;
		table[Element.Lizard.ordinal()][Element.Paper.ordinal()] = Element.Lizard;
		table[Element.Lizard.ordinal()][Element.Scissors.ordinal()] = Element.Scissors;
		table[Element.Lizard.ordinal()][Element.Spock.ordinal()] = Element.Lizard;
		table[Element.Lizard.ordinal()][Element.Lizard.ordinal()] = Element.Lizard;
	}
	
	public Element evaluate(Element element1, Element element2){
		element = table[element1.ordinal()][element2.ordinal()];
		return element;
	}
	
	public Element evaluate(int element1,int element2){
		element = table[element1][element2];
		return element;
	}

	public Element getElement(String elementName) throws InvalidElementException {
		elementName = Character.toString(elementName.charAt(0)).toUpperCase() + elementName.substring(1).toLowerCase();
		try {
			return Element.valueOf(elementName);
		} catch (Exception e) {
			throw new InvalidElementException("Invalid Element : " + elementName);
		}
	}
	
	public Element getElement(){
		return element;
	}

	public class InvalidElementException extends Exception {
		private static final long serialVersionUID = 1L;

		public InvalidElementException(String message) {
			super(message);
		}
	}
	
	@Override
	public String toString() {
		return element.name();
	}

	public static void main(String[] args) {
		//test
		try {
			System.out.println(new BigBangElement().getElement("lizard"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
