package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.junit.jupiter.api.Test;

import _ES._ES.App;
import _ES._ES.Datatable;
import _ES._ES.PieChart;
import junit.framework.TestSuite;

class PieChartTest {


	private TableModel tableModel;
	private PieChart pieChart1;
	String title;
	int column;
	PieDataset dataset;
	
	
	    @Test
	    public void setUp() {

	    	pieChart1= new PieChart(tableModel, title, column);
	    	
	        DefaultPieDataset result = new DefaultPieDataset();
	        result.setValue("comErros", new Double(43.2));
	        result.setValue("semErros", new Double(17.5));
	        pieChart1.createChart(result, title);
	       
	        
	        
	        
//	        final DefaultPieDataset pieDataset = new DefaultPieDataset(new DefaultPieDataset(result));
//	        PiePlot piePlot = new PiePlot(pieDataset);
//	        piePlot.setToolTipGenerator(new StandardPieToolTipGenerator());
//	        
	    }
	    
		}
		
		


	 
	 










