
package dialogues.options;

import dictionary.Settings;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import net.miginfocom.swing.MigLayout;

/**
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class SpecialCharactersOptionsDialogue extends JDialog {

	private static SimpleAttributeSet paragraph = new SimpleAttributeSet();
	
	private final String DESCRIBTION = "Each of the characters entered below will be displayed in the special character input dialog.\n" +
			"You only need to enter small letters, since there is a button in the special characters input dialog which makes them capital letters.";
	
	private JTextPane aboutSpecialCharactersTextPane;
	
	private JTextField specialCharactersTextField;
	
	private JPanel buttonPanel = new JPanel(new MigLayout("wrap 3"));
	
	private JButton okButton = new JButton("Ok");
	private JButton saveSettingsButton = new JButton("Save settings");
	private JButton cancelButton = new JButton("Cancel");
	
	public SpecialCharactersOptionsDialogue() {
		defineStyleConstants();
		addComponents();
		addActionListeners();
	}
	
	private void addComponents() {
		setLayout(new MigLayout());
		
		aboutSpecialCharactersTextPane = new JTextPane();
		aboutSpecialCharactersTextPane.setMinimumSize(new Dimension(400, 100));
		aboutSpecialCharactersTextPane.setMaximumSize(new Dimension(400, 100));
		aboutSpecialCharactersTextPane.setEditable(false);
		aboutSpecialCharactersTextPane.setBackground(getContentPane().getBackground());
		add(aboutSpecialCharactersTextPane, "cell 0 0");
		addHelpText(aboutSpecialCharactersTextPane, DESCRIBTION, paragraph);
		
		specialCharactersTextField = new JTextField(20);
		specialCharactersTextField.setFont(new Font(
				Settings.specialCharactersOptionsDialogueTextFieldFont,
				Font.PLAIN,
				Settings.specialCharactersOptionsDialogueTextFieldFontSize));
		specialCharactersTextField.setText(Settings.specialCharacters);
		add(specialCharactersTextField, "cell 0 1");
		
		add(buttonPanel, "cell 0 2");
		
		buttonPanel.add(okButton, "cell 0 0");
		buttonPanel.add(saveSettingsButton, "cell 1 0");
		buttonPanel.add(cancelButton, "cell 2 0");
	}

	private void addActionListeners() {
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				okButtonAction();
			}
		});
		saveSettingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveSettingsButtonAction();
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelButtonAction();
			}
		});
	}
	
	private void okButtonAction() {
		saveSettings();
		this.dispose();
	}
	
	private void saveSettingsButtonAction() {
		saveSettings();
	}
	
	private void cancelButtonAction() {
		this.dispose();
	}
	
	private void saveSettings() {
		Settings.specialCharacters = specialCharactersTextField.getText();
		Settings.saveSettings();
	}
	
	private void defineStyleConstants() {
		StyleConstants.setFontSize(paragraph, 12);
		StyleConstants.setAlignment(paragraph, StyleConstants.ALIGN_JUSTIFIED);
		
//		StyleConstants.setAlignment(heading1, StyleConstants.ALIGN_LEFT);
//		StyleConstants.setFontSize(heading1, 16);
//		StyleConstants.setBold(heading1, true);
//		
//		StyleConstants.setAlignment(heading2, StyleConstants.ALIGN_LEFT);
//		StyleConstants.setFontSize(heading2, 14);
//		StyleConstants.setBold(heading2, true);
	}
	
	private void addHelpText(JTextPane textPane, String addedText, AttributeSet attrSet) {
		try {
			textPane.getStyledDocument().setParagraphAttributes(textPane.getDocument().getLength(), addedText.length(), attrSet, true);
			textPane.getStyledDocument().insertString(textPane.getDocument().getLength(), addedText, attrSet);
			textPane.setCaretPosition(0);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}