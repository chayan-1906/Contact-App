package com.example.contact30thoct;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView imageViewAddContact;

    private SearchView searchView;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List contactArrayList;
    //private ArrayAdapter<String> arrayAdapter;

    private TextView txtName;

    private TextView txtContactName;
    private TextView txtPhone_1;
    private TextView txtEmail_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );

        recyclerView = findViewById ( R.id.recyclerView );
        recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager ( new LinearLayoutManager ( this ) );

        searchView = findViewById ( R.id.searchView );

        txtName = findViewById ( R.id.txtName );

        txtContactName = findViewById ( R.id.txtContactName );
        txtPhone_1 = findViewById ( R.id.txtPhone_1 );
        txtEmail_1 = findViewById ( R.id.txtEmail_1 );

        contactArrayList = new ArrayList<String> ();
        DatabaseHandler databaseHandler = new DatabaseHandler ( this );

        /*Contact chayan = new Contact ( "Chayan", "9647100133", "chayan19062000@gmail.com" );
        Contact chandrima = new Contact ( "Chandrima", "9832800817", "chandrima1505@gmail.com" );
        Contact prosenjit = new Contact ( "Prosenjit", "9832740782", "prasenjitdaslalbagh@gmail.com");
        Contact abhijit = new Contact ( "Abhijit", "0123456789", "abhijitdhar@gmail.com" );
        Contact priyansh = new Contact ( "Priyansh", "018372832893", "im1706@gmail.com" );
        Contact swagato = new Contact ( "Swagato", "162193727829", "swagatobag@gmail.com" );

        databaseHandler.addContact ( chayan );
        databaseHandler.addContact ( chandrima );
        databaseHandler.addContact ( prosenjit );
        databaseHandler.addContact ( abhijit );
        databaseHandler.addContact ( priyansh );
        databaseHandler.addContact ( swagato );*/

        imageViewAddContact = findViewById ( R.id.imageViewAddContact );
        imageViewAddContact.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( MainActivity.this, SaveContactActivity.class );
                startActivity ( intent );
            }
        } );

        //Log.i ( "TAG", "DatabaseTable: " );
        List<Contact> contactList = databaseHandler.getAllContacts();
        for (Contact contact : contactList) {
            Log.i("TAG",  contact.getId () + " " + contact.getName() + " " + contact.getCompany () + " " + contact.getTitle () + " " + contact.getEmailId () + " " + databaseHandler.getCount () );
            contactArrayList.add(contact);
        }

        searchView.setOnQueryTextListener ( new SearchView.OnQueryTextListener ( ) {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Contact>contactSearchList =null;
                if (query != null || !query.equals ( "" )) {
                    Contact contact = null;
                    try {
                        contact = contactSearchList.get ( 0 );
                    } catch (Exception e) {
                        e.printStackTrace ();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        } );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            checkSelfPermission ( Manifest.permission.READ_CONTACTS ) != PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission ( Manifest.permission.WRITE_CONTACTS ) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions ( new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 1 );
        } else {
            getAndroidContact();
        }

        //Setup Adapter...
        recyclerViewAdapter = new RecyclerViewAdapter ( MainActivity.this, contactArrayList );
        recyclerView.setAdapter ( recyclerViewAdapter );
    }

    private void getAndroidContact() {
        Cursor cursor_Android_Contacts = null;
        ContentResolver contentResolver = getContentResolver ();
        try {
            cursor_Android_Contacts = contentResolver.query ( ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null );
        } catch (Exception ex) {
            ex.printStackTrace ( );
        }
        if (cursor_Android_Contacts.getCount () > 0) {
            while (cursor_Android_Contacts.moveToNext ( )) {
                Contact contact = new Contact (  );
                String contact_ID = cursor_Android_Contacts.getString ( cursor_Android_Contacts.getColumnIndex ( ContactsContract.Contacts._ID ) );
                String contact_display_name = cursor_Android_Contacts.getString ( cursor_Android_Contacts.getColumnIndex ( ContactsContract.Contacts.DISPLAY_NAME ) );

                contact.name = contact_display_name;
                int hasPhoneNumber = Integer.parseInt ( cursor_Android_Contacts.getString ( cursor_Android_Contacts.getColumnIndex ( ContactsContract.Contacts.HAS_PHONE_NUMBER ) ) );
                if (hasPhoneNumber > 0) {
                    Cursor phoneCursor = contentResolver.query ( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ",
                            new String[]{contact_ID},
                            null);
                    while (phoneCursor.moveToNext ()) {
                        String phoneNumber = phoneCursor.getString ( phoneCursor.getColumnIndex ( ContactsContract.CommonDataKinds.Phone.NUMBER ) );
                        contact.phoneNumber = phoneNumber;
                    }
                    phoneCursor.close ();
                }
                contactArrayList.add ( contact );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getAndroidContact ();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ( ).inflate ( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId ( );

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText ( this, "Settings", Toast.LENGTH_SHORT ).show ();
            return true;
        } else if (id == R.id.action_import_export_contacts) {
            Toast.makeText ( this, "Import/Export Contacts", Toast.LENGTH_SHORT ).show ();
            return true;
        }
        return super.onOptionsItemSelected ( item );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed ( );
        finish ();
    }

}