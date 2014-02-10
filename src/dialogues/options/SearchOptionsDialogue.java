
package dialogues.options;

import dictionary.Settings;
import gui.DictionaryMainWindow;
import gui.SearchBox;
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
public class SearchOptionsDialogue extends JDialog {

	//private final DictionaryMainWindow window;
	
	private JPanel standardSearchCriteriaPanel = new JPanel(new MigLayout("wrap 1"));
	
	private JCheckBox firstLanguageCheckbox = new JCheckBox(Settings.languageOptions_firstLanguageName);
	private JCheckBox phoneticScriptCheckbox = new JCheckBox(Settings.languageOptions_phoneticScriptName);
	private JCheckBox secondLanguageCheckbox = new JCheckBox(Settings.languageOptions_secondLanguageName);
	private JCheckBox topicCheckbox = new JCheckBox("Topic");
	private JCheckBox chapterCheckbox = new JCheckBox("Chapter");
	private JCheckBox learnLevelCheckbox = new JCheckBox("Learn level");
	private JCheckBox matchCaseCheckbox = new JCheckBox("Case sensitive");
	private JCheckBox exactMatchCheckbox = new JCheckBox("Exact match");
	private JCheckBox negateSearchCheckbox = new JCheckBox("Negate search"); 
	
	private JPanel buttonPanel = new JPanel(new MigLayout("wrap 3"));
	
	private JButton okButton = new JButton("Ok");
	private JButton saveSettingsButton = new JButton("Save settings");
	private JButton cancelButton = new JButton("Cancel");
	
	public SearchOptionsDialogue(DictionaryMainWindow parent, boolean modal) {
		super(parent, modal);
		//this.window = parent;
		addComponents();
		addActionListeners();
	}
	
	private void addComponents() {
		setLayout(new MigLayout("wrap 3"));
		
		TitledBorder searchBoxTitledBorder;
		searchBoxTitledBorder = BorderFactory.createTitledBorder("Search criteria");
		searchBoxTitledBorder.setTitleJustification(TitledBorder.CENTER);
		standardSearchCriteriaPanel.setBorder(searchBoxTitledBorder);
		
		firstLanguageCheckbox.setSelected(Settings.searchOptions_firstLanguageActivated);
		phoneticScriptCheckbox.setSelected(Settings.searchOptions_phoneticScriptActivated);
		secondLanguageCheckbox.setSelected(Settings.searchOptions_secondLanguageActivated);
		topicCheckbox.setSelected(Settings.searchOptions_topicActivated);
		chapterCheckbox.setSelected(Settings.searchOptions_chapterActivated);
		learnLevelCheckbox.setSelected(Settings.searchOptions_learnLevelActivated);
		matchCaseCheckbox.setSelected(Settings.searchOptions_matchCaseActivated);
		exactMatchCheckbox.setSelected(Settings.searchOptions_exactMatchActivated);
		negateSearchCheckbox.setSelected(Settings.searchOptions_negateSearchActivated);
		
		
		standardSearchCriteriaPanel.add(firstLanguageCheckbox, "cell 0 0");
		standardSearchCriteriaPanel.add(phoneticScriptCheckbox, "cell 0 1");
		standardSearchCriteriaPanel.add(secondLanguageCheckbox, "cell 0 2");
		standardSearchCriteriaPanel.add(topicCheckbox, "cell 0 3");
		standardSearchCriteriaPanel.add(chapterCheckbox, "cell 0 4");
		standardSearchCriteriaPanel.add(learnLevelCheckbox, "cell 0 5");
		standardSearchCriteriaPanel.add(matchCaseCheckbox, "cell 0 6");
		standardSearchCriteriaPanel.add(exactMatchCheckbox, "cell 0 7");
		standardSearchCriteriaPanel.add(negateSearchCheckbox, "cell 0 8");
		
		add(standardSearchCriteriaPanel, "cell 0 0");
		
		buttonPanel.add(okButton, "cell 0 0");
		buttonPanel.add(saveSettingsButton, "cell 1 0");
		buttonPanel.add(cancelButton, "cell 2 0");
		
		add(buttonPanel, "cell 0 1");
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
		updateSearchBox();
		saveSettings();
		this.dispose();
	}
	
	private void saveSettingsButtonAction() {
		updateSearchBox();
		saveSettings();
	}
	
	private void cancelButtonAction() {
		this.dispose();
	}
	
	private void updateSearchBox() {
		SearchBox.updateSearchBoxCheckboxes(firstLanguageCheckbox.isSelected(),
			   phoneticScriptCheckbox.isSelected(),
			   secondLanguageCheckbox.isSelected(),
			   topicCheckbox.isSelected(),
			   chapterCheckbox.isSelected(),
			   learnLevelCheckbox.isSelected(),
			   matchCaseCheckbox.isSelected(),
			   exactMatchCheckbox.isSelected(),
			   negateSearchCheckbox.isSelected());
	}
	
	private void saveSettings() {
		Settings.searchOptions_firstLanguageActivated = firstLanguageCheckbox.isSelected();
		Settings.searchOptions_phoneticScriptActivated = phoneticScriptCheckbox.isSelected();
		Settings.searchOptions_secondLanguageActivated = secondLanguageCheckbox.isSelected();
		Settings.searchOptions_topicActivated = topicCheckbox.isSelected();
		Settings.searchOptions_chapterActivated = chapterCheckbox.isSelected();
		Settings.searchOptions_learnLevelActivated = learnLevelCheckbox.isSelected();
		Settings.searchOptions_matchCaseActivated = matchCaseCheckbox.isSelected();
		Settings.searchOptions_exactMatchActivated = exactMatchCheckbox.isSelected();
		Settings.searchOptions_negateSearchActivated = negateSearchCheckbox.isSelected();
		Settings.saveSettings();
	}
}