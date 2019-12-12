package Tests;

import javax.swing.table.DefaultTableModel;
import org.jfree.data.general.DefaultPieDataset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import _ES._ES.App;
import _ES._ES.DefectDetection;
import _ES._ES.PieChart;

class PieChartTest {


	
	DefaultTableModel tableModel;
	private PieChart pieChart1;
	String title;
	int column;	
	App app;
	DefectDetection iPlasma;
	
    @SuppressWarnings("serial")
	@BeforeEach
    public void setUp() {

    	title = "iPlasma";
    	column = 2;
		String[] columnNames = {"Method ID", "Method Name", "Defect Detetion Result","is_long_method"};
		app = new App();
		iPlasma = new DefectDetection("iPlasma");
		app.buildMatrix();
		
		tableModel = new DefaultTableModel(app.detectDefects(iPlasma), columnNames){
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
        pieChart1 = new PieChart(tableModel, title, column);
    	
    }

    @Test
    public void test() {
    	DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("comErros", new Double(43.2));
        result.setValue("semErros", new Double(17.5));
        pieChart1.createChart(result, title);
    }
}
	    
	
		
		


	 
	 










