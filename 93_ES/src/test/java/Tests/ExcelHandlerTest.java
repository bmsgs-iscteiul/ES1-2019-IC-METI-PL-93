package Tests;


import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import _ES._ES.ExcelHandler;

class ExcelHandlerTest {
	
	
	ExcelHandler excel,excel2,excel3;
	File file;
	
	@BeforeEach
	void setUp() throws Exception {
	 file=new File("JUnitTest.xlsx");
	 excel= new ExcelHandler();
	 excel2= new ExcelHandler(file);
	 excel3=null;
	 
	}
	
	@Test
	public void testNullAndNotNull() {
		assertNotNull(excel);
		assertNotNull(excel2);
		assertNull(excel3);
	}
	
	@Test
	public void testGetDataMatrix() throws Exception {
		assertFalse(excel.getDataMatrix().equals(excel2.getDataMatrix()));
		
		
	}
	
	
	
	
	
	
	
	
	
	

	

}
