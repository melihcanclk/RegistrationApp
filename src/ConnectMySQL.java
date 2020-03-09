import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class ConnectMySQL {
    private static String url = "jdbc:mysql://localhost:3306/fooo?useSSL=false";
    private static String user = "root";
    private static String password = "root123";

    Connection conn = null;

    ConnectMySQL(String name,
                 String surname,
                 String mobile,
                 String gender,
                 int day, int month, int year,
                 String address,
                 File file) {
        ArrayList<String> x = new ArrayList<String>(4);
        x.add(name);
        x.add(surname);
        x.add(mobile);
        x.add(address);
        int counter = 0;

        //if all inputs are empty, dont add
        for (int i = 0; i < 4; ++i) {

            if(!x.get(i).isEmpty()) {
                System.out.println("add");
                i = 5;
            }
            else if(i == 3){
                System.out.println("not add");
                return;
            }

        }
        try {
            conn = getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            /**
             * saving all inputs to database upload part
             */
            FileInputStream fis = new FileInputStream(file);
            String INSERT_PICTURE = "INSERT INTO foo_table(name,surname,mobile,gender,day, month, year, address,pdf_name,pdf) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(INSERT_PICTURE);
            ps.setString(1,name);
            ps.setString(2,surname);
            ps.setString(3,mobile);
            ps.setString(4,gender);
            ps.setString(5, String.valueOf(day));
            ps.setString(6, String.valueOf(month));
            ps.setString(7, String.valueOf(year));
            ps.setString(8, address);
            ps.setString(9,file.getName());
            ps.setBinaryStream(10, fis, (int)file.length());
            ps.executeUpdate();

            /**
             * Loading pdf to current directory
             */
            String SELECT_PDF = "select pdf from foo_table where pdf_name='" + file.getName() + "'";
            ps = conn.prepareStatement(SELECT_PDF);
            ResultSet rs =  ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                FileOutputStream fos = new FileOutputStream("file_" + i+1 + ".pdf");
                InputStream is  = rs.getBinaryStream(1);
                byte[] buf = new byte[100000];
                int read = 0;
                while ((read = is.read(buf))>0) {
                    fos.write(buf, 0, read);
                }
                fos.close();
                is.close();
            }
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}

