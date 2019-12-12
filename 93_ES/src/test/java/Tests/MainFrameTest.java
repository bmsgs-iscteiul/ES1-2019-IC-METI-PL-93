package Tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.FileSystemException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import _ES._ES.MainFrame;

class MainFrameTest {

	JFrame frame;
	MainFrame mainFrame;
	JPanel mainPanel;
	JPanel westpanel;
	JPanel eastPanel;
	JPanel northPanel;
	JPanel centerPanel;
	JPanel southPanel;
	JButton importProjectButton;


	@Test
	public void testDoFrameAndContent() throws FileSystemException, IOException {
		frame = new JFrame();
		mainFrame = new MainFrame();		
		mainFrame.doFrame();
		mainFrame.addFrameContent();
		
		westpanel = new JPanel();
		eastPanel = new JPanel();
		northPanel = new JPanel();
		centerPanel = new JPanel();
		southPanel = new JPanel();
		

		Component[] comp = frame.getComponents();
		for (int i = 0;i<comp.length;i++) {
			if (comp[i] instanceof JPanel) {
				if(comp[i].equals(mainPanel)){
					for (int j = 0;j<mainPanel.getComponentCount();j++) {
						if(comp[j].equals(eastPanel)) {
							for (int x = 0; x<eastPanel.getComponentCount();x++) {
								if(comp[i].equals(northPanel)) {
									for (int y = 0; y<northPanel.getComponentCount();y++) {
										//Fazer caso seja JList e cada botao
										((JButton) comp[i]).doClick();
									}
								}
								else if(comp[i].equals(centerPanel)) {

								}
								else if(comp[i].equals(southPanel)) {

								}
							}
						}
						else if (comp[i].equals(westpanel)) {

						}
					}
				}
			}
		}
	}

	@Test
	public void testAddImportProjectButton() {
		//		mf.addContentImportProjectButton();
		//		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		//		JPanel mainPanel = new JPanel();
		//		assertEquals(dim.width, mainPanel.getWidth());
		//		assertEquals(dim.height, mainPanel.getHeight());
		//		assertEquals(BorderLayout.NORTH, mainPanel.getLayout());
		//		
		//		
		//		JPanel eastPanel = new JPanel();
		//		assertEquals(350, eastPanel.getWidth());
		//		assertEquals(dim.height, eastPanel.getHeight());
		//		assertEquals(new GridLayout(3,1), eastPanel.getLayout());
	}

























}
