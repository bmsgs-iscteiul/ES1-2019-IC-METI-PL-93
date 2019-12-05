package _ES._ES;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DefectCount {

	private int dci;
	private int dii;
	private int adci;
	private int adii;

	public DefectCount() {
		dci=0;
		dii=0;
		adci=0;
		adii=0;
	}


	public JPanel defectCountTable(Object[][] matrix) {         //BIA invoca este método(devolve painel com contagem de defeitos) 
		for(int i=0;i!=matrix.length;i++) {
			if(Boolean.parseBoolean((String)matrix[i][2])==true && Boolean.parseBoolean((String)matrix[i][3])==true)
				dci++;
			if(Boolean.parseBoolean((String)matrix[i][2])==true && Boolean.parseBoolean((String)matrix[i][3])==false)    
				dii++;
			if(Boolean.parseBoolean((String)matrix[i][2])==false && Boolean.parseBoolean((String)matrix[i][3])==false)   
				adci++;
			if(Boolean.parseBoolean((String)matrix[i][2])==false && Boolean.parseBoolean((String)matrix[i][3])==true)      
				adii++;
		}
		return panelBuilding();
	}


	public JPanel panelBuilding() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JLabel titulo= new JLabel("Contagem de defeitos");
		titulo.setHorizontalAlignment(JLabel.CENTER);
		panel.add(titulo, BorderLayout.NORTH);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(5,2));
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
		defeitos.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		dci_.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		dii_.setBorder(BorderFactory.createEmptyBorder(0,10, 0, 0));
		adci_.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		adii_.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		panel2.add(defeitos);
		panel2.add(contagem);
		panel2.add(dci_);
		panel2.add(dci_cont);
		panel2.add(dii_);
		panel2.add(dii_cont);
		panel2.add(adci_);
		panel2.add(adci_cont);
		panel2.add(adii_);
		panel2.add(adii_cont);
		panel.add(panel2, BorderLayout.CENTER);
		return panel;


	}


}
