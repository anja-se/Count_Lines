package countLines;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;



public class CountLinesRecursive extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfExplorer;
	private JTextField tfCount;
	



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CountLinesRecursive frame = new CountLinesRecursive();
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
	public CountLinesRecursive() {
		setTitle("Count Lines");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 149);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tfExplorer = new JTextField();
		tfExplorer.setEditable(false);
		tfExplorer.setBounds(16, 25, 375, 26);
		contentPane.add(tfExplorer);
		tfExplorer.setColumns(10);
		
		tfCount = new JTextField();
		tfCount.setBounds(106, 73, 76, 26);
		contentPane.add(tfCount);
		tfCount.setColumns(10);
		
		//Speichert das file und zeigt den Pfad an
		JButton btnSelect = new JButton("Select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int open = chooser.showOpenDialog(null);			
				if (open == JFileChooser.APPROVE_OPTION) {
					tfExplorer.setText(chooser.getSelectedFile().getPath());
					tfCount.setText("");
				}
			}
		});
		btnSelect.setBounds(392, 25, 88, 29);
		contentPane.add(btnSelect);
		
		//Prüft ob Pfad gewählt wurde und ruft CountMethode auf und gibt counter if tfcount aus
		JButton btnCount = new JButton("Count");
		btnCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
					if(tfExplorer.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "No File selected", "Error",JOptionPane.ERROR_MESSAGE);
					}
					else {
						File selectedPath = new File(tfExplorer.getText());
						try {
							tfCount.setText(String.valueOf(getCount(selectedPath)));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
		});
		btnCount.setBounds(16, 73, 88, 29);
		contentPane.add(btnCount);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnClose.setBounds(392, 73, 88, 29);
		contentPane.add(btnClose);
	}
	
	 int getCount(File file) throws IOException{
		int counter = 0;
		
		if(file.isFile()) {
			counter = count(file);
		}
		else{
			File[] pathList = file.listFiles(new FileFilter() {
			    @Override
			    public boolean accept(File f) {
			        return !f.isHidden();
			    }
			});
			
			for(File path:pathList) {
				counter += getCount(path);	
			}
		}
		return counter;
	}
	 
	private int count(File fileToCount){
		BufferedReader bufferedReader;
		int counter = 0;
		
		try {
			bufferedReader = new BufferedReader(new FileReader(fileToCount));
			String line = bufferedReader.readLine();
			while(line!=null) {
				if(line.length()!=0) {
					counter ++;
				}
				line = bufferedReader.readLine();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Could not find or read the file", "File read Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return counter;
	}
}
