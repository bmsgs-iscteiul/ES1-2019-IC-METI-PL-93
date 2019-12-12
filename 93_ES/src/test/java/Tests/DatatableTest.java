package Tests;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import _ES._ES.App;
import _ES._ES.Datatable;
import _ES._ES.DefectDetection;

	public class DatatableTest {

		App app;
		Datatable datatable;
		DefectDetection iPlasma;
		
		@BeforeEach
		public void setUp() {
			app = new App();
		}

		@Test
		public void test() {
			iPlasma = new DefectDetection("iPlasma");
			app.buildMatrix();
			
			datatable = new Datatable(app.detectDefects(iPlasma));
			JFrame frame = new JFrame();  
	        frame.setTitle("Teste JUnit");
	        
	        JScrollPane sp = new JScrollPane(datatable.getJTable()); 
	        frame.add(sp); 
	        frame.setSize(500, 200);  
	        frame.setVisible(true);
		}

	

}
