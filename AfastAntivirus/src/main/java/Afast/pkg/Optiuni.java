package Afast.pkg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Optiuni {
    public JPanel panel1;
    private JButton administrareContButton;
    private JButton licenteButton;
    private JButton stergereContButton;

    public Optiuni(String email) {
        administrareContButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowAdministrareCont(email);
            }
        });

        stergereContButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection dbConn = DBConnection.connectDB();

                try{

                    PreparedStatement st = dbConn.prepareStatement("DELETE FROM utilizator where email = ? ");
                    st.setString(1, email);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Stergerea contului a fost efectuata cu succes!", "Succes!", JOptionPane.INFORMATION_MESSAGE);


                } catch(SQLException ex) {
                    System.out.println(ex);
                } finally{
                    try {
                        dbConn.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
        licenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame meniu = new JFrame("Afast");

                ImageIcon img = new ImageIcon("D:/Downloads/31.3.png");
                meniu.setIconImage(img.getImage());

                meniu.setContentPane(new Licente().panel1);
                meniu.setLocation(500, 500);
                meniu.setSize(500, 300);
                meniu.setVisible(true);
                // meniu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            }
        });
    }

    public static void ShowAdministrareCont(String email) {

        JFrame meniu = new JFrame("Afast");

        ImageIcon img = new ImageIcon("D:/Downloads/31.3.png");
        meniu.setIconImage(img.getImage());

        meniu.setContentPane(new AdministrareCont(email).panel1);
        meniu.setLocation(500, 500);
        meniu.setSize(500, 300);
        meniu.setVisible(true);
        // meniu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
