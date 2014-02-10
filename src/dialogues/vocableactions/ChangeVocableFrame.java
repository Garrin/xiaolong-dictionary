
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
public class ChangeVocableFrame extends JFrame implements VocabularyLanguagesChangeListener {
	
	private static ChangeVocableFrame instance = null;
	
	private JLabel oldLabel = new JLabel("Old");
	private JLabel newLabel = new JLabel("New");
	
	private JLabel oldFirstLanguageLabel = new JLabel(Settings.languageOptions_firstLanguageName);
	private JLabel oldPhoneticScriptLabel = new JLabel(Settings.languageOptions_phoneticScriptName);
	private JLabel oldSecondLanguageLabel = new JLabel(Settings.languageOptions_secondLanguageName);
	private JLabel oldTopicLabel = new JLabel("Topic");
	private JLabel oldChapterLabel = new JLabel("Chapter");
	private JLabel oldLevelLabel = new JLabel("Level");
	
	private JLabel newFirstLanguageLabel = new JLabel(Settings.languageOptions_firstLanguageName);
	private JLabel newPhoneticScriptLabel = new JLabel(Settings.languageOptions_phoneticScriptName);
	private JLabel newSecondLanguageLabel = new JLabel(Settings.languageOptions_secondLanguageName);
	private JLabel newTopicLabel = new JLabel("Topic");
	private JLabel newChapterLabel = new JLabel("Chapter");
	private JLabel newLevelLabel = new JLabel("Level");
	
	private JTextFieldWithContextMenu oldFirstLanguageTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu oldPhoneticScriptTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu oldSecondLanguageTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu oldTopicTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu oldChapterTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu oldLearnLevelTextField = new JTextFieldWithContextMenu(20);
	
	private JTextFieldWithContextMenu newFirstLanguageTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu newPhoneticScriptTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu newSecondLanguageTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu newTopicTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu newChapterTextField = new JTextFieldWithContextMenu(20);
	private JTextFieldWithContextMenu newLearnLevelTextField = new JTextFieldWithContextMenu(20);

	private JPanel buttonPanel = new JPanel(new MigLayout("wrap 4"));
	
	private JButton changeVocableButton = new JButton("Change");
	private JButton clearTextFieldsButton = new JButton("Clear");
	private JButton cancelButton = new JButton("Cancel");
	private JButton specialCharacterButton = new JButton();

	private static Vocable newVocable;
	private Vocable vocable;
	
	private boolean tonenPopupDialogueDisplayed = false;
	private TonenPopupDialogue tonenPopupDialogue;
	
	private String focusedTextFieldName = "firstLanguage";
	
	private static boolean listenersAdded = false;
	
	/*
	public ChangeVocableDialogue(DictionaryMainWindow window, boolean modal, Vocable vocable) {
		super(window, modal);
		this.window = window;
		this.vocable = vocable;
		addComponents();
		addActionListeners();
		addWindowListeners();
		addFocusListeners();
		Helper.addEscapeListener(this);
		
		ObserveableFactory.getVocabularyLanguagesObserveable().registerListener(this);
	}
	*/
	
	public static ChangeVocableFrame getInstance() {
		if(instance == null) {
			instance = new ChangeVocableFrame();
		}
		return instance;
	}
	
	public void initialize(Vocable vocable) {
		
		this.vocable = vocable;
		
		getContentPane().removeAll();
		addComponents();
		
		//If the listeners are already added, don't add them again, so that their actions aren't performed more than once.
		//Important because it's a "singleton"
		if(!ChangeVocableFrame.listenersAdded) {
			addActionListeners();
			addWindowListeners();
			addFocusListeners();
			Helper.addEscapeListener(this);
			ObserveableFactory.getVocabularyLanguagesObserveable().registerListener(this);
			ChangeVocableFrame.listenersAdded = true;
		}
	}
	
	private void addComponents() {
		this.setLayout(new MigLayout("wrap 4",""));
		
		oldFirstLanguageTextField.setEditable(false);
		oldPhoneticScriptTextField.setEditable(false);
		oldSecondLanguageTextField.setEditable(false);
		oldTopicTextField.setEditable(false);
		oldChapterTextField.setEditable(false);
		oldLearnLevelTextField.setEditable(false);
		
		add(oldLabel, "span 2");
		add(newLabel, "span 2");
		
		add(oldFirstLanguageLabel, "cell 0 1");
		add(oldPhoneticScriptLabel, "cell 0 2");
		add(oldSecondLanguageLabel, "cell 0 3");
		add(oldTopicLabel, "cell 0 4");
		add(oldChapterLabel, "cell 0 5");
		add(oldLevelLabel, "cell 0 6");
		
		oldFirstLanguageTextField.setText(vocable.getFirstLanguage());
		add(oldFirstLanguageTextField, "cell 1 1");
		oldPhoneticScriptTextField.setText(vocable.getPhoneticScript());
		add(oldPhoneticScriptTextField, "cell 1 2");
		oldSecondLanguageTextField.setText(vocable.getSecondLanguage());
		add(oldSecondLanguageTextField, "cell 1 3");
		oldTopicTextField.setText(vocable.getTopic());
		add(oldTopicTextField, "cell 1 4");
		oldChapterTextField.setText(vocable.getChapter());
		add(oldChapterTextField, "cell 1 5");
		oldLearnLevelTextField.setText(vocable.getLearnLevel());
		add(oldLearnLevelTextField, "cell 1 6");
				
		newFirstLanguageTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		newFirstLanguageTextField.setText(vocable.getFirstLanguage());
		newPhoneticScriptTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		newPhoneticScriptTextField.setText(vocable.getPhoneticScript());
		newSecondLanguageTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		newSecondLanguageTextField.setText(vocable.getSecondLanguage());
		newTopicTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		newTopicTextField.setText(vocable.getTopic());
		newChapterTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		newChapterTextField.setText(vocable.getChapter());
		newLearnLevelTextField.setFont(new Font(Settings.addVocable_font, Font.PLAIN, 20));
		newLearnLevelTextField.setText(vocable.getLearnLevel());
		
		oldFirstLanguageTextField.setCaretPosition(0);
		oldPhoneticScriptTextField.setCaretPosition(0);
		oldSecondLanguageTextField.setCaretPosition(0);
		oldTopicTextField.setCaretPosition(0);
		oldChapterTextField.setCaretPosition(0);
		oldLearnLevelTextField.setCaretPosition(0);
		
		newFirstLanguageTextField.setCaretPosition(0);
		newPhoneticScriptTextField.setCaretPosition(0);
		newSecondLanguageTextField.setCaretPosition(0);
		newTopicTextField.setCaretPosition(0);
		newChapterTextField.setCaretPosition(0);
		newLearnLevelTextField.setCaretPosition(0);
		
		add(newFirstLanguageLabel, "cell 2 1");
		add(newPhoneticScriptLabel, "cell 2 2");
		add(newSecondLanguageLabel, "cell 2 3");
		add(newTopicLabel, "cell 2 4");
		add(newChapterLabel, "cell 2 5");
		add(newLevelLabel, "cell 2 6");
		
		add(newFirstLanguageTextField, "cell 3 1");
		add(newPhoneticScriptTextField, "cell 3 2");
		add(newSecondLanguageTextField, "cell 3 3");
		add(newTopicTextField, "cell 3 4");
		add(newChapterTextField, "cell 3 5");
		add(newLearnLevelTextField, "cell 3 6");
		
		add(buttonPanel, "cell 0 7 4 1");
		buttonPanel.add(changeVocableButton);
		buttonPanel.add(clearTextFieldsButton);
		buttonPanel.add(cancelButton);
		try {
			Image img = ImageIO.read(new File("src/resources/insertsymbol.png"));
			specialCharacterButton.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		specialCharacterButton.setContentAreaFilled(false);
		specialCharacterButton.setMargin(new Insets(4,4,4,4));
		buttonPanel.add(specialCharacterButton);
	}
	
	private void addActionListeners() {
		changeVocableButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeVocableButtonAction();
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
	}

	private void addWindowListeners() {
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				cancelButtonAction();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
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
				// TODO Auto-generated method stub
			}
        });
		
		newPhoneticScriptTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "phoneticScript";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
			}
        });
		
		newSecondLanguageTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "secondLanguage";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
			}
        });
		
		newTopicTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "topic";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
			}
        });
		
		newChapterTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "chapter";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
			}
        });
		
		newLearnLevelTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				focusedTextFieldName = "learnLevel";
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
			}
        });
	}
	
	// This button's action is simply to dispose of the JDialog.
	private void changeVocableButtonAction() {
		
		if(!(newTopicTextField.getText().contains(":") || newChapterTextField.getText().contains(":") || newFirstLanguageTextField.getText().contains(":") || newSecondLanguageTextField.getText().contains(":") || newPhoneticScriptTextField.getText().contains(":") || newLearnLevelTextField.getText().contains(":"))) {
			//Dictionary.changeVocable(vocable, newFirstLanguageTextField.getText(), newPhoneticScriptTextField.getText(), newSecondLanguageTextField.getText(), newTopicTextField.getText(), newChapterTextField.getText(), newLearnLevelTextField.getText());			
			VocableManager.changeVocable(vocable, newFirstLanguageTextField.getText(), newPhoneticScriptTextField.getText(), newSecondLanguageTextField.getText(), newTopicTextField.getText(), newChapterTextField.getText(), newLearnLevelTextField.getText());
			//Dictionary.vocabularyIsSavedToFile = false;
			disposeTonenButtonDialog();
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "Colons (:) are used to seperate the parts of a vocable. You cannot use them in parts of a vocable.", "Usage of Colons", JOptionPane.OK_OPTION);
		}
	}
	
	private void clearTextFieldsButtonAction() {
		newFirstLanguageTextField.setText("");
		newPhoneticScriptTextField.setText("");
		newSecondLanguageTextField.setText("");
		newTopicTextField.setText("");
		newChapterTextField.setText("");
		newLearnLevelTextField.setText("");
	}
	
	private void cancelButtonAction() {
		disposeTonenButtonDialog();
		dispose();
	}

	private void disposeTonenButtonDialog() {
		if(tonenPopupDialogueDisplayed) {
			tonenPopupDialogue.dispose();
			setTonenPopupDialogueDisplayed(false);
		}
	}

	public static Vocable getNewVocable() {
		return newVocable;
	}


	public static void setNewVocable(Vocable newVocable) {
		ChangeVocableFrame.newVocable = newVocable;
	}
	
//	public void updateLanguages(String newFirstLanguage, String newPhoneticScript, String newSecondLanguage) {
//		oldFirstLanguageLabel.setText(newFirstLanguage);
//		oldPhoneticScriptLabel.setText(newPhoneticScript);
//		oldSecondLanguageLabel.setText(newSecondLanguage);
//		newFirstLanguageLabel.setText(newFirstLanguage);
//		newPhoneticScriptLabel.setText(newPhoneticScript);
//		newSecondLanguageLabel.setText(newSecondLanguage);
//	}
	
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
		}
	}

	@Override
	public void changeVocabularyLanguages(String newFirstLanguage, String newPhoneticScript, String newSecondLanguage) {
		oldFirstLanguageLabel.setText(newFirstLanguage);
		oldPhoneticScriptLabel.setText(newPhoneticScript);
		oldSecondLanguageLabel.setText(newSecondLanguage);
		newFirstLanguageLabel.setText(newFirstLanguage);
		newPhoneticScriptLabel.setText(newPhoneticScript);
		newSecondLanguageLabel.setText(newSecondLanguage);
	}
}