package observables.settings;

import java.util.ArrayList;

import listener.settings.VocabularyLanguagesChangeListener;

public enum VocabularyLanguagesObservable {
	INSTANCE;
	
	private static String firstLanguageName;
	private static String phoneticScriptName;
	private static String secondLanguageName;
	
	private static ArrayList<VocabularyLanguagesChangeListener> listeners = new ArrayList<VocabularyLanguagesChangeListener>();
	

	/**
	 * This method registers a new listener, so that this new listener can be notified if the language names change.
	 * @param listener the registered {@link VocabularyLanguagesChangeListener}
	 */
	public void registerListener(VocabularyLanguagesChangeListener listener) {
		listeners.add(listener);
	}
	
	
	/**
	 * Notifies all listeners of the change of language names
	 */
	private void notifyListeners() {
		for(VocabularyLanguagesChangeListener vocabularyLanguagesChangeListener : listeners) {
			//System.out.println("Notifying:"+vocabularyLanguagesChangeListener);
			vocabularyLanguagesChangeListener.changeVocabularyLanguages(VocabularyLanguagesObservable.firstLanguageName, VocabularyLanguagesObservable.phoneticScriptName, VocabularyLanguagesObservable.secondLanguageName);
		}
	}
	
	/**
	 * sets the name for first language
	 * @param firstLanguageName the firstLanguageName to set
	 */
	public void setFirstLanguageName(String firstLanguageName) {
		VocabularyLanguagesObservable.firstLanguageName = firstLanguageName;
		notifyListeners();
	}

	/**
	 * sets the name for phonetic script
	 * @param phoneticScriptName the phoneticScriptName to set
	 */
	public void setPhoneticScriptName(String phoneticScriptName) {
		VocabularyLanguagesObservable.phoneticScriptName = phoneticScriptName;
		notifyListeners();
	}

	/**
	 * sets the name for second language
	 * @param secondLanguageName the secondLanguageName to set
	 */
	public void setSecondLanguageName(String secondLanguageName) {
		VocabularyLanguagesObservable.secondLanguageName = secondLanguageName;
		notifyListeners();
	}
	
	/**
	 * sets the name for first language, phonetic script and second language
	 * @param firstLanguageName the first language
	 * @param phoneticScriptName the phonetic script
	 * @param secondLanguageName the second language
	 */
	public void setLanguageNames(String firstLanguageName, String phoneticScriptName, String secondLanguageName) {
		VocabularyLanguagesObservable.firstLanguageName = firstLanguageName;
		VocabularyLanguagesObservable.phoneticScriptName = phoneticScriptName;
		VocabularyLanguagesObservable.secondLanguageName = secondLanguageName;
		notifyListeners();
	}
}
