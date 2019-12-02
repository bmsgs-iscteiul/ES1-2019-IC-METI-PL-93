package _ES._ES;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;

public class DefectDetection {
//	
	private ArrayList<ThresHold> rules = new ArrayList<ThresHold>();
	private Excel excel;
	
	public DefectDetection(Excel excel){
		this.excel = excel;
	}
	
	public void addThresHold(ThresHold thresHold) {
		rules.add(thresHold);
	}
	
	public boolean detection() throws Exception {
		
		for(int i =0; i<rules.size(); i++) {			
			
			int column = rules.get(i).getColumn();
			int line = rules.get(i).getLine();		
			Cell[][] matrix= excel.getDataMatrix();
			
			switch (rules.get(i).getOperator()) {
			
				case MENOR:
						if(rules.get(i).getValue() < matrix[line-1][column-1].getNumericCellValue())							
							break;
						return false;
				case MAIOR:
						if(rules.get(i).getValue() > matrix[line-1][column-1].getNumericCellValue())
							break;
						return false;
				case IGUAL:
						if(rules.get(i).getValue() == matrix[line-1][column-1].getNumericCellValue())
							break;
						return false;
				case MENOR_OU_IGUAL:
						if(rules.get(i).getValue() <= matrix[line-1][column-1].getNumericCellValue())
							break;
						return false;
				case MAIOR_OU_IGUAL:
						if(rules.get(i).getValue() >= matrix[line-1][column-1].getNumericCellValue())
							break;
						return false;
				case DIFERENTE:
						if(rules.get(i).getValue() != matrix[line-1][column-1].getNumericCellValue())
							break;
						return false;
			}
		}
		return true;
	}
}
