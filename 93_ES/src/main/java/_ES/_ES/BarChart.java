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
	private int counterTrue;
	private int counterFalse;


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

		int row = tableModel.getRowCount();
		System.out.println("o row é :" +row);

		double counterTrue = 0;
	

		for (int i = 0; i < row; i++) {

			Object obj = tableModel.getValueAt(i, column);
			System.out.println(i);


			if(obj instanceof String) {
				String value = (String) obj;
				System.out.println(" Converti : " + value + "");

				boolean booleano = Boolean.parseBoolean(value);

				if (booleano == true) {
					counterTrue++;
			//		System.out.println(counterTrue);
				}
			}
			
		}
		
		//System.out.println("counter sem erros" + (row - counterTrue) );
		//System.out.println("counter depois do if" + counterTrue);
		
		
		double counterFalse = row - counterTrue;
		//System.out.println("CounterFalse" + counterFalse);
		
		double percentagemErros = (counterTrue/row);
		//System.out.println("percentagemErros" + percentagemErros);
		double percetagemSemErros = (counterFalse/row);
		//System.out.println("percentagem Sem Erros" + percetagemSemErros);

		//System.out.println(charTitle + ": Com Erros = " + percentagemErros + "%   | Sem Erros = " + percetagemSemErros + "%");

		final String comErros = "Percentagem com Erros";
		final String semErros = "Percentagem sem Erros";


		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue( percentagemErros, comErros , percentagemErros + "%");
		dataset.addValue( percetagemSemErros, semErros , percetagemSemErros + "%");

		return dataset;

	}

	public JFreeChart createChart(CategoryDataset dataset, String charTitle) {

		JFreeChart barChart = ChartFactory.createBarChart(charTitle, "Boolean value", "%", dataset, PlotOrientation.VERTICAL, true, true, false);

		return barChart;
	}
	
	
	
	





	






}



	

	







