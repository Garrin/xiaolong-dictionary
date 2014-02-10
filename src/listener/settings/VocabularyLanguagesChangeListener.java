package listener.settings;

public interface VocabularyLanguagesChangeListener {
	
	/**
	 * This method changes the vocabulary languages whereever they are used in this listener.
	 * @param newFirstLanguageName the new first language
	 * @param newPhoneticScriptName the new phonetic script
	 * @param newSecondLanguageName the new second language
	 */
	public void changeVocabularyLanguages(String newFirstLanguageName, String newPhoneticScriptName, String newSecondLanguageName);
}
