package ufcg.embedded.miniprojeto.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBCore extends SQLiteOpenHelper{
    private static final String NAME_BD = "SHOP";
    private static final int VERSION_BD = 1;

    public DBCore(Context context) {
        super(context, NAME_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE CUSTOMERS(customer_url text primary key not null, firstname text , lastname text );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE CUSTOMERS");
            onCreate(db);
        }
    }
}
