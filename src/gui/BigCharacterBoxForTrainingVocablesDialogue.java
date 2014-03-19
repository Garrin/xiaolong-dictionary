package gui;

import listener.search.SearchListener;
import listener.vocables.VocableChangedListener;
import listener.vocables.VocableDeletedListener;
import dictionary.Vocable;
import factories.ObserveableFactory;

@SuppressWarnings("serial")
public class BigCharacterBoxForTrainingVocablesDialogue extends AbstractBigCharacterBox implements SearchListener, VocableChangedListener, VocableDeletedListener {
	
	public BigCharacterBoxForTrainingVocablesDialogue(String ignoredCharacters) {
		super(ignoredCharacters);
	}

	@Override
	protected void registerListeners() {
		ObserveableFactory.getSearchObservable().registerListener(this);
		ObserveableFactory.getVocablesObserveable().registerVocableChangedListener(this);
		ObserveableFactory.getVocablesObserveable().registerVocableDeletedListener(this);
	}
	
	@Override
	public void searchPerformedAction() {
		textPane.setText("");
	}

	@Override
	public void vocableChangedActionPerformed(Vocable oldVocable, Vocable newVocable) {
		// TODO Auto-generated method stub
	}

	@Override
	public void vocableDeletedActionPerformed(Vocable vocable) {
		if(currentlyDisplayedVocable != null) {
			if(	vocable.getFirstLanguage().equals(currentlyDisplayedVocable.getFirstLanguage()) &&
				vocable.getPhoneticScript().equals(currentlyDisplayedVocable.getPhoneticScript()) &&
				vocable.getSecondLanguage().equals(currentlyDisplayedVocable.getSecondLanguage())
			) {
				System.out.println("It's the shown Vocable being deleted! :O");
			}
		} else {
			System.out.println("currentlyDisplayedVocable is null");
		}
	}

}
