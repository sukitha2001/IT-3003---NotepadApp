import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.awt.event.*;
import java.io.FileWriter;


public class NotepadGUI extends JFrame {

    //file explorer
    private JFileChooser fileChooser;

    private JTextArea textArea;

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
        textArea = new JTextArea();
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

                try{
                    File selectedFile = fileChooser.getSelectedFile();

                    // append .txt to the file if it does not have the txt extension yet

                    String fileName = selectedFile.getName();
                    if(!fileName.substring(fileName.length() - 4).equalsIgnoreCase(".txt")){
                        selectedFile = new File(selectedFile.getAbsoluteFile() + ".txt");
                    }

                    //create new file
                    selectedFile.createNewFile();

                    FileWriter fileWriter = new FileWriter(selectedFile);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(textArea.getText());
                    bufferedWriter.close();
                    fileWriter.close();

                    //update the title header of gui to the save text file
                    setTitle(fileName);

                    //Show display dialog
                    JOptionPane.showMessageDialog(NotepadGUI.this,"Saved File!");

                }catch (Exception e1){
                    e1.printStackTrace();
                }

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
