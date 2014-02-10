package gui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

@SuppressWarnings("serial")
public class JTextFieldWithContextMenu extends JTextField {
	private JPopupMenu contextMenu;
	private UndoManager undoManager;

	public JTextFieldWithContextMenu() {
		undoManager = new UndoManager();
		contextMenu = new JPopupMenu();
		UndoAction undoAction = new UndoAction();
		
		contextMenu.add(new JMenuItem(undoAction));
		getDocument().addUndoableEditListener(undoAction);
		contextMenu.addSeparator();
		addToContextMenu("Cut", DefaultEditorKit.cutAction);
		addToContextMenu("Copy", DefaultEditorKit.copyAction);
		addToContextMenu("Paste", DefaultEditorKit.pasteAction);
		addToContextMenu("Delete", DefaultEditorKit.deleteNextCharAction);
		contextMenu.addSeparator();
		addToContextMenu("Select All", DefaultEditorKit.selectAllAction);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					contextMenu.show(JTextFieldWithContextMenu.this, e.getX(), e.getY());
				}
			}
		});
	}
	
	public JTextFieldWithContextMenu(int columns) {
		super(columns);
		undoManager = new UndoManager();
		contextMenu = new JPopupMenu();
		UndoAction undoAction = new UndoAction();
		
		contextMenu.add(new JMenuItem(undoAction));
		getDocument().addUndoableEditListener(undoAction);
		contextMenu.addSeparator();
		addToContextMenu("Cut", DefaultEditorKit.cutAction);
		addToContextMenu("Copy", DefaultEditorKit.copyAction);
		addToContextMenu("Paste", DefaultEditorKit.pasteAction);
		addToContextMenu("Delete", DefaultEditorKit.deleteNextCharAction);
		contextMenu.addSeparator();
		addToContextMenu("Select All", DefaultEditorKit.selectAllAction);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					contextMenu.show(JTextFieldWithContextMenu.this, e.getX(), e.getY());
				}
			}
		});
	}

	public JTextFieldWithContextMenu(String text) {
		super(text);
		undoManager = new UndoManager();
		contextMenu = new JPopupMenu();
		UndoAction undoAction = new UndoAction();
		
		contextMenu.add(new JMenuItem(undoAction));
		getDocument().addUndoableEditListener(undoAction);
		contextMenu.addSeparator();
		addToContextMenu("Cut", DefaultEditorKit.cutAction);
		addToContextMenu("Copy", DefaultEditorKit.copyAction);
		addToContextMenu("Paste", DefaultEditorKit.pasteAction);
		addToContextMenu("Delete", DefaultEditorKit.deleteNextCharAction);
		contextMenu.addSeparator();
		addToContextMenu("Select All", DefaultEditorKit.selectAllAction);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					contextMenu.show(JTextFieldWithContextMenu.this, e.getX(), e.getY());
				}
			}
		});
	}

	private void addToContextMenu(String label, String actionName) {
		Action action = getActionMap().get(actionName);
		if (action != null) {
			JMenuItem mi = new JMenuItem(action);
			mi.setText(label);
			contextMenu.add(mi);
		}
	}

	class UndoAction extends AbstractAction implements UndoableEditListener {
		public UndoAction() {
			super("Undo");
			setEnabled(false);
		}

		public void undoableEditHappened(UndoableEditEvent e) {
			undoManager.addEdit(e.getEdit());
			setEnabled(undoManager.canUndo());
		}

		public void actionPerformed(ActionEvent e) {
			try {
				undoManager.undo();
			} catch (CannotUndoException ex) {
				ex.printStackTrace();
			}
			setEnabled(undoManager.canUndo());
		}
	}
	/*
	 * public static void main(String[] args) { JFrame frame = new JFrame();
	 * JTextField tf = new JTextFieldWithContextMenu();
	 * frame.getContentPane().add(tf); frame.pack(); frame.setLocation(200,
	 * 200); frame.setVisible(true); }
	 */
}
