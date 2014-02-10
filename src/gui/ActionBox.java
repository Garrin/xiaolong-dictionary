/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import factories.FrameFactory;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import manager.VocableManager;
import dialogues.vocableactions.TrainVocablesDialogue;
import dictionary.Vocable;

/**
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class ActionBox extends JPanel {

	private DictionaryMainWindow window;
	private JButton addVocableButton;
	private JButton deleteVocableButton;
	private JButton changeVocableButton;
	private JButton setVocableLevelButton;
	private JButton trainVocableLevelButton;
	
	public ActionBox(DictionaryMainWindow window) {
		this.window = window;
		addComponents();
		addActionListeners();
	}
	
	private void addComponents() {
		//setLayout(new MigLayout("wrap 1", "[center]"));
		GridLayout gridLayout = new GridLayout(0,1);
		gridLayout.setVgap(5);
		setLayout(gridLayout);
		
		TitledBorder searchBoxTitledBorder;
		searchBoxTitledBorder = BorderFactory.createTitledBorder("Action");
		searchBoxTitledBorder.setTitleJustification(TitledBorder.CENTER);
		setBorder(searchBoxTitledBorder);
		
		addVocableButton = new JButton("Add vocable...");
		add(getActionAddVocableButton());
		
		deleteVocableButton = new JButton("Delete vocables...");
		add(getActionDeleteVocableButton());
		
		changeVocableButton = new JButton("Change vocable...");
		add(getActionChangeVocableButton());
		
		setVocableLevelButton = new JButton("Set level...");
		add(setVocableLevelButton);
		
		trainVocableLevelButton = new JButton("Train vocables...");
		add(trainVocableLevelButton);
	}
	
	private void addActionListeners() {
		getActionAddVocableButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addVocableButtonActionPerformed();
			}
		});
		
		getActionChangeVocableButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeVocableButtonActionPerformed();
			}
		});
		
		getActionDeleteVocableButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteVocableButtonActionPerformed();
			}
		});
		
		setVocableLevelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVocableLevelButtonActionPerformed();
			}
		});
		
		trainVocableLevelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				trainVocablesButtonActionPerformed();
			}
		});
	}
	
	private void addVocableButtonActionPerformed() {
		FrameFactory.getAddVocableFrame().setTitle("Add Vocable...");
		FrameFactory.getAddVocableFrame().pack();
		FrameFactory.getAddVocableFrame().setLocationRelativeTo(null);
		FrameFactory.getAddVocableFrame().setVisible(true);
	}
	
	private void changeVocableButtonActionPerformed() {
		
		int selectedRow = DictionaryMainWindow.vocableTable.getSelectedRow();
		int[] numbersOfRows = DictionaryMainWindow.vocableTable.getSelectedRows();
		if(selectedRow != -1) {
			if(numbersOfRows.length == 1) {
				
				int columnOfFirstLanguage = DictionaryMainWindow.vocableTable.getNumberOfColumnOfFirstLanguage();
				int columnOfPhoneticScript = DictionaryMainWindow.vocableTable.getNumberOfColumnOfPhoneticScript();
				int columnOfSecondLanguage = DictionaryMainWindow.vocableTable.getNumberOfColumnOfSecondLanguage();
				
				String firstLanguage = (String) DictionaryMainWindow.vocableTable.getValueAt(selectedRow, columnOfFirstLanguage);
				String phoneticScript = (String) DictionaryMainWindow.vocableTable.getValueAt(selectedRow, columnOfPhoneticScript);
				String secondLanguage = (String) DictionaryMainWindow.vocableTable.getValueAt(selectedRow, columnOfSecondLanguage);
				
				//Search through the search result to get the right number of the selected vocable
				int positionInSearchResult = -1;
				for(int i = 0; i < VocableManager.getLastSearchResult().size(); i++) {
					if(	VocableManager.getLastSearchResult().get(i).getFirstLanguage().equals(firstLanguage) &&
							VocableManager.getLastSearchResult().get(i).getPhoneticScript().equals(phoneticScript) &&
							VocableManager.getLastSearchResult().get(i).getSecondLanguage().equals(secondLanguage)) {
						positionInSearchResult = i;
						break;
					}
				}
				
				//Create new changeVocableDialogue with the correct number
				FrameFactory.getChangeVocablesFrame().initialize(VocableManager.getLastSearchResult().get(positionInSearchResult));
				FrameFactory.getChangeVocablesFrame().setTitle("Change Vocable ...");
				FrameFactory.getChangeVocablesFrame().pack();
				FrameFactory.getChangeVocablesFrame().setLocationRelativeTo(null);
				FrameFactory.getChangeVocablesFrame().setVisible(true);
			} else {
				//Create new changeVocableDialogue with the correct number
				//DictionaryMainWindow.changeMultipleVocablesDialogue = new ChangeMultipleVocablesFrame(numbersOfRows);
				FrameFactory.getChangeMultipleVocablesFrame().initialize(numbersOfRows);
				FrameFactory.getChangeMultipleVocablesFrame().setTitle("Change Vocables ...");
				FrameFactory.getChangeMultipleVocablesFrame().pack();
				FrameFactory.getChangeMultipleVocablesFrame().setLocationRelativeTo(null);
				FrameFactory.getChangeMultipleVocablesFrame().setVisible(true);
			}
		}
		else {
			JOptionPane.showMessageDialog(window, "Please select a vocable from the vocable list first.", "Hint...", JOptionPane.OK_OPTION);
		}
	}

	/**
	 * performs an action when the user clicks on the delete vocable button
	 */
	private void deleteVocableButtonActionPerformed() {
		int selectedRow = DictionaryMainWindow.vocableTable.getSelectedRow();
		int[] numbersOfRows = DictionaryMainWindow.vocableTable.getSelectedRows();
		if(selectedRow != -1) {
			if(numbersOfRows.length == 1) {
				deleteSingleVocableActionPerformed(selectedRow);
			} else {
				deleteMultipleVocablesActionPerformed(numbersOfRows);
			}
		} else {
			JOptionPane.showMessageDialog(window, "Please select a vocable from the vocable list first.", "Hint...", JOptionPane.OK_OPTION);
		}
	}
	
	private void deleteSingleVocableActionPerformed(int selectedRow) {
		int columnOfFirstLanguage = DictionaryMainWindow.vocableTable.getNumberOfColumnOfFirstLanguage();
		int columnOfPhoneticScript = DictionaryMainWindow.vocableTable.getNumberOfColumnOfPhoneticScript();
		int columnOfSecondLanguage = DictionaryMainWindow.vocableTable.getNumberOfColumnOfSecondLanguage();
		
		String firstLanguage = (String) DictionaryMainWindow.vocableTable.getValueAt(selectedRow, columnOfFirstLanguage);
		String phoneticScript = (String) DictionaryMainWindow.vocableTable.getValueAt(selectedRow, columnOfPhoneticScript);
		String secondLanguage = (String) DictionaryMainWindow.vocableTable.getValueAt(selectedRow, columnOfSecondLanguage);
		
		//Search through the search result to get the right number of the selected vocable
		int positionInSearchResult = -1;
		for(int i = 0; i < VocableManager.getLastSearchResult().size(); i++) {
			if(	VocableManager.getLastSearchResult().get(i).getFirstLanguage().equals(firstLanguage) &&
				VocableManager.getLastSearchResult().get(i).getPhoneticScript().equals(phoneticScript) &&
				VocableManager.getLastSearchResult().get(i).getSecondLanguage().equals(secondLanguage)) {
				positionInSearchResult = i;
				break;
			}
		}
		
		//Create new changeVocableDialogue with the correct number
		FrameFactory.getDeleteVocableDialogue().initialize(VocableManager.getLastSearchResult().get(positionInSearchResult));
		FrameFactory.getDeleteVocableDialogue().setTitle("Delete Vocable ...");
		FrameFactory.getDeleteVocableDialogue().pack();
		FrameFactory.getDeleteVocableDialogue().setLocationRelativeTo(null);
		FrameFactory.getDeleteVocableDialogue().setVisible(true);
	}
	
	private void deleteMultipleVocablesActionPerformed(int[] numbersOfRows) {
		ArrayList<Vocable> vocables = new ArrayList<Vocable>();
		
		for(int numberOfSelectedRow : numbersOfRows) {
			int columnOfFirstLanguage = DictionaryMainWindow.vocableTable.getNumberOfColumnOfFirstLanguage();
			int columnOfPhoneticScript = DictionaryMainWindow.vocableTable.getNumberOfColumnOfPhoneticScript();
			int columnOfSecondLanguage = DictionaryMainWindow.vocableTable.getNumberOfColumnOfSecondLanguage();
			
			String firstLanguage = (String) DictionaryMainWindow.vocableTable.getValueAt(numberOfSelectedRow, columnOfFirstLanguage);
			String phoneticScript = (String) DictionaryMainWindow.vocableTable.getValueAt(numberOfSelectedRow, columnOfPhoneticScript);
			String secondLanguage = (String) DictionaryMainWindow.vocableTable.getValueAt(numberOfSelectedRow, columnOfSecondLanguage);
			
			//Search through the search result to get the right number of the selected vocable
			for(int i = 0; i < VocableManager.getLastSearchResult().size(); i++) {
				if(	VocableManager.getLastSearchResult().get(i).getFirstLanguage().equals(firstLanguage) &&
					VocableManager.getLastSearchResult().get(i).getPhoneticScript().equals(phoneticScript) &&
					VocableManager.getLastSearchResult().get(i).getSecondLanguage().equals(secondLanguage)) {
					vocables.add(VocableManager.getLastSearchResult().get(i));
					break;
				}
			}
		}
		
		//Create new changeVocableDialogue with the correct number
		FrameFactory.getDeleteMultipleVocablesDialogue().initialize(vocables);
		FrameFactory.getDeleteMultipleVocablesDialogue().setTitle("Delete Vocables ...");
		FrameFactory.getDeleteMultipleVocablesDialogue().pack();
		FrameFactory.getDeleteMultipleVocablesDialogue().setLocationRelativeTo(null);
		FrameFactory.getDeleteMultipleVocablesDialogue().setVisible(true);
	}
	
	private void setVocableLevelButtonActionPerformed() {
		FrameFactory.getSetVocableLevelDialogue().setTitle("Set Vocable Level...");
		FrameFactory.getSetVocableLevelDialogue().pack();
		FrameFactory.getSetVocableLevelDialogue().setLocationRelativeTo(null);
		FrameFactory.getSetVocableLevelDialogue().setVisible(true);
	}
	
	private void trainVocablesButtonActionPerformed() {
		if(VocableManager.getLastSearchResult().isEmpty()) {
			JOptionPane.showMessageDialog(window, "Please search for a list of vocables you want to practice first.", "No vocables...", JOptionPane.OK_OPTION);
		} else {
			TrainVocablesDialogue tvd2 = new TrainVocablesDialogue(VocableManager.getLastSearchResult());
			tvd2.pack();
			tvd2.setLocationRelativeTo(null);
			tvd2.setVisible(true);
			
			//FrameFactory.getTrainVocablesFrame();
			
			//FrameFactory.getTrainVocablesFrame().initialize();
			//FrameFactory.getTrainVocablesFrame().pack();
			//FrameFactory.getTrainVocablesFrame().setLocationRelativeTo(null);
			//FrameFactory.getTrainVocablesFrame().setVisible(true);
		}
	}

	/**
	 * @return the actionAddVocableButton
	 */
	public JButton getActionAddVocableButton() {
		return addVocableButton;
	}

	/**
	 * @return the actionDeleteVocableButton
	 */
	public JButton getActionDeleteVocableButton() {
		return deleteVocableButton;
	}

	/**
	 * @return the actionChangeVocableButton
	 */
	public JButton getActionChangeVocableButton() {
		return changeVocableButton;
	}
}
