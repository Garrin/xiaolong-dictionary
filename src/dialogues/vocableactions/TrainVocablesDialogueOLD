
package dialogues.vocableactions;

import factories.ObserveableFactory;
import gui.JTextFieldWithContextMenu;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
public class TrainVocablesDialogueOLD extends JFrame implements VocabularyLanguagesChangeListener {

	ArrayList<Vocable> trainingVocables = new ArrayList<Vocable>();
	
	private JLabel firstLanguageLabel;
	private JLabel phoneticScriptLabel;
	private JLabel secondLanguageLabel;
	private JLabel newLevelLabel = new JLabel("New Level");
		
	private JTextFieldWithContextMenu firstLanguageTextField;
	private JTextFieldWithContextMenu phoneticScriptTextField;
	private JTextFieldWithContextMenu secondLanguageTextField;
	private JTextFieldWithContextMenu newLearnLevelTextField;
	
	private JPanel showButtonsPanel = new JPanel(new MigLayout("wrap 2"));
	private JButton showTranslationButton = new JButton("Show translation");
	private JButton showPhoneticScriptButton;
	
	private JPanel buttonsPanel = new JPanel(new MigLayout("wrap 4"));
	private JButton stopTrainingButton = new JButton();
	private JButton restartButton = new JButton();
	private JButton previousButton = new JButton();
	private JButton nextButton = new JButton();
	
	private static int numberOfCurrentVocable = 0;
	private static int numberOfVocables;
	private JLabel vocableCounter = new JLabel();
	
	private boolean translationShown = false;
	private boolean phoneticScriptShown = Settings.trainingOptions_phoneticScript_shown;
	
	private static boolean alreadyInitializedOnce = false;
	
	private static TrainVocablesDialogueOLD instance = null;
	
	
	public static TrainVocablesDialogueOLD getInstance() {
		if(TrainVocablesDialogueOLD.instance == null) {
			TrainVocablesDialogueOLD.instance = new TrainVocablesDialogueOLD();
		}
		instance.initialize();
		return TrainVocablesDialogueOLD.instance;
	}
	
	public void initialize() {
		trainingVocables = VocableManager.getLastSearchResult();
		numberOfVocables = trainingVocables.size();
		numberOfCurrentVocable = 0;
		translationShown = false;
		phoneticScriptShown = Settings.trainingOptions_phoneticScript_shown;
		
		//Only add components, listeners and register listeners when the frame is initialized for the first time
		if(!alreadyInitializedOnce) {
			setFrameLayout();
			setTitle("Train vocables...");
			addComponents();
			addActionListeners();
			registerListeners();
		}
		
		updateVocableInfo();
		vocableCounter.setText("Vocable: " + (numberOfCurrentVocable+1) + " of " + numberOfVocables);
		this.getRootPane().setDefaultButton(nextButton);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		alreadyInitializedOnce = true;
	}
	
	private void setFrameLayout() {
		this.setLayout(new MigLayout("wrap 2"));
	}
	
	private void addComponents() {
		firstLanguageLabel = new JLabel(Settings.languageOptions_firstLanguageName);
		phoneticScriptLabel = new JLabel(Settings.languageOptions_phoneticScriptName);
		secondLanguageLabel = new JLabel(Settings.languageOptions_secondLanguageName);
		
		firstLanguageTextField = new JTextFieldWithContextMenu(20);
		phoneticScriptTextField = new JTextFieldWithContextMenu(20);
		secondLanguageTextField = new JTextFieldWithContextMenu(20);
		newLearnLevelTextField = new JTextFieldWithContextMenu(20);
		
		showPhoneticScriptButton = new JButton("Show " + Settings.languageOptions_phoneticScriptName);
		
		vocableCounter = new JLabel();
				
		add(firstLanguageLabel, "cell 0 0");
		add(phoneticScriptLabel, "cell 0 1");
		add(secondLanguageLabel, "cell 0 3");
		
		firstLanguageTextField.setEditable(false);
		firstLanguageTextField.setFont(new Font(Settings.trainingOptions_font, Font.PLAIN, 20));
		add(firstLanguageTextField, "cell 1 0");
		
		phoneticScriptTextField.setEditable(false);
		phoneticScriptTextField.setFont(new Font(Settings.trainingOptions_font, Font.PLAIN, 20));
		add(phoneticScriptTextField, "cell 1 1");
		
		secondLanguageTextField.setEditable(false);
		secondLanguageTextField.setFont(new Font(Settings.trainingOptions_font, Font.PLAIN, 20));
		add(secondLanguageTextField, "cell 1 3");
		
		System.out.println("adding the show button");
		add(showButtonsPanel, "cell 0 2 2 1");
		
		if(!Settings.trainingOptions_phoneticScript_shown) {
			showButtonsPanel.add(showPhoneticScriptButton, "cell 1 0");
		}
		
		newLearnLevelTextField.setText(trainingVocables.get(numberOfCurrentVocable).getLearnLevel());
		newLearnLevelTextField.setEditable(false);
		add(newLevelLabel, "cell 0 4");
		add(newLearnLevelTextField, "cell 1 4");
		
		
		
		add(buttonsPanel, "cell 0 5 2 1");
		
		restartButton.setText("Restart");
		stopTrainingButton.setText("Stop training");
		previousButton.setFont(new Font("Monospaced", Font.PLAIN, 14));
		previousButton.setText("<--");
		nextButton.setFont(new Font("Monospaced", Font.PLAIN, 14));
		nextButton.setText("-->");
		
		buttonsPanel.add(restartButton);
		buttonsPanel.add(previousButton);
		buttonsPanel.add(nextButton);
		buttonsPanel.add(stopTrainingButton);
		
		add(vocableCounter, "cell 0 6 2 1");
	}
	
	private void registerListeners() {
		ObserveableFactory.getVocabularyLanguagesObserveable().registerListener(this);
	}
	
	private void addActionListeners() {
		Helper.addEscapeListener(this);
		
		showTranslationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showTranslationButtonAction();
			}
		});
		
		showPhoneticScriptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!phoneticScriptShown) showPhoneticScriptButtonAction();
				else hidePhoneticScriptButtonAction();
			}
		});
		
		previousButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				previousButtonAction();
			}
		});
		
		stopTrainingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopTrainingButtonAction();
			}
		});
		
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(translationShown) nextButtonAction();
				else showTranslationButtonAction();
			}
		});
		
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				restartButtonActionPerformed();
			}
		});
	}
	
	private void showTranslationButtonAction() {
		if(!Settings.trainingOptions_firstToSecond) {
			secondLanguageTextField.setText(trainingVocables.get(numberOfCurrentVocable).getFirstLanguage());
		} else {
			secondLanguageTextField.setText(trainingVocables.get(numberOfCurrentVocable).getSecondLanguage());
		}
		newLearnLevelTextField.setEditable(true);
		newLearnLevelTextField.requestFocusInWindow();
		newLearnLevelTextField.select(0, newLearnLevelTextField.getText().length());
		translationShown = true;
	}
	
	private void showPhoneticScriptButtonAction() {
		togglePhoneticScript();
		
		newLearnLevelTextField.setSelectionStart(0);
		if(translationShown) {	
			newLearnLevelTextField.requestFocusInWindow();
			newLearnLevelTextField.setSelectionEnd(newLearnLevelTextField.getText().length());
		} else {
			newLearnLevelTextField.setSelectionEnd(0);
		}
	}
	
	private void hidePhoneticScriptButtonAction() {
		togglePhoneticScript();
		
		newLearnLevelTextField.setSelectionStart(0);
		if(translationShown) {
			newLearnLevelTextField.requestFocusInWindow();
			newLearnLevelTextField.setSelectionEnd(newLearnLevelTextField.getText().length());
		} else {
			newLearnLevelTextField.setSelectionEnd(0);
		}
	}
	
	private void stopTrainingButtonAction() {
		this.dispose();
	}
	
	private void updateVocableCounter(boolean nextClicked) {
		if(nextClicked) {
			numberOfCurrentVocable = (numberOfCurrentVocable + 1) % trainingVocables.size();
			vocableCounter.setText("Vocable: " + (numberOfCurrentVocable+1) + " of " + numberOfVocables);
		} else if(numberOfCurrentVocable != 0) { //previous clicked and not first vocable
			numberOfCurrentVocable = (numberOfCurrentVocable - 1) % trainingVocables.size();
			vocableCounter.setText("Vocable: " + (numberOfCurrentVocable + 1) + " of " + numberOfVocables);
		}
	}
	
	private void previousButtonAction() {
		updateVocableCounter(false);
		updateVocableInfo();
		newLearnLevelTextField.setEditable(false);
		translationShown = false;
	}
	
	private void nextButtonAction() {
		if(!newLearnLevelTextField.getText().contains(":")) {
			VocableManager.changeVocable(
					trainingVocables.get(numberOfCurrentVocable), 
					trainingVocables.get(numberOfCurrentVocable).getFirstLanguage(),
					trainingVocables.get(numberOfCurrentVocable).getPhoneticScript(), 
					trainingVocables.get(numberOfCurrentVocable).getSecondLanguage(), 
					trainingVocables.get(numberOfCurrentVocable).getTopic(), 
					trainingVocables.get(numberOfCurrentVocable).getChapter(),
					newLearnLevelTextField.getText());
			//trainingVocables.get(numberOfCurrentVocable).setLearnLevel(newLearnLevelTextField.getText());
			//Dictionary.vocabularyIsSavedToFile = false;
			//DictionaryMainWindow.updateStatusLabel("Vocabulary not saved (vocable trained)");
			//ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(trainingVocables.get(numberOfCurrentVocable));
			
			updateVocableCounter(true);
			updateVocableInfo();
			newLearnLevelTextField.setEditable(false);
			translationShown = false;
		} else {
			JOptionPane.showMessageDialog(this, "Colons (:) are used to seperate the parts of a vocable. You cannot use them in parts of a vocable.", "Usage of Colons", JOptionPane.OK_OPTION);
		}
	}
	
	private void restartButtonActionPerformed() {
		numberOfCurrentVocable = 0;
		vocableCounter.setText("Vocable: " + (numberOfCurrentVocable+1) + " of " + numberOfVocables);
		updateVocableInfo();
		newLearnLevelTextField.setEditable(false);
		translationShown = false;
	}
	
	private void updateVocableInfo() {
		if(!Settings.trainingOptions_firstToSecond) {
			firstLanguageLabel.setText(Settings.languageOptions_secondLanguageName);
			secondLanguageLabel.setText(Settings.languageOptions_firstLanguageName);
			firstLanguageTextField.setText(trainingVocables.get(numberOfCurrentVocable).getSecondLanguage());
			secondLanguageTextField.setText("");
			newLearnLevelTextField.setText(trainingVocables.get(numberOfCurrentVocable).getLearnLevel());
		} else {
			firstLanguageLabel.setText(Settings.languageOptions_firstLanguageName);
			secondLanguageLabel.setText(Settings.languageOptions_secondLanguageName);
			firstLanguageTextField.setText(trainingVocables.get(numberOfCurrentVocable).getFirstLanguage());
			secondLanguageTextField.setText("");
			newLearnLevelTextField.setText(trainingVocables.get(numberOfCurrentVocable).getLearnLevel());
		}
		
		if(Settings.trainingOptions_phoneticScript_shown) {
			phoneticScriptTextField.setText(trainingVocables.get(numberOfCurrentVocable).getPhoneticScript());
		} else {
			phoneticScriptTextField.setText(Settings.languageOptions_phoneticScriptName + " in settings disabled");
		}
		
		showPhoneticScriptButton.setText("Show " + Settings.languageOptions_phoneticScriptName);
		phoneticScriptShown = false;
		
		firstLanguageTextField.setCaretPosition(0);
		phoneticScriptTextField.setCaretPosition(0);
		secondLanguageTextField.setCaretPosition(0);
		newLearnLevelTextField.setCaretPosition(0);
	}
	
	private void togglePhoneticScript() {
		if(phoneticScriptShown) {
			phoneticScriptTextField.setText(Settings.languageOptions_phoneticScriptName + " hidden");
			phoneticScriptShown = false;
			showPhoneticScriptButton.setText("Show " + Settings.languageOptions_phoneticScriptName);
		} else {
			phoneticScriptTextField.setText(trainingVocables.get(numberOfCurrentVocable).getPhoneticScript());
			phoneticScriptShown = true;
			showPhoneticScriptButton.setText("Hide " + Settings.languageOptions_phoneticScriptName);
		}
	}
	
//	public void updateLanguages(String newFirstLanguage, String newPhoneticScript, String newSecondLanguage) {
//		firstLanguageLabel.setText(newFirstLanguage);
//		phoneticScriptLabel.setText(newPhoneticScript);
//		secondLanguageLabel.setText(newSecondLanguage);
//	}
	
	public void resetMembers() {
		trainingVocables = VocableManager.getLastSearchResult();
		numberOfCurrentVocable = 0;
		numberOfVocables = trainingVocables.size();
		vocableCounter.setText("Vocable number: " + numberOfCurrentVocable);
	}
	
	public void updateFont() {
		firstLanguageTextField.setFont(new Font(Settings.trainingOptions_font, Font.PLAIN, 20));
		phoneticScriptTextField.setFont(new Font(Settings.trainingOptions_font, Font.PLAIN, 20));
		secondLanguageTextField.setFont(new Font(Settings.trainingOptions_font, Font.PLAIN, 20));
	}

	@Override
	public void changeVocabularyLanguages(String newFirstLanguage, String newPhoneticScript, String newSecondLanguage) {
		firstLanguageLabel.setText(newFirstLanguage);
		phoneticScriptLabel.setText(newPhoneticScript);
		secondLanguageLabel.setText(newSecondLanguage);
	}
}