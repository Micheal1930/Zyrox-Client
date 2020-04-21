package com.varrock.client.security;

import java.io.*;
import java.util.Random;

import com.varrock.client.HttpDownloadUtility;
import com.varrock.client.signlink;

/**
 * A DataOutputStream file is automatically downloaded whenever you start the client, that contains
 * directories and 10 secrets. The client will read that file, and store it. From there it will check to see
 * if any of those directories exist, if it does it will read the serial from that stream. If it doesn't
 * exist it will continue reading the directories until it finds one. If all of the files are deleted
 * that is the only way possible to ban-evade. However, finding the directorys are not easy unless
 * someone decompiles this and has java knowledge to read the stream.
 * If someone does manage to alter one of the streams, the real trick is that the 'secrets' are not only
 * used for the file names, but it also is randomly put into the serial and checked server side if it exists inside of it.
 * @author Jonny
 */
public class JSerial {

    public static String serial = "invalid_serial";

    /**
     * This will generate a 25 character long unique serial
     * @return
     */
    public static String generate(int length) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        int ran = new Random().nextInt(10);
        for (int i = 0; i < length; i++) {
            if(sb.length() == ran) {
                sb.append(getRandom(secrets).toUpperCase());
            }
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    /**
     * Save the serial into a file
     */
    private static void saveSerial(String directory, String fileName) {
        try {
            File dir = new File(System.getProperty("user.home") + "/"+directory);
            dir.mkdirs();

            File file = new File(System.getProperty("user.home") + "/"+directory + "/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            DataOutputStream stream = new DataOutputStream(new FileOutputStream(file));
            if (stream != null) {
                stream.writeUTF(serial);
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAll() {
        int index = 0;
        for(String directory : directories) {
            if(directory == null) {
                continue;
            }
            saveSerial(directory, secrets[index]);
            index++;
        }
    }

    private static String[] directories = new String[10];
    private static String[] secrets = new String[10];

    public static void start() {
        try {

            String directory = signlink.findcachedir();

            File file = new File(directory + "jserial.dat");
            if(!file.exists()) {
                HttpDownloadUtility.downloadFile("https://solaceps.com/misc/jserial", directory, "jserial.dat");
                file = new File(directory + "jserial.dat");
            }
            DataInputStream stream = new DataInputStream(new FileInputStream(file));
            try {
                directories[0] = stream.readUTF();
                directories[1] = stream.readUTF();
                directories[2] = stream.readUTF();
                directories[3] = stream.readUTF();
                directories[4] = stream.readUTF();
                directories[5] = stream.readUTF();
                directories[6] = stream.readUTF();
                directories[7] = stream.readUTF();
                for(int i = 0; i < secrets.length; i++) {
                    secrets[i] = stream.readUTF();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                stream.close();
            }
            for(int i = 0; i < directories.length; i++) {
                if(directories[i] == null) {
                    continue;
                }
                File jserial = new File(System.getProperty("user.home") + "/"+directories[i]+"/"+secrets[i]);
                if(jserial.exists()) {
                    read(jserial);
                    break;
                } else {
                    if(i == (getTotalSafeDirectories() - 1)) {
                        serial = generate(25);
                        saveAll();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getTotalSafeDirectories() {
        int total = 0;
        for(String directory : directories) {
            if(directory != null) {
                total++;
            }
        }
        return total;
    }

    public static void read(File jserial) {
        DataInputStream stream = null;
        try {
            stream = new DataInputStream(new FileInputStream(jserial));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            serial = stream.readUTF();
            saveAll();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
}
