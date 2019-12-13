package _ES._ES;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
/**
 * Classe criada com o objetivo de realizar a deteção de defeitos apartir de uma serie de regras criadas que são definidas 
 * 	através dos TresHolds e de um operador lógico AND ou OR.
 * @author Artem Bogdan 35191
 *
 */
public class DefectDetection {
	
	private String name;
	private ArrayList<ThresHold> rules = new ArrayList<ThresHold>();
	private int logicalOperator; //1 - AND; 2 - OR
	
	/**
	 * Permite obter o operador lógico da deteção de defeitos em questão.
	 * @return Operador lógico onde 1 representa o AND e 2 representa o OR.
	 */
	public int getLogicalOperator() {
		return logicalOperator;
	}
	/**
	 * Permite definir o operador lógico de uma deteção de defeitos.
	 * @param logicalOperator Operador onde lógico 1 representa o AND e 2 representa o OR.
	 */
	public void setLogicalOperator(int logicalOperator) {
		this.logicalOperator = logicalOperator;
	}
	/**
	 * Construtor que permite criar uma deteção de defeitos só uzando o nome.
	 * @param name Nome da deteção de defeitos .
	 */
	public DefectDetection(String name){
		this.name = name;
	}
	/**
	 * Construtor que permite criar uma deteção de defeitos apartir de um threshold e um nome.
	 * @param name Nome da deteção de defeitos.
	 * @param t Threshold a ser adicionado a deteção de defeitos em questão.
	 */
	public DefectDetection(String name, ThresHold t){
		this.name = name;
		addThresHold(t);
	}
	/**
	 * 
	 * @param name Nome da deteção de defeitos.
	 * @param logicalOperator Operador lógico onde 1 representa o AND e 2 representa o OR.
	 * @param t1 Threshold a ser adicionado a deteção de defeitos em questão.
	 * @param t2 Threshold a ser adicionado a deteção de defeitos em questão.
	 */
	public DefectDetection(String name, int logicalOperator, ThresHold t1, ThresHold t2){
		this.name = name;
		this.logicalOperator = logicalOperator;
		addThresHold(t1);
		addThresHold(t2);
	}
	/**
	 * Função que permite adicionar o threshold à lista que representa as regras.
	 * @param thresHold Threshold a ser adicionado a deteção de defeitos em questão.
	 */
	public void addThresHold(ThresHold thresHold) {
		rules.add(thresHold);
	}
	/**
	 * Função que vai realizar a deteção de defeitos usando a lista de regras cujo conteúdo são os thresholds,
	 *  operador lógico euma linha de células com conteúdo excel.
	 * No caso 1 é feita deteção de erros usando o operador logico AND, no caso 2 OR. Dentro de cada caso são
	 *  verificados todos os thresholds, esta verificação tem como apoio um enumerado desenvolvido propositadamente 
	 *  	para a mesma.
	 * @param cell Uma célula excel.
	 * @return Boolean com decisão final.
	 */
	public boolean detection(Cell[] cell) {

		switch(rules.size()) {
			case 1:
				switch (rules.get(0).getOperator()) {
				case MENOR:
					try {
						if(rules.get(0).getValue() > cell[rules.get(0).getColumn()].getNumericCellValue())							
							return true;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(0).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(0).getValue() > cellDouble)
							return true;
					}
				case MAIOR:
					try {
						if(rules.get(0).getValue() < cell[rules.get(0).getColumn()].getNumericCellValue())							
							return true;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(0).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(0).getValue() < cellDouble)
							return true;
					}
				case IGUAL:
					try {
						if(rules.get(0).getValue() == cell[rules.get(0).getColumn()].getNumericCellValue())							
							return true;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(0).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(0).getValue() == cellDouble)
							return true;
					}
				case MENOR_OU_IGUAL:
					try {
						if(rules.get(0).getValue() >= cell[rules.get(0).getColumn()].getNumericCellValue())							
							return true;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(0).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(0).getValue() >= cellDouble)
							return true;
					}
				case MAIOR_OU_IGUAL:
					try {
						if(rules.get(0).getValue() <= cell[rules.get(0).getColumn()].getNumericCellValue())							
							return true;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(0).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(0).getValue() <= cellDouble)
							return true;
					}
				case DIFERENTE:
					try {
						if(rules.get(0).getValue() != cell[rules.get(0).getColumn()].getNumericCellValue())							
							return true;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(0).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(0).getValue() != cellDouble)
							return true;
					}
				}
				break;
			case 2:
				boolean rule1Output = false, rule2Output = false;
				switch (rules.get(0).getOperator()) {
				case MENOR:
					try {
						if(rules.get(0).getValue() > cell[rules.get(0).getColumn()].getNumericCellValue())							
							rule1Output = true;
						break;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(0).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(0).getValue() > cellDouble)
							rule1Output = true;
						break;
					}
				case MAIOR:
					try {
						if(rules.get(0).getValue() < cell[rules.get(0).getColumn()].getNumericCellValue())							
							rule1Output = true;
						break;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(0).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(0).getValue() < cellDouble)
							rule1Output = true;
						break;
					}
				case IGUAL:
					try {
						if(rules.get(0).getValue() == cell[rules.get(0).getColumn()].getNumericCellValue())							
							rule1Output = true;
						break;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(0).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(0).getValue() == cellDouble)
							rule1Output = true;
						break;
					}
				case MENOR_OU_IGUAL:
					try {
						if(rules.get(0).getValue() >= cell[rules.get(0).getColumn()].getNumericCellValue())							
							rule1Output = true;
						break;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(0).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(0).getValue() >= cellDouble)
							rule1Output = true;
						break;
					}
				case MAIOR_OU_IGUAL:
					try {
						if(rules.get(0).getValue() <= cell[rules.get(0).getColumn()].getNumericCellValue())							
							rule1Output = true;
						break;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(0).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(0).getValue() <= cellDouble)
							rule1Output = true;
						break;
					}
				case DIFERENTE:
					try {
						if(rules.get(0).getValue() != cell[rules.get(0).getColumn()].getNumericCellValue())							
							rule1Output = true;
						break;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(0).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(0).getValue() != cellDouble)
							rule1Output = true;
						break;
					}
				}
				switch (rules.get(1).getOperator()) {
				case MENOR:
					try {
						if(rules.get(1).getValue() > cell[rules.get(1).getColumn()].getNumericCellValue())							
							rule2Output = true;
						break;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(1).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(1).getValue() > cellDouble)
							rule2Output = true;
						break;
					}
				case MAIOR:
					try {
						if(rules.get(1).getValue() < cell[rules.get(1).getColumn()].getNumericCellValue())							
							rule2Output = true;
						break;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(1).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(1).getValue() < cellDouble)
							rule2Output = true;
						break;
					}
				case IGUAL:
					try {
						if(rules.get(1).getValue() == cell[rules.get(1).getColumn()].getNumericCellValue())							
							rule2Output = true;
						break;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(1).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(1).getValue() == cellDouble)
							rule2Output = true;
						break;
					}
				case MENOR_OU_IGUAL:
					try {
						if(rules.get(1).getValue() >= cell[rules.get(1).getColumn()].getNumericCellValue())							
							rule2Output = true;
						break;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(1).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(1).getValue() >= cellDouble)
							rule2Output = true;
						break;
					}
				case MAIOR_OU_IGUAL:
					try {
						if(rules.get(1).getValue() <= cell[rules.get(1).getColumn()].getNumericCellValue())							
							rule2Output = true;
						break;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(1).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(1).getValue() <= cellDouble)
							rule2Output = true;
						break;
					}
				case DIFERENTE:
					try {
						if(rules.get(1).getValue() != cell[rules.get(1).getColumn()].getNumericCellValue())							
							rule2Output = true;
						break;
					} catch (IllegalStateException e) {
						String cellString = cell[rules.get(1).getColumn()].toString();
						double cellDouble = Double.parseDouble(cellString);
						if(rules.get(1).getValue() != cellDouble)
							rule2Output = true;
						break;
					}
				}
				switch (logicalOperator) {
				case 1:
					return rule1Output && rule2Output;
				case 2:
					return rule1Output || rule2Output;
				default:
					break;
				}
				break;
			default:
				break;
		}
		return false;
	}
	/**
	 * Um "getter" que permite obter o nome de uma dada deteção de defeitos.
	 * @return Nome da deteção de defeitos em forma de String.
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Esta função obtém um threshold apartir do seu número de ordem na lista de regras.
	 * @param index Número de ordem	do ThresHold pretendido.
	 * @return threshold Treshold na posição indicada.
	 */
	public ThresHold getThresHold(int index) {
		return rules.get(index);
	}

}
