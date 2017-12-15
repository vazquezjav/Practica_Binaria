package vista.Revista;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;

import controlador.GestionRevista;
import modelo.revista.Articulo;
import modelo.revista.Revista;

public class VisRevista extends JInternalFrame implements ActionListener {

	private JTextField nombre, editorial, autor;
	private JLabel etnombre, eteditorial, etautor;
	private JButton guardar, leer;
	private JTextArea listado;
	private JComboBox escojerA;
	private GestionRevista gr;

	public void initComponents() {
		setTitle("Articulo");
		setSize(400, 400);
		setClosable(true);
		setMaximizable(false);
		setMaximizable(true);

	}

	public VisRevista(GestionRevista gr) {
		this.gr = gr;
		setSize(400, 300);
		setTitle("Revista");
		initComponents();
		nombre = new JTextField(10);
		editorial = new JTextField(10);
		autor = new JTextField(10);
		etnombre = new JLabel("Nombre:");
		eteditorial = new JLabel("Editorial:");
		etautor = new JLabel("Articulo");
		guardar = new JButton("Guardar");
		leer = new JButton("Leer");
		listado = new JTextArea(3, 15);

		escojerA = new JComboBox();
		buscarArticulo();

		JPanel pan = new JPanel();

		Container cp1 = getContentPane();

		pan.setLayout(new GridBagLayout());
		GridBagConstraints cp2 = new GridBagConstraints();
		cp2.gridx = 0;
		cp2.gridy = 0;
		pan.add(etnombre, cp2);

		cp2 = new GridBagConstraints();
		cp2.gridx = 1;
		cp2.gridy = 0;

		pan.add(nombre, cp2);

		cp2 = new GridBagConstraints();
		cp2.gridx = 0;
		cp2.gridy = 1;

		pan.add(eteditorial, cp2);

		cp2 = new GridBagConstraints();
		cp2.gridx = 1;
		cp2.gridy = 1;

		pan.add(editorial, cp2);

		cp2 = new GridBagConstraints();
		cp2.gridx = 0;
		cp2.gridy = 2;

		pan.add(etautor, cp2);

		cp2 = new GridBagConstraints();
		cp2.gridx = 1;
		cp2.gridy = 2;

		pan.add(escojerA, cp2);

		cp2 = new GridBagConstraints();
		cp2.gridx = 0;
		cp2.gridy = 3;
		cp2.gridwidth = 2;
		cp2.fill = 1;

		pan.add(guardar, cp2);
		pan.setBorder(BorderFactory.createTitledBorder("Datos Revista"));
		cp1.add(pan, BorderLayout.CENTER);

		JPanel pan2 = new JPanel();

		pan2.setLayout(new GridBagLayout());
		GridBagConstraints cp3 = new GridBagConstraints();
		cp3.gridx = 0;
		cp3.gridy = 0;
		pan2.add(listado, cp3);

		cp3 = new GridBagConstraints();
		cp3.gridx = 0;
		cp3.gridy = 1;

		pan2.add(leer, cp3);
		pan2.setBorder(BorderFactory.createTitledBorder("Leer archivo"));
		cp1.add(pan2,BorderLayout.SOUTH);

		guardar.addActionListener(this);
		guardar.setActionCommand("guardar");

		leer.addActionListener(this);
		leer.setActionCommand("leer");

	}

	public void buscarArticulo() {
		Vector mo = new Vector();
		List<Articulo> articulos = gr.getArticulos();
		for (int i = 0; i < articulos.size(); i++) {
			Articulo rector = articulos.get(i);
			mo.addElement(rector);
		}
		escojerA = new JComboBox(mo);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		String comando = evt.getActionCommand();
		System.out.println("evento boton" + comando);
		switch (comando) {
		case "guardar":
			guardar();
			break;
		case "leer":
			try {
				leer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	public void guardar() {
		try {
			if (gr.validarEspacio1(nombre.getText(), editorial.getText())) {
				if (gr.validarChoose((Articulo) escojerA.getSelectedItem())) {
					if (gr.validarRevista(nombre.getText(), editorial.getText(),
							(Articulo) escojerA.getSelectedItem())) {
						gr.agregarRevista(nombre.getText(), editorial.getText(), (Articulo) escojerA.getSelectedItem());
						JOptionPane.showMessageDialog(this, "Revista registrada", "Mensaje de información",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Mensaje de error", JOptionPane.ERROR_MESSAGE);
			// e.printStackTrace();
		}
		listar();
		nombre.setText("");
		editorial.setText("");
	}
	public void leer() throws IOException{

		listado.append(gr.leerRevista
				());	
	}

	public void listar() {
		List<Revista> revistas = gr.getRevistas();
		for (int i = 0; i < revistas.size(); i++) {
			Revista u = revistas.get(i);
			System.out.println("Nombre: " + u.getNombre() + " " + "Editorial" + u.getEditorial() + " " + "Articulo"
					+ u.getArticulo());

		}
	}

}
