/**
 Provided in 2013 TechLab Education

 The MIT License (MIT)

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

@SuppressWarnings("serial")
/**
 * A simple text editor view.
 * @author (your name) 
 */
public class TextEditor extends JApplet 
implements ActionListener {
	private TextEditorModel model;

	// add components here
	private JTextField field;
	private JTextArea area;

	// Remember whether or not we have saved our work.
	private boolean saved = false;
	private String saveFile;

	/**
	 * Called by the browser or applet viewer to inform this JApplet that it has
	 * been loaded into the system. It is always called before the first time
	 * that the start method is called.
	 */
	public void init() {
		// Create a model and view object.
		model = new TextEditorModel();
		// Set the size of this applet to the size that the view wants.
		this.setSize(new Dimension(500, 500));
		// Make a new content pan           e to organize what we put into the applet - since we only have
		// a JTextArea, we make a 1 x 1 grid to ensure the text area resizes to the entire window.
		this.setLayout(new GridLayout(1, 1));

		initializeComponents();
	}

	/**
	 * Initializes the components of this applet.
	 */
	public void initializeComponents() {
		// Initialize the buttons.
		JMenuBar bar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");

		JMenuItem newItem = new JMenuItem("New");
		fileMenu.add(newItem);
		newItem.setActionCommand("New"); 
		newItem.addActionListener(this);

		JMenuItem openItem = new JMenuItem("Open");
		fileMenu.add(openItem);
		openItem.setActionCommand("Open"); 
		openItem.addActionListener(this);

		JMenuItem saveItem = new JMenuItem("Save");
		fileMenu.add(saveItem);
		saveItem.setActionCommand("Save"); 
		saveItem.addActionListener(this);

		JMenuItem saveAsItem = new JMenuItem("Save As");
		fileMenu.add(saveAsItem);
		saveAsItem.setActionCommand("Save As"); 
		saveAsItem.addActionListener(this);

		JMenuItem clearItem = new JMenuItem("Clear");
		fileMenu.add(clearItem);
		clearItem.setActionCommand("Clear"); 
		clearItem.addActionListener(this);

		bar.add(fileMenu);

		JMenu editMenu = new JMenu("Edit");

		bar.add(editMenu);


		JMenu replaceMenu = new JMenu("Replace");
		replaceMenu.add(createItem("Remove Profanity"));
		replaceMenu.add(createItem("Shakespearean"));
		replaceMenu.add(createItem("Abbreviate"));
		replaceMenu.add(createItem("Contraction"));
		replaceMenu.add(createItem(""));
		bar.add(replaceMenu);
		setJMenuBar(bar);

		// Initializing a text area.

		area = new JTextArea(30, 30);
		JScrollPane pane = new JScrollPane(area);
		add(pane);
	}

	public JMenuItem createItem(String name) {
		JMenuItem item = new JMenuItem(name);
		item.setActionCommand(name);
		item.addActionListener(this);
		return item;
	}


	/**
	 * 
	 * This method is called whenever a component with an action value is
	 * activated.
	 */
	public void actionPerformed(ActionEvent a) {
		if (a.getSource() == field) {
			// Then our text field is sending us data.
		}
		else {
			// Otherwise, some other component is sending us data.
			String action = a.getActionCommand();
			if (action.equals("New")) {
				if (! saved) {
					//int result = JOptionPane.showConfirmDialog("Would you like to save?");
					//if (result == JOptionPane.YES_OPTION) {
					JFileChooser chooser = new JFileChooser();
					chooser.showSaveDialog(this);
					String fileName = chooser.getSelectedFile().getAbsolutePath();
					//model.saveFile(fileName = area.getText());
				}
				area.setText("");

			}
			// Get the file name to open, then set the text area to the contents.
			if (action.equals("Open")) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(this);
				String fileName = chooser.getSelectedFile().getAbsolutePath();
				String contents = model.openFile(fileName);
				area.setText(contents);
				saved = true;
				//saveFile = fileName

				// Your code here.
			}
			// Save the contents of the text area to a file, making a new file if necessary.
			if (action.equals("Save")) {
				if (saved == false) {
					JFileChooser chooser = new JFileChooser();
					chooser.showSaveDialog(this);
					String fileName = chooser.getSelectedFile().getAbsolutePath();
					model.saveFile(fileName, area.getText());
					saved = true;
					saveFile = fileName;
				}
				else {
					model.saveFile(saveFile, area.getText());
				}
				// Your code here.
			}
			// Saves the contents of the text area to a new file, even if it has already been saved.
			if (action.equals("Save As")) {
				JFileChooser chooser = new JFileChooser();
				chooser.showSaveDialog(this);
				String fileName = chooser.getSelectedFile().getAbsolutePath();
				model.saveFile(fileName, area.getText());
				saved = true;
				saveFile = fileName;
				// Your code here.
			}
			// Clears the text editor.
			if (action.equals("Clear")) {
				// Your code here.
			}
			if (action.equals("Remove Profanity")) {
				String text = area.getText();

				text = text.replaceAll("\\bheck\\b", "foo");
				text = text.replaceAll("\\bhell\\b", "foo");
				text = text.replaceAll("\\bdamn\\b", "foo");
				text = text.replaceAll("\\bdarn\\b", "foo");
				area.setText(text);
			}

			if (action.equals("Shakespearean")) {
				String text = area.getText();
				
				text = text.replaceAll("\\b(\\w+)\\b", "$1eth");
				text = text.replaceAll("\\bareeth\\b", "art");
				text = text.replaceAll("\\byoueth\\b", "thou");
				text = text.replaceAll("\\byoureth\\b", "thine");
				text = text.replaceAll("\\byourselfeth\\b", "thineself");
				area.setText(text);
			}

			if (action.equals("Abbreviate")) {
				String text = area.getText();
				
				text = text.replaceAll("\\bWhat the heck\\b", "WTH");
				text = text.replaceAll("\\bI don't know\\b", "IDK");
				area.setText(text);
			}
			if (action.equals("Contraction")) {
				String text = area.getText();
				
				text = text.replaceAll("\\bdo not\\b", "don't");
				text = text.replaceAll("\\bcannot\\b", "can't");
				text = text.replaceAll("\\bcan not\\b", "can't");
				text = text.replaceAll("\\bwould not\\b", "wouldn't");
				text = text.replaceAll("\\bwill not\\b", "won't");
				text = text.replaceAll("\\bcould not\\b", "couldn't");
				text = text.replaceAll("\\bare not\\b", "aren't");
				text = text.replaceAll("\\bdont\\b", "don't");
				text = text.replaceAll("\\bcant\\b", "can't");
				text = text.replaceAll("\\bwouldnt\\b", "wouldn't");
				text = text.replaceAll("\\bwont\\b", "won't");
				text = text.replaceAll("\\bcouldnt\\b", "couldn't");
				text = text.replaceAll("\\barent\\b", "aren't");
				area.setText(text);
			}
			
			

		}
	}
}






