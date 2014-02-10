
package dialogues.options;

import factories.ObserveableFactory;
import gui.DictionaryMainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import dictionary.Settings;

/**
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class LanguageOptionsDialogue extends JDialog {

	@SuppressWarnings("unused")
	private final DictionaryMainWindow window;
	
	private JLabel firstLanguageLabel = new JLabel("First language");
	private JLabel phoneticScriptLabel = new JLabel("Phonetic script");
	private JLabel secondLanguageLabel = new JLabel("Second language");
	
	private JTextField firstLanguageTextField = new JTextField(20);
	private JTextField phoneticScriptTextField = new JTextField(20);
	private JTextField secondLanguageTextField = new JTextField(20);
	
	private JPanel buttonPanel = new JPanel(new MigLayout("wrap 3"));
	
	private JButton okButton = new JButton("Ok");
	private JButton saveSettingsButton = new JButton("Save settings");
	private JButton cancelButton = new JButton("Cancel");
	
	public LanguageOptionsDialogue(DictionaryMainWindow parent, boolean modal) {
		super(parent, modal);
		this.window = parent;
		addComponents();
		addActionListeners();
	}
	
	private void addComponents() {
		setLayout(new MigLayout("wrap 3"));
		
		add(firstLanguageLabel, "cell 0 0");
		add(phoneticScriptLabel, "cell 0 1");
		add(secondLanguageLabel, "cell 0 2");
		
		firstLanguageTextField.setText(Settings.languageOptions_firstLanguageName);
		secondLanguageTextField.setText(Settings.languageOptions_secondLanguageName);
		phoneticScriptTextField.setText(Settings.languageOptions_phoneticScriptName);
		add(firstLanguageTextField, "cell 1 0 2 1");
		add(phoneticScriptTextField, "cell 1 1 2 1");
		add(secondLanguageTextField, "cell 1 2 2 1");
		
		add(buttonPanel, "cell 0 3 3 1");
		
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
		ObserveableFactory.getVocabularyLanguagesObserveable().setLanguageNames(firstLanguageTextField.getText(), phoneticScriptTextField.getText(), secondLanguageTextField.getText());	
//		DictionaryMainWindow.updateLanguages(firstLanguageTextField.getText(), phoneticScriptTextField.getText(), secondLanguageTextField.getText());
	}
}