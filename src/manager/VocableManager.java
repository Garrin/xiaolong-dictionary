package manager;

import java.util.ArrayList;

import dictionary.Dictionary;
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
		
		lastSearchResult = searchVocableList(
				lastSearchCriterias.get(0).searchTerm, 
				lastSearchCriterias.get(0).checkTopic,
				lastSearchCriterias.get(0).checkChapter,
				lastSearchCriterias.get(0).checkFirstLanguage,
				lastSearchCriterias.get(0).checkSecondLanguage,
				lastSearchCriterias.get(0).checkPhoneticScript, 
				lastSearchCriterias.get(0).checkLearnLevel, 
				lastSearchCriterias.get(0).checkCaseSensitive, 
				lastSearchCriterias.get(0).matchWholeWord, 
				lastSearchCriterias.get(0).notSearch, 
				VocableManager.getVocableList()
		);
		
		//TODO check if the vocable needs to be in the search result
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
						lastSearchCriterias.get(i).checkCaseSensitive, 
						lastSearchCriterias.get(i).matchWholeWord, 
						lastSearchCriterias.get(i).notSearch
				);
				
			}
		}
		
		ObserveableFactory.getVocablesObserveable().fireAddVocableNotification(vocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		Dictionary.vocabularyIsSavedToFile = false;
	}

	/**
	 * deletes a vocable from the vocableList
	 * 
	 * @param vocable
	 *            the vocable, which will be deleted
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
		Dictionary.vocabularyIsSavedToFile = false;
	}

	/**
	 * removes the vocable with the given attributes from the vocableList
	 * 
	 * @param topic
	 *            topic of the vocable which will be deleted
	 * @param chapter
	 *            chapter of the vocable which will be deleted
	 * @param firstLanguage
	 *            first language of the vocable which will be deleted
	 * @param secondLanguage
	 *            second language of the vocable which will be deleted
	 * @param phoneticScript
	 *            phonetic script of the vocable which will be deleted
	 * @param learnLevel
	 *            learn level of the vocable which will be deleted
	 */
	public static void deleteVocable(
			String topic, 
			String chapter,
			String firstLanguage, 
			String secondLanguage, 
			String phoneticScript,
			String learnLevel) {
		
		Vocable vocabletoDelete = null;
		
		//delete vocable from list of vocables
		for (int i = 0; i < vocableList.size(); i++) {

			if (vocableList.get(i).getTopic().equals(topic)
					&& vocableList.get(i).getChapter().equals(chapter)
					&& vocableList.get(i).getFirstLanguage().equals(firstLanguage)
					&& vocableList.get(i).getSecondLanguage().equals(secondLanguage)
					&& vocableList.get(i).getPhoneticScript().equals(phoneticScript)
					&& vocableList.get(i).getLearnLevel().equals(learnLevel)) {

				vocabletoDelete = vocableList.get(i);
				vocableList.remove(i);
			}
		}
		
		//delete vocable from last search result
		for(Vocable vocable : lastSearchResult) {
			if (vocable.getTopic().equals(topic) &&
				vocable.getChapter().equals(chapter) &&
				vocable.getFirstLanguage().equals(firstLanguage) &&
				vocable.getSecondLanguage().equals(secondLanguage) &&
				vocable.getPhoneticScript().equals(phoneticScript) &&
				vocable.getLearnLevel().equals(learnLevel)) {
				lastSearchResult.remove(vocable);
			}
		}
		
		ObserveableFactory.getVocablesObserveable().fireDeleteVocableNotification(vocabletoDelete);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		Dictionary.vocabularyIsSavedToFile = false;
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
		Dictionary.vocabularyIsSavedToFile = false;
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
			String newLearnLevel) {
		
		Vocable newVocable = new Vocable(newTopic, newChapter, newFirstLanguage, newSecondLanguage, newPhoneticScript, newLearnLevel);
		
		VocableManager.getVocableList().set(VocableManager.getVocableList().indexOf(oldVocable), newVocable);
		
		if(lastSearchResult.contains(oldVocable)) {
			lastSearchResult.set(lastSearchResult.indexOf(oldVocable), newVocable); //TODO: Causes error when the user searches while in training!
		}
		
		
		ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(oldVocable, newVocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		Dictionary.vocabularyIsSavedToFile = false;
	}
	
	/**
	 * changes the first language of a given vocable
	 * @param oldVocable the vocable which will be changed
	 * @param newFirstLanguage the new first language
	 */
	public static void changeVocableFirstLanguage(Vocable oldVocable, String newFirstLanguage) {
		//Vocable newVocable = new Vocable(oldVocable.getTopic(), oldVocable.getChapter(), oldVocable.getFirstLanguage(), oldVocable.getSecondLanguage(), oldVocable.getPhoneticScript(), oldVocable.getLearnLevel());
		Vocable newVocable = new Vocable(oldVocable.getTopic(), oldVocable.getChapter(), newFirstLanguage, oldVocable.getSecondLanguage(), oldVocable.getPhoneticScript(), oldVocable.getLearnLevel());
		
		VocableManager.getVocableList().set(VocableManager.getVocableList().indexOf(oldVocable), newVocable);
		
		if(lastSearchResult.contains(oldVocable)) {
			lastSearchResult.set(lastSearchResult.indexOf(oldVocable), newVocable); //TODO: Causes error when the user searches while in training!
		}
		
		ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(oldVocable, newVocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		Dictionary.vocabularyIsSavedToFile = false;
	}

	/**
	 * changes the phonetic script of a given vocable
	 * @param oldVocable the vocable which will be changed
	 * @param newPhoneticScript the new phonetic script
	 */
	public static void changeVocablePhoneticScript(Vocable oldVocable, String newPhoneticScript) {
		//Vocable newVocable = new Vocable(oldVocable.getTopic(), oldVocable.getChapter(), oldVocable.getFirstLanguage(), oldVocable.getSecondLanguage(), oldVocable.getPhoneticScript(), oldVocable.getLearnLevel());
		Vocable newVocable = new Vocable(oldVocable.getTopic(), oldVocable.getChapter(), oldVocable.getFirstLanguage(), oldVocable.getSecondLanguage(), newPhoneticScript, oldVocable.getLearnLevel());
		
		VocableManager.getVocableList().set(VocableManager.getVocableList().indexOf(oldVocable), newVocable);
		
		if(lastSearchResult.contains(oldVocable)) {
			lastSearchResult.set(lastSearchResult.indexOf(oldVocable), newVocable); //TODO: Causes error when the user searches while in training!
		}
		
		ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(oldVocable, newVocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		Dictionary.vocabularyIsSavedToFile = false;
	}
	
	/**
	 * changes the second language of a given vocable
	 * @param oldVocable the vocable that will be changed
	 * @param newSecondLanguage the new second language
	 */
	public static void changeVocableSecondLanguage(Vocable oldVocable, String newSecondLanguage) {
		//Vocable newVocable = new Vocable(oldVocable.getTopic(), oldVocable.getChapter(), oldVocable.getFirstLanguage(), oldVocable.getSecondLanguage(), oldVocable.getPhoneticScript(), oldVocable.getLearnLevel());
		Vocable newVocable = new Vocable(oldVocable.getTopic(), oldVocable.getChapter(), oldVocable.getFirstLanguage(), newSecondLanguage, oldVocable.getPhoneticScript(), oldVocable.getLearnLevel());
		
		VocableManager.getVocableList().set(VocableManager.getVocableList().indexOf(oldVocable), newVocable);
		
		if(lastSearchResult.contains(oldVocable)) {
			lastSearchResult.set(lastSearchResult.indexOf(oldVocable), newVocable); //TODO: Causes error when the user searches while in training!
		}
		
		ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(oldVocable, newVocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		Dictionary.vocabularyIsSavedToFile = false;
	}

	/**
	 * changes the chapter of a given vocable
	 * @param oldVocable the vocable which will be changed
	 * @param newChapter the new chapter
	 */
	public static void changeVocableChapter(Vocable oldVocable, String newChapter) {
		//Vocable newVocable = new Vocable(oldVocable.getTopic(), oldVocable.getChapter(), oldVocable.getFirstLanguage(), oldVocable.getSecondLanguage(), oldVocable.getPhoneticScript(), oldVocable.getLearnLevel());
		Vocable newVocable = new Vocable(oldVocable.getTopic(), newChapter, oldVocable.getFirstLanguage(), oldVocable.getSecondLanguage(), oldVocable.getPhoneticScript(), oldVocable.getLearnLevel());
		
		VocableManager.getVocableList().set(VocableManager.getVocableList().indexOf(oldVocable), newVocable);
		
		if(lastSearchResult.contains(oldVocable)) {
			lastSearchResult.set(lastSearchResult.indexOf(oldVocable), newVocable); //TODO: Causes error when the user searches while in training!
		}
		
		ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(oldVocable, newVocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		Dictionary.vocabularyIsSavedToFile = false;
	}

	/**
	 * Changes the topic of a given vocable
	 * @param oldVocable the vocable which will be changed
	 * @param newTopic the new topic
	 */
	public static void changeVocableTopic(Vocable oldVocable, String newTopic) {
		//Vocable newVocable = new Vocable(oldVocable.getTopic(), oldVocable.getChapter(), oldVocable.getFirstLanguage(), oldVocable.getSecondLanguage(), oldVocable.getPhoneticScript(), oldVocable.getLearnLevel());
		Vocable newVocable = new Vocable(newTopic, oldVocable.getChapter(), oldVocable.getFirstLanguage(), oldVocable.getSecondLanguage(), oldVocable.getPhoneticScript(), oldVocable.getLearnLevel());
		
		VocableManager.getVocableList().set(VocableManager.getVocableList().indexOf(oldVocable), newVocable);
		
		if(lastSearchResult.contains(oldVocable)) {
			lastSearchResult.set(lastSearchResult.indexOf(oldVocable), newVocable); //TODO: Causes error when the user searches while in training!
		}
		
		ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(oldVocable, newVocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		Dictionary.vocabularyIsSavedToFile = false;
	}

	/**
	 * Changes the learn level of a given vocable
	 * @param oldVocable the vocable which will be changed
	 * @param newLearnLevel the new learn level
	 */
	public static void changeVocableLearnLevel(Vocable oldVocable, String newLearnLevel) {
		//Vocable newVocable = new Vocable(oldVocable.getTopic(), oldVocable.getChapter(), oldVocable.getFirstLanguage(), oldVocable.getSecondLanguage(), oldVocable.getPhoneticScript(), oldVocable.getLearnLevel());
		Vocable newVocable = new Vocable(oldVocable.getTopic(), oldVocable.getChapter(), oldVocable.getFirstLanguage(), oldVocable.getSecondLanguage(), oldVocable.getPhoneticScript(), newLearnLevel);
		
		VocableManager.getVocableList().set(VocableManager.getVocableList().indexOf(oldVocable), newVocable);
		
		if(lastSearchResult.contains(oldVocable)) {
			lastSearchResult.set(lastSearchResult.indexOf(oldVocable), newVocable); //TODO: Causes error when the user searches while in training!
		}
		
		ObserveableFactory.getVocablesObserveable().fireChangeVocableNotification(oldVocable, newVocable);
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		Dictionary.vocabularyIsSavedToFile = false;
	}
	
	
	/**
	 * Searches through the whole vocableList for vocables matching the search criteria
	 * 
	 * @param searched the searched string
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
//	public static ArrayList<Vocable> searchVocable(String searched,
//			boolean checkTopic, boolean checkChapter,
//			boolean checkFirstLanguage, boolean checkSecondLanguage,
//			boolean checkPhoneticScript, boolean checkLevel,
//			boolean checkCaseSensitive, boolean checkExactMatch) {
//		ArrayList<Vocable> result = new ArrayList<Vocable>();
//
//		if (!checkCaseSensitive) {
//			searched = searched.toUpperCase();
//		}
//
//		// SEARCH
//		for (int i = 0; i < VocableManager.getVocableList().size(); i++) {
//
//			String topic = VocableManager.getVocableList().get(i).getTopic();
//			String chapter = VocableManager.getVocableList().get(i)
//					.getChapter();
//			String learnLevel = VocableManager.getVocableList().get(i)
//					.getLearnLevel();
//			String firstLanguage = VocableManager.getVocableList().get(i)
//					.getFirstLanguage();
//			String phoneticScript = VocableManager.getVocableList().get(i)
//					.getPhoneticScript();
//			String secondLanguage = VocableManager.getVocableList().get(i)
//					.getSecondLanguage();
//			boolean alreadyAddedToSearchResult = false;
//
//			if (checkTopic) {
//				final String[] parts = topic
//						.split(Settings.vocableOptions_translationsSeperator);
//				for (String part : parts) {
//					if (isSearchedStringInAttributeOfVocable(checkExactMatch,
//							checkCaseSensitive, searched, part)) {
//						result.add(VocableManager.getVocableList().get(i));
//						alreadyAddedToSearchResult = true;
//						break;
//					}
//				}
//			}
//
//			if (checkChapter && !alreadyAddedToSearchResult) {
//				final String[] parts = chapter
//						.split(Settings.vocableOptions_translationsSeperator);
//				for (String part : parts) {
//					if (isSearchedStringInAttributeOfVocable(checkExactMatch,
//							checkCaseSensitive, searched, part)) {
//						result.add(VocableManager.getVocableList().get(i));
//						alreadyAddedToSearchResult = true;
//						break;
//					}
//				}
//			}
//
//			if (checkFirstLanguage && !alreadyAddedToSearchResult) {
//				final String[] parts = firstLanguage
//						.split(Settings.vocableOptions_translationsSeperator);
//				for (String part : parts) {
//					if (isSearchedStringInAttributeOfVocable(checkExactMatch,
//							checkCaseSensitive, searched, part)) {
//						result.add(VocableManager.getVocableList().get(i));
//						alreadyAddedToSearchResult = true;
//						break;
//					}
//				}
//			}
//
//			if (checkSecondLanguage && !alreadyAddedToSearchResult) {
//				final String[] parts = secondLanguage
//						.split(Settings.vocableOptions_translationsSeperator);
//				for (String part : parts) {
//					if (isSearchedStringInAttributeOfVocable(checkExactMatch,
//							checkCaseSensitive, searched, part)) {
//						result.add(VocableManager.getVocableList().get(i));
//						alreadyAddedToSearchResult = true;
//						break;
//					}
//				}
//			}
//
//			if (checkPhoneticScript && !alreadyAddedToSearchResult) {
//				final String[] parts = phoneticScript
//						.split(Settings.vocableOptions_translationsSeperator);
//				for (String part : parts) {
//					if (isSearchedStringInAttributeOfVocable(checkExactMatch,
//							checkCaseSensitive, searched, part)) {
//						result.add(VocableManager.getVocableList().get(i));
//						alreadyAddedToSearchResult = true;
//						break;
//					}
//				}
//			}
//
//			if (checkLevel && !alreadyAddedToSearchResult) {
//				final String[] parts = learnLevel
//						.split(Settings.vocableOptions_translationsSeperator);
//				for (String part : parts) {
//					if (isSearchedStringInAttributeOfVocable(checkExactMatch,
//							checkCaseSensitive, searched, part)) {
//						result.add(VocableManager.getVocableList().get(i));
//						alreadyAddedToSearchResult = true;
//						break;
//					}
//				}
//			}
//		}
//
//		VocableManager.lastSearchResult = result;
//		ObserveableFactory.getSearchObservable().fireSearchNotification();
//		return result;
//	}

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

	
	public static ArrayList<Vocable> basicSearchVocableList(
			String searchTerm,
			boolean checkTopic, 
			boolean checkChapter,
			boolean checkFirstLanguage, 
			boolean checkSecondLanguage,
			boolean checkPhoneticScript, 
			boolean checkLearnLevel,
			boolean checkCaseSensitive, 
			boolean matchWholeWord,
			boolean notSearch) {
		
		//empty search criteria list, because it's a new search
		lastSearchCriterias.clear();
		//add new item to search criteria list
		lastSearchCriterias.add(
				new SearchCriteriaHistoryItem(checkFirstLanguage, checkPhoneticScript, checkSecondLanguage, checkTopic, checkChapter, checkLearnLevel, checkCaseSensitive, matchWholeWord, notSearch, false, false, searchTerm)
		);
		
		lastSearchResult = VocableManager.searchVocableList(searchTerm, checkTopic, checkChapter, checkFirstLanguage, checkSecondLanguage, checkPhoneticScript, checkLearnLevel, checkCaseSensitive, matchWholeWord, notSearch, VocableManager.getVocableList()); 
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
			boolean checkCaseSensitive, 
			boolean matchWholeWord,
			boolean notSearch, 
			boolean andSearch, 
			boolean orSearch) {
			
		ArrayList<Vocable> result;
		
		if(andSearch) { //AND Search
			lastSearchCriterias.add(
					new SearchCriteriaHistoryItem(checkFirstLanguage, checkPhoneticScript, checkSecondLanguage, checkTopic, checkChapter, checkLearnLevel, checkCaseSensitive, matchWholeWord, notSearch, true, false, searchTerm)
			);
			result = performANDSearch(searchTerm, checkTopic, checkChapter, checkFirstLanguage, checkSecondLanguage, checkPhoneticScript, checkLearnLevel, checkCaseSensitive, matchWholeWord, notSearch); 
		} else { //OR Search
			lastSearchCriterias.add(
					new SearchCriteriaHistoryItem(checkFirstLanguage, checkPhoneticScript, checkSecondLanguage, checkTopic, checkChapter, checkLearnLevel, checkCaseSensitive, matchWholeWord, notSearch, false, true, searchTerm)
			);
			result = performORSearch(searchTerm, checkTopic, checkChapter, checkFirstLanguage, checkSecondLanguage, checkPhoneticScript, checkLearnLevel, checkCaseSensitive, matchWholeWord, notSearch);
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
			boolean checkCaseSensitive, 
			boolean matchWholeWord,
			boolean notSearch) {
		
		lastSearchResult = searchVocableList(searchTerm, checkTopic, checkChapter, checkFirstLanguage, checkSecondLanguage, checkPhoneticScript, checkLearnLevel, checkCaseSensitive, matchWholeWord, notSearch, lastSearchResult);
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
			boolean checkCaseSensitive, 
			boolean matchWholeWord, 
			boolean notSearch) {
		
		ArrayList<Vocable> lastSearchResultCopy = new ArrayList<Vocable>();
		
		//Make a copy of the current lastSearchResult by iterating over the Vocables, cloning them and saving a reference in the lastSearchResultCopy array.
		lastSearchResultCopy = VocableManager.copyVocableList(lastSearchResult); 
		
		//sets a new list of vocables for lastSearchResult ...
		lastSearchResult = VocableManager.searchVocableList(searchTerm, checkTopic, checkChapter, checkFirstLanguage, checkSecondLanguage, checkPhoneticScript, checkLearnLevel, checkCaseSensitive, matchWholeWord, notSearch, vocableList);
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
		} else {
			return true;
		}
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
	 * @param loadedVocableList
	 *            the list of vocables which will be used in the dictionary
	 *            application. If a new vocable file is loaded this list will
	 *            change.
	 */
	public static void setVocableList(ArrayList<Vocable> loadedVocableList) {
		vocableList = loadedVocableList;
		ObserveableFactory.getVocablesObserveable()
				.fireVocableListLoadedNotification();
	}

	/**
	 * Sets a learn level for all vocables in the vocable list
	 * 
	 * @param newLevel
	 *            the new learn level
	 */
	public static void setLevelForAllVocables(String newLevel) {
		for (Vocable voc : VocableManager.getVocableList()) {
			voc.setLearnLevel(newLevel);
		}
		ObserveableFactory.getVocablesObserveable()
				.fireVocableListChangedNotification();
	}

	/**
	 * Sets the learn level for all vocables in the given vocable list
	 * 
	 * @param vocables
	 *            the vocable list which contains the vocables which will be
	 *            changed
	 * @param newLevel
	 *            the new learn level
	 */
	public static void setLevelForVocables(ArrayList<Vocable> vocables,
			String newLevel) {
		for (Vocable voc : vocables) {
			voc.setLearnLevel(newLevel);
		}
		ObserveableFactory.getVocablesObserveable().fireVocableListChangedNotification();
		Dictionary.vocabularyIsSavedToFile = false;
	}

	/**
	 * Updates the lastSearchResult by searching the vocable list for the
	 * current search criteria and a given string
	 * 
	 * @param searched
	 *            the searched string
	 */
	public static void updateSearchResult() {
		String searched = SearchBox.getSearchTerm();
		boolean checkTopic = SearchBox.isTopicCheckboxSelected();
		boolean checkChapter = SearchBox.isChapterCheckboxSelected();
		boolean checkFirstLanguage = SearchBox.isFirstLanguageCheckboxSelected();
		boolean checkPhoneticScript = SearchBox.isPhoneticScriptCheckboxSelected();
		boolean checkSecondLanguage = SearchBox.isSecondLanguageCheckboxSelected();
		boolean checkLearnLevel = SearchBox.isLearnLevelCheckboxSelected();
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
				checkCaseSensitive, 
				checkExactMatch,
				negateSearch, 
				VocableManager.getVocableList());
		ObserveableFactory.getSearchObservable().fireSearchNotification();
	}

	/**
	 * Returns the current vocableList.
	 * 
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
	
//	/**
//	 * Checks if a vocable is in the last search result
//	 * @return
//	 */
//	private static boolean isVocableInSearchresult(Vocable vocable) {
//		return lastSearchResult.contains(vocable);
//	}
//	
//	/**
//	 * Checks if a vocable, which has been added while a search result is displayed is in that search result
//	 * @return
//	 */
//	private static boolean isAddedVocableInSearchResult() {
//		
//		boolean result = false;
//		
//		for(SearchCriteriaHistoryItem item : lastSearchCriterias) {
//			
//		}
//		
//		return result;
//	}

}
