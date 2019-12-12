package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.table.DefaultTableModel;

import org.jfree.chart.JFreeChart;

import org.jfree.data.category.CategoryDataset;


import _ES._ES.BarChart;



class BarChartTest {



	private BarChart barchart;
	private int column;
	private String chartTitle;
	private CategoryDataset datasetTest;
	private JFreeChart jfreechartTest; 
	



	
	@Test 
	public void createChartTest() {

		Object[][] dataMatrix = new Object[1][5];
		
		dataMatrix[0][0] = true;
		dataMatrix[0][1] = true;
		dataMatrix[0][2] = false;
		dataMatrix[0][3] = false;
		dataMatrix[0][4] = true;

		String[] columnNames = {"Junit Test"};

		DefaultTableModel tableModelTest = new DefaultTableModel(dataMatrix, columnNames);

		column = 0;
		chartTitle = "BarChart";

		barchart = new BarChart(tableModelTest, chartTitle, column);
		
		datasetTest = barchart.createDataset(column);
		
		jfreechartTest = barchart.createChart(datasetTest, chartTitle);
		
		assertNotNull(jfreechartTest);
		assertNotNull(datasetTest);
	
	}
	
	
	@Test
	public void createDatasetTest() {

		Object[][] dataMatrix = new Object[1][5];

		dataMatrix[0][0] = true;
		dataMatrix[0][1] = true;
		dataMatrix[0][2] = false;
		dataMatrix[0][3] = false;
		dataMatrix[0][4] = true;
		
		

		String[] columnNames = {"Junit Test"};

		DefaultTableModel tableModelTest = new DefaultTableModel(dataMatrix, columnNames);
		
		barchart = new BarChart(tableModelTest, "BarChart", 0);
		datasetTest = barchart.createDataset(0);
		
		
		assertNotNull(datasetTest);
		

	}
	

	
	
	
}
		
		
