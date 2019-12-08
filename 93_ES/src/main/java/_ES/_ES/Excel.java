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


public class Excel {
	private File file;

	public Excel() {
		file=new File("Long-Method.xlsx");
	}

	public Excel(File file) {
		this.file=file;
	}

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
