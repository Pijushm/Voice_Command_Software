package edu.cmu.sphinx.demo.project;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Pijush on 9/2/2017.
 */
public class song extends JFrame {

    ArrayList<String> titles = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ArrayList list = new ArrayList();
        // new song().listf("E:\\",list);
        new song().SelectSong();
        for (Object l : list) {
            System.out.println(l);
        }

        File[] paths = File.listRoots();
        FileSystemView fsv = FileSystemView.getFileSystemView();
        for (File file : paths) {
            if (fsv.getSystemTypeDescription(file).equals("Local Disk"))
                System.out.println(file + " " + file.canRead() + "" + file.isHidden());
        }


    }

    public void listf(String directoryName, ArrayList<File> files) {
        File directory = new File(directoryName);


        // get all the files from a directory
        File[] fList = directory.listFiles();

        int number = 0;

        for (File file : fList) {
            if (file.isFile() && file.getName().endsWith(".mp3")) {
                {

                    number++;


                    if (number > (fList.length - 5)) {
                        //play a random file
                        for (File file1 : fList) {
                            try {

                                FileInputStream file2 = new FileInputStream(file1);
                                int size = (int) file1.length();
                                file2.skip(size - 128);
                                byte[] last128 = new byte[128];
                                file2.read(last128);
                                String id3 = new String(last128);
                                String tag = id3.substring(0, 3);
                                if (tag.equals("TAG")) {
                                    files.add(file1);
                                    titles.add(id3.substring(3, 32));
                                }
                                file2.close();
                            } catch (Exception e) {

                            }

                        }
                        break;

                    }
                }

            } else if (file.isDirectory()) {
                {

                    try {
                        listf(file.getAbsolutePath(), files);
                    } catch (Exception e) {

                    }
                }
            }
        }
    }

    public File SelectSong() throws IOException {
        int totaldrives = 0;
        ArrayList<Integer> except = new ArrayList();
        except.add(0);
        ArrayList drive = new ArrayList();
        ArrayList<File> songs = new ArrayList();
        File[] paths = File.listRoots();
        FileSystemView fsv = FileSystemView.getFileSystemView();
        for (File file : paths) {
            if (fsv.getSystemTypeDescription(file).equals("Local Disk")) {

                if (file.canRead() == false || file.isHidden() == false)
                    except.add(totaldrives);

                drive.add(file);
                totaldrives++;

            }

        }

        System.out.println(totaldrives);
        Random random = new Random();
        int drivenum = random.nextInt(totaldrives - 1);

        while (except.contains(drivenum)) {
            drivenum = random.nextInt(totaldrives - 1);
        }
        System.out.println(drive.get(drivenum));
        listf(drive.get(drivenum).toString(), songs);


        int selectedsong = random.nextInt(songs.size() - 1);
        File song = songs.get(selectedsong);
        System.out.println(song.getName());
        PAssistant.addText("pc", "Now Playing" + titles.get(selectedsong));
        //System.out.println("Now Playing"+" "+titles.get(selectedsong));
        System.out.println(songs.size());
        System.out.println(titles.size());
        //Desktop desktop=Desktop.getDesktop();
        //desktop.open(song);


        return song;
    }
}
