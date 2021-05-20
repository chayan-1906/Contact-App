package com.example.contact30thoct;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

public class ContactDetailsActivity_RecyclerView extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private LayoutInflater inflater;

    private TextView txtContactName_recycler;
    private TextView txtCompanyName_recycler;
    private TextView txtTitleName_recycler;
    private TextView txtPhone_1_recycler;
    private ImageView imageViewPhone_1_recycler;
    private ImageView imageViewSMS_1_recycler;
    private LinearLayout linearLayoutVideoCall_recycler;
    private TextView txtEmail_1_recycler;

    private final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_contact_details_recycler_view );
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );

        txtContactName_recycler = findViewById ( R.id.txtContactName_recycler );
        txtCompanyName_recycler = findViewById ( R.id.txtCompanyName_recycler );
        txtTitleName_recycler = findViewById ( R.id.txtTitleName_recycler );

        txtPhone_1_recycler = findViewById ( R.id.txtPhone_1_recycler );
        imageViewPhone_1_recycler = findViewById ( R.id.imageViewPhone_1_recycler );
        imageViewSMS_1_recycler = findViewById ( R.id.imageViewSMS_1_recycler );
        linearLayoutVideoCall_recycler = findViewById ( R.id.linearLayoutVideoCall_recycler );

        txtEmail_1_recycler = findViewById ( R.id.txtEmail_1_recycler );

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString ( "recycler_details_name" );
        String company = bundle.getString ( "recycler2details_company" );
        String title = bundle.getString ( "recycler2details_title" );
        String phone_1 = bundle.getString ( "recycler2details_phone" );
        String email_id1 = bundle.getString ( "recycler2details_email" );

        txtContactName_recycler.setText ( name );
        if (company != "") {
            txtCompanyName_recycler.setVisibility ( View.VISIBLE );
            txtCompanyName_recycler.setText ( company );
        }
        if (title != "") {
            txtTitleName_recycler.setVisibility ( View.VISIBLE );
            txtTitleName_recycler.setText ( title );
        }
        Log.i ( "TAG", "phone_1" + phone_1 );
        if (!phone_1.isEmpty ()) {
            txtPhone_1_recycler.setVisibility ( View.VISIBLE );
            imageViewPhone_1_recycler.setVisibility ( View.VISIBLE );
            imageViewSMS_1_recycler.setVisibility ( View.VISIBLE );
            linearLayoutVideoCall_recycler.setVisibility ( View.VISIBLE );
            txtPhone_1_recycler.setText ( phone_1 );
        }
        if (email_id1 != "") {
            txtEmail_1_recycler.setVisibility ( View.VISIBLE );
            txtEmail_1_recycler.setText ( email_id1 );
        }

        imageViewPhone_1_recycler.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        } );

        imageViewSMS_1_recycler.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                String phone = txtPhone_1_recycler.getText ().toString ();
                Intent intentSMS = new Intent ( Intent.ACTION_VIEW );
                intentSMS.setData ( Uri.parse ( "smsto:" ) );
                intentSMS.setType("vnd.android-dir/mms-sms");
                intentSMS.putExtra("address", phone);
                intentSMS.putExtra("sms_body","");
                try {
                    startActivity(intentSMS);
                }
                catch(Exception e) {
                    e.printStackTrace ();
                }
            }
        } );

        txtEmail_1_recycler.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                String email = txtEmail_1_recycler.getText ().toString ();
                Intent intentEmail = new Intent(Intent.ACTION_SENDTO);
                intentEmail.setData(Uri.parse("mailto:"));
                intentEmail.putExtra(Intent.EXTRA_EMAIL  , new String[] {email});
                intentEmail.putExtra(Intent.EXTRA_SUBJECT, "");
                startActivity(Intent.createChooser(intentEmail, ""));
            }
        } );

        linearLayoutVideoCall_recycler.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                String phone = txtPhone_1_recycler.getText ().toString ();
                Uri data1 = Uri.parse(phone);

                /*String audioCallType = "vnd.android.cursor.item/com.google.android.apps.tachyon.phone.audio";
                String videoCallType = "vnd.android.cursor.item/com.google.android.apps.tachyon.phone";
                String mainGoogleDuoPackage = "com.google.android.apps.tachyon";
                String videoCallPackage = "com.google.android.apps.tachyon.ContactsVideoActionActivity"; // CALL_PRIVILEGE locked package appends '2'
                String audioCallPackage = "com.google.android.apps.tachyon.ContactsAudioActionActivity"; // CALL_PRIVILEGE locked package appends '2'

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(data1 , audioCallType);
                intent.setComponent(new ComponentName(mainGoogleDuoPackage, audioCallPackage));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage(audioCallPackage);
                startActivity(intent);*/
            }
        } );
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ( ).inflate ( R.menu.menu_contact_details_recycler_view, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId ( );

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home_screen) {
            Toast.makeText ( this, "Functionality not added", Toast.LENGTH_SHORT ).show ( );
            return true;
        } else if (id == R.id.action_voicemail) {
            Toast.makeText ( this, "Functionality not added", Toast.LENGTH_SHORT ).show ( );
            return true;
        } else if (id == R.id.action_editContact) {
            editContact();
        } else if (id == R.id.action_deleteContact) {
            createDeletePopupDialog();
        } else if (id == R.id.action_shareContact) {
            Toast.makeText ( this, "Functionality not added", Toast.LENGTH_SHORT ).show ( );
            return true;
        } else if (id == R.id.action_SIMCARD) {
            Toast.makeText ( this, "Functionality not added", Toast.LENGTH_SHORT ).show ( );
            return true;
        }
        return super.onOptionsItemSelected ( item );
    }

    private void editContact() {

        Bundle bundle = getIntent ().getExtras ();
        String name = bundle.getString ( "recycler_details_name" );
        String company = bundle.getString ( "recycler2details_company" );
        String title = bundle.getString ( "recycler2details_title" );
        String phone = bundle.getString ( "recycler2details_phone" );
        String email = bundle.getString ( "recycler2details_email" );
//        Log.i ( "ContactDetails_Recycler", id + " is updated" );
        Intent intentEditContact = new Intent ( ContactDetailsActivity_RecyclerView.this, SaveContactActivity.class );
        intentEditContact.putExtra ( "editContact_name", name );
        intentEditContact.putExtra ( "editContact_company", company );
        intentEditContact.putExtra ( "editContact_title", title );
        intentEditContact.putExtra ( "editContact_phone", phone );
        intentEditContact.putExtra ( "editContact_email", email );
        //startActivity ( intentEditContact );
        startActivityForResult ( intentEditContact, 100 );
    }

    private void createDeletePopupDialog() {

        builder = new AlertDialog.Builder ( ContactDetailsActivity_RecyclerView.this );

        inflater = LayoutInflater.from ( getApplicationContext () );
        View view = inflater.inflate ( R.layout.delete_confirmation_pop, null );

        Button conf_noButton = view.findViewById ( R.id.conf_noButton );
        Button conf_yesButton = view.findViewById ( R.id.conf_yesButton );

        builder.setView ( view );
        alertDialog = builder.create ( );
        alertDialog.show ( );

        conf_yesButton.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent ().getExtras ();
                int recycler_contact_id = bundle.getInt ( "recycler_contact_id" );
                DatabaseHandler databaseHandler_deleteContact = new DatabaseHandler ( getApplicationContext () );
                //Log.i ( "ContactDetails_Recycler", recycler_contact_id + " is deleted" );
                databaseHandler_deleteContact.deleteContact ( recycler_contact_id );
                Intent intentDeleteContact = new Intent ( ContactDetailsActivity_RecyclerView.this, MainActivity.class );
                startActivity ( intentDeleteContact );
                alertDialog.dismiss ( );
            }
        } );
        conf_noButton.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss ( );
            }
        } );
    }

    private void makePhoneCall() {
        String phone = txtPhone_1_recycler.getText ().toString ();
        if (ActivityCompat.checkSelfPermission ( ContactDetailsActivity_RecyclerView.this, Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions ( ContactDetailsActivity_RecyclerView.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL );
        } else {
            String number = "tel:" + phone;
            startActivity ( new Intent ( Intent.ACTION_CALL, Uri.parse ( number ) ) );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                makePhoneCall ();
            } else {
                FancyToast.makeText ( ContactDetailsActivity_RecyclerView.this, "Permission Denied", FancyToast.ERROR, FancyToast.LENGTH_LONG, false ).show ();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed ( );

        Intent intent = new Intent ( getApplicationContext (), MainActivity.class );
        startActivity ( intent );
        finish ();
    }

}
