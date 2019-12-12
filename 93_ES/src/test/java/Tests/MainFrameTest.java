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
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import _ES._ES.MainFrame;

class MainFrameTest {


	MainFrame mainFrame;
	JPanel mainPanel;
	JPanel westpanel;
	JPanel eastPanel;
	JPanel northPanel;
	JPanel centerPanel;
	JPanel southPanel;
	JButton importProjectButton;
	ArrayList<Component> components;

	@BeforeEach
	public void setUp() {
		try {
			mainFrame = new MainFrame();
			mainFrame.doFrame();
			mainFrame.addFrameContent();

			components = new ArrayList<Component>();
			/*
			 * 0 - JList
			 * 1 - JButton Add
			 * 2 - JButton Edit
			 * 3 - JButton Remove
			 * 4 - JButton Import Excel
			 * 5 - JButton Datatable
			 * 6 - JButton Bar Chart
			 * 7 - JButton Pie Chart
			 */

			//////////////////////////////////////
			Component[] comp = mainFrame.getFrame().getComponents();
			Component[] comp2 = ((JRootPane)comp[0]).getComponents();
			for (int i = 0;i<comp2.length;i++) {
				if (comp2[i] instanceof JPanel) {
				} else if (comp2[i] instanceof JLayeredPane) {
					Component[] comp3 = ((JLayeredPane)comp2[i]).getComponents();
					Component[] comp4 = ((JPanel)comp3[0]).getComponents();
					for (int j = 0;j<comp4.length;j++) {
						if (comp4[j] instanceof JPanel) { //eastpanel
							Component[] comp5 = ((JPanel)comp4[j]).getComponents();
							Component[] comp6 = ((JPanel)comp5[0]).getComponents();
							for (int k = 0;k<comp6.length;k++) {
								Component[] comp7 = ((JPanel)comp6[k]).getComponents();
								for (int l = 0;l<comp7.length;l++) {
									if (comp7[l] instanceof JScrollPane) { //SubEastPanel-0 é JList
										Component[] comp8 = ((JScrollPane)comp7[l]).getComponents();
										for (int m = 0;m<comp8.length;m++) {
											if (comp8[m] instanceof JViewport) { //SubEastPanel-0 é JList
												Component[] comp9 = ((JViewport)comp8[m]).getComponents();
												if (comp9[0] instanceof JList) {
													components.add(comp9[0]);
												}
											}
										}
									} else if (comp7[l] instanceof JButton) { //SubEastPanel-0 é o painel com os botões add edit remove; SubEastPanel-2 é o dos botões do import e gráficos
										System.out.println(((JButton)comp7[l]).getText() );
										components.add(comp7[l]);
									}
								}
							}
						}
					}
				}
			}

			//////////////////////////////////////

		} catch (FileSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testSelectListAndCharts() {
		if(((JList)components.get(0)).getSelectedIndex() == 0) {
			((JList)components.get(0)).setSelectedIndex(1);
		} else {
			((JList)components.get(0)).setSelectedIndex(0);
		}
		((JButton)components.get(5)).doClick();
		((JButton)components.get(6)).doClick();
		((JButton)components.get(7)).doClick();
	}

	@Test
	public void testAddNewDefectDetection() {
		((JButton)components.get(1)).doClick();
	}

}
