package observables.settings;

import gui.VocableTable;

import java.awt.Font;
import java.util.ArrayList;

import listener.settings.VocableTableFontSettingsListener;
import dictionary.Settings;

public enum FontsSettingsObservable {
	
	INSTANCE;
	
	private static ArrayList<VocableTableFontSettingsListener> vocableTableFontSettingsListeners = new ArrayList<VocableTableFontSettingsListener>();
	
	/**
	 * This method registers a {@link VocableTableFontSettingsListener} in order to notify it when the Font of the {@link VocableTable} changes.
	 * @param vocableTableFontSettingsListener the listener that shall be added
	 */
	public void registerVocableTableFontSettingsListener(VocableTableFontSettingsListener vocableTableFontSettingsListener) {
		vocableTableFontSettingsListeners.add(vocableTableFontSettingsListener);
	}
	
	/**
	 * This method notifies all registered {@link VocableTableFontSettingsListener}s of the changed Foont setting for the {@link VocableTable}.
	 */
	private void notifyVocableTableFontSettingsListeners() {
		for(VocableTableFontSettingsListener vocableTableFontSettingsListener : FontsSettingsObservable.vocableTableFontSettingsListeners) {
			if(Settings.vocableTable_fontWeight.equals("plain")) {
				vocableTableFontSettingsListener.changeVocableTableFont(new Font(Settings.vocableTable_font, Font.PLAIN, Settings.vocableTable_fontSize));
			} else if(Settings.vocableTable_fontWeight.equals("bold")) {
				vocableTableFontSettingsListener.changeVocableTableFont(new Font(Settings.vocableTable_font, Font.BOLD, Settings.vocableTable_fontSize));
			} else if(Settings.vocableTable_fontWeight.equals("plain")) {
				vocableTableFontSettingsListener.changeVocableTableFont(new Font(Settings.vocableTable_font, Font.ITALIC, Settings.vocableTable_fontSize));
			}
		}
	}
	
	/**
	 * Use this method to cause a notification for all registered {@link VocableTableFontSettingsListener}s.
	 */
	public void fireVocableTableFontSettingsNotification() {
		notifyVocableTableFontSettingsListeners();
	}
}
