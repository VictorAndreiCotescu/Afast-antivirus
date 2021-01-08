package Afast.pkg;

import com.google.protobuf.BoolValue;
import com.google.protobuf.NullValue;
import com.google.protobuf.StringValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

public class Meniu {
    private JButton scanButton;
    public JPanel panel1;
    private JButton logsButton;
    private JButton adaugareCalculator;
    private JButton optiuniButton;
    private JButton utilizatoriButton;
    private JButton virusiButton;
    private JButton calculatoareButton;

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {

        }
        return null;
    }

    public Meniu(String email, String passwd) {

        Connection dbConn = DBConnection.connectDB();

        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                FileDialog dialog = new FileDialog((Frame) null, "Afast - Alegeti fisierul");
                dialog.setMode(FileDialog.LOAD);
                dialog.setVisible(true);
                ImageIcon img = new ImageIcon("D:/Downloads/31.3.png");
                dialog.setIconImage(img.getImage());
                String file = dialog.getFile();
                System.out.println(MD5(file));


                try {
                    /*PreparedStatement st = dbConn.prepareStatement("SELECT  * from tipvirus where HASH_VIRUS = ?");
                    st.setString(1, MD5(file));
                    ResultSet resultSet = st.executeQuery();

                    if (resultSet.next()) {
                        System.out.println("--!!");

                        PreparedStatement stCreate = dbConn.prepareStatement("SELECT ID_Pc from utilizator where username = ?");
                        stCreate.setString(1, usr);
                        ResultSet rs = stCreate.executeQuery();


                        if (rs.next()) {

                            String pcid = rs.getString("ID_Pc");
                            PreparedStatement stInsertLog = dbConn.prepareStatement("INSERT INTO logs (id_pc, status, data) values (?,?,?)");
                            stInsertLog.setString(1, pcid.toString());
                            stInsertLog.setString(2, "Infected");
                            stInsertLog.setString(3, java.time.LocalDateTime.now().toString());
                            stInsertLog.executeUpdate();

                        }

                    } else {
                        System.out.println("++!");

                        PreparedStatement stCreate = dbConn.prepareStatement("SELECT ID_Pc from utilizator where username = ?");
                        stCreate.setString(1, usr);
                        ResultSet rs = stCreate.executeQuery();

                        if (rs.next()) {
                            String pcid = rs.getString("ID_Pc");
                            PreparedStatement stInsertLog = dbConn.prepareStatement("INSERT INTO logs (id_pc, status, data) values (?,?,?)");
                            stInsertLog.setString(1, pcid.toString());
                            stInsertLog.setString(2, "Not infected");
                            stInsertLog.setString(3, java.time.LocalDate.now().toString());
                            stInsertLog.executeUpdate();
                        }
                    }*/

                    int ok = 1;

                    BufferedReader br = new BufferedReader(new FileReader("hashez"));

                    try {
                        String hash = MD5(file);
                        StringBuilder sb = new StringBuilder();
                        String line = br.readLine();

                        while (line != null) {

                            line = br.readLine();

                            if(line != null)
                                if(line.equals(hash)){
                                        String id_utiliz = "";
                                        String id_calculator = "";
                                    try {
                                        PreparedStatement st = dbConn.prepareStatement("SELECT id_utilizator from utilizator where email = ? ");
                                        st.setString(1, email);
                                        ResultSet resultSet = st.executeQuery();

                                        while(resultSet.next()) {
                                            id_utiliz = resultSet.getString("id_utilizator");
                                        }

                                        st = dbConn.prepareStatement("SELECT id_calculator from calculatoare where id_utilizator = ?");
                                        st.setString(1, id_utiliz);
                                        resultSet = st.executeQuery();

                                        while(resultSet.next()) {
                                            id_calculator = resultSet.getString("id_calculator");

                                        }
                                    } catch (SQLException ex) {
                                        System.out.println(ex);
                                    }

                                    SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
                                    Date date = new Date(System.currentTimeMillis());


                                    Connection dbConn = DBConnection.connectDB();

                                    try {
                                        PreparedStatement st = dbConn.prepareStatement("SELECT id_utilizator from utilizator where email = ? ");
                                        st.setString(1, email);
                                        ResultSet resultSet = st.executeQuery();

                                        while(resultSet.next()) {
                                            id_utiliz = resultSet.getString("id_utilizator");
                                        }

                                        st = dbConn.prepareStatement("SELECT id_calculator from calculatoare where id_utilizator = ?");
                                        st.setString(1, id_utiliz);
                                        resultSet = st.executeQuery();

                                        while(resultSet.next()) {
                                            id_calculator = resultSet.getString("id_calculator");

                                        }
                                    } catch (SQLException ex) {
                                        System.out.println(ex);
                                    }

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

                                    stAddCreationLog.setInt(2, Integer.parseInt(id_calculator));
                                    stAddCreationLog.setString(3, formatter.format(date));
                                    stAddCreationLog.setInt(4, 1);

                                    PreparedStatement stAddVirus = dbConn.prepareStatement("INSERT INTO virusi (ID_VIRUS, ID_TIP_VIRUS, ID_CALCULATOR)" +
                                            "values(?,?,?)");


                                    PreparedStatement getNrIVir = dbConn.prepareStatement("SELECT count('ID_VIRUS') as nrIdVirus FROM virusi");

                                    try {
                                        ResultSet nrIdVirus = getNrIVir.executeQuery();
                                        nrIdVirus.next();
                                        stAddVirus.setInt(1, nrIdVirus.getInt("nrIdVirus") + 1);
                                    } catch (SQLException ex) {
                                        System.out.println(ex);
                                    }

                                    Random rand = new Random();
                                    int idTipVirus = rand.nextInt(3) + 1;
                                    stAddVirus.setInt(2, idTipVirus);

                                    stAddVirus.setInt(3, Integer.parseInt(id_calculator));

                                    stAddVirus.executeUpdate();
                                    stAddCreationLog.executeUpdate();

                                    JOptionPane.showMessageDialog(null, "Virus detectat!\n hash: " + hash, "Atentie!", JOptionPane.INFORMATION_MESSAGE);
                                    ok = 0;
                                }
                        }

                    } finally {
                        br.close();
                    }

                    if(ok == 1){

                        String id_utiliz = "";
                        String id_calculator = "";
                        try {
                            PreparedStatement st = dbConn.prepareStatement("SELECT id_utilizator from utilizator where email = ? ");
                            st.setString(1, email);
                            ResultSet resultSet = st.executeQuery();

                            while(resultSet.next()) {
                                id_utiliz = resultSet.getString("id_utilizator");

                            }

                            st = dbConn.prepareStatement("SELECT id_calculator from calculatoare where id_utilizator = ?");
                            st.setString(1, id_utiliz);
                            resultSet = st.executeQuery();

                            while(resultSet.next()) {
                                id_calculator = resultSet.getString("id_calculator");

                            }
                        } catch (SQLException ex) {
                            System.out.println(ex);
                        }

                        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
                        Date date = new Date(System.currentTimeMillis());


                        PreparedStatement stAddLog = dbConn.prepareStatement("INSERT INTO logs (ID_LOG, ID_CALCULATOR, DATA_LOG, INFECTAT)" +
                                "values(?,?,?,?)");

                        PreparedStatement getNrLogs = dbConn.prepareStatement("SELECT count('ID_LOG') as nrLogs FROM logs");

                        try {
                            ResultSet nrLogs = getNrLogs.executeQuery();
                            nrLogs.next();
                            stAddLog.setInt(1, nrLogs.getInt("nrLogs") + 1);
                        } catch (SQLException ex) {
                            System.out.println(ex);
                        }

                        stAddLog.setInt(2, Integer.parseInt(id_calculator));
                        stAddLog.setString(3, formatter.format(date));
                        stAddLog.setInt(4, 0);

                        stAddLog.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Nu au fost detectat virusi!", "Super!", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException | SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });



        logsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ShowLogs(email);

            }
        });

        /*adaugareCalculator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PreparedStatement stCreate = null;

                try{
                    stCreate = dbConn.prepareStatement("INSERT INTO calculatoare(ID_CALCULATOR, ID_UTILIZATOR, SISTEM_OPERARE, STATUS_INFECTARE, ID_LICENTA)" +
                            "VALUES (?,?,?,?,?)");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });*/
        utilizatoriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ShowUtilizatori();

            }
        });
        optiuniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowOptiuni(email);
            }
        });
        virusiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame meniu = new JFrame("Afast");

                ImageIcon img = new ImageIcon("D:/Downloads/31.3.png");
                meniu.setIconImage(img.getImage());
                meniu.setContentPane(new Virusi(email).panel1);
                meniu.setLocation(500, 500);
                meniu.setSize(500, 300);
                meniu.setVisible(true);
                //meniu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            }
        });
        calculatoareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame meniu = new JFrame("Afast");

                ImageIcon img = new ImageIcon("D:/Downloads/31.3.png");
                meniu.setIconImage(img.getImage());
                meniu.setContentPane(new Calculatoare(email).panel1);
                meniu.setLocation(500, 500);
                meniu.setSize(500, 300);
                meniu.setVisible(true);
                //meniu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            }
        });
    }

    static String generateLicenseKey()
    {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();
        ComputerSystem computerSystem = hardwareAbstractionLayer.getComputerSystem();

        String vendor = operatingSystem.getManufacturer();
        String processorSerialNumber = computerSystem.getSerialNumber();
        String processorIdentifier = centralProcessor.getProcessorIdentifier().toString();
        int processors = centralProcessor.getLogicalProcessorCount();

        String delimiter = "#";
        System.out.println(vendor);
        System.out.println(operatingSystem);
        return processorSerialNumber + delimiter + processors;
    }


    public static void main(String[] args) {

        System.out.println(generateLicenseKey());


    }

    public static void ShowLogs(String email) {

        JFrame meniu = new JFrame("Afast");

        ImageIcon img = new ImageIcon("D:/Downloads/31.3.png");
        meniu.setIconImage(img.getImage());
        meniu.setContentPane(new Logs(email).panel1);
        meniu.setLocation(500, 500);
        meniu.setSize(500, 300);
        meniu.setVisible(true);
        //meniu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void ShowUtilizatori() {

        JFrame meniu = new JFrame("Afast");

        ImageIcon img = new ImageIcon("D:/Downloads/31.3.png");
        meniu.setIconImage(img.getImage());
        meniu.setContentPane(new Utilizatori().panel1);
        meniu.setLocation(500, 500);
        meniu.setSize(500, 300);
        meniu.setVisible(true);
       // meniu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void ShowOptiuni(String email) {

        JFrame meniu = new JFrame("Afast");

        ImageIcon img = new ImageIcon("D:/Downloads/31.3.png");
        meniu.setIconImage(img.getImage());
        meniu.setContentPane(new Optiuni(email).panel1);
        meniu.setLocation(500, 500);
        meniu.setSize(500, 300);
        meniu.setVisible(true);
        // meniu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void ShowVirusi(String email) {

        JFrame meniu = new JFrame("Afast");

        ImageIcon img = new ImageIcon("D:/Downloads/31.3.png");
        meniu.setIconImage(img.getImage());
        meniu.setContentPane(new Virusi(email).panel1);
        meniu.setLocation(500, 500);
        meniu.setSize(500, 300);
        meniu.setVisible(true);
        // meniu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
