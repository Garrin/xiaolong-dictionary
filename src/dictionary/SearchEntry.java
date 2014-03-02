package dictionary;

public class SearchEntry {
	
	public boolean firstLanguageSelected;
	public boolean phoneticScriptSelected;
	public boolean secondLanguageSelected;
	public boolean topicSelected;
	public boolean chapterSelecter;
	public boolean learnLevelSelected;
	public boolean relevanceSelected;
	public boolean descriptionSelected;
	public boolean caseSensitiveSelected;
	public boolean exactMatchSelected;
	public boolean negateSearchSelected;
	
	public String searchTerm;
	
	public SearchEntry(
		boolean firstLanguageSelected,
		boolean phoneticScriptSelected,
		boolean secondLanguageSelected,
		boolean topicSelected,
		boolean chapterSelecter,
		boolean levelSelected,
		boolean relevanceSelected,
		boolean descriptionSelected,
		boolean caseSensitiveSelected,
		boolean exactMatchSelected,
		boolean negateSearchSelected,
		String searchTerm
	) {	
		this.firstLanguageSelected = firstLanguageSelected;
		this.phoneticScriptSelected = phoneticScriptSelected;
		this.secondLanguageSelected = secondLanguageSelected;
		this.topicSelected = topicSelected;
		this.chapterSelecter = chapterSelecter;
		this.learnLevelSelected = levelSelected;
		this.relevanceSelected = relevanceSelected;
		this.descriptionSelected = descriptionSelected;
		this.caseSensitiveSelected = caseSensitiveSelected;
		this.exactMatchSelected = exactMatchSelected;
		this.negateSearchSelected = negateSearchSelected;
		
		this.searchTerm = searchTerm;
	}
}
