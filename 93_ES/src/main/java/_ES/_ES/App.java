package _ES._ES;

import java.io.File;

import org.apache.poi.ss.usermodel.Cell;

/**
 * A Classe App é o controlador que faz a ponte entre a classe que lê o ficheiro excel e os componentes gráficos da aplicação 
 * Tem como objetivo gerar o array bidimensional que vai alimentar os componentes gráficos, e alimentar a classe ExcelHandler com novos ficheiros excel, importados através da GUI.
 * @author José Gonçalves - 82694
 * @version 1.0
 * 
 */
public class App {

	private ExcelHandler e;
	private Cell[][] matrix;
	private Object[][] defectMatrix;
	
    /**
     * O construtor desta classe não recebe argumentos.
     * Instancia um objeto da classe ExcelHandler sem argumentos, carregando por defeito o ficheiro que está no repositório.
     * Por último invoca o método buildMatrix para preencher o array bidimensional com os dados do ficheiro excel.
     */
    public App() {
    	this.e = new ExcelHandler();
    	buildMatrix();
    }
    
    /**
     * 
     * @param file - Ficheiro a carregar na classe ExcelHandler
     */
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
    
    /**
     * 
     */
    public void buildMatrix() {
    	try {
			matrix = e.getDataMatrix();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * @param dd
     * @return
     */
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
					defectMatrix[i][2] = Boolean.parseBoolean(matrix[i+1][9].toString());
					break;
				case 2: //PMD
					defectMatrix[i][2] = Boolean.parseBoolean(matrix[i+1][10].toString());
					break;
			}
			defectMatrix[i][3] = matrix[i+1][8].toString().toLowerCase();
		}
		return defectMatrix;
    }

}
