package com.example.srinath.contactsapp.Ui;


import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srinath.contactsapp.Adapter.ContactAdapter;
import com.example.srinath.contactsapp.AddContactActivity;
import com.example.srinath.contactsapp.ContactDetailsActivity;
import com.example.srinath.contactsapp.Entity.ContactEntity;
import com.example.srinath.contactsapp.R;
import com.example.srinath.contactsapp.Utils.Constant;
import com.example.srinath.contactsapp.Utils.MyDividerItemDecoration;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * srinath 30-01-2018.
 */
public class ContactListFragment extends Fragment implements ContactAdapter.NotesAdapterListener  {


    public static final String TAG = ContactListFragment.class.getSimpleName();
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.txt_empty_notes_view)
    TextView noContactsView;
    private ContactsListViewModel viewModel;
    private ContactAdapter mAdapter;
    Unbinder unbinder;

    public ContactListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(ContactsListViewModel.class);
        callAdapter();
    }

    private void callAdapter() {
        mAdapter = new ContactAdapter(getActivity(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.getContacts().observe(this, new Observer<List<ContactEntity>>() {
            @Override
            public void onChanged(@Nullable List<ContactEntity> contacts) {
                mAdapter.submitList(contacts);
                toggleEmptyContacts(contacts.size());
            }
        });
    }



    private void toggleEmptyContacts(int size) {
        if (size > 0) {
            noContactsView.setVisibility(View.GONE);
        } else {
            noContactsView.setVisibility(View.VISIBLE);
        }
    }

    //edit and delete contact details
    @Override
    public void onClick(int contactId, int position) {
        Intent in = new Intent(getActivity(),ContactDetailsActivity.class);
        in.putExtra("contactData",contactId);
        startActivityForResult(in, Constant.REQUEST_CODE_EDIT);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.REQUEST_CODE_EDIT && resultCode == Activity.RESULT_OK){
            callAdapter();
        }
        if (requestCode == Constant.REQUEST_CODE_ADD && resultCode == Activity.RESULT_OK){
            callAdapter();
        }
    }

    public void addContactDetails() {
        Intent in = new Intent(getActivity(), AddContactActivity.class);
        startActivityForResult(in,Constant.REQUEST_CODE_ADD);
    }
}
