package dialogues;

import gui.DictionaryMainWindow;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import net.miginfocom.swing.MigLayout;

/**
 * 
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class HelpDialogue extends JDialog {

	private JList<Object> helpTopicList;
	private JScrollPane helpTopicListScrollPane;
	
	private JTextPane helpTextPane;
	private JScrollPane helpTextPaneScrollPane;
	
	private static SimpleAttributeSet paragraph = new SimpleAttributeSet();
    private static SimpleAttributeSet heading1 = new SimpleAttributeSet();
    private static SimpleAttributeSet heading2 = new SimpleAttributeSet();
	
	private Object[] helpListItems = new String[] { "Help",
			"Settings File",
			"Vocable File",
			"Vocable Structure",
			"How to use this program",
			"About"};

	private static final String HELP_TEXT_HELP = "This is the help dialogue. It contains information on how to use the vocable trainer.";
	
	private static final String HELP_TEXT_HOW_TO_USE_THIS_PROGRAM = "" +
			"I will describe how I use this vocable trainer first and then tell you the reasons why I did it like that. " +
			"Of course this is only a suggestion and not a rule.\n" +
			"Mostly I added vocables of the two books about chinese that I have. So I used the chapter part of a vocable " +
			"a lot and when I wanted to learn the vocables of a new chapter, I simply search for the chapter and used the training function of the " +
			"vocable trainer. So far there is surprise, but now comes the important part:\n" +
			"Whenever the training function offers me to click the show translation button, I don't simply click it " +
			"and look at the solution, but I write down the characters that I think are the translation. Only then I " +
			"click the show translation button and see if I am right or wrong. To write them down I use a sheet of paper " +
			"that has lines for the different characters of the chapter on it. On the left side of these line I write down the " +
			"translation into german and most of the time the pinyin. Each time the training function shows me a word in " +
			"german, I try to remember the word in chinese. Then I write it down in the according line. Now one might " +
			"think that I can see the other characters that I already wrote, but for this reason I use a second sheet of " +
			"paper or a book to cover them, so that I don't cheat myself and really have to remember the character before writing. " +
			"If I don't know or remember the character I click the show translation button and set the learn level of this vocable " +
			"to 0 or 1, depending on if I knew at least the pinyin or didn't even know that. After setting the new learn level I " +
			"click the next button.\n" +
			"The reason why I do it like this is, that you won't learn such a language including its characters without " +
			"writing these characters many times, if you are no a very special person with a photographic memory.\n\n";
	
	private static final String HELP_TEXT_SETTINGS_FILE = "The settings file is a file called \"settings\" in the program directory " +
														"of this vocable trainer. It shouldn't be necessary to access the " +
														"settings file, but if you have to for any reason, it is a simple text file.";
	

	private static final String HELP_TEXT_VOCABLE_FILE = "" +
		"The vocabulary file is a file called \"vocabulary\" in the program directory " + 
		"of this vocable trainer. It contains all vocables you entered. Parts of a " +
		"vocable are divided by colons. A vocable is saved like this:\n" +
		"Topic:Chapter:FirstLanguage:SecondLanguage:PhoneticScript:LearnLevel";
	
	private static final String HELP_TEXT_VOCABLE_STRUCTURE_INTRO = "" +
		"A vocable has the following parts:\n\n" +
		"Topic\nChapter\nFirstLanguage\nSecondLanguage\nPhoneticScript\nLearnLevel\n\n" +
		"Generally the vocable trainer does not limit you to using these parts strictly " +
		"as their names indicate. You can enter whatever you want as a string and it " +
		"will be saved in the vocable. Since the vocable file uses a colon (':') as a " +
		"character to divide the parts of a vocable, it is suggested that you don't " +
		"use colons in these strings or as a translation seperator. Other than that you " +
		"are free to enter whatever you like. Now I am going to explain what I actually " +
		"intended the user to enter:\n\n";
	
	private static final String HELP_TEXT_VOCABLE_STRUCTURE_TOPIC_DEFINITION = "" +
		"A topic can be anything from interrogative pronoun to cookie, it is simply " +
		"something you enter to organize your vocables into categories, so that you can " +
		"find and learn your vocables using categories.\n\n";
	
	private static final String HELP_TEXT_VOCABLE_STRUCTURE_CHAPTER_DEFINITION = "" +
		"A chapter is meant to be a name or an abbreviation of a chapter in a book " +
		"or any kind of tutorial from which you got the vocables from you enter in this " +
		"vocable trainer. So if you are learning using a book or tutorial, this will help " +
		"you to learn vocables in the order the book or tutorial want you to learn them.\n\n";
	
	private static final String HELP_TEXT_VOCABLE_STRUCTURE_LANGUAGES_DEFINITION = "" +
		"Languages are the obivous part of the vocable structure. " +
		"The first and second language you enter are your language and the language you want to learn.\n\n";
	
	private static final String HELP_TEXT_VOCABLE_STRUCTURE_PHONETIC_SCRIPT_DEFINITION = "" +
		"A phonetic script is a script that tells you, how to pronounce " +
		"a vocable. In most languages you don't need a phonetic script, " +
		"but there are languages which use characters that don't tell you " +
		"how to pronounce them. For these languages you need a phonetic " +
		"script to learn them, or at least it will make learning them " +
		"incredibly more easy if you have a phonetic script.\n\n";
	
	private static final String HELP_TEXT_VOCABLE_STRUCTURE_LEARN_LEVEL_DEFINITION = "" +
		"A learn level can be a number or a word that indicates how good you " +
		"know the vocable. You could use a word like \"learned\" or \"easy\" " +
		"to describe your learning status of the vocable, but you could also use " +
		"numbers. The vocable trainer does not limit you to use a predefined set " +
		"of words or values, it leaves the choice of what to use to you. I used " +
		"numbers from 0 to 2 to indicate my knowledge about the vocable. If I " +
		"couldn't speak the vocable then I wrote a '0', if I could speak the " +
		"vocable, but not write it, I wrote a '1' and if I knew the vocable very " +
		"well so that I could write and speak it, I wrote a '2'.\n\n";
	
	private static final String HELP_TEXT_WHY = "" +
		"I tested many vocable trainer programs before I decided " +
		"to write my own vocable trainer. The problem was that " +
		"there was always something missing, wrong or put in a " +
		"not useful way in all vocable trainers I tested. Either " +
		"they didn't support unicode characters, which I needed " +
		"to enter my vocables, or they tried to evaluate my answer " +
		"in training mode, so that the program thought I " +
		"was wrong, when I actually entered a correct translation " +
		"that was only different by one character from the one " +
		"the vocable trainer knows or I couldn't really search " +
		"through my already entered vocables to check if I already " +
		"entered a specific vocable and would create a duplicate " +
		"if I entered it again or I couldn't influence the learn " +
		"level of a vocable when I wanted to or the graphical user " +
		"interface was simply not acceptable anymore or ...\n" +
		"As you can see, there are many things that can be wrong, " +
		"missing or unuseful with a vocable trainer." + 
		"I decided, that I would have to write my own vocable trainer " +
		"if I wanted a tool that supports all that I need to learn " +
		"vocables. At first I created a console program that had many " +
		"of the features I wanted to have. The only issue was that I " +
		"had to type in commands which the program then interpreted " +
		"and that takes more time than clicking a button somewhere " +
		"and it doesn't really convince anyone that this vocable " +
		"trainer was actually very useful compared to others that I tried.\n" +
		"So I decided that I should write a vocable trainer which " +
		"offers all the features I needed and offers a graphical " +
		"user interface that looks simple and functional. " +
		"The result is what you see before you.\n" +
		"Some of the features might seem strange to you, " +
		"but believe me, if you learn a language like chinese " +
		"they are going to help you a lot.";
	
	public HelpDialogue(DictionaryMainWindow parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	private void initComponents() {
		defineStyleConstants();
		addComponents();
		addListSelectionListener();
		Helper.addEscapeListener(this);
	}

	private void addComponents() {
		this.setLayout(new MigLayout("wrap 2"));

		//HelpList
		helpTopicList = new JList<Object>(helpListItems);
		helpTopicList.setSelectionMode(JList.VERTICAL);
		helpTopicListScrollPane = new JScrollPane(helpTopicList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		helpTopicListScrollPane.setMinimumSize(new Dimension(200, 300));
		add(helpTopicListScrollPane, "cell 0 0");
		
		//TextPane
		helpTextPane = new JTextPane();
		helpTextPane.setMinimumSize(new Dimension(400, 300));
		helpTextPane.setMaximumSize(new Dimension(400, 300));
		helpTextPane.setEditable(false);
		helpTextPane.setBackground(getBackground());
		
		helpTextPaneScrollPane = new JScrollPane(helpTextPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		helpTextPaneScrollPane.setMinimumSize(new Dimension(400, 300));
		add(helpTextPaneScrollPane, "cell 1 0");
		
		//Initial Text
		helpTextPane.setText("");
		addHelpText("Help\n\n", heading1);
		addHelpText(HELP_TEXT_HELP, paragraph);
	}

	private void addListSelectionListener() {
		helpTopicList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (helpTopicList.getSelectedIndex() != -1) {
					showHelpContent((String) helpTopicList.getSelectedValue());
				}
			}
		});
	}

	private void showHelpContent(String helpTopic) {
		if(helpTopic.equals("Help")) {
			helpTextPane.setText("");
			addHelpText("Help\n\n", heading1);
			addHelpText(HELP_TEXT_HELP, paragraph);
			
		} else if(helpTopic.equals("How to use this program")) {
			helpTextPane.setText("");
			addHelpText("How to use this vocable trainer\n\n", heading1);
			addHelpText(HELP_TEXT_HOW_TO_USE_THIS_PROGRAM, paragraph);
			
		} else if(helpTopic.equals("Settings File")) {
			helpTextPane.setText("");
			addHelpText("The Settings File\n\n", heading1);
			addHelpText(HELP_TEXT_SETTINGS_FILE, paragraph);
			
		} else if(helpTopic.equals("Vocable File")) {
			helpTextPane.setText("");
			addHelpText("The Vocable File\n\n", heading1);
			addHelpText(HELP_TEXT_VOCABLE_FILE, paragraph);
			
		} else if(helpTopic.equals("Vocable Structure")) {
			helpTextPane.setText("");
			addHelpText("Vocable Structure\n\n", heading1);
			addHelpText(HELP_TEXT_VOCABLE_STRUCTURE_INTRO, paragraph);
			
			addHelpText("Topic\n\n", heading2);
			addHelpText(HELP_TEXT_VOCABLE_STRUCTURE_TOPIC_DEFINITION, paragraph);
			
			addHelpText("Chapter\n\n", heading2);
			addHelpText(HELP_TEXT_VOCABLE_STRUCTURE_CHAPTER_DEFINITION, paragraph);
			
			addHelpText("Languages\n\n", heading2);
			addHelpText(HELP_TEXT_VOCABLE_STRUCTURE_LANGUAGES_DEFINITION, paragraph);
			
			addHelpText("Phonetic Script\n\n", heading2);
			addHelpText(HELP_TEXT_VOCABLE_STRUCTURE_PHONETIC_SCRIPT_DEFINITION, paragraph);
			
			addHelpText("Learn Level\n\n", heading2);
			addHelpText(HELP_TEXT_VOCABLE_STRUCTURE_LEARN_LEVEL_DEFINITION, paragraph);
			
		} else if(helpTopic.equals("About")) {
			helpTextPane.setText("");
			addHelpText("About this Vocable Trainer\n\n", heading1);
			addHelpText(HELP_TEXT_WHY, paragraph);
		}
	}
	
	
	private void addHelpText(String addedText, AttributeSet attrSet) {
		try {
			helpTextPane.getStyledDocument().setParagraphAttributes(helpTextPane.getDocument().getLength(), addedText.length(), attrSet, true);
			helpTextPane.getStyledDocument().insertString(helpTextPane.getDocument().getLength(), addedText, attrSet);
			helpTextPane.setCaretPosition(0);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void defineStyleConstants() {
		StyleConstants.setFontSize(paragraph, 12);
		StyleConstants.setAlignment(paragraph, StyleConstants.ALIGN_JUSTIFIED);
		
		StyleConstants.setAlignment(heading1, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontSize(heading1, 16);
		StyleConstants.setBold(heading1, true);
		
		StyleConstants.setAlignment(heading2, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontSize(heading2, 14);
		StyleConstants.setBold(heading2, true);
	}
}