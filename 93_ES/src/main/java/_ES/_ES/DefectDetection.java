package _ES._ES;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
/**
 * Classe criada com o objetivo de realizar a detecao de defeitos apartir de uma serie de regras criadas definidas atraves dos TresHolds
 * @author Artem Bogdan 35191
 *
 */
public class DefectDetection {
//	
	private String name;
	private ArrayList<ThresHold> rules = new ArrayList<ThresHold>();
	private int logicalOperator; //1 - AND; 2 - OR
	
	public int getLogicalOperator() {
		return logicalOperator;
	}

	public void setLogicalOperator(int logicalOperator) {
		this.logicalOperator = logicalOperator;
	}

	public DefectDetection(String name){
		this.name = name;
	}
	
	public DefectDetection(String name, ThresHold t){
		this.name = name;
		addThresHold(t);
	}
	
	public DefectDetection(String name, int logicalOperator, ThresHold t1, ThresHold t2){
		this.name = name;
		this.logicalOperator = logicalOperator;
		addThresHold(t1);
		addThresHold(t2);
	}
	
	public void addThresHold(ThresHold thresHold) {
		rules.add(thresHold);
	}
	
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Esta funcao obtem um ThresHold apartir do seu numero de ordem na lista de regras
	 * @param index numero de ordem	
	 * 
	 * @return
	 */
	public ThresHold getThresHold(int index) {
		return rules.get(index);
	}

}
