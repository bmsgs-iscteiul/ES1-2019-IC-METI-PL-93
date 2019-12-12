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
	ArrayList<ThresHold> th;
	ArrayList<DefectDetection> dd;
	Cell[][] matriz;
	ExcelHandler excel;
	
	@BeforeEach
	void setUp() throws Exception {
		excel = new ExcelHandler();
		matriz = excel.getDataMatrix();
		th= new ArrayList<ThresHold>();
		dd= new ArrayList<DefectDetection>();
	}
	
	@Test
	public void testIgual() {
		th.add(new ThresHold(4,523,Operators.IGUAL));
		th.add(new ThresHold(5,0.2,Operators.IGUAL));
		dd.add(new DefectDetection("name", 1, th.get(0), th.get(1)));
		dd.get(0).detection(matriz[14]);
	}
	
	@Test
	public void testMaior() {
		th.add(new ThresHold(4,523,Operators.MAIOR));
		th.add(new ThresHold(5,0.2,Operators.MAIOR));
		dd.add(new DefectDetection("name", 1, th.get(0), th.get(1)));
		dd.get(0).detection(matriz[14]);
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
	
	@Test
	public void detectionV() {
		for (int i = 0; i < matriz[7].length; i++) {
			try {
				System.out.println(matriz[7][i].getNumericCellValue());
			} catch (IllegalStateException e) {
				System.out.println(matriz[7][i].getStringCellValue());
			}
		}
	}
}
