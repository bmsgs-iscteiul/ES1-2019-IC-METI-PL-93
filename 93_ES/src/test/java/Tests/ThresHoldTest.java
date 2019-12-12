package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import _ES._ES.Operators;
import _ES._ES.ThresHold;

class ThresHoldTest {

	ThresHold t;	
	
	@BeforeEach
	void setUp() {
		t= new ThresHold(4,523,Operators.DIFERENTE);
	}
	
	@Test
	void tests() {
		t.getColumn();
		t.getOperator();
		t.getValue();
		t.setColumn(3);
		t.setOperator(Operators.DIFERENTE);
		t.setValue(100);
	}
	

}
