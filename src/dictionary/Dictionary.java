package dictionary;

import java.awt.Font;

import javax.swing.SwingUtilities;

import com.alee.laf.WebLookAndFeel;
import com.alee.managers.language.LanguageManager;

import dialogues.FirstRunHelperDialogue;
import factories.FrameFactory;
import gui.SearchBox;


public class Dictionary {

	public static final String APPLICATION_NAME = "Xiaolong Dictionary";
	public static final String VERSION = "1.0";
//	private DictionaryMainWindow dictionaryMainWindow;
	private FirstRunHelperDialogue firstRunHelper;
	public final Settings settings;
	//private static ArrayList<Vocable> vocableList = new ArrayList<Vocable>();
	//public static ArrayList<Vocable> searchResult = new ArrayList<Vocable>();

	/**
	 * Creates a new Xiaolong Dictionary
	 * @param args
	 */
	public static void main(String[] args) {
		LanguageManager.DEFAULT = LanguageManager.ENGLISH;
		Dictionary.setLookAndFeel();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
//				try {
//					@SuppressWarnings("unused")
//					Dictionary dictionary = new Dictionary();
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
				new Dictionary();
			}
		});
	}

	public Dictionary() {
		settings = new Settings();
		FrameFactory.getDictionaryMainWindow(); //Create the instance of the main window
		
		if(Settings.firstTimeRun) {
			firstRunHelper = new FirstRunHelperDialogue(FrameFactory.getDictionaryMainWindow(), true);
		}
		
		FileManager.loadVocableList();
		
		SearchBox.searchVocableButtonActionPerformed();
		SearchHistory.restoreSearchHistory();
	}

	
	private static void setLookAndFeel() {
		/*for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			System.out.println(info.getName());
		}*/
		
		/*
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					
					//UIManager.put("TextField.inactiveBackground", new Color(214,217,223));
					//UIManager.put("FormattedTextField.inactiveBackground", new Color(214,217,223));
					//UIManager.put("PasswordField.inactiveBackground", new Color(214,217,223));
					//UIManager.put("TextField.disabledBackground", new Color(214,217,223));
					//UIManager.put("textInactiveText", new Color(214,217,223));
					
					break;
				}
			}
		} catch (Exception e) {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Metal".equals(info.getName())) {
					try {
						UIManager.setLookAndFeel(info.getClassName());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					break;
				}
			}
		}
		*/
		
		
		try {
			setUIFont();
			WebLookAndFeel.install();
            //UIManager.setLookAndFeel();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
	}
	
	public static void setUIFont() {
//		WebLookAndFeel.buttonFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.checkBoxFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.checkBoxMenuItemAcceleratorFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.checkBoxMenuItemFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.colorChooserFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.comboBoxFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.editorPaneFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.formattedTextFieldFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.globalAcceleratorFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.globalAlertFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.globalControlFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.globalMenuFont = new Font("Dialog", Font.PLAIN, 10);
		
		WebLookAndFeel.globalTextFont = new Font("Dialog", Font.PLAIN, 12);
		
//		WebLookAndFeel.globalTitleFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.globalTooltipFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.globalAlertFont = new Font("Dialog", Font.PLAIN, 10);
//		WebLookAndFeel.globalAlertFont = new Font("Dialog", Font.PLAIN, 10);
		
		
//		Enumeration<Object> keys = UIManager.getDefaults().keys();
//		while (keys.hasMoreElements()) {
//			Object key = keys.nextElement();
//			Object value = UIManager.get (key);
//			if (value != null && value instanceof FontUIResource) {
//				UIManager.put (key, f);
//			}
//		}
	}

	public void replacePinyin() {
	}

	public void showAllTopics() {
	}

	public FirstRunHelperDialogue getFirstRunHelper() {
		return firstRunHelper;
	}
}