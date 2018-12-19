package edu.cmu.sphinx.demo.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Pijush on 9/5/2017.
 */
public class
        Chatbot {


    static String noteshown = null;

    String[][] chatbots =
            {{"hello", "hey", "hi"},
                    {"Hello", "Hey", "Hi"},

                    {"how are you"},
                    {"Good", "Doing Well"},


                    //open
                    {"open"},
                    {"opening", "I just opened it"},


                    {"play"},
                    {""},

                    {"take a note", "take note"},
                    {NoteTaken()},
                    {"show notes", "show all notes"},
                    {""},

                    //timeanddate
                    {"what time is it", "time", "tell me the time please"},
                    {new DateAndTime().getTime()},
                    {"date", "what is today's date", "today's date", "today"},
                    {new DateAndTime().getDate()},
                    {"day", "what day is today"},
                    {new DateAndTime().getDay()},

                    //alarm


                    {"I do not understand it"}


            };

    public static String NoteTaken() {
        String taken = "";
        if (Note.notetaken == 0)
            taken = "Note was not taken";
        else if (Note.notetaken == 1)
            taken = "Note was taken";
        else if (Note.notetaken == 2)
            taken = "Note was updated";
        return taken;
    }

    public KeyListener Listen(JTextField input) {
        KeyListener listener = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    input.setEditable(false);
                    //grab quote
                    String quote = input.getText();

                    PAssistant.addText("user", quote);
                    quote = quote.trim();
                    System.out.println(quote);
                    while (
                            quote.charAt(quote.length() - 1) == '!' ||

                                    quote.charAt(quote.length() - 1) == '.' ||
                                    quote.charAt(quote.length() - 1) == '?'
                            ) {
                        quote = quote.substring(0, quote.length() - 1);
                    }
                    quote = quote.trim();
                    byte response = 0;
                    //0=searching 1=did not find anything 2=found mathchs.

                    //check matches
                    int j = 0;//chatbot group
                    while (response == 0) {

                        // System.out.println(chatbots[j*2][0]);
                        if (inArray(quote.toLowerCase(), chatbots[j * 2])) {

                            response = 2;
                            int r = (int) (Math.random() * chatbots[(j * 2) + 1].length);
                            PAssistant.addText("pc", chatbots[(j * 2) + 1][r]);
                            try {
                                doJob(quote.toLowerCase());
                                System.out.println(r);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                        }
                        j++;
                        if (j * 2 == chatbots.length - 1 && response == 0)
                            response = 1;


                    }
                    if (response == 1) {
                        int r = (int) (Math.random() * chatbots[chatbots.length - 1].length);
                        PAssistant.addText("pc", chatbots[chatbots.length - 1][r]);

                    }
                    //  addText("\n");


                }

            }


            @Override
            public void keyReleased(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    input.setText("");
                    input.setEditable(true);

                }
            }


        };
        return listener;
    }

    public boolean inArray(String in, String[] str) {
        boolean match = false;
        String[] in2 = in.split(" ", 2);
        int u = 0;
        System.out.println(in2[0]);


        for (int i = 0; i < str.length; i++)

        {
            if (str[i].equals(in))
                match = true;

            else if (str[i].equals(in2[0]) && in2[0].equals("open")) {
                match = true;
            } else if (str[i].equals(in2[0]) && in2[0].equals("play")) {
                match = true;
            }
        }

        System.out.println(match);

        return match;

    }

    public void doJob(String str) throws IOException {
        String arrary[] = str.split(" ", 2);
        String drive = System.getenv("SystemDrive");
        String user = System.getProperty("user.name");
        System.out.println(drive + "\\Users\\" + user + "\\Downloads");

        String[][] opens = {
                {"my computer", "this pc"},
                {"cmd /c start explorer"},
                {"control panel"},
                {"cmd /c start control"},
                {"narrator"},
                {"cmd /c narrator.exe"},
                {"magnifier"},
                {"cmd /c magnify.exe"},
                {"notepad"},
                {"cmd /c notepad.exe"},
                {"camera"},
                {"cmd /c camera.exe"},
                {"command prompt"},
                {"cmd.exe /c start"},
                {"snipping tool"},
                {"cmd /c snippingtool.exe"},
                {"paint"},
                {"cmd /c mspaint.exe"},
                {"calculator"},
                {"cmd /c calc.exe"},
                {"sound recorder"},
                {"cmd /c soundrecorder.exe"},
                {"wordpad"},
                {"cmd /c wordpad.exe"},
                {"windows defender"},
                {drive + "\\Program Files\\Windows Defender\\MSASCui.exe"},
                {"windows firewall"},
                {"cmd /c firewall.cpl"},
                {"disk clean up"},
                {"cmd /c cleanmgr.exe"},
                {"system information"},
                {"cmd /c msinfo32.exe"},
                {"device manager"},
                {"cmd /c devmgmt.msc"},
                {"task manager"},
                {"cmd /c taskmgr"},
                {"on screen keyboard"},
                {"cmd /c osk.exe"},
                {"performance monitor"},
                {"cmd /c perfmon.msc /s"},
                {"defragmentor"},
                {"cmd /c  dfrgui.exe"},
                {"programs and features"},
                {"cmd /c appwiz.cpl"},
                {"date and time"},
                {"cmd /c timedate.cpl"},
                {"display settings"},
                {"cmd /c desk.cpl"},
                {"mouse properties", "mouse settings"},
                {"cmd /c main.cpl"},
                {"power options", "power settings"},
                {"cmd /c powercfg.cpl"},
                {"action center"},
                {"cmd /c wscui.cpl"},
                {"sound", "sound settings", "sound properties"},
                {"cmd /c mmsys.cpl"},
                {"system properties"},
                {"cmd /c sysdm.cpl"},
                {"devices and printers", "printers"},
                {"cmd /c control printers"},
                {"fonts"},
                {"cmd /c control fonts"},
                {"folder options"},
                {"cmd /c control folders"},
                {"keyboard settings", "keyboard properties"},
                {"cmd /c control keyboard"},
                {"taskbar", "taskbar settings", "taskbar properties"},
                {"cmd  /c rundll32.exe shell32.dll,Options_RunDLL 1"},
                {"user accounts", "users", "user profiles"},
                {"cmd /c control userpasswords"},
                {"personalization"},
                {"cmd /c control desktop"},
                {"downloads"},
                {"explorer.exe /select " + drive + "\\Users\\" + user + "\\Downloads"},
                {"desktop"},
                {"explorer.exe /select " + drive + "\\Users\\" + user + "Desktop"},
                {"my documents"},
                {"explorer.exe /select \"+drive+\"\\\\Users\\\\\"+user+\"Desktop"},


                {"play a song", "play song", "play music"},
                {""}
                //Runtime.getRuntime().exec("explorer.exe /select," + path);


        };
        if (arrary[0].equals("open")) {

            byte response = 0;
            int k = 0;

            while (response == 0) {
                if (inArray(arrary[1].toLowerCase(), opens[k * 2])) {
                    response = 2;
                    Process process = Runtime.getRuntime().exec(opens[(k * 2) + 1][0]);


                }
                k++;
                System.out.println(opens.length - 1);
                if (k * 2 == opens.length && response == 0)
                    response = 1;


            }
            if (response == 1) {
                // int r= (int) (Math.random()*chatbots[chatbots.length-1].length);
                PAssistant.addText("pc", "Can't be opened");

            }


        } else if (arrary[0].equals("play")) {
            Playsong();
        } else if (str.equals("take a note") || str.equals("take note")) {

            new TakeNote().execute();
        } else if (str.equals("show all notes") || str.equals("show notes")) {
            ShowNote();
        }


    }

    public void Playsong() {
        SongWorker worker = new SongWorker();
        PAssistant.addText("pc", "Please Wait");
        worker.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("state")) {
                    if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                        //button.setEnabled(false);
                        // addText();
                        int n = PAssistant.chatpanel.getComponentCount();
                        PAssistant.chatpanel.remove(n - 3);
                        PAssistant.chatpanel.revalidate();

                    }
                }
            }
        });
        worker.execute();

    }

    public void ShowNote() {

        SwingWorker shownt = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                JFrame frame = new JFrame("Notes");
                frame.setSize(600, 600);
                int x = Toolkit.getDefaultToolkit().getScreenSize().width / 4;
                int y = Toolkit.getDefaultToolkit().getScreenSize().height / 4;
                frame.setLocation(x, y);

                JPanel mainpanel = new JPanel();
                mainpanel.setLayout(new BoxLayout(mainpanel, BoxLayout.Y_AXIS));

                JScrollPane pane = new JScrollPane(mainpanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                frame.add(pane, BorderLayout.CENTER);
                try {
                    ResultSet rs = new Note().ShowAll();
                    if (rs.next() == false) {
                        noteshown = "No Notes were found";
                        PAssistant.addText("pc", noteshown);
                        frame.setVisible(false);

                    }
                    while (rs.next()) {
                        JPanel panel = new JPanel();
                        JTextArea textArea = new JTextArea(8, 36);
                        textArea.setWrapStyleWord(true);
                        textArea.setLineWrap(true);
                        JScrollPane textpane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


                        int id = rs.getInt("id");
                        String text = rs.getString("writings");
                        JLabel label = new JLabel(String.valueOf(id) + ". ");
                        panel.add(label);
                        textArea.setText(text);
                        textArea.setEditable(false);

                        panel.add(textpane);
                        JButton delete = new JButton("delete");
                        panel.add(delete);
                        delete.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    new Note().Delete(id);
                                    panel.setVisible(false);
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                } catch (ClassNotFoundException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });
                        JButton update = new JButton("update");
                        panel.add(update);
                        update.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                textArea.setEditable(true);
                                try {
                                    new Note(textArea.getText(), id, textArea);

                                    textArea.setText(new Note().getText(id));
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                } catch (ClassNotFoundException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });
                        mainpanel.add(panel);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

                frame.setVisible(true);
                frame.setResizable(false);

                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        noteshown = "Notes was showed";
                        PAssistant.addText("pc", noteshown);

                    }
                });

                PAssistant.commands.setEditable(true);
                return null;
            }
        };
        shownt.execute();

    }

    static class SongWorker extends SwingWorker<File, Void> {


        protected File doInBackground() throws Exception {

            File song = new song().SelectSong();
            return song;
        }

        protected void process(java.util.List chunks) {


        }


        @Override
        protected void done() {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(get());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    class TakeNote extends SwingWorker<Void, String> {


        @Override
        protected Void doInBackground() throws Exception {
            new Note("", 0, null);
            PAssistant.commands.setEditable(true);
            return null;
        }
    }
}




