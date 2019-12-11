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
 * A Classe MainFrame comporta-se como uma Aplicacao de avaliacao da qualidade de detecao de defeitos de desenho em Projetos de Software.
 * Tem como funcoes: 
 *  - a visualizacao de Ferramentas como "IPlasma" e "PMD", entre outras que irao ser criadas consoante o desejo do Utilizador, em formato de DataTable, PieChart 
 *  e BarChart; 
 *  - a criacao, edicao e remocao de Ferramentas; 
 *  - a importacao do ficheiro Excel "Long-Method", sendo este projeto adaptavel a outros ficheiros Excel (do tipo .xlsx) de estrutura 
 *  semelhante; 
 *  - a definicao de limites (Thresholds) para as metricas, envolvidas na detecao dos defeitos long_method e feature_envy;
 *  - a definicao de regras e thresholds para a detecao de defeitos, permitindo ao Utilizador escolher as metricas a serem usadas na regra, os thresholds e as 
 *  operacoes logicas (AND e OR);
 *  - a visualizacao de uma tabela com a contabilizacao dos indicadores de qualidade verificados na detecao dos defeitos (DCI, DII, ADCI, ADII) para as ferramentas
 *   "IPlasma", "PMD" e para as regras/thresholds criadas/definidas pelo Utilizador.
 * @author Beatriz Gomes - 82195
 * @version 5.0
 */

public class MainFrame {
	/**
	 * Criacao da Frame Principal da Aplicacao.
	 */
	private JFrame frame;
	ArrayList<DefectDetection> ddList;
	private JList<String> listOfDD;
	private JPanel westPanel, centerPanel;
	private App app;
	private Object[][] dataMatrix;
	DefectCount dc;
	private JButton editButton, removeButton;
	
	/**
	 * Construtor da Classe MainFrame com todos os componentes necessarios para a criacao da Frame principal, nomeadamente a invocacao dos metodos doFrame() e 
	 * addFrameContent(), que permitem adicionar componetes mais especificos a frame, e por fim a listagem de DefectDetection designada como ddList, o que permite 
	 * a visualizacao da JList listOfDD na Frame.
	 * E feito tambem o controlo quando uma ferramneta e selecionada na JList, e caso a ferramenta selecionada for o "IPlasma" ou o "PMD" desabilita a funcionalidade
	 * dos botoes "Edit" e "Remove".
	 *  @throws FileSystemException quando a operacao de sistemas de ficheiros falha em pelo menos um ou dois ficheiros.
	 *  @throws IOException sempre que uma operacao de input ou output falha ou e interpretada.
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
	
//		String[] data = {"iPlasma", "PMD"};
//		listOfDD = new JList<String>(data);
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
	 * Criacao da Frame principal permitindo a sua visibilidade na Aplicacao, feita com dimensoes especificas.
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
	 * Este metodo permite a adicao de paineis na Frame, onde cada painel tem componentes especificos.
	 * O MainPanel e o painel principal, a este painel sao adicionados o WestPanel e o EastPanel.
	 * O WestPanel foi criado para permitir a visualizacao de DataTables, PieCharts e BarCharts.
	 * No EastPanel sao adicionados 3 novos paineis, nomeadamente o NorthPanel, o CenterPanel e o SouthPanel.
	 * No NorthPanel e adicionado a JList listOfDD e os botoes "Edit", "Add" e "Remove".
	 * No CenterPanel e adicionada a tabela com a contabilizacao dos indicadores de qualidade verificados na detecao dos defeitos (DCI, DII, ADCI,ADII) para as 
	 * ferramentas "IPlasma", "PMD" e para as regras/thresholds criadas/definidas pelo Utilizador.
	 * No SouthPanel e adicionado botoes como o "DataTable", "PieChart" e o "BarChart" que ao serem precionados aparecem os/as graficos/tabelas correspondetes, 
	 * e o botao que permite a importacao de ficheiros.
	 * 
	 * TODO botao que o zé vai alterar
	 * 
	 *  @throws FileSystemException quando a operacao de sistemas de ficheiros falha em pelo menos um ou dois ficheiros.
	 *  @throws IOException sempre que uma operacao de input ou output falha ou e interpretada.
	 */
	public void addFrameContent() throws FileSystemException, IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//MAIN PANEL
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setSize(screenSize.width, screenSize.height);
		frame.add(mainPanel, BorderLayout.NORTH);

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
		listScroller.setPreferredSize(new Dimension(320, 180)); 
		northPanel.add(listScroller);		

		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFrameContentNewButton();
			}
		});
		northPanel.add(addButton);
		
		editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String titleNorthPanel = listOfDD.getSelectedValue().toString();
				Border borderNorthPanel = BorderFactory.createTitledBorder(titleNorthPanel);
				mainPanel.setBorder(borderNorthPanel);

				addContentEditButton();			
			}
		});
		northPanel.add(editButton);
		editButton.setEnabled(false);
		
		removeButton = new JButton("Remove");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFrameContentRemoveButton();
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
		westPanel.setPreferredSize(new Dimension(screenSize.width-375, screenSize.height)); 
		mainPanel.add(westPanel, BorderLayout.WEST);

		String titleWestPanel = "Tables and Graphs";
		Border borderWestPanel = BorderFactory.createTitledBorder(titleWestPanel);
		westPanel.setBorder(borderWestPanel);

		JScrollPane scrollPane = new JScrollPane(westPanel, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);		
//		westPanel.add(scrollPane);
		frame.add(scrollPane);

		frame.add(mainPanel);

	}

	/**
	 * Este metodo permite a criacao de novas Ferramentas.
	 * 
	 * TODO zé vai alterar este metodo
	 * 
	 */
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

	/**
	 * Este metodo permite a remocao de Ferramentas.
	 * 
	 * TODO zé vai alterar este metodo
	 * 
	 */
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

	/**
	 * Este metodo permite a edicao de Ferramentas.
	 * 
	 * TODO zé vai alterar este metodo
	 * 
	 */
	public void addContentEditButton() {
		//......
	}
	
	/**
	 * Este metodo permite ao Utilizador importar ficheiros presentes na sua diretoria pessoal, de preferencia do tipo ".xlsx"
	 * 
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
	 * Este metodo permite a definicao de regras e thresholds para a detecao de defeitos, permitindo ao Utilizador escolher as metricas a serem usadas na regra, os
	 * thresholds e as operacoes logicas (AND e OR).
	 */	
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
		final JComboBox listOfMetrics = new JComboBox(metricsList);
		//VER COMO ADICIONAR O TITULO PARA O COMBOBOX
		listOfMetrics.setSelectedIndex(3);
		northPanel.add(listOfMetrics);
		
		String[] symbolsList = {"<", ">", "=", "<=", ">=", "!="};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox listOfSymbols = new JComboBox(symbolsList);
		//VER COMO ADICIONAR O TITULO PARA O COMBOBOX
		String simbolTitle = "Symbols";
		listOfSymbols.setName(simbolTitle);
		listOfSymbols.setSelectedIndex(1);
		northPanel.add(listOfSymbols);
		
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
		northPanel.add(thresholdsValues);
		
		String[] operatorsList = {"AND", "OR"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox listOfOperators = new JComboBox(operatorsList);
		//VER COMO ADICIONAR O TITULO PARA O COMBOBOX
		String operatorsTitle = "Logical Operators";
		listOfOperators.setName(operatorsTitle);
		listOfOperators.setSelectedIndex(1);
		northPanel.add(listOfOperators);
		
		String[] metricsList2 = {"LOC", "CYCLO", "ATFD", "LAA"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox listOfMetrics2 = new JComboBox(metricsList2);
		//VER COMO ADICIONAR O TITULO PARA O COMBOBOX
		String metricTitle2 = "Metrics";
		listOfMetrics2.setName(metricTitle2);
		listOfMetrics2.setSelectedIndex(3);
		northPanel.add(listOfMetrics2);
		
		String[] symbolsList2 = {"<", ">", "=", "<=", ">=", "!="};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox listOfSymbols2 = new JComboBox(symbolsList2);
		//VER COMO ADICIONAR O TITULO PARA O COMBOBOX
		String symbolTitle2 = "Symbols";
		listOfSymbols2.setName(symbolTitle2);
		listOfSymbols2.setSelectedIndex(1);
		northPanel.add(listOfSymbols2);
		
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
		northPanel.add(thresholdsValues2);
						
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1,2));
		frameDefine.add(southPanel, BorderLayout.SOUTH);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
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
				double value2 = Double.parseDouble(thresholdsValues.getText());
				ThresHold t2 = new ThresHold(column2, value2, operator2);
				int logicalOperator = -1;
				if(listOfOperators.getItemAt(listOfOperators.getSelectedIndex()).toString().equals("AND")) {
					logicalOperator = 1;
				} else if(listOfOperators.getItemAt(listOfOperators.getSelectedIndex()).toString().equals("OR")) {
					logicalOperator = 2;
				}
				DefectDetection newDD = new DefectDetection("teste", logicalOperator, t1, t2);
				ddList.add(newDD);
				((DefaultListModel)listOfDD.getModel()).addElement("teste");
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