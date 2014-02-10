
package dialogues.options;

import dictionary.Settings;
import gui.DictionaryMainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class DialogsOptionsDialogue extends JDialog {

	//private final DictionaryMainWindow window;

	private JCheckBox showSaveVocabularyBeforeExitConfirmationDialogCheckbox = new JCheckBox("Show \"save vocabulary\" dialog before exiting");
	private JCheckBox showExitConfirmationDialogCheckbox = new JCheckBox("Show \"exit confirmation\" before exiting");
		
	private JPanel buttonPanel = new JPanel(new MigLayout("wrap 3"));
	
	private JButton okButton = new JButton("Ok");
	private JButton saveSettingsButton = new JButton("Save settings");
	private JButton cancelButton = new JButton("Cancel");
	
	public DialogsOptionsDialogue(DictionaryMainWindow parent, boolean modal) {
		super(parent, modal);
		//this.window = parent;
		addComponents();
		addActionListeners();
	}
	
	private void addComponents() {
		setLayout(new MigLayout("wrap 3"));
		
		showSaveVocabularyBeforeExitConfirmationDialogCheckbox.setSelected(Settings.showSaveVocabularyBeforeExitConfirmationDialog);
		showExitConfirmationDialogCheckbox.setSelected(Settings.showExitConfirmationDialog);
		
		add(showSaveVocabularyBeforeExitConfirmationDialogCheckbox, "cell 0 0");
		add(showExitConfirmationDialogCheckbox, "cell 0 1");
				
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
		Settings.showSaveVocabularyBeforeExitConfirmationDialog = showSaveVocabularyBeforeExitConfirmationDialogCheckbox.isSelected();
		Settings.showExitConfirmationDialog = showExitConfirmationDialogCheckbox.isSelected();
		Settings.saveSettings();
	}
}