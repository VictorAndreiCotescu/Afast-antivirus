package Afast.pkg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.util.*;


public class CreateAcc {

    private JTextField txtEmail;
    private JTextField txtNume;
    private JTextField txtPrenume;
    private JPasswordField passwordField1;
    private JButton button1;
    public JPanel panel1;


    public CreateAcc(String usr, String passwd) {


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                int idUtiliz = 0;
                int idCalc = 0;
                Connection dbConn = DBConnection.connectDB();
                try {

                    PreparedStatement st = dbConn.prepareStatement("SELECT * from utilizator where email = ?");
                    st.setString(1, txtEmail.getText());
                    ResultSet resultSet = st.executeQuery();

                    if (resultSet.next()) {

                        JOptionPane.showMessageDialog(null, "Adresa email este deja in uz", "Oops..", JOptionPane.INFORMATION_MESSAGE);

                    } else {


                        PreparedStatement stCreate = dbConn.prepareStatement("INSERT INTO utilizator (ID_UTILIZATOR, NUME, PRENUME, EMAIL, PAROLA) VALUES (?,?,?,?,?)");

                        PreparedStatement chkID = dbConn.prepareStatement("SELECT * FROM utilizator WHERE ID_UTILIZATOR = ?");

                        Random rand = new Random();
                        idUtiliz = rand.nextInt(10000);
                        chkID.setString(1, String.valueOf(idUtiliz));
                        ResultSet chkRS = chkID.executeQuery();

                        if (chkRS.next()) {
                            while (chkRS.next()) {
                                idUtiliz = rand.nextInt(10000);
                                chkID.setString(1, String.valueOf(idUtiliz));
                                chkRS = chkID.executeQuery();
                            }
                        } else {
                            stCreate.setInt(1, idUtiliz);
                        }

                        stCreate.setString(2, txtNume.getText());
                        stCreate.setString(3, txtPrenume.getText());
                        stCreate.setString(4, txtEmail.getText());

                        char[] passwd = passwordField1.getPassword();
                        String pass = new String(passwd);
                        stCreate.setString(5, pass);

                        try {

                            PreparedStatement stAddCalcualtor = dbConn.prepareStatement("INSERT INTO calculatoare (ID_CALCULATOR, ID_UTILIZATOR, SISTEM_OPERARE, STATUS_INFECTARE, ID_LICENTA)"
                                    + "values(?,?,?,?,?)");

                            SystemInfo systemInfo = new SystemInfo();
                            HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
                            ComputerSystem computerSystem = hardwareAbstractionLayer.getComputerSystem();
                            idCalc = Integer.parseInt(computerSystem.getSerialNumber().replaceAll("\\D", ""));
                            stAddCalcualtor.setInt(1, idCalc);

                            stAddCalcualtor.setInt(2, idUtiliz);


                            OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
                            String Os = operatingSystem.getManufacturer();
                            stAddCalcualtor.setString(3, Os);

                            stAddCalcualtor.setString(4, "Unknown");


                            String LicKeyDefault = "FR" + Integer.parseInt(computerSystem.getSerialNumber().replaceAll("\\D", ""));

                            System.out.println(LicKeyDefault);

                            stAddCalcualtor.setString(5, LicKeyDefault);

                            ;

                            /*byte[] id = LicKeyDefault.getBytes(StandardCharsets.US_ASCII);
                            for (int i: id){
                                idCalc = id[i]*(i+1);
                                ++i;
                            }*/


                            //stAddCalcualtor.setInt(1, idCalc);

                            PreparedStatement stAddLicKeydef = dbConn.prepareStatement("INSERT INTO licente (ID_LICENTA, ID_UTILIZATOR, ID_TIP_LICENTA, ACTIVA, DATA_ACTIVARE)" +
                                    "values(?,?,?,?,?)");

                            stAddLicKeydef.setString(1, LicKeyDefault);
                            stAddLicKeydef.setInt(2, idUtiliz);
                            stAddLicKeydef.setInt(3, 0);
                            stAddLicKeydef.setInt(4, 1);



                            SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
                            Date date = new Date(System.currentTimeMillis());
                            stAddLicKeydef.setString(5, formatter.format(date));



                            PreparedStatement stAddCreationLog = dbConn.prepareStatement("INSERT INTO logs (ID_LOG, ID_CALCULATOR, DATA_LOG, INFECTAT)" +
                                    "values(?,?,?,?)");

                            PreparedStatement getNrLogs = dbConn.prepareStatement("SELECT count('ID_LOG') as nrLogs FROM logs");


                            try {
                                ResultSet nrLogs = getNrLogs.executeQuery();
                                nrLogs.next();
                                stAddCreationLog.setInt(1, nrLogs.getInt("nrLogs") + 1);
                            } catch (SQLException ex) {
                                System.out.println(ex);
                            }



                            stAddCreationLog.setInt(2, idCalc);
                            stAddCreationLog.setString(3, formatter.format(date));
                            stAddCreationLog.setInt(4, 2);


                            try {

                                stCreate.executeUpdate();

                                stAddCalcualtor.executeUpdate();

                                stAddCreationLog.executeUpdate();

                                stAddLicKeydef.executeUpdate();

                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "Adresa EMAIL trebuie sa respecte formatul __@__.__", "Oops..", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }

                        } catch (SQLException ex) {


                            JOptionPane.showMessageDialog(null, "Ceva nu a functionat", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                            return;

                        }

                        JOptionPane.showMessageDialog(null, "Cont creat cu success!", "Succes!", JOptionPane.INFORMATION_MESSAGE);
                    }


                } catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, "Eroare creare cont", ex);

                } finally {
                    if (dbConn != null) {
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



