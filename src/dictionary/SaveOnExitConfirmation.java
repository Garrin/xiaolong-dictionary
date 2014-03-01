package dictionary;

import gui.DictionaryMainWindow;
import gui.SearchBox;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

public class SaveOnExitConfirmation extends WindowAdapter {

	private final DictionaryMainWindow window;
	
	private JCheckBox saveBeforeExit_doNotShowAgainCheckBox = new JCheckBox("Do not show this dialog again.");
	private JCheckBox exit_doNotShowAgainCheckBox = new JCheckBox("Do not show this dialog again.");
	private Object[] saveBeforeExitDialogContent = new Object[]{"Do you want to save changes to the vocabulary file before you exit?",saveBeforeExit_doNotShowAgainCheckBox};
	private Object[] exitDialogContent = new Object[]{"Do you really want to exit?",exit_doNotShowAgainCheckBox};
	private boolean saveVocabularyOnExit = false;
	
	public SaveOnExitConfirmation(DictionaryMainWindow parent) {
		this.window = parent;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		JOptionPane.setDefaultLocale(Locale.ENGLISH);
		
		if(!FileManager.vocabularyIsSavedToFile) {
			if(Settings.showSaveVocabularyBeforeExitConfirmationDialog) {
				showSaveVocabularyBeforeExitConfirmationDialogue();
			} else {
				if(Settings.saveVocabularyOnExit) {
					FileManager.saveVocableList();
				}
				Settings.lastSearchTerm = SearchBox.getSearchTerm();
				Settings.saveSettings();
				System.exit(0);
			}
		} else {
			if(Settings.showExitConfirmationDialog) {
				showExitConfirmationDialogue();
			} else {
				Settings.lastSearchTerm = SearchBox.getSearchTerm();
				Settings.saveSettings();
				System.exit(0);
			}
		}
	}
	
	private void updateSettings() {
		//Save window width and height
		Settings.WINDOW_HEIGHT = window.getHeight();
		Settings.WINDOW_WIDTH = window.getWidth();
		
		//Save column width
		//DictionaryMainWindow.vocableTable.getColumn(DictionaryMainWindow.vocableTable.getColumnName(1)).getWidth();
		
		//Save last search term
		Settings.lastSearchTerm = SearchBox.getSearchTerm();
		
		//Save dialogue options
		Settings.saveVocabularyOnExit = saveVocabularyOnExit;
		Settings.showSaveVocabularyBeforeExitConfirmationDialog = !saveBeforeExit_doNotShowAgainCheckBox.isSelected();
		Settings.showExitConfirmationDialog = !exit_doNotShowAgainCheckBox.isSelected();
	}
	
	private void showSaveVocabularyBeforeExitConfirmationDialogue() {
		int option = JOptionPane.showOptionDialog(
				window,
				saveBeforeExitDialogContent,
				"Exit Dialog",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null, null, null);
			if (option == JOptionPane.YES_OPTION) {
				if(saveBeforeExit_doNotShowAgainCheckBox.isSelected()) {
					saveVocabularyOnExit = true;
				}
				FileManager.saveVocableList();
				updateSettings();
				Settings.saveSettings();
				System.exit(0);
			} else if (option == JOptionPane.NO_OPTION) {
				updateSettings();
				Settings.saveSettings();
				System.exit(0);
			}
	}
	
	private void showExitConfirmationDialogue() {
		int option = JOptionPane.showConfirmDialog(window, exitDialogContent, "Exit Dialog", JOptionPane.YES_NO_OPTION);
		
		if (option == JOptionPane.YES_OPTION) {
			updateSettings();
			Settings.saveSettings();
			System.exit(0);
		} else {
			updateSettings();
			Settings.saveSettings();
		}
	}
}