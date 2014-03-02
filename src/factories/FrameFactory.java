package factories;

import gui.DictionaryMainWindow;
import dialogues.vocableactions.AddVocableFrame;
import dialogues.vocableactions.ChangeMultipleVocablesFrame;
import dialogues.vocableactions.ChangeVocableFrame;
import dialogues.vocableactions.DeleteMultipleVocablesDialogue;
import dialogues.vocableactions.DeleteVocableDialogue;
import dialogues.vocableactions.SetVocableLevelDialogue;

public class FrameFactory {
	
	public static AddVocableFrame getAddVocableFrame() {
		return AddVocableFrame.getInstance();
	}
	
	public static ChangeMultipleVocablesFrame getChangeMultipleVocablesFrame() {
		return ChangeMultipleVocablesFrame.getInstance();
	}
	
	public static ChangeVocableFrame getChangeVocablesFrame() {
		return ChangeVocableFrame.getInstance();
	}
	
	public static DeleteMultipleVocablesDialogue getDeleteMultipleVocablesDialogue() {
		return DeleteMultipleVocablesDialogue.getInstance();
	}
	
	public static DeleteVocableDialogue getDeleteVocableDialogue() {
		return DeleteVocableDialogue.getInstance();
	}
	
	public static SetVocableLevelDialogue getSetVocableLevelDialogue() {
		return SetVocableLevelDialogue.getInstance();
	}
	
	public static DictionaryMainWindow getDictionaryMainWindow() {
		return DictionaryMainWindow.getInstance();
	}
	
	
}
