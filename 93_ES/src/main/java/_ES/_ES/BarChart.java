package _ES._ES;

import javax.swing.JPanel;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
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


	public CategoryDataset createDataset(int column) {
		row = tableModel.getRowCount();
		double counterTrue = 0;

		for (int i = 0; i < row; i++) {

			Object obj = tableModel.getValueAt(i, column);
			if(obj instanceof String) {
				String value = (String) obj;
				if (Boolean.parseBoolean(value)) {
					counterTrue++;
				}
			}
		}
		
		double counterFalse = row - counterTrue;
		double percentagemErros = (counterTrue/row);
		percentagemErros *= 10000;
		percentagemErros = Math.round(percentagemErros);
		percentagemErros /= 100;
		double percetagemSemErros = (counterFalse/row);
		percetagemSemErros *= 10000;
		percetagemSemErros = Math.round(percetagemSemErros);
		percetagemSemErros /= 100;

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue( percentagemErros, "Percentagem com Erros" , percentagemErros + "%");
		dataset.addValue( percetagemSemErros, "Percentagem sem Erros" , percetagemSemErros + "%");

		return dataset;
	}

	public JFreeChart createChart(CategoryDataset dataset, String charTitle) {

		JFreeChart barChart = ChartFactory.createBarChart(charTitle, "Boolean value", "%", dataset, PlotOrientation.VERTICAL, true, true, false);
		return barChart;
	}
	
	
	
	





	






}



	

	







