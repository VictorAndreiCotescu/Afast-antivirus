package Afast.pkg;

import javax.naming.spi.ResolveResult;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministrareCont {
    public JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton actualizareNume;
    private JButton actualizarePrenume;
    private JButton actualizareEmail;
    private JButton actualizarePass;

    public AdministrareCont(String email) {

        Connection dbConn = DBConnection.connectDB();

        try{

            PreparedStatement st = dbConn.prepareStatement("SELECT nume, prenume from utilizator where email = ? ");
            st.setString(1, email);
            ResultSet resultSet = st.executeQuery();

            while(resultSet.next()){
                textField1.setText(resultSet.getString(1));
                textField2.setText(resultSet.getString(2));
            }

        } catch(SQLException ex) {
            System.out.println(ex);
        }

        textField3.setText(email);


        actualizareNume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection dbConn = DBConnection.connectDB();

                try{

                    PreparedStatement st = dbConn.prepareStatement("UPDATE utilizator set nume = ? where email = ? ");
                    st.setString(1, textField1.getText());
                    st.setString(2, email);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Actualizare realizata cu succes!", "Super!", JOptionPane.INFORMATION_MESSAGE);

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

        actualizarePrenume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection dbConn = DBConnection.connectDB();

                try{

                    PreparedStatement st = dbConn.prepareStatement("UPDATE utilizator set prenume = ? where email = ? ");
                    st.setString(1, textField2.getText());
                    st.setString(2, email);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Actualizare realizata cu succes!", "Super!", JOptionPane.INFORMATION_MESSAGE);

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
        actualizareEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection dbConn = DBConnection.connectDB();

                try{

                    PreparedStatement st = dbConn.prepareStatement("UPDATE utilizator set email = ? where email = ? ");
                    st.setString(1, textField3.getText());
                    st.setString(2, email);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Actualizare realizata cu succes!", "Super!", JOptionPane.INFORMATION_MESSAGE);

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
        actualizarePass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        actualizarePass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection dbConn = DBConnection.connectDB();

                try{

                    PreparedStatement st = dbConn.prepareStatement("UPDATE utilizator set parola = ? where email = ? ");

                    char[] passwdOld = passwordField1.getPassword();
                    String passOld = new String(passwdOld);

                    char[] passwdNew = passwordField2.getPassword();
                    String passNew = new String(passwdNew);

                    st.setString(1, passNew);
                    st.setString(2, email);

                    PreparedStatement st2 = dbConn.prepareStatement("select parola from utilizator where email = ? ");
                    st2.setString(1, email);

                    ResultSet rs2 = st2.executeQuery();
                    rs2.next();
                    String ps = rs2.getString("parola");

                    if(passOld.equals(ps)) {
                        if (passNew.equals(passOld)) {
                            JOptionPane.showMessageDialog(null, "Parola noua nu poate fi aceeasi cu cea veche", "Oops..", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        } else {
                            st.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Actualizare realizata cu succes!", "Super!", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Parola veche nu coincide!", "Oops..", JOptionPane.INFORMATION_MESSAGE);
                    }


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
    }
}
