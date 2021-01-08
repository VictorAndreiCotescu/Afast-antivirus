package Afast.pkg;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultEditorKit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


class Log{

    public String getIdLog() {
        return idLog;
    }

    public void setIdLog(String idLog) {
        this.idLog = idLog;
    }

    public String getIdCalc() {
        return idCalc;
    }

    public void setIdCalc(String idCalc) {
        this.idCalc = idCalc;
    }

    public String getDataLog() {
        return dataLog;
    }

    public void setDataLog(String dataLog) {
        this.dataLog = dataLog;
    }

    public String getInfectat() {
        return infectat;
    }

    public void setInfectat(String infectat) {
        this.infectat = infectat;
    }

    private String idLog;
    private String idCalc;
    private String dataLog;
    private String infectat;

    public Log(String idLog, String idCalc, String dataLog, String infectat){

        this.idLog = idLog;
        this.idCalc = idCalc;
        this.dataLog = dataLog;
        this.infectat = infectat;
    }

}

public class Logs{

    public JPanel panel1;
    private JButton sortareNoiButton;
    private JButton sortareVechiButton;
    private JTable table1;
    private JScrollPane logs;
    private JTextArea textArea1;



    public ArrayList<Log> logListNoi(String email) throws SQLException {

        ArrayList<Log> logsList = new ArrayList<Log>();
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


                st = dbConn.prepareStatement("SELECT id_log, id_calculator, data_log, infectat from logs where id_calculator = ? order by data_log desc");
                st.setString(1, id_calculator);
                ResultSet resultSet2 = st.executeQuery();

                Log log;
                while (resultSet2.next()) {
                    log = new Log(resultSet2.getString("id_log"), resultSet2.getString("id_calculator"), resultSet2.getString("data_log"), resultSet2.getString("infectat"));
                    logsList.add(log);
                }
            }
        } catch (SQLException ex){

        } finally {
            if(dbConn != null)
                dbConn.close();
        }
        return logsList;
    }

    public ArrayList<Log> logListVechi(String email) throws SQLException {

        ArrayList<Log> logsList = new ArrayList<Log>();
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
                //System.out.println(id_calculator);


                st = dbConn.prepareStatement("SELECT id_log, id_calculator, data_log, infectat from logs where id_calculator = ? order by data_log asc");
                st.setString(1, id_calculator);
                ResultSet resultSet2 = st.executeQuery();

                Log log;
                while (resultSet2.next()) {
                    log = new Log(resultSet2.getString("id_log"), resultSet2.getString("id_calculator"), resultSet2.getString("data_log"), resultSet2.getString("infectat"));
                    logsList.add(log);
                }
            }

        } catch (SQLException ex){

        } finally {
            if(dbConn != null)
                dbConn.close();
        }
        return logsList;
    }

    public Logs(String email) {

        table1 = new JTable();
        sortareNoiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection dbConn = DBConnection.connectDB();
                String id_utiliz = "";
                String id_calculator = "";
                textArea1.setText("ID_LOG\tID_CALCULATOR\tDATA_LOG\tINFECTAT");
                textArea1.setText(textArea1.getText() + "\n------------------------------------------------------------------------------");

                try {

                    ArrayList<Log> logz = logListNoi(email);

                    Object[] row = new Object[4];
                    for(int i = 0 ; i < logz.size(); ++i) {

                        /*row[0] = logz.get(i).getIdLog().toString();
                        row[1] = logz.get(i).getIdCalc().toString();
                        row[2] = logz.get(i).getDataLog().toString();
                        row[3] = logz.get(i).getInfectat().toString();*/

                        String inf = "";
                        switch (logz.get(i).getInfectat()){
                            case "0":
                                inf = "Neinfectat";
                                break;
                            case "1":
                                inf = "Infectat";
                                break;
                            case "2":
                                inf = "Nedeterminat";
                                break;
                            default:
                                inf = "Nedeterminat";
                                break;

                        }

                        String rez = logz.get(i).getIdLog() + "\t" + logz.get(i).getIdCalc() + "\t" + logz.get(i).getDataLog() + "\t" + inf;

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

                try {
                    PreparedStatement st = dbConn.prepareStatement("SELECT id_utilizator from utilizator where email = ? ");
                    st.setString(1, email);
                    ResultSet resultSet = st.executeQuery();

                    while(resultSet.next()) {
                        id_utiliz = resultSet.getString("id_utilizator");
                        System.out.println(id_utiliz);
                    }

                    st = dbConn.prepareStatement("SELECT id_calculator from calculatoare where id_utilizator = ?");
                    st.setString(1, id_utiliz);
                    resultSet = st.executeQuery();

                    while(resultSet.next()) {
                        id_calculator = resultSet.getString("id_calculator");
                        System.out.println(" ");
                        System.out.println(id_calculator + '\n');
                    }
                } catch (SQLException ex) {

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
        });


        sortareVechiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection dbConn = DBConnection.connectDB();
                String id_utiliz = "";
                String id_calculator = "";
                textArea1.setText("ID_LOG\tID_CALCULATOR\tDATA_LOG\tINFECTAT");
                textArea1.setText(textArea1.getText() + "\n------------------------------------------------------------------------------");

                try {

                    ArrayList<Log> logz = logListVechi(email);
                    DefaultTableModel model = (DefaultTableModel) table1.getModel();
                    table1.setModel(model);
                    Object[] row = new Object[4];
                    for(int i = 0 ; i < logz.size(); ++i) {

                        /*row[0] = logz.get(i).getIdLog().toString();
                        row[1] = logz.get(i).getIdCalc().toString();
                        row[2] = logz.get(i).getDataLog().toString();
                        row[3] = logz.get(i).getInfectat().toString();*/

                        String inf = "";
                        switch (logz.get(i).getInfectat()){
                            case "0":
                                inf = "Neinfectat";
                                break;
                            case "1":
                                inf = "Infectat";
                                break;
                            case "2":
                                inf = "Nedeterminat";
                                break;
                            default:
                                inf = "Nedeterminat";
                                break;

                        }

                        String rez = logz.get(i).getIdLog() + "\t" + logz.get(i).getIdCalc() + "\t" + logz.get(i).getDataLog() + "\t" + inf;
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

                try {
                    PreparedStatement st = dbConn.prepareStatement("SELECT id_utilizator from utilizator where email = ? ");
                    st.setString(1, email);
                    ResultSet resultSet = st.executeQuery();

                    while(resultSet.next()) {
                        id_utiliz = resultSet.getString("id_utilizator");
                        System.out.println(id_utiliz);
                    }

                    st = dbConn.prepareStatement("SELECT id_calculator from calculatoare where id_utilizator = ?");
                    st.setString(1, id_utiliz);
                    resultSet = st.executeQuery();

                    while(resultSet.next()) {
                        id_calculator = resultSet.getString("id_calculator");
                        System.out.println(" ");
                        System.out.println(id_calculator + '\n');
                    }




                } catch (SQLException ex) {

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
        });
    }


}


