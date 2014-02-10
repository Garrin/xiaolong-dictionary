package gui;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import listener.settings.VocabularyLanguagesChangeListener;
import manager.VocableManager;
import net.miginfocom.swing.MigLayout;
import dialogues.Helper;
import dictionary.SearchEntry;
import dictionary.SearchHistory;
import dictionary.Settings;
import factories.ObserveableFactory;

/**
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class SearchBox extends JPanel implements VocabularyLanguagesChangeListener/*, VocableListChangedListener*/ {

	//Normal Search Components
	private static JPanel normalSearchPanel;
	private static JCheckBox firstLanguageCheckbox;
	private static JCheckBox phoneticScriptCheckbox;
	private static JCheckBox secondLanguageCheckbox;
	private static JCheckBox topicCheckbox;
	private static JCheckBox chapterCheckbox;
	private static JCheckBox learnLevelCheckbox;
	private static JCheckBox caseSensitivityCheckbox;
	private static JCheckBox exactMatchCheckbox;
	private static JCheckBox negateSearchCheckbox;
	private static JTextFieldWithContextMenu searchTermTextfield;
	
	//Specify Search Panel Components
	private static JPanel specifySearchPanel;
	private static JLabel specifySearchLabel;
	private static JCheckBox specifySearchNOTCheckbox;
	private static ButtonGroup specifyingSearchButtonGroup;
	private static JRadioButton specifySearchANDRadioButton;
	private static JRadioButton specifySearchORRadioButton;
	private static JTextFieldWithContextMenu specifySearchTextField;
	private static JButton specifySearchButton;
	
	//Button Panel
	private JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
	private JButton searchButton;
	private JButton specialCharacterButton;
	
	private JButton previousSearchButton;
	private JButton nextSearchButton;
	
	private boolean tonenPopupDialogueDisplayed = false;

	private TonenPopupDialogue tonenPopupDialogue;

	private String initialSearchTerm = "";
	
	/*public SearchBox(DictionaryMainWindow window) {
		this.parent = window;
		addComponents();
		addActionListeners();
	}
	
	public SearchBox(DictionaryMainWindow window, String initialSearchTerm) {
		this.parent = window;
		this.initialSearchTerm  = initialSearchTerm;
		addComponents();
		addActionListeners();
	}*/
	
	public SearchBox(String initialSearchTerm) {
		this.initialSearchTerm  = initialSearchTerm;
		addComponents();
		addListeners();
	}
	
	private void addComponents() {
		setLayout(new MigLayout("wrap 1"));
		setTitledBorder();
		addNormalSearchPanel();
		addButtonPanel();
		addSpecifySearchPanel();
	}
	
	private void setTitledBorder() {
		TitledBorder searchBoxTitledBorder;
		searchBoxTitledBorder = BorderFactory.createTitledBorder("Search");
		searchBoxTitledBorder.setTitleJustification(TitledBorder.CENTER);
		setBorder(searchBoxTitledBorder);
	}
	
	private void addNormalSearchPanel() {
		SearchBox.normalSearchPanel = new JPanel(new MigLayout("wrap 1"));
		
		SearchBox.firstLanguageCheckbox = new JCheckBox(Settings.languageOptions_firstLanguageName);
		SearchBox.firstLanguageCheckbox.setSelected(Settings.searchOptions_firstLanguageActivated);
		SearchBox.normalSearchPanel.add(firstLanguageCheckbox);
		
		SearchBox.phoneticScriptCheckbox = new JCheckBox(Settings.languageOptions_phoneticScriptName);
		SearchBox.phoneticScriptCheckbox.setSelected(Settings.searchOptions_phoneticScriptActivated);
		SearchBox.normalSearchPanel.add(phoneticScriptCheckbox);
		
		SearchBox.secondLanguageCheckbox = new JCheckBox(Settings.languageOptions_secondLanguageName);
		SearchBox.secondLanguageCheckbox.setSelected(Settings.searchOptions_secondLanguageActivated);
		SearchBox.normalSearchPanel.add(secondLanguageCheckbox);

		SearchBox.topicCheckbox = new JCheckBox("Topic");
		SearchBox.topicCheckbox.setSelected(Settings.searchOptions_topicActivated);
		SearchBox.normalSearchPanel.add(topicCheckbox);
		
		SearchBox.chapterCheckbox = new JCheckBox("Chapter");
		SearchBox.chapterCheckbox.setSelected(Settings.searchOptions_chapterActivated);
		SearchBox.normalSearchPanel.add(chapterCheckbox);

		SearchBox.learnLevelCheckbox = new JCheckBox("Level");
		SearchBox.learnLevelCheckbox.setSelected(Settings.searchOptions_learnLevelActivated);
		SearchBox.normalSearchPanel.add(learnLevelCheckbox);
		
		SearchBox.caseSensitivityCheckbox = new JCheckBox("Case sensitive");
		SearchBox.caseSensitivityCheckbox.setSelected(Settings.searchOptions_matchCaseActivated);
		SearchBox.normalSearchPanel.add(caseSensitivityCheckbox);
		
		SearchBox.exactMatchCheckbox = new JCheckBox("Exact match");
		SearchBox.exactMatchCheckbox.setSelected(Settings.searchOptions_exactMatchActivated);
		SearchBox.normalSearchPanel.add(exactMatchCheckbox);
		
		SearchBox.negateSearchCheckbox = new JCheckBox("Negate search");
		SearchBox.negateSearchCheckbox.setSelected(Settings.searchOptions_negateSearchActivated);
		SearchBox.normalSearchPanel.add(negateSearchCheckbox);
		
		SearchBox.searchTermTextfield  = new JTextFieldWithContextMenu(initialSearchTerm);
		SearchBox.searchTermTextfield.setColumns(10);
		SearchBox.normalSearchPanel.add(searchTermTextfield);
		
		add(SearchBox.normalSearchPanel);
	}
	
	private void addButtonPanel() {
		searchButton = new JButton("Search");
		searchButton.setMargin(new Insets(4, 4, 4, 4));
		buttonPanel.add(searchButton);
		
		specialCharacterButton = new JButton();
		try {
			Image img = ImageIO.read(new File("src/resources/insertsymbol.png"));
			specialCharacterButton.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//specialCharacterButton.setContentAreaFilled(false);
		specialCharacterButton.setMargin(new Insets(4,4,4,4));
		buttonPanel.add(specialCharacterButton);
		
		previousSearchButton = new JButton("<--");
		previousSearchButton.setMargin(new Insets(4, 4, 4, 4));
		//previousSearchButton.setMargin(new Insets(0, 0, 0, 0));
		
		nextSearchButton = new JButton("-->");
		nextSearchButton.setMargin(new Insets(4, 4, 4, 4));
		//nextSearchButton.setMargin(new Insets(0, 0, 0, 0));
		
		buttonPanel.add(previousSearchButton);
		buttonPanel.add(nextSearchButton);
		
		add(buttonPanel, "wrap");
	}
	
	private void addSpecifySearchPanel() {
		//TODO: Implement specifying search!
		
		//Initialize components
		SearchBox.specifySearchPanel = new JPanel(new MigLayout("wrap 2"));
		
		SearchBox.specifySearchLabel = new JLabel("Specify search:");
		
		SearchBox.specifySearchNOTCheckbox = new JCheckBox("NOT");
		
		SearchBox.specifyingSearchButtonGroup = new ButtonGroup();
		SearchBox.specifySearchANDRadioButton = new JRadioButton("AND");
		SearchBox.specifySearchORRadioButton = new JRadioButton("OR");
		SearchBox.specifyingSearchButtonGroup.add(specifySearchANDRadioButton);
		SearchBox.specifyingSearchButtonGroup.add(specifySearchORRadioButton);

		SearchBox.specifySearchTextField = new JTextFieldWithContextMenu("");
		SearchBox.specifySearchTextField.setColumns(10);
		SearchBox.specifySearchButton = new JButton("Specify search");
		
		//Add components to specifySearchPanel
		SearchBox.specifySearchPanel.add(specifySearchLabel, "wrap");
		SearchBox.specifySearchPanel.add(specifySearchNOTCheckbox, "wrap");
		SearchBox.specifySearchPanel.add(specifySearchANDRadioButton, "wrap");
		SearchBox.specifySearchPanel.add(specifySearchORRadioButton, "wrap");
		SearchBox.specifySearchPanel.add(specifySearchTextField, "wrap");
		SearchBox.specifySearchPanel.add(specifySearchButton, "wrap");
		SearchBox.specifySearchANDRadioButton.setSelected(true);
		
		add(SearchBox.specifySearchPanel);
	}
	
	/**
	 * Adds all kind of listeners that are required.
	 */
	private void addListeners() {
		addActionListeners();
		addObservableListeners();
	}
	
	private void addActionListeners() {
		getSearchTermTextfield().addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				//throw new UnsupportedOperationException("Not supported yet.");
			}
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					searchVocableButtonActionPerformed();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				//throw new UnsupportedOperationException("Not supported yet.");
			}
		});
		
		getSearchButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchVocableButtonActionPerformed();
			}
		});
		
		specialCharacterButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(tonenPopupDialogueDisplayed) {
					tonenPopupDialogue.dispose();
					tonenPopupDialogueDisplayed = false;
				} else {
					displayTonenPopupDialogue();
					tonenPopupDialogueDisplayed = true;
				}
			}
		});
		
		previousSearchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				previousSearchButtonClicked();
			}
		});
		
		nextSearchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				nextSearchButtonClicked();
			}
		});
		
		specifySearchTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				//throw new UnsupportedOperationException("Not supported yet.");
			}
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					specifySearchActionPerformed();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				//throw new UnsupportedOperationException("Not supported yet.");
			}
		});
		
		specifySearchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				specifySearchActionPerformed();
			}
		});
	}
	
	private void addObservableListeners() {
		ObserveableFactory.getVocabularyLanguagesObserveable().registerListener(this);
		//ObserveableFactory.getVocablesObserveable().registerVocableListChangedListener(this);
	}
	
	/**
	 * Handles actions performed when the search button is clicked
	 */
	public static void searchVocableButtonActionPerformed() {
		
		VocableManager.basicSearchVocableList(
				searchTermTextfield.getText(), 
				topicCheckbox.isSelected(), 
				chapterCheckbox.isSelected(), 
				firstLanguageCheckbox.isSelected(),	
				secondLanguageCheckbox.isSelected(), 
				phoneticScriptCheckbox.isSelected(), 
				learnLevelCheckbox.isSelected(), 
				caseSensitivityCheckbox.isSelected(), 
				exactMatchCheckbox.isSelected(), 
				negateSearchCheckbox.isSelected());
		
//		if(DictionaryMainWindow.trainVocablesDialogue != null) {
//			DictionaryMainWindow.trainVocablesDialogue.resetMembers();
//		}
		
		SearchEntry searchHistoryEntry = new SearchEntry(
				firstLanguageCheckbox.isSelected(), 
				phoneticScriptCheckbox.isSelected(), 
				secondLanguageCheckbox.isSelected(), 
				topicCheckbox.isSelected(), 
				chapterCheckbox.isSelected(), 
				learnLevelCheckbox.isSelected(), 
				caseSensitivityCheckbox.isSelected(), 
				exactMatchCheckbox.isSelected(),
				negateSearchCheckbox.isSelected(),
				searchTermTextfield.getText());
		
		SearchHistory.addEntry(searchHistoryEntry);
		//SearchHistory.printSearchHistory();
	}
	
	public static void specifySearchActionPerformed() {
		boolean checkFirstLanguage = firstLanguageCheckbox.isSelected(); 
		boolean checkPhoneticScript = phoneticScriptCheckbox.isSelected(); 
		boolean checkSecondLanguage = secondLanguageCheckbox.isSelected(); 
		boolean checkTopic = topicCheckbox.isSelected(); 
		boolean checkChapter = chapterCheckbox.isSelected(); 
		boolean checkLearnLevel = learnLevelCheckbox.isSelected(); 
		boolean checkCaseSensitive = caseSensitivityCheckbox.isSelected(); 
		boolean checkExactMatch = exactMatchCheckbox.isSelected();
		
		boolean notSearch = specifySearchNOTCheckbox.isSelected();
		boolean andSearch = specifySearchANDRadioButton.isSelected();
		boolean orSearch = specifySearchORRadioButton.isSelected();
		String searched = specifySearchTextField.getText();
		
		VocableManager.performSpecifyingSearch(searched, checkTopic, checkChapter, checkFirstLanguage, checkSecondLanguage, checkPhoneticScript, checkLearnLevel, checkCaseSensitive, checkExactMatch, notSearch, andSearch, orSearch);
	}
	
	public static void historyVocableSearch() {
		VocableManager.basicSearchVocableList(
				searchTermTextfield.getText(), 
				topicCheckbox.isSelected(),	
				chapterCheckbox.isSelected(), 
				firstLanguageCheckbox.isSelected(),	
				secondLanguageCheckbox.isSelected(), 
				phoneticScriptCheckbox.isSelected(), 
				learnLevelCheckbox.isSelected(), 
				caseSensitivityCheckbox.isSelected(), 
				exactMatchCheckbox.isSelected(), 
				negateSearchCheckbox.isSelected());
		
		//DictionaryMainWindow.updateVocableTable(searchResult);
		
//		if(DictionaryMainWindow.trainVocablesDialogue != null) {
//			DictionaryMainWindow.trainVocablesDialogue.resetMembers();
//		}
		//SearchHistory.printSearchHistory();
	}
	
	private static void previousSearchButtonClicked() {
		SearchEntry previousSearchEntry = SearchHistory.getPreviousSearchTerm();
		firstLanguageCheckbox.setSelected(previousSearchEntry.firstLanguageSelected);
		phoneticScriptCheckbox.setSelected(previousSearchEntry.phoneticScriptSelected);
		secondLanguageCheckbox.setSelected(previousSearchEntry.secondLanguageSelected);
		topicCheckbox.setSelected(previousSearchEntry.topicSelected);
		chapterCheckbox.setSelected(previousSearchEntry.chapterSelecter);
		learnLevelCheckbox.setSelected(previousSearchEntry.learnLevelSelected);
		caseSensitivityCheckbox.setSelected(previousSearchEntry.caseSensitiveSelected);
		exactMatchCheckbox.setSelected(previousSearchEntry.exactMatchSelected);
		searchTermTextfield.setText(previousSearchEntry.searchTerm);
		historyVocableSearch();
		//System.out.println("Previous Search Button Clicked!");
	}
	
	private static void nextSearchButtonClicked() {
		SearchEntry nextSearchEntry = SearchHistory.getNextSearchTerm();
		firstLanguageCheckbox.setSelected(nextSearchEntry.firstLanguageSelected);
		phoneticScriptCheckbox.setSelected(nextSearchEntry.phoneticScriptSelected);
		secondLanguageCheckbox.setSelected(nextSearchEntry.secondLanguageSelected);
		topicCheckbox.setSelected(nextSearchEntry.topicSelected);
		chapterCheckbox.setSelected(nextSearchEntry.chapterSelecter);
		learnLevelCheckbox.setSelected(nextSearchEntry.learnLevelSelected);
		caseSensitivityCheckbox.setSelected(nextSearchEntry.caseSensitiveSelected);
		exactMatchCheckbox.setSelected(nextSearchEntry.exactMatchSelected);
		searchTermTextfield.setText(nextSearchEntry.searchTerm);
		historyVocableSearch();
		//System.out.println("Next Search Button Clicked!");
	}

	/**
	 * @return the search_german_checkbox
	 */
	public JCheckBox getGermanCheckbox() {
		return firstLanguageCheckbox;
	}

	/**
	 * @return the search_pinyin_checkbox
	 */
	public JCheckBox getPinyinCheckbox() {
		return phoneticScriptCheckbox;
	}

	/**
	 * @return the search_chinese_checkbox
	 */
	public JCheckBox getChineseCheckbox() {
		return secondLanguageCheckbox;
	}

	/**
	 * @return the search_topic_checkbox
	 */
	public JCheckBox getTopicCheckbox() {
		return topicCheckbox;
	}

	/**
	 * @return the search_chapter_checkbox
	 */
	public JCheckBox getChapterCheckbox() {
		return chapterCheckbox;
	}

	/**
	 * @return the search_level_checkbox
	 */
	public JCheckBox getLevelCheckbox() {
		return learnLevelCheckbox;
	}

	/**
	 * @return the search_case_sensitivity_checkbox
	 */
	public JCheckBox getCaseSensitivityCheckbox() {
		return caseSensitivityCheckbox;
	}

	/**
	 * @return the search_term_textfield
	 */
	public JTextField getSearchTermTextfield() {
		return searchTermTextfield;
	}

	/**
	 * @return the search_button
	 */
	public JButton getSearchButton() {
		return searchButton;
	}
	
	public static void updateSearchBoxCheckboxes(boolean firstLanguageSelected,
			boolean phoneticScriptSelected,
			boolean secondLanguageSelected,
			boolean topicSelected,
			boolean chapterSelected,
			boolean learnLevelSelected,
			boolean caseSensitivySelected,
			boolean exactMatchSelected,
			boolean negateSearchSelect) {
		SearchBox.firstLanguageCheckbox.setSelected(firstLanguageSelected);
		SearchBox.phoneticScriptCheckbox.setSelected(phoneticScriptSelected);
		SearchBox.secondLanguageCheckbox.setSelected(secondLanguageSelected);
		SearchBox.topicCheckbox.setSelected(topicSelected);
		SearchBox.chapterCheckbox.setSelected(chapterSelected);
		SearchBox.learnLevelCheckbox.setSelected(learnLevelSelected);
		SearchBox.caseSensitivityCheckbox.setSelected(caseSensitivySelected);
		SearchBox.exactMatchCheckbox.setSelected(exactMatchSelected);
		SearchBox.negateSearchCheckbox.setSelected(negateSearchSelect);
	}
	
//	public static void updateSearchBoxLabels(String firstLanguageLabelText, String phoneticScriptLabelText, String secondLanguageLabelText) {
//		SearchBox.firstLanguageCheckbox.setText(firstLanguageLabelText);
//		SearchBox.phoneticScriptCheckbox.setText(phoneticScriptLabelText);
//		SearchBox.secondLanguageCheckbox.setText(secondLanguageLabelText);
//	}
	
	/**
	 * This function updates the search term in the search term input field of the search box.
	 * This is needed when deleting a SearchEntry from the SearchHistory, which is displayed at the moment of deletion.  
	 * @param searchTerm
	 */
	public static void updateSearchBoxSearchTerm(String searchTerm) {
		SearchBox.searchTermTextfield.setText(searchTerm);
	}
	
	public void addSpecialCharacterToSearchTextField(String specialCharacter) {
		Helper.insertCharacterIntoTextfield(searchTermTextfield, specialCharacter);
	}

	public void addSpecialCharacterToTextField(String specialCharacter) {
		addSpecialCharacterToSearchTextField(specialCharacter);
	}
	
	private void displayTonenPopupDialogue() {
		try {
			tonenPopupDialogue = new TonenPopupDialogue(this, false, specialCharacterButton);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public boolean isTonenPopupDialogueDisplayed() {
		return tonenPopupDialogueDisplayed;
	}

	public void setTonenPopupDialogueDisplayed(boolean tonenPopupDialogueDisplayed) {
		this.tonenPopupDialogueDisplayed = tonenPopupDialogueDisplayed;
	}

	public static boolean isFirstLanguageCheckboxSelected() {
		return firstLanguageCheckbox.isSelected();
	}

	public static boolean isPhoneticScriptCheckboxSelected() {
		return phoneticScriptCheckbox.isSelected();
	}
	
	public static boolean isSecondLanguageCheckboxSelected() {
		return secondLanguageCheckbox.isSelected();
	}
	
	public static boolean isTopicCheckboxSelected() {
		return topicCheckbox.isSelected();
	}
	
	public static boolean isChapterCheckboxSelected() {
		return chapterCheckbox.isSelected();
	}
	
	public static boolean isLearnLevelCheckboxSelected() {
		return learnLevelCheckbox.isSelected();
	}

	public static boolean isExactMatchCheckboxSelected() {
		return exactMatchCheckbox.isSelected();
	}

	public static boolean isCaseSensitiveCheckboxSelected() {
		return caseSensitivityCheckbox.isSelected();
	}
	
	public static boolean isNegateSearchCheckboxSelected() {
		return negateSearchCheckbox.isSelected();
	}
	
	public static String getSearchTerm() {
		return searchTermTextfield.getText();
	}

	@Override
	public void changeVocabularyLanguages(String newFirstLanguage, String newPhoneticScript, String newSecondLanguage) {
		SearchBox.firstLanguageCheckbox.setText(newFirstLanguage);
		SearchBox.phoneticScriptCheckbox.setText(newPhoneticScript);
		SearchBox.secondLanguageCheckbox.setText(newSecondLanguage);
	}

	
//	@Override
//	public void vocableListChangedActionPerformed() {
//		searchVocableButtonActionPerformed();
//	}
}
