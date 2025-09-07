import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.*;


public class NotepadGUI extends JFrame {

    //file explorer
    private JFileChooser fileChooser;


    public  NotepadGUI() {
        super("Notepad");
        setSize(400,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //file chooser setup
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("src/assets"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));


        addGuiComponents();
    }
    public void addGuiComponents(){
        addToolbar();

        //area to type text into
        JTextArea textArea = new JTextArea();
        add(textArea,BorderLayout.CENTER);
    }
    public void addToolbar(){


        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        //Menu bar
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);

        //Add Menus
        menuBar.add(addFileMenu());
        add(toolBar, BorderLayout.NORTH);
    }
    private  JMenu addFileMenu(){
        JMenu fileMenu = new JMenu("File");

        // "new" functionality - reset everything
        JMenuItem newMenuItem = new JMenuItem("New");
        fileMenu.add(newMenuItem);

        //"Open" functionality - open a text file
        JMenuItem openMenuItem = new JMenuItem("Open");
        fileMenu.add(openMenuItem);

        //"save as" functionality - creates a new text file and saves user text
        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        saveAsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //open save dialog
                fileChooser.showSaveDialog(NotepadGUI.this);
            }
        });
        fileMenu.add(saveAsMenuItem);


        //"save" functionality - save text in to current text file
        JMenuItem saveMenuItem = new JMenuItem("Save");
        fileMenu.add(saveMenuItem);

        //"exit" functinality - ends program process
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);

        return  fileMenu;
    }
}
