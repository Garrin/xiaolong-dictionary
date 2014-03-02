package dictionary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import manager.VocableManager;
import factories.ObserveableFactory;

public class FileManager {

	public static String vocableFilename = Settings.vocableFileLocation;;
	public static String searchResultFilename;
	public static boolean vocabularyIsSavedToFile = true;
	
	/**
	 * This method loads the vocable file from a previously set location.
	 */
	public static void loadVocableList() {
		BufferedReader in = null;
		String newVocableFilePath = "";
		
		try {
			//Choose File
			try {
				in = new BufferedReader(new InputStreamReader(new FileInputStream(vocableFilename), "UTF-8"));
				newVocableFilePath = vocableFilename;
			} catch (IOException e1) {
				try {
					in = new BufferedReader(new InputStreamReader(new FileInputStream(Settings.DEFAULT_VOCABLE_FILE_LOCATION), "UTF-8"));
					newVocableFilePath = Settings.DEFAULT_VOCABLE_FILE_LOCATION;
				} catch (IOException e2) {
					System.out.println("Error while loading vocabulary file.");
					createNewDictionary();
					System.out.println("New vocabulary file created.");
					newVocableFilePath = vocableFilename;
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
					String importanceLevel = tokenizer.nextToken();
					String description = tokenizer.nextToken();
					//Dictionary.vocableList.add(new Vocable(topic, chapter, firstLanguage, secondLanguage, phoneticScript, learnLevel));
					loadedVocableList.add(new Vocable(topic, chapter, firstLanguage, secondLanguage, phoneticScript, learnLevel, importanceLevel, description));
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
	}

	/**
	 * This method saves the vocable list to the vocable file previously specified.
	 */
	public static void saveVocableList() {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(vocableFilename),"UTF-8"));
			
			//FileWriter fw = new FileWriter(Dictionary.vocabularyFilename);
			for (Vocable voc : VocableManager.getVocableList()) {
				String line = voc.getTopic() + ":" + voc.getChapter() + ":" + voc.getFirstLanguage() + ":" + voc.getSecondLanguage() + ":" + voc.getPhoneticScript() + ":" + voc.getLearnLevel() + ":" + voc.getRelevance() + ":" + voc.getDescription() + System.getProperty("line.separator");
				out.write(line);
			}
			out.close();
			FileManager.vocabularyIsSavedToFile = true;
			Settings.vocableFileLocation = vocableFilename;
			ObserveableFactory.getVocablesObserveable().fireVocableListSavedNotification();
			
		} catch (IOException e) {
			System.err.println("Could not save vocabulary in " + vocableFilename + ".");
		}
	}

	/**
	 * This method saves a search result to a new vocable file.
	 * @param searchResult the search result 
	 */
	public static void saveSearchResult(ArrayList<Vocable> searchResult) {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileManager.searchResultFilename),"UTF-8"));
			
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

	/**
	 * This method creates a new vocable file.
	 */
	public static void createNewDictionary() {
		try {
			//Write new dictionary
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(vocableFilename),"UTF-8"));
			//String line = "Topic:Chapter:FirstLanguage:SecondLanguage:PhoneticScript:LearnLevel" + System.getProperty("line.separator");
			//out.write(line);
			out.close();
			
			//load it
			loadVocableList();
			
			//update program
			FileManager.vocabularyIsSavedToFile = true;
			Settings.vocableFileLocation = vocableFilename;
			
			System.out.println("NEW");
			//DictionaryMainWindow.updateStatusLabel("Vocabulary saved (new dictionary created)");
			
			ObserveableFactory.getVocablesObserveable().fireVocableListSavedNotification(); // TODO: Implement that there are different saved messages in the status label
			
			
		} catch (IOException e) {
			System.err.println("Could not save vocabulary in " + vocableFilename + ".");
		}
	}
}