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

public class ContactDetailsActivity_SaveContact extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private LayoutInflater inflater;

    private TextView txtContactName;
    private TextView txtCompanyName;
    private TextView txtTitleName;
    private TextView txtPhone_1;
    private ImageView imageViewPhone_1;
    private ImageView imageViewSMS_1;
    private LinearLayout linearLayoutVideoCall;
    private TextView txtEmail_1;

    private final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_contact_details_save_contact );
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );

        txtContactName = findViewById ( R.id.txtContactName );
        txtCompanyName = findViewById ( R.id.txtCompanyName );
        txtTitleName = findViewById ( R.id.txtTitleName );

        txtPhone_1 = findViewById ( R.id.txtPhone_1 );
        imageViewPhone_1 = findViewById ( R.id.imageViewPhone_1 );
        imageViewSMS_1 = findViewById ( R.id.imageViewSMS_1 );
        linearLayoutVideoCall = findViewById ( R.id.linearLayoutVideoCall );

        txtEmail_1 = findViewById ( R.id.txtEmail_1 );

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String name = bundle.getString ( "name" );
            String company = bundle.getString ( "company_name" );
            String title = bundle.getString ( "title_name" );
            String phone_1 = bundle.getString ( "phone_1" );
            String email_id1 = bundle.getString ( "email_id1" );

            txtContactName.setText ( name );
            if (company!="") {
                txtCompanyName.setVisibility ( View.VISIBLE );
                txtCompanyName.setText ( company );
            }

            if (title!="") {
                txtTitleName.setVisibility ( View.VISIBLE );
                txtTitleName.setText ( title );
            }

            if (phone_1!=null) {
                txtPhone_1.setVisibility ( View.VISIBLE );
                imageViewPhone_1.setVisibility ( View.VISIBLE );
                imageViewSMS_1.setVisibility ( View.VISIBLE );
                linearLayoutVideoCall.setVisibility ( View.VISIBLE );
                txtPhone_1.setText ( phone_1 );
            }

            if (email_id1!="") {
                txtEmail_1.setVisibility ( View.VISIBLE );
                txtEmail_1.setText ( email_id1 );
            }

            imageViewPhone_1.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick(View v) {
                    makePhoneCall();
                }
            } );

            imageViewSMS_1.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick(View v) {
                    String phone = txtPhone_1.getText ().toString ();
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

            txtEmail_1.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick(View v) {
                    String email = txtEmail_1.getText ().toString ();
                    Intent intentEmail = new Intent(Intent.ACTION_SENDTO);
                    intentEmail.setData(Uri.parse("mailto:"));
                    intentEmail.putExtra(Intent.EXTRA_EMAIL  , new String[] {email});
                    intentEmail.putExtra(Intent.EXTRA_SUBJECT, "");
                    startActivity(Intent.createChooser(intentEmail, ""));
                }
            } );

            linearLayoutVideoCall.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick(View v) {
                    String phone = txtPhone_1.getText ().toString ();
                    Uri data1 = Uri.parse(phone);

                    // ALL DATA OPENS THE APP, BUT NO CALL...
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
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ( ).inflate ( R.menu.menu_contact_details_save_contact, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId ( );

        if (id == R.id.action_home_screen) {
            Toast.makeText ( this, "Functionality not added", Toast.LENGTH_SHORT ).show ( );
            return true;
        } else if (id == R.id.action_voicemail) {
            Toast.makeText ( this, "Functionality not added", Toast.LENGTH_SHORT ).show ( );
            return true;
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

    private void createDeletePopupDialog() {

        builder = new AlertDialog.Builder ( ContactDetailsActivity_SaveContact.this );

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
                int save_contact_id = bundle.getInt ( "save_contact_id" );
                DatabaseHandler databaseHandler_deleteContact = new DatabaseHandler ( getApplicationContext () );
                Log.i ( "ContactDetails_Save", String.valueOf ( save_contact_id ) );
                databaseHandler_deleteContact.deleteContact ( save_contact_id );
                Intent intentDeleteContact = new Intent ( ContactDetailsActivity_SaveContact.this, MainActivity.class );
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
        String phone = txtPhone_1.getText ().toString ();
        if (ActivityCompat.checkSelfPermission ( ContactDetailsActivity_SaveContact.this, Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions ( ContactDetailsActivity_SaveContact.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL );
        } else {
            String number = "tel:" + phone;
            startActivity ( new Intent ( Intent.ACTION_CALL, Uri.parse ( number ) ) );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                makePhoneCall ();
            } else {
                FancyToast.makeText ( ContactDetailsActivity_SaveContact.this, "Permission Denied", FancyToast.ERROR, FancyToast.LENGTH_LONG, false ).show ();
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
