package Tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.nio.file.FileSystemException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import _ES._ES.MainFrame;

class MainFrameTest {
	
	JFrame frame;
	MainFrame mf;
	
	
	@Test
	public void testDoFrame() throws FileSystemException, IOException {
		frame = new JFrame("Defect Detection In Software Projects");
		String frameTitle ="Defect Detection In Software Projects";
		assertTrue(frame.getTitle().equals(frameTitle));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		assertNotNull(frame);
		assertEquals(dim.width, frame.getWidth());
		assertEquals(dim.height, frame.getHeight());
		assertTrue(frame.isVisible());
		mf.doFrame();
	}
	
	@Test
	public void testAddFrameContent() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		JPanel mainPanel = new JPanel();
		assertEquals(dim.width, mainPanel.getWidth());
		assertEquals(dim.height, mainPanel.getHeight());
		assertEquals(BorderLayout.NORTH, mainPanel.getLayout());
		
		
		JPanel eastPanel = new JPanel();
		assertEquals(350, eastPanel.getWidth());
		assertEquals(dim.height, eastPanel.getHeight());
		assertEquals(new GridLayout(3,1), eastPanel.getLayout());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}
