package com.example.srinath.contactsapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.srinath.contactsapp.Dao.ContactDao;
import com.example.srinath.contactsapp.Entity.ContactEntity;
import com.example.srinath.contactsapp.Entity.DateConverter;

@Database(entities = {ContactEntity.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class ContactsDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();

    private static ContactsDatabase INSTANCE;

    public static ContactsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactsDatabase.class, "contacts_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
