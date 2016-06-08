package ufcg.embedded.miniprojeto.toolbox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

import ufcg.embedded.miniprojeto.models.Product;

/**
 * Created by treinamento-09 on 08/06/16.
 */
public class FruitDB {
    private SQLiteDatabase dbLite;

    public FruitDB(Context context) {
        DBCore dbCore = new DBCore(context);
        dbLite = dbCore.getWritableDatabase();
    }

    public void insert(Product product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("product_url", product.getProduct_url());

        dbLite.insert("product", null, values);
    }

    public void update(Product product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());

        dbLite.update("product", values, "_id = ?", new String[]{"" + product.getId()});
    }

    public void delete(Product product) {
        dbLite.delete("product", "_id = " + product.getId(), null);
    }

    public void deleteAll() {
        dbLite.delete("product", null, null);
        dbLite.execSQL("delete from sqlite_sequence where name = 'product'");
    }

    public List<Product> getAll() {
        List<Product> product_list = new ArrayList<Product>();
        String[] columns = new String[]{"_id", "name", "product_url"};
        Cursor cursor = dbLite.query("product", columns, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Product product = new Product();
                product.setId(cursor.getInt(0));
                product.setName(cursor.getString(1));
                product.setProduct_url(cursor.getString(2));
                product_list.add(product);
            } while (cursor.moveToNext());
        }
        return product_list;
    }
}
