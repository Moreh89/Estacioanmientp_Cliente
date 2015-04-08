package vista;


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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.jdesktop.swingx.painter.BusyPainter;



//import DTO.ConsultaDTO;
//import DTO.HistoriaClinicaDTO;
//import DTO.ItemHistoriaClinicaDTO;
//import DTO.PacienteDTO;
//import DTO.PracticaAmbulatoriaDTO;
//import DTO.PracticaQuirurgicaDTO;
//import DTO.PrestacionDTO;
import controlador.Controlador;

import java.awt.Font;
import java.awt.Color;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class VerHCE extends JDialog implements FocusListener
{


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private JButton nuevaPrcticaAmbulatoriaButton;


	private TableRowSorter<TableModel> sorter;
	private JTextField apellidoTextField;
	private JTextField nombreTextField;
	private JTextField edadTextField;
	private JTextPane detalleTextPane;
	private JScrollPane scrollDetalleTextPane;
	private JTextField textField_numeroDoc;
	private JButton buscarButton;
	private JComboBox comboBox_TipoDoc;
	//	private PacienteDTO pacienteDTOActual;
	private JTextField sexoTextField;
	private JPanel panelConsultas;
	//	private HistoriaClinicaDTO hceTemp;

	public VerHCE() {
		initGUI();
	}

	public VerHCE(String dni, String tipo) {
		initGUI();
		textField_numeroDoc.setText(dni);
		comboBox_TipoDoc.setSelectedItem(tipo);
		buscarButton.doClick();

	}

	public void initGUI(){
		setTitle("HCE");
		setResizable(false);
		setBounds(100, 100, 758, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		table = new JTable();


		model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};


		model.addColumn("Fecha");
		model.addColumn("Tipo");

		table.setBounds(10, 65, 510, 202);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 141, 217, 291);
		getContentPane().add(scrollPane);

		table.setModel(model);
		table.getTableHeader().setReorderingAllowed(false) ;
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addFocusListener(this);


		JPanel panelBotones = new JPanel();
		panelBotones.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelBotones.setBounds(582, -11, 175, 469);
		contentPane.add(panelBotones);
		panelBotones.setLayout(null);

		JButton nuevaConsultaButton = new JButton("Alta Consulta");
		nuevaConsultaButton.setBounds(10, 27, 154, 32);
		panelBotones.add(nuevaConsultaButton);
		nuevaConsultaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//				if(pacienteDTOActual!=null){
				//				dispose();
				//				AltaConsulta ac = new AltaConsulta();
				//				ac.setVisible(true);
				//				}else{
				//					JOptionPane.showMessageDialog(null, "Debe buscar un paciente previamente", "HCE", JOptionPane.ERROR_MESSAGE);	
				//				}
			}
		});
		nuevaConsultaButton.setIcon(new ImageIcon(VerHCE.class.getResource("/image/consulta.png")));

		boolean validarPermisoConsulta = Controlador.getInstancia().validarPermiso("Oftalmologo General");
		if (validarPermisoConsulta==true){
			nuevaConsultaButton.setEnabled(true);
		}else
			nuevaConsultaButton.setEnabled(false);

		JButton cancelarButton = new JButton("Cancelar");
		cancelarButton.setBounds(10, 407, 154, 32);
		panelBotones.add(cancelarButton);
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		cancelarButton.setIcon(new ImageIcon(VerHCE.class.getResource("/image/cancel.png")));

		nuevaPrcticaAmbulatoriaButton= new JButton("<html>Alta Practica<br>Ambulatoria</html>");
		nuevaPrcticaAmbulatoriaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//				if(pacienteDTOActual!=null){
				//				dispose();
				//				AltaPractica ap = new AltaPractica(pacienteDTOActual);
				//				ap.setVisible(true);
				//				}else{
				//					JOptionPane.showMessageDialog(null, "Debe buscar un paciente previamente", "HCE", JOptionPane.ERROR_MESSAGE);	
				//				}
			}
		});
		nuevaPrcticaAmbulatoriaButton.setIcon(new ImageIcon(VerHCE.class.getResource("/image/practica.png")));
		nuevaPrcticaAmbulatoriaButton.setBounds(10, 70, 154, 40);
		panelBotones.add(nuevaPrcticaAmbulatoriaButton);

		boolean validarPermisoPractica = Controlador.getInstancia().validarPermiso("Oftalmologo Practica Ambulatoria");
		if (validarPermisoPractica==true){
			nuevaPrcticaAmbulatoriaButton.setEnabled(true);
		}else
			nuevaPrcticaAmbulatoriaButton.setEnabled(false);

		JButton nuevaCirugiaButton = new JButton("<html>Alta Practica<br>Quirurgica</html>");
		nuevaCirugiaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//				if(pacienteDTOActual!=null){
				//				dispose();
				//				AltaCirugia ac = new AltaCirugia(pacienteDTOActual);
				//				ac.setVisible(true);
				//				}else{
				//					JOptionPane.showMessageDialog(null, "Debe buscar un paciente previamente", "HCE", JOptionPane.ERROR_MESSAGE);	
				//				}
			}
		});
		nuevaCirugiaButton.setIcon(new ImageIcon(VerHCE.class.getResource("/image/cirugia.png")));
		nuevaCirugiaButton.setBounds(10, 121, 154, 32);
		panelBotones.add(nuevaCirugiaButton);

		boolean validarPermisoCir = Controlador.getInstancia().validarPermiso("Oftalmologo Quirurgico");
		if (validarPermisoCir==true){
			nuevaCirugiaButton.setEnabled(true);
		}else
			nuevaCirugiaButton.setEnabled(false);


		JPanel panelDatosPaciente = new JPanel();
		panelDatosPaciente.setBorder(new TitledBorder(null, "Datos Paciente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDatosPaciente.setBounds(10, 52, 562, 78);
		contentPane.add(panelDatosPaciente);
		panelDatosPaciente.setLayout(null);

		apellidoTextField = new JTextField();
		apellidoTextField.setEditable(false);
		apellidoTextField.setColumns(10);

		apellidoTextField.setBounds(328, 22, 197, 20);
		panelDatosPaciente.add(apellidoTextField);

		nombreTextField = new JTextField();
		nombreTextField.setEditable(false);
		nombreTextField.setColumns(10);
		nombreTextField.setBounds(78, 22, 184, 20);
		panelDatosPaciente.add(nombreTextField);

		JLabel label = new JLabel("Nombre");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(20, 25, 46, 14);
		panelDatosPaciente.add(label);

		JLabel label_1 = new JLabel("Edad");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setBounds(20, 50, 46, 14);
		panelDatosPaciente.add(label_1);

		edadTextField = new JTextField();
		edadTextField.setEditable(false);
		edadTextField.setColumns(10);
		edadTextField.setBounds(78, 47, 184, 20);
		panelDatosPaciente.add(edadTextField);

		JLabel label_4 = new JLabel("Apellido");
		label_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_4.setBounds(272, 25, 46, 14);
		panelDatosPaciente.add(label_4);

		JLabel label_3 = new JLabel("Sexo");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_3.setBounds(282, 50, 69, 14);
		panelDatosPaciente.add(label_3);

		sexoTextField = new JTextField();
		sexoTextField.setEditable(false);
		sexoTextField.setColumns(10);
		sexoTextField.setBounds(328, 46, 197, 22);
		panelDatosPaciente.add(sexoTextField);


		panelConsultas = new JPanel();
		panelConsultas.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Detalle", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelConsultas.setBounds(231, 135, 341, 312);
		contentPane.add(panelConsultas);
		panelConsultas.setLayout(null);

		detalleTextPane = new JTextPane();
		detalleTextPane.setEditable(false);
		scrollDetalleTextPane = new JScrollPane(detalleTextPane);
		scrollDetalleTextPane.setBounds(10, 17, 318, 277);
		scrollDetalleTextPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelConsultas.add(scrollDetalleTextPane);
		detalleTextPane.setContentType("text/html");


		JLabel label_2 = new JLabel("Buscar");
		label_2.setBounds(23, 23, 46, 14);
		contentPane.add(label_2);

		textField_numeroDoc = new JTextField();
		textField_numeroDoc.setColumns(10);
		textField_numeroDoc.setBounds(79, 18, 217, 25);
		contentPane.add(textField_numeroDoc);

		comboBox_TipoDoc = new JComboBox();
		comboBox_TipoDoc.setModel(new DefaultComboBoxModel(new String[] {"DNI", "LC", "LE"}));
		comboBox_TipoDoc.setBounds(306, 16, 114, 25);
		contentPane.add(comboBox_TipoDoc);

		buscarButton = new JButton("Buscar");
		//		buscarButton.setIcon(new ImageIcon(BuscardorHCE.class.getResource("/image/search.png")));
		buscarButton.setBounds(456, 15, 116, 32);
		contentPane.add(buscarButton);

		textField_numeroDoc.requestFocus();




	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
}