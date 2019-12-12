package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import _ES._ES.App;
import _ES._ES.DefectDetection;
import _ES._ES.Operators;
import _ES._ES.ThresHold;

class AppTest {

	App app;
	DefectDetection iPlasma, pmd, longMethod;
	
	@BeforeEach
	void setUp() throws Exception {
		app = new App();
	}
	
	@Test
	public void testSuccessOnBuildMatrixAndImport() {
		app.buildMatrix();
//      System.out.println("Working Directory = " + System.getProperty("user.dir"));
		
		File file = new File(System.getProperty("user.dir") + "/tests/Long-Method.xlsx");
		String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        assertTrue(name.substring(lastIndexOf).equals(".xlsx"));
		app.importExcelFile(file);
	}
	
	@Test
	public void testIllegalArgumentExceptionThrownOnImport() {
		File file = new File(System.getProperty("user.dir") + "/tests/Enunciado.pdf");
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		assertFalse(name.substring(lastIndexOf).equals(".xlsx"));
		try {
			app.importExcelFile(file);
			fail("Devia ter lançado exceção");
		} catch (IllegalArgumentException e) {
			
		}
	}
	
	@Test
	public void testDefectDetection() {
		iPlasma = new DefectDetection("iPlasma");
		pmd = new DefectDetection("PMD");
		longMethod = new DefectDetection("long_method",
				1,
				new ThresHold(4, 80, Operators.MAIOR),
				new ThresHold(5, 10, Operators.MAIOR)
		);
		app.buildMatrix();
		app.detectDefects(iPlasma);
		app.detectDefects(pmd);
		app.detectDefects(longMethod);
	}
}
