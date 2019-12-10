package _ES._ES;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
/**
 * 
 * @author Beatriz Gomes - 82195
 *
 */

public class MainFrame {

	private JFrame frame;
	ArrayList<DefectDetection> ddList;
	private JList<String> listOfDD;
	private JPanel westPanel;
	private App app;
	private Object[][] dataMatrix;
	DefectCount dc;
	
	public MainFrame() throws FileSystemException, IOException {
		app = new App();
		dc = new DefectCount();
		ddList = new ArrayList<DefectDetection>();
		DefectDetection ddIPlasma = new DefectDetection("iPlasma");
		DefectDetection ddPMD = new DefectDetection("PMD");
		ddList.add(ddIPlasma);
		ddList.add(ddPMD);		
	
		String[] data = {"iPlasma", "PMD"};
		listOfDD = new JList<String>(data);
		
	    listOfDD.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

		ListSelectionListener listSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				boolean adjust = listSelectionEvent.getValueIsAdjusting();
				if (!adjust) {
					@SuppressWarnings("rawtypes")
					JList list = (JList) listSelectionEvent.getSource();
					int selections[] = list.getSelectedIndices();
					@SuppressWarnings("deprecation")
					Object selectionValues[] = list.getSelectedValues();
					for (int i = 0, n = selections.length; i < n; i++) {
						System.out.print(selections[i] + "/" + selectionValues[i] + " ");
						int length = ddList.size();
						for(int j = 0; j < length; j++) {
						    if (ddList.get(j).getName().equals(selectionValues[i])) {
						    	System.out.println("Selected " + selectionValues[i]);
						    	dataMatrix = app.detectDefects(ddList.get(j));
						    	dc.defectCountTable(dataMatrix);
						    }
						}
					}
				}
			}
		};
		listOfDD.addListSelectionListener(listSelectionListener);

		doFrame();
		addFrameContent();
	}

	private void doFrame() {
		frame = new JFrame("Defect Detection In Software Projects");
		frame.setTitle("Defect Detection In Software Projects");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);			
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();		
//		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
//		frame.setSize(1200, 670);	
		frame.setSize(dim.width, dim.height);
		frame.setVisible(true);
	}

	private void addFrameContent() throws FileSystemException, IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//MAIN PANEL
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setSize(screenSize.width, screenSize.height); //1200,670
		frame.add(mainPanel, BorderLayout.NORTH);

		//EAST PANEL
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new GridLayout(3,1));
		eastPanel.setPreferredSize(new Dimension(350,screenSize.height)); //350,670
		mainPanel.add(eastPanel, BorderLayout.EAST);

		//NORTH PANEL 
		final JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout());
		eastPanel.add(northPanel);

		String titleNorthPanel = "Defect Detections";
		Border borderNorthPanel = BorderFactory.createTitledBorder(titleNorthPanel);
		northPanel.setBorder(borderNorthPanel);

		listOfDD.setVisibleRowCount(10);
		listOfDD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		northPanel.add(listOfDD);

		JScrollPane listScroller = new JScrollPane(listOfDD);
		listScroller.setPreferredSize(new Dimension(320, 180)); //270, 250
		northPanel.add(listScroller);		

		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String titleNorthPanel = listOfDD.getSelectedValue().toString();
				Border borderNorthPanel = BorderFactory.createTitledBorder(titleNorthPanel);
				mainPanel.setBorder(borderNorthPanel);

				addContentEditButton();			
			}
		});
		northPanel.add(editButton);	

		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFrameContentNewButton();
			}
		});
		northPanel.add(addButton);	

		JButton removeButton = new JButton("Remove");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFrameContentRemoveButton();
			}
		});
		northPanel.add(removeButton);	
	
		
		//CENTER PANEL -> ADICIONAR A JFRAME DO MIRA -> DEFEITOS / CONTAGEM
		JPanel centerPanel = new JPanel();
		eastPanel.add(centerPanel);

		String titleCenterPanel = "Defect Count";
		Border borderCenterPanel = BorderFactory.createTitledBorder(titleCenterPanel);
		centerPanel.setBorder(borderCenterPanel);
		
		JPanel panelCount = dc.panelBuilding();
		panelCount.setPreferredSize(new Dimension(300,200));
		centerPanel.add(panelCount);
		

		//SOUTH PANEL 
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		eastPanel.add(southPanel);
	

		String titleSouthPanel = "View";
		Border borderSouthPanel = BorderFactory.createTitledBorder(titleSouthPanel);
		southPanel.setBorder(borderSouthPanel);
		
		JButton importProjectButton = new JButton("Import Excel File Long-Method");
		importProjectButton.setPreferredSize(new Dimension(275,35));
		importProjectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addContentImportProjectButton();
			}
		});
		southPanel.add(importProjectButton);

		JButton dataTableButton = new JButton("Data Table");
		dataTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String selectedTool = listOfDD.getSelectedValue();
				if(selectedTool != null) {
					String titleMainPanel = listOfDD.getSelectedValue().toString();
					Border borderMainPanel = BorderFactory.createTitledBorder(titleMainPanel);
					mainPanel.setBorder(borderMainPanel);
					
					Datatable datatable = new Datatable(dataMatrix);
					//VER SE É ADICIONADO O SCROLLPANE
					JPanel tablePanel = new JPanel();
					tablePanel.add(datatable.getJTable());
					JScrollPane scrollPane = new JScrollPane(tablePanel);
					westPanel.removeAll();
					westPanel.add(scrollPane);
					westPanel.revalidate();
					westPanel.repaint();
				} else {
					JOptionPane.showMessageDialog(frame, "No Defect Detection tool selected", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		southPanel.add(dataTableButton);

		JButton barChartButton = new JButton("Bar Chart");
		barChartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String selectedTool = listOfDD.getSelectedValue();
				if(selectedTool != null) {
					
					String titleMainPanel = listOfDD.getSelectedValue().toString(); 
					Border borderMainPanel = BorderFactory.createTitledBorder(titleMainPanel);
					mainPanel.setBorder(borderMainPanel);
					
					String[] columnNames = {"Method ID", "Method Name", "Defect Detetion Result","is_long_method"};
			        // Initializing the JTable 
					@SuppressWarnings("serial")
					DefaultTableModel tableModel = new DefaultTableModel(dataMatrix, columnNames){
						@Override
			            public Class<?> getColumnClass(int column) {
			                switch (column) {
			                    case 0:
			                        return Integer.class;
			                    case 1:
			                        return String.class;
			                    case 2:
			                        return String.class;
			                    default:
			                        return String.class;
			                }
			            }
			        };
					BarChart barchart = new BarChart(tableModel, selectedTool, 2);

					//VER SE É ADICIONADO O SCROLLPANE
					JScrollPane scrollPane = new JScrollPane(barchart);	
					scrollPane.setBorder(borderMainPanel);
					westPanel.removeAll();
					westPanel.add(scrollPane);
					westPanel.revalidate();
					westPanel.repaint();
				} else {
					JOptionPane.showMessageDialog(frame, "No Defect Detection tool selected", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		southPanel.add(barChartButton);

		JButton pieChartButton = new JButton("Pie Chart");
		pieChartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String selectedTool = listOfDD.getSelectedValue();
				if(selectedTool != null) {
					String titleMainPanel = listOfDD.getSelectedValue().toString(); 
					Border borderMainPanel = BorderFactory.createTitledBorder(titleMainPanel);
					mainPanel.setBorder(borderMainPanel);
					
					String[] columnNames = {"Method ID", "Method Name", "Defect Detetion Result","is_long_method"};
			        // Initializing the JTable 
					@SuppressWarnings("serial")
					DefaultTableModel tableModel = new DefaultTableModel(dataMatrix, columnNames){
						@Override
			            public Class<?> getColumnClass(int column) {
			                switch (column) {
			                    case 0:
			                        return Integer.class;
			                    case 1:
			                        return String.class;
			                    case 2:
			                        return String.class;
			                    default:
			                        return String.class;
			                }
			            }
			        };
					PieChart pieChart = new PieChart(tableModel, selectedTool, 2);

					//VER SE É ADICIONADO O SCROLLPANE
					JScrollPane scrollPane = new JScrollPane(pieChart);	
					scrollPane.setBorder(borderMainPanel);
					westPanel.removeAll();
					westPanel.add(scrollPane);
					westPanel.revalidate();
					westPanel.repaint();
				} else {
					JOptionPane.showMessageDialog(frame, "No Defect Detection tool selected", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		southPanel.add(pieChartButton);
		
		JButton defineButton = new JButton("Define Defect Detection Rules and Thresholds");
		defineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFrameContentDefineButton();
			}		
		});
		southPanel.add(defineButton);
	

		//WEST PANEL
		westPanel = new JPanel();	
		westPanel.setLayout(new BorderLayout());
		westPanel.setPreferredSize(new Dimension(screenSize.width-375, screenSize.height)); //700, 670
		mainPanel.add(westPanel, BorderLayout.WEST);

		String titleWestPanel = "Tables and Graphs";
		Border borderWestPanel = BorderFactory.createTitledBorder(titleWestPanel);
		westPanel.setBorder(borderWestPanel);

		//VER SE É ADICIONADO O SCROLLPANE
		JScrollPane scrollPane = new JScrollPane(westPanel, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);		
//		westPanel.add(scrollPane);
		frame.add(scrollPane);

		frame.add(mainPanel);

	}

	public void addFrameContentNewButton() {

		final JFrame frameNew = new JFrame("New Threshold");
		frameNew.setTitle("New Threshold");
		frameNew.setSize(400, 250);
		frameNew.setLayout(new GridLayout(7, 10));

		JLabel nameTheThreshold = new JLabel("Name the new Threshold: ");
		final JTextField nameOfTheThreshold = new JTextField();
		frameNew.add(nameTheThreshold);
		frameNew.add(nameOfTheThreshold);

		JLabel locNumber = new JLabel("LOC: ");
		final JTextField loc = new JTextField();
		frameNew.add(locNumber);
		frameNew.add(loc);

		JLabel cycloNumber = new JLabel("CYCLO: ");
		final JTextField cyclo = new JTextField();
		frameNew.add(cycloNumber);
		frameNew.add(cyclo);	

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 5));
		frameNew.add(southPanel, BorderLayout.SOUTH);

		JButton saveButton = new JButton("Save The New Threshold!");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ADICIONAR A NOVA THRESHOLD AO EASTPANEL

				frameNew.dispose();
			}
		});
		southPanel.add(saveButton, BorderLayout.SOUTH);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameNew.dispose();
			}
		});
		southPanel.add(cancelButton, BorderLayout.SOUTH);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frameNew.setLocation(dim.width / 2 - frameNew.getSize().width / 2, dim.height / 2 - frameNew.getSize().height / 2);
		frameNew.setVisible(true);
	}

	public void addFrameContentRemoveButton() {		
		final String selectedDD = listOfDD.getSelectedValue().toString();
		final JFrame frameDelete = new JFrame("Delete");
		String nameTitle = "Delete Defect Detection: " + selectedDD;
		frameDelete.setTitle(nameTitle);
		frameDelete.setSize(400, 100);

		String text = "Do you want to eliminate: " + selectedDD;
		JLabel deleteInfo = new JLabel(text);
		frameDelete.add(deleteInfo);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 5));
		frameDelete.add(southPanel, BorderLayout.SOUTH);

		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				int index = indexOf(selectedDD);
//				listOfDD.remove(index);

				frameDelete.dispose();
			}		
		});
		southPanel.add(confirmButton, BorderLayout.SOUTH);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameDelete.dispose();
			}
		});
		southPanel.add(cancelButton, BorderLayout.SOUTH);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frameDelete.setLocation(dim.width / 2 - frameDelete.getSize().width / 2, dim.height / 2 - frameDelete.getSize().height / 2);
		frameDelete.setVisible(true);		
	}

	public void addContentEditButton() {
		//......
	}
	
	public void addContentImportProjectButton() { //TODO: IMPORTAR E VISUALIZAR O FICHEIRO EXCEL NO WESTPANEL
		File excelFile = new File("Long-Method.xlsx");
		app.importExcelFile(excelFile);
		
		final JFrame frameImport = new JFrame();
		String nameTitle = "Import Project ";
		frameImport.setTitle(nameTitle);
		frameImport.setSize(400, 100);

		String text = "The File Long-Method is imported";
		JLabel info = new JLabel(text);
		frameImport.add(info);
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 2));
		frameImport.add(southPanel, BorderLayout.SOUTH);

		JButton openButton = new JButton("Open File");
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ADICIONAR FILE NO WESTPANEL PARA SER VISUALIZADO
				
				frameImport.dispose();
			}		
		});
		southPanel.add(openButton);
		
		JButton confirmButton = new JButton("Ok");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameImport.dispose();
			}		
		});
		southPanel.add(confirmButton);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frameImport.setLocation(dim.width / 2 - frameImport.getSize().width / 2, dim.height / 2 - frameImport.getSize().height / 2);
		frameImport.setVisible(true);		
	}
	
	
	public void addFrameContentDefineButton() {
		
		final JFrame frameDefine = new JFrame();
		frameDefine.setLayout(new FlowLayout());
		String nameTitle = "Define Defect Detection Rules and Thresholds";
		frameDefine.setTitle(nameTitle);
		frameDefine.setSize(530, 110);
		
		JPanel northPanel = new JPanel();
		frameDefine.add(northPanel, BorderLayout.NORTH);

		String[] metricsList = {"LOC", "CYCLO", "ATFD", "LAA",};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox listOfMetrics = new JComboBox(metricsList);
		//VER COMO ADICIONAR O TITULO PARA O COMBOBOX
		listOfMetrics.setSelectedIndex(3);
		northPanel.add(listOfMetrics);
		
		String[] simbolsList = {"<", ">"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox listOfSimbols = new JComboBox(simbolsList);
		//VER COMO ADICIONAR O TITULO PARA O COMBOBOX
		String simbolTitle = "Symbols";
		listOfSimbols.setName(simbolTitle);
		listOfSimbols.setSelectedIndex(1);
		northPanel.add(listOfSimbols);
		
		//TODO
		String titleThreshold = "Threshold Value";
		JTextField thresholdsValues = new JTextField();
		thresholdsValues.setText(titleThreshold);
		northPanel.add(thresholdsValues);
		
		String[] operatorsList = {"AND", "OR"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox listOfOperators = new JComboBox(operatorsList);
		//VER COMO ADICIONAR O TITULO PARA O COMBOBOX
		String operatorsTitle = "Logical Operators";
		listOfOperators.setName(operatorsTitle);
		listOfOperators.setSelectedIndex(1);
		northPanel.add(listOfOperators);
		
		String[] metricsList2 = {"LOC", "CYCLO", "ATFD", "LAA"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox listOfMetrics2 = new JComboBox(metricsList2);
		//VER COMO ADICIONAR O TITULO PARA O COMBOBOX
		String metricTitle2 = "Metrics";
		listOfMetrics2.setName(metricTitle2);
		listOfMetrics2.setSelectedIndex(3);
		northPanel.add(listOfMetrics2);
		
		String[] simbolsList2 = {"<", ">"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox listOfSimbols2 = new JComboBox(simbolsList2);
		//VER COMO ADICIONAR O TITULO PARA O COMBOBOX
		String simbolTitle2 = "Symbols";
		listOfSimbols2.setName(simbolTitle2);
		listOfSimbols2.setSelectedIndex(1);
		northPanel.add(listOfSimbols2);
		
		//TODO
		String titleThreshold2 = "Threshold Value";
		JTextField thresholdsValues2 = new JTextField();
		thresholdsValues2.setText(titleThreshold2);
		northPanel.add(thresholdsValues2);
						
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1,2));
		frameDefine.add(southPanel, BorderLayout.SOUTH);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//CRIAR UM METODO PARA ADICIONAR/GUARDAR NOVAS DDs COM OS NOVOS PARAMETROS
			
				frameDefine.dispose();
			}		
		});
		southPanel.add(saveButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameDefine.dispose();
			}		
		});
		southPanel.add(cancelButton);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frameDefine.setLocation(dim.width / 2 - frameDefine.getSize().width / 2, dim.height / 2 - frameDefine.getSize().height / 2);
		frameDefine.setVisible(true);
	}


	public static void main(String[] args) throws IOException {
		@SuppressWarnings("unused")
		MainFrame mainframe = new MainFrame();
	}
}