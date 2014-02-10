package gui;

import javax.swing.JLabel;

import factories.ObserveableFactory;
import listener.settings.VocabularyLanguagesChangeListener;
import listener.vocables.VocableListChangedListener;
import listener.vocables.VocableListLoadedListener;
import listener.vocables.VocableListSavedListener;

@SuppressWarnings("serial")
public class DictionaryStatusLabel extends JLabel implements 
	VocableListLoadedListener, 
	VocableListSavedListener, 
	VocableListChangedListener,
	VocabularyLanguagesChangeListener {
	
	private static DictionaryStatusLabel instance = null;
	
	private DictionaryStatusLabel() {
		registerListeners();
	}
	
	private void registerListeners() {
		ObserveableFactory.getVocablesObserveable().registerVocableListLoadedListener(this);
		ObserveableFactory.getVocablesObserveable().registerVocableListSavedListener(this);
		
		ObserveableFactory.getVocablesObserveable().registerVocableListChangedListener(this);
//		ObserveableFactory.getVocablesObserveable().registerVocableAddedListener(this);
//		ObserveableFactory.getVocablesObserveable().registerVocableDeletedListener(this);
//		ObserveableFactory.getVocablesObserveable().registerVocableChangedListener(this);
	}

	public static DictionaryStatusLabel getInstance() {
		if(instance == null) {
			instance = new DictionaryStatusLabel();
		}
		return instance;
	}

//	@Override
//	public void vocableChangedActionPerformed(Vocable vocable) {
//		instance.setText("Vocable list has unsaved changes. (vocable changed)");
//	}
//
//	@Override
//	public void vocableDeletedActionPerformed(Vocable vocable) {
//		instance.setText("Vocable list has unsaved changes (vocable deleted).");
//	}
//
//	@Override
//	public void vocableAddedActionPerformed(Vocable vocable) {
//		instance.setText("Vocable list has unsaved changes (vocable added).");
//	}

	@Override
	public void vocableListSavedActionPerformed() {
		instance.setText("Vocable list is saved.");
	}

	@Override
	public void vocableListLoadedActionPerformed() {
		instance.setText("Vocable list is loaded.");
	}

	@Override
	public void changeVocabularyLanguages(String newFirstLanguageName,
			String newPhoneticScriptName, String newSecondLanguageName) {
		instance.setText("Vocabulary languages changed.");
		
	}

	@Override
	public void vocableListChangedActionPerformed() {
		instance.setText("Vocable list has unsaved changes.");
	}
}
