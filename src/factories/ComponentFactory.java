package factories;

import gui.DictionaryStatusLabel;

public class ComponentFactory {
	
	public static DictionaryStatusLabel getDictionaryStatusLabel() {
		return DictionaryStatusLabel.getInstance();
	}
}
