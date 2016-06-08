package ufcg.embedded.miniprojeto.toolbox;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by treinamento-09 on 08/06/16.
 */
public class DBCore extends SQLiteOpenHelper{
    private static final String NAME_BD = "SHOP";
    private static final int VERSION_BD = 1;

    public DBCore(Context context) {
        super(context, NAME_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table product(_id integer primary key autoincrement, name text not null, product_url text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table product");
        onCreate(db);
    }
}
