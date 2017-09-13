package com.example.nishrah.styleomega.model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.example.nishrah.styleomega/databases/";
    private static String DB_NAME = "StyleOmega.db";
    private SQLiteDatabase myDatabase;

    private final Context myContext;

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;

        try {
            this.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.openDatabase();
    }

    public void createDatabase() throws IOException {

        boolean dbExist = checkDatabase();

        if(!dbExist){
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Error occured when copying database");
            }
        }
    }

    private boolean checkDatabase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }catch(SQLiteException e){  }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    private void copyDatabase() throws IOException{

        //Open your local db as the input stream
        InputStream myInputStream = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutputStream = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInputStream.read(buffer))>0){
            myOutputStream.write(buffer, 0, length);
        }

        //Close the streams
        myOutputStream.flush();
        myOutputStream.close();
        myInputStream.close();
    }

    public void openDatabase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if(myDatabase != null) {
            myDatabase.close();
        }
        super.close();
    }

    public void runSQL(String sql){
        myDatabase = this.getWritableDatabase();
        myDatabase.execSQL(sql);
    }

    public Cursor runSELECT(String sql){
        myDatabase = this.getReadableDatabase();
        Cursor c = myDatabase.rawQuery(sql,null);
        return c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
