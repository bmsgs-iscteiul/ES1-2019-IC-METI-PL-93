package _ES._ES;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * 
 * @author José Gonçalves - 82694
 *
 */

public class Datatable {

	String[][] defectMatrix; 

	public Datatable(String[][] defectMatrix) {
		this.defectMatrix = defectMatrix;
	}
	
	public JTable getJTable() { 
		// Column Names 
		String[] columnNames = new String[4];
		columnNames[0] = "Method ID";
		columnNames[1] = "Method Name";
		columnNames[2] = "Defect Detetion Result";
		columnNames[3] = "is_long_method";
        // Initializing the JTable 
		return new JTable(defectMatrix, columnNames);
	}
	
	//TESTES - APAGAR QUANDO A CLASSE ESTIVER PRONTA
	//
	public static void main(String args[]) throws Exception{
		JFrame frame = new JFrame();  
        frame.setTitle("Teste JUnit");
        App app = new App();
        JScrollPane sp = new JScrollPane(new Datatable(app.detectDefects(null)).getJTable()); 
        frame.add(sp); 
        // Frame Size 
        frame.setSize(800, 600); 
        // Frame Visible = true 
        frame.setVisible(true);
	}
	//
	
}
