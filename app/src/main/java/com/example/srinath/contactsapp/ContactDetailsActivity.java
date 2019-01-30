package com.example.srinath.contactsapp;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.srinath.contactsapp.Entity.ContactEntity;
import com.example.srinath.contactsapp.Ui.ContactsListViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.contact_name)
    EditText contactName;
    @BindView(R.id.contact_phone_no)
    EditText contactPhoneNo;
    @BindView(R.id.contact_email)
    EditText contactEmail;
    @BindView(R.id.contact_address)
    EditText contactAddress;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    @BindView(R.id.btnDelete)
    Button btnDelete;
    private ContactsListViewModel viewModel;
    private ContactEntity contact;
     private int contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(ContactsListViewModel.class);

        getContactDetails();
        setSupportActionBar(toolbar);
        displayHomeAsUpEnabled();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });


    }

    private void getContactDetails() {

        try {
            contactId = getIntent().getIntExtra("contactData",0);
            //fetch contact details
            contact = viewModel.getContact(contactId);
            if (contact!=null){
                contactName.setText(contact.getName());
                contactPhoneNo.setText(contact.getMobileno());
                contactEmail.setText(contact.getEmailid());
                contactAddress.setText(contact.getAddress());
            }


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void displayHomeAsUpEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @OnClick({R.id.btnEdit, R.id.btnDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnEdit:
                updateContactDetails();
                break;
            case R.id.btnDelete:
                deleteContact();
                break;
        }
    }

    private void deleteContact() {
        viewModel.deleteContact(contact);
        Intent in= new Intent();
        setResult(Activity.RESULT_OK,in);
        finish();
    }

    private void updateContactDetails() {
        if (TextUtils.isEmpty(contactName.getText().toString())) {
            Toast.makeText(this, getString(R.string.dialog_title_enter_contact), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(contactPhoneNo.getText().toString())) {
            Toast.makeText(this, getString(R.string.dialog_title_enter_contact_phone), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(contactEmail.getText().toString())) {
            Toast.makeText(this, getString(R.string.dialog_title_enter_contact_email), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(contactAddress.getText().toString())) {
            Toast.makeText(this, getString(R.string.dialog_title_enter_contact_address), Toast.LENGTH_SHORT).show();
            return;
        }

        // updating contact
        if ( contact != null) {

            contact.setId(contact.getId());
            contact.setName(contactName.getText().toString());
            contact.setMobileno(contactPhoneNo.getText().toString());
            contact.setEmailid(contactEmail.getText().toString());
            contact.setAddress(contactAddress.getText().toString());

            viewModel.updateContact(contact);
            Intent in= new Intent();
            setResult(Activity.RESULT_OK,in);
            finish();
        }
    }
}
