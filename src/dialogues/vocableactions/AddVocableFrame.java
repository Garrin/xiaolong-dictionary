package dialogues.vocableactions;

import factories.ObserveableFactory;
import gui.JTextFieldWithContextMenu;
import gui.TonenPopupDialogue;

import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
 * Singleton
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class AddVocableFrame extends JFrame implements VocabularyLanguagesChangeListener {
	
	private static AddVocableFrame instance = null;
	
	private JPanel inputPanel = new JPanel(new MigLayout("wrap 2", "[right][left]"));
	
	private JLabel firstLanguageLabel = new JLabel(Settings.languageOptions_firstLanguageName);
	private JLabel phoneticScriptLabel = new JLabel(Settings.languageOptions_phoneticScriptName);
	private JLabel secondLanguageLabel = new JLabel(Settings.languageOptions_secondLanguageName);
	private JLabel topicLabel = new JLabel("Topic");
	private JLabel chapterLabel = new JLabel("Chapter");
	private JLabel learnLevelLabel = new JLabel("Learn level");
	private JLabel relevanceLabel = new JLabel("Relevance");
	private JLabel descriptionLabel = new JLabel("Description");
	
	private JTextFieldWithContextMenu firstLanguageTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu phoneticScriptTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu secondLanguageTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu topicTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu chapterTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu learnLevelTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu relevanceTextField = new JTextFieldWithContextMenu(20);
	private JTextArea descriptionTextArea;
	private JScrollPane descriptionScrollPane;
		
	private String focusedTextFieldName = "firstLanguage";
	
	private JPanel buttonPanel = new JPanel(new MigLayout("wrap 4"));
	private JButton addButton = new JButton("Add");
	private JButton cancelButton = new JButton("Cancel");
	private JButton clearButton = new JButton("Clear");
	private JButton specialCharacterButton;
	
	private static int addedVocablesCounter = 0;
	private static JLabel statusLabel = new JLabel();
	
	//private DictionaryMainWindow parent;
	
	private boolean tonenPopupDialogueDisplayed = false;

	
	private TonenPopupDialogue tonenPopupDialogue;
	
	private AddVocableFrame() {
		addComponents();
		addWindowListeners();
		addActionListeners();
		addFocusListeners();
		addMouseListeners();
		Helper.addEscapeListener(this);
		
		//Register as listener for vocabulary language name changes
		ObserveableFactory.getVocabularyLanguagesObserveable().registerListener(this);
	}
	
	public static AddVocableFrame getInstance() {
		if(instance == null) {
			instance = new AddVocableFrame();
		}
		return instance;
	}
	
	private void addComponents() {
		this.setLayout(new MigLayout("wrap 2"));
		
		add(inputPanel, "cell 0 0");
		inputPanel.add(firstLanguageLabel, "cell 0 0");
		inputPanel.add(phoneticScriptLabel, "cell 0 1");
		inputPanel.add(secondLanguageLabel, "cell 0 2");
		inputPanel.add(topicLabel, "cell 0 3");
		inputPanel.add(chapterLabel, "cell 0 4");
		inputPanel.add(learnLevelLabel, "cell 0 5");
		inputPanel.add(relevanceLabel, "cell 0 6");
		inputPanel.add(descriptionLabel, "cell 0 7");

		firstLanguageTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		inputPanel.add(firstLanguageTextField, "cell 1 0");
		phoneticScriptTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		inputPanel.add(phoneticScriptTextField, "cell 1 1");
		secondLanguageTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		inputPanel.add(secondLanguageTextField, "cell 1 2");
		topicTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		inputPanel.add(topicTextField, "cell 1 3");
		chapterTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		inputPanel.add(chapterTextField, "cell 1 4");
		learnLevelTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		inputPanel.add(learnLevelTextField, "cell 1 5");
		relevanceTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		inputPanel.add(relevanceTextField, "cell 1 6");
		
		descriptionTextArea = new JTextArea(4, 30);
		descriptionTextArea.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 14));
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setWrapStyleWord(true);
		
		descriptionScrollPane = new JScrollPane(descriptionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		inputPanel.add(descriptionScrollPane, "cell 1 7");
		
		add(buttonPanel, "cell 0 1");
		buttonPanel.add(addButton, "cell 0 0");
		buttonPanel.add(clearButton, "cell 1 0");
		buttonPanel.add(cancelButton, "cell 2 0");
		
		//SPECIAL CHAR BUTTON
		specialCharacterButton = new JButton();
		try {
			Image img = ImageIO.read(new File("src/resources/insertsymbol.png"));
			specialCharacterButton.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		specialCharacterButton.setContentAreaFilled(false);
		specialCharacterButton.setMargin(new Insets(4,4,4,4));
		buttonPanel.add(specialCharacterButton, "cell 3 0");
		
		updateStatusLabel();
		add(statusLabel, "cell 0 7 2 1");
		
		firstLanguageTextField.requestFocusInWindow();
	}
	
	private void addWindowListeners() {
		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}
			@Override
			public void windowIconified(WindowEvent e) {
			}
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			@Override
			public void windowClosing(WindowEvent e) {
				if(tonenPopupDialogueDisplayed) {
					tonenPopupDialogue.dispose();
					setTonenPopupDialogueDisplayed(false);
				}
				dispose();
			}
			@Override
			public void windowClosed(WindowEvent e) {
			}
			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
		
		addWindowListener(new WindowAdapter(){ 
			  public void windowOpened(WindowEvent e){ 
				  firstLanguageTextField.requestFocusInWindow();
			  }
		});
	}
	
	private void addActionListeners() {
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addButtonAction();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelButtonAction();
			}
		});
		
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clearAllTextFields();
			}
		});
	}
	
	private void addFocusListeners(){
		firstLanguageTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "firstLanguage";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
			}
        });
		
		phoneticScriptTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "phoneticScript";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
			}
        });
		
		secondLanguageTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "secondLanguage";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
			}
        });
		
		topicTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "topic";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
			}
        });
		
		chapterTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "chapter";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
			}
        });
		
		learnLevelTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "learnLevel";
			}
			@Override
			public void focusLost(FocusEvent arg0) {

			}
        });
		
		relevanceTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
			}
			@Override
			public void focusGained(FocusEvent e) {
				focusedTextFieldName = "relevance";
			}
		});
		
		descriptionTextArea.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {	
			}
			@Override
			public void focusGained(FocusEvent e) {
				focusedTextFieldName = "description";
			}
		});
	}

	private void addMouseListeners() {
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
	}
	
	// This button's action is simply to dispose of the JDialog.
	private void addButtonAction() {
		if(!isColonInInput()) {
			//No empty fields allowed
			if(isAnInputFieldIsEmpty()) {
				JOptionPane.showMessageDialog(this, "No field can be empty! You can fill empty fields with \"---\" for example.", "Empty fields", JOptionPane.OK_OPTION);
			} else {
				//If the vocable is already in the dictionary, it should not be added again
				if(isVocableAlreadyInDictionary(firstLanguageTextField.getText(), phoneticScriptTextField.getText(), secondLanguageTextField.getText())) {
					JOptionPane.showMessageDialog(this, "This vocable is already in your dictionary.", "Redundant vocable...", JOptionPane.OK_OPTION);
				} else {
					addedVocablesCounter++;
					updateStatusLabel();
					VocableManager.addVocable(new Vocable(
						topicTextField.getText(),
						chapterTextField.getText(),
						firstLanguageTextField.getText(),
						secondLanguageTextField.getText(),
						phoneticScriptTextField.getText(),
						learnLevelTextField.getText(),
						relevanceTextField.getText(),
						descriptionTextArea.getText()
					));
					resetTexts();
					firstLanguageTextField.requestFocusInWindow();
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "Colons (:) are used to seperate the parts of a vocable. You cannot use them in parts of a vocable.", "Usage of Colons", JOptionPane.OK_OPTION);
		}
	}
	
	private void cancelButtonAction() {
		if(tonenPopupDialogueDisplayed) {
			tonenPopupDialogue.dispose();
			setTonenPopupDialogueDisplayed(false);
		}
		this.dispose();
	}
	
	private void updateStatusLabel() {
		statusLabel.setText("Vocables added: " + addedVocablesCounter);
	}
	
	private void resetTexts() {
		firstLanguageTextField.setText("");
		phoneticScriptTextField.setText("");
		secondLanguageTextField.setText("");
		descriptionTextArea.setText("---");
	}
	
	/**
	 * This method inserts a special character into a textarea or textfield at the currently marked position.
	 * @param specialCharacter the special character which will be inserted
	 */
	public void addSpecialCharacterToTextField(String specialCharacter) {
		if(focusedTextFieldName.equals("firstLanguage")) {
			Helper.insertCharacterIntoTextfield(firstLanguageTextField, specialCharacter);
		} else if(focusedTextFieldName.equals("phoneticScript")) {
			Helper.insertCharacterIntoTextfield(phoneticScriptTextField, specialCharacter);
		} else if(focusedTextFieldName.equals("secondLanguage")) {
			Helper.insertCharacterIntoTextfield(secondLanguageTextField, specialCharacter);
		} else if(focusedTextFieldName.equals("topic")) {
			Helper.insertCharacterIntoTextfield(topicTextField, specialCharacter);
		} else if(focusedTextFieldName.equals("chapter")) {
			Helper.insertCharacterIntoTextfield(chapterTextField, specialCharacter);
		} else if(focusedTextFieldName.equals("learnLevel")) {
			Helper.insertCharacterIntoTextfield(learnLevelTextField, specialCharacter);
		} else if(focusedTextFieldName.equals("relevance")) {
			Helper.insertCharacterIntoTextfield(relevanceTextField, specialCharacter);
		} else if(focusedTextFieldName.equals("description")) {
			Helper.insertCharacterIntoTextArea(descriptionTextArea, specialCharacter);
		}
	}
	
	private void clearAllTextFields() {
		firstLanguageTextField.setText("");
		phoneticScriptTextField.setText("");
		secondLanguageTextField.setText("");
		topicTextField.setText("");
		chapterTextField.setText("");
		learnLevelTextField.setText("");
		relevanceTextField.setText("");
		descriptionTextArea.setText("");
		firstLanguageTextField.requestFocusInWindow();
	}
	
	private void displayTonenPopupDialogue() {
		tonenPopupDialogue = new TonenPopupDialogue(this, false, specialCharacterButton, "Add Vocable Dialog - Special Characters");
	}
	
	public boolean isTonenPopupDialogueDisplayed() {
		return tonenPopupDialogueDisplayed;
	}

	public void setTonenPopupDialogueDisplayed(boolean tonenPopupDialogueDisplayed) {
		this.tonenPopupDialogueDisplayed = tonenPopupDialogueDisplayed;
	}
	
	private static boolean isVocableAlreadyInDictionary(String firstLanguage, String phoneticScript, String secondLanguage) {		
		return VocableManager.isVocableAlreadyInVocableList(firstLanguage, phoneticScript, secondLanguage);
	}

	@Override
	public void changeVocabularyLanguages(String newFirstLanguage, String newPhoneticScript, String newSecondLanguage) {
		firstLanguageLabel.setText(newFirstLanguage);
		phoneticScriptLabel.setText(newPhoneticScript);
		secondLanguageLabel.setText(newSecondLanguage);
	}
	
	private boolean isColonInInput() {
		return (
			topicTextField.getText().contains(":") ||
			chapterTextField.getText().contains(":") ||
			firstLanguageTextField.getText().contains(":") ||
			secondLanguageTextField.getText().contains(":") ||
			phoneticScriptTextField.getText().contains(":") ||
			learnLevelTextField.getText().contains(":") ||
			relevanceTextField.getText().contains(":") ||
			descriptionTextArea.getText().contains(":")
		);
	}
	
	private boolean isAnInputFieldIsEmpty() {
		return (
			firstLanguageTextField.getText().isEmpty() ||
			phoneticScriptTextField.getText().isEmpty() ||
			secondLanguageTextField.getText().isEmpty() ||
			topicTextField.getText().isEmpty() ||
			chapterTextField.getText().isEmpty() ||
			learnLevelTextField.getText().isEmpty() ||
			relevanceTextField.getText().isEmpty() ||
			descriptionTextArea.getText().isEmpty()
		);
	}
}