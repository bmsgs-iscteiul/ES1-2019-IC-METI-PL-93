package _ES._ES;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * A classe Datatable é a converte os dados da análise de defeitos para uma tabela swing a inserir na MainFrame da GUI.
 * @author José Gonçalves - 82694
 * @version 1.0
 */

public class Datatable {

	Object[][] defectMatrix; 

	/**
	 * O construtor desta classe recebe como argumento um array Bidimensional de objetos, gerado pela classe App, que guarda numa variável.
	 * @param defectMatrix
	 */
	public Datatable(Object[][] defectMatrix) {
		this.defectMatrix = defectMatrix;
	}
	
	/**
	 * O método getJTable é o que devolve um objeto da classe JTable para ser inserido na MainFrame da GUI.
	 * A construção de uma DefaultTableModel é necessária para que o sorter consiga interpretar os MethodID como números, de modo a fazer a ordenação correta
	 * @return JTable table - tabela a inserir na GUI
	 */
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
		return table;
	}
	
}
