package dictionary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

import factories.ObserveableFactory;
import listener.settings.VocabularyLanguagesChangeListener;

/**
 *
 * @author xiaolong
 */
public class Settings implements VocabularyLanguagesChangeListener {
	private static String settingsFileFileName = "settings";
	
	public static boolean firstTimeRun;
	
	public static boolean trainingOptions_firstToSecond;
	public static boolean trainingOptions_secondToFirst;
	public static boolean trainingOptions_phoneticScript_shown;
	public static String trainingOptions_font;
	public static final String TRAINING_OPTIONS_DEFAULT_FONT = "Dialog";
	
	public static final String ADD_VOCABLE_DEFAULT_FONT = "Dialog";
	public static String addVocable_font = "Dialog";

	public static boolean searchOptions_firstLanguageActivated;
	public static boolean searchOptions_phoneticScriptActivated;
	public static boolean searchOptions_secondLanguageActivated;
	public static boolean searchOptions_topicActivated;
	public static boolean searchOptions_matchCaseActivated;
	public static boolean searchOptions_chapterActivated;
	public static boolean searchOptions_learnLevelActivated;
	public static boolean searchOptions_exactMatchActivated;
	public static boolean searchOptions_negateSearchActivated;

	public static final String VOCABLE_OPTIONS_DEFAULT_TRANSLATIONS_SEPERATOR = "/";
	public static String vocableOptions_translationsSeperator = "/";
	
	public static String languageOptions_firstLanguageName;
	public static String languageOptions_phoneticScriptName;
	public static String languageOptions_secondLanguageName;
	
	public static String languageOptions_previousFirstLanguage = "FirstLanguage";
	public static String languageOptions_previousPhoneticScript = "PhoneticScript";
	public static String languageOptions_previousSecondLanguage = "SecondLanguage";
	
	public static final String FIRST_LANGUAGE_DEFAULT = "English";
	public static final String PHONETIC_SCRIPT_DEFAULT = "Pinyin";
	public static final String SECOND_LANGUAGE_DEFAULT = "Chinese";
	
	public static final String VOCABLE_TABLE_DEFAULT_FONT = "Dialog";
	public static final int VOCABLE_TABLE_DEFAULT_FONTSIZE = 12;
	public static final String VOCABLE_TABLE_DEFAULT_FONTWEIGHT = "plain";
	public static String vocableTable_font;
	public static int vocableTable_fontSize;
	public static String vocableTable_fontWeight;

	public static final String BIG_CHARACTER_BOX_DEFAULT_FONT = "Dialog";
	public static final int BIG_CHARACTER_BOX_DEFAULT_FONTSIZE = 100;
	public static final String BIG_CHARACTER_BOX_DEFAULT_FONTWEIGHT = "plain";
	public static final String BIG_CHARACTER_BOX_DEFAULT_IGNORE = "/";
	public static String bigCharacterBoxOptions_font;
	public static int bigCharacterBoxOptions_fontSize;
	public static String bigCharacterBoxOptions_fontWeight;
	public static String bigCharacterBoxOptions_ignored_characters;
	
	public static boolean showSaveVocabularyBeforeExitConfirmationDialog;
	public static boolean showExitConfirmationDialog;
	public static boolean saveVocabularyOnExit;
	
	public static final String DEFAULT_VOCABLE_FILE_LOCATION = "vocabulary";
	public static String vocableFileLocation = "";

	public static String lastSearchTerm = "";
	
	public static int MINIMUM_WINDOW_HEIGHT = 300;
	public static int MINIMUM_WINDOW_WIDTH = 600;

	public static int WINDOW_HEIGHT = 300;
	public static int WINDOW_WIDTH = 600;
	
	public static String specialCharacters;
	public static String specialCharactersOptionsDialogueTextFieldFont = "Dialog";
	public static int specialCharactersOptionsDialogueTextFieldFontSize = 20;
	
	public static int searchHistoryMaximumLength;
	public static String searchHistory;
	
	/*
	public static int firstLanguageColumnWidth = 0;
	public static int phoneticScriptColumnWidth = 0;
	public static int secondLanguageColumnWidth = 0;
	 */
	
	public Settings() {
		loadSettings(settingsFileFileName);
		testSettings();
		
		//Register the Settings object as a listener for vocabulary language name changes
		ObserveableFactory.getVocabularyLanguagesObserveable().registerListener(this);
	}
	
	public static void saveSettings() {		
		try {
			//FileWriter out = new FileWriter(settingsFilename);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(settingsFileFileName), "UTF-8"));
			
			String line;
			
			line = "WINDOW_WIDTH=" + Settings.WINDOW_WIDTH + System.getProperty("line.separator");
			out.write(line);
			line = "WINDOW_HEIGHT=" + Settings.WINDOW_HEIGHT + System.getProperty("line.separator");
			out.write(line);
			/*
			line = "FIRST_LANGUAGE_COLUMN_WIDTH=" + Settings.firstLanguageColumnWidth + System.getProperty("line.separator");
			fw.write(line);
			line = "PHONETIC_SCRIPT_COLUMN_WIDTH=" + Settings.phoneticScriptColumnWidth + System.getProperty("line.separator");
			fw.write(line);
			line = "SECOND_LANGUAGE_COLUMN_WIDTH=" + Settings.secondLanguageColumnWidth + System.getProperty("line.separator");
			fw.write(line);
			*/
			line = "FIRST_TIME_RUN=" + Boolean.toString(firstTimeRun) + System.getProperty("line.separator");
			out.write(line);
			
			line = "VOCABLE_OPTION_TRANSLATION_SEPERATOR=" + vocableOptions_translationsSeperator + System.getProperty("line.separator");
			out.write(line);
			
			line = "SEARCH_OPTION_FIRST_LANGUAGE_ACTIVATED=" + Boolean.toString(searchOptions_firstLanguageActivated) + System.getProperty("line.separator");
			out.write(line);
			line = "SEARCH_OPTION_PHONETIC_SCRIPT_ACTIVATED=" + Boolean.toString(searchOptions_phoneticScriptActivated) + System.getProperty("line.separator");
			out.write(line);
			line = "SEARCH_OPTION_SECOND_LANGUAGE_ACTIVATED=" + Boolean.toString(searchOptions_secondLanguageActivated) + System.getProperty("line.separator");
			out.write(line);
			line = "SEARCH_OPTION_TOPIC_ACTIVATED=" + Boolean.toString(searchOptions_topicActivated) + System.getProperty("line.separator");
			out.write(line);
			line = "SEARCH_OPTION_CHAPTER_ACTIVATED=" + Boolean.toString(searchOptions_chapterActivated) + System.getProperty("line.separator");
			out.write(line);
			line = "SEARCH_OPTION_LEARN_LEVEL_ACTIVATED=" + Boolean.toString(searchOptions_learnLevelActivated) + System.getProperty("line.separator");
			out.write(line);
			line = "SEARCH_OPTION_MATCH_CASE_ACTIVATED=" + Boolean.toString(searchOptions_matchCaseActivated) + System.getProperty("line.separator");
			out.write(line);
			line = "SEARCH_OPTION_EXACT_MATCH_ACTIVATED=" + Boolean.toString(searchOptions_exactMatchActivated) + System.getProperty("line.separator");
			out.write(line);
			line = "SEARCH_OPTION_NEGATE_SEARCH_ACTIVATED=" + Boolean.toString(searchOptions_negateSearchActivated) + System.getProperty("line.separator");
			out.write(line);
			line = "LAST_SEARCH_TERM=" + lastSearchTerm + "-" + System.getProperty("line.separator");
			out.write(line);
			
			
			line = "LANGUAGE_OPTIONS_FIRST_LANGUAGE=" + languageOptions_firstLanguageName + System.getProperty("line.separator");
			out.write(line);
			line = "LANGUAGE_OPTIONS_PHONETIC_SCRIPT=" + languageOptions_phoneticScriptName + System.getProperty("line.separator");
			out.write(line);
			line = "LANGUAGE_OPTIONS_SECOND_LANGUAGE=" + languageOptions_secondLanguageName + System.getProperty("line.separator");
			out.write(line);
			line = "LANGUAGE_OPTIONS_OLD_FIRST_LANGUAGE=" + languageOptions_previousFirstLanguage + System.getProperty("line.separator");
			out.write(line);
			line = "LANGUAGE_OPTIONS_OLD_PHONETIC_SCRIPT=" + languageOptions_previousPhoneticScript + System.getProperty("line.separator");
			out.write(line);
			line = "LANGUAGE_OPTIONS_OLD_SECOND_LANGUAGE=" + languageOptions_previousSecondLanguage + System.getProperty("line.separator");
			out.write(line);
			
			line = "VOCABLE_TABLE_OPTIONS_FONT=" + vocableTable_font + System.getProperty("line.separator");
			out.write(line);
			line = "VOCABLE_TABLE_OPTIONS_FONT_SIZE=" + vocableTable_fontSize + System.getProperty("line.separator");
			out.write(line);
			line = "VOCABLE_TABLE_OPTIONS_FONT_WEIGHT=" + vocableTable_fontWeight + System.getProperty("line.separator");
			out.write(line);
			
			line = "TRAINING_OPTIONS_FIRST_TO_SECOND=" + Boolean.toString(trainingOptions_firstToSecond) + System.getProperty("line.separator");
			out.write(line);
			line = "TRAINING_OPTIONS_SECOND_TO_FIRST=" + Boolean.toString(trainingOptions_secondToFirst) + System.getProperty("line.separator");
			out.write(line);
			line = "TRAINING_OPTIONS_PHONETIC_SCRIPT=" + Boolean.toString(trainingOptions_phoneticScript_shown) + System.getProperty("line.separator");
			out.write(line);
			line = "TRAINING_OPTIONS_FONT=" + trainingOptions_font + System.getProperty("line.separator");
			out.write(line);
			
			line = "BIG_CHARACTER_BOX_OPTIONS_FONT=" + bigCharacterBoxOptions_font + System.getProperty("line.separator");
			out.write(line);
			line = "BIG_CHARACTER_BOX_OPTIONS_FONT_SIZE=" + bigCharacterBoxOptions_fontSize + System.getProperty("line.separator");
			out.write(line);
			line = "BIG_CHARACTER_BOX_OPTIONS_FONT_WEIGHT=" + bigCharacterBoxOptions_fontWeight + System.getProperty("line.separator");
			out.write(line);
			
			if(bigCharacterBoxOptions_ignored_characters.isEmpty())
				line = "BIG_CHARACTER_BOX_OPTIONS_IGNORE=none" + System.getProperty("line.separator");
			else
				line = "BIG_CHARACTER_BOX_OPTIONS_IGNORE=" + bigCharacterBoxOptions_ignored_characters + System.getProperty("line.separator");
			out.write(line);
			
			line = "SHOW_SAVE_VOCABULARY_BEFORE_EXIT_CONFIRMATION_DIALOG=" + showSaveVocabularyBeforeExitConfirmationDialog + System.getProperty("line.separator");
			out.write(line);
			line = "SHOW_EXIT_CONFIRMATION_DIALOG=" + showExitConfirmationDialog + System.getProperty("line.separator");
			out.write(line);
			line = "SAVE_VOCABULARY_ON_EXIT=" + saveVocabularyOnExit + System.getProperty("line.separator");
			out.write(line);
			
			line = "VOCABLE_FILE_LOCATION=" + vocableFileLocation + System.getProperty("line.separator");
			out.write(line);
			
			line = "SPECIAL_CHARACTERS=" + specialCharacters + System.getProperty("line.separator");
			out.write(line);
			
			line = "SEARCH_HISTORY_MAXIMUM_LENGTH=" + searchHistoryMaximumLength + System.getProperty("line.separator");
			out.write(line);
			
			line = "SEARCH_HISTORY=";
			for(SearchEntry entry : SearchHistory.getSearchEntryList()) {
				line += entry.searchTerm + ":";
				line += entry.firstLanguageSelected + ":";
				line += entry.phoneticScriptSelected + ":";
				line += entry.secondLanguageSelected + ":";
				line += entry.topicSelected + ":";
				line += entry.chapterSelecter + ":";
				line += entry.learnLevelSelected + ":";
				line += entry.caseSensitiveSelected + ":";
				line += entry.exactMatchSelected + ":";
				line += entry.negateSearchSelected+ ":";
				line += ",";
			}
			out.write(line);
			
			out.close();
		}
		catch(IOException e) {
			System.err.println("Could not save settings in " + settingsFileFileName + ".");
		}
	}
	
	public static void loadSettings(String settingsFileFileName) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(settingsFileFileName), "UTF-8"));
			
			String line;
			while ((line = in.readLine()) != null) {
				//System.out.println(":::"+line);
				StringTokenizer tokenizer = new StringTokenizer(line, "=");
				for(int i = 0; i < tokenizer.countTokens(); i++){

					String name = tokenizer.nextToken();
					String value = tokenizer.nextToken();
					
					//WINDOW SIZE
					if(name.equals("WINDOW_HEIGHT")) {
						Settings.WINDOW_HEIGHT = Integer.parseInt(value);
					}
					if(name.equals("WINDOW_WIDTH")) {
						Settings.WINDOW_WIDTH = Integer.parseInt(value);
					}
					
					/*
					//COLUMN WIDTHS
					if(name.equals("FIRST_LANGUAGE_COLUMN_WIDTH")) {
						Settings.firstLanguageColumnWidth = Integer.parseInt(value);
					}
					if(name.equals("PHONETIC_SCRIPT_COLUMN_WIDTH")) {
						Settings.phoneticScriptColumnWidth = Integer.parseInt(value);
					}
					if(name.equals("SECOND_LANGUAGE_COLUMN_WIDTH")) {
						Settings.secondLanguageColumnWidth = Integer.parseInt(value);
					}
					*/
					
					//FIRST TIME RUN
					if(name.equals("FIRST_TIME_RUN") && value.equals("true")) {
						Settings.firstTimeRun = true;
					}
					
					if(name.equals("VOCABLE_OPTION_TRANSLATION_SEPERATOR")) {
						Settings.vocableOptions_translationsSeperator = value;
					}
					
					//SEARCH
					if(name.equals("SEARCH_OPTION_FIRST_LANGUAGE_ACTIVATED") && value.equals("true")) {
						Settings.searchOptions_firstLanguageActivated = true;
					}
					if(name.equals("SEARCH_OPTION_PHONETIC_SCRIPT_ACTIVATED") && value.equals("true")) {
						Settings.searchOptions_phoneticScriptActivated = true;
					}
					if(name.equals("SEARCH_OPTION_SECOND_LANGUAGE_ACTIVATED") && value.equals("true")) {
						Settings.searchOptions_secondLanguageActivated = true;
					}					
					if(name.equals("SEARCH_OPTION_TOPIC_ACTIVATED") && value.equals("true")) {
						Settings.searchOptions_topicActivated = true;
					}
					if(name.equals("SEARCH_OPTION_CHAPTER_ACTIVATED") && value.equals("true")) {
						Settings.searchOptions_chapterActivated = true;
					}
					if(name.equals("SEARCH_OPTION_LEARN_LEVEL_ACTIVATED") && value.equals("true")) {
						Settings.searchOptions_learnLevelActivated = true;
					}
					if(name.equals("SEARCH_OPTION_MATCH_CASE_ACTIVATED") && value.equals("true")) {
						Settings.searchOptions_matchCaseActivated = true;
					}
					if(name.equals("SEARCH_OPTION_EXACT_MATCH_ACTIVATED") && value.equals("true")) {
						Settings.searchOptions_exactMatchActivated = true;
					}
					if(name.equals("SEARCH_OPTION_NEGATE_SEARCH_ACTIVATED") && value.equals("true")) {
						Settings.searchOptions_negateSearchActivated = true;
					}
					if(name.equals("LAST_SEARCH_TERM")) {
						Settings.lastSearchTerm = value;
					}
					
					//LANGUAGE
					if(name.equals("LANGUAGE_OPTIONS_FIRST_LANGUAGE")) {
						Settings.languageOptions_firstLanguageName = value;
					}
					if(name.equals("LANGUAGE_OPTIONS_PHONETIC_SCRIPT")) {
						Settings.languageOptions_phoneticScriptName = value;
					}
					if(name.equals("LANGUAGE_OPTIONS_SECOND_LANGUAGE")) {
						Settings.languageOptions_secondLanguageName = value;
					}
					if(name.equals("LANGUAGE_OPTIONS_OLD_FIRST_LANGUAGE")) {
						Settings.languageOptions_previousFirstLanguage = value;
					}
					if(name.equals("LANGUAGE_OPTIONS_OLD_PHONETIC_SCRIPT")) {
						Settings.languageOptions_previousPhoneticScript = value;
					}
					if(name.equals("LANGUAGE_OPTIONS_OLD_SECOND_LANGUAGE")) {
						Settings.languageOptions_previousSecondLanguage = value;
					}
					
					//VOCABLE TABLE
					if(name.equals("VOCABLE_TABLE_OPTIONS_FONT")) {
						Settings.vocableTable_font = value;
					}
					if(name.equals("VOCABLE_TABLE_OPTIONS_FONT_SIZE")) {
						Settings.vocableTable_fontSize = Integer.parseInt(value);
					}
					if(name.equals("VOCABLE_TABLE_OPTIONS_FONT_WEIGHT")) {
						Settings.vocableTable_fontWeight = value;
					}
					
					//TRAINING
					if(name.equals("TRAINING_OPTIONS_FIRST_TO_SECOND") && value.equals("true")) {
						Settings.trainingOptions_firstToSecond = true;
					}
					if(name.equals("TRAINING_OPTIONS_SECOND_TO_FIRST") && value.equals("true")) {
						Settings.trainingOptions_secondToFirst = true;
					}
					if(name.equals("TRAINING_OPTIONS_PHONETIC_SCRIPT") && value.equals("true")) {
						Settings.trainingOptions_phoneticScript_shown = true;
					}
					if(name.equals("TRAINING_OPTIONS_FONT")) {
						Settings.trainingOptions_font = value;
					}
					
					//BIG CHARACTER BOX
					if(name.equals("BIG_CHARACTER_BOX_OPTIONS_FONT")) {
						Settings.bigCharacterBoxOptions_font = value;
					}
					if(name.equals("BIG_CHARACTER_BOX_OPTIONS_FONT_SIZE")) {
						Settings.bigCharacterBoxOptions_fontSize = Integer.parseInt(value);
					}
					if(name.equals("BIG_CHARACTER_BOX_OPTIONS_FONT_WEIGHT")) {
						Settings.bigCharacterBoxOptions_fontWeight = value;
					}
					if(name.equals("BIG_CHARACTER_BOX_OPTIONS_IGNORE")) {
						Settings.bigCharacterBoxOptions_ignored_characters = value;
					}
					
					//DIALOGS
					if(name.equals("SHOW_SAVE_VOCABULARY_BEFORE_EXIT_CONFIRMATION_DIALOG") && value.equals("true")) {
						showSaveVocabularyBeforeExitConfirmationDialog = true;
					}
					if(name.equals("SHOW_EXIT_CONFIRMATION_DIALOG") && value.equals("true")) {
						showExitConfirmationDialog = true;
					}
					if(name.equals("SAVE_VOCABULARY_ON_EXIT")) {
						saveVocabularyOnExit = value.equals("true");
					}
					
					if(name.equals("VOCABLE_FILE_LOCATION")) {
						vocableFileLocation = value;
					}
					
					//SPECIAL CHARACTERS
					if(name.equals("SPECIAL_CHARACTERS")) {
						Settings.specialCharacters = value;
					}
					
					//SEARCH HISTORY
					if(name.equals("SEARCH_HISTORY_MAXIMUM_LENGTH")) {
						Settings.searchHistoryMaximumLength = Integer.parseInt(value, 10);
					}
					
					if(name.equals("SEARCH_HISTORY")) {
						Settings.searchHistory = value;
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Error while loading settings file.");
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("IOException while reading settings file.");
			}
		}
	}

	private void testSettings() {
		System.out.println("windowWidth: " + Settings.WINDOW_WIDTH);
		System.out.println("windowHeight: " + Settings.WINDOW_HEIGHT);
		/*
		System.out.println("firstLanguageColumnWidth: " + Settings.firstLanguageColumnWidth);
		System.out.println("phoneticScriptColumnWidth: " + Settings.phoneticScriptColumnWidth);
		System.out.println("secondLanguageColumnWidth: " + Settings.secondLanguageColumnWidth);
		*/
		System.out.println("firstTimeRun: " + firstTimeRun);
		
		System.out.println("vocableOptions_translationSeperator: " + vocableOptions_translationsSeperator);
		
		System.out.println("searchOptions_firstLanguageActivated: " + searchOptions_firstLanguageActivated);
		System.out.println("searchOptions_phoneticScriptActivated: " + searchOptions_phoneticScriptActivated);
		System.out.println("searchOptions_secondLanguageActivated: " + searchOptions_secondLanguageActivated);
		System.out.println("searchOptions_topicActivated: " + searchOptions_topicActivated);
		System.out.println("searchOptions_chapterActivated: " + searchOptions_chapterActivated);
		System.out.println("searchOptions_learnLevelActivated: " + searchOptions_learnLevelActivated);
		System.out.println("searchOptions_matchCaseActivated: " + searchOptions_matchCaseActivated);
		System.out.println("searchOptions_exactMatchActivated: " + searchOptions_exactMatchActivated);
		System.out.println("searchOptions_negateSearchActivated: " + searchOptions_negateSearchActivated);
		System.out.println("lastSearchTerm: " + Settings.lastSearchTerm);
		
		System.out.println("languageOptions_firstLanguageName: " + languageOptions_firstLanguageName);
		System.out.println("languageOptions_phoneticScriptName: " + languageOptions_phoneticScriptName);
		System.out.println("languageOptions_secondLanguageName: " + languageOptions_secondLanguageName);
		
		System.out.println("vocableTable_font: " + vocableTable_font);
		System.out.println("vocableTable_fontSize: " + vocableTable_fontSize);
		System.out.println("vocableTable_fontWeight: " + vocableTable_fontWeight);
		
		System.out.println("trainingOptions_firstToSecond: " + trainingOptions_firstToSecond);
		System.out.println("trainingOptions_phoneticScript_shown: " + trainingOptions_phoneticScript_shown);
		System.out.println("trainingOptions_secondToFirst: " + trainingOptions_secondToFirst);
		System.out.println("trainingOptions_font: " + trainingOptions_font);
		
		System.out.println("bigCharacterBoxOptions_font: " + bigCharacterBoxOptions_font);
		System.out.println("bigCharacterBoxOptions_fontSize: " + bigCharacterBoxOptions_fontSize);
		System.out.println("bigCharacterBoxOptions_fontWeight: " + bigCharacterBoxOptions_fontWeight);
		
		System.out.println("bigCharacterBoxOptions_ignored_characters: " + bigCharacterBoxOptions_ignored_characters);
		
		System.out.println("showSaveVocabularyBeforeExitConfirmationDialog: " + showSaveVocabularyBeforeExitConfirmationDialog);
		System.out.println("showExitConfirmationDialog: " + showExitConfirmationDialog);
		System.out.println("saveVocabularyOnExit: " + saveVocabularyOnExit);
		
		System.out.println("vocableFileLocation: " + vocableFileLocation);
		
		System.out.println("specialCharacters: " + specialCharacters);
		System.out.println("searchHistoryMaximumLength: " + searchHistoryMaximumLength);
	}

	@Override
	public void changeVocabularyLanguages(String newFirstLanguage, String newPhoneticScript, String newSecondLanguage) {
		//Save previous language names for later use
		//Example: Vocable Table Headers still have the previous language names and need to be searched through for an identifying unique name, which is the previous language name
		Settings.languageOptions_previousFirstLanguage = Settings.languageOptions_firstLanguageName;
		Settings.languageOptions_previousPhoneticScript = Settings.languageOptions_phoneticScriptName;
		Settings.languageOptions_previousSecondLanguage = Settings.languageOptions_secondLanguageName;
		
		Settings.languageOptions_firstLanguageName = newFirstLanguage;
		Settings.languageOptions_phoneticScriptName = newPhoneticScript;
		Settings.languageOptions_secondLanguageName = newSecondLanguage;
		
		Settings.saveSettings();
	}
	
	public static String getSettingsFileFileName() {
		return settingsFileFileName;
	}
}
