package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JTable;
//import javax.swing.table.DefaultTableModel;

import listener.search.SearchListener;
import listener.settings.VocabularyLanguagesChangeListener;
import listener.vocables.VocableAddedListener;
import listener.vocables.VocableChangedListener;
import listener.vocables.VocableDeletedListener;
import manager.VocableManager;
import dictionary.Settings;
import dictionary.Vocable;
import factories.FrameFactory;
import factories.ObserveableFactory;

/**
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public final class VocableTable extends JTable implements VocabularyLanguagesChangeListener, SearchListener, VocableChangedListener, VocableAddedListener, VocableDeletedListener {
	
	private UneditableCellsTableModel tableModel;
	
	@SuppressWarnings("unused")
	private final int NUMBER_COLUMN = 0;
	
	public static final int FIRST_LANGUAGE_COLUMN = 1;
	public static final int PHONETIC_SCRIPT_COLUMN = 2;
	public static final int SECOND_LANGUAGE_COLUMN = 3;
	
	private final int PREFERRED_TABLE_WIDTH = 500;
	private final int PREFERRED_TABLE_HEIGHT = 300;
	
	private final int COLUMN_MINIMUM_WIDTH = 50;
	private final int COLUMN_PREFFERED_WIDTH = 50;
	
	public VocableTable(UneditableCellsTableModel model) {
		super(model);
		
		this.tableModel = model;
		
		initializeLayout();
		setColumnsWidth();
		addActionListeners();
		updateFont(Settings.vocableTable_font, Settings.vocableTable_fontSize, Settings.vocableTable_fontWeight);
		
		registerListeners();
	}
	
	/**
	 * Registers all necessary listeners
	 */
	private void registerListeners() {
		//Language Settings
		ObserveableFactory.getVocabularyLanguagesObserveable().registerListener(this);
		
		//Vocable Actions
		ObserveableFactory.getVocablesObserveable().registerVocableAddedListener(this);
		ObserveableFactory.getVocablesObserveable().registerVocableDeletedListener(this);
		ObserveableFactory.getVocablesObserveable().registerVocableChangedListener(this);
		
		//Searches
		ObserveableFactory.getSearchObservable().registerListener(this);
	}
	
	private void initializeLayout() {
		setPreferredScrollableViewportSize(new Dimension(PREFERRED_TABLE_WIDTH, PREFERRED_TABLE_HEIGHT));
		setFillsViewportHeight(true);
	}
	
	/**
	 * Updates the vocableTable with a given list of vocables
	 * @param searchResult the vocable list that will be displayed in the table
	 */
	private void updateVocableTable(ArrayList<Vocable> searchResult) {
		if(!searchResult.isEmpty()) {
			ArrayList<String[]> tmp = new ArrayList<String[]>();
			
			
			for(int i = 1; i <= searchResult.size(); i++) {
				tmp.add(new String[]{Integer.toString(i-1), searchResult.get(i-1).getFirstLanguage(), searchResult.get(i-1).getPhoneticScript(), searchResult.get(i-1).getSecondLanguage()});
			}

			Object[][] rowData = new Object[tmp.size()][tmp.get(0).length];

			for(int i = 0; i < tmp.size(); i++) {
				System.arraycopy(tmp.get(i), 0, rowData[i], 0, tmp.get(i).length);
			}

			tableModel.setDataVector(rowData, new Object[]{"#", Settings.languageOptions_firstLanguageName, Settings.languageOptions_phoneticScriptName, Settings.languageOptions_secondLanguageName});
		}
		else {
			tableModel.setDataVector(new Object[0][0], new Object[]{"#", Settings.languageOptions_firstLanguageName, Settings.languageOptions_phoneticScriptName, Settings.languageOptions_secondLanguageName});
		}
		setColumnsWidth();
	}
	
	private void addActionListeners() {
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = getSelectedRow();
				if(selectedRow != -1) {
					updateBigCharacterBox(selectedRow);
					updateVocableDetailBox(selectedRow);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				//throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				//throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				//throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//throw new UnsupportedOperationException("Not supported yet.");
			}
		});
		
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				//throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void keyPressed(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					int selectedRow = getSelectedRow();
					if(selectedRow != -1) {
						updateBigCharacterBox(selectedRow);
						updateVocableDetailBox(selectedRow);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_ENTER) {
					int selectedRow = getSelectedRow();
					if(selectedRow != -1) {
						updateBigCharacterBox(selectedRow);
						updateVocableDetailBox(selectedRow);
					}
				}
				
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					FrameFactory.getDictionaryMainWindow().bigCharacterBox.pressPrevious();
				}
				
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					FrameFactory.getDictionaryMainWindow().bigCharacterBox.pressNext();
				}
				
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					int selectedRow = getSelectedRow();
					if(selectedRow != -1) {
						FrameFactory.getDeleteVocableDialogue().initialize(VocableManager.getLastSearchResult().get(selectedRow));
						FrameFactory.getDeleteVocableDialogue().setTitle("Delete Vocable...");
						FrameFactory.getDeleteVocableDialogue().pack();
						FrameFactory.getDeleteVocableDialogue().setLocationRelativeTo(null);
						FrameFactory.getDeleteVocableDialogue().setVisible(true);
					}
				}
			}
		});
	}

	private void setColumnsWidth() {
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getColumn("#").setMinWidth(COLUMN_MINIMUM_WIDTH);
		//getColumn("#").setMaxWidth(40);
		getColumn("#").setPreferredWidth(COLUMN_PREFFERED_WIDTH);
		
		getColumn(Settings.languageOptions_firstLanguageName).setMinWidth(150);
		//getColumn("German").setMaxWidth(150);
		getColumn(Settings.languageOptions_firstLanguageName).setPreferredWidth(150);
		
		getColumn(Settings.languageOptions_phoneticScriptName).setMinWidth(150);
		//getColumn("Pinyin").setMaxWidth(150);
		getColumn(Settings.languageOptions_phoneticScriptName).setPreferredWidth(150);
		
		getColumn(Settings.languageOptions_secondLanguageName).setMinWidth(150);
		//getColumn("Chinese").setMaxWidth(150);
		getColumn(Settings.languageOptions_secondLanguageName).setPreferredWidth(150);
	}
	
	public void updateFont(String font, int fontSize, String fontWeight) {
		//System.out.println("Font for Vocable Table: " + getFont().getName());
		if(fontWeight.equals("plain"))
			setFont(new Font(font, Font.PLAIN, fontSize));
		else if(fontWeight.equals("bold"))
			setFont(new Font(font, Font.BOLD, fontSize));
		else if(fontWeight.equals("italic"))
			setFont(new Font(font, Font.ITALIC, fontSize));
		
		setRowHeight((int)Math.floor(fontSize*1.5));
	}
	
	/*
	public void updateHeadings(String firstLanguage, String phoneticScript, String secondLanguage) {
		//EDIT
		getColumn(Settings.languageOptions_firstLanguageName).setHeaderValue(firstLanguage);
		getColumn(Settings.languageOptions_phoneticScriptName).setHeaderValue(phoneticScript);
		getColumn(Settings.languageOptions_secondLanguageName).setHeaderValue(secondLanguage);
		
		((DefaultTableModel) getModel()).setColumnIdentifiers(new Object[]{"#",Settings.languageOptions_firstLanguageName,Settings.languageOptions_phoneticScriptName,Settings.languageOptions_secondLanguageName});
	}
	*/
	
	private void updateBigCharacterBox(int selectedRow) {
		int secondLanguageColumnPos = getTableHeader().getColumnModel().getColumnIndex(Settings.languageOptions_secondLanguageName);
		
		FrameFactory.getDictionaryMainWindow().updateBigCharacterBox((String) getValueAt(selectedRow, secondLanguageColumnPos));
	}
	
	private void updateVocableDetailBox(int selectedRow) {
		int firstLanguageColumnPos = getTableHeader().getColumnModel().getColumnIndex(Settings.languageOptions_firstLanguageName);
		int phoneticScriptColumnPos = getTableHeader().getColumnModel().getColumnIndex(Settings.languageOptions_phoneticScriptName);
		int secondLanguageColumnPos = getTableHeader().getColumnModel().getColumnIndex(Settings.languageOptions_secondLanguageName);
		
		String firstLanguage = ((String) getValueAt(selectedRow, firstLanguageColumnPos));
		String phoneticScript = ((String) getValueAt(selectedRow, phoneticScriptColumnPos));
		String secondLanguage = ((String) getValueAt(selectedRow, secondLanguageColumnPos));
		
		FrameFactory.getDictionaryMainWindow().updateVocableDetailsBox(firstLanguage, phoneticScript, secondLanguage);
	}
	
//	public void updateTableHeader(String newFirstLanguage, String newPhoneticScript, String newSecondLanguage) {
//		int firstLanguageColumnPos = getTableHeader().getColumnModel().getColumnIndex(Settings.languageOptions_previousFirstLanguage);
//		int phoneticScriptColumnPos = getTableHeader().getColumnModel().getColumnIndex(Settings.languageOptions_previousPhoneticScript);
//		int secondLanguageColumnPos = getTableHeader().getColumnModel().getColumnIndex(Settings.languageOptions_previousSecondLanguage);
//		getTableHeader().getColumnModel().getColumn(firstLanguageColumnPos).setHeaderValue(Settings.languageOptions_firstLanguageName);
//		getTableHeader().getColumnModel().getColumn(phoneticScriptColumnPos).setHeaderValue(Settings.languageOptions_phoneticScriptName);
//		getTableHeader().getColumnModel().getColumn(secondLanguageColumnPos).setHeaderValue(Settings.languageOptions_secondLanguageName);
//		getTableHeader().repaint();
//	}
	
	public void setTableHeader(String newFirstLanguage, String newPhoneticScript, String newSecondLanguage) {
		getTableHeader().getColumnModel().getColumn(FIRST_LANGUAGE_COLUMN).setHeaderValue(Settings.languageOptions_firstLanguageName);
		getTableHeader().getColumnModel().getColumn(PHONETIC_SCRIPT_COLUMN).setHeaderValue(Settings.languageOptions_phoneticScriptName);
		getTableHeader().getColumnModel().getColumn(SECOND_LANGUAGE_COLUMN).setHeaderValue(Settings.languageOptions_secondLanguageName);
		getTableHeader().repaint();
	}
	
	public int getNumberOfColumnOfFirstLanguage() {
		return getTableHeader().getColumnModel().getColumnIndex(Settings.languageOptions_firstLanguageName);
	}
	
	public int getNumberOfColumnOfPhoneticScript() {
		return getTableHeader().getColumnModel().getColumnIndex(Settings.languageOptions_phoneticScriptName);
	}
	
	public int getNumberOfColumnOfSecondLanguage() {
		return getTableHeader().getColumnModel().getColumnIndex(Settings.languageOptions_secondLanguageName);
	}

	/**
	 * returns the number of a row specified by a vocable given
	 * @param vocable the given vocable
	 * @return the number of the row representing the vocable
	 */
	public int getNumberOfRowOfVocable(Vocable vocable) {
		int result = -1;
		
		//a vocable is uniquely identified by its first language, phonetic script and second language 
		for(int i = 0; i < getModel().getRowCount(); i++) {
			if(	getModel().getValueAt(i, FIRST_LANGUAGE_COLUMN).equals(vocable.getFirstLanguage()) &&
				getModel().getValueAt(i, PHONETIC_SCRIPT_COLUMN).equals(vocable.getPhoneticScript()) &&
				getModel().getValueAt(i, SECOND_LANGUAGE_COLUMN).equals(vocable.getSecondLanguage())){
				result = i;
				break; //if one match is found it has to be the only one and we can return the number of the current row
			}
		}
		
		return result;
	}
	
	@Override
	public void changeVocabularyLanguages(String newFirstLanguage, String newPhoneticScript, String newSecondLanguage) {
		int firstLanguageColumnPos = getTableHeader().getColumnModel().getColumnIndex(Settings.languageOptions_previousFirstLanguage);
		int phoneticScriptColumnPos = getTableHeader().getColumnModel().getColumnIndex(Settings.languageOptions_previousPhoneticScript);
		int secondLanguageColumnPos = getTableHeader().getColumnModel().getColumnIndex(Settings.languageOptions_previousSecondLanguage);
		getTableHeader().getColumnModel().getColumn(firstLanguageColumnPos).setHeaderValue(Settings.languageOptions_firstLanguageName);
		getTableHeader().getColumnModel().getColumn(phoneticScriptColumnPos).setHeaderValue(Settings.languageOptions_phoneticScriptName);
		getTableHeader().getColumnModel().getColumn(secondLanguageColumnPos).setHeaderValue(Settings.languageOptions_secondLanguageName);
		getTableHeader().repaint();
	}

	@Override
	public void searchPerformedAction() {
		updateVocableTable(VocableManager.getLastSearchResult());
		TableColumnsAutofitter.autoResizeTable(this, true, 0, true, 175);
	}

	@Override
	public void vocableChangedActionPerformed(Vocable oldVocable, Vocable newVocable) {
		updateVocableTable(VocableManager.getLastSearchResult());
		TableColumnsAutofitter.autoResizeTable(this, true, 0, true, 175);
		
		//TODO: select the row representing the changed vocable
		//TODO: update the big character box with the selected row
	}

	@Override
	public void vocableDeletedActionPerformed(Vocable vocable) {
		updateVocableTable(VocableManager.getLastSearchResult());
		TableColumnsAutofitter.autoResizeTable(this, true, 0, true, 175);
		
		//TODO: if there are still vocables displayed, select the first one
		//TODO: update the big character box with the selected row
	}

	@Override
	public void vocableAddedActionPerformed(Vocable vocable) {
		updateVocableTable(VocableManager.getLastSearchResult());
		TableColumnsAutofitter.autoResizeTable(this, true, 0, true, 175);
		
		//TODO: Find the row containing the added vocable if it was added to the search result
		//TODO: Select it
		//TODO: update the big character box
	}
}