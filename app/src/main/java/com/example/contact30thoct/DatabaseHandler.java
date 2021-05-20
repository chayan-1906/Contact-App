package com.example.contact30thoct;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context ) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    //We create our table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //SQL - Structured Query Language
        /*
           create table _name(id, name, company, title, phone_number, email_id);
         */
        String CREATE_CONTACT_TABLE = "CREATE TABLE "
                + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_NAME + " TEXT,"
                + Util.KEY_COMPANY + " TEXT,"
                + Util.KEY_TITLE + " TEXT,"
                + Util.KEY_PHONE_NUMBER + " TEXT,"
                + Util.KEY_EMAIL_ID + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE); //creating our table
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});

        //Create a table again...
        onCreate(sqLiteDatabase);
    }

    /*CRUD = Create, Read, Update, Delete*/
    //Add Contact
    public void addContact(Contact contact) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put ( Util.KEY_NAME, contact.getName () );
        contentValues.put ( Util.KEY_COMPANY, contact.getCompany () );
        contentValues.put ( Util.KEY_TITLE, contact.getTitle () );
        contentValues.put ( Util.KEY_PHONE_NUMBER, contact.getPhoneNumber () );
        contentValues.put ( Util.KEY_EMAIL_ID, contact.getEmailId () );

        //Insert to row...
        sqLiteDatabase.insert(Util.TABLE_NAME, null, contentValues);
        Log.i("DatabaseHandler", "Added: " + contact.getId () + " " + contact.getName () + " " + contact.getPhoneNumber () + " " + contact.getEmailId () );
        sqLiteDatabase.close(); //closing db connection!
    }

    //Get a contact
    public Contact getContact(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(Util.TABLE_NAME,
                new String[]{ Util.KEY_ID,
                        Util.KEY_NAME,
                        Util.KEY_COMPANY,
                        Util.KEY_TITLE,
                        Util.KEY_PHONE_NUMBER,
                        Util.KEY_EMAIL_ID},
                Util.KEY_ID +"=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact("James", "213986", "abcd@gmail.com");
        contact.setId(Integer.parseInt(cursor.getString(0)));
        contact.setName(cursor.getString(1));
        contact.setCompany (cursor.getString(2));
        contact.setTitle (cursor.getString(3));
        contact.setPhoneNumber(cursor.getString(4));
        contact.setEmailId(cursor.getString (5));

        return contact;
    }

    //Get all Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        //Select all contacts
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(selectAll, null);

        //Loop through our data
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact("James", "213986", "abcd@gmail.com");
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setCompany (cursor.getString(2));
                contact.setTitle (cursor.getString(3));
                contact.setPhoneNumber(cursor.getString(4));
                contact.setEmailId(cursor.getString (5));

                //add contact objects to our list
                contactList.add(contact);
            }while (cursor.moveToNext());
        }
        return contactList;
    }

    //Update a single contact
    public int updateContact (int id, String name, String company, String title, String phone, String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase ();
        Contact contact = new Contact (  );
        Log.i("DBHandler",name + " " + company + " " + title + " " + phone + " " + email );
        ContentValues contentValues = new ContentValues (  );
        contentValues.put ( Util.KEY_NAME, name );
        contentValues.put ( Util.KEY_COMPANY, company );
        contentValues.put ( Util.KEY_TITLE, title );
        contentValues.put ( Util.KEY_PHONE_NUMBER, phone );
        contentValues.put ( Util.KEY_EMAIL_ID, email );

        //updating the specific row / contact...
        return sqLiteDatabase.update ( Util.TABLE_NAME, contentValues, Util.KEY_ID + "=?", new String[] {String.valueOf ( id )});
    }

    //Delete a single contact
    public void deleteContact (int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase ();
        sqLiteDatabase.delete ( Util.TABLE_NAME, Util.KEY_ID + "=?", new String[] {String.valueOf ( id )});
        sqLiteDatabase.close ();
    }

    //Get Contacts count
    public int getCount () {
        String countQuery = "SELECT * FROM " +Util.TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase ();
        Cursor cursor = sqLiteDatabase.rawQuery ( countQuery, null );

        return cursor.getCount ();
    }

}