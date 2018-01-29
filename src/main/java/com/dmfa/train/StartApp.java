package com.dmfa.train;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.dmfa.train.command.Command;
import com.dmfa.train.command.CommandBuilder;
import com.dmfa.train.graph.GraphBuilder;

public class StartApp {

	private JFrame frame;
	private JTextField txtPathconfiguracion;
	private JTextArea txtLogs = new JTextArea();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartApp window = new StartApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		redirectSystemStreams();
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblConfiguracin = new JLabel("Configuración");
		lblConfiguracin.setBounds(6, 17, 102, 16);
		panel.add(lblConfiguracin);
		
		txtPathconfiguracion = new JTextField();
		txtPathconfiguracion.setBounds(108, 11, 178, 28);
		panel.add(txtPathconfiguracion);
		txtPathconfiguracion.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    txtPathconfiguracion.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		btnBuscar.setBounds(285, 12, 117, 29);
		panel.add(btnBuscar);
		
		JLabel lblResultado = new JLabel("Resultado:");
		lblResultado.setBounds(6, 90, 85, 16);
		panel.add(lblResultado);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtLogs.setText("");
			}
		});
		btnLimpiar.setBounds(222, 51, 117, 29);
		panel.add(btnLimpiar);
		
		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtPathconfiguracion.getText()!=null&& !txtPathconfiguracion.getText().isEmpty()){
					final Commuter commuter = new CommuterImpl(GraphBuilder.getEmptyGraph());
			        File inputFile = null;
			        
			        try {
			        	inputFile = new File(txtPathconfiguracion.getText());
					} catch (Exception e2) {
						try {
							inputFile = new File(StartApp.class.getResource("/input.txt").toURI());
						} catch (URISyntaxException e1) {
							System.err.println("Archivo no válido: " + e1);
						}
					}
			        
			        try {
			        	final List<Command> inputCommands = new CommandBuilder(System.out).getCommandsFromFile(inputFile);
				        for (final Command eachCommand : inputCommands) {
				            eachCommand.execute(commuter);
				        }
					} catch (Exception e2) {
						System.err.println("Error al procesar: " + e2);
					}
			        
				} else {
					JOptionPane.showMessageDialog(frame, "Ingrese la ubicación del archivo");
				}
			}
		});
		btnCalcular.setBounds(104, 49, 117, 29);
		panel.add(btnCalcular);
		txtLogs.setLineWrap(true);
		txtLogs.setEditable(false);
		
		txtLogs.setBounds(6, 136, 438, 114);
		panel.add(txtLogs);
		
		JScrollPane scroll = new JScrollPane (txtLogs, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(0, 120, 450, 152);
		panel.add(scroll);
	}
	
	private void updateTextArea(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				txtLogs.setText(txtLogs.getText() + text);
			}
		});
	}
	
	private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                updateTextArea(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextArea(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }
}
