/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableRowSorter;

import manager.VocableManager;
import net.miginfocom.swing.MigLayout;
import dialogues.HelpDialogue;
import dialogues.InfoDialogue;
import dialogues.options.BigCharacterBoxOptionsDialogue;
import dialogues.options.DialogsOptionsDialogue;
import dialogues.options.LanguageOptionsDialogue;
import dialogues.options.SearchHistoryOptionsDialogue;
import dialogues.options.SearchOptionsDialogue;
import dialogues.options.SpecialCharactersOptionsDialogue;
import dialogues.options.TrainingOptionsDialogue;
import dialogues.options.VocableOptionsDialogue;
import dialogues.options.VocableTableOptionsDialogue;
import dialogues.vocableactions.AddVocableFrame;
import dialogues.vocableactions.ChangeMultipleVocablesFrame;
import dialogues.vocableactions.ChangeVocableFrame;
import dialogues.vocableactions.DeleteMultipleVocablesDialogue;
import dialogues.vocableactions.DeleteVocableDialogue;
import dialogues.vocableactions.SetVocableLevelDialogue;
import dialogues.vocableactions.TrainVocablesDialogue;
import dictionary.Dictionary;
import dictionary.SaveOnExitConfirmation;
import dictionary.Settings;
import factories.ComponentFactory;

/**
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class DictionaryMainWindow extends JFrame {

	private static DictionaryMainWindow instance = null;
	
	private SaveOnExitConfirmation saveOnExitConfirmation;
	
	//MENUBAR
	DictionaryMenuBar dictionaryMenuBar;
	
	private static final String VERSION_LABEL_STRING = "Version 1.0";

	//NORTH
	private JLabel titleLabel;
	
	//WEST
	private JPanel westJPanel;
	private static SearchBox searchBox;

	//CENTER
	public static VocableTable vocableTable;
	private JScrollPane vocableTableScrollPane;
	private String[] columnNames = {"#", Settings.languageOptions_firstLanguageName, Settings.languageOptions_phoneticScriptName, Settings.languageOptions_secondLanguageName};
	//private Object[][] initialTableData = {{"","",""}};
	
	//EAST
	public BigCharacterBox bigCharacterBox;
	
	//SOUTH
	private JPanel southJPanel;
	private JLabel versionLabel;
	private static JLabel statusLabel;
	
	private VocableDetailBox vocableDetailBox;
	

	//EXTERNAL
	public static AddVocableFrame addVocableDialogue;
	public static ChangeVocableFrame changeVocableDialogue;
	public static ChangeMultipleVocablesFrame changeMultipleVocablesDialogue;
	public static DeleteVocableDialogue deleteVocableDialogue;
	public static DeleteMultipleVocablesDialogue deleteMultipleVocablesDialogue;
	public SetVocableLevelDialogue setVocableLevelDialogue;
	public static TrainVocablesDialogue trainVocablesDialogue;
	
	public VocableOptionsDialogue vocableOptionsDialogue;
	public SearchOptionsDialogue searchOptionsDialogue;
	public LanguageOptionsDialogue languageOptionsDialogue;
	public VocableTableOptionsDialogue vocableTableOptionsDialogue;
	public TrainingOptionsDialogue trainingOptionsDialogue;
	public BigCharacterBoxOptionsDialogue bigCharacterBoxOptionsDialogue;
	public SpecialCharactersOptionsDialogue specialCharactersOptionsDialogue;
	public SearchHistoryOptionsDialogue searchHistoryOptionsDialogue;
	
	public DialogsOptionsDialogue dialogsOptionsDialogue;
	public InfoDialogue infoDialogue;
	public HelpDialogue helpDialogue;
	
	public String specialCharacterToAdd = ""; 
	
	public static DictionaryMainWindow getInstance() {
		if(instance == null) {
			instance = new DictionaryMainWindow();
		}
		return instance;
	}
	
	private DictionaryMainWindow() {
		setTitle(Dictionary.APPLICATION_NAME);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		saveOnExitConfirmation = new SaveOnExitConfirmation(this);
		addWindowListener(saveOnExitConfirmation);
		
		setMinimumSize(new Dimension(Settings.MINIMUM_WINDOW_WIDTH, Settings.MINIMUM_WINDOW_HEIGHT));
		setPreferredSize(new Dimension(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT));
		
		initializeLayout();
		createComponents();
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		//restoreLastSearch();
	}
	
	/*private void restoreLastSearch() {
		SearchBox.searchVocableButtonActionPerformed();
	}*/

	private void initializeLayout() {
		getContentPane().setLayout(new BorderLayout());

		southJPanel = new JPanel(new MigLayout("wrap 3", "[][center, grow][]", ""));
		getContentPane().add(southJPanel, BorderLayout.SOUTH);

		westJPanel = new JPanel(new MigLayout("wrap 1", "[center]"));
		//westJPanel = new JPanel(new MigLayout("wrap 1", ""));
		
		getContentPane().add(westJPanel, BorderLayout.WEST);
	}
	
	private void createComponents() {
		createMenu();
		//NORTH
		createTitleLabel();
		//WEST
		createSearchBox();
		createActionBox();
		//CENTER
		createVocableTable();
		
		//EAST
		createBigCharacterBox();
		//SOUTH
		createStatusLabel();
		createVocableDetailsBox();
		createVersionLabel();
	}

	private void createMenu() {
		dictionaryMenuBar = new DictionaryMenuBar(this);
		setJMenuBar(dictionaryMenuBar);
	}

	private void createSearchBox() {
		searchBox = new SearchBox(Settings.lastSearchTerm.substring(0, Settings.lastSearchTerm.length()-1));
		westJPanel.add(searchBox);
	}

	private void createActionBox() {
		westJPanel.add(new ActionBox(this));
	}

	private void createVocableTable() {
		UneditableCellsTableModel model = new UneditableCellsTableModel(columnNames, 0);
		vocableTable = new VocableTable(model);
		vocableTableScrollPane = new JScrollPane(vocableTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		TableRowSorter<UneditableCellsTableModel> sorter = new TableRowSorter<UneditableCellsTableModel>();
		vocableTable.setRowSorter(sorter);
		sorter.setModel(model);
		
		add(vocableTableScrollPane, BorderLayout.CENTER);
	}
	
	private void createBigCharacterBox() {
		bigCharacterBox = new BigCharacterBox(Settings.bigCharacterBoxOptions_ignored_characters);
		getContentPane().add(bigCharacterBox, BorderLayout.EAST);
	}
	
	
	private void createTitleLabel() {
		titleLabel = new JLabel("Dictionary", JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		getContentPane().add(titleLabel, BorderLayout.NORTH);
	}

	private void createStatusLabel() {
		statusLabel = ComponentFactory.getDictionaryStatusLabel();
		
		southJPanel.add(new JPanel(), "cell 0 0");
		southJPanel.add(new JPanel(), "cell 0 1");
		southJPanel.add(statusLabel, "cell 0 2");
	}

	private void createVocableDetailsBox() {
		vocableDetailBox = new VocableDetailBox();
		southJPanel.add(vocableDetailBox, "cell 1 0 1 3");
	}
	
	private void createVersionLabel() {
		versionLabel = new JLabel(VERSION_LABEL_STRING);

		southJPanel.add(new JPanel(), "cell 2 0");
		southJPanel.add(new JPanel(), "cell 2 1");
		southJPanel.add(versionLabel, "cell 2 2");
	}
	
	/*
	public static void updateStatusLabel(String text) {
		statusLabel.setText(text);		
	}
	*/
	
//	public static void updateVocableTable(ArrayList<Vocable> searchResult) {
//		//currentSearchResult = searchResult;
//		vocableTable.updateVocableTable(searchResult);
//		TableColumnsAutofitter.autoResizeTable(vocableTable, true, 0, true, 175);
//		
//		/*
//		int widthOfColumns =	vocableTable.getColumn(vocableTable.getColumnName(0)).getWidth() +
//								vocableTable.getColumn(vocableTable.getColumnName(1)).getWidth() +
//								vocableTable.getColumn(vocableTable.getColumnName(2)).getWidth() +
//								vocableTable.getColumn(vocableTable.getColumnName(3)).getWidth();
//		
//		System.out.println("Containers: " + vocableTable.getParent());
//		System.out.println("Breite des Containers: " + vocableTable.getParent().getWidth());
//		System.out.println("Breite der Tabelle: " + vocableTable.getWidth());
//		System.out.println("Breite der Spalten: " + widthOfColumns);
//		
//		if(vocableTable.getWidth() < widthOfColumns) {
//			
//		}
//		*/
//	}
	
	public void updateBigCharacterBox(String characters) {
		bigCharacterBox.setCharacters(characters);
	}
	
	/**
	 * This method updates the VocableDetailBox below the shown search result
	 * by searching through the current search result for the 3 parameters.
	 * This is necessary because the user can sort the shown vocables and thus 
	 * change the order of vocables shown so that a simple lookup in the current 
	 * search result with the number of the selected row of the search result will 
	 * most likely lead to using the from values.
	 * 
	 * @param firstLanguage the first language
	 * @param phoneticScript the phonetic script
	 * @param secondLanguage the second language
	 */
	public void updateVocableDetailsBox(String firstLanguage, String phoneticScript, String secondLanguage) {
		
		int row = -1;
		
		//Search for vocable and take it's values EDIT
		for(int i = 0; i < VocableManager.getLastSearchResult().size(); i++) {
			if(	VocableManager.getLastSearchResult().get(i).getFirstLanguage().equals(firstLanguage) &&
				VocableManager.getLastSearchResult().get(i).getPhoneticScript().equals(phoneticScript) &&
				VocableManager.getLastSearchResult().get(i).getSecondLanguage().equals(secondLanguage)) {
				row = i;
				break;
			}
		}
		
		//Get the correct values
		String chapter = VocableManager.getLastSearchResult().get(row).getChapter();
		String topic = VocableManager.getLastSearchResult().get(row).getTopic();
		String learnLevel = VocableManager.getLastSearchResult().get(row).getLearnLevel();
		
		//If vocable was found (should always be the case)
		if(row != -1) {
			vocableDetailBox.updateChapter(chapter);
			vocableDetailBox.updateTopic(topic);
			vocableDetailBox.updateLevel(learnLevel);
		}
	}
	
	public SaveOnExitConfirmation getSaveOnExitConfirmation() {
		return saveOnExitConfirmation;
	}
	
//	public VocableTable getVocableTable() {
//		return vocableTable;
//	}
	
//	public static void updateLanguages(String newFirstLanguage, String newPhoneticScript, String newSecondLanguage) {
//		//if(addVocableDialogue != null) addVocableDialogue.updateLanguages(newFirstLanguage, newPhoneticScript, newSecondLanguage);
//		//if(changeVocableDialogue != null) changeVocableDialogue.updateLanguages(newFirstLanguage, newPhoneticScript, newSecondLanguage);
//		//if(deleteVocableDialogue != null) deleteVocableDialogue.updateLanguages(newFirstLanguage, newPhoneticScript, newSecondLanguage);
//		//if(deleteMultipleVocablesDialogue != null) deleteMultipleVocablesDialogue.updateLanguages(newFirstLanguage, newPhoneticScript, newSecondLanguage);
//		//if(trainVocablesDialogue != null) trainVocablesDialogue.updateLanguages(newFirstLanguage, newPhoneticScript, newSecondLanguage);
//		//SearchBox.updateSearchBoxLabels(newFirstLanguage, newPhoneticScript, newSecondLanguage);
//		//if(vocableTable != null) vocableTable.updateTableHeader(newFirstLanguage, newPhoneticScript, newSecondLanguage);
//	}
	
	/**
	 * This method updates the languages when the user closes the first run dialogue. 
	 * It is different from the method that updates the languages used after the user closed the first run dialogue. 
	 * When the user has closed the first run dialogue the user could have changed the order of the columns of the vocable table, 
	 * which makes it necessary to access them using their headers, which are not available when the user starts the programm for the first time.
	 * @param newFirstLanguage the new first language
	 * @param newPhoneticScript the new phonetic script
	 * @param newSecondLanguage the new second language
	 */
//	public static void updateLanguagesOnFirstRun(String newFirstLanguage, String newPhoneticScript, String newSecondLanguage) {
//		if(addVocableDialogue != null) addVocableDialogue.changeVocabularyLanguages(newFirstLanguage, newPhoneticScript, newSecondLanguage);
//		if(changeVocableDialogue != null) changeVocableDialogue.changeVocabularyLanguages(newFirstLanguage, newPhoneticScript, newSecondLanguage);
//		if(deleteVocableDialogue != null) deleteVocableDialogue.updateLanguages(newFirstLanguage, newPhoneticScript, newSecondLanguage);
//		if(deleteMultipleVocablesDialogue != null) deleteMultipleVocablesDialogue.updateLanguages(newFirstLanguage, newPhoneticScript, newSecondLanguage);
//		if(trainVocablesDialogue != null) trainVocablesDialogue.updateLanguages(newFirstLanguage, newPhoneticScript, newSecondLanguage);
//		SearchBox.updateSearchBoxLabels(newFirstLanguage, newPhoneticScript, newSecondLanguage);
//		if(vocableTable != null) vocableTable.setTableHeader(newFirstLanguage, newPhoneticScript, newSecondLanguage);
//	}
	
//	public ArrayList<Vocable> getSearchResult() {
//		return currentSearchResult;
//	}
	
}