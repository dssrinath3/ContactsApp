package com.example.srinath.contactsapp.Adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.srinath.contactsapp.Entity.ContactEntity;
import com.example.srinath.contactsapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends ListAdapter<ContactEntity, ContactAdapter.MyViewHolder> {
    private static final String TAG = ContactAdapter.class.getSimpleName();



    private Context context;
    private NotesAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.contact_image)
        CircleImageView contactImage;
        @BindView(R.id.contact_name)
        TextView contactName;
        @BindView(R.id.contact_phone)
        TextView contactPhone;
        @BindView(R.id.timestamp)
        TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(getContact(getLayoutPosition()).getId(), getLayoutPosition());
                }
            });


        }
    }

    public ContactAdapter(Context context, NotesAdapterListener listener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ContactEntity contact = getContact(position);
        if (contact != null) {
            holder.contactImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_contact));
            holder.contactName.setText(contact.getName());
            holder.contactPhone.setText(contact.getMobileno());
            // Formatting and displaying timestamp
            holder.timestamp.setText(formatDate(contact.getTimestamp()));
        }
    }

    public ContactEntity getContact(int position) {
        return getItem(position);
    }

    private static final DiffUtil.ItemCallback<ContactEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ContactEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull ContactEntity oldNote, @NonNull ContactEntity newNote) {
                    return oldNote.getId() == newNote.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull ContactEntity oldNote, @NonNull ContactEntity newNote) {
                    return oldNote.getId() == newNote.getId() && oldNote.getName().equals(newNote.getName());
                }
            };


    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2019-01-30 00:15:42
     * Output: Jan 30
     */
    private String formatDate(Date timestamp) {
        try {
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(timestamp);
        } catch (Exception e) {
            // TODO - handle exception
            e.printStackTrace();
        }

        return "";
    }

    public interface NotesAdapterListener {
        void onClick(int noteId, int position);
    }
}
