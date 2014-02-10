package listener.vocables;

import dictionary.Vocable;

public interface VocableAddedListener {
	
	/**
	 * This method handles the actions taken when a vocable has been added.
	 */
	void vocableAddedActionPerformed(Vocable vocable);
	
}
