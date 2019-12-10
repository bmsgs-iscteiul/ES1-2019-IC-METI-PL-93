package _ES._ES;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Esta classe compara, a partir de uma matriz recebida como parametro no metodo defectCountTable(Object[][] matrix),
 * o resultado dos Defeitios Detetados com a coluna is_long_method.
 * A partir dessa comparação é gerado, no metodo panelBuilding(),um JPanel apresentado na classe MainFrame. 
 * @author Miguel Mira - 82966
 * @version 2.0
 *
 */

public class DefectCount {

	private int dci;
	private int dii;
	private int adci;
	private int adii;
	
	/**
	 * Contrutor DefectCount que inicializa as variaveis dci(Defeitos corretamente identificados),dii(defeitos incorretamente identificados),
	 * adci(ausencia de defeitos corretamente identificados) e adii(ausencia de defeitos incorretamente identificados) a 0. 
	 *
	 */

	public DefectCount() {
		dci=0;
		dii=0;
		adci=0;
		adii=0;
	}
	
	/**
	 * Este metodo efetua a comparacao entre o resultado dos Defeitos detetados com 
	 * a quarta posicao(is_long_method) da matriz recebida como argumento 
	 * 
	 * @param matrix matriz de Objetos construida na classe Datatable
	 * @return retorna um JPanel com a contagem das diversas comparacoes
	 */

	public JPanel defectCountTable(Object[][] matrix) {       
		for(int i=0;i!=matrix.length;i++) {
			if(matrix[i][2].equals("TRUE") && Boolean.parseBoolean((String)matrix[i][3])==true)
				dci++;
			if(matrix[i][2].equals("TRUE") && Boolean.parseBoolean((String)matrix[i][3])==false)    
				dii++;
			if(matrix[i][2].equals("FALSE") && Boolean.parseBoolean((String)matrix[i][3])==false)   
				adci++;
			if(matrix[i][2].equals("FALSE") && Boolean.parseBoolean((String)matrix[i][3])==true)      
				adii++;
		}
		return panelBuilding();
	}
	
	
	/**
	 * Este metodo constroi um JPanel que ira ser apresentado na classe MainFrame com a contagem das diversas 
	 * comparacoes efetuadass no metodo defectCountTabel(Object[][] matrix)
	 * @return retorna um JPanel com duas colunas : uma para os defeitos e outras para a sua frequencia.
	 */

	public JPanel panelBuilding() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5,2));
		JLabel defeitos= new JLabel("Defeitos");
		JLabel contagem= new JLabel("Contagem");
		JLabel dci_= new JLabel("DCI");
		JLabel dii_= new JLabel("DII");
		JLabel adci_= new JLabel("ADCI");
		JLabel adii_= new JLabel("ADII");
		JLabel dci_cont= new JLabel(String.valueOf(dci));
		JLabel dii_cont= new JLabel(String.valueOf(dii));
		JLabel adci_cont= new JLabel(String.valueOf(adci));
		JLabel adii_cont= new JLabel(String.valueOf(adii));
		contagem.setHorizontalAlignment(JLabel.CENTER);
		dci_cont.setHorizontalAlignment(JLabel.CENTER);
		dii_cont.setHorizontalAlignment(JLabel.CENTER);
		adci_cont.setHorizontalAlignment(JLabel.CENTER);
		adii_cont.setHorizontalAlignment(JLabel.CENTER);
		Color color= new Color(192,192,192);
		defeitos.setBorder(BorderFactory.createMatteBorder(1,1,0,1,color));
		dci_.setBorder(BorderFactory.createMatteBorder(1,1,0,1,color));
		dii_.setBorder(BorderFactory.createMatteBorder(1,1,0,1,color));
		adci_.setBorder(BorderFactory.createMatteBorder(1,1,0,1,color));
		adii_.setBorder(BorderFactory.createMatteBorder(1,1,1,1,color));
		contagem.setBorder(BorderFactory.createMatteBorder(1,0,0,1,color));
		dci_cont.setBorder(BorderFactory.createMatteBorder(1,0,0,1,color));
		dii_cont.setBorder(BorderFactory.createMatteBorder(1,0,0,1,color));
		adci_cont.setBorder(BorderFactory.createMatteBorder(1,0,0,1,color));
		adii_cont.setBorder(BorderFactory.createMatteBorder(1,0,1,1,color));
		panel.add(defeitos);
		panel.add(contagem);
		panel.add(dci_);
		panel.add(dci_cont);
		panel.add(dii_);
		panel.add(dii_cont);
		panel.add(adci_);
		panel.add(adci_cont);
		panel.add(adii_);
		panel.add(adii_cont);
		return panel;
	}


}
