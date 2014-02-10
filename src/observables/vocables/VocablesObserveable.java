package observables.vocables;

import java.util.ArrayList;

import listener.vocables.VocableAddedListener;
import listener.vocables.VocableChangedListener;
import listener.vocables.VocableDeletedListener;
import listener.vocables.VocableListChangedListener;
import listener.vocables.VocableListLoadedListener;
import listener.vocables.VocableListSavedListener;
import dictionary.Vocable;

public enum VocablesObserveable {
	INSTANCE;

	private static ArrayList<VocableListChangedListener> vocableListChangedListeners = new ArrayList<VocableListChangedListener>();
	private static ArrayList<VocableListSavedListener> vocableListSavedListeners = new ArrayList<VocableListSavedListener>();
	private static ArrayList<VocableListLoadedListener> vocableListLoadedListeners = new ArrayList<VocableListLoadedListener>();
	
	private static ArrayList<VocableChangedListener> vocableChangedListeners = new ArrayList<VocableChangedListener>();
	private static ArrayList<VocableAddedListener> vocableAddedListeners = new ArrayList<VocableAddedListener>();
	private static ArrayList<VocableDeletedListener> vocableDeletedListeners = new ArrayList<VocableDeletedListener>();

	/**
	 * Registers a new listener for changes of the vocable list.
	 * 
	 * @param vocableListChangedListener
	 *            the new listener
	 */
	public void registerVocableListChangedListener(
			VocableListChangedListener vocableListChangedListener) {
		vocableListChangedListeners.add(vocableListChangedListener);
	}

	/**
	 * Registers a new listener for changes of a vocable.
	 * 
	 * @param vocableChangedListener
	 *            the new listener
	 */
	public void registerVocableChangedListener(
			VocableChangedListener vocableChangedListener) {
		vocableChangedListeners.add(vocableChangedListener);
	}

	/**
	 * Registers a new listener for adding of a vocable.
	 * 
	 * @param vocableChangedListener
	 *            the new listener
	 */
	public void registerVocableAddedListener(
			VocableAddedListener vocableAddedListener) {
		vocableAddedListeners.add(vocableAddedListener);
	}

	/**
	 * Registers a new listener for deletion of a vocable.
	 * 
	 * @param vocableChangedListener
	 *            the new listener
	 */
	public void registerVocableDeletedListener(
			VocableDeletedListener vocableDeletedListener) {
		vocableDeletedListeners.add(vocableDeletedListener);
	}
	
	/**
	 * Registers a new listener for saving of the vocable list.
	 * @param vocableChangedListener the new listener
	 */
	public void registerVocableListSavedListener(VocableListSavedListener vocableListSavedListener) {
		vocableListSavedListeners.add(vocableListSavedListener);
	}
	
	/**
	 * Registers a new listener for loading of the vocable list.
	 * @param vocableChangedListener the new listener
	 */
	public void registerVocableListLoadedListener(VocableListLoadedListener vocableListLoadedListener) {
		vocableListLoadedListeners.add(vocableListLoadedListener);
	}
	
	/**
	 * notifies all listeners for vocableList changes
	 */
	private void notifyVocableListChangedListeners() {
		for(VocableListChangedListener vocableListChangedListener : vocableListChangedListeners) {
			vocableListChangedListener.vocableListChangedActionPerformed();
		}
	}
	
	/**
	 * notifies all listeners for vocableList saves
	 */
	private void notifyVocableListSavedListeners() {
		for(VocableListSavedListener vocableListSavedListener : vocableListSavedListeners) {
			vocableListSavedListener.vocableListSavedActionPerformed();
		}
	}
	
	private void notifyVocableListLoadedListeners() {
		for(VocableListLoadedListener vocableListLoadedListener : vocableListLoadedListeners) {
			vocableListLoadedListener.vocableListLoadedActionPerformed();
		}
	}
	
	/**
	 * notifies all listeners for vocable changes
	 * @param vocable the changed vocable
	 */
	private void notifyVocableChangedListeners(Vocable oldVocable, Vocable newVocable) {
		for(VocableChangedListener vocableChangedListener : vocableChangedListeners) {
			vocableChangedListener.vocableChangedActionPerformed(oldVocable, newVocable);
		}
	}

	/**
	 * notifies all listeners for added vocables
	 * @param vocable the added vocable
	 */
	private void notifyVocableAddedListeners(Vocable vocable) {
		for(VocableAddedListener vocableAddedListener : vocableAddedListeners) {
			vocableAddedListener.vocableAddedActionPerformed(vocable);
		}
	}
	
	/**
	 * notifies all listeners for deleted vocables
	 * @param vocable the deleted vocable
	 */
	private void notifyVocableDeletedListeners(Vocable vocable) {
		for(VocableDeletedListener vocableDeletedListener : vocableDeletedListeners) {
			vocableDeletedListener.vocableDeletedActionPerformed(vocable);
		}
	}
	
	public void fireAddVocableNotification(Vocable vocable) {
		notifyVocableAddedListeners(vocable);
		//notifyVocableListChangedListeners();
		System.out.println("Notifying all listeners for adding a vocable");
		//System.out.println("Notifying all listeners for changing the vocableList");
	}
	
	public void fireDeleteVocableNotification(Vocable vocable) {
		notifyVocableDeletedListeners(vocable);
		//notifyVocableListChangedListeners();
		System.out.println("Notifying all listeners for deleting a vocable");
		//System.out.println("Notifying all listeners for changing the vocableList");
	}
	
	public void fireChangeVocableNotification(Vocable oldVocable, Vocable newVocable) {
		notifyVocableChangedListeners(oldVocable, newVocable);
		//notifyVocableListChangedListeners();
		System.out.println("Notifying all listeners for changing a vocable");
		//System.out.println("Notifying all listeners for changing the vocableList");
	}
	
	public void fireVocableListChangedNotification() {
		notifyVocableListChangedListeners();
		System.out.println("Notifying all listeners for changing the vocableList");
	}
	
	public void fireVocableListSavedNotification() {
		notifyVocableListSavedListeners();
		System.out.println("Notifying all listeners for saving the vocableList");
	}
	
	public void fireVocableListLoadedNotification() {
		notifyVocableListLoadedListeners();
		System.out.println("Notifying all listeners for loading the vocableList");
	}
}
