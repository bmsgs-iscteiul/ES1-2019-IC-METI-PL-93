package _ES._ES;
import java.io.File;
/**
 * 
 * @author Miguel Mira - 82966
 *
 */
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Esta classe tem dois metodos contrutores : um recebe um ficheiro dado como parametro e altera a variavel file consoante o ficheiro Excel dado, 
 * outro nao possui parametros e altera a variavel file para o  Excel dado para a elaboracao do projeto.
 * Devolve, atraves do metodo getDataMatrix(), uma matrix com o conteudo do ficheiro Excel file . 
 * @author Miguel Mira - 82966
 * @version 2.0
 */

public class ExcelHandler {


	private File file;
	
	/**
	 Constroi um ExcelHandler a partir do ficheiro dado para a realizacao do projeto
	 */
	
	public ExcelHandler() {
		file=new File("Long-Method.xlsx");
	}
	
	/**
	 * Constroi um ExcelHandler a partir de qualquer ficheiro Excel file que for dado como parametro
	 * @param file e um ficheiro Excel 
	 */

	public ExcelHandler(File file) {
		this.file=file;
	}
	
	
	/**
	 * Este metodo le o conteudo do ficheiro Excel file e passa essa informacao para uma matriz 
	 * @return retorna uma matriz com o conteudo do ficheiro Excel file
	 * @throws Exception usado caso o ficheiro nao consiga ser aberto ou lido com sucesso 
	 */

	public Cell[][] getDataMatrix() throws Exception{

		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb=new XSSFWorkbook(fis);
		XSSFSheet Sheet1=wb.getSheetAt(0);
		int rowcount=Sheet1.getPhysicalNumberOfRows();
		int colcount=Sheet1.getRow(0).getPhysicalNumberOfCells();
		Cell[][] matrix= new Cell[rowcount][colcount];
		for(int i=0;i!=rowcount;i++){
			for(int j=0;j!=colcount;j++) {
				matrix[i][j]=Sheet1.getRow(i).getCell(j);   
			}
		}
		wb.close();
		return matrix;
	}

}
