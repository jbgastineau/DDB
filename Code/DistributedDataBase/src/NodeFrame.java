import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.FlowLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;


public class NodeFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2016796322875926957L;
	
	
	private Node node;
	
	private JPanel contentPane;
	private JTextField portNumber;
	private JTextField otherNodes;
	private JTextField dbName;
	private JTextArea console;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		final int x;
		final int y;
		final int height;
		final int width;
		final int port;
		final String nodeNames;
		final String dbName;
				
		if(args.length == 0){
			x = 10;
			y = 10;
			width = 550;
			height = 600;
			port = 6001;
			nodeNames = "localhost:6001, localhost:6002, localhost:6003, localhost:6004, localhost:6005";
			dbName = "test1.db";
		}else{
			x = Integer.parseInt(args[0]);
			y = Integer.parseInt(args[1]);
			width = 350;
			height = 400;
			port = Integer.parseInt(args[2]);
			nodeNames = args[3];
			dbName = args[4];
		}

		
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NodeFrame frame = new NodeFrame();
					
 					frame.setBounds(x, y, width, height);
 					frame.getPortNumber().setText(Integer.toString(port));
 					frame.getOtherNodes().setText(nodeNames);
 					frame.getDbName().setText(dbName);
 					
 					InetAddress IP = InetAddress.getLocalHost();
 					frame.setTitle(IP.toString());
 					
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
	public NodeFrame() {
		setTitle("Node");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
		panel.setLayout(new GridLayout(3, 0, 0, 0));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_1);
		
		JLabel lblPortNumber = new JLabel("Port:");
		panel_1.add(lblPortNumber);
		
		portNumber = new JTextField();
		portNumber.setText("6666");
		panel_1.add(portNumber);
		portNumber.setColumns(10);
		
		JLabel lblDbName = new JLabel("DB:");
		panel_1.add(lblDbName);
		
		dbName = new JTextField();
		dbName.setText("database.db");
		panel_1.add(dbName);
		dbName.setColumns(10);
		
		otherNodes = new JTextField();
		otherNodes.setText("localhost:6001, localhost:6002, localhost:6003, localhost:6004, localhost:6005");
		panel.add(otherNodes);
		otherNodes.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel.add(panel_2);
		
		JButton btnSrart = new JButton("Srart");
		panel_2.add(btnSrart);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(node != null){
					node.kill();
					node = null;
				}
				
			}
		});
		panel_2.add(btnStop);
		btnSrart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(node != null)	return;
				
				int port = Integer.parseInt(portNumber.getText());
				
				// create nodes names
                String[] parts = otherNodes.getText().split(", ");
                NodeName[] nodes = new NodeName[parts.length];
                for(int i=0; i!=parts.length; ++i){
                	nodes[i] = NodeName.parse(parts[i]);
                	if(nodes[i] == null){
                		console.append("Error in node name: " + parts[i] + "\n");
                	}else{
                		console.append("Node " + i + ": " + nodes[i] + "\n");
                	}
                }
				
				
				node = new Node(console);
				node.setParam(port, nodes, dbName.getText());
				node.start();
			}
		});
	}

	public JTextField getOtherNodes() {
		return otherNodes;
	}
	public JTextField getPortNumber() {
		return portNumber;
	}
	public JTextField getDbName() {
		return dbName;
	}
	public JTextArea getConsole() {
		return console;
	}
}
