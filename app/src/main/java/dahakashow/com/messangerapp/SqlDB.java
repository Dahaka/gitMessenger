package dahakashow.com.messangerapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Dahakashow on 12/18/2015.
 */
public class SqlDB extends ContextWrapper{

    SQLiteDatabase db;

    public SqlDB(Context base) {
        super(base);
        db=openOrCreateDatabase("MyDB", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS messages(message VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS username(username VARCHAR);");
    }

    public void insert(String message){
        db.execSQL("INSERT INTO messages VALUES('"+message+"')");

    }
    public void deleteAll(){
        db.execSQL("DELETE FROM messages;");
    }


    public void insert_username(String username){
        db.execSQL("DELETE FROM username;");
        db.execSQL("INSERT INTO username VALUES('"+username+"')");

    }



    public String read_username(){
        Cursor c=db.rawQuery("SELECT * FROM username",null);
        StringBuffer buffer=new StringBuffer();
        while (c.moveToNext()) {
            buffer.append(c.getString(0));

        }
        Log.v("username:",buffer.toString());
        return buffer.toString();
    }
}
