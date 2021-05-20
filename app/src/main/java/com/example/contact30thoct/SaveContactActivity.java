package com.example.contact30thoct;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SaveContactActivity extends AppCompatActivity {

    private ImageView imageCancelSaveContact;
    private ImageView imageCheckSaveContact;
    private ImageView imageCheckUpdateContact;
    private ImageView imageCheckEditContact;
    public static EditText editTextName;
    private EditText editTextCompany;
    private EditText editTextTitle;
    private EditText editTextPhone_1;
    private EditText editTextEmail_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_save_contact );

        imageCancelSaveContact = findViewById ( R.id.imageCancelSaveContact );
        imageCheckSaveContact = findViewById ( R.id.imageCheckSaveContact );
        imageCheckUpdateContact = findViewById ( R.id.imageCheckUpdateContact );
        imageCheckEditContact = findViewById ( R.id.imageCheckEditContact );

        editTextName = findViewById ( R.id.editTextName );
        editTextCompany = findViewById ( R.id.editTextCompany );
        editTextTitle = findViewById ( R.id.editTextTitle );
        editTextPhone_1 = findViewById ( R.id.editTextPhone_1 );
        editTextEmail_1 = findViewById ( R.id.editTextEmail_1 );

        final DatabaseHandler databaseHandler = new DatabaseHandler ( this );
        //final Contact contact = new Contact ( editTextName.getText ().toString (), editTextPhone_1.getText ().toString (), editTextEmail_1.getText ().toString () );

        /* Update Contact... RecyclerViewAdapter -> ContactDetailsActivity_RecyclerView -> SaveContactActivity */
        Bundle bundle = getIntent ().getExtras ();
        Log.i ( "SaveContactActivity", "bundle= " + bundle );
        if (bundle != null) {
            String name = bundle.getString ( "editContact_name" );
            String company = bundle.getString ( "editContact_company" );
            String title = bundle.getString ( "editContact_title" );
            String phone = bundle.getString ( "editContact_phone" );
            String email = bundle.getString ( "editContact_email" );

            editTextName.setText ( name );
            editTextCompany.setText ( company );
            editTextTitle.setText ( title );
            editTextPhone_1.setText ( phone );
            editTextEmail_1.setText ( email );

            imageCheckSaveContact.setVisibility ( View.GONE );
            imageCheckEditContact.setVisibility ( View.GONE );
            imageCheckUpdateContact.setVisibility ( View.VISIBLE );
            imageCheckUpdateContact.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick(View v) {
                    editContact_recycler2save();
                }

                private void editContact_recycler2save() {
                    if (editTextName.getText ( ).toString ( ).isEmpty ( ) && editTextPhone_1.getText ( ).toString ( ).isEmpty ( ) && editTextEmail_1.getText ( ).toString ( ).isEmpty ( ))
                        FancyToast.makeText ( getApplicationContext ( ), "Contact is empty!!!", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false ).show ( );
                    else {
                        Intent intentContactDetails = new Intent ( SaveContactActivity.this, ContactDetailsActivity_SaveContact.class );
                        List<Contact> contactList = databaseHandler.getAllContacts ( );
                        int maxID = 0;
                        for (Contact contactId : contactList) {
                            Log.i ( "ContactDetails_Save", contactId.getId ( ) + " " + contactId.getName ( ) + " " + contactId.getCompany ( ) + " " + contactId.getTitle ( ) + " " + contactId.getPhoneNumber ( ) + " " + contactId.getEmailId ( ) );
                            if (maxID < contactId.getId ( ))
                                maxID = contactId.getId ( );
                        }
                        databaseHandler.updateContact ( maxID, editTextName.getText ( ).toString ( ), editTextCompany.getText ( ).toString ( ), editTextTitle.getText ( ).toString ( ), editTextPhone_1.getText ( ).toString ( ), editTextEmail_1.getText ( ).toString ( ) );
                        //Log.i ( "ContactDetails_Save", "Max ID: " + maxID );
                        intentContactDetails.putExtra ( "save_contact_id", maxID );

                        //USER-INPUT... NAME, COMPANY, TITLE...
                        if (!editTextName.getText ( ).toString ( ).isEmpty ( )) {
                            intentContactDetails.putExtra ( "name", editTextName.getText ( ).toString ( ) );
                        }
                        if (!editTextCompany.getText ( ).toString ( ).isEmpty ( )) {
                            intentContactDetails.putExtra ( "company_name", editTextCompany.getText ( ).toString ( ) );
                        }
                        if (!editTextTitle.getText ( ).toString ( ).isEmpty ( )) {
                            intentContactDetails.putExtra ( "title_name", editTextTitle.getText ( ).toString ( ) );
                        }

                        //USER-INPUT... PHONE_NUMBER_1...
                        if (!editTextPhone_1.getText ( ).toString ( ).isEmpty ( )) {
                            intentContactDetails.putExtra ( "phone_1", editTextPhone_1.getText ( ).toString ( ) );
                        }

                        //USER-INPUT... EMAIL_1...
                        if (!editTextEmail_1.getText ( ).toString ( ).isEmpty ( )) {
                            intentContactDetails.putExtra ( "email_id1", editTextEmail_1.getText ( ).toString ( ) );
                        }
                        startActivity ( intentContactDetails );
                        finish ( );
                    }
                }
            } );
        }

        imageCancelSaveContact.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                finish ();
            }
        } );

        imageCheckSaveContact.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                saveContact();
            }

            private void saveContact() {
                if (editTextName.getText ().toString ().isEmpty () && editTextPhone_1.getText ().toString ().isEmpty () && editTextEmail_1.getText ().toString ().isEmpty ())
                    FancyToast.makeText ( getApplicationContext (), "Contact is empty!!!", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false ).show ();
                else {
                    Intent intentContactDetails = new Intent( SaveContactActivity.this, ContactDetailsActivity_SaveContact.class );
                    Contact contact = new Contact ( editTextName.getText ().toString (), editTextCompany.getText ().toString (), editTextTitle.getText ().toString (), editTextPhone_1.getText ().toString (), editTextEmail_1.getText ().toString () );
                    databaseHandler.addContact ( contact );
                    List<Contact> contactList = databaseHandler.getAllContacts();
                    int maxID = 0;
                    for (Contact contactId : contactList) {
                        //Log.i("ContactDetails_Save",  contactId.getId () + " " + contactId.getName() + " " + contactId.getCompany () + " " + contactId.getTitle () + " " + contact.getEmailId () );
                        if (maxID < contactId.getId ())
                            maxID = contactId.getId ();
                    }
                    //Log.i ( "ContactDetails_Save", "Max ID: " + maxID );
                    intentContactDetails.putExtra ( "save_contact_id", maxID );

                    //USER-INPUT... NAME, COMPANY, TITLE...
                    if (!editTextName.getText ().toString ().isEmpty ()) {
                        intentContactDetails.putExtra ( "name", editTextName.getText ().toString () );
                    }
                    if (!editTextCompany.getText ().toString ().isEmpty ()) {
                        intentContactDetails.putExtra ( "company_name", editTextCompany.getText ().toString () );
                    }
                    if (!editTextTitle.getText ().toString ().isEmpty ()) {
                        intentContactDetails.putExtra ( "title_name", editTextTitle.getText ().toString () );
                    }

                    //USER-INPUT... PHONE_NUMBER_1...
                    if (!editTextPhone_1.getText ().toString ().isEmpty ()) {
                        intentContactDetails.putExtra ( "phone_1", editTextPhone_1.getText ( ).toString ( ) );
                    }

                    //USER-INPUT... EMAIL_1...
                    if (!editTextEmail_1.getText ().toString ().isEmpty ()) {
                        intentContactDetails.putExtra ( "email_id1", editTextEmail_1.getText ().toString () );
                    }
                    startActivity ( intentContactDetails );
                    finish ();
                }
            }
        } );
    }
}