package Afast.pkg;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


class User{

    private String idUtiliz;
    private String nume;
    private String prenume;
    private String email;


    public String getIdUtiliz() {
        return idUtiliz;
    }

    public void setIdUtiliz(String idUtiliz) {
        this.idUtiliz = idUtiliz;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public User(String idUtiliz, String nume, String prenume, String email) {

        this.idUtiliz = idUtiliz;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
    }

}

public class Utilizatori {
    private JButton ordonareCrescAlfabeticaButton;
    private JButton ordonareDescAlfabeticaButton;
    private JTextArea textArea1;
    public JPanel panel1;

    public ArrayList<User> usrListDesc() throws SQLException {

        ArrayList<User> usrList = new ArrayList<User>();
        Connection dbConn = DBConnection.connectDB();

        try {
            String id_utiliz = "";
            String id_calculator = "";

            PreparedStatement st = dbConn.prepareStatement("SELECT id_utilizator, nume, prenume, email from utilizator order by nume desc");
            ResultSet resultSet = st.executeQuery();

            User usr;
            while (resultSet.next()) {
                usr = new User(resultSet.getString("id_utilizator"), resultSet.getString("nume"), resultSet.getString("prenume"), resultSet.getString("email"));
                usrList.add(usr);
            }


        } catch (SQLException ex){

        } finally {
            if(dbConn != null)
                dbConn.close();
        }
        return usrList;
    }

    public ArrayList<User> usrListAsc() throws SQLException {

        ArrayList<User> usrList = new ArrayList<User>();
        Connection dbConn = DBConnection.connectDB();


        try {
            String id_utiliz = "";
            String id_calculator = "";

            PreparedStatement st = dbConn.prepareStatement("SELECT id_utilizator, nume, prenume, email from utilizator order by nume asc");
            ResultSet resultSet = st.executeQuery();

            User usr;
            while (resultSet.next()) {
                usr = new User(resultSet.getString("id_utilizator"), resultSet.getString("nume"), resultSet.getString("prenume"), resultSet.getString("email"));
                usrList.add(usr);
            }


        } catch (SQLException ex){

        } finally {
            if(dbConn != null)
                dbConn.close();
        }
        return usrList;
    }


    public Utilizatori() {
        ordonareCrescAlfabeticaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection dbConn = DBConnection.connectDB();
                textArea1.setText("ID_UTILIZATOR\tNUME\tPRENUME\tEMAIL");
                textArea1.setText(textArea1.getText() + "\n---------------------------------------------------------------------------------");

                try {

                    ArrayList<User> usrz = usrListAsc();


                    for(int i = 0 ; i < usrz.size(); ++i) {


                        String rez = usrz.get(i).getIdUtiliz() + "\t" + usrz.get(i).getNume() + "\t" + usrz.get(i).getPrenume() + "\t" + usrz.get(i).getEmail();

                        textArea1.setText(textArea1.getText() + '\n' + rez + '\n');
                    }


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    if(dbConn != null) {
                        try {
                            dbConn.close();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }

            }
        });

        ordonareDescAlfabeticaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection dbConn = DBConnection.connectDB();
                textArea1.setText("ID_UTILIZATOR\tNUME\tPRENUME\tEMAIL");
                textArea1.setText(textArea1.getText() + "\n---------------------------------------------------------------------------------");

                try {

                    ArrayList<User> usrz = usrListDesc();


                    for(int i = 0 ; i < usrz.size(); ++i) {


                        String rez = usrz.get(i).getIdUtiliz() + "\t" + usrz.get(i).getNume() + "\t" + usrz.get(i).getPrenume() + "\t" + usrz.get(i).getEmail();

                        textArea1.setText(textArea1.getText() + '\n' + rez + '\n');
                    }


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    if(dbConn != null) {
                        try {
                            dbConn.close();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }

            }
        });
    }
}
