package listener.settings;

import gui.VocableTable;

import java.awt.Font;

public interface VocableTableFontSettingsListener {
	
	/**
	 * This method changes the {@link VocableTable} font.
	 * @param newFirstLanguageName the new first language
	 * @param newPhoneticScriptName the new phonetic script
	 * @param newSecondLanguageName the new second language
	 */
	public void changeVocableTableFont(Font font);
	
}
