package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import _ES._ES.DefectDetection;
import _ES._ES.ExcelHandler;
import _ES._ES.Operators;
import _ES._ES.ThresHold;

class DefectDetectionTest {
	ArrayList<ThresHold> th= new ArrayList<ThresHold>();
	ArrayList<DefectDetection> dd= new ArrayList<DefectDetection>();
	Cell[][] matriz;
	ExcelHandler excel;
	
	@BeforeEach
	void setUp() throws Exception {
		File file = new File ("Long-Method.xlsx");
		excel= new ExcelHandler(file);
		matriz=excel.getDataMatrix();
		
		th.add(new ThresHold(4,523,Operators.IGUAL));
		th.add(new ThresHold(4,523,Operators.DIFERENTE));
		th.add(new ThresHold(4,522,Operators.IGUAL));
		th.add(new ThresHold(4,522,Operators.DIFERENTE));
		th.add(new ThresHold(7,0.3,Operators.IGUAL));
		th.add(new ThresHold(7,0.3,Operators.DIFERENTE));
		th.add(new ThresHold(7,0.2,Operators.IGUAL));
		th.add(new ThresHold(7,0.2,Operators.DIFERENTE));
		
		dd.add(new DefectDetection("name", 1, th.get(0), th.get(1)));
		dd.add(new DefectDetection("name", 1, th.get(2), th.get(3)));
		dd.add(new DefectDetection("name", 1, th.get(4), th.get(5)));
		dd.add(new DefectDetection("name", 1, th.get(6), th.get(7)));
		
		
		dd.add(new DefectDetection("name", 1, th.get(1), th.get(0)));
		dd.add(new DefectDetection("name", 1, th.get(3), th.get(2)));
		dd.add(new DefectDetection("name", 1, th.get(5), th.get(4)));
		dd.add(new DefectDetection("name", 1, th.get(7), th.get(6)));
		
		dd.add(new DefectDetection("name", 2, th.get(0), th.get(1)));
		dd.add(new DefectDetection("name", 2, th.get(2), th.get(3)));
		dd.add(new DefectDetection("name", 2, th.get(4), th.get(5)));
		dd.add(new DefectDetection("name", 2, th.get(6), th.get(7)));
		
		dd.add(new DefectDetection("name", 2, th.get(1), th.get(0)));
		dd.add(new DefectDetection("name", 2, th.get(3), th.get(2)));
		dd.add(new DefectDetection("name", 2, th.get(5), th.get(4)));
		dd.add(new DefectDetection("name", 2, th.get(7), th.get(6)));
		
	}
	
	@Test
	public void add() {
		dd.get(0).addThresHold(th.get(0));
	}
	
	@Test
	public void getters_setters(){
		dd.get(0).getName();
		dd.get(0).setName("name");
		dd.get(0).getLogicalOperator();
		dd.get(0).setLogicalOperator(2);
		dd.get(0).getThresHold(0);
	}
	
	@Test
	public void detection() {
		for (int i = 0; i < dd.size(); i++) {
			dd.get(i).detection(matriz[14]);
		}
	}
	
}
