import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;




public class Calculatoare {
    private JTextArea textArea1;
    public JPanel panel1;
    private JButton button1;


    public Calculatoare(String email) {


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



            }
        });
        panel1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });
    }
}
