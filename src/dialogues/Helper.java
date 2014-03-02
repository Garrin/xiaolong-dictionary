package dialogues;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class Helper {
	
	public static void addEscapeListener(final JDialog dialog) {
	    ActionListener escListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dialog.setVisible(false);
			}
	    };
	    dialog.getRootPane().registerKeyboardAction(escListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
	
	public static void addEscapeListener(final JFrame frame) {
	    ActionListener escListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				frame.setVisible(false);
			}
	    };
	    frame.getRootPane().registerKeyboardAction(escListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
	
	/**
	 * Inserts a given special character into a textfield at the cursor position or replacing the highlighted text
	 * @param textfield the textfield in which the given special character will be inserted
	 * @param specialCharacter the special character inserted
	 */
	public static void insertCharacterIntoTextfield(JTextField textfield, String specialCharacter) {
		if(textfield.getSelectionEnd()-textfield.getSelectionStart() != 0) {
			try {
				int oldCaretPosition = textfield.getCaretPosition();
				
				String newText = textfield.getText(0, textfield.getSelectionStart());
				newText += specialCharacter;
				newText += textfield.getText(textfield.getSelectionEnd(), textfield.getText().length()-textfield.getSelectionEnd());
				
				textfield.setText(newText);
				textfield.requestFocusInWindow();
				
				if(oldCaretPosition+Math.floor(textfield.getColumns()/2) < textfield.getText().length()) {
					textfield.setCaretPosition((int) (oldCaretPosition+Math.floor(textfield.getColumns()/2)));
				} else {
					textfield.setCaretPosition(textfield.getText().length());
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(textfield.getCaretPosition() != textfield.getText().length()) {
			try {
				int oldCaretPosition = textfield.getCaretPosition();
				
				String newText = textfield.getText(0, textfield.getCaretPosition());
				newText += specialCharacter;
				newText += textfield.getText(textfield.getCaretPosition(), textfield.getText().length()-textfield.getCaretPosition());
				
				textfield.setText(newText);
				textfield.requestFocusInWindow();
				
				if(oldCaretPosition+Math.floor(textfield.getColumns()/2) < textfield.getText().length()) {
					textfield.setCaretPosition((int) (oldCaretPosition+Math.floor(textfield.getColumns()/2)));
				} else {
					textfield.setCaretPosition(textfield.getText().length());
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			textfield.setText(textfield.getText() + specialCharacter);
			textfield.requestFocusInWindow();
		}
	}
	
	/**
	 * Inserts a given special character into a {@link JTextArea}.
	 * @param textArea the {@link JTextArea} in which the special character will be inserted
	 * @param specialCharacter the special character inserted
	 */
	public static void insertCharacterIntoTextArea(JTextArea textArea, String specialCharacter) {
		if(textArea.getSelectionEnd()-textArea.getSelectionStart() != 0) {
			try {
				int oldCaretPosition = textArea.getCaretPosition();
				
				String newText = textArea.getText(0, textArea.getSelectionStart());
				newText += specialCharacter;
				newText += textArea.getText(textArea.getSelectionEnd(), textArea.getText().length()-textArea.getSelectionEnd());
				
				textArea.setText(newText);
				textArea.requestFocusInWindow();
				
				if(oldCaretPosition+Math.floor(textArea.getColumns()/2) < textArea.getText().length()) {
					textArea.setCaretPosition((int) (oldCaretPosition+Math.floor(textArea.getColumns()/2)));
				} else {
					textArea.setCaretPosition(textArea.getText().length());
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(textArea.getCaretPosition() != textArea.getText().length()) {
			try {
				int oldCaretPosition = textArea.getCaretPosition();
				
				String newText = textArea.getText(0, textArea.getCaretPosition());
				newText += specialCharacter;
				newText += textArea.getText(textArea.getCaretPosition(), textArea.getText().length()-textArea.getCaretPosition());
				
				textArea.setText(newText);
				textArea.requestFocusInWindow();
				
				if(oldCaretPosition+Math.floor(textArea.getColumns()/2) < textArea.getText().length()) {
					textArea.setCaretPosition((int) (oldCaretPosition+Math.floor(textArea.getColumns()/2)));
				} else {
					textArea.setCaretPosition(textArea.getText().length());
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			textArea.setText(textArea.getText() + specialCharacter);
			textArea.requestFocusInWindow();
		}
	}
}
