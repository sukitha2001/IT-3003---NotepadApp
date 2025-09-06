import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
                }catch (Exception e){
                    e.printStackTrace();
                }
                new NotepadGUI().setVisible(true);
            }
        });
    }
}
