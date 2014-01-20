import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.JLabel;

import java.awt.FlowLayout;

import javax.swing.JFileChooser;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;


public class ClientFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2709504377688590640L;
	private JPanel contentPane;
	private JTextArea console;
	
	private Client client = null;
	private JTextField textNodes;

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
		
		JLabel lblPortNumber = new JLabel("Ports:");
		panel.add(lblPortNumber);
		
		textNodes = new JTextField();
		textNodes.setText("6001, 6002, 6003, 6004, 6005");
		panel.add(textNodes);
		textNodes.setColumns(80);
		
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
	                    
	                    // create nodes names
	                    String[] parts = textNodes.getText().split(", ");
	                    NodeName[] nodes = new NodeName[parts.length];
	                    for(int i=0; i!=parts.length; ++i){
	                    	nodes[i] = new NodeName("localhost", Integer.valueOf(parts[i]));
	                    }
	                    
	                    // run client
	                    client = new Client(console);
	    				client.setParam(nodes, commands);
	    				client.start();
	                    
					} catch (FileNotFoundException e) {
						console.append(e.getMessage() + "\n");
					} catch (IOException e) {
						console.append(e.getMessage() + "\n");
					}
                }
			}
		});
	}
	public JTextArea getConsole() {
		return console;
	}
}
