
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
public class VocableTableOptionsDialogue extends JDialog {

	//private final DictionaryMainWindow window;
	
	private String[] fonts = {"WenQuanYi Zen Hei", "Dialog","Bitstream Cyberbit", "BitstreamCyberCJK"};
	private String[] fontSizes = {"10","12","14","16","18","20"};
	private String[] fontWeights = {"plain","bold","italic"};
	
	private JPanel fontSettingsPanel = new JPanel(new MigLayout("wrap 2"));
	
	private JLabel fontLabel = new JLabel("Font");
	private JLabel fontSizeLabel = new JLabel("Font size");
	private JLabel fontWeightLabel = new JLabel("Font weight");
	
	private JComboBox<String> fontComboBox = new JComboBox<String>(fonts);
	private JComboBox<String> fontSizeComboBox = new JComboBox<String>(fontSizes);
	private JComboBox<String> fontWeightComboBox = new JComboBox<String>(fontWeights);
	
	private JPanel buttonPanel = new JPanel(new MigLayout("wrap 3"));
	
	private JButton okButton = new JButton("Ok");
	private JButton saveSettingsButton = new JButton("Save settings");
	private JButton cancelButton = new JButton("Cancel");
	
	public VocableTableOptionsDialogue(boolean modal) {
		//super(parent, modal);
		//this.window = parent;
		addComponents();
		addActionListeners();
	}
	
	private void addComponents() {
		setLayout(new MigLayout("wrap 2"));
		
		TitledBorder searchBoxTitledBorder;
		searchBoxTitledBorder = BorderFactory.createTitledBorder("Font settings");
		searchBoxTitledBorder.setTitleJustification(TitledBorder.CENTER);
		fontSettingsPanel.setBorder(searchBoxTitledBorder);
		
		add(fontSettingsPanel, "cell 0 0 2 1");
		
		fontSettingsPanel.add(fontLabel, "cell 0 0");
		fontSettingsPanel.add(fontSizeLabel, "cell 0 1");
		fontSettingsPanel.add(fontWeightLabel, "cell 0 2");
		
		if(getComboBoxIndexOf(Settings.vocableTable_font, fontComboBox) != -1)
			fontComboBox.setSelectedIndex(getComboBoxIndexOf(Settings.vocableTable_font, fontComboBox));
		else
			fontComboBox.setSelectedIndex(getComboBoxIndexOf(Settings.VOCABLE_TABLE_DEFAULT_FONT, fontComboBox));
		
		
		if(getComboBoxIndexOf(Integer.toString(Settings.vocableTable_fontSize), fontSizeComboBox) != -1)
			fontSizeComboBox.setSelectedIndex(getComboBoxIndexOf(Integer.toString(Settings.vocableTable_fontSize), fontSizeComboBox));
		else
			fontSizeComboBox.setSelectedIndex(getComboBoxIndexOf(Integer.toString(Settings.VOCABLE_TABLE_DEFAULT_FONTSIZE), fontSizeComboBox));
		
		
		if(getComboBoxIndexOf(Settings.vocableTable_fontWeight, fontWeightComboBox) != -1)
			fontWeightComboBox.setSelectedIndex(getComboBoxIndexOf(Settings.vocableTable_fontWeight, fontWeightComboBox));
		else
			fontWeightComboBox.setSelectedIndex(getComboBoxIndexOf(Settings.VOCABLE_TABLE_DEFAULT_FONTWEIGHT, fontWeightComboBox));
		
		fontSettingsPanel.add(fontComboBox, "cell 1 0");
		fontSettingsPanel.add(fontSizeComboBox, "cell 1 1");
		fontSettingsPanel.add(fontWeightComboBox, "cell 1 2");
		
		add(buttonPanel, "cell 0 2 3 1");
		
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
		DictionaryMainWindow.vocableTable.updateFont( (String) fontComboBox.getSelectedItem(), Integer.parseInt( (String) fontSizeComboBox.getSelectedItem()), (String) fontWeightComboBox.getSelectedItem());
		saveSettings();
		this.dispose();
	}
	
	private void saveSettingsButtonAction() {
		DictionaryMainWindow.vocableTable.updateFont( (String) fontComboBox.getSelectedItem(), Integer.parseInt( (String) fontSizeComboBox.getSelectedItem()), (String) fontWeightComboBox.getSelectedItem());
		saveSettings();
	}
	
	private void cancelButtonAction() {
		this.dispose();
	}
	
	private void saveSettings() {
		Settings.vocableTable_font = (String) fontComboBox.getSelectedItem();
		Settings.vocableTable_fontSize = Integer.parseInt( (String) fontSizeComboBox.getSelectedItem());
		Settings.vocableTable_fontWeight = (String) fontWeightComboBox.getSelectedItem();
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