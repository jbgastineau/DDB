import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JSpinner;

import java.awt.FlowLayout;

import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;


public class ClientFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6577256907151014674L;
	private JPanel contentPane;
	private JTextField txtQuestion;
	private JSpinner portNumber;
	private JTextArea console;

	/**
	 * Launch the application.
	 * 
	 * arg[0]	x position
	 * arg[1]	y position
	 */
	public static void main(String[] args) {
		
		final int x = Integer.parseInt(args[0]);
		final int y = Integer.parseInt(args[1]);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientFrame frame = new ClientFrame();
					frame.setBounds(x, y, 450, 300);
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

		setTitle("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel.add(panel_2);
		
		JLabel lblPortNumber = new JLabel("Port number:");
		panel_2.add(lblPortNumber);
		
		portNumber = new JSpinner();
		panel_2.add(portNumber);
		portNumber.setModel(new SpinnerNumberModel(6001, 0, 10000, 1));
		
		JLabel lblMessage = new JLabel("Message: ");
		panel_2.add(lblMessage);
		
		txtQuestion = new JTextField();
		txtQuestion.setText("Hello System!");
		panel_2.add(txtQuestion);
		txtQuestion.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client client = new Client(console);
				client.setParam(new NodeName("localhost", (Integer)portNumber.getValue()), txtQuestion.getText());
				client.start();
			}
		});
		panel_2.add(btnSend);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		console = new JTextArea();
		console.setEditable(false);
		scrollPane.setViewportView(console);
		
		
		
	}

	public JSpinner getPortNumber() {
		return portNumber;
	}
	public JTextField getTxtQuestion() {
		return txtQuestion;
	}
	public JTextArea getConsole() {
		return console;
	}
}
