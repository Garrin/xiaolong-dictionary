package factories;

import observables.search.SearchObservable;
import observables.settings.FontsSettingsObservable;
import observables.settings.VocabularyLanguagesObservable;
import observables.vocables.VocablesObserveable;

public class ObserveableFactory {
	
	public static VocabularyLanguagesObservable getVocabularyLanguagesObserveable() {
		return VocabularyLanguagesObservable.INSTANCE;
	}
	
	public static VocablesObserveable getVocablesObserveable() {
		return VocablesObserveable.INSTANCE;
	}
	
	public static SearchObservable getSearchObservable() {
		return SearchObservable.INSTANCE;
	}
	
	public static FontsSettingsObservable getVocableTableFontSettingsObservable() {
		return FontsSettingsObservable.INSTANCE;
	}
}
