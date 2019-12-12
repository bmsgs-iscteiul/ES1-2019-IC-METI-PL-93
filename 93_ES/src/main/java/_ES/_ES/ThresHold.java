package _ES._ES;

/**
 * Esta classe permite definir um ThresHold, para este ser utilizado posteriormente na detecao de defeitos.
 * package _ES._ES;
 * @author Artem Bogdan 35191
 *
 */


public class ThresHold {
	private double value;
	private Operators operator;
	private int column;
	/**
	 * Um Threshold neste caso vai ser representado por um valor um operador e por uma coluna.
	 * 
	 * @param column	coluna
	 * @param value		metrica
	 * @param operator	operador de comparacao
	 */
	
	public ThresHold(int column, double value, Operators operator) {
		this.value = value;
		this.operator=operator;	
		this.column=column;
	}
	/**
	 * Um "getter" que nos permite obter o operador do ThresHold em questao
	 * @return operador de comparacao
	 */
	public Operators getOperator() {
		return operator;
	}

	/**
	 * Um "setter" que nos permite definir o operador do ThresHold em questao
	 * @param operator operador de comparacao
	 */
	public void setOperator(Operators operator) {
		this.operator = operator;
	}
	/**
	 *  Um "getter" que nos permite obter a coluna do ThresHold em questao
	 * @return coluna
	 */
	public int getColumn() {
		return column;
	}
	/**
	 *  Um "setter" que nos permite alterar a coluna do ThresHold em questao
	 * @param column coluna
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	/**
	 *  Um "getter" que nos permite obter o valor da metrica do ThresHold em questao
	 * @return metrica
	 */
	public double getValue() {
		return value;
	}
	/**
	 *  Um "setter" que nos permite alterar o valor da metrica do ThresHold em questao
	 * @param value metrica
	 */
	public void setValue(double value) {
		this.value = value;
	}
}
