package com.example.srinath.contactsapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.srinath.contactsapp.Ui.ContactListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new ContactListFragment(), ContactListFragment.TAG)
                    .commitNow();
        }

    }

    @OnClick(R.id.fab)
    void onFabClick() {
        ContactListFragment fragment = (ContactListFragment) getSupportFragmentManager().findFragmentByTag(ContactListFragment.TAG);
        if (fragment != null) {
            //add contact
            fragment.addContactDetails();
        }
    }
}
