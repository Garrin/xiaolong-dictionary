package listener.vocabletable;

import gui.VocableTable;
import dictionary.Vocable;

public interface VocableTableActionsListener {
	
	/**
	 * This method handles a reaction on selections of {@link Vocable}s in the {@link VocableTable}.
	 * @param vocable the selected {@link Vocable}
	 */
	public void selectedVocableInVocableTable(Vocable vocable);
	
}
