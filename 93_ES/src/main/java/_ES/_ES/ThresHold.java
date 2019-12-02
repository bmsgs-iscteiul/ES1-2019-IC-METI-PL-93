package _ES._ES;


public class ThresHold {
//
	private int column;
	private int line;
	private int value;
	private Operators operator;
	
	public ThresHold(int column, int line, int value, Operators operator) {
		this.column = column;
		this.line = line;
		this.value = value;
		this.operator=operator;	
	}

	public Operators getOperator() {
		return operator;
	}

	public void setOperator(Operators operator) {
		this.operator = operator;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
