package listener.vocables;

import dictionary.Vocable;

public interface VocableChangedListener {
	
	/**
	 * This method handles the actions taken when a vocable has been changed.
	 */
	void vocableChangedActionPerformed(Vocable oldVocable, Vocable newVocable);
	
}
