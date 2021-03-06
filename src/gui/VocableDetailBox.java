/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import listener.search.SearchListener;
import listener.vocables.VocableChangedListener;
import listener.vocables.VocableDeletedListener;
import listener.vocabletable.VocableTableActionsListener;
import net.miginfocom.swing.MigLayout;
import dictionary.Vocable;
import factories.ObserveableFactory;

/**
 * 
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class VocableDetailBox extends JPanel implements VocableDeletedListener, VocableChangedListener, VocableTableActionsListener, SearchListener {

	// private DictionaryMainWindow window;

	private JLabel vocableDetailChapter = new JLabel("Chapter:");
	private JLabel vocableDetailTopic = new JLabel("Topic:");
	private JLabel vocableDetailLearnLevel = new JLabel("Level:");
	private JLabel vocableDetailRelevance = new JLabel("Relevance:");
	private JLabel vocableDetailChapterText = new JLabel("---");
	private JLabel vocableDetailTopicText = new JLabel("---");
	private JLabel vocableDetailLearnLevelText = new JLabel("---");
	private JLabel vocableDetailRelevanceText = new JLabel("---");
	private Vocable currentVocable;

	public VocableDetailBox() {
		addComponents();
		registerListeners();
	}

	private void addComponents() {
		setLayout(new MigLayout("wrap 2", "[left][left]", ""));

		TitledBorder vocableDetailBoxTitledBorder;
		vocableDetailBoxTitledBorder = BorderFactory
				.createTitledBorder("Details");
		vocableDetailBoxTitledBorder.setTitleJustification(TitledBorder.CENTER);
		setBorder(vocableDetailBoxTitledBorder);

		add(vocableDetailChapter, "cell 0 0");
		add(vocableDetailTopic, "cell 0 1");
		add(vocableDetailLearnLevel, "cell 0 2");
		add(vocableDetailRelevance, "cell 0 3");

		add(vocableDetailChapterText, "cell 1 0");
		add(vocableDetailTopicText, "cell 1 1");
		add(vocableDetailLearnLevelText, "cell 1 2");
		add(vocableDetailRelevanceText, "cell 1 3");
	}

	/**
	 * This method registers this component as a listener for vocables
	 */
	private void registerListeners() {
		ObserveableFactory.getVocablesObserveable().registerVocableChangedListener(this);
		ObserveableFactory.getVocablesObserveable().registerVocableDeletedListener(this);
		ObserveableFactory.getSearchObservable().registerListener(this);
		DictionaryMainWindow.vocableTable.registerVocableTableActionsListener(this);
	}

	private void updateVocableDetails(Vocable vocable) {
		this.currentVocable = vocable;
		vocableDetailChapterText.setText(vocable.getChapter());
		vocableDetailTopicText.setText(vocable.getTopic());
		vocableDetailLearnLevelText.setText(vocable.getLearnLevel());
		
	}

	public void updateChapter(String newChapter) {
		vocableDetailChapterText.setText(newChapter);
	}

	public void updateTopic(String newTopic) {
		vocableDetailTopicText.setText(newTopic);
	}

	public void updateLearnLevel(String newLevel) {
		vocableDetailLearnLevelText.setText(newLevel);
	}
	
	public void updateRelevance(String newRelevance) {
		vocableDetailRelevanceText.setText(newRelevance);
	}

	@Override
	public void vocableChangedActionPerformed(Vocable oldVocable,
			Vocable newVocable) {
		if (oldVocable == currentVocable) {
			updateVocableDetails(newVocable);
		}
	}

	@Override
	public void vocableDeletedActionPerformed(Vocable vocable) {
		if (vocable == currentVocable) {
			updateChapter("");
			updateLearnLevel("");
			updateTopic("");
		}
	}

	@Override
	public void selectedVocableInVocableTable(Vocable vocable) {
		updateVocableDetails(vocable);
	}

	@Override
	public void searchPerformedAction() {
		updateChapter("---");
		updateLearnLevel("---");
		updateTopic("---");
		updateRelevance("---");
	}
}
