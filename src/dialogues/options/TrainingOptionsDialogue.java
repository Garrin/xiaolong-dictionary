
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
public class TrainingOptionsDialogue extends JDialog {

	//private final DictionaryMainWindow window;
	
	private ButtonGroup translationDirectionButtonGroup = new ButtonGroup();
	private JRadioButton firstLanguageToSecondLanguageRadioButton = new JRadioButton(Settings.languageOptions_firstLanguageName + " --> " + Settings.languageOptions_secondLanguageName);
	private JRadioButton secondLanguageToFirstLanguageRadioButton = new JRadioButton(Settings.languageOptions_secondLanguageName + " --> " + Settings.languageOptions_firstLanguageName);
	private JCheckBox phoneticScriptCheckbox = new JCheckBox(Settings.languageOptions_phoneticScriptName + " enabled");
	
	private JPanel fontSettingsPanel = new JPanel(new MigLayout("wrap 2"));
	private String[] fonts = {"WenQuanYi Zen Hei", "Dialog","Bitstream Cyberbit", "BitstreamCyberCJK"};
	private JLabel fontLabel = new JLabel("Font");
	private JComboBox<String> fontComboBox = new JComboBox<String>(fonts);
	
	private JPanel buttonPanel = new JPanel(new MigLayout("wrap 3"));
	
	private JButton okButton = new JButton("Ok");
	private JButton saveSettingsButton = new JButton("Save settings");
	private JButton cancelButton = new JButton("Cancel");
	
	public TrainingOptionsDialogue(DictionaryMainWindow parent, boolean modal) {
		super(parent, modal);
		//this.window = parent;
		addComponents();
		addActionListeners();
	}
	
	private void addComponents() {
		setLayout(new MigLayout("wrap 3"));
		
		translationDirectionButtonGroup.add(firstLanguageToSecondLanguageRadioButton);
		translationDirectionButtonGroup.add(secondLanguageToFirstLanguageRadioButton);
		
		firstLanguageToSecondLanguageRadioButton.setSelected(Settings.trainingOptions_firstToSecond);
		secondLanguageToFirstLanguageRadioButton.setSelected(Settings.trainingOptions_secondToFirst);
		phoneticScriptCheckbox.setSelected(Settings.trainingOptions_phoneticScript_shown);
		
		add(firstLanguageToSecondLanguageRadioButton, "cell 0 0");
		add(secondLanguageToFirstLanguageRadioButton, "cell 0 1");
		add(phoneticScriptCheckbox, "cell 0 2");
		
		add(fontSettingsPanel, "cell 0 3");
		fontSettingsPanel.add(fontLabel, "cell 0 0");
		fontSettingsPanel.add(fontComboBox, "cell 1 0");
		if(getComboBoxIndexOf(Settings.vocableTable_font, fontComboBox) != -1)
			fontComboBox.setSelectedIndex(getComboBoxIndexOf(Settings.trainingOptions_font, fontComboBox));
		else
			fontComboBox.setSelectedIndex(getComboBoxIndexOf(Settings.TRAINING_OPTIONS_DEFAULT_FONT, fontComboBox));
		
		add(buttonPanel, "cell 0 4 3 1");
		
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
		Settings.trainingOptions_firstToSecond = firstLanguageToSecondLanguageRadioButton.isSelected();
		Settings.trainingOptions_phoneticScript_shown = phoneticScriptCheckbox.isSelected();
		Settings.trainingOptions_secondToFirst = secondLanguageToFirstLanguageRadioButton.isSelected();
		Settings.trainingOptions_font = (String) fontComboBox.getSelectedItem();
		Settings.saveSettings();
	}
	
	public boolean isFirstLanguageToSecondLanguageRadioButtonSelected() {
		return firstLanguageToSecondLanguageRadioButton.isSelected();
	}
	
	public boolean isSecondLanguageToFirstLanguageRadioButtonSelected() {
		return secondLanguageToFirstLanguageRadioButton.isSelected();
	}
	
	public boolean isPhoneticScriptCheckboxSelected() {
		return phoneticScriptCheckbox.isSelected();
	}
	
	private int getComboBoxIndexOf(String str, JComboBox<String> combobox) {
		int tmp = -1;
		for(int i = 0; i < combobox.getItemCount(); i++) {
			if(((String) combobox.getItemAt(i)).equals(str)) {
				tmp = i;
				break;
			}
		}
		return tmp;
	}
}