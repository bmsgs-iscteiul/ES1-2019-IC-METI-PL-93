package _ES._ES;

import java.io.File;

import org.apache.poi.ss.usermodel.Cell;

/**
 * 
 * @author José Gonçalves - 82694
 *
 */
public class App 
{
	private ExcelHandler e;
	private Cell[][] matrix;
	private Object[][] defectMatrix;
	
    public App() {
    	this.e = new ExcelHandler();
    	buildMatrix();
    }
    
    public void importExcelFile(File file) {
    	//Verificar se ficheiro é .xlsx
    	String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if(!name.substring(lastIndexOf).equals(".xlsx"))
        	throw new IllegalArgumentException("Invalid file type - must submit a .xlsx file");
    	else {
    		this.e = new ExcelHandler(file);
    		buildMatrix();
    	}
    }
    
    public void buildMatrix() {
    	try {
			matrix = e.getDataMatrix();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public Object[][] detectDefects(DefectDetection dd){
    	defectMatrix = new Object[matrix.length-1][4];
    	int d = 0; //0 - Default; 1 - iPlasma; 2 - PMD
    	if(dd.getName().equals("iPlasma"))
    		d = 1;
    	else if(dd.getName().equals("PMD"))
    		d = 2;

		for (int i = 0; i < matrix.length-1; i++) {
			String idString = matrix[i+1][0].toString();
			idString = idString.substring(0, idString.length() - 2);
			defectMatrix[i][0] = Integer.parseInt(idString);
			defectMatrix[i][1] = matrix[i+1][3].toString();
			
			switch(d) {
				case 0: //Outros
					defectMatrix[i][2] = dd.detection(matrix[i+1]);
					break;
				case 1: //iPlasma
					defectMatrix[i][2] = matrix[i+1][9].toString();
					break;
				case 2: //PMD
					defectMatrix[i][2] = matrix[i+1][10].toString();
					break;
			}
			defectMatrix[i][3] = matrix[i+1][8].toString().toLowerCase();
		}
		return defectMatrix;
    }

}
