package dictionary;

public class SearchCriteriaHistoryItem {
	
	public boolean checkFirstLanguage;
	public boolean checkPhoneticScript;
	public boolean checkSecondLanguage;
	public boolean checkTopic;
	public boolean checkChapter;
	public boolean checkLearnLevel;
	public boolean checkRelevance;
	public boolean checkDescription;
	public boolean checkCaseSensitive;
	public boolean matchWholeWord;
	
	public boolean notSearch;
	public boolean andSearch;
	public boolean orSearch;
	
	public String searchTerm;
	
	public SearchCriteriaHistoryItem(
			boolean checkFirstLanguage, 
			boolean checkPhoneticScript, 
			boolean checkSecondLanguage, 
			boolean checkTopic, 
			boolean checkChapter, 
			boolean checkLearnLevel, 
			boolean checkRelevance,
			boolean checkDescription,
			boolean checkCaseSensitive,
			boolean matchWholeWord, 
			boolean notSearch, 
			boolean andSearch, 
			boolean orSearch, 
			String searchTerm) {
		this.checkFirstLanguage = checkFirstLanguage;
		this.checkPhoneticScript = checkPhoneticScript;
		this.checkSecondLanguage = checkSecondLanguage;
		this.checkTopic = checkTopic;
		this.checkChapter = checkChapter;
		this.checkLearnLevel = checkLearnLevel;
		this.checkRelevance = checkRelevance;
		this.checkDescription = checkDescription;
		this.checkCaseSensitive = checkCaseSensitive;
		this.matchWholeWord = matchWholeWord;
		this.notSearch = notSearch;
		this.andSearch = andSearch;
		this.orSearch = orSearch;
		this.searchTerm = searchTerm;
	}
}
