package _ES._ES;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JPanel;
import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.charts.ChartDataFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 * 
 * @author Júlia Monteiro - 82472
 *
 */

public class BarChart extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TableModel tableModel;
	private String charTitle;
	private int row;


	public BarChart(TableModel tableModel, String chartTitle, int column) {

		this.tableModel = tableModel;
		this.charTitle = chartTitle;

		CategoryDataset dataset = createDataset(column);

		JFreeChart Barchart = createChart(dataset, charTitle);

		ChartPanel chartPanel = new ChartPanel (Barchart);
		chartPanel.setPreferredSize((new java.awt.Dimension(600, 400)));


		this.add(chartPanel);

	}


	private CategoryDataset createDataset(int column) {

		int row = tableModel.getRowCount();

		int counterTrue = 0;
		int counterFalse = 0;

		for (int i = 0; i < row; i++) {

			Object obj = tableModel.getValueAt(i, column);


			if(obj instanceof String) {
				String value = (String) obj;
				System.out.println(" Converti : " + value + "");

				boolean booleano = Boolean.parseBoolean(value);

				if (booleano == true) {
					counterTrue++;
				}
			}
		}

		double percentagemErros = counterTrue/row;
		double percetagemSemErros = (row - counterTrue)/row;

		System.out.println(charTitle + ": Com Erros = " + percentagemErros + "%   | Sem Erros = " + percetagemSemErros + "%");

		final String comErros = "Percentagem com Erros";
		final String semErros = "Percentagem sem Erros";


		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue( 3, comErros , percentagemErros + "%");
		dataset.addValue( 6, semErros , percetagemSemErros + "%");

		return dataset;

	}

	private JFreeChart createChart(CategoryDataset dataset, String charTitle) {

		JFreeChart barChart = ChartFactory.createBarChart(charTitle, "Boolean value", "Score", dataset, PlotOrientation.VERTICAL, true, true, false);

		return barChart;
	}
	
	
	





	






}



	

	







