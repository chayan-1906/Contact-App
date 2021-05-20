package com.example.contact30thoct;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Contact> contactList;

    public RecyclerViewAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.contact_row, parent, false );
        return new ViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, final int position) {
        Contact contact = contactList.get ( position ); // each contact object inside of our list...
        //Log.i ( "TAG", "36. Name: " + contact.getName () );
        viewHolder.txtName.setText ( contact.getName () );
    }

    @Override
    public int getItemCount() {
        return contactList.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;

        public ViewHolder(@NonNull View itemView) {
            super ( itemView );

            txtName = itemView.findViewById ( R.id.txtName );

            itemView.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition ();
                    Contact contact = contactList.get ( position );

                    seeDetails(contact);
                }
            } );
        }

        public void seeDetails(Contact contact) {
            Intent intent = new Intent ( context, ContactDetailsActivity_RecyclerView.class );
            intent.putExtra ( "recycler_contact_id", contact.getId () );
            intent.putExtra ( "recycler_details_name", contact.getName () );
            intent.putExtra ( "recycler2details_company", contact.getCompany () );
            intent.putExtra ( "recycler2details_title", contact.getTitle () );
            intent.putExtra ( "recycler2details_phone", contact.getPhoneNumber () );
            intent.putExtra ( "recycler2details_email", contact.getEmailId () );
            context.startActivity ( intent );
        }
    }
}
