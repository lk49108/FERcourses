package hr.fer.zemris.java.hw11.jnotepadpp.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;


import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizatonProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableJLabel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.strategyForCaseChanging.CaseChanger;
import hr.fer.zemris.java.hw11.jnotepadpp.strategyForCaseChanging.CaseInvert;
import hr.fer.zemris.java.hw11.jnotepadpp.strategyForCaseChanging.CaseToLower;
import hr.fer.zemris.java.hw11.jnotepadpp.strategyForCaseChanging.CaseToUpper;
import hr.fer.zemris.java.hw11.jnotepadpp.strategyForOrdering.AscendingSort;
import hr.fer.zemris.java.hw11.jnotepadpp.strategyForOrdering.DescendingSort;
import hr.fer.zemris.java.hw11.jnotepadpp.strategyForOrdering.ISort;

/**
 * This class extends {@link JFrame} class
 * and is used for rendering and manipulation of GUI.
 * This GUI allows user to manipulate with text documents, 
 * i.e. to edit them, to open some readable file, 
 * modify it and save it, to create new readable file etc, all
 * that in multiple tabs feature. 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class JNotepadPPWindow extends JFrame {
	
	/**
	 * String representing current language being used in {@link JNotepadPPWindow}.
	 */
	private String language = "en";
	
	/**
	 * Instance variable holding reference to {@link FormLocalizationProvider}
	 * instance, allows localization mechanism to work.
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizatonProvider.getInstance(), this);;
	
	/**
	 * Variable holding default background color used in this program.
	 */
	private final Color BACKGROUND = Color.DARK_GRAY;
	
	/**
	 * Variable holding format of shown date and time.
	 */
	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	/**
	 * Default tab title.
	 */
	private final static String DEFAULT_TAB_TITLE = "Untitled";
	
	/**
	 * Path to image from which {@link #modifiedIcon} is created.
	 */
	private final static String PATH_TO_MODIFIED_ICON = "../icons/modified.png";
	
	/**
	 * Path to image from which {@link #unModifiedIcon} is created.
	 */
	private final static String PATH_TO_UNMODIFIED_ICON = "../icons/unmodified.png";

	/**
	 * {@link ImageIcon} which is displayed on a tab of
	 * document(file) which has been modified.
	 */
	private final ImageIcon MODIFIED_ICON = createIcon(PATH_TO_MODIFIED_ICON);
	
	/**
	 * {@link ImageIcon} which is displayed on a tab of
	 * document(file) which has not been modified.
	 */
	private final ImageIcon UNMODIFIED_ICON = createIcon(PATH_TO_UNMODIFIED_ICON);
	
	/**
	 * {@link JTabbedPane} instance added to this {@link JNotepadPPWindow}.
	 */
	private JTabbedPane pane;
	
	/**
	 * Text which is displayed as a title on {@link JFileChooser} when user chooses
	 * file which he wants to import into this {@link JNotepadPPWindow}.
	 */
	private final static String FILE_CHOOSER_DIALOG_TITLE = "Open file from disc";
	
	/**
	 * {@link Charset} used in encoding int this class methods. 
	 */
	private final Charset CHARSET_USED = StandardCharsets.UTF_8;

	/**
	 * Default tab tip.
	 */
	private final String DEFAULT_TAB_TIP = "Untitled";
	
	/**
	 * Map holding pairs (JPanel, JLabel), where first panel is panel being
	 * selected by current tab. The other JLabel is one holding
	 * length information in it.
	 */
	private Map<JPanel, LocalizableJLabel> statusMapLength = new HashMap<>();
	
	/**
	 * {@link Map} holding pairs (JPanel, JTextArea).
	 * JPanel is added to {@link #pane} and corresponding {@link JTextArea} is 
	 * added to this {@link JPanel}. Eases the way in which reference to
	 * specific {@link JTextArea} is got.
	 */
	private Map<JPanel, JTextArea> map = new HashMap<>();
	
	/**
	 * Map holding pairs (JPanel, JLabel), where first panel is panel being
	 * selected by current tab. The other JLabel is one holding
	 * caret line information in it.
	 */
	private Map<JPanel, LocalizableJLabel> statusMapCaretLine = new HashMap<>();
	
	/**
	 * Map holding pairs (JPanel, JLabel), where first panel is panel being
	 * selected by current tab. The other JLabel is one holding
	 * caret column information in it.
	 */
	private Map<JPanel, LocalizableJLabel> statusMapCaretColumn = new HashMap<>();
	
	/**
	 * Map holding pairs (JPanel, JLabel), where first panel is panel being
	 * selected by current tab. The other JLabel is one holding
	 * caret selection information in it.
	 */
	private Map<JPanel, LocalizableJLabel> statusMapCaretSelection = new HashMap<>();
	
	/**
	 * Map holding pairs (JPanel, JLabel), where first panel is panel being
	 * selected by current tab. The other JLabel is one holding
	 * date and time information in it.
	 */
	private Map<JPanel, JLabel> statusMapTime = new HashMap<>();
	
	
	/**
	 * Instance variable used for storing text which is
	 * copied or cut by corresponding actions. 
	 */
	private String textStore;

	/**
	 * Default {@link JNotepadPPWindow} title.
	 */
	private final String DEFAULT_WINDOW_TITLE = "JNotepad++";

	/**
	 * Variable which is set to be true when program is finished. It allows timeThread
	 * to stop its execution.
	 */
	private boolean exit = false;
	
	/**
	 * changeCase JMenu.
	 */
	private JMenu changeCase;
	
	/**
	 * sort JMenu
	 */
	private JMenu sort; 
	
	/**
	 * Boolean instance which value, when set to true, causes
	 * while loop in method {@link #refreshSelection(Caret)} to break.
	 */
	private boolean selectionResizingOver;

	/**
	 * {@link MouseAdapter} which is used to perceive events when mouse is pressed
	 * and when it is released.
	 */
	MouseAdapter mouseListener = new MouseAdapter() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			selectionResizingOver = true;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			Thread selectionWork = new Thread(() -> {
				int position = pane.getSelectedIndex();
				if(position == -1) return;
				
				JPanel panel = (JPanel)pane.getComponentAt(position);
				JTextArea textArea = map.get(panel);
				
				Caret caret = textArea.getCaret();
				LocalizableJLabel caretSelection = statusMapCaretSelection.get(panel);
				refreshSelection(caret, caretSelection);
			});
			selectionWork.start();
		}	
		
		@Override
		public void mouseClicked(MouseEvent e) {
			int position = pane.getSelectedIndex();
			if(position == -1) return;
			
			JPanel panel = (JPanel)pane.getComponentAt(position);
			JTextArea textArea = map.get(panel);
			
			Caret caret = textArea.getCaret();
			LocalizableJLabel caretSelection = statusMapCaretSelection.get(panel);
			caretSelection.changeAdditionalText(Integer.toString(getSelectionSize(caret)));
		}
	};
	
	/**
	 * Constructor which creates {@link JNotepadPPWindow} instance
	 * and causes initial-look GUI to be rendered.
	 */
	public JNotepadPPWindow(){
		super();
		
		setDefaultCloseOperation(
				WindowConstants.DO_NOTHING_ON_CLOSE
				);
		setLocation(300, 200);
		setSize(500, 500);
		setTitle(DEFAULT_WINDOW_TITLE);
			
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				checkForClosing();
			}
		});
		
		Thread timeThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					if(exit) break;
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					SwingUtilities.invokeLater(new Runnable() {
						
				
						@Override
						public void run() {
							int selected = pane.getSelectedIndex();
							if(selected == -1) return;
							try {
								JPanel panelSelected = (JPanel)pane.getComponentAt(
										selected);		
								statusMapTime.get(panelSelected).setText(dateAndTime());
							} catch(NullPointerException unprocessed){}
						}
					});
				}
			}
		});
		timeThread.start();
	
		initGUI();
		
	}

	/**
	 * Receives the length of text currently being selected (if user selected anything); as selection grows, it
	 * automatically updates selection section i status bar. 
	 * @param caret
	 */
	private void refreshSelection(Caret caret, LocalizableJLabel caretSelection) {
			
		while(!selectionResizingOver){
			int selectionSize = getSelectionSize(caret);
			caretSelection.changeAdditionalText(Integer.toString(selectionSize));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		selectionResizingOver = false;
	}

	/**
	 * Creates {@link ImageIcon} instance from
	 * image, to which, appropriate path(as a {@link String} instance) is provided.
	 * @param pathToImage {@link String} representation of path to image.
	 * @return New instance of {@link ImageIcon} created from provided path's image.
	 */
	private ImageIcon createIcon(String pathToImage) {
		if(pathToImage == null){
			throw new IllegalArgumentException("Path provided must not be null reference.");
		}
		
		InputStream is = this.getClass().getResourceAsStream(pathToImage);

		if(is == null){
			System.out.println("Provided path " + pathToImage + " do not exist.");
		}

		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(is);
		} catch (IOException e1) {
			System.err.println("Error occured while reading " + pathToImage + " image.");
		}
		return new ImageIcon(bufferedImage);
	}

	/**
	 * Method called when this {@link JNotepadPPWindow} is attempted
	 * to be shut down. It checks if any of open tabs are not saved
	 * and asks user if he wants to save that specific document.
	 */
	protected void checkForClosing() {
		int num = pane.getTabCount();
		if(num == 0){
			dispose();
			exit = true;
			return;
		}
		pane.setSelectedIndex(0);
		closeTabAction.actionPerformed(
				new ActionEvent(JNotepadPPWindow.this,
				ActionEvent.ACTION_PERFORMED,
				null));
		checkForClosing();
	}

	/**
	 * Initializes GUI, i.e. initializes look of {@link JNotepadPPWindow} and renders it.
	 */
	private void initGUI() {
		
		createTabbedPane();
		
		createActions();
		
		createMenus();
		
		createToolbars();
	}

	

	/**
	 * Creates {@link JTabbedPane} and inserts it into {@link JNotepadPPWindow}.
	 * {@link JTabbedPane} is used for holding tabs of different documents user
	 * holds open in its {@link JNotepadPPWindow}. Listener is also added to {@link JTabbedPane}
	 * which is notified when user selects new tab.
	 */
	private void createTabbedPane() {
		JTabbedPane pane = new JTabbedPane();
		
		this.pane = pane;
		
		JPanel panel = createTextAreaAndStatusBar(null);
		
		pane.addTab(DEFAULT_TAB_TITLE,
				UNMODIFIED_ICON, 
				panel,
				DEFAULT_TAB_TIP);
		
		pane.addContainerListener(new ContainerListener() {
			
			@Override
			public void componentRemoved(ContainerEvent e) {
				doChangeOfSelection();	
			}
			
			@Override
			public void componentAdded(ContainerEvent e) {
				doChangeOfSelection();
			}
			
			/**
			 * Method used for changing of selection size.
			 */
			private void doChangeOfSelection(){
				int selected = pane.getSelectedIndex();
				if(selected == -1) return;
				JPanel panel = (JPanel)pane.getComponentAt(selected);
				LocalizableJLabel caretSelection = statusMapCaretSelection.get(panel);
				caretSelection.changeAdditionalText("0");
			}
			
		});
		
		pane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int selected = pane.getSelectedIndex();
				if(selected == -1) {
					JNotepadPPWindow.this.setTitle(DEFAULT_WINDOW_TITLE);
					return;
				}
				JNotepadPPWindow.this.setTitle(pane.getToolTipTextAt(selected)
						+ " - " + DEFAULT_WINDOW_TITLE);
				
				//Update information about length of currently opened document
				JPanel panel = (JPanel)pane.getComponentAt(selected);
				lengthChanged(map.get(panel).getDocument());
				JTextArea textArea = map.get(panel);
				
				try{
					LocalizableJLabel caretSelection = statusMapCaretSelection.get(panel);
					Caret caret = textArea.getCaret();
					caretSelection.changeAdditionalText(Integer.toString(getSelectionSize(caret)));
				} catch (NullPointerException ex){}
				
			}
		});
		
		//Create ChangeEvent manually so title of JNotepadPPWindow changes to appropriate value.
		ChangeListener listener = pane.getChangeListeners()[0];
		listener.stateChanged(new ChangeEvent(JNotepadPPWindow.this));
		
		getContentPane().add(pane);
		
	}
	
	/**
	 * Creates new {@link JPanel}, {@link JTextArea}
	 * and {@link JScrollPane}. Adds {@link JTextArea} into
	 * {@link JScrollPane}. Latter is then added into
	 * {@link JPanel} with {@link LayoutManager} set to {@link GridLayout}
	 * with dimensions set to (1,1). Additionally {@link DocumentListener}
	 * is added to corresponding model of created {@link JTextArea}.
	 * When textArea is modified, icon next to corresponding tab is changed
	 * into one which signalizes that documents has been changed.
	 * @return {@link JPanel} created in this method.
	 */
	private JPanel createTextAreaAndStatusBar(String textToSet){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JTextArea textArea = new JTextArea();
		
		textArea.addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				updateCarret(textArea);
			}

		});
		
		textArea.addMouseListener(mouseListener);
		
		if(textToSet != null){
			textArea.setText(textToSet);
		}
		
		Document doc = textArea.getDocument();

		doc.addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModifiedIcon();
				lengthChanged(doc);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModifiedIcon();
				lengthChanged(doc);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModifiedIcon();
				lengthChanged(doc);
			}
			
			/**
			 * Sets modified icon to specific tab.
			 */
			private void setModifiedIcon() {
				int index = pane.getSelectedIndex();
				pane.setIconAt(index, MODIFIED_ICON);				
			}
			
		});
		
		map.put(panel, textArea);
		panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
		
		//Status bar...
		JPanel statusBarPanel = createStatusBar();

		//I will not add statusBarPanel into JScrollPane 
		//because i think it is not necessary
		panel.add(statusBarPanel, BorderLayout.SOUTH);
		
		statusMapLength.put(panel, (LocalizableJLabel)((JPanel)statusBarPanel.getComponent(0)).getComponent(0));
		statusMapTime.put(panel, (JLabel)statusBarPanel.getComponent(1));
		
		
		JPanel caretStatusPanel = (JPanel)((JPanel)statusBarPanel.getComponent(0)).getComponent(1);
		
		statusMapCaretLine.put(panel, (LocalizableJLabel)caretStatusPanel.getComponent(0));
		statusMapCaretColumn.put(panel, (LocalizableJLabel)caretStatusPanel.getComponent(1));
		statusMapCaretSelection.put(panel, (LocalizableJLabel)caretStatusPanel.getComponent(2));

		return panel;
	}
	
	/**
	 * Updates informations about caret provided through statusBar.
	 */
	private void updateCarret(JTextArea textArea) {
		
		int position = pane.getSelectedIndex();
		if(position == -1) return;
		JPanel panel = (JPanel)pane.getComponentAt(position);
		
		//Obtaining right LocalizableJLabels
		LocalizableJLabel caretLine = statusMapCaretLine.get(panel);
		LocalizableJLabel caretColumn = statusMapCaretColumn.get(panel);

		Document doc = textArea.getDocument();
	
		Caret caret = textArea.getCaret();
		
		//Enables and disables particular JMenu's
		if(caret.getDot() != caret.getMark()){
			this.changeCase.setEnabled(true);
			this.sort.setEnabled(true);
		} else {
			this.changeCase.setEnabled(false);
			this.sort.setEnabled(false);
		}

		int line = -1;
		try {
			line = calculateCaretLine(caret.getDot(), doc.getText(0, caret.getDot()).toCharArray());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		int column = -1;
		try {
			column = calculateCaretColumn(caret.getDot(), doc.getText(0, caret.getDot()).toCharArray());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		//Setting right texts
		caretLine.changeAdditionalText(Integer.toString(line));
		caretColumn.changeAdditionalText(Integer.toString(column));

	}
	
	/**
	 * Calculates how many characters are being selected
	 * in document currently being opened in this {@link JNotepadPPWindow}.
	 * '\n' and such characters are also included.
	 * @param caret
	 * @return
	 */
	private int getSelectionSize(Caret caret){
		return Math.abs(caret.getDot() - caret.getMark());
	}
	
	/**
	 * Calculates column in which caret is currently situated.
	 * @param position Position of caret in document.
	 * @param text Document text based on which caret column is derived.
	 * @return Current column in which caret is situated.
	 */
	private int calculateCaretColumn(int position, char[] text){
		if(position == 0) return 0;
		int count = 0;
		for(int i = position - 1; i >= 0; i--){
			if(text[i] == '\n'){
				break;
			}
			count++;
		}
		return count;
	}
	
	/**
	 * Calculates row in which caret is currently situated.
	 * @param position Position of caret in document.
	 * @param text Document text which is being checked for having '\n' characters in itself.
	 * @return Current line in which caret is situated.
	 */
	private int calculateCaretLine(int position, char[] text){
		if(position == 0) return 0;
		int count = 0;
		for(int i = 0; i < position; i++){
			if(text[i] == '\n'){
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Updates information about length of current document on statusBar.
	 */
	private void lengthChanged(Document doc){
		int selected = pane.getSelectedIndex();
		JPanel panel = (JPanel)pane.getComponentAt(selected);
		LocalizableJLabel lengthLabel = statusMapLength.get(panel);
		lengthLabel.changeAdditionalText(Integer.toString(doc.getLength()));
	}
	
	/**
	 * Creates JPanel holding JLabels which display informations
	 * about length of current document, about position
	 * of caret, and how many characters are selected.
	 * @return JPanel holding status bar.
	 */
	private JPanel createStatusBar() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2, 1, 0));
		panel.setBackground(BACKGROUND);
		
		JPanel panelLeft = statusPanel();
				
		panel.add(panelLeft);
		panel.add(timePanel());
		
		return panel;
	}

	/**
	 * Calculates current date and time and returns
	 * {@link String} representation of it.
	 * @return {@link String} instance holding date and time in itself. 
	 */
	private String dateAndTime(){
		Date date = new Date();
		if(DATE_FORMAT == null) return "";
		return DATE_FORMAT.format(date);
	}
	
	
	/**
	 * Creates {@link JLabel} which displays current time.
	 * @return New {@link JLabel} instance.
	 */
	private JLabel timePanel() {
		JLabel timeLabel = new JLabel(dateAndTime(), SwingConstants.RIGHT);
		timeLabel.setOpaque(true);
		
		return timeLabel;
	}

	/**
	 * Creates status panel and returns appropriate {@link JPanel} instance.
	 * @return New {@link JPanel} instance holding status panel inside of itself.
	 */
	private JPanel statusPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2, 1, 0));
		panel.setBackground(BACKGROUND);
		
		LocalizableJLabel labelLen = new LocalizableJLabel(flp, "length", "");
		labelLen.setOpaque(true);
		
		JPanel panel2 = getStatusJPanel();

		panel.add(labelLen);
		panel.add(panel2);
		
		return panel;
	}

	/**
	 * Creates part of status bar where
	 * caret line, column and selection are displayed.
	 * @return {@link JPanel} containing {@link JLabel}'s 
	 * used as a part of status bar.
	 */
	private JPanel getStatusJPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));
		
		JLabel labelOne = new LocalizableJLabel(flp, "line", "");
		JLabel labelTwo = new LocalizableJLabel(flp, "column", "");
		JLabel labelThree = new LocalizableJLabel(flp, "selection", "");
		
		panel.add(labelOne);
		panel.add(labelTwo);
		panel.add(labelThree);
		
		return panel;
	}
	
	/**
	 * Action, when triggered creates new document and corresponding tab
	 * using {@link #createTextArea()} method.
	 */
	private Action newDocumentAction = new LocalizableAction(flp, "new") {
		
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JPanel panel = createTextAreaAndStatusBar(null);
			
			pane.addTab(DEFAULT_TAB_TITLE,
					UNMODIFIED_ICON,
					panel,
					DEFAULT_TAB_TIP);
		
		}
	};
	
	/**
	 * Action, which, when triggered open file
	 * specified by user. {@link JFileChooser} is used in 
	 * selection of file which will be imported.
	 */
	private Action openDocumentAction = new LocalizableAction(flp, "open") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle(FILE_CHOOSER_DIALOG_TITLE);
			
			if(chooser.showOpenDialog(JNotepadPPWindow.this) != JFileChooser.APPROVE_OPTION){
				return;
			}
			
			File fileName = chooser.getSelectedFile();
			Path filePath = fileName.toPath();
			if(!Files.isReadable(filePath)){
				JOptionPane.showMessageDialog(JNotepadPPWindow.this, 
						"File " + fileName.getAbsolutePath() + " is not readable.", 
						"Error", 
					JOptionPane.ERROR_MESSAGE);
				return;
			}
			byte[] bytes;
			try {
				bytes = Files.readAllBytes(filePath);
			} catch(Exception ex){
				JOptionPane.showMessageDialog(
						JNotepadPPWindow.this,
					"Mistake while reading " + fileName.getAbsolutePath() + " file occured.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
				return;
			}
			String text = new String(bytes, CHARSET_USED);
			
			JPanel panel = createTextAreaAndStatusBar(text);
			
			pane.addTab(filePath.getFileName().toString(), 
					UNMODIFIED_ICON, 
					panel, 
					fileName.getAbsolutePath()
					);
			
			
		}
		
	};
	
	/**
	 * {@link Action} instance used for closing
	 * tab which is currently selected. Additionally,
	 * user is asked(if changes to file has been made)
	 * if he wants to save changes made
	 * to file before corresponding tab is closed.
	 */
	private Action closeTabAction =new LocalizableAction(flp, "close") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int position = pane.getSelectedIndex();

			if(position == -1){
				return;
			}
			
			removeAllMapElements(position);
			
			if(pane.getIconAt(position).equals(UNMODIFIED_ICON)){
				removeTab(position);
				return;
			}
			
			int decision = JOptionPane.showConfirmDialog(JNotepadPPWindow.this,
					"Unsaved file \"" + pane.getTitleAt(position) + "\", do you want to"
							+ "save changes?",
					"OS message",
					JOptionPane.YES_NO_OPTION);
			if(decision != JOptionPane.YES_OPTION) {
				removeTab(position);
				return;
			}
			
			//Manually invoke saveDocumentAction.
			//Consequently currently selected file is saved.
			saveDocumentAction.actionPerformed(
					new ActionEvent(JNotepadPPWindow.this, 
						ActionEvent.ACTION_PERFORMED, 
						null));
			removeTab(position);
			
	
		}
		
		/**
		 * Removes all outdated elements contained in private maps in this
		 * class. I.e. {@link #map}, {@link #statusMapTime}, {@link #statusMapCaret}
		 * and {@link #statusMapLength}.
		 * @param position Position of tab which is being removed and which corresponding
		 * element from above mentioned maps must be removed.
		 */
		private void removeAllMapElements(int position) {
			JPanel correspondingPanel = (JPanel)pane.getComponentAt(position);
			map.remove(correspondingPanel);
			statusMapCaretColumn.remove(correspondingPanel);
			statusMapCaretLine.remove(correspondingPanel);
			statusMapCaretSelection.remove(correspondingPanel);
			statusMapLength.remove(correspondingPanel);
			statusMapTime.remove(correspondingPanel);
		}

		/**
		 * Removes tab specified by index provided as parameter.
		 * @param index Index of tab which will be closed.
		 */
		private void removeTab(int index){
			if(index < 0 || index >= pane.getTabCount()){
				throw new IllegalArgumentException("Illegal index, index should be between 0 and " + (pane.getTabCount() - 1) + "(inclusive).");
			}
			pane.removeTabAt(index);
		}
	};
	
	/**
	 * {@link Action}, when triggered causes selected document to 
	 * be saved, i.e. destination of document do not change, just
	 * its containment. If document which still do not have its 
	 * destination on disk is attempted to be saved,
	 * {@link #saveAsDocumentAction} is triggered instead.
	 */
	private Action saveDocumentAction = new LocalizableAction(flp, "save") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int selected = pane.getSelectedIndex();
			
			String tabTitle = pane.getTitleAt(selected);
			
			//Cannot be saved, must be Saved As...
			if(tabTitle.equals(DEFAULT_TAB_TITLE)){
				saveAsDocumentAction.actionPerformed(
						new ActionEvent(JNotepadPPWindow.this, 
						ActionEvent.ACTION_PERFORMED, 
						null
						));
				return;
			}
			
			JPanel panel = (JPanel) pane.getComponent(selected);
			JTextArea textArea = map.get(panel); 
			
			byte[] bytes = textArea.getText().getBytes(CHARSET_USED);
			Path path = Paths.get(pane.getToolTipTextAt(selected));
			
			try{
				Files.write(path, bytes);
			} catch(Exception ex){
				JOptionPane.showMessageDialog(
						JNotepadPPWindow.this,
					"Mistake while saving " + path.toString() + " file occured.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			}
			
		}
	};
	
	/**
	 * {@link Action}, when triggered, causes selected document to
	 * be Saved As. {@link JFileChooser} is used in selection of destination
	 * into which currently selected document will be saveed into.
	 */
	private Action saveAsDocumentAction = new LocalizableAction(flp, "saveAs") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int selected = pane.getSelectedIndex();
			
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save as");
			if(jfc.showSaveDialog(JNotepadPPWindow.this) != JFileChooser.APPROVE_OPTION){
				JOptionPane.showMessageDialog(
						JNotepadPPWindow.this,
						"Nothing vas saved.",
						"Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			Path fileSaveAsPath = jfc.getSelectedFile().toPath();
			if(Files.exists(fileSaveAsPath)){
				int decision = JOptionPane.showConfirmDialog(JNotepadPPWindow.this,
						"File already exists do you want to overwrite it?",
						"OS message",
						JOptionPane.YES_NO_OPTION);
				if(decision != JOptionPane.YES_OPTION) return;
			}
			
			JPanel panel = (JPanel) pane.getComponent(selected);
			JTextArea textArea = map.get(panel); 
			byte[] bytes = textArea.getText().getBytes(CHARSET_USED);
			try{
				Files.write(fileSaveAsPath, bytes);
			} catch(Exception ex){
				JOptionPane.showMessageDialog(
						JNotepadPPWindow.this,
					"Mistake while saving " + fileSaveAsPath.toString() + " file occured.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//Changes corresponding tab's layout.
			changeTab(selected, fileSaveAsPath);
			
			//Called because document was Saved As and consequently, this
			//JNotepadPPWindow must change its title so it matches with a path
			//of file currently been opened in this JNotepadPPWindow.
			ChangeListener[] listeners = pane.getChangeListeners();
			listeners[0].stateChanged(new ChangeEvent(JNotepadPPWindow.this));
		}
	};
	
	/**
	 * Causes pane's specific tab to change it's
	 * title and tool tip text. This method is invoked when
	 * e.g. document is Saved As.
	 * @param index Index of tab to be changed.
	 * @param path New path to file which will be added
	 * to tab specified with index.
	 */
	private void changeTab(int index, Path path) {
		pane.setTitleAt(index, path.getFileName().toString());
		pane.setToolTipTextAt(index, path.toString());
	}
	
	/**
	 * {@link Action}, when triggered, causes selected text to
	 * be copied.
	 */
	private Action copySelectedTextAction = new LocalizableAction(flp, "copy") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			cutCopyMethod(false);
		}
	};
	
	/**
	 * {@link Action}, when triggered, causes selected text to
	 * be cut.
	 */
	private Action cutSelectedTextAction = new LocalizableAction(flp, "cut") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			cutCopyMethod(true);
		}
	};
	
	/**
	 * Method used by, both copy and cut {@link Action}.
	 * @param deleteText boolean type parameter which, when set
	 * to true(action cut) causes selected text to be deleted.
	 * Otherwise(action copy) selected text remains untouched.
	 */
	private void cutCopyMethod(boolean deleteText){
		JTextArea textArea = getActiveTextArea();
			
		Document doc = textArea.getDocument();
		
		int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
		if(len == 0) return;
		
		int offset = Math.min(textArea.getCaret().getDot(),
				textArea.getCaret().getMark());
		
		try{
			textStore = doc.getText(offset, len);
			if(deleteText){
				doc.remove(offset, len);
			}
		} catch(BadLocationException ex){
			JOptionPane.showMessageDialog(
					JNotepadPPWindow.this,
				"Mistake while cutting selected text occured.",
				"Error",
				JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * {@link Action}, when triggered causes text stored in {@link #textStore}
	 * to be inserted in document at selected spot. If any text in document is marked,
	 * first, selected text is deleted and after that pasting continues.
	 */
	private Action pasteSelectedTextAction = new LocalizableAction(flp, "paste") {

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = getActiveTextArea();

			Document doc = textArea.getDocument();
			
			//Checking if there is selected text. If statement is true, selected text is being deleted.
			if(textArea.getCaret().getDot() != textArea.getCaret().getMark()){
				int len = Math.abs(
						textArea.getCaret().getDot() - textArea.getCaret().getMark()
						);
				int offset = Math.min(textArea.getCaret().getDot(),
						textArea.getCaret().getMark());
				try{
					doc.remove(offset, len);
				} catch(BadLocationException ex){
					JOptionPane.showMessageDialog(
							JNotepadPPWindow.this,
						"Mistake while deleting selected text occured.",
						"Error",
						JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			//Insert, i.e. paste String
			try{
				doc.insertString(textArea.getCaretPosition(), textStore, null);
			} catch(BadLocationException ex){
				JOptionPane.showMessageDialog(
						JNotepadPPWindow.this,
					"Mistake while pasting text occured.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			}
		}
	};
	
	/**
	 * Fetches active textArea and returns it.
	 * @return Active {@link JTextArea}.
	 */
	private JTextArea getActiveTextArea(){
		int selected = pane.getSelectedIndex();

		JPanel panel = (JPanel) pane.getComponent(selected);
		return map.get(panel);
	}
	
	/**
	 * {@link Action}, when triggered, causes informations about how many lines, 
	 * how many characters and how many non-blank characters
	 * are in selected document, to be displayed to user by {@link JOptionPane}.
	 */
	private Action statisticalInfoOfDocumentAction = new LocalizableAction(flp, "info") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JTextArea textArea = getActiveTextArea();

			char[] chars = textArea.getText().toCharArray();
			
			int numOfBlank = findBlank(chars);
			int numOfLines = findNumOfLines(chars);
			
			JOptionPane.showMessageDialog(
					JNotepadPPWindow.this,
				"Your document has " + chars.length + " characters, "
				+ (chars.length - numOfBlank) + " non-blank characters and "
				+ numOfLines + " lines.",
				"Statistical info",
				JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		

		/**
		 * Calculates how many lines are there in provided text
		 * represented by char array.
		 * @param text char array.
		 * @return Number of lines in provided text.
		 */
		private int findNumOfLines(char[] text) {
			if(text.length == 0) return 0;
			int numOfLines = 0;
			for(char sign : text){
				if(sign == '\n'){
					numOfLines++;
				}
			}
			return numOfLines + text[text.length - 1] == '\n' ? 0 : 1;
		}

		/**
		 * Calculates how many blank characters
		 * are there in provided text
		 * represented by char array.
		 * @param text char array.
		 * @return Number of blank characters in provided text.
		 */
		private int findBlank(char[] text) {
			int num = 0;
			for(char sign: text){
				if(Character.isWhitespace(sign)){
					num++;
				}
			}
			return num;
		}
		
	};
	
	/**
	 * Action instance, which, when triggered causes change of 
	 * {@link JNotepadPPWindow} language to Croatian language. 
	 */
	private Action croatianLanguageAction = new LocalizableAction(flp, "hr") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			language = "hr";
			LocalizatonProvider.getInstance().setLanguage("hr");
		}
	};
	
	/**
	 * Action instance, which, when triggered causes change of 
	 * {@link JNotepadPPWindow} language to German language. 
	 */
	private Action germanLanguageAction = new LocalizableAction(flp, "de") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			language = "de";
			LocalizatonProvider.getInstance().setLanguage("de");
		}
	};
	
	/**
	 * Action instance, which, when triggered causes change of 
	 * {@link JNotepadPPWindow} language to English language. 
	 */
	private Action englishLanguageAction = new LocalizableAction(flp, "en") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			language = "en";
			LocalizatonProvider.getInstance().setLanguage("en");
		}
	};
	
	/**
	 * Action, which, when triggered, causes selected text to change all
	 * of its letters to upper cases. 
	 */
	private Action toUpperCaseAction = new LocalizableAction(flp, "toUpper") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			caseChange(new CaseToUpper());
		}
	};
	
	/**
	 * Action, which, when triggered, causes selected text to change all
	 * of its letters to lower cases. 
	 */
	private Action toLowerCaseAction = new LocalizableAction(flp, "toLower") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			caseChange(new CaseToLower());
		}
	};
	
	/**
	 * Action, which, when triggered, causes selected text to change all
	 * of its letters to invert case, i.e. if letter is lower case, it will be changed to upper case
	 * and vise versa. 
	 */
	private Action invertCaseAction = new LocalizableAction(flp, "invertCase") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			caseChange(new CaseInvert());
		}
	};
	
	/**
	 * Method used to change cases, which case change will be applied depends
	 * on CaseChanger which was provided.
	 */
	private void caseChange(CaseChanger caseChanger){
		JTextArea textArea = getActiveTextArea();
		
		Document doc = textArea.getDocument();
		
		Caret caret = textArea.getCaret();

		int offSet = Math.min(caret.getDot(), caret.getMark());
		
		int length = Math.abs(caret.getDot() - caret.getMark());
		
		try {
			String text = doc.getText(offSet, length);
			text = caseChanger.changeCase(text);
			doc.remove(offSet, length);
			doc.insertString(offSet, text, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Action, which, when triggered, causes selected rows of text to be sorted in
	 * descending order.
	 */
	private Action sortDescendingAction = new LocalizableAction(flp, "descending") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			sort(new DescendingSort());
		}
	};
	
	/**
	 * Action, which, when triggered, causes selected rows of text to be sorted in
	 * ascending order.
	 */
	private Action sortAscendingAction = new LocalizableAction(flp, "ascending") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			sort(new AscendingSort());
		}
	};
	
	/**
	 * Sorts selected text.
	 * @param sort {@link ISort} instance, determines if applyed sort
	 * will be of ascending or of descending order.
	 */
	private void sort(ISort sort){
		JTextArea textArea = getActiveTextArea();
		
		Caret caret = textArea.getCaret();
		
		int dot = caret.getDot();
		int mark = caret.getMark();
		
		int line = -1;
		int placeForInsertion = -1;
		try {
			line = textArea.getLineOfOffset(Math.min(dot, mark));
			placeForInsertion = textArea.getLineStartOffset(line);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		String[] linesToSort = getLinesToSort(textArea, dot, mark); 
		if(linesToSort == null) return;
		
		Locale locale = new Locale(this.language);
		
		linesToSort = sort.sort(linesToSort, Collator.getInstance(locale));
		
		for(int i = linesToSort.length - 1; i >= 0; i--){
			textArea.insert(linesToSort[i], placeForInsertion);
		}
	}
	
	/**
	 * Finds out which lines from currently active {@link JTextArea}
	 * are to be sorted. It also deletes Strings which are to be sorted from
	 * JTextArea.
	 * @param textArea Currently active {@link JTextArea}.
	 * @param dot Position of dot in {@link JTextArea}.
	 * @param mark Position of mark in {@link JTextArea}.
	 * @return Array of Strings(lines) which are to be sorted or null
	 * if there is nothing to be sorted. 
	 */
	private String[] getLinesToSort(JTextArea textArea, int dot, int mark){
		int offset = Math.min(dot, mark);
		int end = Math.max(mark, dot);
		
		int lineBegin = -1;
		int lineEnd = -1; 
		try {
			lineBegin = textArea.getLineOfOffset(offset);
			lineEnd = textArea.getLineOfOffset(end);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		if(lineEnd == lineBegin) return null;
		String[] lines = new String[lineEnd - lineBegin + 1];
		int j = 0;
		
		for(int i = lineBegin; i <= lineEnd; i++){
			int lineFrom = -1;
			int lineTo = -1;
			
			try {
				lineFrom = textArea.getLineStartOffset(i);
				lineTo = textArea.getLineEndOffset(i);
				lines[j++] = textArea.getText(lineFrom, lineTo - lineFrom);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			
		}
		
		//Checking if there is newLine character missing at the end of last line...Must fix this or if this line will change with any one of the rest,
		//two lines will seal together(NOT GOOD).
		String eol = System.getProperty("line.separator");
		if(!lines[lines.length - 1].contains(eol)){
			lines[lines.length - 1] = lines[lines.length - 1] + eol;
		}
		
		//Erase those lines
		Document doc = textArea.getDocument();
		try {
			doc.remove(textArea.getLineStartOffset(lineBegin), 
					textArea.getLineEndOffset(lineEnd) - textArea.getLineStartOffset(lineBegin));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		return lines;
	}
	
	/**
	 * This action, when triggered exits {@link JNotepadPPWindow}.
	 */
	private Action exitAction = new LocalizableAction(flp, "exit") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			checkForClosing();
		}
	};
	
	/**
	 * Creates dockable toolbar with {@link JButton} instances
	 * corresponding to defined {@link Action} instances
	 * provided in it.
	 */
	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);
		
		toolBar.add(new JButton(newDocumentAction));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsDocumentAction));
		toolBar.add(new JButton(exitAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(cutSelectedTextAction));
		toolBar.add(new JButton(copySelectedTextAction));
		toolBar.add(new JButton(pasteSelectedTextAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(closeTabAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(statisticalInfoOfDocumentAction));
		
		this.getContentPane().add(new JScrollPane(toolBar), BorderLayout.PAGE_START);
	}

	/**
	 * Creates menus for this {@link JNotepadPPWindow}.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new LocalizableJMenu(flp, "file");
		menuBar.add(fileMenu);
		
		JMenu editMenu = new LocalizableJMenu(flp, "edit");
		menuBar.add(editMenu);
		
		JMenu infoMenu = new LocalizableJMenu(flp, "info");
		menuBar.add(infoMenu);
		
		JMenu tabMenu = new LocalizableJMenu(flp, "tab");
		menuBar.add(tabMenu);
		
		JMenu languageMenu = new LocalizableJMenu(flp, "language");
		menuBar.add(languageMenu);
		
		JMenu toolsMenu = new LocalizableJMenu(flp, "tools");
		menuBar.add(toolsMenu);
		
		JMenu changeCase = new LocalizableJMenu(flp, "changeCase");
		toolsMenu.add(changeCase);
		
		JMenu sort = new LocalizableJMenu(flp, "sort");
		toolsMenu.add(sort);
		
		//Saving it to variable, and initially setting it to be disabled.
		this.sort = sort;
		sort.setEnabled(false);
		
		//Also saving it to variable, and initially setting it to be disabled.
		this.changeCase = changeCase;
		changeCase.setEnabled(false);
		
		sort.add(new JMenuItem(sortAscendingAction));
		sort.add(new JMenuItem(sortDescendingAction));

		
		changeCase.add(new JMenuItem(toUpperCaseAction));
		changeCase.add(new JMenuItem(toLowerCaseAction));
		changeCase.add(new JMenuItem(invertCaseAction));
		
		//I will not add these language change buttons onto toolBar because I think it is not appropriate for them to be there.
		JMenuItem en = new JMenuItem(englishLanguageAction);
		JMenuItem hr = new JMenuItem(croatianLanguageAction);
		JMenuItem de = new JMenuItem(germanLanguageAction);
		
		languageMenu.add(de);
		languageMenu.add(en);
		languageMenu.add(hr);
		
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(saveDocumentAction);
		fileMenu.add(saveAsDocumentAction);
		fileMenu.addSeparator();
		fileMenu.add(exitAction);
		
		editMenu.add(new JMenuItem(cutSelectedTextAction));
		editMenu.add(new JMenuItem(copySelectedTextAction));
		editMenu.addSeparator();
		editMenu.add(new JMenuItem(pasteSelectedTextAction));

		infoMenu.add(new JMenuItem(statisticalInfoOfDocumentAction));
		
		tabMenu.add(new JMenuItem(closeTabAction));
		
		this.setJMenuBar(menuBar);		
	}

	/**
	 * Creates actions by delegating work to {@link #createAction(Action, String, String, int, String)} method.
	 * For more see {@link #createAction(Action, String, String, int, String)}.
	 */
	private void createActions() {
	
		createAction(openDocumentAction, "control O", KeyEvent.VK_O);
		createAction(newDocumentAction, "control N", KeyEvent.VK_N);
		createAction(saveDocumentAction, "control S", KeyEvent.VK_S);
		createAction(saveAsDocumentAction, "control A", KeyEvent.VK_A);
		createAction(cutSelectedTextAction, "control X", KeyEvent.VK_X);
		createAction(copySelectedTextAction, "control C", KeyEvent.VK_C);
		createAction(pasteSelectedTextAction, "control V", KeyEvent.VK_V);
		createAction(closeTabAction, "control D", KeyEvent.VK_D);
		createAction(statisticalInfoOfDocumentAction, "control I", KeyEvent.VK_I);
		createAction(exitAction, "control E", KeyEvent.VK_E);
	}
	
	/**
	 * Creates action provided as argument action
	 * in a way that it puts mnemonic_key and accelerator_key.
	 * @param action Action which is being "created".
	 * @param name Action name.
	 * @param keyStroke String representation of accelerator_key for provided action.
	 * @param keyEvent Mnemonic_key for provided action.
	 */
	private void createAction(Action action, String keyStroke, int keyEvent){
		
		action.putValue(Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke(keyStroke));
		action.putValue(Action.MNEMONIC_KEY, 
				keyEvent);
	}
	
}
