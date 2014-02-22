
package dialogues.vocableactions;

import gui.BigCharacterBox;
import gui.JTextFieldWithContextMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import manager.VocableManager;
import net.miginfocom.swing.MigLayout;
import dictionary.Settings;
import dictionary.Vocable;

@SuppressWarnings("serial")
public class TrainVocablesDialogue extends JFrame {
	
	private JLabel headingLabel;
	private JLabel firstLanguageLabel;
	private JTextFieldWithContextMenu firstLanguageTextField;
	private JLabel phoneticScriptLabel;
	private JTextFieldWithContextMenu phoneticScriptLanguageTextField;
	private JLabel secondLanguageLabel;
	private JTextFieldWithContextMenu secondLanguageTextField;
	private JLabel learnLevelLabel;
	private JTextFieldWithContextMenu learnLevelLanguageTextField;
	
	private JButton showPhoneticScriptButton;
	private JButton showTranslationButton;
	private JButton nextVocableButton;
	private JButton previousVocableButton;
	private JButton restartTrainingButton;
	private JButton stopTrainingButton;
	
	private JLabel statusLabel;
	
	private JPanel buttonPanel;
	private JPanel vocableTrainingPanel;
	
	private BigCharacterBox bigCharacterBox = new BigCharacterBox(Settings.bigCharacterBoxOptions_ignored_characters);
	private JPanel bigCharacterBoxPanel;
	
	private ArrayList<Vocable> trainingVocables = new ArrayList<Vocable>();
	private int currentPositionInTrainingVocables = 0;
	private boolean phoneticScriptShown = Settings.trainingOptions_phoneticScript_shown;
	private boolean translationShown = false;
	
	public TrainVocablesDialogue(ArrayList<Vocable> trainingVocables) {
		this.trainingVocables = trainingVocables;
		setTitle("Vocable Training");
		initializeComponents();
		addComponents();
		addActionListeners();
		startTraining();
	}
	
	/**
	 * Initializes all components
	 */
	private void initializeComponents() {
		this.setLayout(new MigLayout("wrap 2"));
		this.setResizable(false);
		
		buttonPanel = new JPanel();
		GridLayout gridLayout = new GridLayout(3,2);
		gridLayout.setVgap(5);
		buttonPanel.setLayout(gridLayout);
		
		
		vocableTrainingPanel = new JPanel(new MigLayout("wrap 2"));
		
		initializeHeadingComponent();
		initializeFirstLanguageComponents();
		initializePhoneticScriptComponents();
		initializeSecondLanguageComponents();
		initializeLearnLevelComponents();
		initializeButtonComponents();
		initializeStatusLabel();
	}
	
	/**
	 * initializes the heading component
	 */
	private void initializeHeadingComponent() {
		headingLabel = new JLabel("Vocable Training");
		headingLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
	}
	
	/**
	 * initializes the first language components
	 */
	private void initializeFirstLanguageComponents() {
		if(Settings.trainingOptions_firstToSecond) {
			firstLanguageLabel = new JLabel(Settings.languageOptions_firstLanguageName);
		} else {
			firstLanguageLabel = new JLabel(Settings.languageOptions_secondLanguageName);
		}
		
		firstLanguageTextField = new JTextFieldWithContextMenu(20);
		firstLanguageTextField.setEditable(false);
		firstLanguageTextField.setBackground(new Color(240,240,240));
		firstLanguageTextField.setFont(new Font(Settings.trainingOptions_font, Font.PLAIN, 16));
	}
	
	/**
	 * initializes the phonetic script components
	 */
	private void initializePhoneticScriptComponents() {
		phoneticScriptLabel = new JLabel(Settings.languageOptions_phoneticScriptName);
		phoneticScriptLanguageTextField= new JTextFieldWithContextMenu(20);
		phoneticScriptLanguageTextField.setEditable(false);
		phoneticScriptLanguageTextField.setBackground(new Color(240,240,240));
		phoneticScriptLanguageTextField.setFont(new Font(Settings.trainingOptions_font, Font.PLAIN, 16));
	}
	
	/**
	 * initializes the second language components
	 */
	private void initializeSecondLanguageComponents() {
		if(Settings.trainingOptions_firstToSecond) {
			secondLanguageLabel = new JLabel(Settings.languageOptions_secondLanguageName);
		} else {
			secondLanguageLabel = new JLabel(Settings.languageOptions_firstLanguageName);
		}
		
		secondLanguageTextField = new JTextFieldWithContextMenu(20);
		secondLanguageTextField.setEditable(false);
		secondLanguageTextField.setBackground(new Color(240,240,240));
		secondLanguageTextField.setFont(new Font(Settings.trainingOptions_font, Font.PLAIN, 20));
	}
	
	/**
	 * initializes the learn level components
	 */
	private void initializeLearnLevelComponents() {
		learnLevelLabel = new JLabel("Learn Level");
		learnLevelLanguageTextField = new JTextFieldWithContextMenu(20);
		//learnLevelLanguageTextField.setEditable(true);
		//learnLevelLanguageTextField.setBackground(new Color(240,240,240));
		learnLevelLanguageTextField.setFont(new Font(Settings.trainingOptions_font, Font.PLAIN, 16));
	}
	
	/**
	 * initializes the button components
	 */
	private void initializeButtonComponents() {
		if(Settings.trainingOptions_phoneticScript_shown) {
			showPhoneticScriptButton = new JButton("Hide " + Settings.languageOptions_phoneticScriptName);
		} else {
			showPhoneticScriptButton = new JButton("Show " + Settings.languageOptions_phoneticScriptName);
		}
		
		if(Settings.trainingOptions_firstToSecond) {
			showTranslationButton = new JButton("Show " + Settings.languageOptions_secondLanguageName);
		} else {
			showTranslationButton = new JButton("Show " + Settings.languageOptions_firstLanguageName);
		}
		nextVocableButton = new JButton(">>");
		previousVocableButton = new JButton("<<");
		restartTrainingButton = new JButton("Restart");
		stopTrainingButton = new JButton("Stop");
	}
	
	/**
	 * initializes status labels
	 */
	private void initializeStatusLabel() {
		statusLabel = new JLabel("Vocable " + (currentPositionInTrainingVocables+1) + " of " + trainingVocables.size());
	}
	
	/**
	 * adds gui components to this dialogue
	 */
	private void addComponents() {
		add(headingLabel, "cell 0 0 2 1");
		
		vocableTrainingPanel.add(firstLanguageLabel);
		vocableTrainingPanel.add(firstLanguageTextField);
		
		vocableTrainingPanel.add(phoneticScriptLabel);
		vocableTrainingPanel.add(phoneticScriptLanguageTextField);
		
		vocableTrainingPanel.add(secondLanguageLabel);
		vocableTrainingPanel.add(secondLanguageTextField);
		
		vocableTrainingPanel.add(learnLevelLabel);
		vocableTrainingPanel.add(learnLevelLanguageTextField);
		
		addBorder(vocableTrainingPanel, null);
		
		add(vocableTrainingPanel, "cell 0 1 1 1");
		
		
		
		buttonPanel.add(showPhoneticScriptButton);
		buttonPanel.add(showTranslationButton);
		
		buttonPanel.add(previousVocableButton);
		buttonPanel.add(nextVocableButton);
		
		buttonPanel.add(restartTrainingButton);
		buttonPanel.add(stopTrainingButton);
		
		//addBorder(buttonPanel, null);
		
		add(buttonPanel, "cell 0 2 2 1");
		
		
		
		bigCharacterBoxPanel = new JPanel();
		
		bigCharacterBoxPanel.add(bigCharacterBox);
		
		add(bigCharacterBoxPanel, "cell 1 1 1 1");
		
		add(statusLabel);
	}
	
	/**
	 * Adds a titled border to a component
	 * @param component the component which will the border be added to
	 * @param title the title of the border
	 */
	private void addBorder(JComponent component, String title) {
		TitledBorder border;
		border = BorderFactory.createTitledBorder(title);
		border.setTitleJustification(TitledBorder.CENTER);
		((JComponent) component).setBorder(border);
	}
	
	
	/**
	 * Adds action listeners to buttons in this dialogue
	 */
	private void addActionListeners() {
		nextVocableButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextVocable();
			}
		});
		
		previousVocableButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				previousVocable();
			}
		});
		
		showPhoneticScriptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				togglePhoneticScript();
			}
		});
		
		showTranslationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleTranslation();
			}
		});
		
		restartTrainingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				restartTraining();
			}
		});
		
		stopTrainingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopTraining();
			}
		});
	}
	
	/**
	 * switches to the next vocable of the training vocables
	 */
	private void nextVocable() {
		saveVocableLearnLevel();
		
		currentPositionInTrainingVocables = (currentPositionInTrainingVocables + 1) % trainingVocables.size();
		setVocable(trainingVocables.get(currentPositionInTrainingVocables));
		
		bigCharacterBox.setCharacters("");
		secondLanguageTextField.setText(Settings.languageOptions_secondLanguageName + " hidden");
		
		if(Settings.trainingOptions_firstToSecond) {
			showTranslationButton.setText("Show " + Settings.languageOptions_secondLanguageName);
		} else {
			showTranslationButton.setText("Show " + Settings.languageOptions_firstLanguageName);
		}
		
		translationShown = false;
	}
	
	/**
	 * Switches to the previous vocable of the training vocables
	 */
	private void previousVocable() {
		saveVocableLearnLevel();
		
		currentPositionInTrainingVocables = (currentPositionInTrainingVocables - 1);
		if(currentPositionInTrainingVocables < 0) {
			currentPositionInTrainingVocables = trainingVocables.size()-1;
		}
		
		setVocable(trainingVocables.get(currentPositionInTrainingVocables));
		
		//reset big character box
		bigCharacterBox.setCharacters("");
		
		//always hide translation when a new vocable is set
		secondLanguageTextField.setText(Settings.languageOptions_secondLanguageName + " hidden");
		
		if(Settings.trainingOptions_firstToSecond) {
			showTranslationButton.setText("Show " + Settings.languageOptions_secondLanguageName);
		} else {
			showTranslationButton.setText("Show " + Settings.languageOptions_firstLanguageName);
		}
		
		translationShown = false;
	}
	
	/**
	 * Resets the training progress.
	 */
	private void restartTraining() {
		saveVocableLearnLevel();
		currentPositionInTrainingVocables = 0;
		setVocable(trainingVocables.get(currentPositionInTrainingVocables));
	}
	
	private void startTraining() {
		currentPositionInTrainingVocables = 0;
		setVocable(trainingVocables.get(currentPositionInTrainingVocables));
	}
	
	/**
	 * closes the training dialogue
	 */
	private void stopTraining() {
		saveVocableLearnLevel();
		this.dispose();
	}
	
	private void saveVocableLearnLevel() {
		//Save change to the learn level of a vocable if one exists
		if(!learnLevelLanguageTextField.getText().equals(trainingVocables.get(currentPositionInTrainingVocables).getLearnLevel())) {
			VocableManager.changeVocableLearnLevel(trainingVocables.get(currentPositionInTrainingVocables), learnLevelLanguageTextField.getText());
		}
	}
	
	/**
	 * Toggles phonetic script on or off
	 */
	private void togglePhoneticScript() {
		if(phoneticScriptShown) {
			phoneticScriptLanguageTextField.setText(Settings.languageOptions_phoneticScriptName + " hidden");
			showPhoneticScriptButton.setText("Show " + Settings.languageOptions_phoneticScriptName);
			phoneticScriptShown = false;
		} else {
			phoneticScriptLanguageTextField.setText(trainingVocables.get(currentPositionInTrainingVocables).getPhoneticScript());
			showPhoneticScriptButton.setText("Hide " + Settings.languageOptions_phoneticScriptName);
			phoneticScriptShown = true;
		}
	}
	
	/**
	 * toggles the displaying of the translation of the current vocable on or off
	 */
	private void toggleTranslation() {
		if(translationShown) {
			if(Settings.trainingOptions_firstToSecond) {
				secondLanguageTextField.setText(Settings.languageOptions_secondLanguageName + " hidden");
				bigCharacterBox.setCharacters(trainingVocables.get(currentPositionInTrainingVocables).getSecondLanguage());
				bigCharacterBox.setCharacters("");
				showTranslationButton.setText("Show " + Settings.languageOptions_secondLanguageName);
				translationShown = false;
			} else {
				secondLanguageTextField.setText(Settings.languageOptions_firstLanguageName + " hidden");
				bigCharacterBox.setCharacters(trainingVocables.get(currentPositionInTrainingVocables).getFirstLanguage());
				bigCharacterBox.setCharacters("");
				showTranslationButton.setText("Show " + Settings.languageOptions_firstLanguageName);
				translationShown = false;
			}
			
		} else {
			if(Settings.trainingOptions_firstToSecond) {
				secondLanguageTextField.setText(trainingVocables.get(currentPositionInTrainingVocables).getSecondLanguage());
				bigCharacterBox.setCharacters(trainingVocables.get(currentPositionInTrainingVocables).getSecondLanguage());
				showTranslationButton.setText("Hide " + Settings.languageOptions_secondLanguageName);
				translationShown = true;
			} else {
				secondLanguageTextField.setText(trainingVocables.get(currentPositionInTrainingVocables).getFirstLanguage());
				bigCharacterBox.setCharacters(trainingVocables.get(currentPositionInTrainingVocables).getFirstLanguage());
				showTranslationButton.setText("Hide " + Settings.languageOptions_firstLanguageName);
				translationShown = true;
			}
		}
	}
	
	/**
	 * Sets a vocable for the vocable training dialogue. This vocables attributes will be shown.
	 * @param vocable the shown vocable
	 */
	private void setVocable(Vocable vocable) {
		System.out.println("Pinyin is: " + phoneticScriptShown);
		
		//Settings texts according to the translation direction setting
		if(Settings.trainingOptions_firstToSecond) {
			firstLanguageTextField.setText(vocable.getFirstLanguage());
			secondLanguageTextField.setText(Settings.languageOptions_secondLanguageName + " hidden");
		} else {
			firstLanguageTextField.setText(vocable.getSecondLanguage());
			secondLanguageTextField.setText(Settings.languageOptions_secondLanguageName + " hidden");
		}
		
		//showing or hiding the phonetic script depending on the show pinyin setting and labelling the show/hidePhoneticScript button accordingly 
		if(Settings.trainingOptions_phoneticScript_shown) {
			phoneticScriptLanguageTextField.setText(vocable.getPhoneticScript());
			showPhoneticScriptButton.setText("Hide " + Settings.languageOptions_phoneticScriptName);
			System.out.println(phoneticScriptShown + " --> " + true);
			phoneticScriptShown = true;
		} else {
			phoneticScriptLanguageTextField.setText(Settings.languageOptions_phoneticScriptName + " hidden");
			showPhoneticScriptButton.setText("Show " + Settings.languageOptions_phoneticScriptName);
			System.out.println(phoneticScriptShown + " --> " + false);
			phoneticScriptShown = false;
		}
		
		learnLevelLanguageTextField.setText(vocable.getLearnLevel());
		
		firstLanguageTextField.setCaretPosition(0);
		phoneticScriptLanguageTextField.setCaretPosition(0);
		secondLanguageTextField.setCaretPosition(0);
		learnLevelLanguageTextField.setCaretPosition(0);
		
		statusLabel.setText("Vocable " + (currentPositionInTrainingVocables+1) + " of " + trainingVocables.size());
	}
}
