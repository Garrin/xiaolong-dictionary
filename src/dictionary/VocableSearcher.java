package dictionary;

import java.util.ArrayList;

import javax.swing.SwingWorker;

import manager.VocableManager;

public class VocableSearcher extends SwingWorker<ArrayList<Vocable>, Vocable> {

	//private ArrayList<Vocable> searchResult;

	private String searchedString;
	private boolean checkTopic;
	private boolean checkChapter;
	private boolean checkFirstLanguage;
	private boolean checkSecondLanguage;
	private boolean checkPhoneticScript;
	private boolean checkLevel;
	private boolean checkCaseSensitive;
	private boolean checkExactMatch;
	private boolean negateSearch;
	private ArrayList<Vocable> listOfVocables;

	private ArrayList<Vocable> temporaryResult;
	
	//private boolean finished = false;

	public VocableSearcher(String searched, boolean checkTopic,
			boolean checkChapter, boolean checkFirstLanguage,
			boolean checkSecondLanguage, boolean checkPhoneticScript,
			boolean checkLevel, boolean checkCaseSensitive,
			boolean checkExactMatch, boolean negateSearch,
			ArrayList<Vocable> listOfVocables) {

		this.searchedString = searched;
		this.checkTopic = checkTopic;
		this.checkChapter = checkChapter;
		this.checkFirstLanguage = checkFirstLanguage;
		this.checkPhoneticScript = checkPhoneticScript;
		this.checkSecondLanguage = checkSecondLanguage;
		this.checkLevel = checkLevel;
		this.checkCaseSensitive = checkCaseSensitive;
		this.checkExactMatch = checkExactMatch;
		this.negateSearch = negateSearch;
		this.listOfVocables = listOfVocables;
	}

	@Override
	public ArrayList<Vocable> doInBackground() {
		searchVocableList();
		return listOfVocables;
	}

	@Override
	protected void done() {
		VocableManager.setLastSearchresult(temporaryResult);
	}
	
	protected void process(Vocable match) {
		VocableManager.addVocableToSearchResult(match);
	}
	
	/**
	 * Searches through the whole vocableList for vocables matching the search
	 * criteria
	 * 
	 * @param searchedString the searched string
	 * @param checkTopic true if the topic attribute of a vocable shall be considered
	 * @param checkChapter true if the chapter attribute of a vocable shall be considered
	 * @param checkFirstLanguage true if the first language attribute of a vocable shall be considered
	 * @param checkSecondLanguage true if the second language attribute of a vocable shall be considered
	 * @param checkPhoneticScript true if the phonetic script attribute of a vocable shall be considered
	 * @param checkLevel true if the level attribute of a vocable shall be considered
	 * @param checkCaseSensitive true if the search shall be case sensitive
	 * @param checkExactMatch true if the search shall only look for whole word matches
	 * @return a list of vocables matching the search criteria
	 */
	private ArrayList<Vocable> searchVocableList() {

		temporaryResult = new ArrayList<Vocable>();

		if (!checkCaseSensitive) {
			searchedString = searchedString.toUpperCase();
		}

		// SEARCH
		for (int i = 0; i < listOfVocables.size(); i++) {

			String topic = listOfVocables.get(i).getTopic();
			String chapter = listOfVocables.get(i).getChapter();
			String learnLevel = listOfVocables.get(i).getLearnLevel();
			String firstLanguage = listOfVocables.get(i).getFirstLanguage();
			String phoneticScript = listOfVocables.get(i).getPhoneticScript();
			String secondLanguage = listOfVocables.get(i).getSecondLanguage();
			boolean alreadyAddedToSearchResult = false;

			if (checkTopic) {
				final String[] parts = topic
						.split(Settings.vocableOptions_translationsSeperator);
				for (String part : parts) {
					if (isSearchedStringInAttributeOfVocable(checkExactMatch,
							checkCaseSensitive, searchedString, part)) {

						if (!negateSearch) {
							temporaryResult.add(listOfVocables.get(i));
						}

						alreadyAddedToSearchResult = true;
						break;
					}
				}
			}

			if (checkChapter && !alreadyAddedToSearchResult) {
				final String[] parts = chapter
						.split(Settings.vocableOptions_translationsSeperator);
				for (String part : parts) {
					if (isSearchedStringInAttributeOfVocable(checkExactMatch,
							checkCaseSensitive, searchedString, part)) {

						if (!negateSearch) {
							temporaryResult.add(listOfVocables.get(i));
						}

						alreadyAddedToSearchResult = true;
						break;
					}
				}
			}

			if (checkFirstLanguage && !alreadyAddedToSearchResult) {
				final String[] parts = firstLanguage
						.split(Settings.vocableOptions_translationsSeperator);
				for (String part : parts) {
					if (isSearchedStringInAttributeOfVocable(checkExactMatch,
							checkCaseSensitive, searchedString, part)) {

						if (!negateSearch) {
							temporaryResult.add(listOfVocables.get(i));
						}

						alreadyAddedToSearchResult = true;
						break;
					}
				}
			}

			if (checkSecondLanguage && !alreadyAddedToSearchResult) {
				final String[] parts = secondLanguage
						.split(Settings.vocableOptions_translationsSeperator);
				for (String part : parts) {
					if (isSearchedStringInAttributeOfVocable(checkExactMatch,
							checkCaseSensitive, searchedString, part)) {

						if (!negateSearch) {
							temporaryResult.add(listOfVocables.get(i));
						}

						alreadyAddedToSearchResult = true;
						break;
					}
				}
			}

			if (checkPhoneticScript && !alreadyAddedToSearchResult) {
				final String[] parts = phoneticScript
						.split(Settings.vocableOptions_translationsSeperator);
				for (String part : parts) {
					if (isSearchedStringInAttributeOfVocable(checkExactMatch,
							checkCaseSensitive, searchedString, part)) {

						if (!negateSearch) {
							temporaryResult.add(listOfVocables.get(i));
						}

						alreadyAddedToSearchResult = true;
						break;
					}
				}
			}

			if (checkLevel && !alreadyAddedToSearchResult) {
				final String[] parts = learnLevel
						.split(Settings.vocableOptions_translationsSeperator);
				for (String part : parts) {
					if (isSearchedStringInAttributeOfVocable(checkExactMatch,
							checkCaseSensitive, searchedString, part)) {

						// Add only if it is not a negated search
						if (!negateSearch) {
							temporaryResult.add(listOfVocables.get(i));
						}

						alreadyAddedToSearchResult = true;
						break;
					}
				}
			}

			if (negateSearch) {
				if (!alreadyAddedToSearchResult) {
					temporaryResult.add(listOfVocables.get(i));
				}
			}
			
			publish(listOfVocables.get(i));
		} //End of For-Loop

		return temporaryResult;
	}

	/**
	 * Checks if a searched string is part of a vocable
	 * 
	 * @param exactMatch true if one wants to check for a whole word match
	 * @param checkCaseSensitive true if one wants to check case sensitve
	 * @param searched the searched string
	 * @param partOfVocable the part of a vocable that could contain the searched string
	 * @return true if the part of the vocable contains the searched string
	 */
	private static boolean isSearchedStringInAttributeOfVocable(
			boolean exactMatch, boolean checkCaseSensitive, String searched,
			String partOfVocable) {
		boolean result = false;

		if (exactMatch) {
			if (!checkCaseSensitive) {
				if (partOfVocable.toUpperCase().equals(searched)) {
					result = true;
				}
			} else {
				if (partOfVocable.equals(searched)) {
					result = true;
				}
			}
		} else {
			if (!checkCaseSensitive) {
				if (partOfVocable.toUpperCase().contains(searched)) {
					result = true;
				}
			} else {
				if (partOfVocable.contains(searched)) {
					result = true;
				}
			}
		}

		return result;
	}
}
