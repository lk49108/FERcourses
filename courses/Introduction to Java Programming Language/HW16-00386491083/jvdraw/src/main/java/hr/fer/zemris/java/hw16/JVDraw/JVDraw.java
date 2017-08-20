package hr.fer.zemris.java.hw16.JVDraw;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw16.color.CJLabel;
import hr.fer.zemris.java.hw16.color.JColorArea;
import hr.fer.zemris.java.hw16.components.GeometricalObject;
import hr.fer.zemris.java.hw16.drawing.Drawer;
import hr.fer.zemris.java.hw16.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw16.models.DrawingModel;
import hr.fer.zemris.java.hw16.models.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.models.ImplementationModel;
import hr.fer.zemris.java.hw16.util.FileHandler;

/**
 * Program starts its execution from main method in this class.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class JVDraw extends JFrame {

	
    
    /**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Extension used for saving files by this program.
     */
    private static final String EXTENSION = ".jvd";

    /**
     * {@link JColorArea} used for containing foreground color used by user in program.
     */
    private JColorArea foregnd;
    
    /**
     * {@link JColorArea} used for containing background color used by user in program.
     */
    private JColorArea backgnd;
    
    /**
     * {@link JDrawingCanvas} instance, canvas used in this program.
     */
    private JDrawingCanvas canvas;
    
    /**
     * Drawer used in this program.
     */
    private Drawer drawer;
    
    /**
     * {@link DrawingModel} instance.
     */
    private ImplementationModel model = new ImplementationModel();
    
    /**
     * {@link Path} instance.
     */
    private Path path;

    /**
     * Creates a new {@link JVDraw} object. Initializes the gui.
     */
    public JVDraw() {
        setLayout(new BorderLayout());

        setLocation(100, 100);
        setSize(1000, 700);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitAction.actionPerformed(null);
            }
        });
        createColorAreas();
        
        createCanvas();

        createMenu();

        createList();
       
        createToolbar();
    }

    /**
     * Creates two {@link JColorArea} used for color switching.
     */
    private void createColorAreas() {
        foregnd = new JColorArea(Color.BLUE, true);
        backgnd = new JColorArea(Color.GREEN, false);
    }

    /**
     * Creates an empty canvas used for drawing.
     */
    private void createCanvas() {
        canvas = new JDrawingCanvas(model);
        drawer = new Drawer(canvas, model, foregnd, backgnd);

        add(canvas, BorderLayout.CENTER);
    }

   

    /**
     * Creates menu for this program.
     */
    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();

        initializeActions();

        JMenu file = new JMenu("File");
        file.add(new JMenuItem(openFileAction));
        file.add(new JMenuItem(saveAsFileAction));
        file.add(new JMenuItem(saveFileAction));
        file.addSeparator();
        file.add(new JMenuItem(exportFileAction));
        file.add(new JMenuItem(exitAction));
        menuBar.add(file);

        setJMenuBar(menuBar);
    }

    /**
     * Used to initialize actions.
     */
    private void initializeActions() {
        openFileAction.putValue(Action.NAME, "Open");
        openFileAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        openFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
        openFileAction.putValue(Action.SHORT_DESCRIPTION, "Opens a new file");

        saveFileAction.putValue(Action.NAME, "Save");
        saveFileAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        saveFileAction.putValue(Action.SHORT_DESCRIPTION, "Saves the current file");

        saveAsFileAction.putValue(Action.NAME, "Save as");
        saveAsFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        saveAsFileAction.putValue(Action.SHORT_DESCRIPTION, "Saves the current file to given path");

        exportFileAction.putValue(Action.NAME, "Export");
        exportFileAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
        exportFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
        exportFileAction.putValue(Action.SHORT_DESCRIPTION, "Exports the created file as image");

        exitAction.putValue(Action.NAME, "Exit");
        exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F4"));
        exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
        exitAction.putValue(Action.SHORT_DESCRIPTION, "Closes the program");

        clearAll.putValue(Action.NAME, "Clear");
    }

    @SuppressWarnings("serial")
    private Action openFileAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            path = fc.getSelectedFile().toPath();
            if (!path.getFileName().toString().endsWith(EXTENSION)) {
                JOptionPane.showMessageDialog(null, "File must have *" + EXTENSION + " extension", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            model.clear();
            FileHandler.addObjectsToModelFromFile(model, path);
        }
    };

    @SuppressWarnings("serial")
    private Action saveFileAction = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (path == null) {
                saveAsFileAction.actionPerformed(e);
            }
            if (path == null) {
                return;
            }
            FileHandler.saveModelToFile(model, path);
            drawer.saveWasMade();
        }
    };

    @SuppressWarnings("serial")
    private Action saveAsFileAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            path = addExtension(fc.getSelectedFile().toPath());
            saveFileAction.actionPerformed(e);
        }

        private Path addExtension(Path path) {
            return Paths.get(path.toString() + EXTENSION);
        }
    };

    @SuppressWarnings("serial")
    private Action exportFileAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            FileHandler.export(model);
        }
    };

    @SuppressWarnings("serial")
    private Action exitAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (drawer.hasChanged()) {
                int answer = JOptionPane.showConfirmDialog(JVDraw.this, "Drawing is unsaved, do you want to save it?", "Save",
                        JOptionPane.YES_NO_OPTION);

                if (answer == JOptionPane.YES_OPTION) {
                    saveFileAction.actionPerformed(e);
                }
            }
            JVDraw.this.dispose();
        }
    };

    @SuppressWarnings("serial")
    private Action clearAll = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.clear();
        }
      
    };

    /**
     * Creates a {@link JList} used for managing objects.
     */
    private void createList() {
        final JList<GeometricalObject> list = new JList<>(new DrawingObjectListModel(model));

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 2) {
                    return;
                }
                int index = list.getSelectedIndex();
                if (index == -1) {
                    return;
                }

                model.getObject(index).onClick(JVDraw.this.canvas, JVDraw.this.canvas);
            }
        });

        JScrollPane pane = new JScrollPane(list);
        add(pane, BorderLayout.LINE_END);
    }

    /**
     * Creates the toolbar for this program.
     */
    private void createToolbar() {
        JToolBar toolbar = new JToolBar("Tools");

        JToggleButton line = new JToggleButton("Line");
        line.setSelected(true);
        JToggleButton circle = new JToggleButton("Circle");
        JToggleButton fcircle = new JToggleButton("FCircle");

        line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawer.setStatus(Drawer.LINE);
            }
        });

        circle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawer.setStatus(Drawer.CIRCLE);
            }
        });

        fcircle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawer.setStatus(Drawer.FCIRCLE);
            }
        });

        ButtonGroup group = new ButtonGroup();
        group.add(line);
        group.add(circle);
        group.add(fcircle);

        toolbar.add(foregnd);
        toolbar.add(backgnd);
        toolbar.addSeparator();
        toolbar.add(line);
        toolbar.add(circle);
        toolbar.add(fcircle);
        toolbar.addSeparator();
        toolbar.add(new JButton(clearAll));
        toolbar.addSeparator();
        toolbar.add(new CJLabel(foregnd, backgnd));

        add(toolbar, BorderLayout.PAGE_START);
    }
    
    /**
     * Starts program.
     * @param args Not used here.
     */
    public static void main(String[] args) {
        
    	SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new JVDraw().setVisible(true);
            }
        });
    }

}