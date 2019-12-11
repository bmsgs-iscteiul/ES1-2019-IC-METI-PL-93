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
 * A Classe BarChart e uma classe que permite criar um gráfico de Barras apartir de uma tabela.
 * @author Julia Monteiro - 82472
 * @version 1.0
 */


public class BarChart extends JPanel{

	
	private static final long serialVersionUID = 1L;
	private TableModel tableModel;
	private String charTitle;
	private int row;
	
	
	
	/**
	 * Constroi a BarChart atraves de uma tableModel, de uma String que representa o nome do grafico e do numero da coluna a analisar na tableModel. 
	 *	Para a criacao da BarChart sao utilizados dois metodos, o createDataset(column) e o createChart(dataset, charTitle). E tambem utilizada a classe ChartPanel que permitiu exibir a Barchart e estabelecer as suas dimensoes.
	 * 
	 * @param tableModel Tabela 
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
	 * Este metodo recebe o número da coluna a analisar e foi criado com o intuito de devolver um dataset, para isso e necessário ir buscar o numero de linhas da tabela e atraves de um ciclo estas mesma linhas sao percorridas, para cada linha percorrida sao criados objetos que vao obter a string que esta na coluna. 
		No caso dessa string ser true o contador CounterTrue e incrementado, depois de todas todas as linhas serem percorridas através da diferença do número de linhas da tabela e do CounterTrue e criado o CounterFalse. 
		Por fim e calculada a percentagem de True e Falses e sao entao adicionados as dados para a criacao das duas barras ao dataset.
	 * 
	 * @param column Numero da coluna a retirar os dados
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
	 * Este metodo permite criar a Bar Chart atraves do dataset devolvido pelo metodo anterior. 
	 * 
	 * 
	 * @param dataset Dados retirados da tabela
	 * @param charTitle Nome do grafico
	 * @return O grafico barChart
	 */

	public JFreeChart createChart(CategoryDataset dataset, String charTitle) {

		JFreeChart barChart = ChartFactory.createBarChart(charTitle, "Boolean value", "%", dataset, PlotOrientation.VERTICAL, true, true, false);
		return barChart;
	}
	
	
	
	





	






}



	

	







