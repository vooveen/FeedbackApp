/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feedback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author vooveen
 */
public class functions {
    static Connection conn = null;
    public static Connection connect() {
        
        // SQLite connection string
        String url = "jdbc:sqlite:C:\\Users\\ACER\\Feedback\\database\\feedback.db";
        //String url = "jdbc:sqlite:feedback.db";
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection Secced");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur de connection à la base de donnée", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public static void ajoutavi(String avi, String date, String heure ) {
        String sql = "INSERT INTO avis(avi,date,heure) VALUES('"+avi+"','"+date+"','"+heure+"')";
        Statement stmt = null;
        conn = connect();
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("Ajouter avec succes");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Ajout Impossible", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public static String date() {
        LocalDate d = java.time.LocalDate.now();
        String date = d.format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));
    return date;
    }
    
    public static String heure() {
        LocalTime t = java.time.LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String heure = t.format(formatter);
        return heure;
    }
    
    public static void switchpanel(JPanel panel, JLayeredPane layeredpane) {
        layeredpane.removeAll();
        layeredpane.add(panel);
        layeredpane.repaint();
        layeredpane.revalidate();
    }
    public static ResultSet getdata(){
        String sql = "SELECT avi, date, heure FROM avis";
        Statement stmt;
        ResultSet rs =null;
        try {
            Connection conn = connect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
    
    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }
    
    public static void export(ResultSet rs) throws FileNotFoundException, IOException, SQLException{            
                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = workbook.createSheet("Sheet");

                int rownum = 0;
                Cell cell;
                Row row;

                HSSFCellStyle style = createStyleForTitle(workbook);

                row = sheet.createRow(rownum);

                // Date
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue("Avis");
                cell.setCellStyle(style);
                // Heure
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue("Date");
                cell.setCellStyle(style);
                // Satisfaction
                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue("Heure");
                cell.setCellStyle(style);
                int j = 1;
                while (rs.next()) {
                    row = sheet.createRow(j);
                    // avi (A)
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue(rs.getString("avi"));
                    // date (B)
                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellValue(rs.getString("date"));
                    // heure (C)
                    cell = row.createCell(2, CellType.STRING);
                    cell.setCellValue(rs.getString("heure"));
                    j++;
                }
                File file = new File("C:\\Users\\ACER\\Feedback\\"+date()+".xls");
                file.getParentFile().mkdirs();

                FileOutputStream outFile = new FileOutputStream(file);
                workbook.write(outFile);
                System.out.println("Created file: "+file.getAbsolutePath());
    }
    
    public static void deletedata(){
        String sql = "DELETE FROM avis";
        Statement stmt;
        try {
            Connection conn = connect();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
        public static String getmac(){
        InetAddress ip;
        String macc=null;
	try {	
		ip = InetAddress.getLocalHost();
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
		byte[] mac = network.getHardwareAddress();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
		}
		macc = sb.toString();
	} catch (UnknownHostException | SocketException e) {
		e.printStackTrace();
	}
    return macc;
    }
    
}
