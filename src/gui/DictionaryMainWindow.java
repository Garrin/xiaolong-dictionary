package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableRowSorter;

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
	private JPanel eastJPanel;
	public BigCharacterBoxForVocableTable bigCharacterBox;
	private VocableDetailBox vocableDetailBox;
	
	//SOUTH
	private JPanel southJPanel;
	private JLabel versionLabel;
	private static JLabel statusLabel;

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

		southJPanel = new JPanel(new MigLayout("wrap 3","[]push[center, grow]push[]","[]push[]"));
		eastJPanel = new JPanel(new MigLayout());
		westJPanel = new JPanel(new MigLayout("wrap 1", "[center]"));
		
		getContentPane().add(southJPanel, BorderLayout.SOUTH);
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
		createEASTComponents();
		
		//SOUTH
		createSOUTHComponents();
		
	}

	private void createMenu() {
		dictionaryMenuBar = new DictionaryMenuBar(this);
		setJMenuBar(dictionaryMenuBar);
	}

	private void createEASTComponents() {
		add(eastJPanel, BorderLayout.EAST);
		bigCharacterBox = new BigCharacterBoxForVocableTable(Settings.bigCharacterBoxOptions_ignored_characters);
		eastJPanel.add(bigCharacterBox, "cell 0 0 1 1");
	}
	
	private void createSOUTHComponents() {
		southJPanel.add(new JPanel());
		createVocableDetailsBox();
		southJPanel.add(new JPanel());
		createStatusLabel();
		createVersionLabel();
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
	
	private void createTitleLabel() {
		titleLabel = new JLabel("Dictionary", JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		add(titleLabel, BorderLayout.NORTH);
	}

	private void createStatusLabel() {
		statusLabel = ComponentFactory.getDictionaryStatusLabel();
		southJPanel.add(statusLabel);
	}

	private void createVocableDetailsBox() {
		vocableDetailBox = new VocableDetailBox();
		southJPanel.add(vocableDetailBox, "span 1 2");
	}
	
	private void createVersionLabel() {
		versionLabel = new JLabel(VERSION_LABEL_STRING);
		southJPanel.add(versionLabel);
	}
	
	public SaveOnExitConfirmation getSaveOnExitConfirmation() {
		return saveOnExitConfirmation;
	}
}