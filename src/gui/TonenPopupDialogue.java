package gui;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import dialogues.vocableactions.AddVocableFrame;
import dialogues.vocableactions.ChangeMultipleVocablesFrame;
import dialogues.vocableactions.ChangeVocableFrame;
import dictionary.Settings;

@SuppressWarnings("serial")
public class TonenPopupDialogue extends JDialog {
	
	private SearchBox searchBoxParent;
	private AddVocableFrame addVocableDialogue;
	private ChangeVocableFrame changeVocableDialogueParent;
	private ChangeMultipleVocablesFrame changeMultipleVocablesDialogueParent;
	
	private JPanel tonenPanel = new JPanel(new MigLayout("wrap 4", "[0][0][0][0]"));
	private ArrayList<JButton> tonenButtons = new ArrayList<JButton>();
//	= {
//			new JButton("Ä�"), new JButton("Ã¡"), new JButton("ÇŽ"), new JButton("Ã "),
//			new JButton("Ä“"), new JButton("Ã©"), new JButton("Ä›"), new JButton("Ã¨"),
//			new JButton("Ä«"), new JButton("Ã­"), new JButton("Ç�"), new JButton("Ã¬"),
//			new JButton("Å�"), new JButton("Ã³"), new JButton("Ç’"), new JButton("Ã²"),
//			new JButton("Å«"), new JButton("Ãº"), new JButton("Ç”"), new JButton("Ã¹"),
//			new JButton("Ç–"), new JButton("Ç˜"), new JButton("Çš"), new JButton("Çœ")};
	private JButton toUpperLowerButton = new JButton();
	private JButton relativeButton;
	
	
	//CONSTRUCTORS
	public TonenPopupDialogue(SearchBox parent, boolean modal, JButton relativeButton) {
		this.searchBoxParent = parent;
		this.relativeButton = relativeButton;
		setTitle("Search Box - Special Characters");
		setPosition();
		createSpecialCharacterButtons();
		addComponents();
		addActionListeners();
	}
	
	public TonenPopupDialogue(AddVocableFrame addVocableDialogue, boolean modal, JButton relativeButton, String title) {
		this.addVocableDialogue = addVocableDialogue;
		this.relativeButton = relativeButton;
		setTitle(title);
		setPosition();
		createSpecialCharacterButtons();
		addComponents();
		addActionListeners();
	}
	
	public TonenPopupDialogue(ChangeVocableFrame changeVocableDialogParent, boolean modal, JButton relativeButton) {
		this.changeVocableDialogueParent = changeVocableDialogParent;
		this.relativeButton = relativeButton;
		setTitle("Change Vocable Dialog - Special Characters");
		setPosition();
		createSpecialCharacterButtons();
		addComponents();
		addActionListeners();
	}
	
	public TonenPopupDialogue(ChangeMultipleVocablesFrame changeMultipleVocablesDialogueParent, boolean modal, JButton relativeButton) {
		this.changeMultipleVocablesDialogueParent = changeMultipleVocablesDialogueParent;
		this.relativeButton = relativeButton;
		setTitle("Change Vocable Dialog - Special Characters");
		setPosition();
		createSpecialCharacterButtons();
		addComponents();
		addActionListeners();
	}
	
	/**
	 * Sets the position of this dialogue relative to the button
	 * that caused it to be displayed.
	 */
	private void setPosition() {
		setLocationRelativeTo(relativeButton);
	}
	
	/**
	 * Reads the special characters from the settings
	 * and creates a JButton for every special character
	 * and adds the JButtons to the button array
	 */
	private void createSpecialCharacterButtons() {
		for(int i = 0; i < Settings.specialCharacters.length(); i++) {
			//System.out.println("Laenge:" + Settings.specialCharacters.length() + ":");
			//System.out.println("Zeichen: " + Settings.specialCharacters.charAt(i) + ":");
			char buttonText = Settings.specialCharacters.charAt(i);
			tonenButtons.add(new JButton(Character.toString(buttonText)));
		}
	}
	
	private void addComponents() {
		//this.setUndecorated(true);
		
		setLayout(new MigLayout("wrap 4", "[][][][]"));
		
		add(tonenPanel, "cell 1 0");
		for(JButton button : tonenButtons) {
			button.setFont(new Font("Monospaced", Font.PLAIN, 16));
			button.setMargin(new Insets(4,8,4,8));
			tonenPanel.add(button);
		}
		toUpperLowerButton.setText("Capital letters");
		
		int toUpperButtonRow = (int) (Math.ceil(Settings.specialCharacters.length()/4)) + 1;
		tonenPanel.add(toUpperLowerButton, "cell 0 " + toUpperButtonRow + " 4 1");
		
		pack();
		setVisible(true);
	}
	
	private void addActionListeners() {
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
				if(searchBoxParent != null) {
					searchBoxParent.setTonenPopupDialogueDisplayed(false);
					dispose();
				} else if(addVocableDialogue != null) {
					addVocableDialogue.setTonenPopupDialogueDisplayed(false);
					dispose();
				} else if(changeVocableDialogueParent != null) { //changeVocableDialogueParent
					changeVocableDialogueParent.setTonenPopupDialogueDisplayed(false);
					dispose();
				} else { //changeMultipleVocablesDialogueParent
					changeMultipleVocablesDialogueParent.setTonenPopupDialogueDisplayed(false);
					dispose();
				}
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
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
			}
		});
		
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				int key = arg0.getKeyCode();
				if (key == KeyEvent.VK_ESCAPE) {         
					dispose();
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		
		toUpperLowerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(JButton button : tonenButtons) {
					if(button.getText().toUpperCase().equals(button.getText())) {
						button.setText(button.getText().toLowerCase(Locale.ENGLISH));
					} else {
						button.setText(button.getText().toUpperCase(Locale.ENGLISH));
					}
				}
			}
		});
		
		for(final JButton button : tonenButtons) {
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(searchBoxParent != null) {
						searchBoxParent.addSpecialCharacterToTextField(button.getText());
					} else if(addVocableDialogue != null) {
						addVocableDialogue.addSpecialCharacterToTextField(button.getText());
					} else if(changeVocableDialogueParent != null) {
						changeVocableDialogueParent.addSpecialCharacterToTextField(button.getText());
					} else { //changeVocableDialogParent
						changeMultipleVocablesDialogueParent.addSpecialCharacterToTextField(button.getText());
					}
				}
			});
		}
	}
}
