
package dialogues.options;

import dictionary.Settings;
import gui.DictionaryMainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

/**
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class VocableOptionsDialogue extends JDialog {

	private JPanel vocableOptionsPanel = new JPanel(new MigLayout("wrap 2"));
	private JLabel translationSeperatorLabel = new JLabel("Translations seperator:");
	private JTextField translationSeperatorTextField = new JTextField(10);
	
	private JPanel buttonPanel = new JPanel(new MigLayout("wrap 3"));
	
	private JButton okButton = new JButton("Ok");
	private JButton saveSettingsButton = new JButton("Save settings");
	private JButton cancelButton = new JButton("Cancel");
	
	public VocableOptionsDialogue(DictionaryMainWindow parent, boolean modal) {
		super(parent, modal);
		addComponents();
		addActionListeners();
	}
	
	private void addComponents() {
		setLayout(new MigLayout("wrap 1"));
		
		TitledBorder vocableOptionsBoxTitledBorder;
		vocableOptionsBoxTitledBorder = BorderFactory.createTitledBorder("Vocable options");
		vocableOptionsBoxTitledBorder.setTitleJustification(TitledBorder.CENTER);
		vocableOptionsPanel.setBorder(vocableOptionsBoxTitledBorder);
		
		vocableOptionsPanel.add(translationSeperatorLabel, "cell 0 0");
		vocableOptionsPanel.add(translationSeperatorTextField, "cell 1 0");
		translationSeperatorTextField.setText(Settings.vocableOptions_translationsSeperator);
		
		add(vocableOptionsPanel, "cell 0 0");
		
		buttonPanel.add(okButton, "cell 0 0");
		buttonPanel.add(saveSettingsButton, "cell 1 0");
		buttonPanel.add(cancelButton, "cell 2 0");
		
		add(buttonPanel, "cell 0 1");
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
		if(translationSeperatorTextField.getText().contains(":")) {
			JOptionPane.showMessageDialog(this, "The colon cannot be used as a seperator, since it is already used to seperate the parts of a vocable.", "Usage of colons", JOptionPane.OK_OPTION);
		} else {
			saveSettings();
			this.dispose();
		}
	}
	
	private void saveSettingsButtonAction() {
		if(translationSeperatorTextField.getText().contains(":")) {
			JOptionPane.showMessageDialog(this, "The colon cannot be used as a seperator, since it is already used to seperate the parts of a vocable.", "Usage of colons", JOptionPane.OK_OPTION);
		} else {
			saveSettings();
		}
	}
	
	private void cancelButtonAction() {
		this.dispose();
	}
	
	private void saveSettings() {
		Settings.vocableOptions_translationsSeperator = translationSeperatorTextField.getText();
		Settings.saveSettings();
	}
}