/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dialogues.options.BigCharacterBoxOptionsDialogue;
import dialogues.options.DialogsOptionsDialogue;
import dialogues.options.SearchHistoryOptionsDialogue;
import dialogues.options.SearchOptionsDialogue;
import dialogues.options.LanguageOptionsDialogue;
import dialogues.options.SpecialCharactersOptionsDialogue;
import dialogues.options.TrainingOptionsDialogue;
import dialogues.options.VocableOptionsDialogue;
import dialogues.options.VocableTableOptionsDialogue;
import dialogues.*;
import dictionary.Dictionary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import manager.VocableManager;

/**
 *
 * @author xiaolong
 */
@SuppressWarnings("serial")
public class DictionaryMenuBar extends JMenuBar {
	
	private DictionaryMainWindow window;
	
	private JFileChooser fileChooser;
	
	private JMenu fileMenu = new JMenu();
	private JMenuItem createNewDictionaryMenuItem = new JMenuItem();
	private JMenuItem openDictionaryMenuItem = new JMenuItem();
	private JMenuItem saveDictionaryMenuItem = new JMenuItem();
	private JMenuItem saveDictionaryAsMenuItem = new JMenuItem();
	private JMenuItem saveSearchResultAsMenuItem = new JMenuItem();
	private JMenuItem exitMenuItem = new JMenuItem();
	
	private JMenu optionsMenu = new JMenu();
	private JMenuItem vocableOptionsMenuItem = new JMenuItem("Vocables ...");
	private JMenuItem searchOptionsMenuItem = new JMenuItem("Search...");
	private JMenuItem languageOptionsMenuItem = new JMenuItem("Language...");
	private JMenuItem vocableTableOptionsMenuItem = new JMenuItem("Vocable table...");
	private JMenuItem trainingOptionsMenuItem = new JMenuItem("Training...");
	private JMenuItem bigCharacterBoxOptionsMenuItem = new JMenuItem("Big character box...");
	private JMenuItem dialogsOptionsMenuItem = new JMenuItem("Dialogs...");
	private JMenuItem specialCharactersOptionsMenuItem = new JMenuItem("Special characters ...");
	private JMenuItem searchHistoryOptionsMenuItem = new JMenuItem("Search History ...");
	
	private JMenu helpMenu = new JMenu();
	private JMenuItem helpMenuItem = new JMenuItem();
	private JMenuItem infoMenuItem = new JMenuItem();
	
	public DictionaryMenuBar(DictionaryMainWindow parent) {
		super();
		this.window = parent;
		createMenu();
	}
	
	private void createMenu() {
		createFileMenu();
		createOptionsMenu();
		createHelpMenu();
		addMenuActionListeners();
	}

	private void createFileMenu() {
		fileMenu.setText("File");
		fileMenu.setMnemonic('F');
		add(fileMenu);
		
		createNewDictionaryMenuItem.setText("Create new dictionary ...");
		createNewDictionaryMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
		fileMenu.add(createNewDictionaryMenuItem);
		
		openDictionaryMenuItem.setText("Open dictionary");
		openDictionaryMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK));
		fileMenu.add(openDictionaryMenuItem);
		
		saveDictionaryMenuItem.setText("Save dictionary");
		saveDictionaryMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
		fileMenu.add(saveDictionaryMenuItem);
		
		saveDictionaryAsMenuItem.setText("Save dictionary as ...");
		saveDictionaryAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK | java.awt.Event.SHIFT_MASK));
		fileMenu.add(saveDictionaryAsMenuItem);
		
		fileMenu.addSeparator();
		
		saveSearchResultAsMenuItem.setText("Save search result as...");
		saveSearchResultAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.Event.CTRL_MASK));
		fileMenu.add(saveSearchResultAsMenuItem);
		
		fileMenu.addSeparator();
		
		exitMenuItem.setText("Exit");
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.Event.CTRL_MASK));
		fileMenu.add(exitMenuItem);
	}

	private void createOptionsMenu() {
		optionsMenu.setText("Options");
		optionsMenu.setMnemonic('O');
		add(optionsMenu);
		
		vocableOptionsMenuItem.setMnemonic('V');
		searchOptionsMenuItem.setMnemonic('S');
		languageOptionsMenuItem.setMnemonic('L');
		vocableTableOptionsMenuItem.setMnemonic('V');
		trainingOptionsMenuItem.setMnemonic('T');
		bigCharacterBoxOptionsMenuItem.setMnemonic('B');
		dialogsOptionsMenuItem.setMnemonic('D');
		specialCharactersOptionsMenuItem.setMnemonic('C');
		searchHistoryOptionsMenuItem.setMnemonic('H');
		
		optionsMenu.add(vocableOptionsMenuItem);
		optionsMenu.add(searchOptionsMenuItem);
		optionsMenu.add(languageOptionsMenuItem);
		optionsMenu.add(vocableTableOptionsMenuItem);
		optionsMenu.add(trainingOptionsMenuItem);
		optionsMenu.add(bigCharacterBoxOptionsMenuItem);
		optionsMenu.add(dialogsOptionsMenuItem);
		optionsMenu.add(specialCharactersOptionsMenuItem);
		optionsMenu.add(searchHistoryOptionsMenuItem);
	}
	
	private void createHelpMenu() {
		helpMenu.setText("Help");
		helpMenu.setMnemonic('H');
		add(helpMenu);
		
		helpMenuItem.setText("Help");
		helpMenuItem.setMnemonic('e');
		helpMenu.add(helpMenuItem);
		infoMenuItem.setText("Info");
		infoMenuItem.setMnemonic('I');
		helpMenu.add(infoMenuItem);
	}
	
	private void addMenuActionListeners() {
		/*
		======================
		======FILE MENU=======
		======================
		*/
		createNewDictionaryMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createNewDictionaryMenuItemActionPerformed();
			}
		});
		
		openDictionaryMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openDictionaryMenuItemActionPerformed();
			}
		});
		
		saveDictionaryMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Dictionary.saveVocableList();
			}
		});
		
		saveDictionaryAsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveDictionaryAsMenuItemActionPerformed();
			}
		});
		
		saveSearchResultAsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveSearchResultAsMenuItemActionPerformed();
				
			}
		});
		
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.getSaveOnExitConfirmation().windowClosing(new WindowEvent(window, 0, null, 0, 0));
			}
		});
		
		/*
		======================
		=====OPTIONS MENU=====
		======================
		*/
		vocableOptionsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				vocableOptionsMenuItemAction();
			}
		});
		
		searchOptionsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchOptionsMenuItemAction();
			}
		});
		
		languageOptionsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				languageOptionsMenuItemAction();
			}
		});
		
		vocableTableOptionsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				vocableTableOptionsMenuItemAction();
			}
		});
		
		trainingOptionsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				trainingOptionsMenuItemAction();
			}
		});
		
		bigCharacterBoxOptionsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bigCharacterBoxOptionsMenuItemAction();
			}
		});
		
		dialogsOptionsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogsOptionsMenuItemAction();
			}
		});
		
		specialCharactersOptionsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				specialCharactersOptionsMenuItemAction();
			}
		});
		
		searchHistoryOptionsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchHistoryOptionsMenuItemAction();
			}
		});
		
		/*
		======================
		=====HELP MENU========
		======================
		*/
		
		infoMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				infoMenuItemAction();
			}
		});
		
		helpMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				helpMenuItemAction();
			}
		});
	}
	
	private void vocableOptionsMenuItemAction() {
		window.vocableOptionsDialogue = new VocableOptionsDialogue(window, true);
		window.vocableOptionsDialogue.setTitle("Vocable options");
		window.vocableOptionsDialogue.pack();
		window.vocableOptionsDialogue.setLocationRelativeTo(null);
		window.vocableOptionsDialogue.setVisible(true);
	}
	
	private void searchOptionsMenuItemAction() {
		window.searchOptionsDialogue = new SearchOptionsDialogue(window, true);
		window.searchOptionsDialogue.setTitle("Search options");
		window.searchOptionsDialogue.pack();
		window.searchOptionsDialogue.setLocationRelativeTo(null);
		window.searchOptionsDialogue.setVisible(true);
	}
	
	private void languageOptionsMenuItemAction() {
		window.languageOptionsDialogue = new LanguageOptionsDialogue(window, true);
		window.languageOptionsDialogue.setTitle("Language options");
		window.languageOptionsDialogue.pack();
		window.languageOptionsDialogue.setLocationRelativeTo(null);
		window.languageOptionsDialogue.setVisible(true);
	}
	
	private void vocableTableOptionsMenuItemAction() {
		window.vocableTableOptionsDialogue = new VocableTableOptionsDialogue(true);
		window.vocableTableOptionsDialogue.setTitle("Vocable table options");
		window.vocableTableOptionsDialogue.pack();
		window.vocableTableOptionsDialogue.setLocationRelativeTo(null);
		window.vocableTableOptionsDialogue.setVisible(true);
	}
	
	private void trainingOptionsMenuItemAction() {
		window.trainingOptionsDialogue = new TrainingOptionsDialogue(window, true);
		window.trainingOptionsDialogue.setTitle("Training options");
		window.trainingOptionsDialogue.pack();
		window.trainingOptionsDialogue.setLocationRelativeTo(null);
		window.trainingOptionsDialogue.setVisible(true);
	}
	
	private void bigCharacterBoxOptionsMenuItemAction() {
		window.bigCharacterBoxOptionsDialogue = new BigCharacterBoxOptionsDialogue(window, true);
		window.bigCharacterBoxOptionsDialogue.setTitle("Big character box options");
		window.bigCharacterBoxOptionsDialogue.pack();
		window.bigCharacterBoxOptionsDialogue.setLocationRelativeTo(null);
		window.bigCharacterBoxOptionsDialogue.setVisible(true);
	}
	
	private void dialogsOptionsMenuItemAction() {
		window.dialogsOptionsDialogue = new DialogsOptionsDialogue(window, true);
		window.dialogsOptionsDialogue.setTitle("Dialogs options");
		window.dialogsOptionsDialogue.pack();
		window.dialogsOptionsDialogue.setLocationRelativeTo(null);
		window.dialogsOptionsDialogue.setVisible(true);
	}
	
	private void specialCharactersOptionsMenuItemAction() {
		window.specialCharactersOptionsDialogue = new SpecialCharactersOptionsDialogue();
		window.specialCharactersOptionsDialogue.setTitle("Special characters options");
		window.specialCharactersOptionsDialogue.pack();
		window.specialCharactersOptionsDialogue.setLocationRelativeTo(null);
		window.specialCharactersOptionsDialogue.setVisible(true);
	}
	
	private void searchHistoryOptionsMenuItemAction() {
		window.searchHistoryOptionsDialogue = new SearchHistoryOptionsDialogue();
		window.searchHistoryOptionsDialogue.setTitle("Search History Options");
		window.searchHistoryOptionsDialogue.pack();
		window.searchHistoryOptionsDialogue.setLocationRelativeTo(null);
		window.searchHistoryOptionsDialogue.setVisible(true);
	}
	
	private void infoMenuItemAction() {
		window.infoDialogue = new InfoDialogue(window, true);
		window.infoDialogue.setTitle("Info");
		window.infoDialogue.pack();
		window.infoDialogue.setLocationRelativeTo(null);
		window.infoDialogue.setVisible(true);
	}
	private void helpMenuItemAction() {
		window.helpDialogue = new HelpDialogue(window, true);
		window.helpDialogue.setTitle("Help");
		window.helpDialogue.pack();
		window.helpDialogue.setLocationRelativeTo(null);
		window.helpDialogue.setVisible(true);
	}
	
	private void createNewDictionaryMenuItemActionPerformed() {
		fileChooser = new JFileChooser(Dictionary.vocableFilename);
		int returnValue = fileChooser.showSaveDialog(window);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			Dictionary.vocableFilename = file.getPath();
			Dictionary.createNewDictionary();
			Dictionary.saveVocableList();
		}
	}
	
	private void openDictionaryMenuItemActionPerformed() {
		fileChooser = new JFileChooser(Dictionary.vocableFilename);
		int returnValue = fileChooser.showOpenDialog(window);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			Dictionary.vocableFilename = file.getPath();
			Dictionary.loadVocableList();
		}
	}
	
	private void saveDictionaryAsMenuItemActionPerformed() {
		fileChooser = new JFileChooser(Dictionary.vocableFilename);
		int returnValue = fileChooser.showSaveDialog(window);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			Dictionary.vocableFilename = file.getPath();
			Dictionary.saveVocableList();
		}
	}
	
	private void saveSearchResultAsMenuItemActionPerformed() {
		fileChooser = new JFileChooser(Dictionary.vocableFilename);
		int returnValue = fileChooser.showSaveDialog(window);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			Dictionary.searchResultFilename = file.getPath();
			Dictionary.saveSearchResult(VocableManager.getLastSearchResult());
		}
	}
}
