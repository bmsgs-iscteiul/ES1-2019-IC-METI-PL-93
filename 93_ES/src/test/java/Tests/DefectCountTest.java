package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import _ES._ES.DefectCount;



class DefectCountTest {
	
	DefectCount def,def_1;
	Object[][] matrix;

	@BeforeEach
	public void setUp() {	
		matrix= new Object[9][9];
		matrix[0][2]="TRUE";
		matrix[0][3]=false;
		def= new DefectCount();
		def_1=null;
		
	}
	
	
	@Test
	public void testNullAndNotNull() {
		assertNotNull(def);
		assertNull(def_1);
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
