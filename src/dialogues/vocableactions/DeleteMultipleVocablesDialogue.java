
package dialogues.vocableactions;

import factories.FrameFactory;
import factories.ObserveableFactory;
import gui.DictionaryMainWindow;
import gui.JTextFieldWithContextMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import listener.settings.VocabularyLanguagesChangeListener;
import manager.VocableManager;
import net.miginfocom.swing.MigLayout;
import dialogues.Helper;
import dictionary.Settings;
import dictionary.Vocable;

/**
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class DeleteMultipleVocablesDialogue extends JDialog implements VocabularyLanguagesChangeListener {
	
	private JTextArea warningTextArea;
	private JLabel firstLanguageLabel;
	private JLabel phoneticScriptLabel;
	private JLabel secondLanguageLabel;
	private JLabel topicLabel = new JLabel("Topic");
	private JLabel chapterLabel = new JLabel("Chapter");
	private JLabel learnLevelLabel = new JLabel("Level");
	private JLabel relevanceLabel = new JLabel("Relevance");
	private JLabel descriptionLabel = new JLabel("Description");

	private JTextFieldWithContextMenu firstLanguageTextField;
	private JTextFieldWithContextMenu phoneticScriptTextField;
	private JTextFieldWithContextMenu secondLanguageTextField;
	private JTextFieldWithContextMenu topicTextField;
	private JTextFieldWithContextMenu chapterTextField;
	private JTextFieldWithContextMenu learnLevelTextField;
	private JTextFieldWithContextMenu relevanceTextField;
	private JTextArea descriptionTextArea;
	private JScrollPane descriptionScrollPane;
	
	private JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 5, 5));
	private JButton yesButton = new JButton("Yes");
	private JButton noButton = new JButton("No");
	private JButton yesAllButton = new JButton("Yes all");
	private JButton noAllButton = new JButton("No all");
	private JButton cancelButton = new JButton("Cancel");
	
	private int currentVocableNumber = 0;
	private ArrayList<Vocable> vocables;

	private static DeleteMultipleVocablesDialogue instance = null;
	private static boolean listenersAdded = false;
	
	
	private DeleteMultipleVocablesDialogue(DictionaryMainWindow window, boolean modal) {
		super(window, modal);
	}
	
	/**
	 * returns the singleton instance
	 * @return the singleton instance
	 */
	public static DeleteMultipleVocablesDialogue getInstance() {
		if(DeleteMultipleVocablesDialogue.instance == null) {
			DeleteMultipleVocablesDialogue.instance = new DeleteMultipleVocablesDialogue(FrameFactory.getDictionaryMainWindow(), true); 
		}
		return DeleteMultipleVocablesDialogue.instance;
	}
	
	/**
	 * initializes the {@link DeleteMultipleVocablesDialogue} for use
	 */
	public void initialize(ArrayList<Vocable> vocables) {
		
		for(Vocable voc : vocables) {
			System.out.println("--"+voc.getFirstLanguage()+"|"+voc.getPhoneticScript()+"|"+voc.getSecondLanguage()+"--");
		}
		
		getContentPane().removeAll();
		
		currentVocableNumber = 0;
		this.vocables = vocables;
		
		firstLanguageLabel = new JLabel(Settings.languageOptions_firstLanguageName);
		phoneticScriptLabel = new JLabel(Settings.languageOptions_phoneticScriptName);
		secondLanguageLabel = new JLabel(Settings.languageOptions_secondLanguageName);
		
		firstLanguageTextField = new JTextFieldWithContextMenu("");
		phoneticScriptTextField = new JTextFieldWithContextMenu("");
		secondLanguageTextField = new JTextFieldWithContextMenu("");
		topicTextField = new JTextFieldWithContextMenu("");
		chapterTextField = new JTextFieldWithContextMenu("");
		learnLevelTextField = new JTextFieldWithContextMenu("");
		relevanceTextField = new JTextFieldWithContextMenu("");
		descriptionTextArea = new JTextArea(4, 20);
		
		addComponents();
		
		if(!DeleteMultipleVocablesDialogue.listenersAdded) {
			addActionListeners();
			Helper.addEscapeListener(this);
			
			ObserveableFactory.getVocabularyLanguagesObserveable().registerListener(this);
			DeleteMultipleVocablesDialogue.listenersAdded = true;
		}
	}

	/**
	 * Adds the components to the contentPane of this dialog.
	 */
	private void addComponents() {
		this.setLayout(new MigLayout("wrap 2"));
		
		setTitle("Delete Vocable...");
		setLocationRelativeTo(null);
		
		warningTextArea = new JTextArea("Do you really want to delete this vocable from the dictionary?");
		warningTextArea.setColumns(30);
		warningTextArea.setLineWrap(true);
		warningTextArea.setWrapStyleWord(true);
		warningTextArea.setEditable(false);
		warningTextArea.setBackground(new Color(240,240,240));
		add(warningTextArea, "cell 0 0 2 1");
		
		add(firstLanguageLabel, "cell 0 1");
		firstLanguageTextField = new JTextFieldWithContextMenu();
		firstLanguageTextField.setColumns(20);
		firstLanguageTextField.setText(vocables.get(currentVocableNumber).getFirstLanguage());
		firstLanguageTextField.setEditable(false);
		firstLanguageTextField.setBackground(new Color(240,240,240));
		add(firstLanguageTextField, "cell 1 1");
		
		add(phoneticScriptLabel, "cell 0 2");
		phoneticScriptTextField = new JTextFieldWithContextMenu();
		phoneticScriptTextField.setColumns(20);
		phoneticScriptTextField.setText(vocables.get(currentVocableNumber).getPhoneticScript());
		phoneticScriptTextField.setEditable(false);
		phoneticScriptTextField.setBackground(new Color(240,240,240));
		add(phoneticScriptTextField, "cell 1 2");
		
		add(secondLanguageLabel, "cell 0 3");
		secondLanguageTextField = new JTextFieldWithContextMenu();
		secondLanguageTextField.setColumns(20);
		secondLanguageTextField.setText(vocables.get(currentVocableNumber).getSecondLanguage());
		secondLanguageTextField.setEditable(false);
		secondLanguageTextField.setBackground(new Color(240,240,240));
		add(secondLanguageTextField, "cell 1 3");
		
		add(topicLabel, "cell 0 4");
		topicTextField = new JTextFieldWithContextMenu();
		topicTextField.setColumns(20);
		topicTextField.setText(vocables.get(currentVocableNumber).getTopic());
		topicTextField.setEditable(false);
		topicTextField.setBackground(new Color(240,240,240));
		add(topicTextField, "cell 1 4");
		
		add(chapterLabel, "cell 0 5");
		chapterTextField = new JTextFieldWithContextMenu();
		chapterTextField.setColumns(20);
		chapterTextField.setText(vocables.get(currentVocableNumber).getChapter());
		chapterTextField.setEditable(false);
		chapterTextField.setBackground(new Color(240,240,240));
		add(chapterTextField, "cell 1 5");
		
		add(learnLevelLabel, "cell 0 6");
		learnLevelTextField = new JTextFieldWithContextMenu();
		learnLevelTextField.setColumns(20);
		learnLevelTextField.setText(vocables.get(currentVocableNumber).getLearnLevel());
		learnLevelTextField.setEditable(false);
		learnLevelTextField.setBackground(new Color(240,240,240));
		add(learnLevelTextField, "cell 1 6");
		
		add(relevanceLabel, "cell 0 7");
		relevanceTextField = new JTextFieldWithContextMenu();
		relevanceTextField.setColumns(20);
		relevanceTextField.setText(vocables.get(currentVocableNumber).getRelevance());
		relevanceTextField.setEditable(false);
		relevanceTextField.setBackground(new Color(240,240,240));
		add(relevanceTextField, "cell 1 7");
		
		add(descriptionLabel, "cell 0 8");
		descriptionTextArea.setText(vocables.get(currentVocableNumber).getDescription());
		descriptionTextArea.setEditable(false);
		descriptionTextArea.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 12));
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setWrapStyleWord(true);
		descriptionTextArea.setBackground(new Color(240,240,240));
		descriptionScrollPane = new JScrollPane(descriptionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(descriptionScrollPane, "cell 1 8");
		
		add(buttonPanel, "cell 0 9 2 1");
		buttonPanel.add(yesButton);
		buttonPanel.add(noButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(yesAllButton);
		buttonPanel.add(noAllButton);
		
		this.getRootPane().setDefaultButton(cancelButton);
	}
	
	private void addActionListeners() {
		
		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				yesButtonAction();
			}
		});
		
		yesAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				yesAllButtonAction();
			}
		});
		
		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noButtonAction();
			}
		});
		
		noAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noAllButtonAction();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelButtonAction();
			}
		});
	}
	
	private void yesButtonAction() {
		//Dictionary.deleteVocable(vocables.get(currentVocableNumber));
		VocableManager.deleteVocable(vocables.get(currentVocableNumber));
		//Dictionary.vocabularyIsSavedToFile = false;
		showNextVocable();
	}
	
	private void yesAllButtonAction() {
		for(Vocable voc : vocables) {
			//Dictionary.deleteVocable(voc);
			VocableManager.deleteVocable(voc);
		}
		//Dictionary.vocabularyIsSavedToFile = false;
		dispose();
	}
	
	private void noButtonAction() {
		showNextVocable();
	}
	
	private void noAllButtonAction() {
		dispose();
	}
	
	private void cancelButtonAction() {
		dispose();
	}
	
	
	
	private void showNextVocable() {
		currentVocableNumber++;
		if(currentVocableNumber < vocables.size()) {
			firstLanguageTextField.setText(vocables.get(currentVocableNumber).getFirstLanguage());
			phoneticScriptTextField.setText(vocables.get(currentVocableNumber).getPhoneticScript());
			secondLanguageTextField.setText(vocables.get(currentVocableNumber).getSecondLanguage());
			topicTextField.setText(vocables.get(currentVocableNumber).getTopic());
			chapterTextField.setText(vocables.get(currentVocableNumber).getChapter());
			learnLevelTextField.setText(vocables.get(currentVocableNumber).getLearnLevel());
			relevanceTextField.setText(vocables.get(currentVocableNumber).getRelevance());
			descriptionTextArea.setText(vocables.get(currentVocableNumber).getDescription());
		} else {
			dispose();
		}
	}

	@Override
	public void changeVocabularyLanguages(String newFirstLanguage, String newPhoneticScript, String newSecondLanguage) {
		firstLanguageLabel.setText(newFirstLanguage);
		phoneticScriptLabel.setText(newPhoneticScript);
		secondLanguageLabel.setText(newSecondLanguage);
	}
}