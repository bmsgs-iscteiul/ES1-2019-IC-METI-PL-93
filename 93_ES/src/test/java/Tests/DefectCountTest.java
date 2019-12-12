package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import _ES._ES.DefectCount;



class DefectCountTest {
	
	DefectCount def,def_1;
	Object[][] matrix,matrix2,matrix3,matrix4;


	@BeforeEach
	public void setUp() {	
		def= new DefectCount();
		def_1=null;
		matrix=new Object[1][4];
		matrix2=new Object[1][4];
		matrix3=new Object[1][4];
		matrix4=new Object[1][4];
		matrix[0][2]=true;
		matrix[0][3]="false";
		matrix2[0][2]=true;
		matrix2[0][3]="true";
		matrix3[0][2]=false;
		matrix3[0][3]="false";
		matrix4[0][2]=false;
		matrix4[0][3]="true";
		
	}
	
	
	@Test
	public void testNullAndNotNull() {
		assertNotNull(def);
		assertNull(def_1);
	}
	
	@Test
	public void testDefefctCountTable() {
		assertFalse(def.defectCountTable(matrix).equals(def.defectCountTable(matrix2)));
		assertFalse(def.defectCountTable(matrix).equals(def.defectCountTable(matrix3)));
		assertFalse(def.defectCountTable(matrix).equals(def.defectCountTable(matrix4)));
		assertFalse(def.defectCountTable(matrix2).equals(def.defectCountTable(matrix3)));
		assertFalse(def.defectCountTable(matrix2).equals(def.defectCountTable(matrix4)));
		assertFalse(def.defectCountTable(matrix3).equals(def.defectCountTable(matrix4)));
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
