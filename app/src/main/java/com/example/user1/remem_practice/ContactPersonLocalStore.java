package com.example.user1.remem_practice;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user1 on 2016-04-25.
 */
public class ContactPersonLocalStore {
    public static final String SP_CONTACT = "contactDetails";

    SharedPreferences userLocalDatabase;
    public ContactPersonLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_CONTACT,0);
    }



    public void storeUserData(Person person){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putInt("ID",person.returnID());
        spEditor.putInt("idPassword",person.returnIdPassword());
        spEditor.putString("name",person.returnName());
        spEditor.putString("surname",person.returnSurname());
        spEditor.putString("sex",person.returnSex());
        spEditor.putString("telNumber",person.returnTelNumber());
        spEditor.commit();
    }
    public Person getLoggedInUser(){
        int ID = userLocalDatabase.getInt("ID", -1);
        int idPassword = userLocalDatabase.getInt("idPassword",-1);
        String name = userLocalDatabase.getString("name","");
        String surname = userLocalDatabase.getString("surname","");
        String sex = userLocalDatabase.getString("sex","");
        String telNumber = userLocalDatabase.getString("telNumber","");
        Person storePerson = new Person(ID,idPassword,name,surname,sex,telNumber);

        return storePerson;
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
