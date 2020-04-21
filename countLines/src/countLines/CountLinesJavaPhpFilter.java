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
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;



public class CountLinesJavaPhpFilter extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfExplorer;
	private JTextField tfCount;
	
	private boolean javaPhpFilter=false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CountLinesJavaPhpFilter frame = new CountLinesJavaPhpFilter();
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
	public CountLinesJavaPhpFilter() {
		setTitle("Count Lines");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 198);
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
		tfCount.setBounds(106, 125, 76, 26);
		contentPane.add(tfCount);
		tfCount.setColumns(10);
		
		//Öffnet den Explorer und zeigt gewählten Pfad an
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
		
		//Prüft ob Pfad gewählt wurde, ruft getCount-Methode auf und gibt counter im Textfeld zurück
		JButton btnCount = new JButton("Count");
		btnCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
					if(tfExplorer.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "No File selected", "Error",JOptionPane.ERROR_MESSAGE);
					}
					else {
						File selectedPath = new File(tfExplorer.getText());
						if (selectedPath.isFile()&&!isValid(selectedPath.getName())) {
							JOptionPane.showMessageDialog(null, "Invalid file selection", "Error",JOptionPane.ERROR_MESSAGE);
						}
						else {
							try {
								tfCount.setText(String.valueOf(getCount(selectedPath)));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						
					}
				}
		});
		btnCount.setBounds(16, 125, 88, 29);
		contentPane.add(btnCount);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnClose.setBounds(392, 125, 88, 29);
		contentPane.add(btnClose);
		
		//Setzt den Filter auf an/aus
		JCheckBox checkBoxFilter = new JCheckBox(".java and .php only");
		checkBoxFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBoxFilter.isSelected()) {
					javaPhpFilter = true;
				}
				else {
					javaPhpFilter = false;
				}
			}
		});
		checkBoxFilter.setBounds(23, 63, 159, 23);
		contentPane.add(checkBoxFilter);
	}
	
	//Prüft Dateien, erstellt Dateilisten und ruft count() Methode auf
	 int getCount(File file) throws IOException{
		int counter = 0;
		
		if(file.isFile() && isValid(file.getName())) {
			counter = count(file);
		}
		else if(file.isDirectory()){
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
	
	//gibt die Anzahl der Zeilen eines Dokuments zurück
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
	
	//prüft ob das Dokument Bedingungen entspricht
	private boolean isValid(String fileName) {
		boolean isValid = false;
		if(!javaPhpFilter||fileName.endsWith(".java")||fileName.endsWith(".php")){
			isValid = true;
		}
		return isValid;
	}
}
