package vista;

import java.awt.EventQueue;
import java.util.ArrayList;


import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.EtchedBorder;

import controlador.Controlador;

public class BuscardorPaciente extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField buscarTextField;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
//	private ArrayList <PacienteDTO> pacientes;
	private JComboBox comboBox;
	
	
	private TableRowSorter<TableModel> sorter;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BuscardorPaciente frame = new BuscardorPaciente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BuscardorPaciente() {
		initGUI();
	}
	
	
	public void initGUI(){
		setTitle("Buscar Pacientes");
		setResizable(false);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 671, 324);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBuscar = new JLabel("Buscar");
		lblBuscar.setBounds(10, 19, 46, 14);
		contentPane.add(lblBuscar);
		
		buscarTextField = new JTextField();
		buscarTextField.setBounds(53, 14, 217, 25);
		contentPane.add(buscarTextField);
		buscarTextField.setColumns(10);
		
		table = new JTable();
		model = new DefaultTableModel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		model.addColumn("Nombre");
		model.addColumn("Apellido");
		model.addColumn("Tipo Doc.");
		model.addColumn("Numero");
		
		table.setBounds(10, 65, 510, 202);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 64, 510, 224);
		getContentPane().add(scrollPane);
		
		//table.setModel(model);
		table.getTableHeader().setReorderingAllowed(false) ;
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		JButton buscarButton = new JButton("Buscar");
		buscarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrar();
				
			}
		});
		buscarButton.setBounds(404, 7, 116, 32);
		contentPane.add(buscarButton);
		buscarButton.setIcon(new ImageIcon(BuscardorPaciente.class.getResource("/image/search.png")));
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 510, 6);
		contentPane.add(separator);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"DNI", "Apellido", "Nombre"}));
		comboBox.setBounds(280, 14, 114, 25);
		contentPane.add(comboBox);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(530, -12, 146, 322);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton nuevoButton = new JButton("Nuevo");
		nuevoButton.setBounds(10, 25, 116, 32);
		panel.add(nuevoButton);
		nuevoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
//				AltaPaciente ap = new AltaPaciente();
//				ap.setVisible(true);
				
				
			}
		});
		nuevoButton.setIcon(new ImageIcon(BuscardorPaciente.class.getResource("/image/plus.png")));
		
		JButton modificarButton = new JButton("Modificar");
		modificarButton.setBounds(10, 68, 116, 32);
		panel.add(modificarButton);
		modificarButton.setIcon(new ImageIcon(BuscardorPaciente.class.getResource("/image/modificar.png")));
		//contentPane.add(table);
		
		JButton cancelarButton = new JButton("Cancelar");
		cancelarButton.setBounds(10, 265, 116, 32);
		panel.add(cancelarButton);
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		cancelarButton.setIcon(new ImageIcon(BuscardorPaciente.class.getResource("/image/cancel.png")));
		modificarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int registro = table.getSelectedRow();
				if (registro == -1){
					JOptionPane.showMessageDialog(null, "Debe Selccionar un paciente para Modificar", "Modificar Paciente", JOptionPane.ERROR_MESSAGE);
				}else{
						//TODO
						
				}
				
			}
		});
		
		llenarTabla();
		
		this.setLocationRelativeTo(null);
		//setAlwaysOnTop(true);
		setModal(true);
	}
	
	@SuppressWarnings("unused")
	private void filtrar() {
		ArrayList<RowFilter<Object, Object>> rfs = new ArrayList<RowFilter<Object,Object>>();
		RowFilter<TableModel, Object> rf = null;
		int indiceColumnaTabla = 2;
		switch (comboBox.getSelectedIndex()) {
		case 0: indiceColumnaTabla = 3;break;//DNI
		case 1: indiceColumnaTabla = 4;break;//Apellido
		case 2: indiceColumnaTabla = 2;break;//Nombre
		}
		try {
			String text = buscarTextField.getText();
		    String[] textArray = text.split(" ");

		    for (int i = 0; i < textArray.length; i++) {
		        rfs.add(RowFilter.regexFilter("(?i)" + textArray[i], 0, 1, 2, 4));
		    }	
			
		    rf = RowFilter.andFilter(rfs);	
		} catch (java.util.regex.PatternSyntaxException e) {
		}
		sorter.setRowFilter(rf);
		}
	
	public void llenarTabla()
	{
//		pacientes = Sistema.getInstancia().getPacientes();
//
//		model.setNumRows(pacientes.size());
//		for (int i = 0; i < pacientes.size(); i++) {
//			model.setValueAt(pacientes.get(i).getNombre(), i, 0);
//			model.setValueAt(pacientes.get(i).getApellido(), i,1);
//			model.setValueAt(pacientes.get(i).getTipoDoc(), i, 2);	
//			model.setValueAt(pacientes.get(i).getDni(), i, 3);		
//		}
//		
//		table.setModel(model);
//		sorter = new TableRowSorter<TableModel>(model);
//		table.setRowSorter(sorter);
		
	}
}

