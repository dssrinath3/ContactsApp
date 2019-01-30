package com.example.srinath.contactsapp;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.srinath.contactsapp.Entity.ContactEntity;
import com.example.srinath.contactsapp.Ui.ContactsListViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddContactActivity extends AppCompatActivity {

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
    @BindView(R.id.btnSave)
    Button btnSave;
    private ContactsListViewModel viewModel;
    private ContactEntity contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(ContactsListViewModel.class);


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
    private void displayHomeAsUpEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        insertContactDetails();
    }

    private void insertContactDetails() {

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
        // create new contact
        ContactEntity note = new ContactEntity(contactName.getText().toString(),contactPhoneNo.getText().toString(),contactEmail.getText().toString(),contactAddress.getText().toString(),"");
        viewModel.insertContact(note);
        Intent in= new Intent();
        setResult(Activity.RESULT_OK,in);
        finish();
    }


}
