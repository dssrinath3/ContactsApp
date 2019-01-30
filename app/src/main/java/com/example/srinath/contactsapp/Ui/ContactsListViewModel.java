package com.example.srinath.contactsapp.Ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.srinath.contactsapp.ContactsRepository;
import com.example.srinath.contactsapp.Entity.ContactEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ContactsListViewModel extends AndroidViewModel {
    private ContactsRepository mRepository;
    private LiveData<List<ContactEntity>> mContacts;

    public ContactsListViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ContactsRepository(application);
    }

    public LiveData<List<ContactEntity>> getContacts() {
        if (mContacts == null) {
            mContacts = mRepository.getmAllContacts();
        }

        return mContacts;
    }

    public ContactEntity getContact(int id) throws ExecutionException, InterruptedException {
        return mRepository.getContactById(id);
    }

    public void insertContact(ContactEntity contact) {
        mRepository.insertContact(contact);
    }

    public void updateContact(ContactEntity contact) {
        mRepository.updateContact(contact);
    }

    public void deleteContact(ContactEntity contact) {
        mRepository.deleteContact(contact);
    }


}
