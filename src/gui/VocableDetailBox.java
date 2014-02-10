/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

/**
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class VocableDetailBox extends JPanel {
	
	//private DictionaryMainWindow window;
	
	private JLabel vocableDetailChapter = new JLabel("Chapter");
	private JLabel vocableDetailTopic = new JLabel("Topic");
	private JLabel vocableDetailLevel = new JLabel("Level");
	private JLabel vocableDetailChapterText = new JLabel("---");
	private JLabel vocableDetailTopicText = new JLabel("---");
	private JLabel vocableDetailLevelText = new JLabel("---");
	
	public VocableDetailBox() {
		addComponents();
	}
	
	private void addComponents() {
		setLayout(new MigLayout("wrap 2", "[left][left]", ""));
		
		TitledBorder vocableDetailBoxTitledBorder;
		vocableDetailBoxTitledBorder = BorderFactory.createTitledBorder("Details");
		vocableDetailBoxTitledBorder.setTitleJustification(TitledBorder.CENTER);
		setBorder(vocableDetailBoxTitledBorder);
		
		add(vocableDetailChapter, "cell 0 0");
		add(vocableDetailTopic, "cell 0 1");
		add(vocableDetailLevel, "cell 0 2");
		
		add(vocableDetailChapterText, "cell 1 0");
		add(vocableDetailTopicText, "cell 1 1");
		add(vocableDetailLevelText, "cell 1 2");
	}
	
	public void updateChapter(String newChapter) {
		vocableDetailChapterText.setText(newChapter);
	}
	
	public void updateTopic(String newTopic) {
		vocableDetailTopicText.setText(newTopic);
	}
	
	public void updateLevel(String newLevel) {
		vocableDetailLevelText.setText(newLevel);
	}
}
