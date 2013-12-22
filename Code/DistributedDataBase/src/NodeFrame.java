import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;

import java.awt.FlowLayout;

import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;


public class NodeFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2284152317883198487L;

	private Node node;
	private int[] portslist;
	
	private JPanel contentPane;
	private JSpinner portNumber;
	private JTextArea console;

	/**
	 * Launch the application.
	 * 
	 * arg[0]	x position
	 * arg[1]	y position
	 * arg[2]   port number
	 * arg[3]	list of nodes port
	 * 
	 */
	public static void main(String[] args) {
		
		final int x = Integer.parseInt(args[0]);
		final int y = Integer.parseInt(args[1]);
		final int port = Integer.parseInt(args[2]);
		String temp[] = args[3].split(",");
		final int[] ports = new int[temp.length];
		for(int i=0; i!=temp.length; ++i){
			ports[i] = Integer.parseInt(temp[i]);
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NodeFrame frame = new NodeFrame();
					frame.setBounds(x, y, 350, 300);
					frame.getPortNumber().setValue(port);
					frame.setPortsList(ports);
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
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_1);
		
		JLabel lblPortNumber = new JLabel("Port Number:");
		panel_1.add(lblPortNumber);
		
		portNumber = new JSpinner();
		portNumber.setModel(new SpinnerNumberModel(6001, 0, 10003, 1));
		panel_1.add(portNumber);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int port = (Integer)portNumber.getValue();
				node = new Node(console);
				node.setParam(port, portslist);
				node.start();
			}
		});
		panel_1.add(btnRun);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				node.kill();
			}
		});
		panel_1.add(btnStop);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_3.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panel.add(panel_3);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		console = new JTextArea();
		console.setEditable(false);
		scrollPane.setViewportView(console);
	}
	
	public void setPortsList(int[] portslist){
		this.portslist = portslist;
	}

	public JSpinner getPortNumber() {
		return portNumber;
	}
	public JTextArea getConsole() {
		return console;
	}
}
