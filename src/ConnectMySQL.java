import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class ConnectMySQL {
    private static String url = "jdbc:mysql://localhost:3306/registrationDatabase?useSSL=false";
    private static String user = "root";
    private static String password = "53422558352Mm.";

    Connection conn = null;

    ConnectMySQL(String name,
                 String surname,
                 String mobile,
                 String gender,
                 int date, int month, int year,
                 String address) {
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

            Connection con = getConnection(url, user, password);
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO register(name,surname,mobile,gender,date, month, year, address) VALUE " +
                    "('" + name + "','" + surname + "','" + mobile + "','" + gender + "','" + date + "','" + month + "','" + year + "','" + address + "')");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}

