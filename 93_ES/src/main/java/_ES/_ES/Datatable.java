package _ES._ES;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * 
 * @author José Gonçalves - 82694
 *
 */

public class Datatable {

	Object[][] defectMatrix; 

	public Datatable(Object[][] defectMatrix) {
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
		@SuppressWarnings("serial")
		DefaultTableModel tableModel = new DefaultTableModel(defectMatrix, columnNames){
			@Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };
		JTable table = new JTable(tableModel);
		//Row Sorter
		RowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(tableModel);
		table.setRowSorter(rowSorter);
		
//		return new JTable(defectMatrix, columnNames);
		return table;
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
