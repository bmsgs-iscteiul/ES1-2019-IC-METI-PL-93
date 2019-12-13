package _ES._ES;

/**
 * Esta classe permite definir um ThresHold apartir de um valor, operador e um número da coluna a analisar, para este ser
 * 	utilizado posteriormente na definição das regras e na deteção de defeitos.
 * @author Artem Bogdan 35191
 *
 */


public class ThresHold {
	private double value;
	private Operators operator;
	private int column;
	/**
	 * Um construtor que permite criar um novo threshold tendo por base o número da coluna a analizar, o valor e um 
	 * 	operador de comparação.
	 * 
	 * @param column	Número da coluna a ser analizada.
	 * @param value		Valor usado na comparação.
	 * @param operator	Operador de comparação.
	 */
	
	public ThresHold(int column, double value, Operators operator) {
		this.value = value;
		this.operator=operator;	
		this.column=column;
	}
	/**
	 * Um "getter" que nos permite obter o operador do ThresHold em questão.
	 * @return Operador de comparação.
	 */
	public Operators getOperator() {
		return operator;
	}

	/**
	 * Um "setter" que nos permite definir o operador do ThresHold em questão.
	 * @param operator Operador de comparação
	 */
	public void setOperator(Operators operator) {
		this.operator = operator;
	}
	/**
	 *  Um "getter" que nos permite obter a coluna analizada do ThresHold em questão
	 * @return O número da coluna a ser analizada.
	 */
	public int getColumn() {
		return column;
	}
	/**
	 *  Um "setter" que nos permite alterar a coluna analizada do ThresHold em questão.
	 * @param column coluna
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	/**
	 *  Um "getter" que nos permite obter o valor de comparação do ThresHold em questão.
	 * @return Valor a ser comparado.
	 */
	public double getValue() {
		return value;
	}
	/**
	 *  Um "setter" que nos permite definir o valor de comparação do ThresHold em questão.
	 * @param  Valor a ser comparado.
	 */
	public void setValue(double value) {
		this.value = value;
	}
}
