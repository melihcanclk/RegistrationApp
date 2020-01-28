
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Enumeration;
import java.util.Map;

import static com.cloudinary.utils.ObjectUtils.*;

/**
 * A Java Swing program that does registration
 *
 * @author Melihcan Çilek
 */
public class Register extends JFrame implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Container c;
    private JLabel title;
    private JLabel name;
    private JTextField nameText;
    private JLabel surname;
    private JTextField surnameText;
    private JLabel mobile;
    private JTextField mobileText;
    private JLabel gender;
    private JRadioButton male;
    private JRadioButton female;
    private JRadioButton intersex;
    private JRadioButton ftm;
    private JRadioButton mtf;
    private ButtonGroup genderGroup;
    private JLabel birthday;
    private JComboBox<Integer> date;
    private JComboBox<Integer> month;
    private JComboBox<Integer> year;
    private JLabel address;
    private JTextArea addressText;
    private JFileChooser chooser;
    private File file;

    private TextField nameOfFile;
    private JButton addFile;
    private JButton submit;
    private JButton reset;

    private Integer dates[];
    private Integer months[];
    private Integer years[];
    Cloudinary cloudinary;

    private StringBuilder concatedString;


    Statement stmt;
    ResultSet rs;

    public Register() {

        setTitle("Registration Form");
        setBounds(300, 90, 600, 590);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        createMenuBar();

        years = new Integer[26];
        months = new Integer[12];
        dates = new Integer[31];

        setArr(years, 1994, 26);
        setArr(months, 1, 12);
        setArr(dates, 1, 31);

        title = new JLabel("Registration Form");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBackground(Color.WHITE);
        title.setSize(350, 35);
        title.setLocation(175, 30);
        c.add(title);

        name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(100, 20);
        name.setLocation(100, 100);
        c.add(name);

        nameText = new JTextField();
        nameText.setFont(new Font("Arial", Font.PLAIN, 15));
        nameText.setSize(300, 20);
        nameText.setLocation(200, 100);
        c.add(nameText);

        surname = new JLabel("Surname");
        surname.setFont(new Font("Arial", Font.PLAIN, 20));
        surname.setSize(100, 20);
        surname.setLocation(100, 150);
        c.add(surname);

        surnameText = new JTextField();
        surnameText.setFont(new Font("Arial", Font.PLAIN, 15));
        surnameText.setSize(300, 20);
        surnameText.setLocation(200, 150);
        c.add(surnameText);

        mobile = new JLabel("Mobile");
        mobile.setFont(new Font("Arial", Font.PLAIN, 20));
        mobile.setSize(100, 20);
        mobile.setLocation(100, 200);
        c.add(mobile);

        mobileText = new JTextField();
        mobileText.setFont(new Font("Arial", Font.PLAIN, 15));
        mobileText.setSize(250, 20);
        mobileText.setLocation(200, 200);
        c.add(mobileText);

        gender = new JLabel("Gender");
        gender.setFont(new Font("Arial", Font.PLAIN, 20));
        gender.setSize(100, 20);
        gender.setLocation(100, 250);
        c.add(gender);

        male = new JRadioButton("Male");
        male.setFont(new Font("Arial", Font.PLAIN, 10));
        male.setSelected(true);
        male.setSize(50, 20);
        male.setLocation(200, 250);
        c.add(male);

        female = new JRadioButton("Female");
        female.setFont(new Font("Arial", Font.PLAIN, 10));
        female.setSelected(false);
        female.setSize(75, 20);
        female.setLocation(250, 250);
        c.add(female);

        intersex = new JRadioButton("Intersex");
        intersex.setFont(new Font("Arial", Font.PLAIN, 10));
        intersex.setSelected(false);
        intersex.setSize(75, 20);
        intersex.setLocation(325, 250);
        c.add(intersex);

        ftm = new JRadioButton("FTM Male");
        ftm.setFont(new Font("Arial", Font.PLAIN, 10));
        ftm.setSelected(false);
        ftm.setSize(90, 20);
        ftm.setLocation(400, 250);
        c.add(ftm);

        mtf = new JRadioButton("MTF Female");
        mtf.setFont(new Font("Arial", Font.PLAIN, 10));
        mtf.setSelected(false);
        mtf.setSize(90, 20);
        mtf.setLocation(490, 250);
        c.add(mtf);

        genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        genderGroup.add(intersex);
        genderGroup.add(ftm);
        genderGroup.add(mtf);

        birthday = new JLabel("Birthday");
        birthday.setFont(new Font("Arial", Font.PLAIN, 20));
        birthday.setSize(100, 20);
        birthday.setLocation(100, 300);
        c.add(birthday);

        date = new JComboBox<Integer>(dates);
        date.setFont(new Font("Arial", Font.PLAIN, 15));
        date.setSize(100, 20);
        date.setLocation(200, 300);
        c.add(date);

        month = new JComboBox<Integer>(months);
        month.setFont(new Font("Arial", Font.PLAIN, 15));
        month.setSize(100, 20);
        month.setLocation(300, 300);
        c.add(month);

        year = new JComboBox<Integer>(years);
        year.setFont(new Font("Arial", Font.PLAIN, 15));
        year.setSize(100, 20);
        year.setLocation(400, 300);
        c.add(year);

        address = new JLabel("Address");
        address.setFont(new Font("Arial", Font.PLAIN, 20));
        address.setSize(100, 20);
        address.setLocation(100, 350);
        c.add(address);

        addressText = new JTextArea();
        addressText.setFont(new Font("Arial", Font.PLAIN, 15));
        addressText.setSize(300, 75);
        addressText.setLocation(200, 350);
        addressText.setLineWrap(true);
        c.add(addressText);

        addFile = new JButton("Add File");
        addFile.setName("addFile");
        addFile.setFont(new Font("Arial", Font.PLAIN, 15));
        addFile.setSize(100, 20);
        addFile.setLocation(150, 435);
        addFile.addActionListener(this);
        c.add(addFile);

        nameOfFile = new TextField(5);
        nameOfFile.setFont(new Font("Arial", Font.PLAIN, 10));
        nameOfFile.setSize(100, 20);
        nameOfFile.setLocation(250, 435);
        c.add(nameOfFile);

        submit = new JButton("Submit");
        submit.setName("submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 15));
        submit.setSize(100, 20);
        submit.setLocation(150, 500);
        submit.addActionListener(this);
        c.add(submit);

        reset = new JButton("Reset");
        submit.setName("reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(300, 500);
        reset.addActionListener(this);
        c.add(reset);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == submit) {

            String selectedButton = getSelectedButtonText(genderGroup);

            ConnectMySQL connection = new ConnectMySQL(
                    nameText.getText(),
                    surnameText.getText(),
                    mobileText.getText(),
                    selectedButton,
                    (int) date.getSelectedItem(),
                    (int) month.getSelectedItem(),
                    (int) year.getSelectedItem(),
                    addressText.getText());

            // TODO : Input correction (Mobile will take only numbers etc.)
            

        }
        if (event.getSource() == addFile) {
            chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
                file = chooser.getSelectedFile();
                nameOfFile.setText(chooser.getSelectedFile().getName());
                nameOfFile.setSize(chooser.getSelectedFile().getName().length() * 8, 20);
            }
        }
        if (event.getSource() == reset) {
            String def = "";
            nameText.setText(def);
            surnameText.setText(def);
            addressText.setText(def);
            mobileText.setText(def);
            date.setSelectedIndex(0);
            month.setSelectedIndex(0);
            year.setSelectedIndex(0);
            nameOfFile.setText(def);
        }
    }

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

    private void createMenuBar() {

        JMenuBar menuBar = new JMenuBar();
        ImageIcon exitIcon = new ImageIcon("UserRegistrationApp/src/resources/Exit.png");

        JMenu fileMenu = new JMenu("File");
        //alt+f is shortcut to select file menu bar
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenu viewMenu = new JMenu("View");
        //alt+V is shortcut to select file menu bar
        viewMenu.setMnemonic(KeyEvent.VK_V);

        JMenuItem eMenuItem = new JMenuItem("Exit", exitIcon);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener((event) -> System.exit(0));

        JCheckBoxMenuItem showStatusBarMenuItem = new JCheckBoxMenuItem("Dark Mode");
        showStatusBarMenuItem.setMnemonic(KeyEvent.VK_S);
        showStatusBarMenuItem.setDisplayedMnemonicIndex(5);
        showStatusBarMenuItem.setSelected(false);

        showStatusBarMenuItem.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                c.setBackground(Color.black);
                title.setForeground(Color.white);
                name.setForeground(Color.white);
                surname.setForeground(Color.white);
                birthday.setForeground(Color.white);
                mobile.setForeground(Color.white);
                gender.setForeground(Color.white);
                address.setForeground(Color.white);

            } else {
                c.setBackground(Color.white);
                title.setForeground(Color.black);
                name.setForeground(Color.black);
                surname.setForeground(Color.black);
                birthday.setForeground(Color.black);
                mobile.setForeground(Color.black);
                gender.setForeground(Color.black);
                address.setForeground(Color.black);
            }
        });

        fileMenu.add(eMenuItem);
        viewMenu.add(showStatusBarMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);

        setJMenuBar(menuBar);
    }

    void setArr(Integer[] arr, int startingNumber, int numberOfElements) {

        for (int i = 0; i < numberOfElements; i++) {
            arr[i] = startingNumber + i;
        }
    }

}
