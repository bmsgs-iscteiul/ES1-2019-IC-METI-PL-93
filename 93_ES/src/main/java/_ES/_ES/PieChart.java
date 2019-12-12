package _ES._ES;
import java.text.DecimalFormat;

import javax.swing.JPanel;
import javax.swing.table.TableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * A Classe PieChart tem como objetivo gerar gráficos circulares através da leitura de uma tabela. 
 * @author Catarina Garcez - 82517
 * @version 2.1
 */

public class PieChart extends JPanel {

	private TableModel tableModel;
	
	
	/**
	 * 
	 * O construtor desta classe recebe três argumentos de forma a conseguir construir o gráfico circular. 
	 * No construtor são criados e inicializados diferentes tipos de objectos provinientes dos métodos criados nesta classe.
	 * Antes da criação do gráfico definiou-se novos formatos para a geração de Labels de forma a ser mais simples a análise do gráfico.
	 * Por fim é criado o gráfico, indicando as dimensões que o mesmo deve ocupar no painel e adicionado ao mesmo.
	 * 
	 * @param tableModel Tabela da qual se pretende retirar os dados.  
	 * @param chartTitle Título que vai ser apresentado aquando a construção do gráfico.
	 * @param column  Número da coluna de onde vão ser lidos os dados.
	 */


	public PieChart(TableModel tableModel, String chartTitle, int column) {
		this.tableModel = tableModel;

		PieDataset dataset = createDataset(column);
		// based on the dataset we create the chart
		JFreeChart chart = createChart(dataset, chartTitle);
		// create labels for the chart 
		PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{2}", new DecimalFormat("0"), new DecimalFormat("0.00%"));
		((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);
		// we put the chart into a panel
		ChartPanel chartPanel = new ChartPanel(chart);
		// default size
		chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
		
		this.add(chartPanel);
	}

	
	
	/**
	 * O método createDataset recebe o número da coluna que se pretender ler e tem como finalidade devolver um dataset.
	 * Primeiramente, é necessario contar o número de linhas e os dados que cada linha apresenta. Esta informação e retirada da coluna escolhida da tableModel passada como argumento no construtor.
	 * Percorre-se todas as linhas através de um ForEach, em cada linha e criado objecto do valor presente na mesma e onde atraves de casts se transforma o objecto lido num Boolean. 
	 * O contador previamente criado é inicializado a 0 e incrementado caso o Boolean lido na linha seja True.
	 * Por fim são criados duas variaveis a serem apresentadas como resultados no gráfico. Atraves do contador e do numero de linhas percorridas é calculado o valor da variável comErros. 
	 * A segunda variável(semErros) é facilmente calculada com base na primeira. Após se definir os valores das variáveis estes são atualizados no gráfico.

	 * @param column Número da coluna de onde vão ser lidos os dados.
	 * @return PieDataSet result, ou seja, os dados a serem apresentados no gráfico provenientes da tableModel.
	 */

	public PieDataset createDataset(int column) {

		int rows = tableModel.getRowCount();
		double counter = 0;
		
		for (int i = 0; i < rows; i++) {

			Boolean object = (Boolean)tableModel.getValueAt(i, column);
			
			if (object)
				counter++;
		}

		double comErros = counter / rows;
		double semErros = (rows - counter) / rows;

		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("C/Erros", comErros);
		result.setValue("S/Erros", semErros);
		return result;

	}
	
	
	
	/**
	 * 
	 * Este método permite criar o gráfico pieChart e recebe como argumentos o dataset devolvido no metodo anteriormente e uma String que corresponde ao título que o gráfico deve apresentar.
	 * 
	 * 
	 * @param dataset PieDataSet, ou seja, os dados a serem apresentados no gráfico provenientes da tableModel.
	 * @param title Título que vai ser apresentado aquando a construção do gráfico.
	 * @return JFreeChart, gráfico circular 3D.
	 */

	public JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(
				title,                  // chart title
				dataset,                // data
				true,                   // include legend
				true,
				false
				);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

		
		
	}

}
