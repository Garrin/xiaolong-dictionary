package dictionary;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.SwingUtilities;

import manager.VocableManager;

import com.alee.laf.WebLookAndFeel;
import com.alee.managers.language.LanguageManager;

import dialogues.FirstRunHelperDialogue;
import factories.FrameFactory;
import factories.ObserveableFactory;
import gui.SearchBox;


public class Dictionary {

	public static final String APPLICATION_NAME = "Xiaolong Dictionary";
	public static final String VERSION = "1.0";
//	private DictionaryMainWindow dictionaryMainWindow;
	private FirstRunHelperDialogue firstRunHelper;
	public final Settings settings;
	//private static ArrayList<Vocable> vocableList = new ArrayList<Vocable>();
	//public static ArrayList<Vocable> searchResult = new ArrayList<Vocable>();
	public static String vocableFilename;
	public static String searchResultFilename;
	public static boolean vocabularyIsSavedToFile = true;

	/**
	 * Creates a new Xiaolong Dictionary
	 * @param args
	 */
	public static void main(String[] args) {
		LanguageManager.DEFAULT = LanguageManager.ENGLISH;
		Dictionary.setLookAndFeel();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
//				try {
//					@SuppressWarnings("unused")
//					Dictionary dictionary = new Dictionary();
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
				new Dictionary();
			}
		});
	}

	public Dictionary() {
		settings = new Settings();
		vocableFilename = Settings.vocableFileLocation;
		FrameFactory.getDictionaryMainWindow(); //Create the instance of the main window
		
		if(Settings.firstTimeRun) {
			firstRunHelper = new FirstRunHelperDialogue(FrameFactory.getDictionaryMainWindow(), true);
		}
		
		loadVocableList();
		
		SearchBox.searchVocableButtonActionPerformed();
		SearchHistory.restoreSearchHistory();
	}

	
	private static void setLookAndFeel() {
		/*for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			System.out.println(info.getName());
		}*/
		
		/*
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					
					//UIManager.put("TextField.inactiveBackground", new Color(214,217,223));
					//UIManager.put("FormattedTextField.inactiveBackground", new Color(214,217,223));
					//UIManager.put("PasswordField.inactiveBackground", new Color(214,217,223));
					//UIManager.put("TextField.disabledBackground", new Color(214,217,223));
					//UIManager.put("textInactiveText", new Color(214,217,223));
					
					break;
				}
			}
		} catch (Exception e) {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Metal".equals(info.getName())) {
					try {
						UIManager.setLookAndFeel(info.getClassName());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					break;
				}
			}
		}
		*/
		
		
		try {
			setUIFont();
			WebLookAndFeel.install();
            //UIManager.setLookAndFeel();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
	}
	
	public static void setUIFont() {
//		WebLookAndFeel.buttonFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.checkBoxFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.checkBoxMenuItemAcceleratorFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.checkBoxMenuItemFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.colorChooserFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.comboBoxFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.editorPaneFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.formattedTextFieldFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.globalAcceleratorFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.globalAlertFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.globalControlFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.globalMenuFont = new Font("Dialog", Font.PLAIN, 10);
		
		WebLookAndFeel.globalTextFont = new Font("Dialog", Font.PLAIN, 12);
		
//		WebLookAndFeel.globalTitleFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.globalTooltipFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.globalAlertFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.globalAlertFont = new Font("Dialog", Font.PLAIN, 10);
		
		
//		Enumeration<Object> keys = UIManager.getDefaults().keys();
//		while (keys.hasMoreElements()) {
//			Object key = keys.nextElement();
//			Object value = UIManager.get (key);
//			if (value != null && value instanceof FontUIResource) {
//				UIManager.put (key, f);
//			}
//		}
	}
	
	/* REFACTORED
	public static void addVocable(Vocable voc) {
		vocableList.add(voc);
		updateSearchResult(SearchBox.getSearchTerm());
	}
	*/

	/* REFACTORED
	public static void deleteVocable(String topic, String chapter, String german, String chinese, String pinyin, String level) {
		for (int i = 0; i < vocableList.size(); i++) {
			if (vocableList.get(i).getTopic().equals(topic)
				   && vocableList.get(i).getChapter().equals(chapter)
				   && vocableList.get(i).getFirstLanguage().equals(german)
				   && vocableList.get(i).getSecondLanguage().equals(chinese)
				   && vocableList.get(i).getPhoneticScript().equals(pinyin)
				   && vocableList.get(i).getLearnLevel().equals(level)) {
				vocableList.remove(i);
			}
		}
		DictionaryMainWindow.updateStatusLabel("Vocabulary not saved (vocable deleted)");
		updateSearchResult(SearchBox.getSearchTerm());
	}
	*/

	/* REFACTORED
	public static void deleteVocable(Vocable voc) {
		for (int i = 0; i < vocableList.size(); i++) {
			if (vocableList.get(i) == voc) {
				vocableList.remove(i);
			}
		}
		updateSearchResult(SearchBox.getSearchTerm());
		DictionaryMainWindow.updateStatusLabel("Vocabulary not saved (vocable deleted)");
	}
	*/
	
	/* REFACTORED
	public static void deleteVocables(Vocable[] vocs) {
		for(Vocable voc : vocs) {
			Dictionary.deleteVocable(voc);
		}
	}
	*/

//	public static ArrayList<Vocable> searchVocable(String searched, boolean checkTopic, boolean checkChapter, boolean checkFirstLanguage, boolean checkSecondLanguage, boolean checkPhoneticScript, boolean checkLevel, boolean checkCaseSensitive, boolean checkExactMatch) {
//
//		ArrayList<Vocable> result = new ArrayList<Vocable>();
//
//		if (!checkCaseSensitive) {
//			searched = searched.toUpperCase();
//		}
//		
//		//SEARCH
//		for (int i = 0; i < VocableManager.getVocableList().size(); i++) {
//			
//			String topic = VocableManager.getVocableList().get(i).getTopic();
//			String chapter = VocableManager.getVocableList().get(i).getChapter();
//			String learnLevel = VocableManager.getVocableList().get(i).getLearnLevel();
//			String firstLanguage = VocableManager.getVocableList().get(i).getFirstLanguage();
//			String phoneticScript = VocableManager.getVocableList().get(i).getPhoneticScript();
//			String secondLanguage = VocableManager.getVocableList().get(i).getSecondLanguage();
//			boolean alreadyAddedToSearchResult = false;
//			
//			if (checkTopic) {
//				final String[] parts = topic.split(Settings.vocableOptions_translationsSeperator);
//				for (String part : parts) {
//					if(isVocableInSearchResult(checkExactMatch, checkCaseSensitive, searched, part)) {
//						result.add(VocableManager.getVocableList().get(i));
//						alreadyAddedToSearchResult = true;
//						break;
//					}
//				}
//			}
//			
//			if (checkChapter && !alreadyAddedToSearchResult) {
//				final String[] parts = chapter.split(Settings.vocableOptions_translationsSeperator);
//				for (String part : parts) {
//					if(isVocableInSearchResult(checkExactMatch, checkCaseSensitive, searched, part)) {
//						result.add(VocableManager.getVocableList().get(i));
//						alreadyAddedToSearchResult = true;
//						break;
//					}
//				}
//			}
//			
//			if (checkFirstLanguage && !alreadyAddedToSearchResult) {
//				final String[] parts = firstLanguage.split(Settings.vocableOptions_translationsSeperator);
//				for (String part : parts) {
//					if(isVocableInSearchResult(checkExactMatch, checkCaseSensitive, searched, part)) {
//						result.add(VocableManager.getVocableList().get(i));
//						alreadyAddedToSearchResult = true;
//						break;
//					}
//				}
//			}
//			
//			if (checkSecondLanguage && !alreadyAddedToSearchResult) {
//				final String[] parts = secondLanguage.split(Settings.vocableOptions_translationsSeperator);
//				for (String part : parts) {
//					if(isVocableInSearchResult(checkExactMatch, checkCaseSensitive, searched, part)) {
//						result.add(VocableManager.getVocableList().get(i));
//						alreadyAddedToSearchResult = true;
//						break;
//					}
//				}
//			}
//			
//			if (checkPhoneticScript && !alreadyAddedToSearchResult) {
//				final String[] parts = phoneticScript.split(Settings.vocableOptions_translationsSeperator);
//				for (String part : parts) {
//					if(isVocableInSearchResult(checkExactMatch, checkCaseSensitive, searched, part)) {
//						result.add(VocableManager.getVocableList().get(i));
//						alreadyAddedToSearchResult = true;
//						break;
//					}
//				}
//			}
//			
//			if (checkLevel && !alreadyAddedToSearchResult) {
//				final String[] parts = learnLevel.split(Settings.vocableOptions_translationsSeperator);
//				for (String part : parts) {
//					if(isVocableInSearchResult(checkExactMatch, checkCaseSensitive, searched, part)) {
//						result.add(VocableManager.getVocableList().get(i));
//						alreadyAddedToSearchResult = true;
//						break;
//					}
//				}
//			}
//		}
//		
//		Dictionary.searchResult = result;
//		return result;
//	}
//
//	private static boolean isVocableInSearchResult(boolean exactMatch, boolean checkCaseSensitive, String searched, String partOfVocable) {
//		boolean result = false;
//		
//		if(exactMatch) {
//			if (!checkCaseSensitive) {
//				if (partOfVocable.toUpperCase().equals(searched)) {
//					result = true;
//				}
//			} else {
//				if (partOfVocable.equals(searched)) {
//					result = true;
//				}
//			}
//		} else {
//			if (!checkCaseSensitive) {
//				if (partOfVocable.toUpperCase().contains(searched)) {
//					result = true;
//				}
//			} else {
//				if (partOfVocable.contains(searched)) {
//					result = true;
//				}
//			}
//		}
//		
//		return result;
//	}
//	
//	public static void setLevelForAllVocables(String newLevel) {
//		for (Vocable voc : VocableManager.getVocableList()) {
//			voc.setLearnLevel(newLevel);
//		}
//		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification(); //TODO: Implement that the status label show a more exact message
//	}
	
//	public static void setLevelForVocables(ArrayList<Vocable> vocables, String newLevel) {
//		for (Vocable voc : vocables) {
//			voc.setLearnLevel(newLevel);
//		}
//		//DictionaryMainWindow.updateStatusLabel("Vocabulary not saved (new level set)");
//		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification(); //TODO: Implement that the status label show a more exact message
//	}

	public void replacePinyin() {
	}

	public void showAllTopics() {
	}

	/* REFACTORED
	public static void changeVocable(Vocable vocable, String newFirstLanguage, String newPhoneticScript, String newSecondLanguage, String newTopic, String newChapter, String newLearnLevel) {
		vocable.setFirstLanguage(newFirstLanguage);
		vocable.setPhoneticScript(newPhoneticScript);
		vocable.setSecondLanguage(newSecondLanguage);
		vocable.setChapter(newChapter);
		vocable.setTopic(newTopic);
		vocable.setLearnLevel(newLearnLevel);
		DictionaryMainWindow.updateStatusLabel("Vocabulary not saved (vocable changed)");
		updateSearchResult(SearchBox.getSearchTerm());
	}
	*/

	public static void createNewDictionary() {
		try {
			//Write new dictionary
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Dictionary.vocableFilename),"UTF-8"));
			//String line = "Topic:Chapter:FirstLanguage:SecondLanguage:PhoneticScript:LearnLevel" + System.getProperty("line.separator");
			//out.write(line);
			out.close();
			
			//load it
			loadVocableList();
			
			//update program
			Dictionary.vocabularyIsSavedToFile = true;
			Settings.vocableFileLocation = Dictionary.vocableFilename;
			
			System.out.println("NEW");
			//DictionaryMainWindow.updateStatusLabel("Vocabulary saved (new dictionary created)");
			
			ObserveableFactory.getVocablesObserveable().fireVocableListSavedNotification(); // TODO: Implement that there are different saved messages in the status label
			
			
		} catch (IOException e) {
			System.err.println("Could not save vocabulary in " + vocableFilename + ".");
		}
	}
	
	public static void loadVocableList() {
		BufferedReader in = null;
		String newVocableFilePath = "";
		
		try {
			//Choose File
			try {
				in = new BufferedReader(new InputStreamReader(new FileInputStream(Dictionary.vocableFilename), "UTF-8"));
				newVocableFilePath = Dictionary.vocableFilename;
			} catch (IOException e1) {
				try {
					in = new BufferedReader(new InputStreamReader(new FileInputStream(Settings.DEFAULT_VOCABLE_FILE_LOCATION), "UTF-8"));
					newVocableFilePath = Settings.DEFAULT_VOCABLE_FILE_LOCATION;
				} catch (IOException e2) {
					System.out.println("Error while loading vocabulary file.");
					createNewDictionary();
					System.out.println("New vocabulary file created.");
					newVocableFilePath = Dictionary.vocableFilename;
				}
			}
			
			//Begin to read
			ArrayList<Vocable> loadedVocableList = new ArrayList<Vocable>();
			//Dictionary.vocableList = new ArrayList<Vocable>();
			String line;
			while ((line = in.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, ":");
				for (int i = 0; i < tokenizer.countTokens(); i++) {
					String topic = tokenizer.nextToken();
					String chapter = tokenizer.nextToken();
					String firstLanguage = tokenizer.nextToken();
					String secondLanguage = tokenizer.nextToken();
					String phoneticScript = tokenizer.nextToken();
					String learnLevel = tokenizer.nextToken();
					//Dictionary.vocableList.add(new Vocable(topic, chapter, firstLanguage, secondLanguage, phoneticScript, learnLevel));
					loadedVocableList.add(new Vocable(topic, chapter, firstLanguage, secondLanguage, phoneticScript, learnLevel));
				}
			}
			VocableManager.setVocableList(loadedVocableList);
			
			//New vocableFileLocation is the one just used
			Settings.vocableFileLocation = newVocableFilePath;
			
		} catch (Exception e3) {
			e3.printStackTrace();
			
		} finally {
			try {
				in.close();
			} catch (IOException e4) {
				System.out.println("Error while closing file stream for vocable file.");
				e4.printStackTrace();
			}
		}
		
		VocableManager.updateSearchResult();
					
		/*
		//Try saved path to vocabulary file
		BufferedReader in = null;
		try {
			//Try saved location
			in = new BufferedReader(new InputStreamReader(new FileInputStream(Dictionary.vocableFilename), "UTF-8"));
		} catch (IOException ex) {
			try {
				//Try default location
				in = new BufferedReader(new InputStreamReader(new FileInputStream(Settings.DEFAULT_VOCABLE_FILE_LOCATION), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				//If all fails
				System.out.println("Error while loading vocabulary file.");
				createNewDictionary();
			}
		}
		
		Dictionary.vocableList = new ArrayList<Vocable>();
		String line;
		
		try {
			while ((line = in.readLine()) != null) {
				
				StringTokenizer tokenizer = new StringTokenizer(line, ":");
				
				for (int i = 0; i < tokenizer.countTokens(); i++) {

					String topic = tokenizer.nextToken();
					String chapter = tokenizer.nextToken();
					String firstLanguage = tokenizer.nextToken();
					String secondLanguage = tokenizer.nextToken();
					String phoneticScript = tokenizer.nextToken();
					String learnLevel = tokenizer.nextToken();
					
					Dictionary.vocableList.add(new Vocable(topic, chapter, firstLanguage, secondLanguage, phoneticScript, learnLevel));
				}
			
			Settings.vocableFileLocation = Dictionary.vocableFilename;
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error while trying to read a line of the input stream for vocables.");
		}
		
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error while trying to close the input stream for vocable list.");
		}
		
		updateSearchResult(SearchBox.getSearchTerm());
		*/
	}

	public static void saveVocableList() {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Dictionary.vocableFilename),"UTF-8"));
			
			//FileWriter fw = new FileWriter(Dictionary.vocabularyFilename);
			for (Vocable voc : VocableManager.getVocableList()/*Dictionary.vocableList*/) {
				String line = voc.getTopic() + ":" + voc.getChapter() + ":" + voc.getFirstLanguage() + ":" + voc.getSecondLanguage() + ":" + voc.getPhoneticScript() + ":" + voc.getLearnLevel() + System.getProperty("line.separator");
				out.write(line);
			}
			out.close();
			Dictionary.vocabularyIsSavedToFile = true;
			Settings.vocableFileLocation = Dictionary.vocableFilename;
			//DictionaryMainWindow.updateStatusLabel("Vocabulary saved (saved vocable list)");
			ObserveableFactory.getVocablesObserveable().fireVocableListSavedNotification();
			
		} catch (IOException e) {
			System.err.println("Could not save vocabulary in " + vocableFilename + ".");
		}
	}

	public static void saveSearchResult(ArrayList<Vocable> searchResult) {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Dictionary.searchResultFilename),"UTF-8"));
			
			//FileWriter fw = new FileWriter(Dictionary.vocabularyFilename);
			for (Vocable voc : searchResult) {
				String line = voc.getTopic() + ":" + voc.getChapter() + ":" + voc.getFirstLanguage() + ":" + voc.getSecondLanguage() + ":" + voc.getPhoneticScript() + ":" + voc.getLearnLevel() + System.getProperty("line.separator");
				out.write(line);
			}
			out.close();
		} catch (IOException e) {
			System.err.println("Could not save search result in " + searchResultFilename + ".");
		}
	}
	
	/* REFACTORED
	public ArrayList<Vocable> getVocableList() {
		return Dictionary.vocableList;
	}
	*/
	
	/**
	 * Searches for the given string with the current search options and updates the serach result.
	 * @param searched The search term 
	 */
//	public static void updateSearchResult(String searched) {
//		boolean checkTopic = SearchBox.isTopicCheckboxSelected();
//		boolean checkChapter = SearchBox.isChapterCheckboxSelected();
//		boolean checkFirstLanguage = SearchBox.isFirstLanguageCheckboxSelected();
//		boolean checkPhoneticScript = SearchBox.isPhoneticScriptCheckboxSelected();
//		boolean checkSecondLanguage = SearchBox.isSecondLanguageCheckboxSelected();
//		boolean checkLearnLevel = SearchBox.isLearnLevelCheckboxSelected();
//		boolean checkCaseSensitive = SearchBox.isCaseSensitiveCheckboxSelected();
//		boolean checkExactMatch = SearchBox.isExactMatchCheckboxSelected();
//		
//		VocableManager.searchVocable(searched, checkTopic, checkChapter, checkFirstLanguage, checkSecondLanguage, checkPhoneticScript, checkLearnLevel, checkCaseSensitive, checkExactMatch);
//		
//		//DictionaryMainWindow.updateVocableTable(searchResult);
//	}
	
	public static boolean isVocableAlreadyInVocableList(String firstLanguage, String phoneticScript, String secondLanguage) {
		for(Vocable vocable : VocableManager.getVocableList()) {
			if(	vocable.getFirstLanguage().equals(firstLanguage) &&
				vocable.getPhoneticScript().equals(phoneticScript) &&
				vocable.getSecondLanguage().equals(secondLanguage)) {
				return true;
			}
		}
		return false;
	}
	
	public FirstRunHelperDialogue getFirstRunHelper() {
		return firstRunHelper;
	}
}