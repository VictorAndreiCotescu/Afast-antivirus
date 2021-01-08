package Afast.pkg;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Login extends JFrame {

    DBConnection connection;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JPanel Panel1;
    private JButton createAccountButton;

    private String email;
    private String passwd;


    public Login() {

        connection = new DBConnection();

        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Eroare la conectare!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }


        loginButton.addActionListener(e -> {
            String email = textField1.getText();
            char[] passwd = passwordField1.getPassword();
            String pass = new String(passwd);

            userLogin(email, pass);


        });
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                ShowCreateAcc(email, passwd);

            }
        });

    }


    private void userLogin(String email, String passwd) {


        Connection dbConn = DBConnection.connectDB();
        try {
            PreparedStatement st = dbConn.prepareStatement("SELECT * from utilizator where EMAIL = ? and PAROLA = ?");
            st.setString(1, email);
            st.setString(2, passwd);
            ResultSet resultSet = st.executeQuery();

            if (resultSet.next()) {

                Login.this.setVisible(false);
                
                this.dispose();
                Panel1.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                CloseForm(login);
                ShowMeniu(email, passwd);
                st.close();

            } else {
                JOptionPane.showMessageDialog(null, "Email sau parola incorecte!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                shakeButton();
                st.close();

            }

        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            if(dbConn != null) {
                try {
                    dbConn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    static JFrame login = new JFrame("Afast - Login");
    public static void ShowLogIn() {


        login.setTitle("Afast - Login");
        ImageIcon img = new ImageIcon("D:/Downloads/31.3.png");
        login.setIconImage(img.getImage());
        login.setContentPane(new Login().Panel1);
        login.setLocation(500, 500);
        login.setSize(500, 300);
        login.setVisible(true);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    public static void ShowCreateAcc(String email, String passwd) {

        JFrame createAcc = new JFrame("Afast - Create Account");

        ImageIcon img = new ImageIcon("D:/Downloads/31.3.png");
        createAcc.setIconImage(img.getImage());
        createAcc.setContentPane(new CreateAcc(email, passwd).panel1);
        createAcc.setLocation(500, 500);
        createAcc.setSize(500, 300);
        createAcc.setVisible(true);
        createAcc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    }

    public static void CloseForm(JFrame form){

        form.setVisible(false);

    }


    public static void ShowMeniu(String email, String passwd) {

        JFrame meniu = new JFrame("Afast");

        ImageIcon img = new ImageIcon("D:/Downloads/31.3.png");
        meniu.setIconImage(img.getImage());
        meniu.setContentPane(new Meniu(email, passwd).panel1);
        meniu.setLocation(500, 500);
        meniu.setSize(500, 300);
        meniu.setVisible(true);
        meniu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }


    public static void main(String[] args) {

    }

    private void moveButton(final Point p) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                loginButton.setLocation(p);
            }
        });
    }

    private void shakeButton() {
        final Point point = loginButton.getLocation();
        final int delay = 25;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {

                        moveButton(new Point(point.x + 5, point.y));
                        Thread.sleep(delay);
                        moveButton(point);
                        Thread.sleep(delay);
                        moveButton(new Point(point.x - 5, point.y));
                        Thread.sleep(delay);
                        moveButton(point);
                        Thread.sleep(delay);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
}
