package listener.vocables;

import dictionary.Vocable;

public interface VocableDeletedListener {

	/**
	 * This method handles the actions taken when a vocable has been deleted.
	 */
	void vocableDeletedActionPerformed(Vocable vocable);
}
