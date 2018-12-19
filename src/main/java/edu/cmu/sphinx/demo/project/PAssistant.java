package edu.cmu.sphinx.demo.project;
/**
 * Created by Pijush on 8/28/2017.
 */

import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Pijush on 8/28/2017.
 */
public class PAssistant extends JFrame {

    static JPanel chatpanel;
    static JTextField commands;
    static JPanel filler;
    static int speakercnditon = 0, started = 0;
    static GridBagConstraints c = new GridBagConstraints();
    static Font font = new Font("TimesRoman", Font.BOLD, 18);
    JPanel commandpanel;
    JButton speaker;
    SpeakingTest2 speak;
    LiveSpeechRecognizer recognizer;
    SwingWorker sworker;
    Worker worker;


    PAssistant() throws IOException {
        setSize(getToolkit().getScreenSize().width, getToolkit().getScreenSize().height - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        chatpanel = new JPanel();
        chatpanel.setLayout(new GridBagLayout());


        String user = System.getProperty("user.name");
        System.out.println(user);


        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.NORTHWEST;

        String[] greetings = {"Hi " + user, "Welcome " + user, "How can I help you," + user, "Hi there,friend!", "Good to see you"};
        Random random = new Random();
        int num = random.nextInt(greetings.length - 1);
        JTextArea text1 = new JTextArea(greetings[num]);
        text1.setFont(font);
        text1.setBackground(Color.GRAY);

        chatpanel.add(text1, c);


        JScrollPane textpane = new JScrollPane(chatpanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        getContentPane().add(textpane, BorderLayout.CENTER);


        commandpanel = new JPanel();
        commandpanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 1;

        gc.fill = GridBagConstraints.HORIZONTAL;


        commands = new JTextField();
        commands.addKeyListener(new Chatbot().Listen(commands));

        commandpanel.add(commands, gc);

        speaker = new JButton();
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/microphone2.png"));
        speaker.setIcon(icon);


        commandpanel.add(speaker);
        gc.weightx = 1;
        gc.weighty = 1;

        //commandpanel.add(new JPanel());


        getContentPane().add(commandpanel, BorderLayout.SOUTH);


        c.weightx = 1;
        c.weighty = 1;
        filler = new JPanel();
        chatpanel.add(filler, c);


     /*   commands.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                chatpanel.remove(filler);
                c.weightx = 0;
                c.weighty = 0;
                c.gridy++;
                String text = commands.getText();
                c.anchor = GridBagConstraints.NORTHEAST;
                JTextArea area = new JTextArea(text);
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setBackground(Color.BLUE);
                chatpanel.add(area, c);
                c.weightx = 1;
                c.weighty = 1;
                chatpanel.add(filler, c);
                chatpanel.revalidate();
            }
        });*/

        speak = new SpeakingTest2();
        recognizer = speak.getSpeech();

        speaker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // LiveSpeechRecognizer r=new SpeakingTest2().getSpeech();
                    if (speakercnditon == 0) {


                        if (started == 0)
                            recognizer.startRecognition(true);
                        else if (started >= 0) {
                            recognizer.startRecognition(false);

                        }
                        worker = new Worker();
                        worker.execute();
                        speaker.setBorder(new LineBorder(Color.green));
                        speakercnditon = 1;
                        started++;
                    } else if (speakercnditon == 1) {
                        try {


                            worker.cancel(true);
                            speakercnditon = 0;
                            speaker.setBorder(null);


                        } catch (Exception ex) {


                        }
                    }


                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu commandmenu = new JMenu("Commands");
        menuBar.add(commandmenu);
        JMenu Utilities = new JMenu("Utilities");
        commandmenu.add(Utilities);
        int length = Commands.utilites.length;
        for (int i = 0; i < length; i++) {
            JMenuItem item = new JMenuItem(Commands.utilites[i]);
            Utilities.add(item);
            final int finalI = i;
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    commands.setText(Commands.utilites[finalI]);
                    commands.grabFocus();
                    Robot robot = null;
                    try {
                        robot = new Robot();
                    } catch (Exception ev) {

                    }
                    robot.keyPress(KeyEvent.VK_ENTER);

                }
            });

        }
        JMenu Folders = new JMenu("Folders");
        commandmenu.add(Folders);
        length = Commands.Folders.length;
        for (int i = 0; i < length; i++) {
            JMenuItem item = new JMenuItem(Commands.Folders[i]);
            Folders.add(item);
            final int finalI = i;
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    commands.setText(Commands.Folders[finalI]);
                    commands.grabFocus();
                    Robot robot = null;
                    try {
                        robot = new Robot();
                    } catch (Exception ev) {

                    }
                    robot.keyPress(KeyEvent.VK_ENTER);

                }
            });

        }

        JMenu System = new JMenu("System");
        commandmenu.add(System);
        length = Commands.System.length;
        for (int i = 0; i < length; i++) {
            JMenuItem item = new JMenuItem(Commands.System[i]);
            System.add(item);
            final int finalI = i;
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    commands.setText(Commands.System[finalI]);
                    commands.grabFocus();
                    Robot robot = null;
                    try {
                        robot = new Robot();
                    } catch (Exception ev) {

                    }
                    robot.keyPress(KeyEvent.VK_ENTER);

                }
            });

        }
        JMenu Change = new JMenu("Change");
        commandmenu.add(Change);
        length = Commands.Change.length;
        for (int i = 0; i < length; i++) {
            JMenuItem item = new JMenuItem(Commands.Change[i]);
            Change.add(item);
            final int finalI = i;
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    commands.setText(Commands.Change[finalI]);
                    commands.grabFocus();
                    Robot robot = null;
                    try {
                        robot = new Robot();
                    } catch (Exception ev) {

                    }
                    robot.keyPress(KeyEvent.VK_ENTER);

                }
            });


        }

        ImageIcon notesicon = new ImageIcon(getClass().getResource("/images/notes.png"));
        ImageIcon musicicon = new ImageIcon(getClass().getResource("/images/musicicon.png"));
        ImageIcon dateicon = new ImageIcon(getClass().getResource("/images/dateicon.png"));


        JMenu notes = new JMenu();
        notes.setIcon(notesicon);
        menuBar.add(notes);
        JMenuItem takenote = new JMenuItem("Take a Note");
        takenote.addActionListener(runCommand());
        notes.add(takenote);

        JMenuItem shownotes = new JMenuItem("Show all Notes");
        shownotes.addActionListener(runCommand());
        notes.add(shownotes);
        setJMenuBar(menuBar);

        JMenu music = new JMenu();
        music.setIcon(musicicon);
        menuBar.add(music);
        JMenuItem play = new JMenuItem("Play a song");
        play.addActionListener(runCommand());
        music.add(play);

        JMenu time = new JMenu();
        time.setIcon(dateicon);
        menuBar.add(time);
        JMenuItem timeitem = new JMenuItem("Time");
        timeitem.addActionListener(runCommand());
        time.add(timeitem);
        JMenuItem day = new JMenuItem("Date");
        day.addActionListener(runCommand());
        time.add(day);


        setVisible(true);


    }

    public static void addText(String person, String text) {
        chatpanel.remove(filler);
        c.gridy++;
        c.weightx = 0;
        c.weighty = 0;
        JTextArea text1 = new JTextArea(text);
        text1.setLineWrap(true);
        text1.setWrapStyleWord(true);
        text1.setFont(font);

        if (person.equals("user")) {
            text1.setBackground(Color.BLUE);
            c.anchor = GridBagConstraints.NORTHEAST;
            chatpanel.add(text1, c);


        } else if (person.equals("pc")) {

            text1.setBackground(Color.GRAY);
            c.anchor = GridBagConstraints.NORTHWEST;
            chatpanel.add(text1, c);

        }
        c.weightx = 1;
        c.weighty = 1;
        chatpanel.add(filler, c);
        chatpanel.revalidate();

    }

    public static void main(String[] args) throws IOException {
        new PAssistant();
    }

    public ActionListener runCommand() {

        ActionListener rn = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem item = (JMenuItem) e.getSource();
                String str = item.getText();
                commands.setText(str);
                commands.grabFocus();
                Robot robot = null;
                try {
                    robot = new Robot();
                } catch (Exception ev) {

                }
                robot.keyPress(KeyEvent.VK_ENTER);

            }
        };
        return rn;
    }

    class Worker extends SwingWorker<Void, String> {

        @Override
        protected Void doInBackground() throws Exception {
            SpeechResult result;


            System.out.println(isCancelled());

            while ((result = recognizer.getResult()) != null) {


                if (isCancelled()) {
                    recognizer.stopRecognition();
                    break;
                }
                String t = result.getHypothesis();
                System.out.println(t);

                publish(t);


                ///commands.setText(t);
                //}

            }


            return null;
        }

        protected void process(java.util.List chunks) {
            // define what the event dispatch thread
            // will do with the intermediate results received
            // while the thread is executing
            String val = (String) chunks.get(chunks.size() - 1);

            commands.setText(val);
            commands.grabFocus();
            Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException e) {

                e.printStackTrace();
            }


            robot.keyPress(KeyEvent.VK_ENTER);

            //robot.keyRelease(KeyEvent.VK_ENTER);


        }

    }
}
