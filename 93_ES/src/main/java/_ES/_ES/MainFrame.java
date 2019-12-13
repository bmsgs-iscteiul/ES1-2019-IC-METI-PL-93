package _ES._ES;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

/**
 * A Classe MainFrame comporta-se como uma Aplicação de avaliação da qualidade de deteção de defeitos de desenho em Projetos de Software.
 * Tem como funções: 
 *  - a visualização de Ferramentas como "IPlasma" e "PMD", entre outras que irão ser criadas consoante o desejo do Utilizador, e possível visualização em formato de DataTable, 
 *  PieChart e BarChart; 
 *  - a criação, edição e remoção de Ferramentas; 
 *  - a importação do ficheiro Excel "Long-Method", sendo este projeto adaptável a outros ficheiros Excel (do tipo .xlsx) de estrutura semelhante; 
 *  - a definição de limites (Thresholds) para as métricas, envolvidas na deteção dos defeitos long_method;
 *  - a definição de regras e thresholds para a deteção de defeitos, permitindo ao Utilizador escolher as métricas a serem usadas na regra, os thresholds 
 *  e as operações lógicas (AND e OR);
 *  - a visualização de uma tabela com a contabilização dos indicadores de qualidade verificados na deteção dos defeitos (DCI, DII, ADCI, ADII) para as 
 *  ferramentas Plasma", "PMD" e para as regras/thresholds criadas/definidas pelo Utilizador.
 *   
 * @author Beatriz Gomes - 82195
 * @version 10.0
 */

public class MainFrame {
	/**
	 * Criação da Frame Principal da Aplicação.
	 */
	private JFrame frame;
	public JFrame getFrame() {
		return frame;
	}

	ArrayList<DefectDetection> ddList;
	private JList<String> listOfDD;
	private JPanel westPanel, centerPanel;
	private App app;
	private Object[][] dataMatrix;
	DefectCount dc;
	private JButton editButton, removeButton;

	/**
	 * Construtor da Classe MainFrame com todos os componentes necessários para a criação da Frame principal, nomeadamente a invocação dos métodos doFrame()
	 * e addFrameContent(), que permitem adicionar componetes mais específicos à frame, e por fim a listagem de DefectDetection designada como ddList, o que 
	 * permite a visualização da JList listOfDD na Frame.
	 * É feito também o controlo quando uma ferramneta é selecionada na JList, e caso a ferramenta selecionada for o "IPlasma" ou o "PMD" desabilita a 
	 * funcionalidade dos botões "Edit" e "Remove".
	 * 
	 *  @throws FileSystemException quando a operação de sistemas de ficheiros falha em pelo menos um ou dois ficheiros.
	 *  @throws IOException sempre que uma operação de input ou output falha ou é interpretada.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MainFrame() throws FileSystemException, IOException {
		app = new App();
		dc = new DefectCount();
		ddList = new ArrayList<DefectDetection>();
		DefectDetection ddIPlasma = new DefectDetection("iPlasma");
		DefectDetection ddPMD = new DefectDetection("PMD");
		ddList.add(ddIPlasma);
		ddList.add(ddPMD);		

		listOfDD = new JList<String>(new DefaultListModel<String>());
		((DefaultListModel)listOfDD.getModel()).addElement("iPlasma");
		((DefaultListModel)listOfDD.getModel()).addElement("PMD");

		listOfDD.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

		ListSelectionListener listSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				boolean adjust = listSelectionEvent.getValueIsAdjusting();
				if (!adjust) {
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
								//Criar matriz de dados com o Defect Detection selecionado
								dataMatrix = app.detectDefects(ddList.get(j));
								//Criar painel de Defect Count
								dc.defectCountTable(dataMatrix);
								centerPanel.removeAll();
								JPanel panelCount = dc.panelBuilding();
								panelCount.setPreferredSize(new Dimension(300,200));
								centerPanel.add(panelCount);
								centerPanel.revalidate();
								centerPanel.repaint();
								//Se a ferramenta selecionada for o iPlasma ou o PMD, desabilita os botões de editar e remover
								if(selectionValues[i].equals("iPlasma") || selectionValues[i].equals("PMD")) {
									editButton.setEnabled(false);
									removeButton.setEnabled(false);
								} else {
									editButton.setEnabled(true);
									removeButton.setEnabled(true);
								}
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

	/**
	 * Criação da Frame principal permitindo a sua visibilidade na Aplicação, e é feita com dimensões específicas.
	 */
	public void doFrame() {
		frame = new JFrame("Defect Detection In Software Projects");
		frame.setTitle("Defect Detection In Software Projects");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);			
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(dim.width, dim.height);
		frame.setVisible(true);
	}


	/**
	 * Este método permite a adição de paineis na Frame Principal, tendo cada painel componentes específicos.
	 * O MainPanel é o painel principal, a este painel são adicionados o WestPanel e o EastPanel.
	 * O WestPanel foi criado para permitir a visualização de DataTables, PieCharts e BarCharts.
	 * No EastPanel são adicionados 3 novos paineis, nomeadamente o NorthPanel, o CenterPanel e o SouthPanel.
	 * No NorthPanel é adicionado a JList listOfDD e os botões "Edit", "Add" e "Remove".
	 * No CenterPanel é adicionada a tabela com a contabilização dos indicadores de qualidade verificados na deteção dos defeitos (DCI, DII, ADCI,ADII)
	 * para as ferramentas "IPlasma", "PMD" e para as regras/thresholds criadas/definidas pelo Utilizador.
	 * No SouthPanel é adicionado botões como o "DataTable", "PieChart" e o "BarChart" que ao serem pressionados aparecem os/as gráficos/tabelas correspondentes, 
	 * e o botão que permite a importação de ficheiros. 
	 * 
	 *  @throws FileSystemException quando a operação de sistemas de ficheiros falha em pelo menos um ou dois ficheiros.
	 *  @throws IOException sempre que uma operação de input ou output falha ou é interpretada.
	 */
	public void addFrameContent() throws FileSystemException, IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//MAIN PANEL
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setSize(screenSize.width, screenSize.height);

		//EAST PANEL
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new GridLayout(3,1));
		eastPanel.setPreferredSize(new Dimension(350,screenSize.height)); 
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
		listScroller.setPreferredSize(new Dimension(300, 150)); 
		northPanel.add(listScroller);		

		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDefectDetectionButton();
			}
		});
		northPanel.add(addButton);

		editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editDefectDetectionButton();
			}
		});
		northPanel.add(editButton);
		editButton.setEnabled(false);

		removeButton = new JButton("Remove");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeDefectDetectionButton();
			}
		});
		northPanel.add(removeButton);	
		removeButton.setEnabled(false);

		//CENTER PANEL
		centerPanel = new JPanel();
		eastPanel.add(centerPanel);
		String titleCenterPanel = "Defect Count";
		Border borderCenterPanel = BorderFactory.createTitledBorder(titleCenterPanel);
		centerPanel.setBorder(borderCenterPanel);		

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
					JScrollPane scrollPane = new JScrollPane(datatable.getJTable());
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

		//WEST PANEL
		westPanel = new JPanel();	
		westPanel.setLayout(new BorderLayout());
		westPanel.setPreferredSize(new Dimension(screenSize.width-375, screenSize.height)); 
		mainPanel.add(westPanel, BorderLayout.WEST);

		String titleWestPanel = "Tables and Graphs";
		Border borderWestPanel = BorderFactory.createTitledBorder(titleWestPanel);
		westPanel.setBorder(borderWestPanel);

		frame.add(mainPanel);

	}

	/**
	 * Este método permite ao Utilizador importar ficheiros presentes na sua diretoria pessoal, de preferência do tipo ".xlsx", ou seja ficheiros Excel. 
	 */
	public void addContentImportProjectButton() { 

		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel file", "xlsx");
		fileChooser.setFileFilter(filter);
		int value = fileChooser.showOpenDialog(null);

		if (value == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String filePath = selectedFile.getAbsolutePath();
			int lastIndexOf = filePath.lastIndexOf(".");
			if(!filePath.substring(lastIndexOf).equals(".xlsx")) {
				JOptionPane.showMessageDialog(frame, "File must have .xlsx extension", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				app.importExcelFile(selectedFile);
				listOfDD.setSelectedIndex(listOfDD.getSelectedIndex());
				JOptionPane.showMessageDialog(frame, "Excel File was successfully imported", "Excel File Imported", JOptionPane.INFORMATION_MESSAGE);
			}
		}		
	}

	/**
	 * Este método permite a adição de DefectDetections e a definição de regras e thresholds para a deteção de defeitos, permitindo ao Utilizador escolher 
	 * as métricas a serem usadas na regra, os thresholds e as operações lógicas (AND e OR).
	 */	
	public void addDefectDetectionButton() {

		final JFrame frameDefine = new JFrame();
		frameDefine.setLayout(new FlowLayout());
		String nameTitle = "Add new Defect Detection";
		frameDefine.setTitle(nameTitle);
		frameDefine.setSize(530, 145);

		JPanel mainPanel = new JPanel(new GridLayout(2,1));
		frameDefine.add(mainPanel, BorderLayout.NORTH);

		JPanel northPanel = new JPanel();
		JPanel southPanel = new JPanel();
		mainPanel.add(northPanel);
		mainPanel.add(southPanel);

		JLabel name = new JLabel("Name:");
		final JTextField nameField = new JTextField("", 33);
		northPanel.add(name);
		northPanel.add(nameField);

		String[] metricsList = {"LOC", "CYCLO", "ATFD", "LAA",};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox listOfMetrics = new JComboBox(metricsList);
		listOfMetrics.setSelectedIndex(0);
		southPanel.add(listOfMetrics);

		String[] symbolsList = {"<", ">", "=", "<=", ">=", "!="};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox listOfSymbols = new JComboBox(symbolsList);
		listOfSymbols.setSelectedIndex(0);
		southPanel.add(listOfSymbols);

		//TODO
		final JTextField thresholdsValues = new JTextField("Threshold");
		thresholdsValues.setForeground(Color.GRAY);
		thresholdsValues.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (thresholdsValues.getText().equals("Threshold")) {
					thresholdsValues.setText("");
					thresholdsValues.setForeground(Color.BLACK);
				}
			}
			public void focusLost(FocusEvent e) {
				if (thresholdsValues.getText().isEmpty()) {
					thresholdsValues.setForeground(Color.GRAY);
					thresholdsValues.setText("Threshold");
				}
			}
		});
		southPanel.add(thresholdsValues);

		String[] operatorsList = {"AND", "OR"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox listOfOperators = new JComboBox(operatorsList);
		listOfOperators.setSelectedIndex(0);
		southPanel.add(listOfOperators);

		String[] metricsList2 = {"LOC", "CYCLO", "ATFD", "LAA"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox listOfMetrics2 = new JComboBox(metricsList2);
		listOfMetrics2.setSelectedIndex(1);
		southPanel.add(listOfMetrics2);

		String[] symbolsList2 = {"<", ">", "=", "<=", ">=", "!="};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox listOfSymbols2 = new JComboBox(symbolsList2);
		listOfSymbols2.setSelectedIndex(0);
		southPanel.add(listOfSymbols2);

		//TODO
		final JTextField thresholdsValues2 = new JTextField("Threshold");
		thresholdsValues2.setForeground(Color.GRAY);
		thresholdsValues2.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (thresholdsValues2.getText().equals("Threshold")) {
					thresholdsValues2.setText("");
					thresholdsValues2.setForeground(Color.BLACK);
				}
			}
			public void focusLost(FocusEvent e) {
				if (thresholdsValues2.getText().isEmpty()) {
					thresholdsValues2.setForeground(Color.GRAY);
					thresholdsValues2.setText("Threshold");
				}
			}
		});
		southPanel.add(thresholdsValues2);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		frameDefine.add(buttonPanel, BorderLayout.SOUTH);

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void actionPerformed(ActionEvent e) {

				boolean hasSameName = false;
				for (DefectDetection dd : ddList) {
					if(dd.getName().equals(nameField.getText()))
						hasSameName = true;
				}

				if(hasSameName) {
					JOptionPane.showMessageDialog(frame, "Another Defect Detection with this name already exists", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				//CRIAR UM METODO PARA ADICIONAR/GUARDAR NOVAS DDs COM OS NOVOS PARAMETROS
				int column1 = -1;
				if(listOfMetrics.getItemAt(listOfMetrics.getSelectedIndex()).toString().equals("LOC")) {
					column1 = 4;
				} else if(listOfMetrics.getItemAt(listOfMetrics.getSelectedIndex()).toString().equals("CYCLO")) {
					column1 = 5;
				} else if(listOfMetrics.getItemAt(listOfMetrics.getSelectedIndex()).toString().equals("ATFD")) {
					column1 = 6;
				} else if(listOfMetrics.getItemAt(listOfMetrics.getSelectedIndex()).toString().equals("LAA")) {
					column1 = 7;
				}
				Operators operator = null;
				if(listOfSymbols.getItemAt(listOfSymbols.getSelectedIndex()).toString().equals("<")) {
					operator = Operators.MENOR;
				} else if(listOfSymbols.getItemAt(listOfSymbols.getSelectedIndex()).toString().equals(">")) {
					operator = Operators.MAIOR;
				} else if(listOfSymbols.getItemAt(listOfSymbols.getSelectedIndex()).toString().equals("=")) {
					operator = Operators.IGUAL;
				} else if(listOfSymbols.getItemAt(listOfSymbols.getSelectedIndex()).toString().equals("<=")) {
					operator = Operators.MENOR_OU_IGUAL;
				} else if(listOfSymbols.getItemAt(listOfSymbols.getSelectedIndex()).toString().equals(">=")) {
					operator = Operators.MAIOR_OU_IGUAL;
				} else if(listOfSymbols.getItemAt(listOfSymbols.getSelectedIndex()).toString().equals("!=")) {
					operator = Operators.DIFERENTE;
				}
				double value1 = Double.parseDouble(thresholdsValues.getText());
				ThresHold t1 = new ThresHold(column1, value1, operator);
				int column2 = -1;
				if(listOfMetrics2.getItemAt(listOfMetrics2.getSelectedIndex()).toString().equals("LOC")) {
					column2 = 4;
				} else if(listOfMetrics2.getItemAt(listOfMetrics2.getSelectedIndex()).toString().equals("CYCLO")) {
					column2 = 5;
				} else if(listOfMetrics2.getItemAt(listOfMetrics2.getSelectedIndex()).toString().equals("ATFD")) {
					column2 = 6;
				} else if(listOfMetrics2.getItemAt(listOfMetrics2.getSelectedIndex()).toString().equals("LAA")) {
					column2 = 7;
				}
				Operators operator2 = null;
				if(listOfSymbols2.getItemAt(listOfSymbols2.getSelectedIndex()).toString().equals("<")) {
					operator2 = Operators.MENOR;
				} else if(listOfSymbols2.getItemAt(listOfSymbols2.getSelectedIndex()).toString().equals(">")) {
					operator2 = Operators.MAIOR;
				} else if(listOfSymbols2.getItemAt(listOfSymbols2.getSelectedIndex()).toString().equals("=")) {
					operator2 = Operators.IGUAL;
				} else if(listOfSymbols2.getItemAt(listOfSymbols2.getSelectedIndex()).toString().equals("<=")) {
					operator2 = Operators.MENOR_OU_IGUAL;
				} else if(listOfSymbols2.getItemAt(listOfSymbols2.getSelectedIndex()).toString().equals(">=")) {
					operator2 = Operators.MAIOR_OU_IGUAL;
				} else if(listOfSymbols2.getItemAt(listOfSymbols2.getSelectedIndex()).toString().equals("!=")) {
					operator2 = Operators.DIFERENTE;
				}
				double value2 = Double.parseDouble(thresholdsValues2.getText());
				ThresHold t2 = new ThresHold(column2, value2, operator2);
				int logicalOperator = -1;
				if(listOfOperators.getItemAt(listOfOperators.getSelectedIndex()).toString().equals("AND")) {
					logicalOperator = 1;
				} else if(listOfOperators.getItemAt(listOfOperators.getSelectedIndex()).toString().equals("OR")) {
					logicalOperator = 2;
				}
				System.out.println("Metrics1 = " + column1);
				System.out.println("Operator1 = " + operator);
				System.out.println("Value1 = " + value1);
				System.out.println("logical operator = " + logicalOperator);
				System.out.println("Metrics2 = " + column2);
				System.out.println("Operator2 = " + operator2);
				System.out.println("Value2 = " + value2);
				DefectDetection newDD = new DefectDetection(nameField.getText(), logicalOperator, t1, t2);
				ddList.add(newDD);
				((DefaultListModel)listOfDD.getModel()).addElement(nameField.getText());
				frameDefine.dispose();
			}		
		});
		buttonPanel.add(saveButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameDefine.dispose();
			}		
		});
		buttonPanel.add(cancelButton);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frameDefine.setLocation(dim.width / 2 - frameDefine.getSize().width / 2, dim.height / 2 - frameDefine.getSize().height / 2);
		frameDefine.setVisible(true);
	}

	/**
	 * Este método permite a edição de DefectDetections e a definição de regras e thresholds para a deteção de defeitos, permitindo ao Utilizador escolher 
	 * as métricas a serem usadas na regra, os thresholds e as operações lógicas (AND e OR).
	 */	
	public void editDefectDetectionButton() {

		String ddToEditName = listOfDD.getSelectedValue();
		int ddIndex = -1;
		for (DefectDetection dd : ddList) {
			if(dd.getName().equals(ddToEditName)) {
				ddIndex = ddList.indexOf(dd);
				break;
			}
		}
		final int ddIndexFinal = ddIndex;
		final JFrame frameDefine = new JFrame();
		frameDefine.setLayout(new FlowLayout());
		String nameTitle = "Edit Defect Detection " + ddToEditName;
		frameDefine.setTitle(nameTitle);
		frameDefine.setSize(530, 110);

		JPanel northPanel = new JPanel();
		frameDefine.add(northPanel, BorderLayout.NORTH);

		String[] metricsList = {"LOC", "CYCLO", "ATFD", "LAA",};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox listOfMetrics = new JComboBox(metricsList);
		switch(ddList.get(ddIndex).getThresHold(0).getColumn()) {
		case 4:
			listOfMetrics.setSelectedIndex(0);
			break;
		case 5:
			listOfMetrics.setSelectedIndex(1);
			break;
		case 6:
			listOfMetrics.setSelectedIndex(2);
			break;
		case 7:
			listOfMetrics.setSelectedIndex(3);
			break;
		}
		northPanel.add(listOfMetrics);

		String[] symbolsList = {"<", ">", "=", "<=", ">=", "!="};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox listOfSymbols = new JComboBox(symbolsList);
		switch(ddList.get(ddIndex).getThresHold(0).getOperator()) {
		case MENOR:
			listOfSymbols.setSelectedIndex(0);
			break;
		case MAIOR:
			listOfSymbols.setSelectedIndex(1);
			break;
		case IGUAL:
			listOfSymbols.setSelectedIndex(2);
			break;
		case MENOR_OU_IGUAL:
			listOfSymbols.setSelectedIndex(3);
			break;
		case MAIOR_OU_IGUAL:
			listOfSymbols.setSelectedIndex(4);
			break;
		case DIFERENTE:
			listOfSymbols.setSelectedIndex(5);
			break;
		}
		String simbolTitle = "Symbols";
		listOfSymbols.setName(simbolTitle);
		northPanel.add(listOfSymbols);

		//TODO
		final JTextField thresholdsValues = new JTextField(ddList.get(ddIndex).getThresHold(0).getValue()+"");
		northPanel.add(thresholdsValues);

		String[] operatorsList = {"AND", "OR"}; 
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox listOfOperators = new JComboBox(operatorsList);
		listOfOperators.setSelectedIndex(ddList.get(ddIndex).getLogicalOperator()-1); //1 - AND; 2 - OR
		northPanel.add(listOfOperators);

		String[] metricsList2 = {"LOC", "CYCLO", "ATFD", "LAA"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox listOfMetrics2 = new JComboBox(metricsList2);
		switch(ddList.get(ddIndex).getThresHold(1).getColumn()) {
		case 4:
			listOfMetrics2.setSelectedIndex(0);
			break;
		case 5:
			listOfMetrics2.setSelectedIndex(1);
			break;
		case 6:
			listOfMetrics2.setSelectedIndex(2);
			break;
		case 7:
			listOfMetrics2.setSelectedIndex(3);
			break;
		}
		northPanel.add(listOfMetrics2);

		String[] symbolsList2 = {"<", ">", "=", "<=", ">=", "!="};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox listOfSymbols2 = new JComboBox(symbolsList2);
		switch(ddList.get(ddIndex).getThresHold(1).getOperator()) {
		case MENOR:
			listOfSymbols2.setSelectedIndex(0);
			break;
		case MAIOR:
			listOfSymbols2.setSelectedIndex(1);
			break;
		case IGUAL:
			listOfSymbols2.setSelectedIndex(2);
			break;
		case MENOR_OU_IGUAL:
			listOfSymbols2.setSelectedIndex(3);
			break;
		case MAIOR_OU_IGUAL:
			listOfSymbols2.setSelectedIndex(4);
			break;
		case DIFERENTE:
			listOfSymbols2.setSelectedIndex(5);
			break;
		}
		northPanel.add(listOfSymbols2);

		//TODO
		final JTextField thresholdsValues2 = new JTextField(ddList.get(ddIndex).getThresHold(1).getValue()+"");
		northPanel.add(thresholdsValues2);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1,2));
		frameDefine.add(southPanel, BorderLayout.SOUTH);

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//CRIAR UM METODO PARA ADICIONAR/GUARDAR NOVAS DDs COM OS NOVOS PARAMETROS
				int column1 = -1;
				if(listOfMetrics.getItemAt(listOfMetrics.getSelectedIndex()).toString().equals("LOC")) {
					column1 = 4;
				} else if(listOfMetrics.getItemAt(listOfMetrics.getSelectedIndex()).toString().equals("CYCLO")) {
					column1 = 5;
				} else if(listOfMetrics.getItemAt(listOfMetrics.getSelectedIndex()).toString().equals("ATFD")) {
					column1 = 6;
				} else if(listOfMetrics.getItemAt(listOfMetrics.getSelectedIndex()).toString().equals("LAA")) {
					column1 = 7;
				}
				Operators operator = null;
				if(listOfSymbols.getItemAt(listOfSymbols.getSelectedIndex()).toString().equals("<")) {
					operator = Operators.MENOR;
				} else if(listOfSymbols.getItemAt(listOfSymbols.getSelectedIndex()).toString().equals(">")) {
					operator = Operators.MAIOR;
				} else if(listOfSymbols.getItemAt(listOfSymbols.getSelectedIndex()).toString().equals("=")) {
					operator = Operators.IGUAL;
				} else if(listOfSymbols.getItemAt(listOfSymbols.getSelectedIndex()).toString().equals("<=")) {
					operator = Operators.MENOR_OU_IGUAL;
				} else if(listOfSymbols.getItemAt(listOfSymbols.getSelectedIndex()).toString().equals(">=")) {
					operator = Operators.MAIOR_OU_IGUAL;
				} else if(listOfSymbols.getItemAt(listOfSymbols.getSelectedIndex()).toString().equals("!=")) {
					operator = Operators.DIFERENTE;
				}
				double value1 = Double.parseDouble(thresholdsValues.getText());
				int column2 = -1;
				if(listOfMetrics2.getItemAt(listOfMetrics2.getSelectedIndex()).toString().equals("LOC")) {
					column2 = 4;
				} else if(listOfMetrics2.getItemAt(listOfMetrics2.getSelectedIndex()).toString().equals("CYCLO")) {
					column2 = 5;
				} else if(listOfMetrics2.getItemAt(listOfMetrics2.getSelectedIndex()).toString().equals("ATFD")) {
					column2 = 6;
				} else if(listOfMetrics2.getItemAt(listOfMetrics2.getSelectedIndex()).toString().equals("LAA")) {
					column2 = 7;
				}
				Operators operator2 = null;
				if(listOfSymbols2.getItemAt(listOfSymbols2.getSelectedIndex()).toString().equals("<")) {
					operator2 = Operators.MENOR;
				} else if(listOfSymbols2.getItemAt(listOfSymbols2.getSelectedIndex()).toString().equals(">")) {
					operator2 = Operators.MAIOR;
				} else if(listOfSymbols2.getItemAt(listOfSymbols2.getSelectedIndex()).toString().equals("=")) {
					operator2 = Operators.IGUAL;
				} else if(listOfSymbols2.getItemAt(listOfSymbols2.getSelectedIndex()).toString().equals("<=")) {
					operator2 = Operators.MENOR_OU_IGUAL;
				} else if(listOfSymbols2.getItemAt(listOfSymbols2.getSelectedIndex()).toString().equals(">=")) {
					operator2 = Operators.MAIOR_OU_IGUAL;
				} else if(listOfSymbols2.getItemAt(listOfSymbols2.getSelectedIndex()).toString().equals("!=")) {
					operator2 = Operators.DIFERENTE;
				}
				double value2 = Double.parseDouble(thresholdsValues2.getText());
				int logicalOperator = -1;
				if(listOfOperators.getItemAt(listOfOperators.getSelectedIndex()).toString().equals("AND")) {
					logicalOperator = 1;
				} else if(listOfOperators.getItemAt(listOfOperators.getSelectedIndex()).toString().equals("OR")) {
					logicalOperator = 2;
				}
				ddList.get(ddIndexFinal).getThresHold(0).setColumn(column1);
				ddList.get(ddIndexFinal).getThresHold(0).setOperator(operator);
				ddList.get(ddIndexFinal).getThresHold(0).setValue(value1);
				ddList.get(ddIndexFinal).setLogicalOperator(logicalOperator);
				ddList.get(ddIndexFinal).getThresHold(1).setColumn(column2);
				ddList.get(ddIndexFinal).getThresHold(1).setOperator(operator2);
				ddList.get(ddIndexFinal).getThresHold(1).setValue(value2);

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

	/**
	 * Este método é feito com o intuito de remover DefectDetections, o botão "Remove" ao ser pressionado faz com que este método seja executado automáticamente. 
	 */	
	@SuppressWarnings("rawtypes")
	public void removeDefectDetectionButton() {
		String ddToRemoveName = listOfDD.getSelectedValue();
		System.out.println("listOfDD.getSelectedIndex(): " + listOfDD.getSelectedIndex());
		System.out.println(ddToRemoveName);
		for (DefectDetection dd : ddList) {
			if(dd.getName().equals(ddToRemoveName)) {
				ddList.remove(dd);
				((DefaultListModel)listOfDD.getModel()).remove(listOfDD.getSelectedIndex());
				listOfDD.revalidate();
				listOfDD.repaint();
				editButton.setEnabled(false);
				removeButton.setEnabled(false);
				break;
			}
		}
	}

	/**
	 * Método principal da Aplicação responsável por executar a mesma.
	 * 
	 * @throws IOException sempre que uma operação de input ou output falha ou é interpretada.
	 */	
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("unused")
		MainFrame mainframe = new MainFrame();
	}
}