package _ES._ES;

import java.io.File;

import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.Cell;

/**
 * 
 * @author José Gonçalves - 82694
 *
 */
public class App 
{
	private Excel e;
	private Cell[][] matrix;
	private Object[][] defectMatrix;
	
    public App() {
    	this.e = new Excel();
    	buildMatrix();
    }
    
    public void importExcelFile(File file) {
    	//Verificar se ficheiro é .xlsx
    	String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if(!name.substring(lastIndexOf).equals(".xlsx"))
        	throw new IllegalArgumentException("Invalid file type - must submit a .xlsx file");
    	else {
    		this.e = new Excel(file);
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
			
			//defectMatrix[i][2] = dd.detection(matrix[i]);
			switch(d) {
			case 0:
				defectMatrix[i][2] = dd.detection(matrix[i]);
				break;
			case 1:
				defectMatrix[i][2] = matrix[i+1][9].toString();
				break;
			case 2:
				defectMatrix[i][2] = matrix[i+1][10].toString();
				break;
			}
			
			//TESTES - APAGAR QUANDO O MÉTODO ESTIVER PRONTO
			//
//			if(i % 2 == 0)
//				defectMatrix[i][2] = "true";
//			else 
//				defectMatrix[i][2] = "false";
			//
			
			defectMatrix[i][3] = matrix[i+1][8].toString().toLowerCase();
		}
		return defectMatrix;
    }
    
    public TableModel getTableModel() {
    	return null;
    }
    
}
