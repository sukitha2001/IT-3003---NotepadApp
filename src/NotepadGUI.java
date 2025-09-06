import javax.swing.*;
import java.awt.*;

public class NotepadGUI extends JFrame {
    public  NotepadGUI() {
        super("Notepad");
        setSize(400,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addGuiComponents();
    }
    public void addGuiComponents(){
        addToolbar();
    }
    public void addToolbar(){


        JToolBar toolBar = new JToolBar();

        //Menu bar
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);

        //Add Menus
        menuBar.add(addFileMenu());

        add(toolBar, BorderLayout.NORTH);
    }
    private  JMenu addFileMenu(){
        JMenu fileMenu = new JMenu("File");
        return  fileMenu;
    }
}
