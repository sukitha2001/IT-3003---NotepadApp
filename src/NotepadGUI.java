import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.event.*;



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
                //dispose of this GUI
                NotepadGUI.this.dispose();
            }
        });
        fileMenu.add(exitMenuItem);

        return  fileMenu;
    }
}
