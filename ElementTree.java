package Assignment3;
/*
*Sahil Sathe - 1252972
*Ricky Dua - 1243947
*Equal Contribution
*/
import Assignment3.BigBangElement.InvalidElementException;

public class ElementTree {
	BinaryTreeNode currentNode;
	BigBangElement element;
	BinaryTreeNode root;

	public ElementTree(String expression) throws InvalidElementException, InvalidExpressionException {
		element = new BigBangElement();
		root = createTree(expression);
		currentNode = root;
	}

	@Override
	public String toString() {
		return root.toString();
	}

	public Element evaluateResult(){
		return root.getElement();
	}
	
	public boolean rotateRight(){
		if(currentNode.getLeftChild().hasChildren()){
			BinaryTreeNode parent = currentNode.getParent();
			BinaryTreeNode newRoot = currentNode.getLeftChild();
			boolean wasLeftChild = false;
			boolean wasChild = parent != null;

			if(wasChild){
				wasLeftChild = parent.getLeftChild() == currentNode;
			}
			//changing links, ends up rotating right
			currentNode.setLeftChild(currentNode.getLeftChild().getRightChild());
			currentNode.getLeftChild().getParent().setRightChild(currentNode);
			currentNode.getLeftChild().getParent().setParent(null);
			currentNode.setParent(currentNode.getLeftChild().getParent());
			currentNode.getLeftChild().setParent(currentNode);

			//if rotated within a subtree then fix parent link and its children
			if(wasChild){
				if(wasLeftChild){
					parent.setLeftChild(currentNode.getParent());
					parent.getLeftChild().setParent(parent);
				}
				else{
					parent.setRightChild(currentNode.getParent());
					parent.getRightChild().setParent(parent);
				}
			}
			
			//refer back to root so reevaluate method can reevaluate the whole tree
			while(newRoot.getParent() != null){
				newRoot = newRoot.getParent();
			}
			reevaluate(newRoot);
			return true;
		}
		return false;
	}
	
	public boolean rotateLeft(){
		if(currentNode.getRightChild().hasChildren()){
			BinaryTreeNode parent = currentNode.getParent();
			BinaryTreeNode newRoot = currentNode.getRightChild();
			boolean wasRightChild = false;
			boolean wasChild = parent != null;

			if(wasChild){
				wasRightChild = parent.getRightChild() == currentNode;
			}
			//changing links, ends up rotating left
			currentNode.setRightChild(currentNode.getRightChild().getLeftChild());
			currentNode.getRightChild().getParent().setLeftChild(currentNode);
			currentNode.getRightChild().getParent().setParent(null);
			currentNode.setParent(currentNode.getRightChild().getParent());
			currentNode.getRightChild().setParent(currentNode);
			
			//if rotated within a subtree then fix parent link and its children
			if(wasChild){
				if(wasRightChild){
					parent.setRightChild(currentNode.getParent());
					parent.getRightChild().setParent(parent);
				}
				else{
					parent.setLeftChild((currentNode.getParent()));
					parent.getLeftChild().setParent(parent);
				}
			}
			
			//refer back to root so reevaluate method can reevaluate the whole tree
			while(newRoot.getParent() != null){
				newRoot = newRoot.getParent();
			}
			reevaluate(newRoot);
			return true;
		}
		return false;
	}

	public boolean moveLeft(){
		if(currentNode.getLeftChild().hasChildren()){
			currentNode = currentNode.getLeftChild();
			return true;
		}
		else
			return false;
	}

	public boolean moveRight(){
		if(currentNode.getRightChild().hasChildren()){
			currentNode = currentNode.getRightChild();
			return true;
		}
		else
			return false;
	}

	public boolean moveUp(){
		if(currentNode != root){
			currentNode = currentNode.getParent();
			return true;
		}else 
			return false;
	}

	public boolean flip(){
		if(currentNode.hasChildren()){
			BinaryTreeNode copy = currentNode.getLeftChild();
			currentNode.setLeftChild(currentNode.getRightChild());
			currentNode.setRightChild(copy);
			reevaluate(root);
			return true;
		}else 
			return false;
	}

	private void reevaluate(BinaryTreeNode root){
		BinaryTreeNode node = root;
		if(node.getLeftChild().hasChildren()){
			reevaluate(node.getLeftChild());
		}	

		if(node.getRightChild().hasChildren()){
			reevaluate(node.getRightChild());
		}
		node.setElement(element.evaluate(node.getLeftChild().getElement(), node.getRightChild().getElement()));
		this.root = node;
	}

	public BinaryTreeNode createTree(String expression) throws InvalidElementException, InvalidExpressionException{
		if (!checkExpression(expression)) {
			throw new InvalidExpressionException("Invalid Parentheses or Operator");
		}

		int operatorIndex = 0;
		int bracketCount = 0;
		boolean isFound = false;
		for (char c : expression.toCharArray()) {
			switch(c) {
			case '(':
				bracketCount++;
				break;
			case ')':
				bracketCount--;
				break;
			case '+':
				if (bracketCount == 1) {
					isFound = true;
				}
				break;
			}
			if (isFound) {
				break;
			}
			operatorIndex++;
		}
		String leftOperand = expression.substring(1, operatorIndex++);
		String rightOperand = expression.substring(operatorIndex, expression.length()-1);	
		BinaryTreeNode node = new BinaryTreeNode();

		if (leftOperand.contains("+")){
			node.setLeftChild(createTree(leftOperand));
			node.getLeftChild().setParent(node);
		} else{
			node.setLeftChild(new BinaryTreeNode());
			node.getLeftChild().setElement(element.getElement(leftOperand));
			node.getLeftChild().setParent(node);
		}

		if (rightOperand.contains("+")){
			node.setRightChild(createTree(rightOperand));
			node.getRightChild().setParent(node);
		} else {
			node.setRightChild(new BinaryTreeNode());
			node.getRightChild().setElement(element.getElement(rightOperand));
			node.getRightChild().setParent(node);
		}
		node.setElement(element.evaluate(node.getLeftChild().getElement(),node.getRightChild().getElement()));
		return node;
	}

	private boolean checkExpression(String expression) {
		int openBracket = 0;
		int closedBracket = 0;
		int operatorCount = 0;
		for (char c : expression.toCharArray()) {
			switch(c) {
			case '(':
				openBracket++;
				break;
			case ')':
				closedBracket++;
				break;
			case '+':
				operatorCount++;
				break;
			}
		}
		if (openBracket != closedBracket) {
			return false;
		} else if (openBracket != operatorCount) {
			return false;
		} else {
			return true;
		}
	}

	public class InvalidExpressionException extends Exception{
		private static final long serialVersionUID = 1L;
		public InvalidExpressionException(String message) {
			super(message);
		}
	}

	private class BinaryTreeNode{
		private Element element;
		private BinaryTreeNode leftChild;
		private BinaryTreeNode rightChild;
		private BinaryTreeNode parent;

		public Element getElement() {
			return element;
		}
		public void setElement(Element element) {
			this.element = element;
		}
		public BinaryTreeNode getLeftChild() {
			return leftChild;
		}
		public void setLeftChild(BinaryTreeNode leftChild) {
			this.leftChild = leftChild;
		}
		public BinaryTreeNode getRightChild() {
			return rightChild;
		}
		public void setRightChild(BinaryTreeNode rightChild) {
			this.rightChild = rightChild;
		}
		public boolean hasChildren() {
			return !(leftChild == null) && !(rightChild == null);
		}
		public BinaryTreeNode getParent() {
			return parent;
		}
		public void setParent(BinaryTreeNode parent) {
			this.parent = parent;
		}
		public String toString() {
			String result = "";
			if (leftChild.hasChildren()) {
				result += "(" + leftChild.toString();
			} else {
				result += "(" + leftChild.element;
			}
			if (this == currentNode) {
				result += "<+>";
			} else {
				result += "+";
			}
			if (rightChild.hasChildren()) {
				result += rightChild.toString() + ")";
			} else {
				result += rightChild.element + ")";
			}
			return result;
		}
	}

	public static void main(String[] args) {
		try {
			ElementTree tree = new ElementTree("(((lizard+rock)+lizard)+(lizard+spock))");
			//tree.moveRight();
			//tree.moveLeft();
			//tree.moveUp();
			tree.rotateLeft();
			System.out.println(tree+" "+tree.evaluateResult());
		} catch (InvalidElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
