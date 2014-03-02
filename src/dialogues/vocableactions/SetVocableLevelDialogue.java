
package dialogues.vocableactions;

import factories.FrameFactory;
import gui.DictionaryMainWindow;
import gui.JTextFieldWithContextMenu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import manager.VocableManager;
import net.miginfocom.swing.MigLayout;
import dialogues.Helper;

/**
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class SetVocableLevelDialogue extends JDialog {
	
	private JTextPane warningTextPane;
	private JTextFieldWithContextMenu learnLevelTextField;
	
	private JPanel buttonPanel = new JPanel();
	private JButton setLevelButton = new JButton("Set level");
	private JButton cancelButton = new JButton("Cancel");
	
	private static SetVocableLevelDialogue instance = null;
	private static boolean listenersAdded = false;
	
	private SetVocableLevelDialogue(DictionaryMainWindow parent, boolean modal) {
		super(parent, modal);
		
		addComponents();
		
		if(!SetVocableLevelDialogue.listenersAdded) {
			addActionListeners();
			Helper.addEscapeListener(this);
			SetVocableLevelDialogue.listenersAdded = true;
		}
	}
	
	public static SetVocableLevelDialogue getInstance() {
		if(!SetVocableLevelDialogue.listenersAdded) {
			SetVocableLevelDialogue.instance = new SetVocableLevelDialogue(FrameFactory.getDictionaryMainWindow(), true);
		}
		return SetVocableLevelDialogue.instance;
	}
	
	private void addComponents() {
		this.setLayout(new MigLayout("wrap 2"));
		
		String warningText = 	"Warning: Setting a vocable level will overwrite the level attribute of all vocables in the current search result." +
								" For example if you set level \"0\", all vocables of the current search result will have level \"0\".";
		
		SimpleAttributeSet style = new SimpleAttributeSet();
		StyleConstants.setFontSize(style, 12);
		StyleConstants.setAlignment(style, StyleConstants.ALIGN_JUSTIFIED);
		
		warningTextPane = new JTextPane();
		//warningTextPane.setMinimumSize(new Dimension(200, 200));
		warningTextPane.setMaximumSize(new Dimension(300, 200));
		warningTextPane.setEditable(false);
		warningTextPane.setBackground(getBackground());
		warningTextPane.setText("");
		
		try {
			warningTextPane.getStyledDocument().setParagraphAttributes(0, warningText.length(), style, true);
			warningTextPane.getStyledDocument().insertString(0, warningText, style);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		warningTextPane.setCaretPosition(0);
		warningTextPane.setBackground(getBackground());
		warningTextPane.setEditable(false);
		
		add(warningTextPane, "cell 0 0 2 1");
		
		learnLevelTextField = new JTextFieldWithContextMenu("");
		learnLevelTextField.setColumns(10);
		add(learnLevelTextField, "cell 0 1");
		
		add(buttonPanel, "cell 0 2");
		buttonPanel.add(setLevelButton, "cell 0 0");
		buttonPanel.add(cancelButton, "cell 1 0");
	}
	
	private void addActionListeners() {
		setLevelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setLevelButtonAction();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelButtonAction();
			}
		});
	}

	private void setLevelButtonAction() {
		if(!learnLevelTextField.getText().contains(":")) {
			VocableManager.setLevelForVocables(VocableManager.getLastSearchResult(), learnLevelTextField.getText());
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "Colons (:) are used to seperate the parts of a vocable. You cannot use them in parts of a vocable.", "Usage of Colons", JOptionPane.OK_OPTION);
		}
	}
	
	private void cancelButtonAction() {
		this.dispose();
	}
}