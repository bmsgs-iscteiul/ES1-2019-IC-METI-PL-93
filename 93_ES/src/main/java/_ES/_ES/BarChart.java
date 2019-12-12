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
 * A Classe BarChart permite criar um gráfico de Barras apartir de uma tabela do tipo TableModel.
 * 
 * @author Júlia Monteiro - 82472
 * @version 1.3
 */


public class BarChart extends JPanel{

	
	private static final long serialVersionUID = 1L;
	private TableModel tableModel;
	private String charTitle;
	private int row;
	
	
	
	/**
	 * Constroi a BarChart atravás de uma tableModel, de uma String que representa o nome do gráfico e do número da coluna a analisar na tableModel. 
	 *	Para a criacao da BarChart sao utilizados dois metodos, o createDataset(column) e o createChart(dataset, charTitle). É tambem utilizada a classe ChartPanel que permitiu exibir a Barchart e estabelecer as suas dimensões.
	 * 
	 * @param tableModel Tabela do tipo TableModel 
	 * @param chartTitle Nome do gráfico
	 * @param column  Número da coluna a retirar os dados
	 */

	public BarChart(TableModel tableModel, String chartTitle, int column) {
		this.tableModel = tableModel;
		this.charTitle = chartTitle;
		CategoryDataset dataset = createDataset(column);
		JFreeChart Barchart = createChart(dataset, charTitle);
		ChartPanel chartPanel = new ChartPanel (Barchart);
		chartPanel.setPreferredSize((new java.awt.Dimension(600, 400)));
		this.add(chartPanel);
	}
	
	
	
	/**
	 * Este método recebe o número da coluna a analisar e foi criado com o intuito de devolver um dataset, para isso foi criado um ciclo que percorre as linhas da TableModel, para cada linha percorrida sao criados objetos com o informação da coluna dada como argumento. 
		Estes objetos são transformados numa variável boolena através de um cast.
		No caso dessse objeto ser true o contador CounterTrue é incrementado.
 		Quando todas as linhas são percorridas é calculado o número de objetos false e obtem-se o CounterFalse. 
		Por fim é calculada a percentagem de True e Falses, e são então adicionados os dados para a criacão das duas barras ao dataset.

	 * 
	 * @param column Número da coluna a retirar os dados
	 * @return Os dados retirados da tabela
	 */

	public CategoryDataset createDataset(int column) {
		row = tableModel.getRowCount();
		double counterTrue = 0;

		for (int i = 0; i < row; i++) {

			Boolean obj = (Boolean)tableModel.getValueAt(i, column);
			
			if (obj)
				counterTrue++;
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
	

	/**
	 * 
	 * Este método permite criar a Bar Chart através do dataset devolvido pelo método anterior. 
	 * 
	 * 
	 * @param dataset Dados retirados da tabela
	 * @param charTitle Nome do gráfico
	 * @return O gráfico barChart
	 */

	public JFreeChart createChart(CategoryDataset dataset, String charTitle) {

		JFreeChart barChart = ChartFactory.createBarChart(charTitle, "Boolean value", "%", dataset, PlotOrientation.VERTICAL, true, true, false);
		return barChart;
	}
	
	
	
	





	






}



	

	







