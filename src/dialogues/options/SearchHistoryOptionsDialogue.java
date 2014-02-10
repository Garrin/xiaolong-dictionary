package dialogues.options;

import gui.JTextFieldWithContextMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import dictionary.SearchHistory;
import dictionary.Settings;

/**
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class SearchHistoryOptionsDialogue extends JDialog {

	private JPanel buttonPanel;
	private JButton okButton;
	private JButton saveSettingsButton;
	private JButton cancelButton;
	
	private JLabel searchHistoryLengthLabel;
	private JTextFieldWithContextMenu searchHistoryTextField;
	
	public SearchHistoryOptionsDialogue() {
		addComponents();
		addActionListeners();
	}
	
	private void addComponents() {
		setLayout(new MigLayout("wrap 2"));
		
		searchHistoryLengthLabel = new JLabel("Search History Length");
		searchHistoryTextField = new JTextFieldWithContextMenu(Integer.toString(Settings.searchHistoryMaximumLength));
		searchHistoryTextField.setColumns(10);
		
		buttonPanel = new JPanel(new MigLayout("wrap 3"));
		okButton = new JButton("Ok");
		saveSettingsButton = new JButton("Save Settings");
		cancelButton = new JButton("Cancel");
		
		buttonPanel.add(okButton);
		buttonPanel.add(saveSettingsButton);
		buttonPanel.add(cancelButton);
		
		add(searchHistoryLengthLabel, "cell 0 0");
		add(searchHistoryTextField, "cell 1 0");
		add(buttonPanel, "cell 0 1 2 1");
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
		//EDIT
		saveSettings();
		this.dispose();
	}
	
	private void saveSettingsButtonAction() {
		//EDIT
		saveSettings();
	}
	
	private void cancelButtonAction() {
		this.dispose();
	}
	
	private void saveSettings() {
		try {
			int newMaxLength = Integer.parseInt(searchHistoryTextField.getText());
			Settings.searchHistoryMaximumLength = newMaxLength;
			SearchHistory.setMaxHistoryLength(newMaxLength);
			Settings.saveSettings();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}