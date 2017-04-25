package com.mou.inc.myapplication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;


public class Utilities {

    /**
     * String extra for a note's filename
     */
    public static final String EXTRAS_NOTE_FILENAME = "EXTRAS_NOTE_FILENAME";
    public static final String FILE_EXTENSION = ".bin";

    /**
     * Save a note on private storage of the app
     * @param context Application's context
     * @param note The note to be saved
     */
    public static boolean saveNote(Context context, Note note) {

        String fileName = String.valueOf(note.getDateTime()) + FILE_EXTENSION;

        FileOutputStream fos;
        ObjectOutputStream oos;

        try{
            fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(note);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            //tell user the note was saved!
            return false;
        }

        return true;
    }

    //testing some thing
    public static String getFileNandD(Context context, String fileName) {
        File dirFiles = context.getFilesDir();
        File file = new File(dirFiles, fileName);

        if (file.exists()) {
            return file.getAbsolutePath().toString();
        }else return "No File";
    }


        /**
         * Read all saved
         * @param context Application's context
         * @return ArrayList of Note
         */
    public static ArrayList<Note> getAllSavedNotes(Context context) {
        ArrayList<Note> notes = new ArrayList<>();

        File filesDir = context.getFilesDir();
        ArrayList<String> noteFiles = new ArrayList<>();
        List<String> customThing=new ArrayList<String>();
        String file0 = "";

        //add .bin files to the noteFiles list
        for(String file : filesDir.list()) {
            if(file.endsWith(FILE_EXTENSION)) {
                noteFiles.add(file);
            }
        }

        //read objects and add to list of notes
        FileInputStream fis;
        ObjectInputStream ois;

        for (int i = 0; i < noteFiles.size(); i++) {
            try{
                fis = context.openFileInput(noteFiles.get(i));
                ois = new ObjectInputStream(fis);

                notes.add((Note) ois.readObject());
                fis.close();
                ois.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }

        return notes;
    }

    /**
     * Loads a note file by its name
     * @param context Application's context
     * @param fileName Name of the note file
     * @return A Note object, null if something goes wrong!
     */
    public static Note getNoteByFileName(Context context, String fileName) {

        File file = new File(context.getFilesDir(), fileName);
        if(file.exists() && !file.isDirectory()) { //check if file  exist

            Log.v("UTILITIES", "File exist = " + fileName);


            FileInputStream fis;
            ObjectInputStream ois;

            try { //load the file
                fis = context.openFileInput(fileName);
                ois = new ObjectInputStream(fis);
                Note note = (Note) ois.readObject();
                fis.close();
                ois.close();

                return note;

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            return null;
        }
    }








   /* private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = new FileInputStream(filePath + fileNameWithExtension);

        // Path to the just created empty db
        String outFileName = OUTPUT_PATH + "/" + FILE_NAME;

        // Open the empty db as the output stream
        new File(outFileName).createNewFile();
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }*/





    public static boolean deleteFile(Context context, String fileName) {
        File dirFiles = context.getFilesDir();
        File file = new File(dirFiles, fileName);

        if(file.exists() && !file.isDirectory()) {
            return file.delete();
        }

        return false;
    }



}