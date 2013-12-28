import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import java.awt.FlowLayout;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ClientFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2709504377688590640L;
	private JPanel contentPane;
	private JTextField message1;
	private JTextField message2;
	private JSpinner portNumber;
	private JTextArea console;
	private JTextField message3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		final int x = Integer.parseInt(args[0]);
		final int y = Integer.parseInt(args[1]);
		
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
		panel.setLayout(new GridLayout(4, 0, 0, 0));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_1);
		
		JLabel lblPortNumber = new JLabel("Port number:");
		panel_1.add(lblPortNumber);
		
		portNumber = new JSpinner();
		portNumber.setModel(new SpinnerNumberModel(new Integer(6001), null, null, new Integer(1)));
		panel_1.add(portNumber);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel.add(panel_2);
		
		message1 = new JTextField();
		message1.setText("Hello System!");
		panel_2.add(message1);
		message1.setColumns(100);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Client client = new Client(console);
				client.setParam(new NodeName("localhost", (Integer)portNumber.getValue()), message1.getText());
				client.start();
			}
		});
		panel_2.add(btnSend);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_3.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panel.add(panel_3);
		
		message2 = new JTextField();
		message2.setText("CREATE TABLE COMPANY (ID INT PRIMARY KEY NOT NULL, NAME TEXT NOT NULL, AGE INT NOT NULL, ADDRESS CHAR(50), SALARY REAL)");
		panel_3.add(message2);
		message2.setColumns(100);
		
		JButton btnSend_1 = new JButton("Send");
		btnSend_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client client = new Client(console);
				client.setParam(new NodeName("localhost", (Integer)portNumber.getValue()), message2.getText());
				client.start();
			}
		});
		panel_3.add(btnSend_1);
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_4.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		panel.add(panel_4);
		
		message3 = new JTextField();
		message3.setText("DROP TABLE COMPANY");
		panel_4.add(message3);
		message3.setColumns(100);
		
		JButton btnSend_2 = new JButton("Send");
		btnSend_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Client client = new Client(console);
				client.setParam(new NodeName("localhost", (Integer)portNumber.getValue()), message3.getText());
				client.start();
			}
		});
		panel_4.add(btnSend_2);
	}

	public JTextField getMessage1() {
		return message1;
	}
	public JTextField getMessage2() {
		return message2;
	}
	public JSpinner getSpinner() {
		return portNumber;
	}
	public JTextArea getConsole() {
		return console;
	}
	public JTextField getMessage3() {
		return message3;
	}
}
