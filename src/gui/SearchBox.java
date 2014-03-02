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
public class SearchBox extends JPanel implements VocabularyLanguagesChangeListener {

	//Normal Search Components
	private static JPanel normalSearchPanel;
	private static JCheckBox firstLanguageCheckbox;
	private static JCheckBox phoneticScriptCheckbox;
	private static JCheckBox secondLanguageCheckbox;
	private static JCheckBox topicCheckbox;
	private static JCheckBox chapterCheckbox;
	private static JCheckBox learnLevelCheckbox;
	private static JCheckBox relevanceCheckbox;
	private static JCheckBox descriptionCheckbox;
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
		
		SearchBox.relevanceCheckbox = new JCheckBox("Relevance");
		SearchBox.relevanceCheckbox.setSelected(Settings.searchOptions_relevanceActivated);
		SearchBox.normalSearchPanel.add(relevanceCheckbox);
		
		SearchBox.descriptionCheckbox = new JCheckBox("Description");
		SearchBox.descriptionCheckbox.setSelected(Settings.searchOptions_descriptionActivated);
		SearchBox.normalSearchPanel.add(descriptionCheckbox);
		
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
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
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
			relevanceCheckbox.isSelected(),
			descriptionCheckbox.isSelected(),
			caseSensitivityCheckbox.isSelected(), 
			exactMatchCheckbox.isSelected(), 
			negateSearchCheckbox.isSelected()
		);
		
		SearchEntry searchHistoryEntry = new SearchEntry(
			firstLanguageCheckbox.isSelected(), 
			phoneticScriptCheckbox.isSelected(), 
			secondLanguageCheckbox.isSelected(), 
			topicCheckbox.isSelected(), 
			chapterCheckbox.isSelected(), 
			learnLevelCheckbox.isSelected(), 
			relevanceCheckbox.isSelected(),
			descriptionCheckbox.isSelected(),
			caseSensitivityCheckbox.isSelected(), 
			exactMatchCheckbox.isSelected(),
			negateSearchCheckbox.isSelected(),
			searchTermTextfield.getText()
		);
		
		SearchHistory.addEntry(searchHistoryEntry);
	}
	
	public static void specifySearchActionPerformed() {
		boolean checkFirstLanguage = firstLanguageCheckbox.isSelected(); 
		boolean checkPhoneticScript = phoneticScriptCheckbox.isSelected(); 
		boolean checkSecondLanguage = secondLanguageCheckbox.isSelected(); 
		boolean checkTopic = topicCheckbox.isSelected(); 
		boolean checkChapter = chapterCheckbox.isSelected(); 
		boolean checkLearnLevel = learnLevelCheckbox.isSelected(); 
		boolean checkRelevance = relevanceCheckbox.isSelected();
		boolean checkDescription = descriptionCheckbox.isSelected();
		boolean checkCaseSensitive = caseSensitivityCheckbox.isSelected(); 
		boolean checkExactMatch = exactMatchCheckbox.isSelected();
		
		boolean notSearch = specifySearchNOTCheckbox.isSelected();
		boolean andSearch = specifySearchANDRadioButton.isSelected();
		boolean orSearch = specifySearchORRadioButton.isSelected();
		String searched = specifySearchTextField.getText();
		
		VocableManager.performSpecifyingSearch(
			searched,
			checkTopic,
			checkChapter,
			checkFirstLanguage,
			checkSecondLanguage,
			checkPhoneticScript,
			checkLearnLevel,
			checkRelevance,
			checkDescription,
			checkCaseSensitive,
			checkExactMatch,
			notSearch,
			andSearch,
			orSearch
		);
	}
	
	/**
	 * This method searches the vocable list without adding a new {@link SearchEntry} to the {@link SearchHistory}.
	 */
	public static void historyVocableSearch() {
		VocableManager.basicSearchVocableList(
			searchTermTextfield.getText(), 
			topicCheckbox.isSelected(),	
			chapterCheckbox.isSelected(), 
			firstLanguageCheckbox.isSelected(),	
			secondLanguageCheckbox.isSelected(), 
			phoneticScriptCheckbox.isSelected(), 
			learnLevelCheckbox.isSelected(),
			relevanceCheckbox.isSelected(),
			descriptionCheckbox.isSelected(),
			caseSensitivityCheckbox.isSelected(), 
			exactMatchCheckbox.isSelected(), 
			negateSearchCheckbox.isSelected()
		);
	}
	
	private static void previousSearchButtonClicked() {
		SearchEntry previousSearchEntry = SearchHistory.getPreviousSearchTerm();
		firstLanguageCheckbox.setSelected(previousSearchEntry.firstLanguageSelected);
		phoneticScriptCheckbox.setSelected(previousSearchEntry.phoneticScriptSelected);
		secondLanguageCheckbox.setSelected(previousSearchEntry.secondLanguageSelected);
		topicCheckbox.setSelected(previousSearchEntry.topicSelected);
		chapterCheckbox.setSelected(previousSearchEntry.chapterSelecter);
		learnLevelCheckbox.setSelected(previousSearchEntry.learnLevelSelected);
		relevanceCheckbox.setSelected(previousSearchEntry.relevanceSelected);
		descriptionCheckbox.setSelected(previousSearchEntry.descriptionSelected);
		caseSensitivityCheckbox.setSelected(previousSearchEntry.caseSensitiveSelected);
		exactMatchCheckbox.setSelected(previousSearchEntry.exactMatchSelected);
		searchTermTextfield.setText(previousSearchEntry.searchTerm);
		historyVocableSearch();
	}
	
	private static void nextSearchButtonClicked() {
		SearchEntry nextSearchEntry = SearchHistory.getNextSearchTerm();
		firstLanguageCheckbox.setSelected(nextSearchEntry.firstLanguageSelected);
		phoneticScriptCheckbox.setSelected(nextSearchEntry.phoneticScriptSelected);
		secondLanguageCheckbox.setSelected(nextSearchEntry.secondLanguageSelected);
		topicCheckbox.setSelected(nextSearchEntry.topicSelected);
		chapterCheckbox.setSelected(nextSearchEntry.chapterSelecter);
		learnLevelCheckbox.setSelected(nextSearchEntry.learnLevelSelected);
		relevanceCheckbox.setSelected(nextSearchEntry.relevanceSelected);
		descriptionCheckbox.setSelected(nextSearchEntry.descriptionSelected);
		caseSensitivityCheckbox.setSelected(nextSearchEntry.caseSensitiveSelected);
		exactMatchCheckbox.setSelected(nextSearchEntry.exactMatchSelected);
		searchTermTextfield.setText(nextSearchEntry.searchTerm);
		historyVocableSearch();
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
			System.out.println("Could not create TonenPopupDialogue!");
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

	public static boolean isRelevanceCheckboxSelected() {
		return relevanceCheckbox.isSelected();
	}
	
	public static boolean isDescriptionCheckboxSelected() {
		return descriptionCheckbox.isSelected();
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

	/**
	 * This method updates selections of checkboxes in the {@link SearchBox}.
	 * @param firstLanguageCheckboxSelected true if the first language checkbox shall be selected
	 * @param phoneticScriptCheckboxSelected true if the phonetic script checkbox shall be selected
	 * @param secondLanguageCheckboxSelected true if the second language checkbox shall be selected
	 * @param topicCheckboxSelected true if the topic checkbox shall be selected
	 * @param chapterCheckboxSelected true if the chapter checkbox shall be selected
	 * @param learnLevelCheckboxSelected true if the learn level checkbox shall be selected
	 * @param relevanceCheckboxSelected true if the relevance checkbox shall be selected
	 * @param descriptionCheckboxSelected true if the description checkbox shall be selected
	 * @param matchCaseCheckboxSelected true if the match case checkbox shall be selected
	 * @param exactMatchCheckboxSelected true if the exact match checkbox shall be selected
	 * @param negateSearchCheckboxSelected true if the negate search checkbox shall be selected
	 */
	public static void updateSearchBoxCheckboxes(
		boolean firstLanguageCheckboxSelected,
		boolean phoneticScriptCheckboxSelected,
		boolean secondLanguageCheckboxSelected,
		boolean topicCheckboxSelected,
		boolean chapterCheckboxSelected,
		boolean learnLevelCheckboxSelected,
		boolean relevanceCheckboxSelected,
		boolean descriptionCheckboxSelected,
		boolean matchCaseCheckboxSelected,
		boolean exactMatchCheckboxSelected,
		boolean negateSearchCheckboxSelected
	) {
		SearchBox.firstLanguageCheckbox.setSelected(firstLanguageCheckboxSelected);
		SearchBox.phoneticScriptCheckbox.setSelected(phoneticScriptCheckboxSelected);
		SearchBox.secondLanguageCheckbox.setSelected(secondLanguageCheckboxSelected);
		SearchBox.topicCheckbox.setSelected(topicCheckboxSelected);
		SearchBox.chapterCheckbox.setSelected(chapterCheckboxSelected);
		SearchBox.learnLevelCheckbox.setSelected(learnLevelCheckboxSelected);
		SearchBox.relevanceCheckbox.setSelected(relevanceCheckboxSelected);
		SearchBox.descriptionCheckbox.setSelected(descriptionCheckboxSelected);
		SearchBox.caseSensitivityCheckbox.setSelected(matchCaseCheckboxSelected);
		SearchBox.exactMatchCheckbox.setSelected(exactMatchCheckboxSelected);
		SearchBox.negateSearchCheckbox.setSelected(negateSearchCheckboxSelected);
	}

}
