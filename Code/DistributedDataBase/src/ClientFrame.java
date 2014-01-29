import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import java.awt.FlowLayout;

import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClientFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2709504377688590640L;
	private JPanel contentPane;
	private JSpinner portNumber;
	private JTextArea console;
	
	private Client client = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		final int x;
		final int y;
		
		if(args.length == 0){
			x = 10;
			y = 10;
		}else{
			x = Integer.parseInt(args[0]);
			y = Integer.parseInt(args[1]);
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientFrame frame = new ClientFrame();
					frame.setBounds(x, y, 1300, 450);
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
	public ClientFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		console = new JTextArea();
		scrollPane.setViewportView(console);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblPortNumber = new JLabel("Port number:");
		panel.add(lblPortNumber);
		
		portNumber = new JSpinner();
		panel.add(portNumber);
		portNumber.setModel(new SpinnerNumberModel(new Integer(6001), null, null, new Integer(1)));
		
		JButton btnRunFile = new JButton("Run from file ...");
		panel.add(btnRunFile);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(client != null){
					client.kill();
				}
			}
		});
		panel.add(btnStop);
		btnRunFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File(".\\sqlscripts"));
                chooser.setSelectedFile(new File("Unnamed"));
                chooser.setFileFilter(new FileFilter()
                {
                        public boolean accept(File f)
                        {
                            return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt") || f.getName().toLowerCase().endsWith(".txt");
                        }
                        public String getDescription()
                        {
                            return "SQL script";
                        }

                });
                int returnVal = chooser.showOpenDialog(ClientFrame.this);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    String filename = chooser.getSelectedFile().getPath();
                    
                    // read the file and prepare a command list
                    List<String> inputs = new ArrayList<String>();
                    
                    BufferedReader br;
					try {
						br = new BufferedReader(new FileReader(filename));
						String line = null;
	                    while ((line = br.readLine()) != null) {
	                    	inputs.add(line);
	                    }
	                    br.close();
	                    console.append("SQL script " + filename + " loaded sucessfully\n");
	                    
	                    String[] commands = inputs.toArray(new String[inputs.size()]);
	                    
	                    // run client
	                    client = new Client(console);
	    				client.setParam(new NodeName("localhost", (Integer)portNumber.getValue()), commands);
	    				client.start();
	    				
	    				/**
	    				 * 
	    				 * We run the tests we want. We need to uncoment or coment this lane
	    				 * if we want to test them or not
	    				 * 
	    				 * @author Alvar Viana
	    				 */
	    				client.runTests();
	                    
					} catch (FileNotFoundException e) {
						console.append(e.getMessage() + "\n");
					} catch (IOException e) {
						console.append(e.getMessage() + "\n");
					}
                }
			}
		});
	}
	public JSpinner getSpinner() {
		return portNumber;
	}
	public JTextArea getConsole() {
		return console;
	}
	
	/**
	 * @author Alvar Viana
	 * 
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}
}
