package Afast.pkg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class Licenta{

    public String getIdTipLic() {
        return idTipLic;
    }

    public void setIdTipLic(String idTipLic) {
        this.idTipLic = idTipLic;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPret() {
        return pret;
    }

    public void setPret(String pret) {
        this.pret = pret;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    private String idTipLic;
        private String nume;
        private String pret;
        private String moneda;
        private String durata;

    public Licenta(String idTipLic, String nume, String pret, String moneda, String durata) {
        this.idTipLic = idTipLic;
        this.nume = nume;
        this.pret = pret;
        this.moneda = moneda;
        this.durata = durata;
    }
}


public class Licente {

    private JButton pretCrescatorButton;
    private JButton pretDescrescatorButton;
    private JTextArea textArea1;
    public JPanel panel1;


    public ArrayList<Licenta> licListDesc() throws SQLException {

        ArrayList<Licenta> licList = new ArrayList<Licenta>();
        Connection dbConn = DBConnection.connectDB();

        textArea1.setText("TIP_LICENTA\tNUME\tPRET\tMONEDA\tDURATA");

        try {
            String id_utiliz = "";
            String id_calculator = "";

            PreparedStatement st = dbConn.prepareStatement("SELECT id_tip_licenta, nume, pret, moneda, durata from tip_licenta order by pret desc");
            ResultSet resultSet = st.executeQuery();

            Licenta lic;
            while (resultSet.next()) {
                lic = new Licenta(resultSet.getString("id_tip_licenta"), resultSet.getString("nume"), resultSet.getString("pret"), resultSet.getString("moneda"), resultSet.getString("durata"));
                licList.add(lic);
            }


        } catch (SQLException ex){

        } finally {
            if(dbConn != null)
                dbConn.close();
        }
        return licList;
    }

    public ArrayList<Licenta> licListAsc() throws SQLException {

        ArrayList<Licenta> licList = new ArrayList<Licenta>();
        Connection dbConn = DBConnection.connectDB();

        textArea1.setText("TIP_LICENTA\tNUME\tPRET\tMONEDA\tDURATA");

        try {
            String id_utiliz = "";
            String id_calculator = "";

            PreparedStatement st = dbConn.prepareStatement("SELECT id_tip_licenta, nume, pret, moneda, durata from tip_licenta order by pret asc");
            ResultSet resultSet = st.executeQuery();

            Licenta lic;
            while (resultSet.next()) {
                lic = new Licenta(resultSet.getString("id_tip_licenta"), resultSet.getString("nume"), resultSet.getString("pret"), resultSet.getString("moneda"), resultSet.getString("durata"));
                licList.add(lic);
            }


        } catch (SQLException ex){

        } finally {
            if(dbConn != null)
                dbConn.close();
        }
        return licList;
    }

    public Licente() {
        pretCrescatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection dbConn = DBConnection.connectDB();


                try {

                    ArrayList<Licenta> licz = licListAsc();


                    for(int i = 0 ; i < licz.size(); ++i) {



                        String rez = licz.get(i).getIdTipLic() + "\t" + licz.get(i).getNume() + "\t" + licz.get(i).getPret() + "\t" + licz.get(i).getMoneda() + "\t" + licz.get(i).getDurata();

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
        pretDescrescatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection dbConn = DBConnection.connectDB();


                try {

                    ArrayList<Licenta> licz = licListDesc();


                    for(int i = 0 ; i < licz.size(); ++i) {



                        String rez = licz.get(i).getIdTipLic() + "\t" + licz.get(i).getNume() + "\t" + licz.get(i).getPret() + "\t" + licz.get(i).getMoneda() + "\t" + licz.get(i).getDurata();

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
