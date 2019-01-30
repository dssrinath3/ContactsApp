package com.example.srinath.contactsapp.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.srinath.contactsapp.Entity.ContactEntity;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contacts ORDER BY id DESC")
    LiveData<List<ContactEntity>> getAllContact();

    @Query("SELECT * FROM contacts WHERE id=:id")
    ContactEntity getContactById(int id);

    @Query("SELECT * FROM contacts WHERE id=:id")
    LiveData<ContactEntity> getContact(int id);

    @Insert
    long insert(ContactEntity contact);

    @Update
    void update(ContactEntity contact);

    @Delete
    void delete(ContactEntity contact);
}
