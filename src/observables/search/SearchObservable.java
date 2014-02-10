package observables.search;

import java.util.ArrayList;

import listener.search.ANDSearchListener;
import listener.search.NOTSearchListener;
import listener.search.ORSearchListener;
import listener.search.SearchListener;

public enum SearchObservable {
	
	INSTANCE;
	
	private static ArrayList<SearchListener> searchListeners = new ArrayList<SearchListener>();
	private static ArrayList<ANDSearchListener> andSearchListeners = new ArrayList<ANDSearchListener>();
	private static ArrayList<ORSearchListener> orSearchListeners = new ArrayList<ORSearchListener>();
	private static ArrayList<NOTSearchListener> notSearchListeners = new ArrayList<NOTSearchListener>();
	
	
	public void registerListener(SearchListener searchListener) {
		SearchObservable.searchListeners.add(searchListener);
	}
	
	public void registerListener(ANDSearchListener andSearchListener) {
		SearchObservable.andSearchListeners.add(andSearchListener);
	}
	
	public void registerListener(ORSearchListener orSearchListener) {
		SearchObservable.orSearchListeners.add(orSearchListener);
	}
	
	public void registerListener(NOTSearchListener notSearchListener) {
		SearchObservable.notSearchListeners.add(notSearchListener);
	}
	
	private void notifySearchListeners() {
		for(SearchListener searchListener : SearchObservable.searchListeners) {
			searchListener.searchPerformedAction();
		}
	}
	
	private void notifyANDSearchListeners() {
		for(ANDSearchListener andSearchListener : SearchObservable.andSearchListeners) {
			andSearchListener.andSearchPerformedAction();
		}
	}
	
	private void notifyORSearchListeners() {
		for(ORSearchListener orSearchListener : SearchObservable.orSearchListeners) {
			orSearchListener.orSearchPerformedAction();
		}
	}
	
	private void notifyNOTSearchListeners() {
		for(NOTSearchListener notSearchListener : SearchObservable.notSearchListeners) {
			notSearchListener.notSearchPerformedAction();
		}
	}
	
	public void fireSearchNotification() {
		notifySearchListeners();
	}

	public void fireANDSearchNotification() {
		notifyANDSearchListeners();
	}
	
	public void fireORSearchNotification() {
		notifyORSearchListeners();
	}
	
	public void fireNOTSearchNotification() {
		notifyNOTSearchListeners();
	}
}
