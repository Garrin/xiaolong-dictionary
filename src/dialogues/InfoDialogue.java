
package dialogues;

import gui.DictionaryMainWindow;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;
import dictionary.Dictionary;

/**
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class InfoDialogue extends JDialog {

	private JScrollPane infoScrollPane;
	
	private JTextArea infoTextArea;
	private JButton okButton = new JButton("OK");
	
	private static final String programmers = "Zelphir";
	private static final String testers = "Zelphir, Sargjaeger";
	
	
	public InfoDialogue(DictionaryMainWindow parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}
	
	private void initComponents() {
		addComponents();
		addActionListeners();
		Helper.addEscapeListener(this);
	}
	
	private void addComponents() {
		this.setLayout(new MigLayout("wrap 1"));
		
		infoTextArea = new JTextArea(Dictionary.APPLICATION_NAME + " v" + Dictionary.VERSION + "\n" +
				"Created by: " + programmers + "\n" +
				"Tested by: " + testers + "\n" +
				"Vocabulary file by: Zelphir\n" +
				"Using MigLayout from www.miglayout.com\n" + 
				"Using WebLaF from http://weblookandfeel.com\n");

		infoTextArea.setColumns(30);
		infoTextArea.setLineWrap(true);
		infoTextArea.setWrapStyleWord(true);
		infoTextArea.setBackground(getBackground());
		infoTextArea.setEditable(false);

		infoScrollPane = new JScrollPane(infoTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		infoScrollPane.setPreferredSize(new Dimension(300, 150));
		
		add(infoScrollPane, "center");
		add(okButton, "center");
		
		pack();
	}
	
	private void addActionListeners() {
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				okButtonAction();
			}
		});
	}
	
	private void okButtonAction() {
		this.dispose();
	}
}