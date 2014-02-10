
package dialogues.options;

import gui.DictionaryMainWindow;
//import gui.FontChooserComboBox;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import dictionary.Settings;

/**
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class BigCharacterBoxOptionsDialogue extends JDialog {

	private final DictionaryMainWindow window;
	
	private String[] fonts = {"Bitstream Cyberbit", "BitstreamCyberCJK", "Dialog", "WenQuanYi Zen Hei"};
	private String[] fontSizes = {"80","90","100","110","120"};
	private String[] fontWeights = {"plain","bold","italic"};
	
	private JPanel fontSettingsPanel = new JPanel(new MigLayout("wrap 2", "[left][right]"));
	
	private JLabel fontLabel = new JLabel("Font");
	private JLabel fontSizeLabel = new JLabel("Font size");
	private JLabel fontWeightLabel = new JLabel("Font weight");
	
	/*private JFontChooser jFontChooser;
	private JTextFieldWithContextMenu chosenFontTextField;
	private JButton chooseFontButton;*/
	
	//private FontChooserComboBox fontChooserComboBox = new FontChooserComboBox();
	private JComboBox<String> fontComboBox = new JComboBox<String>(fonts);
	private JComboBox<String> fontSizeComboBox = new JComboBox<String>(fontSizes);
	private JComboBox<String> fontWeightComboBox = new JComboBox<String>(fontWeights);
	
	private JLabel ignoredCharactersLabel = new JLabel("Ignored characters");
	private JTextField ignoredCharactersTextField = new JTextField(Settings.bigCharacterBoxOptions_ignored_characters, 10);
	
	private JPanel buttonPanel = new JPanel(new MigLayout("wrap 3"));
	
	private JButton okButton = new JButton("Ok");
	private JButton saveSettingsButton = new JButton("Save settings");
	private JButton cancelButton = new JButton("Cancel");
	
	public BigCharacterBoxOptionsDialogue(DictionaryMainWindow parent, boolean modal) {
		super(parent, modal);
		this.window = parent;
		addComponents();
		addActionListeners();
	}
	
	private void addComponents() {
		setLayout(new MigLayout("wrap 2"));
		
		TitledBorder searchBoxTitledBorder;
		searchBoxTitledBorder = BorderFactory.createTitledBorder("Font settings");
		searchBoxTitledBorder.setTitleJustification(TitledBorder.CENTER);
		fontSettingsPanel.setBorder(searchBoxTitledBorder);
		
		/*
		try {
			jFontChooser = new JFontChooser(fontSizes);
			chosenFontTextField = new JTextFieldWithContextMenu(Settings.BIG_CHARACTER_BOX_DEFAULT_FONT);
			chooseFontButton = new JButton("...");
			
			fontSettingsPanel.add(chosenFontTextField);
			fontSettingsPanel.add(chooseFontButton);
		} catch (Exception E) {
			E.printStackTrace();
		}*/
		
		add(fontSettingsPanel, "cell 0 0 2 1");
		
		fontSettingsPanel.add(fontLabel, "cell 0 0");
		fontSettingsPanel.add(fontSizeLabel, "cell 0 1");
		fontSettingsPanel.add(fontWeightLabel, "cell 0 2");
		//fontSettingsPanel.add(fontLabel, "cell 0 3");
		
		if(getComboBoxIndexOf(Settings.bigCharacterBoxOptions_font, fontComboBox) != -1)
			fontComboBox.setSelectedIndex(getComboBoxIndexOf(Settings.bigCharacterBoxOptions_font, fontComboBox));
		else
			fontComboBox.setSelectedIndex(getComboBoxIndexOf(Settings.BIG_CHARACTER_BOX_DEFAULT_FONT, fontComboBox));
		
		
		if(getComboBoxIndexOf(Integer.toString(Settings.bigCharacterBoxOptions_fontSize), fontSizeComboBox) != -1)
			fontSizeComboBox.setSelectedIndex(getComboBoxIndexOf(Integer.toString(Settings.bigCharacterBoxOptions_fontSize), fontSizeComboBox));
		else
			fontSizeComboBox.setSelectedIndex(getComboBoxIndexOf(Integer.toString(Settings.BIG_CHARACTER_BOX_DEFAULT_FONTSIZE), fontSizeComboBox));
		
		
		if(getComboBoxIndexOf(Settings.bigCharacterBoxOptions_fontWeight, fontWeightComboBox) != -1)
			fontWeightComboBox.setSelectedIndex(getComboBoxIndexOf(Settings.bigCharacterBoxOptions_fontWeight, fontWeightComboBox));
		else
			fontWeightComboBox.setSelectedIndex(getComboBoxIndexOf(Settings.BIG_CHARACTER_BOX_DEFAULT_FONTWEIGHT, fontWeightComboBox));
		
		/*fontChooserComboBox.setBackground(getBackground());
		fontChooserComboBox.setEditable(false);
		fontChooserComboBox.setRecentFontsCount(5);*/
		
		fontSettingsPanel.add(fontComboBox, "cell 1 0");
		fontSettingsPanel.add(fontSizeComboBox, "cell 1 1");
		fontSettingsPanel.add(fontWeightComboBox, "cell 1 2");
		//fontSettingsPanel.add(fontChooserComboBox, "cell 1 3");
		
		add(ignoredCharactersLabel, "cell 0 1");
		add(ignoredCharactersTextField, "cell 1 1");
		
		add(buttonPanel, "cell 0 2 2 1");
		
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
		updateBigCharacterBox();
		saveSettings();
		this.dispose();
	}
	
	private void saveSettingsButtonAction() {
		updateBigCharacterBox();
		saveSettings();
	}
	
	private void cancelButtonAction() {
		this.dispose();
	}
	
	private void updateBigCharacterBox() {
		//String font = (String) fontComboBox.getSelectedItem();
		Font font = new Font((String) fontComboBox.getSelectedItem(), fontWeightComboBox.getSelectedIndex(), Integer.parseInt((String) fontSizeComboBox.getSelectedItem()));
		//int fontSize = Integer.parseInt((String) fontSizeComboBox.getSelectedItem());
		//String fontWeight = (String) fontWeightComboBox.getSelectedItem();
		//int fontWeight = fontWeightComboBox.getSelectedIndex();
		
		window.bigCharacterBox.updateFont(font);
		window.bigCharacterBox.updateIgnoredCharacters(ignoredCharactersTextField.getText());
	}
	
	private void saveSettings() {
		Settings.bigCharacterBoxOptions_font = (String) fontComboBox.getItemAt(fontComboBox.getSelectedIndex());
		Settings.bigCharacterBoxOptions_fontSize = Integer.parseInt((String) fontSizeComboBox.getItemAt(fontSizeComboBox.getSelectedIndex()));
		Settings.bigCharacterBoxOptions_fontWeight = (String) fontWeightComboBox.getItemAt(fontWeightComboBox.getSelectedIndex());
		Settings.bigCharacterBoxOptions_ignored_characters = ignoredCharactersTextField.getText();
		
		Settings.saveSettings();
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