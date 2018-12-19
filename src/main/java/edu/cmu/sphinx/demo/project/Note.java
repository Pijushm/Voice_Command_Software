package edu.cmu.sphinx.demo.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Created by Pijush on 9/7/2017.
 */
public class Note extends JFrame {

    static int notetaken = 0;
    JTextArea textArea = new JTextArea(5, 100);
    JButton okbutton, cancelbutton;

    public Note() {

    }

    public Note(String str, int id, JTextArea txtarea) {
        int x = getToolkit().getScreenSize().width / 4;
        int y = getToolkit().getScreenSize().height / 4;
        setLocation(x, y);

        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(400, 400));
        setVisible(true);

        textArea.setText(str);
        add(textArea, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        okbutton = new JButton("OK");
        okbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String writing = textArea.getText();

                if (writing.equals("")) {
                    notetaken = 0;
                    //addText(Note was not Taken);
                    Chatbot.NoteTaken();
                } else if (!writing.equals("") && id == 0) {//addText();if(id==0)

                    try {
                        Insert(writing);
                        notetaken = 1;
                        Chatbot.NoteTaken();
                        //addText(Note was taken);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    try {


                        Update(id, writing, txtarea);
                        //addText(Note was updated);


                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }


                setVisible(false);
            }
        });
        panel.add(okbutton);
        cancelbutton = new JButton("Cancel");
        cancelbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        panel.add(cancelbutton);
        add(panel, BorderLayout.SOUTH);

    }

    public Connection Connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/notes", "root", "");

        return con;

    }

    public void Insert(String writing) throws SQLException, ClassNotFoundException {
        int id = 0;
        Connection con = Connect();
        Statement st = con.createStatement();
        ResultSet resultSet = st.executeQuery("Select Max(id) maxid From notes");
        while (resultSet.next()) {
            id = resultSet.getInt("maxid");
        }
        boolean rs = st.execute("INSERT into notes VALUES(" + (id + 1) + ",\"" + writing + "\")");
    }

    public String getText(int id) throws SQLException, ClassNotFoundException {
        String text = "";
        Connection con = Connect();
        Statement st = con.createStatement();
        ResultSet resultSet = st.executeQuery("Select * From notes WHERE id=" + id);
        while (resultSet.next()) {
            text = resultSet.getString("writings");
        }
        return text;

    }

    public void Delete(int id) throws SQLException, ClassNotFoundException {
        Connection con = Connect();
        Statement st = con.createStatement();
        boolean rs = st.execute("DELETE FROM notes WHERE id=" + id);
        boolean update = st.execute("UPDATE notes SET id =id-1 WHERE id>" + id);
    }

    public ResultSet ShowAll() throws SQLException, ClassNotFoundException {
        Connection con = Connect();
        Statement st = con.createStatement();
        ResultSet resultSet = st.executeQuery("Select * From notes");

        return resultSet;
    }

    public String Update(int id, String writing, JTextArea textarea) throws SQLException, ClassNotFoundException {
        String text = "";
        Connection con = Connect();
        Statement st = con.createStatement();


        boolean update = st.execute("UPDATE notes SET writings=\"" + writing + "\" WHERE id=" + id);

        ResultSet resultSet = st.executeQuery("Select * From notes WHERE id=" + id);
        while (resultSet.next()) {
            text = resultSet.getString("writings");
            textarea.setText(text);
        }

        return text;
    }

}
