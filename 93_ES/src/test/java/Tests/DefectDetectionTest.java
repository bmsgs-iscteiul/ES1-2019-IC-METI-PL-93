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
//		excel = new ExcelHandler();
		File file = new File(System.getProperty("user.dir") + "/tests/teste_defect_detection.xlsx");
		excel = new ExcelHandler(file);
		matriz = excel.getDataMatrix();
		
		
	}
	
	@Test
	public void testIgual() {
		dd = new ArrayList<DefectDetection>();
		dd.add(new DefectDetection("name", 1, new ThresHold(4,10,Operators.IGUAL), new ThresHold(5,20.2,Operators.IGUAL)));
		dd.add(new DefectDetection("name2", 1, new ThresHold(4,10.1,Operators.IGUAL), new ThresHold(5,20,Operators.IGUAL)));
		dd.add(new DefectDetection("name3", 2, new ThresHold(4,10,Operators.IGUAL), new ThresHold(5,20.2,Operators.IGUAL)));
		dd.add(new DefectDetection("name4", 2, new ThresHold(4,10.1,Operators.IGUAL), new ThresHold(5,20,Operators.IGUAL)));
		for (int j = 0; j < dd.size(); j++) {
			dd.get(j).detection(matriz[1]);
			dd.get(j).detection(matriz[2]);
			dd.get(j).detection(matriz[3]);
			dd.get(j).detection(matriz[4]);
		}
	}
	
	@Test
	public void testDiferente() {
		dd = new ArrayList<DefectDetection>();
		dd.add(new DefectDetection("name", 1, new ThresHold(4,10,Operators.DIFERENTE), new ThresHold(5,20.2,Operators.DIFERENTE)));
		dd.add(new DefectDetection("name2", 1, new ThresHold(4,10.1,Operators.DIFERENTE), new ThresHold(5,20,Operators.DIFERENTE)));
		dd.add(new DefectDetection("name3", 2, new ThresHold(4,10,Operators.DIFERENTE), new ThresHold(5,20.2,Operators.DIFERENTE)));
		dd.add(new DefectDetection("name4", 2, new ThresHold(4,10.1,Operators.DIFERENTE), new ThresHold(5,20,Operators.DIFERENTE)));
		for (int j = 0; j < dd.size(); j++) {
			dd.get(j).detection(matriz[1]);
			dd.get(j).detection(matriz[2]);
			dd.get(j).detection(matriz[3]);
			dd.get(j).detection(matriz[4]);
		}
	}
	
	@Test
	public void testMaior() {
		dd = new ArrayList<DefectDetection>();
		dd.add(new DefectDetection("name", 1, new ThresHold(4,10,Operators.MAIOR), new ThresHold(5,20.2,Operators.MAIOR)));
		dd.add(new DefectDetection("name2", 1, new ThresHold(4,10.1,Operators.MAIOR), new ThresHold(5,20,Operators.MAIOR)));
		dd.add(new DefectDetection("name3", 2, new ThresHold(4,10,Operators.MAIOR), new ThresHold(5,20.2,Operators.MAIOR)));
		dd.add(new DefectDetection("name4", 2, new ThresHold(4,10.1,Operators.MAIOR), new ThresHold(5,20,Operators.MAIOR)));
		for (int j = 0; j < dd.size(); j++) {
			dd.get(j).detection(matriz[1]);
			dd.get(j).detection(matriz[2]);
			dd.get(j).detection(matriz[3]);
			dd.get(j).detection(matriz[4]);
		}
	}
	
	@Test
	public void testMaiorOuIgual() {
		dd = new ArrayList<DefectDetection>();
		dd.add(new DefectDetection("name", 1, new ThresHold(4,10,Operators.MAIOR_OU_IGUAL), new ThresHold(5,20.2,Operators.MAIOR_OU_IGUAL)));
		dd.add(new DefectDetection("name2", 1, new ThresHold(4,10.1,Operators.MAIOR_OU_IGUAL), new ThresHold(5,20,Operators.MAIOR_OU_IGUAL)));
		dd.add(new DefectDetection("name3", 1, new ThresHold(4,0,Operators.MAIOR_OU_IGUAL), new ThresHold(5,0,Operators.MAIOR_OU_IGUAL)));
		dd.add(new DefectDetection("name4", 1, new ThresHold(4,11111110,Operators.MAIOR_OU_IGUAL), new ThresHold(5,11111110,Operators.MAIOR_OU_IGUAL)));
		dd.add(new DefectDetection("name5", 2, new ThresHold(4,10,Operators.MAIOR_OU_IGUAL), new ThresHold(5,20.2,Operators.MAIOR_OU_IGUAL)));
		dd.add(new DefectDetection("name6", 2, new ThresHold(4,10.1,Operators.MAIOR_OU_IGUAL), new ThresHold(5,20,Operators.MAIOR_OU_IGUAL)));
		dd.add(new DefectDetection("name7", 2, new ThresHold(4,0,Operators.MAIOR_OU_IGUAL), new ThresHold(5,0,Operators.MAIOR_OU_IGUAL)));
		dd.add(new DefectDetection("name8", 2, new ThresHold(4,11111110,Operators.MAIOR_OU_IGUAL), new ThresHold(5,11111110,Operators.MAIOR_OU_IGUAL)));
		for (int j = 0; j < dd.size(); j++) {
			dd.get(j).detection(matriz[1]);
			dd.get(j).detection(matriz[2]);
			dd.get(j).detection(matriz[3]);
			dd.get(j).detection(matriz[4]);
		}
	}
	
	@Test
	public void testMenor() {
		dd = new ArrayList<DefectDetection>();
		dd.add(new DefectDetection("name", 1, new ThresHold(4,10,Operators.MENOR), new ThresHold(5,20.2,Operators.MENOR)));
		dd.add(new DefectDetection("name2", 1, new ThresHold(4,10.1,Operators.MENOR), new ThresHold(5,20,Operators.MENOR)));
		dd.add(new DefectDetection("name3", 1, new ThresHold(4,0,Operators.MENOR), new ThresHold(5,0,Operators.MENOR)));
		dd.add(new DefectDetection("name4", 1, new ThresHold(4,11111110,Operators.MENOR), new ThresHold(5,11111110,Operators.MENOR)));
		dd.add(new DefectDetection("name5", 2, new ThresHold(4,10,Operators.MENOR), new ThresHold(5,20.2,Operators.MENOR)));
		dd.add(new DefectDetection("name6", 2, new ThresHold(4,10.1,Operators.MENOR), new ThresHold(5,20,Operators.MENOR)));
		dd.add(new DefectDetection("name7", 2, new ThresHold(4,0,Operators.MENOR), new ThresHold(5,0,Operators.MENOR)));
		dd.add(new DefectDetection("name8", 2, new ThresHold(4,11111110,Operators.MENOR), new ThresHold(5,11111110,Operators.MENOR)));
		for (int j = 0; j < dd.size(); j++) {
			dd.get(j).detection(matriz[1]);
			dd.get(j).detection(matriz[2]);
			dd.get(j).detection(matriz[3]);
			dd.get(j).detection(matriz[4]);
		}
	}
	
	@Test
	public void testMenorOuIgual() {
		dd = new ArrayList<DefectDetection>();
		dd.add(new DefectDetection("name", 1, new ThresHold(4,10,Operators.MENOR_OU_IGUAL), new ThresHold(5,20.2,Operators.MENOR_OU_IGUAL)));
		dd.add(new DefectDetection("name2", 1, new ThresHold(4,10.1,Operators.MENOR_OU_IGUAL), new ThresHold(5,20,Operators.MENOR_OU_IGUAL)));
		dd.add(new DefectDetection("name3", 1, new ThresHold(4,0,Operators.MENOR_OU_IGUAL), new ThresHold(5,0,Operators.MENOR_OU_IGUAL)));
		dd.add(new DefectDetection("name4", 1, new ThresHold(4,11111110,Operators.MENOR_OU_IGUAL), new ThresHold(5,11111110,Operators.MENOR_OU_IGUAL)));
		dd.add(new DefectDetection("name5", 2, new ThresHold(4,10,Operators.MENOR_OU_IGUAL), new ThresHold(5,20.2,Operators.MENOR_OU_IGUAL)));
		dd.add(new DefectDetection("name6", 2, new ThresHold(4,10.1,Operators.MENOR_OU_IGUAL), new ThresHold(5,20,Operators.MENOR_OU_IGUAL)));
		dd.add(new DefectDetection("name7", 2, new ThresHold(4,0,Operators.MENOR_OU_IGUAL), new ThresHold(5,0,Operators.MENOR_OU_IGUAL)));
		dd.add(new DefectDetection("name8", 2, new ThresHold(4,11111110,Operators.MENOR_OU_IGUAL), new ThresHold(5,11111110,Operators.MENOR_OU_IGUAL)));
		for (int j = 0; j < dd.size(); j++) {
			dd.get(j).detection(matriz[1]);
			dd.get(j).detection(matriz[2]);
			dd.get(j).detection(matriz[3]);
			dd.get(j).detection(matriz[4]);
		}
	}
	
	@Test
	public void getters_setters(){
		dd = new ArrayList<DefectDetection>();
		dd.add(new DefectDetection("name2", 1, new ThresHold(4,523,Operators.MAIOR), new ThresHold(5,0.2,Operators.MAIOR)));
		dd.get(0).getName();
		dd.get(0).setName("name");
		dd.get(0).getLogicalOperator();
		dd.get(0).setLogicalOperator(2);
		dd.get(0).getThresHold(0);
	}
	
	@Test
	public void testOneThresHold() {
		dd = new ArrayList<DefectDetection>();
		dd.add(new DefectDetection("name", new ThresHold(4,10,Operators.IGUAL)));
		dd.add(new DefectDetection("name2", new ThresHold(4,10.1,Operators.IGUAL)));
		dd.add(new DefectDetection("name3", new ThresHold(4,0,Operators.IGUAL)));
		dd.add(new DefectDetection("name4", new ThresHold(4,11111110,Operators.IGUAL)));
		dd.add(new DefectDetection("name5", new ThresHold(5,20.2,Operators.IGUAL)));
		dd.add(new DefectDetection("name6", new ThresHold(5,20,Operators.IGUAL)));
		dd.add(new DefectDetection("name7", new ThresHold(5,0,Operators.IGUAL)));
		dd.add(new DefectDetection("name8", new ThresHold(5,11111110,Operators.IGUAL)));
		dd.add(new DefectDetection("name9", new ThresHold(4,10,Operators.DIFERENTE)));
		dd.add(new DefectDetection("name10", new ThresHold(4,10.1,Operators.DIFERENTE)));
		dd.add(new DefectDetection("name11", new ThresHold(4,0,Operators.DIFERENTE)));
		dd.add(new DefectDetection("name12", new ThresHold(4,11111110,Operators.DIFERENTE)));
		dd.add(new DefectDetection("name13", new ThresHold(5,20.2,Operators.DIFERENTE)));
		dd.add(new DefectDetection("name14", new ThresHold(5,20,Operators.DIFERENTE)));
		dd.add(new DefectDetection("name15", new ThresHold(5,0,Operators.DIFERENTE)));
		dd.add(new DefectDetection("name16", new ThresHold(5,11111110,Operators.DIFERENTE)));
		dd.add(new DefectDetection("name17", new ThresHold(4,10,Operators.MAIOR)));
		dd.add(new DefectDetection("name18", new ThresHold(4,10.1,Operators.MAIOR)));
		dd.add(new DefectDetection("name19", new ThresHold(4,0,Operators.MAIOR)));
		dd.add(new DefectDetection("name20", new ThresHold(4,11111110,Operators.MAIOR)));
		dd.add(new DefectDetection("name21", new ThresHold(5,20.2,Operators.MAIOR)));
		dd.add(new DefectDetection("name22", new ThresHold(5,20,Operators.MAIOR)));
		dd.add(new DefectDetection("name23", new ThresHold(5,0,Operators.MAIOR)));
		dd.add(new DefectDetection("name24", new ThresHold(5,11111110,Operators.MAIOR)));
		dd.add(new DefectDetection("name25", new ThresHold(4,10,Operators.MENOR)));
		dd.add(new DefectDetection("name26", new ThresHold(4,10.1,Operators.MENOR)));
		dd.add(new DefectDetection("name27", new ThresHold(4,0,Operators.MENOR)));
		dd.add(new DefectDetection("name28", new ThresHold(4,11111110,Operators.MENOR)));
		dd.add(new DefectDetection("name29", new ThresHold(5,20.2,Operators.MENOR)));
		dd.add(new DefectDetection("name30", new ThresHold(5,20,Operators.MENOR)));
		dd.add(new DefectDetection("name31", new ThresHold(5,0,Operators.MENOR)));
		dd.add(new DefectDetection("name32", new ThresHold(5,11111110,Operators.MENOR)));
		dd.add(new DefectDetection("name33", new ThresHold(4,10,Operators.MAIOR_OU_IGUAL)));
		dd.add(new DefectDetection("name34", new ThresHold(4,10.1,Operators.MAIOR_OU_IGUAL)));
		dd.add(new DefectDetection("name35", new ThresHold(4,0,Operators.MAIOR_OU_IGUAL)));
		dd.add(new DefectDetection("name36", new ThresHold(4,11111110,Operators.MAIOR_OU_IGUAL)));
		dd.add(new DefectDetection("name37", new ThresHold(5,20.2,Operators.MAIOR_OU_IGUAL)));
		dd.add(new DefectDetection("name38", new ThresHold(5,20,Operators.MAIOR_OU_IGUAL)));
		dd.add(new DefectDetection("name39", new ThresHold(5,0,Operators.MAIOR_OU_IGUAL)));
		dd.add(new DefectDetection("name40", new ThresHold(5,11111110,Operators.MAIOR_OU_IGUAL)));
		dd.add(new DefectDetection("name41", new ThresHold(4,10,Operators.MENOR_OU_IGUAL)));
		dd.add(new DefectDetection("name42", new ThresHold(4,10.1,Operators.MENOR_OU_IGUAL)));
		dd.add(new DefectDetection("name43", new ThresHold(4,0,Operators.MENOR_OU_IGUAL)));
		dd.add(new DefectDetection("name44", new ThresHold(4,11111110,Operators.MENOR_OU_IGUAL)));
		dd.add(new DefectDetection("name45", new ThresHold(5,20.2,Operators.MENOR_OU_IGUAL)));
		dd.add(new DefectDetection("name46", new ThresHold(5,20,Operators.MENOR_OU_IGUAL)));
		dd.add(new DefectDetection("name47", new ThresHold(5,0,Operators.MENOR_OU_IGUAL)));
		dd.add(new DefectDetection("name48", new ThresHold(5,11111110,Operators.MENOR_OU_IGUAL)));
		for (int j = 0; j < dd.size(); j++) {
			dd.get(j).detection(matriz[1]);
			dd.get(j).detection(matriz[2]);
			dd.get(j).detection(matriz[3]);
			dd.get(j).detection(matriz[4]);
		}
	}
	
	@Test
	public void testiPlasmaThresHold() {
		dd = new ArrayList<DefectDetection>();
		dd.add(new DefectDetection("iPlasma"));
	}
	
	@Test
	public void testWrongLogicalOperator() {
		dd = new ArrayList<DefectDetection>();
		dd.add(new DefectDetection("name2", 3, new ThresHold(4,523,Operators.MAIOR), new ThresHold(5,0.2,Operators.MAIOR)));
		dd.get(0).detection(matriz[1]);
	}
	
}
