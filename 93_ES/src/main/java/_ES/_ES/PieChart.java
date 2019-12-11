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
 * A Classe PieChart tem como objetivo gerar graficos circulares atraves da leitura da classe DataTable presente no projecto. 
 * @author Catarina Garcez - 82517
 * @version 2.0
 */

public class PieChart extends JPanel {

	private TableModel tableModel;
	
	
	/**
	 * 
	 * O construtor desta classe recebe tres argumentos de forma a conseguir construir o grafico circular. 
	 * No construtor sao criados e inicializados diferentes tipos de objectos provinientes dos metodos criados nesta classe.
	 * Antes da criacao do grafico definiou-se novos formatos para a geracao de Labels de forma a ser mais simples a analise do grafico.
	 * Por fim e criado o grafico, indicando as dimensoes que o mesmo deve ocupar no painel e adicionado ao mesmo.
	 * 
	 * @param tableModel Tabela da qual se pretende retirar os dados.  
	 * @param chartTitle Titulo que vai ser apresentado aquando a construçao do grafico.
	 * @param column  Numero da coluna de onde vao ser lidos os dados.
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
	 * O metodo createDataset recebe o numero da coluna que se pretender ler e tem como finalidade devolver um dataset.
	 * Primeiramente, e necessario contar o numero de linhas e os dados que cada linha apresenta. Esta informacao e retirada da coluna escolhida da tableModel passada como argumento no construtor.
	 * Percorre-se todas as linhas atraves de um ForEach, em cada linha e criado objecto do valor presente na mesma e onde atraves de casts se transforma o objecto lido num Boolean. 
	 * O contador previamente criado e inicializado a 0 e incrementado caso o Boolean lido na linha seja True.
	 * Por fim sao criados duas variaveis a serem apresentadas como resultados no grafico. Atraves do contador e do numero de linhas percorridas e calculado o valor da variavel comErros. 
	 * A segunda variavel(semErros) e facilmente calculada com base na primeira. Apes se definir os valores das variaveis estes sao atualizados no grafico.

	 * @param column Numero da coluna de onde vao ser lidos os dados.
	 * @return PieDataSet result, ou seja, os dados a serem apresentados no grafico provenientes da tableModel.
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
	 * Este metodo permite criar o grafico pieChart e recebe como argumentos o dataset devolvido no metodo anteriormente e uma String que corresponde ao titulo que o grafico deve apresentar.
	 * 
	 * 
	 * @param dataset PieDataSet, ou seja, os dados a serem apresentados no grafico provenientes da tableModel.
	 * @param Title Titulo que vai ser apresentado aquando a construcao do grafico.
	 * @return JFreeChart, grafico circular 3D.
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
