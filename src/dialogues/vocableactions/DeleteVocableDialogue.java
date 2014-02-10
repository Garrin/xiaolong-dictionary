
package dialogues.vocableactions;

import factories.FrameFactory;
import factories.ObserveableFactory;
import gui.DictionaryMainWindow;
import gui.JTextFieldWithContextMenu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
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
public class DeleteVocableDialogue extends JDialog implements VocabularyLanguagesChangeListener {

	private static DeleteVocableDialogue instance = null;
	private static boolean listenersAdded = false;
	
	private Vocable vocable;
	
	private JTextArea warningTextArea;
	private JLabel firstLanguageLabel = new JLabel(Settings.languageOptions_firstLanguageName);
	private JLabel phoneticScriptLabel = new JLabel(Settings.languageOptions_phoneticScriptName);
	private JLabel secondLanguageLabel = new JLabel(Settings.languageOptions_secondLanguageName);
	private JLabel topicLabel = new JLabel("Topic");
	private JLabel chapterLabel = new JLabel("Chapter");
	private JLabel levelLabel = new JLabel("Level");

	private JTextFieldWithContextMenu firstLanguageTextField = new JTextFieldWithContextMenu("");
	private JTextFieldWithContextMenu phoneticScriptTextField = new JTextFieldWithContextMenu("");
	private JTextFieldWithContextMenu secondLanguageTextField = new JTextFieldWithContextMenu("");
	private JTextFieldWithContextMenu topicTextField = new JTextFieldWithContextMenu("");
	private JTextFieldWithContextMenu chapterTextField = new JTextFieldWithContextMenu("");
	private JTextFieldWithContextMenu levelTextField = new JTextFieldWithContextMenu("");
	
	private JButton deleteVocableButton = new JButton("Delete vocable");
	private JButton cancelButton = new JButton("Cancel");
	
	private DeleteVocableDialogue(DictionaryMainWindow parent, boolean modal) {
		super(parent, modal);
	}
	
	public static DeleteVocableDialogue getInstance() {
		if(DeleteVocableDialogue.instance == null) {
			DeleteVocableDialogue.instance = new DeleteVocableDialogue(FrameFactory.getDictionaryMainWindow(), true);
		}
		return DeleteVocableDialogue.instance;
	}
	
	public void initialize(Vocable vocable) {
		
		this.vocable = vocable;
		getContentPane().removeAll();
		addComponents();
		
		if(!DeleteVocableDialogue.listenersAdded) {
			addActionListeners();
			Helper.addEscapeListener(this);
			
			ObserveableFactory.getVocabularyLanguagesObserveable().registerListener(this);
			DeleteVocableDialogue.listenersAdded = true;
		}
	}
	
	private void addComponents() {
		this.setLayout(new MigLayout("wrap 2"));
		
		setTitle("Delete Vocable...");
		setLocationRelativeTo(null);
		
		warningTextArea = new JTextArea("Do you really want to delete the vocable from the dictionary?");
		warningTextArea.setColumns(30);
		warningTextArea.setLineWrap(true);
		warningTextArea.setWrapStyleWord(true);
		warningTextArea.setEditable(false);
		warningTextArea.setBackground(new Color(238, 238, 238));
		add(warningTextArea, "cell 0 0 2 1");
		
		add(firstLanguageLabel, "cell 0 1");
		firstLanguageTextField = new JTextFieldWithContextMenu();
		firstLanguageTextField.setColumns(20);
		firstLanguageTextField.setText(vocable.getFirstLanguage());
		firstLanguageTextField.setEditable(false);
		add(firstLanguageTextField, "cell 1 1");
		
		add(phoneticScriptLabel, "cell 0 2");
		phoneticScriptTextField = new JTextFieldWithContextMenu();
		phoneticScriptTextField.setColumns(20);
		phoneticScriptTextField.setText(vocable.getPhoneticScript());
		phoneticScriptTextField.setEditable(false);
		add(phoneticScriptTextField, "cell 1 2");
		
		add(secondLanguageLabel, "cell 0 3");
		secondLanguageTextField = new JTextFieldWithContextMenu();
		secondLanguageTextField.setColumns(20);
		secondLanguageTextField.setText(vocable.getSecondLanguage());
		secondLanguageTextField.setEditable(false);
		add(secondLanguageTextField, "cell 1 3");
		
		add(topicLabel, "cell 0 4");
		topicTextField = new JTextFieldWithContextMenu();
		topicTextField.setColumns(20);
		topicTextField.setText(vocable.getTopic());
		topicTextField.setEditable(false);
		add(topicTextField, "cell 1 4");
		
		add(chapterLabel, "cell 0 5");
		chapterTextField = new JTextFieldWithContextMenu();
		chapterTextField.setColumns(20);
		chapterTextField.setText(vocable.getChapter());
		chapterTextField.setEditable(false);
		add(chapterTextField, "cell 1 5");
		
		add(levelLabel, "cell 0 6");
		levelTextField = new JTextFieldWithContextMenu();
		levelTextField.setColumns(20);
		levelTextField.setText(vocable.getLearnLevel());
		levelTextField.setEditable(false);
		add(levelTextField, "cell 1 6");
		
		add(deleteVocableButton, "cell 0 7");
		add(cancelButton, "cell 1 7");
		this.getRootPane().setDefaultButton(cancelButton);
	}
	
	private void addActionListeners() {
		
		deleteVocableButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteVocableButtonAction();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelButtonAction();
			}
		});
	}
	
	private void deleteVocableButtonAction() {
		//Dictionary.deleteVocable(vocable);
		VocableManager.deleteVocable(vocable);
		//Dictionary.vocabularyIsSavedToFile = false;
		this.dispose();
	}
	
	private void cancelButtonAction() {
		this.dispose();
	}

	@Override
	public void changeVocabularyLanguages(String newFirstLanguage, String newPhoneticScript, String newSecondLanguage) {
		firstLanguageLabel.setText(newFirstLanguage);
		phoneticScriptLabel.setText(newPhoneticScript);
		secondLanguageLabel.setText(newSecondLanguage);
	}
}