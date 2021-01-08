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


class VirCalcUsr{

    public String getIdUtiliz() {
        return idUtiliz;
    }

    public void setIdUtiliz(String idUtiliz) {
        this.idUtiliz = idUtiliz;
    }

    public String getIdCalc() {
        return idCalc;
    }

    public void setIdCalc(String idCalc) {
        this.idCalc = idCalc;
    }

    public String getIdTipVir() {
        return idTipVir;
    }

    public void setIdTipVir(String idTipVir) {
        this.idTipVir = idTipVir;
    }

    private String idUtiliz;
    private String idCalc;
    private String idTipVir;

    public VirCalcUsr(String idUtiliz, String idCalc, String idTipVir){

        this.idUtiliz = idUtiliz;
        this.idCalc = idCalc;
        this.idTipVir = idTipVir;
    }


}

public class Virusi {
    private JButton sortareGravitateButton;
    private JButton sortareDataButton;
    private JTextArea textArea1;
    public JPanel panel1;

    public ArrayList<VirCalcUsr> usrCalcVirDesc(String email) throws SQLException {

        ArrayList<VirCalcUsr> list = new ArrayList<VirCalcUsr>();
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


            st = dbConn.prepareStatement("select usr.ID_UTILIZATOR, calc.ID_CALCULATOR, vir.id_tip_virus\n" +
                    "\tfrom utilizator usr\n" +
                    "    join calculatoare calc on usr.ID_UTILIZATOR = calc.ID_UTILIZATOR\n" +
                    "    join virusi vir on vir.ID_CALCULATOR = calc.ID_CALCULATOR order by ID_TIP_VIRUS desc;");

            resultSet = st.executeQuery();

            VirCalcUsr virCalcUsr;
            while (resultSet.next()) {
                virCalcUsr = new VirCalcUsr(resultSet.getString("id_utilizator"), resultSet.getString("id_calculator"), resultSet.getString("id_tip_virus"));
                list.add(virCalcUsr);
            }

        } catch (SQLException ex){

        } finally {
            if(dbConn != null)
                dbConn.close();
        }
        return list;
    }

    public ArrayList<VirCalcUsr> usrCalcVirAsc(String email) throws SQLException {

        ArrayList<VirCalcUsr> list = new ArrayList<VirCalcUsr>();
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


            st = dbConn.prepareStatement(" select usr.ID_UTILIZATOR, calc.ID_CALCULATOR, vir.id_tip_virus\n" +
                    "\t\tfrom utilizator usr \n" +
                    "\t\tjoin calculatoare calc on usr.ID_UTILIZATOR = calc.ID_UTILIZATOR\n" +
                    "\t\tjoin virusi vir on vir.ID_CALCULATOR = calc.ID_CALCULATOR order by vir.ID_TIP_VIRUS asc;");

            resultSet = st.executeQuery();

            VirCalcUsr virCalcUsr;
            while (resultSet.next()) {
                virCalcUsr = new VirCalcUsr(resultSet.getString("id_utilizator"), resultSet.getString("id_calculator"), resultSet.getString("id_tip_virus"));
                list.add(virCalcUsr);
            }

        } catch (SQLException ex){

        } finally {
            if(dbConn != null)
                dbConn.close();
        }
        return list;
    }

    public Virusi(String email) {
        sortareGravitateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection dbConn = DBConnection.connectDB();

                String id_utiliz = "";
                String id_calculator = "";
                textArea1.setText("ID_VIRUS\tID_CALCULATOR\tTIP_VIRUS");
                try {

                    ArrayList<VirCalcUsr> vcu = usrCalcVirDesc(email);

                    Object[] row = new Object[4];
                    for (int i = 0; i < vcu.size(); ++i) {
                        String rez = vcu.get(i).getIdUtiliz() + "\t" + vcu.get(i).getIdCalc() + "\t" + vcu.get(i).getIdTipVir();

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

        sortareDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection dbConn = DBConnection.connectDB();

                String id_utiliz = "";
                String id_calculator = "";

                textArea1.setText("ID_VIRUS\tID_CALCULATOR\tTIP_VIRUS");
                try {

                    ArrayList<VirCalcUsr> vcu = usrCalcVirAsc(email);

                    Object[] row = new Object[4];
                    for (int i = 0; i < vcu.size(); ++i) {
                        String rez = vcu.get(i).getIdUtiliz() + "\t" + vcu.get(i).getIdCalc() + "\t" + vcu.get(i).getIdTipVir();

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
