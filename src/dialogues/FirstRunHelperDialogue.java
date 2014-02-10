
package dialogues;

import factories.ObserveableFactory;
import gui.DictionaryMainWindow;
import gui.JTextFieldWithContextMenu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import net.miginfocom.swing.MigLayout;
import dictionary.Dictionary;
import dictionary.Settings;

/**
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class FirstRunHelperDialogue extends JDialog {
	
	@SuppressWarnings("unused")
	private DictionaryMainWindow window;
	
	private JTextPane firstRunTextPane;
	private JScrollPane firstRunTextPaneScrollPane;
	
	private static SimpleAttributeSet paragraph = new SimpleAttributeSet();
    private static SimpleAttributeSet heading1 = new SimpleAttributeSet();
    private static SimpleAttributeSet heading2 = new SimpleAttributeSet();
	
	private JPanel buttonPanel = new JPanel(new MigLayout("wrap 3"));
	private JButton nextButton = new JButton("Next");
	private JButton previousButton = new JButton("Previous");
	private JButton closeButton = new JButton("Close");
	
	private JPanel languagesInputPanel = new JPanel(new MigLayout("wrap 2"));
	private JLabel firstLanguageLabel = new JLabel("First language");
	private JLabel phoneticScriptLabel = new JLabel("Phonetic script");
	private JLabel secondLanguageLabel = new JLabel("Second language");
	private JTextFieldWithContextMenu firstLanguageTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu phoneticScriptTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu secondLanguageTextField = new JTextFieldWithContextMenu(20);
	
	private static final String INTRODUCTION_TEXT = "" +
					"Welcome to " + Dictionary.APPLICATION_NAME + ". " +
					"The program you're looking at is a combination of a dictionary and a vocable trainer. " +
					"It has many functions to enable you to enter, edit, delete and train your vocables. " +
					"This is a first run dialogue and won't be displayed next time you start " + Dictionary.APPLICATION_NAME + ". " +
					"All settings you change here will be accessible in the options menu later on." + 
					"Since this program is designed to support all kind of languages I could think of, " + 
					"it does not restrict you to predefined languages.";
	
	private static final String CHOOSE_LANGUAGES_TEXT = "" +
					"To begin your studies you should enter the names of the languages you want to use. " +
					"Most times that will be your mother tongue as the first language and the language " +
					"you want to learn as the second language. Don't let the phonetic script field confuse " +
					"you, if you don't know what that is you most likely won't need it for your languages.";
	
	private static final String FINALISATION_TEXT = "" +
					"Now your are ready to start using " + Dictionary.APPLICATION_NAME + "!\n" +
					"There is already a sample vocable file for the languages chinese and german. " +
					"If you want to use another vocable file, you can simply choose \"Create new dictionary\" " +
					"from the \"File\" menu at the top.";
	
	private static int INTRODUCTION = 1;
	private static int CHOOSE_LANGUAGES = 2;
	private static int FINALISATION = 3;
	
	private int displayedText = INTRODUCTION;
	
	private static final int TEXT_PANE_WIDTH = 400;
	private static final int TEXT_PANE_HEIGHT = 300;
	
	public FirstRunHelperDialogue(DictionaryMainWindow parent, boolean modal) {
		super(parent, modal);
		this.window = parent;
		initComponents();
	}
	
	
	private void initComponents() {
		defineStyleConstants();
		addComponents();
		addActionListeners();
		
		pack();
		setVisible(true);
	}
	
	private void addComponents() {
		this.setLayout(new MigLayout("wrap 1"));
				
		//TextPane
		firstRunTextPane = new JTextPane();
		firstRunTextPane.setPreferredSize(new Dimension(TEXT_PANE_WIDTH, TEXT_PANE_HEIGHT));
		firstRunTextPane.setEditable(false);
		firstRunTextPane.setBackground(getBackground());
		
		//ScrollPane
		firstRunTextPaneScrollPane = new JScrollPane(firstRunTextPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(firstRunTextPaneScrollPane, "cell 0 0");
		
		//Initial Text
		firstRunTextPane.setText("");
		addFirstRunText("Introduction\n\n", heading1);
		addFirstRunText(INTRODUCTION_TEXT, paragraph);
		
		//Buttons
		add(buttonPanel, "cell 0 2");
		buttonPanel.add(previousButton);
		previousButton.setEnabled(false);
		buttonPanel.add(nextButton);
		buttonPanel.add(closeButton);
		closeButton.setEnabled(false);
		
		pack();
		setLocationRelativeTo(null);
	}
	
	private void addLanguageInputFields() {
		add(languagesInputPanel, "cell 0 1");
		languagesInputPanel.add(firstLanguageLabel, "cell 0 0");
		languagesInputPanel.add(firstLanguageTextField, "cell 1 0");
		languagesInputPanel.add(phoneticScriptLabel, "cell 0 1");
		languagesInputPanel.add(phoneticScriptTextField, "cell 1 1");
		languagesInputPanel.add(secondLanguageLabel, "cell 0 2");
		languagesInputPanel.add(secondLanguageTextField, "cell 1 2");
		pack();
	}
	
	private void deleteLanguageInputFields() {
		getContentPane().remove(languagesInputPanel);
		pack();
	}
	
	private void addActionListeners() {
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextButtonAction();
			}
		});
		
		previousButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				previousButtonAction();
			}
		});
		
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeButtonAction();
			}
		});
	}
	
	private void nextButtonAction() {
		if(displayedText == INTRODUCTION) {
			firstRunTextPane.setText("");
			addFirstRunText("Choose your Languages\n\n", heading1);
			addFirstRunText(CHOOSE_LANGUAGES_TEXT, paragraph);
			addLanguageInputFields();
			previousButton.setEnabled(true);
			displayedText = CHOOSE_LANGUAGES;
		

		} else if(displayedText == CHOOSE_LANGUAGES) {
			//Go to finalisation state
			firstRunTextPane.setText("");
			addFirstRunText("Finalisation\n\n", heading1);
			addFirstRunText(FINALISATION_TEXT, paragraph);
			deleteLanguageInputFields();
			nextButton.setEnabled(false);
			closeButton.setEnabled(true);
			displayedText = FINALISATION;
		}
	}
	
	private void previousButtonAction() {
		if(displayedText == CHOOSE_LANGUAGES) {
			firstRunTextPane.setText("");
			addFirstRunText("Introduction\n\n", heading1);
			addFirstRunText(INTRODUCTION_TEXT, paragraph);
			deleteLanguageInputFields();
			previousButton.setEnabled(false);
			displayedText = INTRODUCTION;
		
		} else if(displayedText == FINALISATION) {
			firstRunTextPane.setText("");
			addFirstRunText("Choose your Languages\n\n", heading1);
			addFirstRunText(CHOOSE_LANGUAGES_TEXT, paragraph);
			addLanguageInputFields();
			nextButton.setEnabled(true);
			displayedText = CHOOSE_LANGUAGES;
		}
	}
	
	private void closeButtonAction() {
		//Save language settings
		if(firstLanguageTextField.getText().isEmpty()) {
			Settings.languageOptions_firstLanguageName = Settings.FIRST_LANGUAGE_DEFAULT;
		} else {
			Settings.languageOptions_firstLanguageName = firstLanguageTextField.getText();
		}
		
		if(phoneticScriptTextField.getText().isEmpty()) {
			Settings.languageOptions_phoneticScriptName = "---";
		} else {
			Settings.languageOptions_phoneticScriptName = phoneticScriptTextField.getText();
		}
		
		if(secondLanguageTextField.getText().isEmpty()) {
			Settings.languageOptions_secondLanguageName = Settings.SECOND_LANGUAGE_DEFAULT;
		} else {
			Settings.languageOptions_secondLanguageName = secondLanguageTextField.getText();
		}
		
		Settings.firstTimeRun = false;
		Settings.saveSettings();
		
		ObserveableFactory.getVocabularyLanguagesObserveable().setLanguageNames(Settings.languageOptions_firstLanguageName, Settings.languageOptions_phoneticScriptName, Settings.languageOptions_secondLanguageName);
		this.dispose();
	}
	
	
	private void addFirstRunText(String addedText, AttributeSet attrSet) {
		try {
			firstRunTextPane.getStyledDocument().setParagraphAttributes(firstRunTextPane.getDocument().getLength(), addedText.length(), attrSet, true);
			firstRunTextPane.getStyledDocument().insertString(firstRunTextPane.getDocument().getLength(), addedText, attrSet);
			firstRunTextPane.setCaretPosition(0);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void defineStyleConstants() {
		StyleConstants.setFontSize(paragraph, 12);
		StyleConstants.setAlignment(paragraph, StyleConstants.ALIGN_JUSTIFIED);
		
		StyleConstants.setAlignment(heading1, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontSize(heading1, 16);
		StyleConstants.setBold(heading1, true);
		
		StyleConstants.setAlignment(heading2, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontSize(heading2, 14);
		StyleConstants.setBold(heading2, true);
	}
}