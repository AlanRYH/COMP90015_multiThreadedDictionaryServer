/**
 * Author Name: RAO YUNHUI
 * Student Number:1316834
 */
package client;


import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * The graphical UI implementation
 *
 */
public class ClientGUI {

	private JFrame frame;
	private DicClient client;
	private JTextField word_area;
	private JTextArea meaning_area;

	

	/**
	 * Create the application.
	 */
	public ClientGUI(DicClient client) {
		this.client = client;
		initialize();
	}
	
	public JFrame getFrame() {
		return this.frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 961, 666);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmation = JOptionPane.showConfirmDialog(frame, "Confirm to add a new word?", "Confirmation", JOptionPane.YES_NO_OPTION);
				if(confirmation == JOptionPane.YES_OPTION) {
					String word = word_area.getText();
					String meaning = meaning_area.getText();
					client.send_request("ADD", word, meaning);
				}
			}
		});
		addButton.setFont(new Font("Dialog", Font.BOLD, 30));
		addButton.setBounds(22, 532, 218, 87);
		addButton.setBackground(Color.WHITE);
		frame.getContentPane().add(addButton);
		
		
		JButton queryButton = new JButton("Query");
		queryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = word_area.getText();
				client.send_request("QUERY", word, "");
			}
		});
		queryButton.setFont(new Font("Dialog", Font.BOLD, 30));
		queryButton.setBackground(Color.WHITE);
		queryButton.setBounds(250, 532, 218, 87);
		frame.getContentPane().add(queryButton);
		
		
		JButton removeButton = new JButton("Remove");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmation = JOptionPane.showConfirmDialog(frame, "Confirm to remove the word?", "Confirmation", JOptionPane.YES_NO_OPTION);
				if(confirmation == JOptionPane.YES_OPTION) {
					String word = word_area.getText();
					client.send_request("REMOVE", word, "");
				}
			}
		});
		removeButton.setFont(new Font("Dialog", Font.BOLD, 30));
		removeButton.setBackground(Color.WHITE);
		removeButton.setBounds(478, 532, 218, 87);
		frame.getContentPane().add(removeButton);
		
		
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmation = JOptionPane.showConfirmDialog(frame, "Confirm to update the word?", "Confirmation", JOptionPane.YES_NO_OPTION);
				if(confirmation == JOptionPane.YES_OPTION) {
					String word = word_area.getText();
					String meaning = meaning_area.getText();
					client.send_request("UPDATE", word, meaning);
				}
			}
		});
		updateButton.setFont(new Font("Dialog", Font.BOLD, 30));
		updateButton.setBackground(Color.WHITE);
		updateButton.setBounds(706, 532, 218, 87);
		frame.getContentPane().add(updateButton);
		
		
		word_area = new JTextField();
		word_area.setBounds(22, 485, 902, 37);
		frame.getContentPane().add(word_area);
		word_area.setColumns(10);
		
		meaning_area = new JTextArea();
		meaning_area.setBounds(22, 43, 902, 400);
		frame.getContentPane().add(meaning_area);
		
		JLabel lblNewLabel = new JLabel("Meaning of the word:");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 25));
		lblNewLabel.setBounds(22, 10, 283, 37);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Word:");
		lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 25));
		lblNewLabel_1.setBounds(22, 453, 283, 37);
		frame.getContentPane().add(lblNewLabel_1);
	}
	
	public void set_meaning(String s) {
		meaning_area.setText(s);
	}
	
	public void show_info(String s) {
		JOptionPane.showMessageDialog(frame, s);
	}
}
