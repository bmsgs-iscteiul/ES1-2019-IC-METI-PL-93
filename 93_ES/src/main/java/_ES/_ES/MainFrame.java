package _ES._ES;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
/**
 * 
 * @author Beatriz Gomes - 82195
 *
 */

public class MainFrame {

	private JFrame frame;
//	private JList<DefectDetection> listOfDD;
	private JList<String> listOfDD;


	private JPanel westPanel;

	private App app;
	private Datatable datatable;
	private JTable table;
	private PieChart pieChart;
//	private BarChart barChart;


	public MainFrame() throws FileSystemException, IOException {
		app = new App();
		datatable = new Datatable(app.detectDefects(null));
		table = datatable.getJTable();
//		pieChart = new PieChart(app);
//		barChart = new BarChart(app);

	    //FALTA UPDATE DA CLASSE DO ARTEM
//		ArrayList<DefectDetection> ddList = new ArrayList<DefectDetection>();
//		DefectDetection ddIPlasma = new DefectDetection("IPlasma");
//		DefectDetection ddPMD = new DefectDetection("PMD");
//		ddList.add(ddIPlasma);
//		ddList.add(ddPMD);		
//		listOfDD = new JList(ddlist);
		
		String[] data = {"IPlasma", "PMD"};
		listOfDD = new JList<String>(data);
		
	    listOfDD.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

		ListSelectionListener listSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				boolean adjust = listSelectionEvent.getValueIsAdjusting();
				if (!adjust) {
					JList list = (JList) listSelectionEvent.getSource();
					int selections[] = list.getSelectedIndices();
					Object selectionValues[] = list.getSelectedValues();
					for (int i = 0, n = selections.length; i < n; i++) {
						System.out.print(selections[i] + "/" + selectionValues[i] + " ");
					}
					System.out.println();
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
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width, screenSize.height);	
		frame.setVisible(true);
	}

	private void addFrameContent() throws FileSystemException, IOException {
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

		//NORTH PANEL -> ADICIONAR LISTA RETIRADA DA APP
		final JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout());
		northPanel.setPreferredSize(new Dimension(350, 300));
		eastPanel.add(northPanel, new GridLayout(1,1));

		String titleNorthPanel = "Defect Detection";
		Border borderNorthPanel = BorderFactory.createTitledBorder(titleNorthPanel);
		northPanel.setBorder(borderNorthPanel);

		listOfDD.setVisibleRowCount(10);
		listOfDD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		northPanel.add(listOfDD);

		JScrollPane listScroller = new JScrollPane(listOfDD);
		listScroller.setPreferredSize(new Dimension(300, 150));
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
		centerPanel.setPreferredSize(new Dimension(350, 100));
		eastPanel.add(centerPanel, new GridLayout(2,1));

		String titleCenterPanel = "Defect Count";
		Border borderCenterPanel = BorderFactory.createTitledBorder(titleCenterPanel);
		centerPanel.setBorder(borderCenterPanel);


		//SOUTH PANEL 
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		southPanel.setPreferredSize(new Dimension(350, 40));
		eastPanel.add(southPanel, new GridLayout(3,1));

		String titleSouthPanel = "View";
		Border borderSouthPanel = BorderFactory.createTitledBorder(titleSouthPanel);
		southPanel.setBorder(borderSouthPanel);

		JButton dataTableButton = new JButton("Data Table");
		dataTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ADICIONAR A DATA TABLE AO WESTPANEL -> CRIAR UMA DATATABLE E DEPOIS METER O METODO DE SHOW

				String titleMainPanel = listOfDD.getSelectedValue().toString();
				Border borderMainPanel = BorderFactory.createTitledBorder(titleMainPanel);
				mainPanel.setBorder(borderMainPanel);
			}
		});
		southPanel.add(dataTableButton);

		JButton barChartButton = new JButton("Bar Chart");
		barChartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ADICIONAR O BAR CHART AO WESTPANEL -> CRIAR UM BARCHART E DEPOIS METER O METODO DE SHOW

				String titleMainPanel = listOfDD.getSelectedValue().toString(); 
				Border borderMainPanel = BorderFactory.createTitledBorder(titleMainPanel);
				mainPanel.setBorder(borderMainPanel);
			}
		});
		southPanel.add(barChartButton);

		JButton pieChartButton = new JButton("Pie Chart");
		pieChartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ADICIONAR O PIE CHART AO WESTPANEL -> CRIAR UM PIECHART E DEPOIS METER O METODO DE SHOW

				String titleMainPanel = listOfDD.getSelectedValue().toString(); 
				Border borderMainPanel = BorderFactory.createTitledBorder(titleMainPanel);
				mainPanel.setBorder(borderMainPanel);


				String selectedTool = listOfDD.getSelectedValue();
				if(selectedTool != null) {
					TableModel tableModel = table.getModel();
					PieChart pieChart = new PieChart(tableModel, selectedTool, 2);

					//VER SE É ADICIONADO O SCROLLPANE
					JScrollPane scrollPane = new JScrollPane(pieChart);	
					scrollPane.setBorder(borderMainPanel);
					westPanel.add(scrollPane);
				}

			}
		});
		southPanel.add(pieChartButton);

		JButton importProjectButton = new JButton("Import Projects");
		importProjectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//METER METODO
			}
		});
		southPanel.add(importProjectButton);

		//WEST PANEL
		westPanel = new JPanel();	
		westPanel.setLayout(new BorderLayout());
		westPanel.setPreferredSize(new Dimension(900, screenSize.height));
		mainPanel.add(westPanel, BorderLayout.WEST);

		String titleWestPanel = "Tables and Graphs";
		Border borderWestPanel = BorderFactory.createTitledBorder(titleWestPanel);
		westPanel.setBorder(borderWestPanel);

		//VER SE É ADICIONADO O SCROLLPANE
//		JScrollPane scrollPane = new JScrollPane(westPanel, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);		
//		westPanel.add(scrollPane);
//		frame.add(scrollPane);

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
		frameDelete.setLocation(dim.width / 2 - frameDelete.getSize().width / 2,
				dim.height / 2 - frameDelete.getSize().height / 2);
		frameDelete.setVisible(true);		
	}

	public void addContentEditButton() {
		//......
	}
	
	public void addContentImportProjectsButton() {
		//IMPORTAR E VISUALIZAR O FICHEIRO EXCEL NO WESTPANEL
//		app.importExcelFile("Long-Method.xlsx");
////		Excel excel=new Excel();
////		JTable table = excel.getDataMatrix();
////		westPanel.add(table);
//
//		String titleMainPanel = "Excel File Long-Method";
//		Border borderMainPanel = BorderFactory.createTitledBorder(titleMainPanel);
//		mainPanel.setBorder(borderMainPanel);
	}


	public static void main(String[] args) throws IOException {
		@SuppressWarnings("unused")
		MainFrame mainframe = new MainFrame();

	}
}



