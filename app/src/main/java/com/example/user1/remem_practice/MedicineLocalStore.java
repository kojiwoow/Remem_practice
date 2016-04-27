package com.example.user1.remem_practice;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user1 on 2016-04-25.
 */
public class MedicineLocalStore {
    public static final String SP_MEDICINE = "medicineDetails";

    SharedPreferences medicalLocalDatabase;
    public MedicineLocalStore(Context context){
        medicalLocalDatabase = context.getSharedPreferences(SP_MEDICINE,0);
    }


    public void storeUserData(medicine Medicine){
        SharedPreferences.Editor spEditor = medicalLocalDatabase.edit();
        spEditor.putInt("ID",Medicine.returnIDOfMedicine());
        spEditor.putString("nameOfMedicine",Medicine.returnNameOfMedicine());
        spEditor.putString("Strength",Medicine.returnStrengthOfMedicine());
        spEditor.putString("DayLimit",Medicine.returnDayLimit());
        spEditor.putString("DaysLimit",Medicine.returnDaysLimit());
        spEditor.commit();
    }
    public medicine getLoggedInUser(){
        int ID = medicalLocalDatabase.getInt("ID", -1);
        String nameOfMedicine = medicalLocalDatabase.getString("nameOfMedicine", "");
        String Strength = medicalLocalDatabase.getString("Strength","");
        String DayLimit = medicalLocalDatabase.getString("DayLimit","");
        String DaysLimit = medicalLocalDatabase.getString("DaysLimit","");
        medicine storeMedicine = new medicine(ID,nameOfMedicine,Strength,DayLimit,DaysLimit);

        return storeMedicine;
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = medicalLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
