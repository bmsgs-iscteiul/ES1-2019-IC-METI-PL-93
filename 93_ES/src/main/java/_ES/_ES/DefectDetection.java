package _ES._ES;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;

public class DefectDetection {
//	
	private String name;
	private ArrayList<ThresHold> rules = new ArrayList<ThresHold>();
	
	public DefectDetection(String name){
		this.name = name;
	}
	
	public void addThresHold(ThresHold thresHold) {
		rules.add(thresHold);
	}
	
	public boolean detection(Cell[] cell) throws Exception {
		
		
		for(int i =0; i<rules.size(); i++) {						
			
			int column = rules.get(i).getColumn();					
			
			switch (rules.get(i).getOperator()) {
			
				case MENOR:
						if(rules.get(i).getValue() < cell[column-1].getNumericCellValue())							
							return false;
				case MAIOR:
						if(rules.get(i).getValue() > cell[column-1].getNumericCellValue())
							return false;
				case IGUAL:
						if(rules.get(i).getValue() == cell[column-1].getNumericCellValue())
							return false;
				case MENOR_OU_IGUAL:
						if(rules.get(i).getValue() <= cell[column-1].getNumericCellValue())
							return false;
				case MAIOR_OU_IGUAL:
						if(rules.get(i).getValue() >= cell[column-1].getNumericCellValue())
							return false;
				case DIFERENTE:
						if(rules.get(i).getValue() != cell[column-1].getNumericCellValue())
							return false;
			}
		}
		return true;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
