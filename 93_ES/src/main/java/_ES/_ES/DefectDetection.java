package _ES._ES;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;

public class DefectDetection {
//	
	private String name;
	private ArrayList<ThresHold> rules = new ArrayList<ThresHold>();
	private int logicalOperator; //1 - AND; 2 - OR
	
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
//		System.out.println(cell[rules.get(0).getColumn()].getNumericCellValue());
		System.out.println(cell[rules.get(0).getColumn()].toString());
		switch(rules.size()) {
			case 1:
				switch (rules.get(0).getOperator()) {
				case MENOR:
						if(rules.get(0).getValue() < cell[rules.get(0).getColumn()].getNumericCellValue())							
							return true;
				case MAIOR:
						if(rules.get(0).getValue() > cell[rules.get(0).getColumn()].getNumericCellValue())
							return true;
				case IGUAL:
						if(rules.get(0).getValue() == cell[rules.get(0).getColumn()].getNumericCellValue())
							return true;
				case MENOR_OU_IGUAL:
						if(rules.get(0).getValue() <= cell[rules.get(0).getColumn()].getNumericCellValue())
							return true;
				case MAIOR_OU_IGUAL:
						if(rules.get(0).getValue() >= cell[rules.get(0).getColumn()].getNumericCellValue())
							return true;
				case DIFERENTE:
						if(rules.get(0).getValue() != cell[rules.get(0).getColumn()].getNumericCellValue())
							return true;
				}
				break;
			case 2:
				boolean rule1Output = true, rule2Output = true;
				switch (rules.get(0).getOperator()) {
				case MENOR:
						if(rules.get(0).getValue() < cell[rules.get(0).getColumn()].getNumericCellValue())							
							rule1Output = true;
						break;
				case MAIOR:
						if(rules.get(0).getValue() > cell[rules.get(0).getColumn()].getNumericCellValue())
							rule1Output = true;
						break;
				case IGUAL:
						if(rules.get(0).getValue() == cell[rules.get(0).getColumn()].getNumericCellValue())
							rule1Output = true;
						break;
				case MENOR_OU_IGUAL:
						if(rules.get(0).getValue() <= cell[rules.get(0).getColumn()].getNumericCellValue())
							rule1Output = true;
						break;
				case MAIOR_OU_IGUAL:
						if(rules.get(0).getValue() >= cell[rules.get(0).getColumn()].getNumericCellValue())
							rule1Output = true;
						break;
				case DIFERENTE:
						if(rules.get(0).getValue() != cell[rules.get(0).getColumn()].getNumericCellValue())
							rule1Output = true;
						break;
				}
				switch (rules.get(1).getOperator()) {
				case MENOR:
						if(rules.get(1).getValue() < cell[rules.get(1).getColumn()].getNumericCellValue())							
							rule2Output = true;
						break;
				case MAIOR:
						if(rules.get(1).getValue() > cell[rules.get(1).getColumn()].getNumericCellValue())
							rule2Output = true;
						break;
				case IGUAL:
						if(rules.get(1).getValue() == cell[rules.get(1).getColumn()].getNumericCellValue())
							rule2Output = true;
						break;
				case MENOR_OU_IGUAL:
						if(rules.get(1).getValue() <= cell[rules.get(1).getColumn()].getNumericCellValue())
							rule2Output = true;
						break;
				case MAIOR_OU_IGUAL:
						if(rules.get(1).getValue() >= cell[rules.get(1).getColumn()].getNumericCellValue())
							rule2Output = true;
						break;
				case DIFERENTE:
						if(rules.get(1).getValue() != cell[rules.get(1).getColumn()].getNumericCellValue())
							rule2Output = true;
						break;
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

}
