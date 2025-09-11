import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.event.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;



public class NotepadGUI extends JFrame {

    //file explorer
    private JFileChooser fileChooser;

    private JTextArea textArea;
    private File currentFile;

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
        menuBar.add(addEditMenu());
        menuBar.add(addHelpMenu());
        add(toolBar, BorderLayout.NORTH);
    }
    private  JMenu addFileMenu(){
        JMenu fileMenu = new JMenu("File");

        // "new" functionality - reset everything
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //reset title header
                setTitle("Notepad");

                //reset text area
                textArea.setText("");

                //reset current file
                currentFile = null;
            }
        });
        fileMenu.add(newMenuItem);

        //"Open" functionality - open a text file
        // "open" functionality - open a text file
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // open file explorer
                int result = fileChooser.showOpenDialog(NotepadGUI.this);

                if(result != JFileChooser.APPROVE_OPTION) return;
                try{
                    // reset notepad
                    newMenuItem.doClick();

                    // get the selected file
                    File selectedFile = fileChooser.getSelectedFile();

                    // update current file
                    currentFile = selectedFile;

                    // update title header
                    setTitle(selectedFile.getName());

                    // read the file
                    FileReader fileReader = new FileReader(selectedFile);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    // store the text
                    StringBuilder fileText = new StringBuilder();
                    String readText;
                    while((readText = bufferedReader.readLine()) != null){
                        fileText.append(readText + "\n");
                    }

                    // update text area gui
                    textArea.setText(fileText.toString());

                }catch(Exception e1){

                }
            }
        });
        fileMenu.add(openMenuItem);


        //"save as" functionality - creates a new text file and saves user text
        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        saveAsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //open save dialog
                int result = fileChooser.showSaveDialog(NotepadGUI.this);

                //Continoue to  execute code only if the user pressed the save button
                if(result != JFileChooser.APPROVE_OPTION) return;
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

                    //Update Current File
                    currentFile = selectedFile;

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
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //if current file is null then we have to perform save as functionality
                if(currentFile == null) saveAsMenuItem.doClick();

                //if the user choose to cancel saving the file current file still be null, prevents executing the rest of the code
                if(currentFile == null) return;

                try{
                    //write to current number
                    FileWriter fileWriter = new FileWriter(currentFile);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(textArea.getText());
                    bufferedWriter.close();
                    fileWriter.close();

                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });

        fileMenu.add(saveMenuItem);

        //"exit" functinality - ends program process
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //Exit the Notepad
                NotepadGUI.this.dispose();
            }
        });
        fileMenu.add(exitMenuItem);

        return  fileMenu;
    }
    private JMenu addEditMenu(){
        JMenu editMenu = new JMenu("Edit");

        //"cut" functionality - cuts selected text to clipboard
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        cutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get selected text
                String selectedText = textArea.getSelectedText();

                if(selectedText != null){
                    //copy to clipboard
                    StringSelection stringSelection = new StringSelection(selectedText);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);

                    //remove selected text from text area
                    textArea.replaceSelection("");
                }
            }
        });
        editMenu.add(cutMenuItem);

        //"copy" functionality - copies selected text to clipboard
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        copyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get selected text
                String selectedText = textArea.getSelectedText();

                if(selectedText != null){
                    //copy to clipboard
                    StringSelection stringSelection = new StringSelection(selectedText);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                }
            }
        });
        editMenu.add(copyMenuItem);

        //"paste" functionality - pastes text from clipboard
        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        pasteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    //get clipboard contents
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    Transferable contents = clipboard.getContents(null);

                    if(contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)){
                        String clipboardText = (String) contents.getTransferData(DataFlavor.stringFlavor);

                        //paste text at cursor position
                        textArea.replaceSelection(clipboardText);
                    }
                }catch(Exception e1){
                    e1.printStackTrace();
                }
            }
        });
        editMenu.add(pasteMenuItem);

        return editMenu;
    }

    private JMenu addHelpMenu(){
        JMenu helpMenu = new JMenu("Help");

        //"about" functionality - shows information about the application
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //create about dialog message
                String aboutMessage = "Simple Text Editor\n\n" +
                        "Created by: Sukitha Rathnayake\n" +
                        "Student ID: 2022s19501@stu.cmb.ac.lk";

                //show about dialog
                JOptionPane.showMessageDialog(NotepadGUI.this, aboutMessage, "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(aboutMenuItem);

        return helpMenu;
    }
}
