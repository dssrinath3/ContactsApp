package com.example.srinath.contactsapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.srinath.contactsapp.Dao.ContactDao;
import com.example.srinath.contactsapp.Database.ContactsDatabase;
import com.example.srinath.contactsapp.Entity.ContactEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ContactsRepository {
    private ContactDao mContactDao;
    private LiveData<List<ContactEntity>> mAllContacts;

    public ContactsRepository(Application application) {
        ContactsDatabase db = ContactsDatabase.getDatabase(application);
        mContactDao = db.contactDao();
        mAllContacts = mContactDao.getAllContact();
    }

    public LiveData<List<ContactEntity>> getmAllContacts() {
        return mAllContacts;
    }

    public ContactEntity getContactById(int contactId) throws ExecutionException, InterruptedException {
        return new getContactAsync(mContactDao).execute(contactId).get();
    }

    public void insertContact(ContactEntity contactEntity) {
        new insertContactsAsync(mContactDao).execute(contactEntity);
    }

    public void updateContact(ContactEntity contactEntity) {
        new updateContactAsync(mContactDao).execute(contactEntity);
    }

    public void deleteContact(ContactEntity contactEntity) {
        new deleteContactAsync(mContactDao).execute(contactEntity);
    }


    //tasks running on background thread
    private static class getContactAsync extends AsyncTask<Integer, Void, ContactEntity> {

        private ContactDao mContactDaoAsync;

        getContactAsync(ContactDao contactDao) {
            mContactDaoAsync = contactDao;
        }

        @Override
        protected ContactEntity doInBackground(Integer... ids) {
            return mContactDaoAsync.getContactById(ids[0]);
        }
    }

    private static class insertContactsAsync extends AsyncTask<ContactEntity, Void, Long> {

        private ContactDao mContactDaoAsync;

        insertContactsAsync(ContactDao contactDao) {
            mContactDaoAsync = contactDao;
        }

        @Override
        protected Long doInBackground(ContactEntity... contact) {
            long id = mContactDaoAsync.insert(contact[0]);
            return id;
        }
    }

    private static class updateContactAsync extends AsyncTask<ContactEntity, Void, Void> {

        private ContactDao mContactDaoAsync;

        updateContactAsync(ContactDao contactDao) {
            mContactDaoAsync = contactDao;
        }

        @Override
        protected Void doInBackground(ContactEntity... contact) {
            mContactDaoAsync.update(contact[0]);
            return null;
        }
    }

    private static class deleteContactAsync extends AsyncTask<ContactEntity, Void, Void> {

        private ContactDao mContactDaoAsync;

        deleteContactAsync(ContactDao contactDao) {
            mContactDaoAsync = contactDao;
        }

        @Override
        protected Void doInBackground(ContactEntity... contact) {
            mContactDaoAsync.delete(contact[0]);
            return null;
        }
    }

}
