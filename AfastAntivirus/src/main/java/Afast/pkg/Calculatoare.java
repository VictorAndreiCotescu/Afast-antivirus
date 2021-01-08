package Afast.pkg;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class Calc {

    public String getID_CALCULATOR() {
        return ID_CALCULATOR;
    }

    public void setID_CALCULATOR(String ID_CALCULATOR) {
        this.ID_CALCULATOR = ID_CALCULATOR;
    }

    public String getID_UTILIZATOR() {
        return ID_UTILIZATOR;
    }

    public void setID_UTILIZATOR(String ID_UTILIZATOR) {
        this.ID_UTILIZATOR = ID_UTILIZATOR;
    }

    public String getSISTEM_OPERARE() {
        return SISTEM_OPERARE;
    }

    public void setSISTEM_OPERARE(String SISTEM_OPERARE) {
        this.SISTEM_OPERARE = SISTEM_OPERARE;
    }

    public String getSTATUS_INFECTARE() {
        return STATUS_INFECTARE;
    }

    public void setSTATUS_INFECTARE(String STATUS_INFECTARE) {
        this.STATUS_INFECTARE = STATUS_INFECTARE;
    }

    public String getID_LICENTA() {
        return ID_LICENTA;
    }

    public void setID_LICENTA(String ID_LICENTA) {
        this.ID_LICENTA = ID_LICENTA;
    }

    public Calc(String ID_CALCULATOR, String ID_UTILIZATOR, String SISTEM_OPERARE, String STATUS_INFECTARE, String ID_LICENTA) {
        this.ID_CALCULATOR = ID_CALCULATOR;
        this.ID_UTILIZATOR = ID_UTILIZATOR;
        this.SISTEM_OPERARE = SISTEM_OPERARE;
        this.STATUS_INFECTARE = STATUS_INFECTARE;
        this.ID_LICENTA = ID_LICENTA;
    }

    String ID_CALCULATOR;
    String ID_UTILIZATOR;
    String SISTEM_OPERARE;
    String STATUS_INFECTARE;
    String ID_LICENTA;
}

public class Calculatoare {
    private JTextArea textArea1;
    public JPanel panel1;

    public ArrayList<Calc> usrCalcVirDesc(String email) throws SQLException {

        ArrayList<Calc> list = new ArrayList<Calc>();
        Connection dbConn = DBConnection.connectDB();

        try {
            String id_utiliz = "";
            String id_calculator = "";

            PreparedStatement st = dbConn.prepareStatement("SELECT id_utilizator from utilizator where email = ? ");
            st.setString(1, email);
            ResultSet resultSet = st.executeQuery();

            while (resultSet.next()) {
                id_utiliz = resultSet.getString("id_utilizator");

            }

            st = dbConn.prepareStatement("SELECT id_calculator from calculatoare where id_utilizator = ?");
            st.setString(1, id_utiliz);
            resultSet = st.executeQuery();

            while (resultSet.next()) {
                id_calculator = resultSet.getString("id_calculator");

            }


            st = dbConn.prepareStatement("select id_calculator, id_utilizator, sistem_operare, status_infectare, id_licenta from calculatoare group by id_utilizator, sistem_operare having sistem_operare = 'Microsoft';");

            resultSet = st.executeQuery();

            Calc calc;
            while (resultSet.next()) {
                calc = new Calc( resultSet.getString("id_calculator"), resultSet.getString("id_utilizator"), resultSet.getString("sistem_operare"), resultSet.getString("status_infectare"), resultSet.getString("id_licenta"));
                list.add(calc);
            }

        } catch (SQLException ex){

        } finally {
            if(dbConn != null)
                dbConn.close();
        }
        return list;
    }

    public Calculatoare(String email) {
        panel1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                Connection dbConn = DBConnection.connectDB();

                String id_utiliz = "";
                String id_calculator = "";
                textArea1.setText("ID_CALCULATOR\tID_UTILIZATOR\tSISTEM_OPERARE\tINFECTAT\tLICENTA");
                try {

                    ArrayList<Calc> vcu = usrCalcVirDesc(email);

                    Object[] row = new Object[4];
                    for (int i = 0; i < vcu.size(); ++i) {
                        String rez = vcu.get(i).getID_CALCULATOR() + "\t" + vcu.get(i).getID_UTILIZATOR()  + "\t" + vcu.get(i).getSISTEM_OPERARE() + '\t' + vcu.get(i).getSTATUS_INFECTARE() + "\n" + vcu.get(i).getID_LICENTA();

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
