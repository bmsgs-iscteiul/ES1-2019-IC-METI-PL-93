package _ES._ES;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * 
 * @author Miguel Mira - 82966
 *
 */

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

	public JPanel defectCountTable(Object[][] matrix) {       
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
