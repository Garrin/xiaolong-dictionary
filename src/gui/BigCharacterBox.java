/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import listener.search.SearchListener;
import listener.vocables.VocableChangedListener;
import listener.vocables.VocableDeletedListener;
import listener.vocabletable.VocableTableActionsListener;
import net.miginfocom.swing.MigLayout;
import dictionary.Settings;
import dictionary.Vocable;
import factories.ObserveableFactory;

/**
 * 
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class BigCharacterBox extends JPanel implements SearchListener, VocableChangedListener, VocableDeletedListener, VocableTableActionsListener {

	// private DictionaryMainWindow window;

	private final int XSIZE = 125;
	private final int YSIZE = 125;

	private JButton previousButton;
	private JButton nextButton;
	private JTextPane textPane;

	private String characters = "";
	private int positionOfShownCharacter = 0;
	private String ignoredCharacters;

	// Style stuff
	private static Style mainStyle;
	private StyleContext sc;
	private StyledDocument doc;
	
	private Vocable currentlyDisplayedVocable;

	public BigCharacterBox(String ignoredCharacters) {
		this.ignoredCharacters = ignoredCharacters;
		addComponents();
		addBorder();

		registerListeners();
		addActionListeners();
	}

	private void registerListeners() {
		ObserveableFactory.getSearchObservable().registerListener(this);
		ObserveableFactory.getVocablesObserveable().registerVocableChangedListener(this);
		ObserveableFactory.getVocablesObserveable().registerVocableDeletedListener(this);
		DictionaryMainWindow.vocableTable.registerVocableTableActionsListener(this);
	}

	private void addBorder() {
		TitledBorder bigCharacterBoxTitledBorder;
		bigCharacterBoxTitledBorder = BorderFactory
				.createTitledBorder("Character");
		bigCharacterBoxTitledBorder.setTitleJustification(TitledBorder.CENTER);
		textPane.setBorder(bigCharacterBoxTitledBorder);
	}

	private void addComponents() {
		setLayout(new MigLayout("wrap 3"));

		sc = new StyleContext();
		doc = new DefaultStyledDocument(sc);
		textPane = new JTextPane(doc);

		// Create and add the main document style
		Style defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
		mainStyle = sc.addStyle("MainStyle", defaultStyle);

		StyleConstants.setFontSize(mainStyle, Settings.bigCharacterBoxOptions_fontSize);
		StyleConstants.setAlignment(mainStyle, StyleConstants.ALIGN_CENTER);
		System.out.println("Setting Font: " + Settings.bigCharacterBoxOptions_font);
		StyleConstants.setFontFamily(mainStyle,	Settings.bigCharacterBoxOptions_font);
		
		StyleConstants.setLeftIndent(mainStyle, 0);
		StyleConstants.setRightIndent(mainStyle, 0);
		StyleConstants.setFirstLineIndent(mainStyle, 0);

		textPane.setText("_");
		doc.setCharacterAttributes(0, doc.getLength(), mainStyle, true);
		textPane.setText("");
		
		if (Settings.BIG_CHARACTER_BOX_DEFAULT_FONTWEIGHT.equals("bold")) {
			StyleConstants.setBold(mainStyle, true);
		} else if (Settings.BIG_CHARACTER_BOX_DEFAULT_FONTWEIGHT
				.equals("italic")) {
			StyleConstants.setItalic(mainStyle, true);
		}

		// Set the style
		doc.setLogicalStyle(0, mainStyle);
		
		textPane.setEditable(false);
		textPane.setBackground(getBackground());
		System.out.println(getBackground().toString());
		// textPane.setFont(new Font(Settings.bigCharacterBoxOptions_font,
		// Font.PLAIN, Settings.bigCharacterBoxOptions_fontSize));
		textPane.setMinimumSize(new Dimension(XSIZE, YSIZE));
		textPane.setSize(new Dimension(XSIZE, YSIZE));
		textPane.setText("");

		add(textPane, "span 3");

		JPanel buttonPanel = new JPanel();
		GridLayout gridLayout = new GridLayout(1, 2);
		gridLayout.setVgap(5);
		buttonPanel.setLayout(gridLayout);

		previousButton = new JButton("<<");
		buttonPanel.add(getPreviousButton());
		nextButton = new JButton(">>");
		buttonPanel.add(getNextButton());

		add(buttonPanel);
	}

	private void addActionListeners() {
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

	private void previousButtonActionPerformed() {
		if (!characters.equals("") && characters != null) {
			if (positionOfShownCharacter > 0) {
				this.positionOfShownCharacter--;
				while (ignoredCharacters
						.contains(characters.substring(
								positionOfShownCharacter,
								positionOfShownCharacter + 1))) {
					this.positionOfShownCharacter--;
				}
				textPane.setText("");
				addText(characters.substring(positionOfShownCharacter,
						positionOfShownCharacter + 1), mainStyle);
				// textPane.setText(characters.substring(positionOfShownCharacter,
				// positionOfShownCharacter+1));
			}
		}

	}

	private void nextButtonActionPerformed() {
		if (!characters.equals("") && characters != null) {
			int nextCharacterPosition = positionOfShownCharacter;
			for (int i = positionOfShownCharacter + 1; i < characters.length(); i++) {
				if (!ignoredCharacters.contains(String.valueOf(characters
						.charAt(i)))) {
					nextCharacterPosition = i;
					break;
				}
			}
			textPane.setText("");
			addText(String.valueOf(characters.charAt(nextCharacterPosition)),
					mainStyle);
			positionOfShownCharacter = nextCharacterPosition;
		}

	}

	public void setCharacters(String characters) {
		this.characters = characters;
		this.positionOfShownCharacter = 0;
		textPane.setText("");
		if(characters.length() != 0) {
			addText(characters.substring(positionOfShownCharacter, positionOfShownCharacter + 1), mainStyle);	
		}
	}
	
	/**
	 * This method sets a {@link Vocable} of which the second language is shown in the {@link BigCharacterBox}.
	 * @param vocable the vocable, which will be set
	 */
	public void setVocable(Vocable vocable) {
		this.characters = vocable.getSecondLanguage();
		this.positionOfShownCharacter = 0;
		textPane.setText("");
		if(characters.length() != 0) {
			addText(characters.substring(positionOfShownCharacter, positionOfShownCharacter + 1), mainStyle);	
		}
	}

	/**
	 * @return the previousButton
	 */
	public JButton getPreviousButton() {
		return previousButton;
	}

	/**
	 * @return the nextButton
	 */
	public JButton getNextButton() {
		return nextButton;
	}

	/**
	 * @return the textPane
	 */
	public JTextPane getTextPane() {
		return textPane;
	}

	public void pressNext() {
		// System.out.println("NEXT");
		nextButtonActionPerformed();
	}

	public void pressPrevious() {
		// System.out.println("RIGHT");
		previousButtonActionPerformed();
	}

	public void updateFont(Font font) {
		StyleConstants.setFontSize(mainStyle, font.getSize());
		StyleConstants.setFontFamily(mainStyle, font.getFamily());
		StyleConstants.setBold(mainStyle, font.isBold());
		StyleConstants.setItalic(mainStyle, font.isItalic());

		StyleConstants.setFontFamily(mainStyle, font.getFontName());
		doc.setCharacterAttributes(0, doc.getLength(), mainStyle, true);
	}

	public void updateIgnoredCharacters(String ignoredCharacters) {
		this.ignoredCharacters = ignoredCharacters;
	}

	/**
	 * Adds text in a way that the text is styled as definded in {@link AttributeSet} attrSet.
	 * @param addedText the added text
	 * @param attrSet the AttributeSet
	 */
	private void addText(String addedText, AttributeSet attrSet) {
		try {
			doc.setParagraphAttributes(0, addedText.length(), attrSet, true);
			doc.insertString(0, addedText, attrSet);
			textPane.setCaretPosition(0);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void searchPerformedAction() {
		textPane.setText("");
	}

	@Override
	public void vocableChangedActionPerformed(Vocable oldVocable, Vocable newVocable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectedVocableInVocableTable(Vocable vocable) {
		currentlyDisplayedVocable = vocable;
		setCharacters(vocable.getSecondLanguage());
	}

	@Override
	public void vocableDeletedActionPerformed(Vocable vocable) {
		if(currentlyDisplayedVocable != null) {
			if(	vocable.getFirstLanguage().equals(currentlyDisplayedVocable.getFirstLanguage()) &&
				vocable.getPhoneticScript().equals(currentlyDisplayedVocable.getPhoneticScript()) &&
				vocable.getSecondLanguage().equals(currentlyDisplayedVocable.getSecondLanguage())
			) {
				System.out.println("It's the shown Vocable being deleted! :O");
			}
		} else {
			System.out.println("currentlyDisplayedVocable is null");
		}
	}
}