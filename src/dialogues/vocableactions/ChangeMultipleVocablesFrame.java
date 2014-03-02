
package dialogues.vocableactions;

import factories.ObserveableFactory;
import gui.DictionaryMainWindow;
import gui.JTextFieldWithContextMenu;
import gui.TonenPopupDialogue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

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
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class ChangeMultipleVocablesFrame extends JFrame implements VocabularyLanguagesChangeListener {
	
	private static ChangeMultipleVocablesFrame instance = null;
	
	private JLabel oldLabel = new JLabel("Old");
	private JLabel newLabel = new JLabel("New");
	
	private JLabel oldFirstLanguageLabel;
	private JLabel oldPhoneticScriptLabel;
	private JLabel oldSecondLanguageLabel;
	private JLabel oldTopicLabel = new JLabel("Topic");
	private JLabel oldChapterLabel = new JLabel("Chapter");
	private JLabel oldLearnLevelLabel = new JLabel("Level");
	private JLabel oldRelevanceLabel = new JLabel("Relevance");
	private JLabel oldDescriptionLabel = new JLabel("Description");
	
	private JLabel newFirstLanguageLabel;
	private JLabel newPhoneticScriptLabel;
	private JLabel newSecondLanguageLabel;
	private JLabel newTopicLabel = new JLabel("Topic");
	private JLabel newChapterLabel = new JLabel("Chapter");
	private JLabel newLevelLabel = new JLabel("Level");
	private JLabel newRelevanceLabel = new JLabel("Relevance");
	private JLabel newDescriptionLabel = new JLabel("Description");
	
	private JTextFieldWithContextMenu oldFirstLanguageTextField;
	private JTextFieldWithContextMenu oldPhoneticScriptTextField;
	private JTextFieldWithContextMenu oldSecondLanguageTextField;
	private JTextFieldWithContextMenu oldTopicTextField;
	private JTextFieldWithContextMenu oldChapterTextField;
	private JTextFieldWithContextMenu oldLearnLevelTextField;
	private JTextFieldWithContextMenu oldRelevanceTextField;
	private JTextArea oldDescriptionTextArea;
	private JScrollPane oldDescriptionScrollPane;
	
	private JTextFieldWithContextMenu newFirstLanguageTextField;
	private JTextFieldWithContextMenu newPhoneticScriptTextField;
	private JTextFieldWithContextMenu newSecondLanguageTextField;
	private JTextFieldWithContextMenu newTopicTextField;
	private JTextFieldWithContextMenu newChapterTextField;
	private JTextFieldWithContextMenu newLearnLevelTextField;
	private JTextFieldWithContextMenu newRelevanceTextField;
	private JTextArea newDescriptionTextArea;
	private JScrollPane newDescriptionScrollPane;
	
	private JPanel buttonPanel = new JPanel(new MigLayout());
	
	private JButton changeVocableButton = new JButton("Change");
	private JButton changeAllVocablesButton = new JButton("Change all");
	private JButton clearTextFieldsButton = new JButton("Clear");
	private JButton cancelButton = new JButton("Cancel");
	private JButton specialCharacterButton = new JButton();
	private JButton previousButton = new JButton("<<");
	private JButton nextButton = new JButton(">>");

	private ArrayList<Vocable> vocables;
	private ArrayList<Vocable> changedVocables;
	
	private boolean tonenPopupDialogueDisplayed = false;
	private TonenPopupDialogue tonenPopupDialogue;
	
	private String focusedTextFieldName = "firstLanguage";
	
	private static int numberOfCurrentlyDisplayedVocable;
	
	private static boolean listenersAdded = false;
	
	/**
	 * returns the singleton instance
	 * @return the singleton
	 */
	public static ChangeMultipleVocablesFrame getInstance() {
		if(instance == null) {
			instance = new ChangeMultipleVocablesFrame();
		}
		return instance;
	}
	
	/**
	 * Initializes the frame with values
	 * @param numbersOfRows the numbers of the rows in the vocable list of the selected vocables
	 */
	public void initialize(int[] numbersOfRows) {
		
		getContentPane().removeAll();
		
		vocables = new ArrayList<Vocable>();
		changedVocables = new ArrayList<Vocable>();
		
		numberOfCurrentlyDisplayedVocable = 0;
		
		oldFirstLanguageLabel = new JLabel(Settings.languageOptions_firstLanguageName);
		oldPhoneticScriptLabel = new JLabel(Settings.languageOptions_phoneticScriptName);
		oldSecondLanguageLabel = new JLabel(Settings.languageOptions_secondLanguageName);
		
		newFirstLanguageLabel = new JLabel(Settings.languageOptions_firstLanguageName);
		newPhoneticScriptLabel = new JLabel(Settings.languageOptions_phoneticScriptName);
		newSecondLanguageLabel = new JLabel(Settings.languageOptions_secondLanguageName);
		
		oldFirstLanguageTextField = new JTextFieldWithContextMenu(20);
		oldPhoneticScriptTextField = new JTextFieldWithContextMenu(20);
		oldSecondLanguageTextField = new JTextFieldWithContextMenu(20);
		oldTopicTextField = new JTextFieldWithContextMenu(20);
		oldChapterTextField = new JTextFieldWithContextMenu(20);
		oldLearnLevelTextField = new JTextFieldWithContextMenu(20);
		oldRelevanceTextField = new JTextFieldWithContextMenu(20);
		oldDescriptionTextArea = new JTextArea(4, 20);
		oldDescriptionTextArea.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 12));
		oldDescriptionTextArea.setLineWrap(true);
		oldDescriptionTextArea.setWrapStyleWord(true);
		oldDescriptionScrollPane = new JScrollPane(oldDescriptionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		newFirstLanguageTextField = new JTextFieldWithContextMenu(20);
		newPhoneticScriptTextField = new JTextFieldWithContextMenu(20);
		newSecondLanguageTextField = new JTextFieldWithContextMenu(20);
		newTopicTextField = new JTextFieldWithContextMenu(20);
		newChapterTextField = new JTextFieldWithContextMenu(20);
		newLearnLevelTextField = new JTextFieldWithContextMenu(20);
		newRelevanceTextField = new JTextFieldWithContextMenu(20);
		newDescriptionTextArea = new JTextArea(4, 20);
		newDescriptionTextArea.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 12));
		newDescriptionTextArea.setLineWrap(true);
		newDescriptionTextArea.setWrapStyleWord(true);
		newDescriptionScrollPane = new JScrollPane(newDescriptionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//make old attribute value input fields uneditable 
		oldFirstLanguageTextField.setEditable(false);
		oldPhoneticScriptTextField.setEditable(false);
		oldSecondLanguageTextField.setEditable(false);
		oldTopicTextField.setEditable(false);
		oldChapterTextField.setEditable(false);
		oldLearnLevelTextField.setEditable(false);
		oldRelevanceTextField.setEditable(false);
		oldDescriptionTextArea.setEditable(false);
		
		oldFirstLanguageTextField.setBackground(new Color(240,240,240));
		oldPhoneticScriptTextField.setBackground(new Color(240,240,240));
		oldSecondLanguageTextField.setBackground(new Color(240,240,240));
		oldTopicTextField.setBackground(new Color(240,240,240));
		oldChapterTextField.setBackground(new Color(240,240,240));
		oldLearnLevelTextField.setBackground(new Color(240,240,240));
		oldRelevanceTextField.setBackground(new Color(240,240,240));
		oldDescriptionTextArea.setBackground(new Color(240,240,240));
		
		//set font for new attribute value components
		newFirstLanguageTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		newPhoneticScriptTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		newSecondLanguageTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		newTopicTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		newChapterTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		newLearnLevelTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		newRelevanceTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		
		for(int numberOfSelectedRow : numbersOfRows) {
		
			int columnOfFirstLanguage = DictionaryMainWindow.vocableTable.getNumberOfColumnOfFirstLanguage();
			int columnOfPhoneticScript = DictionaryMainWindow.vocableTable.getNumberOfColumnOfPhoneticScript();
			int columnOfSecondLanguage = DictionaryMainWindow.vocableTable.getNumberOfColumnOfSecondLanguage();
			
			String firstLanguage = (String) DictionaryMainWindow.vocableTable.getValueAt(numberOfSelectedRow, columnOfFirstLanguage);
			String phoneticScript = (String) DictionaryMainWindow.vocableTable.getValueAt(numberOfSelectedRow, columnOfPhoneticScript);
			String secondLanguage = (String) DictionaryMainWindow.vocableTable.getValueAt(numberOfSelectedRow, columnOfSecondLanguage);
			
			//Search through the search result to get the right number of the selected vocable
			for(int i = 0; i < VocableManager.getLastSearchResult().size(); i++) {
				if(	VocableManager.getLastSearchResult().get(i).getFirstLanguage().equals(firstLanguage) &&
					VocableManager.getLastSearchResult().get(i).getPhoneticScript().equals(phoneticScript) &&
					VocableManager.getLastSearchResult().get(i).getSecondLanguage().equals(secondLanguage)) {
					vocables.add(VocableManager.getLastSearchResult().get(i));
					break;
				}
			}
			
			try {
				changedVocables.add((Vocable) vocables.get(vocables.size()-1).clone());
			} catch (CloneNotSupportedException e) {
				System.out.println("Could not add changed vocable to changedVocable (ArrayList).");
				e.printStackTrace();
			}
		}
		
		addComponents();
		
		//If the listeners are already added, don't add them again, so that their actions aren't performed more than once.
		//Important because it's a "singleton"
		if(!ChangeMultipleVocablesFrame.listenersAdded) {
			addActionListeners();
			//addWindowListeners();
			addFocusListeners();
			Helper.addEscapeListener(this);
			ChangeMultipleVocablesFrame.listenersAdded = true;
		}
	}
	
	private void addComponents() {
		this.setLayout(new MigLayout("wrap 4",""));
		
		//set text for old attribute value components
		oldFirstLanguageTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getFirstLanguage());
		oldPhoneticScriptTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getPhoneticScript());
		oldSecondLanguageTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getSecondLanguage());
		oldTopicTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getTopic());
		oldChapterTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getChapter());
		oldLearnLevelTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getLearnLevel());
		oldRelevanceTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getRelevance());
		oldDescriptionTextArea.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getDescription());
		
		//set text for new attribute value components
		newFirstLanguageTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getFirstLanguage());
		newPhoneticScriptTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getPhoneticScript());
		newSecondLanguageTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getSecondLanguage());
		newTopicTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getTopic());
		newChapterTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getChapter());
		newLearnLevelTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getLearnLevel());
		newRelevanceTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getRelevance());
		newDescriptionTextArea.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getDescription());
		
		//set caret positions for old attribute value components
		oldFirstLanguageTextField.setCaretPosition(0);
		oldPhoneticScriptTextField.setCaretPosition(0);
		oldSecondLanguageTextField.setCaretPosition(0);
		oldTopicTextField.setCaretPosition(0);
		oldChapterTextField.setCaretPosition(0);
		oldLearnLevelTextField.setCaretPosition(0);
		oldRelevanceTextField.setCaretPosition(0);
		oldDescriptionTextArea.setCaretPosition(0);
		
		//set caret postions for new attribute value components
		newFirstLanguageTextField.setCaretPosition(0);
		newPhoneticScriptTextField.setCaretPosition(0);
		newSecondLanguageTextField.setCaretPosition(0);
		newTopicTextField.setCaretPosition(0);
		newChapterTextField.setCaretPosition(0);
		newLearnLevelTextField.setCaretPosition(0);
		newRelevanceTextField.setCaretPosition(0);
		newDescriptionTextArea.setCaretPosition(0);
		
		//add headings
		add(oldLabel, "span 2");
		add(newLabel, "span 2");
		
		//add old attribute labels
		add(oldFirstLanguageLabel, "cell 0 1");
		add(oldPhoneticScriptLabel, "cell 0 2");
		add(oldSecondLanguageLabel, "cell 0 3");
		add(oldTopicLabel, "cell 0 4");
		add(oldChapterLabel, "cell 0 5");
		add(oldLearnLevelLabel, "cell 0 6");
		add(oldRelevanceLabel, "cell 0 7");
		add(oldDescriptionLabel, "cell 0 8");
		
		//add old attribute value components
		add(oldFirstLanguageTextField, "cell 1 1");
		add(oldPhoneticScriptTextField, "cell 1 2");
		add(oldSecondLanguageTextField, "cell 1 3");
		add(oldTopicTextField, "cell 1 4");
		add(oldChapterTextField, "cell 1 5");
		add(oldLearnLevelTextField, "cell 1 6");
		add(oldRelevanceTextField, "cell 1 7");
		add(oldDescriptionScrollPane, "cell 1 8");
		
		//add new attribute labels
		add(newFirstLanguageLabel, "cell 2 1");
		add(newPhoneticScriptLabel, "cell 2 2");
		add(newSecondLanguageLabel, "cell 2 3");
		add(newTopicLabel, "cell 2 4");
		add(newChapterLabel, "cell 2 5");
		add(newLevelLabel, "cell 2 6");
		add(newRelevanceLabel, "cell 2 7");
		add(newDescriptionLabel, "cell 2 8");
		
		//add new attribute value components
		add(newFirstLanguageTextField, "cell 3 1");
		add(newPhoneticScriptTextField, "cell 3 2");
		add(newSecondLanguageTextField, "cell 3 3");
		add(newTopicTextField, "cell 3 4");
		add(newChapterTextField, "cell 3 5");
		add(newLearnLevelTextField, "cell 3 6");
		add(newRelevanceTextField, "cell 3 7");
		add(newDescriptionScrollPane, "cell 3 8");
		
		//add buttons
		add(buttonPanel, "cell 0 9 4 1");
		buttonPanel.add(changeVocableButton, "cell 0 0");
		buttonPanel.add(changeAllVocablesButton, "cell 1 0");
		buttonPanel.add(clearTextFieldsButton, "cell 2 0");
		buttonPanel.add(cancelButton, "cell 3 0");
		
		try {
			Image img = ImageIO.read(new File("src/resources/insertsymbol.png"));
			specialCharacterButton.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		specialCharacterButton.setContentAreaFilled(false);
		specialCharacterButton.setMargin(new Insets(4,4,4,4));
		buttonPanel.add(specialCharacterButton, "cell 4 0");

		buttonPanel.add(previousButton, "cell 5 0");
		buttonPanel.add(nextButton, "cell 6 0");
	}
	
	private void addActionListeners() {
		changeVocableButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeVocableButtonAction();
			}
		});
		
		changeAllVocablesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeAllVocablesButtonAction();
			}
		});
		
		clearTextFieldsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearTextFieldsButtonAction();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelButtonAction();
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
		
		previousButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				previousButtonActionPerformed();
			}
		});
		
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextButtonActionPerformed();
			}
		});
	}
	
	private void addFocusListeners() {
		newFirstLanguageTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "firstLanguage";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
			}
        });
		
		newPhoneticScriptTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "phoneticScript";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
			}
        });
		
		newSecondLanguageTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "secondLanguage";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
			}
        });
		
		newTopicTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "topic";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
			}
        });
		
		newChapterTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "chapter";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
			}
        });
		
		newLearnLevelTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "learnLevel";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
			}
        });
		
		newRelevanceTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "relevance";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
			}
        });
		
		newDescriptionTextArea.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "description";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
			}
        });
	}
	
	// This button's action is simply to dispose of the JDialog.
	private void changeVocableButtonAction() {
		
		String firstLanguage = newFirstLanguageTextField.getText();
		String phoneticScript = newPhoneticScriptTextField.getText();
		String secondLanguage = newSecondLanguageTextField.getText();
		String topic = newTopicTextField.getText();
		String chapter = newChapterTextField.getText();
		String learnLevel = newLearnLevelTextField.getText();
		String relevance = newRelevanceTextField.getText();
		String description = newDescriptionTextArea.getText();
		
		//Check for colons in input fields
		if(!(
			topic.contains(":") ||
			chapter.contains(":") ||
			firstLanguage.contains(":") ||
			secondLanguage.contains(":") ||
			phoneticScript.contains(":") ||
			learnLevel.contains(":")) ||
			relevance.contains(":") ||
			description.contains(":")
		) {
			VocableManager.changeVocable(vocables.get(numberOfCurrentlyDisplayedVocable), firstLanguage, phoneticScript, secondLanguage, topic, chapter, learnLevel, relevance, description);
		} else {
			JOptionPane.showMessageDialog(this, "Colons (:) are used to seperate the parts of a vocable. You cannot use them in parts of a vocable.", "Usage of Colons", JOptionPane.OK_OPTION);
		}
	}
	
	private void changeAllVocablesButtonAction() {
		storeCurrentlyDisplayedVocableChanges();
		
		for(int i = 0; i < changedVocables.size(); i++) {
			vocables.get(i).setFirstLanguage(changedVocables.get(i).getFirstLanguage());
			vocables.get(i).setPhoneticScript(changedVocables.get(i).getPhoneticScript());
			vocables.get(i).setSecondLanguage(changedVocables.get(i).getSecondLanguage());
			vocables.get(i).setTopic(changedVocables.get(i).getTopic());
			vocables.get(i).setChapter(changedVocables.get(i).getChapter());
			vocables.get(i).setLearnLevel(changedVocables.get(i).getLearnLevel());
			vocables.get(i).setRelevance(changedVocables.get(i).getRelevance());
			vocables.get(i).setDescription(changedVocables.get(i).getDescription());
		}
		
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		cancelButtonAction();
	}
	
	private void clearTextFieldsButtonAction() {
		newFirstLanguageTextField.setText("");
		newPhoneticScriptTextField.setText("");
		newSecondLanguageTextField.setText("");
		newTopicTextField.setText("");
		newChapterTextField.setText("");
		newLearnLevelTextField.setText("");
		newRelevanceTextField.setText("");
		newDescriptionTextArea.setText("");
	}
	
	private void cancelButtonAction() {
		disposeTonenButtonDialog();
		dispose();
	}
	
	private void previousButtonActionPerformed() {
		storeCurrentlyDisplayedVocableChanges();
		
		if(numberOfCurrentlyDisplayedVocable - 1 >= 0) {
			numberOfCurrentlyDisplayedVocable--;
			updateVocableInformation();
		}
	}
	
	private void nextButtonActionPerformed() {
		storeCurrentlyDisplayedVocableChanges();
		
		if(numberOfCurrentlyDisplayedVocable + 1 < vocables.size()) {
			numberOfCurrentlyDisplayedVocable++;
			updateVocableInformation();
		}
	}

	private void disposeTonenButtonDialog() {
		if(tonenPopupDialogueDisplayed) {
			tonenPopupDialogue.dispose();
			setTonenPopupDialogueDisplayed(false);
		}
	}
	
	private void displayTonenPopupDialogue() {
		tonenPopupDialogue = new TonenPopupDialogue(this, false, specialCharacterButton);
	}
	
	public boolean isTonenPopupDialogueDisplayed() {
		return tonenPopupDialogueDisplayed;
	}

	public void setTonenPopupDialogueDisplayed(boolean tonenPopupDialogueDisplayed) {
		this.tonenPopupDialogueDisplayed = tonenPopupDialogueDisplayed;
	}
	
	public void addSpecialCharacterToTextField(String specialCharacter) {
		if(focusedTextFieldName.equals("firstLanguage")) {
			Helper.insertCharacterIntoTextfield(newFirstLanguageTextField, specialCharacter);
		} else if(focusedTextFieldName.equals("phoneticScript")) {
			Helper.insertCharacterIntoTextfield(newPhoneticScriptTextField, specialCharacter);
		} else if(focusedTextFieldName.equals("secondLanguage")) {
			Helper.insertCharacterIntoTextfield(newSecondLanguageTextField, specialCharacter);
		} else if(focusedTextFieldName.equals("topic")) {
			Helper.insertCharacterIntoTextfield(newTopicTextField, specialCharacter);
		} else if(focusedTextFieldName.equals("chapter")) {
			Helper.insertCharacterIntoTextfield(newChapterTextField, specialCharacter);
		} else if(focusedTextFieldName.equals("learnLevel")) {
			Helper.insertCharacterIntoTextfield(newLearnLevelTextField, specialCharacter);
		} else if(focusedTextFieldName.equals("relevance")) {
			Helper.insertCharacterIntoTextfield(newRelevanceTextField, specialCharacter);
		} else if(focusedTextFieldName.equals("description")) {
			Helper.insertCharacterIntoTextArea(newDescriptionTextArea, specialCharacter);
		}
	}
	
	private void updateVocableInformation() {
		oldFirstLanguageTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getFirstLanguage());
		oldPhoneticScriptTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getPhoneticScript());
		oldSecondLanguageTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getSecondLanguage());		
		oldChapterTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getChapter());
		oldTopicTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getTopic());
		oldLearnLevelTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getLearnLevel());
		oldRelevanceTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getRelevance());
		oldDescriptionTextArea.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getDescription());
		
		newFirstLanguageTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getFirstLanguage());
		newPhoneticScriptTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getPhoneticScript());
		newSecondLanguageTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getSecondLanguage());		
		newChapterTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getChapter());
		newTopicTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getTopic());
		newLearnLevelTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getLearnLevel());
		newRelevanceTextField.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getRelevance());
		newDescriptionTextArea.setText(changedVocables.get(numberOfCurrentlyDisplayedVocable).getDescription());
	}
	
	private void storeCurrentlyDisplayedVocableChanges() {
		changedVocables.get(numberOfCurrentlyDisplayedVocable).setFirstLanguage(newFirstLanguageTextField.getText());
		changedVocables.get(numberOfCurrentlyDisplayedVocable).setPhoneticScript(newPhoneticScriptTextField.getText());
		changedVocables.get(numberOfCurrentlyDisplayedVocable).setSecondLanguage(newSecondLanguageTextField.getText());
		changedVocables.get(numberOfCurrentlyDisplayedVocable).setTopic(newTopicTextField.getText());
		changedVocables.get(numberOfCurrentlyDisplayedVocable).setChapter(newChapterTextField.getText());
		changedVocables.get(numberOfCurrentlyDisplayedVocable).setLearnLevel(newLearnLevelTextField.getText());
		changedVocables.get(numberOfCurrentlyDisplayedVocable).setRelevance(newRelevanceTextField.getText());
		changedVocables.get(numberOfCurrentlyDisplayedVocable).setDescription(newDescriptionTextArea.getText());
	}

	@Override
	public void changeVocabularyLanguages(String newFirstLanguageName, String newPhoneticScriptName, String newSecondLanguageName) {
		oldFirstLanguageLabel.setText(newFirstLanguageName);
		oldPhoneticScriptLabel.setText(newPhoneticScriptName);
		oldSecondLanguageLabel.setText(newSecondLanguageName);
		newFirstLanguageLabel.setText(newFirstLanguageName);
		newPhoneticScriptLabel.setText(newPhoneticScriptName);
		newSecondLanguageLabel.setText(newSecondLanguageName);
	}
}