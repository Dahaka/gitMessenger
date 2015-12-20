package dahakashow.com.messangerapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Dahakashow on 12/18/2015.
 */
public class SqlDB extends ContextWrapper{

    SQLiteDatabase db;

    public SqlDB(Context base) {
        super(base);
        db=openOrCreateDatabase("MyDB", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS messages(message VARCHAR);");
    }

    public void insert(String message){
        db.execSQL("INSERT INTO messages VALUES('"+message+"')");

    }
    public void deleteAll(){
        db.execSQL("DELETE FROM messages;");
    }


}
