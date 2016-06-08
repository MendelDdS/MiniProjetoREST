package ufcg.embedded.miniprojeto.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ufcg.embedded.miniprojeto.models.Customer;

public class CustomerDB {
    private SQLiteDatabase dbLite;
    private final static String TABLE_NAME = "CUSTOMERS";

    public CustomerDB(Context context) {
        DBCore dbCore = new DBCore(context);
        dbLite = dbCore.getWritableDatabase();
    }

    public void insert(Customer customer) {
        ContentValues values = new ContentValues();
        values.put("firstname", customer.getFirstname());
        values.put("lastname", customer.getLastname());
        values.put("customer_url", customer.getCustomer_url());

        dbLite.insert(TABLE_NAME, null, values);
    }

    public void update(Customer customer) {
        ContentValues values = new ContentValues();
        values.put("firstname", customer.getFirstname());
        values.put("lastname", customer.getLastname());
        dbLite.update(TABLE_NAME, values, "customer_url = " + customer.getCustomer_url(), null);
    }

    public void delete(Customer customer) {
        dbLite.delete(TABLE_NAME, "customer_url = " + customer.getCustomer_url(), null);
    }

    public void deleteAll() {
        dbLite.delete(TABLE_NAME, null, null);
    }

    public List<Customer> getAll() {
        List<Customer> customer_list = new ArrayList<Customer>();
        String[] columns = new String[]{"customer_url", "firstname", "lastname"};
        Cursor cursor = dbLite.query(TABLE_NAME, columns, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Customer customer = new Customer();
                customer.setCustomer_url(cursor.getString(0));
                customer.setFirstname(cursor.getString(1));
                customer.setLastname(cursor.getString(2));
                customer_list.add(customer);
            } while (cursor.moveToNext());
        }
        return customer_list;
    }
}
