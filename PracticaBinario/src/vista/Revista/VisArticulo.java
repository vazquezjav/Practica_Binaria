package vista.Revista;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controlador.GestionRevista;
import modelo.revista.Articulo;
import modelo.revista.Revista;

public class VisArticulo extends JInternalFrame implements ActionListener {

	private JTextField nombreA, apellidoA, nacionalidad, idioma, tema;
	private JLabel etnombre, etapellido, etnacionalidad, etidioma, ettema;
	private JTextArea listado;
	private JButton guardar, leer;
	private GestionRevista gr;

	public void initComponents() {
		setTitle("Articulo");
		setSize(400, 400);
		setClosable(true);
		setMaximizable(false);
		setMaximizable(true);

	}

	public VisArticulo(GestionRevista gr) {
		this.gr = gr;
		setSize(400, 300);
		setTitle("Aspirante");
		initComponents();

		nombreA = new JTextField(10);
		apellidoA = new JTextField(10);
		nacionalidad = new JTextField(10);
		idioma = new JTextField(10);
		tema = new JTextField(10);
		etnombre = new JLabel("Nombre:");
		etapellido = new JLabel("Apellido:");
		etnacionalidad = new JLabel("Nacionalidad:");
		etidioma = new JLabel("Idioma:");
		ettema = new JLabel("Tema:");
		guardar = new JButton("Guardar");
		leer = new JButton("Leer");
		listado = new JTextArea(3, 15);

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

		pan.add(nombreA, cp2);

		cp2 = new GridBagConstraints();
		cp2.gridx = 0;
		cp2.gridy = 1;

		pan.add(etapellido, cp2);

		cp2 = new GridBagConstraints();
		cp2.gridx = 1;
		cp2.gridy = 1;

		pan.add(apellidoA, cp2);

		cp2 = new GridBagConstraints();
		cp2.gridx = 0;
		cp2.gridy = 2;

		pan.add(etnacionalidad, cp2);

		cp2 = new GridBagConstraints();
		cp2.gridx = 1;
		cp2.gridy = 2;

		pan.add(nacionalidad, cp2);

		pan.setBorder(BorderFactory.createTitledBorder("Datos Autor"));
		cp1.add(pan, BorderLayout.NORTH);

		JPanel pan2 = new JPanel();
		pan2.setLayout(new GridBagLayout());
		GridBagConstraints cp3 = new GridBagConstraints();
		cp3.gridx = 0;
		cp3.gridy = 0;
		pan2.add(ettema, cp3);

		cp3 = new GridBagConstraints();
		cp3.gridx = 1;
		cp3.gridy = 0;
		pan2.add(tema, cp3);

		cp3 = new GridBagConstraints();
		cp3.gridx = 0;
		cp3.gridy = 1;
		pan2.add(etidioma, cp3);

		cp3 = new GridBagConstraints();
		cp3.gridx = 1;
		cp3.gridy = 1;
		pan2.add(idioma, cp3);

		cp3 = new GridBagConstraints();
		cp3.gridx = 0;
		cp3.gridy = 2;
		cp3.gridwidth = 2;
		cp3.fill = 1;
		pan2.add(guardar, cp3);

		pan2.setBorder(BorderFactory.createTitledBorder("Dator Articulo"));
		cp1.add(pan2, BorderLayout.CENTER);

		JPanel pan3 = new JPanel();
		pan3.setLayout(new GridBagLayout());
		GridBagConstraints cp4 = new GridBagConstraints();
		cp4.gridx = 0;
		cp4.gridy = 0;
		pan3.add(listado, cp4);

		cp4 = new GridBagConstraints();
		cp4.gridx = 0;
		cp4.gridy = 1;
		pan3.add(leer, cp4);

		pan3.setBorder(BorderFactory.createTitledBorder("Leer Datos"));
		cp1.add(pan3, BorderLayout.SOUTH);

		guardar.addActionListener(this);
		guardar.setActionCommand("guardar");

		leer.addActionListener(this);
		leer.setActionCommand("leer");

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
			if (gr.validarEspacio(nombreA.getText(), apellidoA.getText(), nacionalidad.getText(), tema.getText(),
					idioma.getText())) {
				if (gr.validarAutor(nombreA.getText(), apellidoA.getText(), nacionalidad.getText())) {
					if (gr.validarArticulo(tema.getText(), idioma.getText())) {
						gr.agregarArticulo(nombreA.getText(), apellidoA.getText(), nacionalidad.getText(),
								tema.getText(), idioma.getText());
						JOptionPane.showMessageDialog(this, "Articulo registrado", "Mensaje de información",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		} catch (Exception e) {

			JOptionPane.showMessageDialog(this, e.getMessage(), "Mensaje de error", JOptionPane.ERROR_MESSAGE);

			// e.printStackTrace();
		}
		nombreA.setText("");
		apellidoA.setText("");
		nacionalidad.setText("");
		tema.setText("");
		idioma.setText("");
		listar();
	}

	public void leer() throws IOException {
//		listado.append(gr.leerArticulo());
	}

	public void listar() {
		List<Articulo> articulos = gr.getArticulos();
		for (int i = 0; i < articulos.size(); i++) {
			Articulo u = articulos.get(i);
			System.out.println("Nombre=" + u.getTema() + "apellido=" + u.getAutor().getApellido() + "Idioma="
					+ u.getIdioma() + "Autor=" + u.getAutor());

		}
	}
}
