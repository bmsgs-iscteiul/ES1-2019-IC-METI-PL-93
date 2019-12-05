package _ES._ES;


public class ThresHold {
	
	private int value;
	private Operators operator;
	private int column;
	
	
	public ThresHold(int column, int value, Operators operator) {
		this.value = value;
		this.operator=operator;	
		this.column=column;
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
