package manager;

import java.util.ArrayList;

import dictionary.FileManager;
import dictionary.SearchCriteriaHistoryItem;
import dictionary.Settings;
import dictionary.Vocable;
import factories.ObserveableFactory;
import gui.SearchBox;

public class VocableManager {

	private static ArrayList<Vocable> vocableList = new ArrayList<Vocable>();
	private static ArrayList<Vocable> lastSearchResult = new ArrayList<Vocable>();
	
	private static ArrayList<SearchCriteriaHistoryItem> lastSearchCriterias = new ArrayList<SearchCriteriaHistoryItem>();
	

	/**
	 * adds a vocable to the vocableList and checks whether it is in the lastSearchResult
	 * @param vocable the vocable, which will be added to the vocableList
	 */
	public static void addVocable(Vocable vocable) {
		vocableList.add(vocable);
		
		if(lastSearchCriterias.isEmpty()) {
			System.out.println("EMPTY!");
		}
		
		/*
		 * Perform the last search again, this time including the newly added vocable,
		 * so that the new vocable could be in the resulting search result.
		 * This first search is always a standard search and not an AND or OR search.
		 */
		lastSearchResult = searchVocableList(
				lastSearchCriterias.get(0).searchTerm, 
				lastSearchCriterias.get(0).checkTopic,
				lastSearchCriterias.get(0).checkChapter,
				lastSearchCriterias.get(0).checkFirstLanguage,
				lastSearchCriterias.get(0).checkSecondLanguage,
				lastSearchCriterias.get(0).checkPhoneticScript, 
				lastSearchCriterias.get(0).checkLearnLevel, 
				lastSearchCriterias.get(0).checkCaseSensitive, 
				lastSearchCriterias.get(0).checkRelevance,
				lastSearchCriterias.get(0).checkDescription,
				lastSearchCriterias.get(0).matchWholeWord, 
				lastSearchCriterias.get(0).notSearch, 
				VocableManager.getVocableList()
		);
		
		/*
		 * Perform all of AND or OR searches (specifying searches) performed that led 
		 * to the search result before the new vocable has been added, to have the 
		 * same search including the new vocable. 
		 */
		for(int i = 1; i < lastSearchCriterias.size(); i++) {
			if(lastSearchCriterias.get(i).andSearch) {
			
				performANDSearch(
						lastSearchCriterias.get(i).searchTerm, 
						lastSearchCriterias.get(i).checkTopic, 
						lastSearchCriterias.get(i).checkChapter, 
						lastSearchCriterias.get(i).checkFirstLanguage, 
						lastSearchCriterias.get(i).checkSecondLanguage, 
						lastSearchCriterias.get(i).checkPhoneticScript, 
						lastSearchCriterias.get(i).checkLearnLevel, 
						lastSearchCriterias.get(0).checkRelevance,
						lastSearchCriterias.get(0).checkDescription,
						lastSearchCriterias.get(i).checkCaseSensitive, 
						lastSearchCriterias.get(i).matchWholeWord, 
						lastSearchCriterias.get(i).notSearch
				);
				
			} else if(lastSearchCriterias.get(i).orSearch) {
				
				performORSearch(
						lastSearchCriterias.get(i).searchTerm, 
						lastSearchCriterias.get(i).checkTopic, 
						lastSearchCriterias.get(i).checkChapter, 
						lastSearchCriterias.get(i).checkFirstLanguage, 
						lastSearchCriterias.get(i).checkSecondLanguage, 
						lastSearchCriterias.get(i).checkPhoneticScript, 
						lastSearchCriterias.get(i).checkLearnLevel, 
						lastSearchCriterias.get(0).checkRelevance,
						lastSearchCriterias.get(0).checkDescription,
						lastSearchCriterias.get(i).checkCaseSensitive, 
						lastSearchCriterias.get(i).matchWholeWord, 
						lastSearchCriterias.get(i).notSearch
				);
				
			}
		}
		
		ObserveableFactory.getVocablesObserveable().fireAddVocableNotification(vocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		FileManager.vocabularyIsSavedToFile = false;
	}

	/**
	 * deletes a vocable from the vocableList
	 * @param vocable the vocable, which will be deleted
	 */
	public static void deleteVocable(Vocable vocable) {
		//delete vocable from list of vocables
		for (int i = 0; i < vocableList.size(); i++) {
			if (vocableList.get(i) == vocable) {
				vocableList.remove(i);
				break;
			}
		}

		//delete vocable from last search result
		lastSearchResult.remove(vocable);
		
		ObserveableFactory.getVocablesObserveable().fireDeleteVocableNotification(vocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		FileManager.vocabularyIsSavedToFile = false;
	}

	/**
	 * removes the vocable with the given attributes from the vocableList
	 * @param topic topic of the {@link Vocable} which will be deleted
	 * @param chapter chapter of the {@link Vocable} which will be deleted
	 * @param firstLanguage first language of the {@link Vocable} which will be deleted
	 * @param secondLanguage second language of the {@link Vocable} which will be deleted
	 * @param phoneticScript phonetic script of the {@link Vocable} which will be deleted
	 * @param learnLevel learn level of the {@link Vocable} which will be deleted
	 * @param relevance relevance of the {@link Vocable} which will be deleted
	 * @param description description of the {@link Vocable} which will be deleted
	 */
	public static void deleteVocable(
			String topic, 
			String chapter,
			String firstLanguage, 
			String secondLanguage, 
			String phoneticScript,
			String learnLevel,
			String relevance,
			String description) {
		
		Vocable vocabletoDelete = null;
		
		//delete vocable from list of vocables
		for (int i = 0; i < vocableList.size(); i++) {

			if (
				vocableList.get(i).getTopic().equals(topic)
				&& vocableList.get(i).getChapter().equals(chapter)
				&& vocableList.get(i).getFirstLanguage().equals(firstLanguage)
				&& vocableList.get(i).getSecondLanguage().equals(secondLanguage)
				&& vocableList.get(i).getPhoneticScript().equals(phoneticScript)
				&& vocableList.get(i).getLearnLevel().equals(learnLevel)
				&& vocableList.get(i).getRelevance().equals(relevance)
				&& vocableList.get(i).getDescription().equals(description)
			) {

				vocabletoDelete = vocableList.get(i);
				vocableList.remove(i);
			}
		}
		
		//delete vocable from last search result
		for(Vocable vocable : lastSearchResult) {
			if (
				vocable.getTopic().equals(topic) &&
				vocable.getChapter().equals(chapter) &&
				vocable.getFirstLanguage().equals(firstLanguage) &&
				vocable.getSecondLanguage().equals(secondLanguage) &&
				vocable.getPhoneticScript().equals(phoneticScript) &&
				vocable.getLearnLevel().equals(learnLevel) &&
				vocable.getRelevance().equals(relevance) &&
				vocable.getDescription().equals(description)
			) {
				lastSearchResult.remove(vocable);
			}
		}
		
		ObserveableFactory.getVocablesObserveable().fireDeleteVocableNotification(vocabletoDelete);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		FileManager.vocabularyIsSavedToFile = false;
	}

	/**
	 * Deletes a list of vocables from the vocableList
	 * 
	 * @param vocs the vocables, which will be deleted
	 */
	public static void deleteVocables(Vocable[] vocs) {
		for (Vocable voc : vocs) {
			deleteVocable(voc);
			ObserveableFactory.getVocablesObserveable().fireDeleteVocableNotification(voc);
			ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		}
		FileManager.vocabularyIsSavedToFile = false;
	}

	/**
	 * changes a vocable in the vocableList
	 * 
	 * @param vocable the vocable which will be changed
	 * @param newFirstLanguage the new first language attribute value
	 * @param newPhoneticScript the new phonetic script attribute value
	 * @param newSecondLanguage the new second language attribute value
	 * @param newTopic the new topic attribute value
	 * @param newChapter the new chapter attribute value
	 * @param newLearnLevel the new learn level attribute value
	 */
	public static void changeVocable(
			Vocable oldVocable, 
			String newFirstLanguage,
			String newPhoneticScript, 
			String newSecondLanguage,
			String newTopic, 
			String newChapter, 
			String newLearnLevel,
			String newRelevance,
			String newDescription
	) {
		
		Vocable newVocable = new Vocable(
			newTopic, 
			newChapter,
			newFirstLanguage,
			newSecondLanguage,
			newPhoneticScript,
			newLearnLevel,
			newRelevance,
			newDescription
		);
		
		VocableManager.getVocableList().set(VocableManager.getVocableList().indexOf(oldVocable), newVocable);
		
		//Replace the old Vocable with the new one
		if(lastSearchResult.contains(oldVocable)) {
			lastSearchResult.set(lastSearchResult.indexOf(oldVocable), newVocable);
		}
		
		ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(oldVocable, newVocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		FileManager.vocabularyIsSavedToFile = false;
	}
	
	/**
	 * changes the first language of a given vocable
	 * @param oldVocable the vocable which will be changed
	 * @param newFirstLanguage the new first language
	 */
	public static void changeVocableFirstLanguage(Vocable oldVocable, String newFirstLanguage) {
		Vocable newVocable = new Vocable(oldVocable.getTopic(), oldVocable.getChapter(), newFirstLanguage, oldVocable.getSecondLanguage(), oldVocable.getPhoneticScript(), oldVocable.getLearnLevel(), oldVocable.getRelevance(), oldVocable.getDescription());
		
		VocableManager.getVocableList().set(VocableManager.getVocableList().indexOf(oldVocable), newVocable);
		
		if(lastSearchResult.contains(oldVocable)) {
			lastSearchResult.set(lastSearchResult.indexOf(oldVocable), newVocable);
		}
		
		ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(oldVocable, newVocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		FileManager.vocabularyIsSavedToFile = false;
	}

	/**
	 * changes the phonetic script of a given vocable
	 * @param oldVocable the vocable which will be changed
	 * @param newPhoneticScript the new phonetic script
	 */
	public static void changeVocablePhoneticScript(Vocable oldVocable, String newPhoneticScript) {
		Vocable newVocable = new Vocable(oldVocable.getTopic(), oldVocable.getChapter(), oldVocable.getFirstLanguage(), oldVocable.getSecondLanguage(), newPhoneticScript, oldVocable.getLearnLevel(), oldVocable.getRelevance(), oldVocable.getDescription());
		
		VocableManager.getVocableList().set(VocableManager.getVocableList().indexOf(oldVocable), newVocable);
		
		if(lastSearchResult.contains(oldVocable)) {
			lastSearchResult.set(lastSearchResult.indexOf(oldVocable), newVocable);
		}
		
		ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(oldVocable, newVocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		FileManager.vocabularyIsSavedToFile = false;
	}
	
	/**
	 * changes the second language of a given vocable
	 * @param oldVocable the vocable that will be changed
	 * @param newSecondLanguage the new second language
	 */
	public static void changeVocableSecondLanguage(Vocable oldVocable, String newSecondLanguage) {
		Vocable newVocable = new Vocable(oldVocable.getTopic(), oldVocable.getChapter(), oldVocable.getFirstLanguage(), newSecondLanguage, oldVocable.getPhoneticScript(), oldVocable.getLearnLevel(), oldVocable.getRelevance(), oldVocable.getDescription());
		
		VocableManager.getVocableList().set(VocableManager.getVocableList().indexOf(oldVocable), newVocable);
		
		if(lastSearchResult.contains(oldVocable)) {
			lastSearchResult.set(lastSearchResult.indexOf(oldVocable), newVocable);
		}
		
		ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(oldVocable, newVocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		FileManager.vocabularyIsSavedToFile = false;
	}

	/**
	 * changes the chapter of a given vocable
	 * @param oldVocable the vocable which will be changed
	 * @param newChapter the new chapter
	 */
	public static void changeVocableChapter(Vocable oldVocable, String newChapter) {
		Vocable newVocable = new Vocable(oldVocable.getTopic(), newChapter, oldVocable.getFirstLanguage(), oldVocable.getSecondLanguage(), oldVocable.getPhoneticScript(), oldVocable.getLearnLevel(), oldVocable.getRelevance(), oldVocable.getDescription());
		
		VocableManager.getVocableList().set(VocableManager.getVocableList().indexOf(oldVocable), newVocable);
		
		if(lastSearchResult.contains(oldVocable)) {
			lastSearchResult.set(lastSearchResult.indexOf(oldVocable), newVocable);
		}
		
		ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(oldVocable, newVocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		FileManager.vocabularyIsSavedToFile = false;
	}

	/**
	 * Changes the topic of a given vocable
	 * @param oldVocable the vocable which will be changed
	 * @param newTopic the new topic
	 */
	public static void changeVocableTopic(Vocable oldVocable, String newTopic) {
		Vocable newVocable = new Vocable(newTopic, oldVocable.getChapter(), oldVocable.getFirstLanguage(), oldVocable.getSecondLanguage(), oldVocable.getPhoneticScript(), oldVocable.getLearnLevel(), oldVocable.getRelevance(), oldVocable.getDescription());
		
		VocableManager.getVocableList().set(VocableManager.getVocableList().indexOf(oldVocable), newVocable);
		
		if(lastSearchResult.contains(oldVocable)) {
			lastSearchResult.set(lastSearchResult.indexOf(oldVocable), newVocable);
		}
		
		ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(oldVocable, newVocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		FileManager.vocabularyIsSavedToFile = false;
	}

	/**
	 * Changes the learn level of a given vocable
	 * @param oldVocable the vocable which will be changed
	 * @param newLearnLevel the new learn level
	 */
	public static void changeVocableLearnLevel(Vocable oldVocable, String newLearnLevel) {
		Vocable newVocable = new Vocable(oldVocable.getTopic(), oldVocable.getChapter(), oldVocable.getFirstLanguage(), oldVocable.getSecondLanguage(), oldVocable.getPhoneticScript(), newLearnLevel, oldVocable.getRelevance(), oldVocable.getDescription());
		
		VocableManager.getVocableList().set(VocableManager.getVocableList().indexOf(oldVocable), newVocable);
		
		if(lastSearchResult.contains(oldVocable)) {
			lastSearchResult.set(lastSearchResult.indexOf(oldVocable), newVocable);
		}
		
		ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(oldVocable, newVocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		FileManager.vocabularyIsSavedToFile = false;
	}
	
	/**
	 * Changes the relevance of a given vocable
	 * @param oldVocable the vocable which will be changed
	 * @param newLearnLevel the new relevance
	 */
	public static void changeVocableRelevance(Vocable oldVocable, String newRelevance) {
		Vocable newVocable = new Vocable(oldVocable.getTopic(), oldVocable.getChapter(), oldVocable.getFirstLanguage(), oldVocable.getSecondLanguage(), oldVocable.getPhoneticScript(), oldVocable.getLearnLevel(), newRelevance, oldVocable.getDescription());
		
		VocableManager.getVocableList().set(VocableManager.getVocableList().indexOf(oldVocable), newVocable);
		
		if(lastSearchResult.contains(oldVocable)) {
			lastSearchResult.set(lastSearchResult.indexOf(oldVocable), newVocable);
		}
		
		ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(oldVocable, newVocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		FileManager.vocabularyIsSavedToFile = false;
	}
	
	/**
	 * Changes the description of a given vocable
	 * @param oldVocable the vocable which will be changed
	 * @param newLearnLevel the new description
	 */
	public static void changeVocableDescription(Vocable oldVocable, String newDescription) {
		Vocable newVocable = new Vocable(oldVocable.getTopic(), oldVocable.getChapter(), oldVocable.getFirstLanguage(), oldVocable.getSecondLanguage(), oldVocable.getPhoneticScript(), oldVocable.getLearnLevel(), oldVocable.getRelevance(), newDescription);
		
		VocableManager.getVocableList().set(VocableManager.getVocableList().indexOf(oldVocable), newVocable);
		
		if(lastSearchResult.contains(oldVocable)) {
			lastSearchResult.set(lastSearchResult.indexOf(oldVocable), newVocable);
		}
		
		ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(oldVocable, newVocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		FileManager.vocabularyIsSavedToFile = false;
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

	/**
	 * This method performs a basic search on the vocable list
	 * @param searchTerm search term
	 * @param checkTopic true if the topic attribute of {@link Vocable}s shall be considered
	 * @param checkChapter true if the chapter attribute of {@link Vocable}s shall be considered
	 * @param checkFirstLanguage true if the first language attribute of {@link Vocable}s shall be considered
	 * @param checkSecondLanguage true if the second language attribute of {@link Vocable}s shall be considered
	 * @param checkPhoneticScript true if the phonetic script attribute of {@link Vocable}s shall be considered
	 * @param checkLearnLevel true if the learn level attribute of {@link Vocable}s shall be considered
	 * @param checkRelevance true if the relevance attribute of {@link Vocable}s shall be considered
	 * @param checkDescription true if the description attribute of {@link Vocable}s shall be considered
	 * @param checkCaseSensitive true if the search shall be case sensitive
	 * @param matchWholeWord true if the search shall only allow whole word matches
	 * @param notSearch true if the search shall be an inverted search
	 * @return a list of {@link Vocable}s matching the given search criteria
	 */
	public static ArrayList<Vocable> basicSearchVocableList(
			String searchTerm,
			boolean checkTopic, 
			boolean checkChapter,
			boolean checkFirstLanguage, 
			boolean checkSecondLanguage,
			boolean checkPhoneticScript, 
			boolean checkLearnLevel,
			boolean checkRelevance,
			boolean checkDescription,
			boolean checkCaseSensitive, 
			boolean matchWholeWord,
			boolean notSearch) {
		
		//empty search criteria list, because it's a new search
		lastSearchCriterias.clear();
		//add new item to search criteria list
		lastSearchCriterias.add(
				new SearchCriteriaHistoryItem(checkFirstLanguage, checkPhoneticScript, checkSecondLanguage, checkTopic, checkChapter, checkLearnLevel, checkRelevance, checkDescription, checkCaseSensitive, matchWholeWord, notSearch, false, false, searchTerm)
		);
		
		lastSearchResult = VocableManager.searchVocableList(searchTerm, checkTopic, checkChapter, checkFirstLanguage, checkSecondLanguage, checkPhoneticScript, checkLearnLevel, checkRelevance, checkDescription, checkCaseSensitive, matchWholeWord, notSearch, VocableManager.getVocableList()); 
		ObserveableFactory.getSearchObservable().fireSearchNotification();
		
		return lastSearchResult;
	}

	/**
	 * Searches through the whole vocableList for vocables matching the search
	 * criteria
	 * 
	 * @param searched the searched string
	 * @param checkTopic true if the topic attribute of a vocable shall be considered
	 * @param checkChapter true if the chapter attribute of a vocable shall be considered
	 * @param checkFirstLanguage true if the first language attribute of a vocable shall be considered
	 * @param checkSecondLanguage true if the second language attribute of a vocable shall be considered
	 * @param checkPhoneticScript true if the phonetic script attribute of a vocable shall be considered
	 * @param checkLevel true if the level attribute of a vocable shall be considered
	 * @param checkRelevance true if the relevance attribute of a vocable shall be considered
	 * @param checkDescription true if the description attribute of a vocable shall be considered
	 * @param checkCaseSensitive true if the search shall be case sensitive
	 * @param checkExactMatch true if the search shall only look for whole word matches
	 * @param negateSearch true if the search shall be negated
	 * @param listOfVocables the list of vocables that will be searched through to find matches
	 * @return a list of vocables matching the search criteria
	 */
	private static ArrayList<Vocable> searchVocableList(
			String searched,
			boolean checkTopic, 
			boolean checkChapter,
			boolean checkFirstLanguage, 
			boolean checkSecondLanguage,
			boolean checkPhoneticScript, 
			boolean checkLevel,
			boolean checkRelevance,
			boolean checkDescription,
			boolean checkCaseSensitive, 
			boolean checkExactMatch,
			boolean negateSearch, 
			ArrayList<Vocable> listOfVocables) {

		ArrayList<Vocable> result = new ArrayList<Vocable>();

		if (!checkCaseSensitive) {
			searched = searched.toUpperCase();
		}

		// SEARCH
		for (int i = 0; i < listOfVocables.size(); i++) {

			String topic = listOfVocables.get(i).getTopic();
			String chapter = listOfVocables.get(i).getChapter();
			String learnLevel = listOfVocables.get(i).getLearnLevel();
			String firstLanguage = listOfVocables.get(i).getFirstLanguage();
			String phoneticScript = listOfVocables.get(i).getPhoneticScript();
			String secondLanguage = listOfVocables.get(i).getSecondLanguage();
			String relevance = listOfVocables.get(i).getRelevance();
			String description = listOfVocables.get(i).getDescription();
			
			boolean alreadyAddedToSearchResult = false;

			if (checkTopic) {
				final String[] parts = topic
						.split(Settings.vocableOptions_translationsSeperator);
				for (String part : parts) {
					if (isSearchedStringInAttributeOfVocable(checkExactMatch,
							checkCaseSensitive, searched, part)) {

						if (!negateSearch) {
							result.add(listOfVocables.get(i));
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
							checkCaseSensitive, searched, part)) {

						if (!negateSearch) {
							result.add(listOfVocables.get(i));
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
							checkCaseSensitive, searched, part)) {

						if (!negateSearch) {
							result.add(listOfVocables.get(i));
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
							checkCaseSensitive, searched, part)) {

						if (!negateSearch) {
							result.add(listOfVocables.get(i));
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
							checkCaseSensitive, searched, part)) {

						if (!negateSearch) {
							result.add(listOfVocables.get(i));
						}

						alreadyAddedToSearchResult = true;
						break;
					}
				}
			}
			
			//check for relevance
			if(checkRelevance && !alreadyAddedToSearchResult) {
				final String[] parts = relevance.split(Settings.vocableOptions_translationsSeperator);
				for (String part : parts) {
					if (isSearchedStringInAttributeOfVocable(checkExactMatch,checkCaseSensitive, searched, part)) {
						if (!negateSearch) {
							result.add(listOfVocables.get(i));
						}
						alreadyAddedToSearchResult = true;
						break;
					}
				}
			}

			//check for description
			if(checkDescription && !alreadyAddedToSearchResult) {
				final String[] parts = description.split(Settings.vocableOptions_translationsSeperator);
				for (String part : parts) {
					if (isSearchedStringInAttributeOfVocable(checkExactMatch,checkCaseSensitive, searched, part)) {
						if (!negateSearch) {
							result.add(listOfVocables.get(i));
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
							checkCaseSensitive, searched, part)) {

						// Add only if it is not a negated search
						if (!negateSearch) {
							result.add(listOfVocables.get(i));
						}

						alreadyAddedToSearchResult = true;
						break;
					}
				}
			}

			if (negateSearch) {
				if (!alreadyAddedToSearchResult) {
					result.add(listOfVocables.get(i));
				}
			}
		}

		return result;
	}
	
	/**
	 * performs a specifying search
	 * @param searched
	 * @param checkTopic true if the topic attribute of a vocable shall be considered
	 * @param checkChapter true if the chapter attribute of a vocable shall be considered
	 * @param checkFirstLanguage true if the first language attribute of a vocable shall be considered
	 * @param checkSecondLanguage true if the second language attribute of a vocable shall be considered
	 * @param checkPhoneticScript true if the phonetic script attribute of a vocable shall be considered
	 * @param checkLevel true if the level attribute of a vocable shall be considered
	 * @param checkCaseSensitive true if the search shall be case sensitive
	 * @param checkExactMatch true if the search shall only look for whole word matches
	 * @param notSearch true if the search is negated
	 * @param andSearch true if a vocable shall be in both lists in order to appear in the final result list
	 * @param orSearch true if a vocable only needs to be in one of the lists in order to appear in the final result list
	 * @return the result list
	 */
	public static ArrayList<Vocable> performSpecifyingSearch(
			String searchTerm,
			boolean checkTopic, 
			boolean checkChapter, 
			boolean checkFirstLanguage, 
			boolean checkSecondLanguage,
			boolean checkPhoneticScript, 
			boolean checkLearnLevel,
			boolean checkRelevance,
			boolean checkDescription,
			boolean checkCaseSensitive, 
			boolean matchWholeWord,
			boolean notSearch, 
			boolean andSearch, 
			boolean orSearch) {
			
		ArrayList<Vocable> result;
		
		if(andSearch) { //AND Search
			lastSearchCriterias.add(
					new SearchCriteriaHistoryItem(checkFirstLanguage, checkPhoneticScript, checkSecondLanguage, checkTopic, checkChapter, checkLearnLevel, checkRelevance, checkDescription, checkCaseSensitive, matchWholeWord, notSearch, true, false, searchTerm)
			);
			result = performANDSearch(searchTerm, checkTopic, checkChapter, checkFirstLanguage, checkSecondLanguage, checkPhoneticScript, checkLearnLevel, checkRelevance, checkDescription, checkCaseSensitive, matchWholeWord, notSearch); 
		} else { //OR Search
			lastSearchCriterias.add(
					new SearchCriteriaHistoryItem(checkFirstLanguage, checkPhoneticScript, checkSecondLanguage, checkTopic, checkChapter, checkLearnLevel, checkRelevance, checkDescription, checkCaseSensitive, matchWholeWord, notSearch, false, true, searchTerm)
			);
			result = performORSearch(searchTerm, checkTopic, checkChapter, checkFirstLanguage, checkSecondLanguage, checkPhoneticScript, checkLearnLevel, checkRelevance, checkDescription, checkCaseSensitive, matchWholeWord, notSearch);
		}
		
		//(wrote already the lastSearchResult in called functions, so don't need to do this here again using the result array (performANDSearch and performORSearch work on the lastSearchResult))
		ObserveableFactory.getSearchObservable().fireSearchNotification();
		return result;
	}
	
	/**
	 * Performs a search on the last search result
	 * @param searched the searched String
	 * @param checkTopic true if the topic attribute of a vocable shall be considered
	 * @param checkChapter true if the chapter attribute of a vocable shall be considered
	 * @param checkFirstLanguage true if the first language attribute of a vocable shall be considered
	 * @param checkSecondLanguage true if the second language attribute of a vocable shall be considered
	 * @param checkPhoneticScript true if the phonetic script attribute of a vocable shall be considered
	 * @param checkLevel true if the level attribute of a vocable shall be considered
	 * @param checkCaseSensitive true if the search shall be case sensitive
	 * @param checkExactMatch true if the search shall only look for whole word matches
	 * @param notSearch true if the search is negated
	 * @return the result list
	 */
	private static ArrayList<Vocable> performANDSearch(
			String searchTerm,
			boolean checkTopic, 
			boolean checkChapter, 
			boolean checkFirstLanguage, 
			boolean checkSecondLanguage,
			boolean checkPhoneticScript, 
			boolean checkLearnLevel,
			boolean checkRelevance,
			boolean checkDescription,
			boolean checkCaseSensitive, 
			boolean matchWholeWord,
			boolean notSearch) {
		
		lastSearchResult = searchVocableList(searchTerm, checkTopic, checkChapter, checkFirstLanguage, checkSecondLanguage, checkPhoneticScript, checkLearnLevel, checkRelevance, checkDescription, checkCaseSensitive, matchWholeWord, notSearch, lastSearchResult);
		ObserveableFactory.getSearchObservable().fireSearchNotification();
		
		return lastSearchResult;
	}

	/**
	 * Performs a search on the vocable list and adds the results to the lastSearchResult
	 * @param searched the searched String
	 * @param checkTopic true if the topic attribute of a vocable shall be considered
	 * @param checkChapter true if the chapter attribute of a vocable shall be considered
	 * @param checkFirstLanguage true if the first language attribute of a vocable shall be considered
	 * @param checkSecondLanguage true if the second language attribute of a vocable shall be considered
	 * @param checkPhoneticScript true if the phonetic script attribute of a vocable shall be considered
	 * @param checkLevel true if the level attribute of a vocable shall be considered
	 * @param checkCaseSensitive true if the search shall be case sensitive
	 * @param checkExactMatch true if the search shall only look for whole word matches
	 * @param notSearch true if the search is negated
	 * @return the result list
	 */
	private static ArrayList<Vocable> performORSearch(
			String searchTerm, 
			boolean checkTopic, 
			boolean checkChapter, 
			boolean checkFirstLanguage, 
			boolean checkSecondLanguage, 
			boolean checkPhoneticScript, 
			boolean checkLearnLevel,
			boolean checkRelevance,
			boolean checkDescription, 
			boolean checkCaseSensitive, 
			boolean matchWholeWord, 
			boolean notSearch) {
		
		ArrayList<Vocable> lastSearchResultCopy = new ArrayList<Vocable>();
		
		//Make a copy of the current lastSearchResult by iterating over the Vocables, cloning them and saving a reference in the lastSearchResultCopy array.
		lastSearchResultCopy = VocableManager.copyVocableList(lastSearchResult); 
		
		//sets a new list of vocables for lastSearchResult ...
		lastSearchResult = VocableManager.searchVocableList(searchTerm, checkTopic, checkChapter, checkFirstLanguage, checkSecondLanguage, checkPhoneticScript, checkLearnLevel, checkRelevance, checkDescription, checkCaseSensitive, matchWholeWord, notSearch, vocableList);
		ObserveableFactory.getSearchObservable().fireSearchNotification();
		
		//If a vocable is in the result but is not already in the last search result, it needs to be added to the lastSearchResult (OR operation requires it in only one list) 
		for(int i = 0; i < lastSearchResultCopy.size(); i++) {
			if(isVocableInList(lastSearchResultCopy.get(i), lastSearchResult)) {
//				System.out.println("Vocable: |" + lastSearchResultCopy.get(i).getFirstLanguage() + "| is already existing.");
				continue;
			} else {
//				System.out.println("Vocable: |" + lastSearchResultCopy.get(i).getFirstLanguage() + "| will be removed.");
				lastSearchResult.add(lastSearchResultCopy.get(i));
			}
		}
		
		//lastSearchResult = lastSearchResultCopy;
		
//		for(Vocable vocable : lastSearchResult) {
//			System.out.println("lastSearchResult contains: |" + vocable.getFirstLanguage() + "|");
//		}
		
		return lastSearchResult;
	}

	/**
	 * Checks if two vocables have the same attribute values
	 * @param a one Vocable
	 * @param b the other Vocable
	 * @return true if the Vocables have the same attribute values
	 */
	private static boolean vocableAttributesEqual(Vocable a, Vocable b) {
		if(!a.getFirstLanguage().equals(b.getFirstLanguage())) {
			return false;
		} else if(!a.getPhoneticScript().equals(b.getPhoneticScript())) {
			return false;
		} else if(!a.getSecondLanguage().equals(b.getSecondLanguage())) {
			return false;
		} else if(!a.getChapter().equals(b.getChapter())) {
			return false;
		} else if(!a.getTopic().equals(b.getTopic())) {
			return false;
		} else if(!a.getLearnLevel().equals(b.getLearnLevel())) {
			return false;
		} else if(!a.getRelevance().equals(b.getRelevance())) {
			return false;
		} else if(!a.getDescription().equals(b.getDescription())) {
			return false;
		}
		//If none of the above returned false, true is returned
		return true;
	}

	/**
	 * checks if a vocable with the same attribute values is in a list of vocables
	 * @param vocable the vocable
	 * @param list the list of vocables
	 * @return true if the vocable with the same attribute values is in the list of vocables
	 */
	private static boolean isVocableInList(Vocable vocable, ArrayList<Vocable> list) {
		for(Vocable listVocable : list) {
			if(vocableAttributesEqual(vocable, listVocable)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Copies a vocable list using clone() on Vocable objects
	 * @param listOfVocables the list to be copied
	 * @return the copy of the vocable list
	 */
	private static ArrayList<Vocable> copyVocableList(ArrayList<Vocable> listOfVocables) {
		ArrayList<Vocable> result = new ArrayList<Vocable>();
		
		for(Vocable vocable : listOfVocables) {
			try {
				result.add((Vocable) vocable.clone());
			} catch (CloneNotSupportedException e) {
				System.out.println("Could not clone() Vocable instance!");
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	/**
	 * This method loads the vocableList from the vocable file specified in the
	 * settings file, or if that file does not exists attempts to load the
	 * standard vocable file. If none of these exist, it will create a new
	 * vocable file.
	 * 
	 * @param loadedVocableList the list of vocables which will be used in the dictionary 
	 * application. If a new vocable file is loaded this list will change.
	 */
	public static void setVocableList(ArrayList<Vocable> loadedVocableList) {
		vocableList = loadedVocableList;
		ObserveableFactory.getVocablesObserveable().fireVocableListLoadedNotification();
	}

	/**
	 * Sets a learn level for all vocables in the vocable list
	 * @param newLevel the new learn level
	 */
	public static void setLevelForAllVocables(String newLevel) {
		for (Vocable voc : VocableManager.getVocableList()) {
			voc.setLearnLevel(newLevel);
		}
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
	}

	/**
	 * Sets the learn level for all vocables in the given vocable list
	 * @param vocables the vocable list which contains the vocables which will be changed
	 * @param newLevel the new learn level
	 */
	public static void setLevelForVocables(ArrayList<Vocable> vocables,
			String newLevel) {
		for (Vocable voc : vocables) {
			voc.setLearnLevel(newLevel);
		}
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		FileManager.vocabularyIsSavedToFile = false;
	}

	/**
	 * Updates the lastSearchResult by searching the vocable list for the
	 * current search criteria and a given string
	 * @param searched the searched string
	 */
	public static void updateSearchResult() {
		String searched = SearchBox.getSearchTerm();
		boolean checkTopic = SearchBox.isTopicCheckboxSelected();
		boolean checkChapter = SearchBox.isChapterCheckboxSelected();
		boolean checkFirstLanguage = SearchBox.isFirstLanguageCheckboxSelected();
		boolean checkPhoneticScript = SearchBox.isPhoneticScriptCheckboxSelected();
		boolean checkSecondLanguage = SearchBox.isSecondLanguageCheckboxSelected();
		boolean checkLearnLevel = SearchBox.isLearnLevelCheckboxSelected();
		boolean checkRelevance = SearchBox.isRelevanceCheckboxSelected();
		boolean checkDescription = SearchBox.isDescriptionCheckboxSelected();
		boolean checkCaseSensitive = SearchBox.isCaseSensitiveCheckboxSelected();
		boolean checkExactMatch = SearchBox.isExactMatchCheckboxSelected();
		boolean negateSearch = SearchBox.isNegateSearchCheckboxSelected();

		lastSearchResult = VocableManager.searchVocableList(
			searched, 
			checkTopic, 
			checkChapter,
			checkFirstLanguage, 
			checkSecondLanguage, 
			checkPhoneticScript,
			checkLearnLevel,
			checkRelevance,
			checkDescription,
			checkCaseSensitive, 
			checkExactMatch,
			negateSearch, 
			VocableManager.getVocableList()
		);
		
		ObserveableFactory.getSearchObservable().fireSearchNotification();
	}

	
	/**
	 * This method check wether a {@link Vocable} is already in the vocable list or not, 
	 * by using the attributes firstLanguage, phoneticScript and secondLanguage as 
	 * uniquely identifying attributes.
	 * @param firstLanguage the firstLanguage
	 * @param phoneticScript the phonetic Script
	 * @param secondLanguage the secondLanguage
	 * @return returns true if the {@link Vocable} is already in the list of {@link Vocable}s
	 */
	public static boolean isVocableAlreadyInVocableList(String firstLanguage, String phoneticScript, String secondLanguage) {
		for(Vocable vocable : VocableManager.getVocableList()) {
			if(
				vocable.getFirstLanguage().equals(firstLanguage) &&
				vocable.getPhoneticScript().equals(phoneticScript) &&
				vocable.getSecondLanguage().equals(secondLanguage)
			) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the current vocableList.
	 * @return the current vocableList.
	 */
	public static ArrayList<Vocable> getVocableList() {
		return vocableList;
	}

	public static ArrayList<Vocable> getLastSearchResult() {
		return VocableManager.lastSearchResult;
	}

	/**
	 * Only accessed by SwingWorker
	 * @param match a search criteria matching {@link Vocable}
	 */
	public static void addVocableToSearchResult(Vocable match) {
		lastSearchResult.add(match);
	}

	/**
	 * Only accessed by SwingWorker
	 * @param temporaryResult the result from the SwingWorker
	 */
	public static void setLastSearchresult(ArrayList<Vocable> temporaryResult) {
		lastSearchResult = temporaryResult;
	}

	/**
	 * This method returns the {@link Vocable}, which has the three attributes first language, 
	 * phonetic script and secondd language with the given values.
	 * @param firstLanguage the firstLanguage attribute of the {@link Vocable}
	 * @param phoneticScript the phoneticScript attribute of the {@link Vocable}
	 * @param secondLanguage the secondLanguage attribute of the {@link Vocable}
	 * @return the {@link Vocable} having the three attributes with the given values.
	 */
	public static Vocable getVocableWith(String firstLanguage, String phoneticScript, String secondLanguage) {
		for(Vocable vocable : VocableManager.getVocableList()) {
			if(
				vocable.getFirstLanguage().equals(firstLanguage) &&
				vocable.getPhoneticScript().equals(phoneticScript) &&
				vocable.getSecondLanguage().equals(secondLanguage)
			) {
				return vocable;
			}
		}
		return null;
	}
	
	/**
	 * This method returns the {@link Vocable} of the lastSearchResult, which has the three attributes first language, 
	 * phonetic script and secondd language with the given values.
	 * @param firstLanguage the firstLanguage attribute of the {@link Vocable}
	 * @param phoneticScript the phoneticScript attribute of the {@link Vocable}
	 * @param secondLanguage the secondLanguage attribute of the {@link Vocable}
	 * @return the {@link Vocable} having the three attributes with the given values.
	 */
	public static Vocable getVocableFromSearchResultWith(String firstLanguage, String phoneticScript, String secondLanguage) {
		for(Vocable vocable : VocableManager.getLastSearchResult()) {
			if(
				vocable.getFirstLanguage().equals(firstLanguage) &&
				vocable.getPhoneticScript().equals(phoneticScript) &&
				vocable.getSecondLanguage().equals(secondLanguage)
			) {
				return vocable;
			}
		}
		return null;
	}

}
