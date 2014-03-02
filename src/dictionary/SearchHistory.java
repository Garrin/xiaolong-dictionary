package dictionary;

import gui.SearchBox;

import java.util.ArrayList;

public class SearchHistory {

	private static int currentlyUsedElementsCount = 0;
	private static int currentSearchTermPointer = 0;
	private static int maxLength = Settings.searchHistoryMaximumLength;
	private static ArrayList<SearchEntry> entryList = new ArrayList<SearchEntry>();
	
	/**
	 * This function adds a SearchEntry at the end of the SearchEntry list or in between previously added SearchEntry list items if the currentSearchTermPointer doesn't point at the last element in the list of SearchEntry elements.
	 * @param newEntry the SearchEntry that includes information the search performed by the user 
	 */
	public static void addEntry(SearchEntry newEntry) {
		if(SearchHistory.currentlyUsedElementsCount == maxLength) { //Limit reached
			//System.out.println("SearchHistory maxlength reached");
			if(SearchHistory.currentSearchTermPointer != currentlyUsedElementsCount-1) { //Insert entry after last displayed entry and shift the rest of the list after this new entry
				removeOldestEntry();
				entryList.add(currentSearchTermPointer, newEntry);
				currentlyUsedElementsCount++;
			} else {
				removeOldestEntry();
				entryList.add(newEntry);
				currentlyUsedElementsCount++;
			}
			
		} else { //Limit not reached
			//System.out.println("SearchHistory maxlength NOT reached");
			if(SearchHistory.currentSearchTermPointer != currentlyUsedElementsCount-1) { //Add the element in between
				if(currentlyUsedElementsCount == 0 && currentSearchTermPointer == 0) {
					entryList.add(currentSearchTermPointer, newEntry);
					currentlyUsedElementsCount++;
				} else {
					entryList.add(currentSearchTermPointer+1, newEntry);
					currentSearchTermPointer++;
					currentlyUsedElementsCount++;
				}
			} else { //Simply add it to the end of the list
				entryList.add(newEntry);
				currentlyUsedElementsCount++;
				currentSearchTermPointer = currentlyUsedElementsCount-1;
			}
		}
	}
	
	
	public static SearchEntry getPreviousSearchTerm() {
		//System.out.println("Before Previous:\nPointing on:"+currentSearchTermPointer+"\nLength:"+maxLength+"\nUsed slots:"+currentlyUsedElementsCount);
		//System.out.println("-----");
		if(currentSearchTermPointer > 0) {
			currentSearchTermPointer--;
			//System.out.println("After/ Previous:\nPointing on:"+currentSearchTermPointer+"\nLength:"+maxLength+"\nUsed slots:"+currentlyUsedElementsCount);
			//System.out.println("-----");
			return entryList.get(currentSearchTermPointer);
		} else { //pointer already points at the beginning of the list
			//System.out.println("After/ Previous:\nPointing on:"+currentSearchTermPointer+"\nLength:"+maxLength+"\nUsed slots:"+currentlyUsedElementsCount);
			//System.out.println("-----");
			return entryList.get(0);
		}
	}
	
	public static SearchEntry getNextSearchTerm() {
		//System.out.println("Before Next:\nPointing on:"+currentSearchTermPointer+"\nLength:"+maxLength+"\nUsed slots:"+currentlyUsedElementsCount);
		//System.out.println("-----");
		if(currentSearchTermPointer < currentlyUsedElementsCount-1) {
			currentSearchTermPointer++;
			//System.out.println("After Next:\nPointing on:"+currentSearchTermPointer+"\nLength:"+maxLength+"\nUsed slots:"+currentlyUsedElementsCount);
			//System.out.println("-----");
			return entryList.get(currentSearchTermPointer);
		} else { //pointer already points at the end of the list
			//System.out.println("After Next:\nPointing on:"+currentSearchTermPointer+"\nLength:"+maxLength+"\nUsed slots:"+currentlyUsedElementsCount);
			//System.out.println("-----");
			return entryList.get(currentSearchTermPointer);
		}
	}

	private static void removeOldestEntry() {
		entryList.remove(0);
		currentlyUsedElementsCount--;
	}
	
	public static void printSearchHistory() {
		for(SearchEntry entry : entryList) {
			System.out.println(entry.searchTerm);
		}
		System.out.println("-----");
	}
	
	public static ArrayList<SearchEntry> getSearchEntryList() {
		return entryList;
	}
	
	public static void restoreSearchHistory() {
		String[] searchHistoryItems = Settings.searchHistory.split(",");
		for(String searchHistoryItem : searchHistoryItems) {
			if(!searchHistoryItem.equals("")) {
				String[] searchHistoryItemMembers = searchHistoryItem.split(":");
				try {
					String searchTerm = searchHistoryItemMembers[0];
					Boolean firstLanguageCheckboxSelected = new Boolean(searchHistoryItemMembers[1]);
					Boolean phoneticScriptCheckboxSelected = new Boolean(searchHistoryItemMembers[2]);
					Boolean secondLanguageCheckboxSelected = new Boolean(searchHistoryItemMembers[3]);
					Boolean topicCheckboxSelected = new Boolean(searchHistoryItemMembers[4]);
					Boolean chapterCheckboxSelected = new Boolean(searchHistoryItemMembers[5]);
					Boolean learnLevelCheckboxSelected = new Boolean(searchHistoryItemMembers[6]);
					Boolean relevanceCheckboxSelected = new Boolean(searchHistoryItemMembers[7]);
					Boolean descriptionCheckboxSelected = new Boolean(searchHistoryItemMembers[8]);
					Boolean caseSensitiveCheckboxSelected = new Boolean(searchHistoryItemMembers[9]);
					Boolean exactMatchCheckboxSelected = new Boolean(searchHistoryItemMembers[10]);
					Boolean negateSearchCheckboxSelected = new Boolean(searchHistoryItemMembers[11]);
					
					addEntry(
						new SearchEntry(firstLanguageCheckboxSelected, phoneticScriptCheckboxSelected, secondLanguageCheckboxSelected, topicCheckboxSelected, chapterCheckboxSelected, learnLevelCheckboxSelected, relevanceCheckboxSelected, descriptionCheckboxSelected, caseSensitiveCheckboxSelected, exactMatchCheckboxSelected, negateSearchCheckboxSelected, searchTerm)
					);
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static void setMaxHistoryLength(int newLength) {
		int oldLength = SearchHistory.maxLength;
		int oldPointer = currentSearchTermPointer;
		
		if(oldLength > newLength) { //Verkleinere Liste
			deleteSearchEntries(SearchHistory.maxLength - newLength);
			SearchHistory.maxLength = newLength;
			
			if(currentlyUsedElementsCount > newLength) {
				currentlyUsedElementsCount = newLength;
			
				if(currentSearchTermPointer > SearchHistory.maxLength-1) {
					currentSearchTermPointer = newLength-1;
				}
			}
			
			//Update SearchTerm
			if(oldPointer == 0) {
				SearchBox.updateSearchBoxSearchTerm(entryList.get(currentSearchTermPointer).searchTerm);
			} else {
				SearchBox.updateSearchBoxSearchTerm(entryList.get(currentSearchTermPointer-1).searchTerm);
			}
			
		} else if(SearchHistory.maxLength < newLength) { //Vergroeßere Liste
			SearchHistory.maxLength = newLength;
		}
		
		/*for(SearchEntry entry : SearchHistory.entryList) {
			System.out.println(entry.searchTerm);
		}*/
	}
	
	private static void deleteSearchEntries(int deleteCount) {
		int i = 0;
		while(i < deleteCount) {
			entryList.remove(0);
			i++;
		}
	}
}
