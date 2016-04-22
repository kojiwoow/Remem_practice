package com.example.user1.remem_practice;

import java.util.ArrayList;

/**
 * Created by user1 on 2016-04-22.
 */
public class Person {
    private int ID, idPassword;
    private String name;
    private String surname;
    private String sex;
    private String telNumber;
    private Person contactPerson;
    private ArrayList<medicineTimetables> timetable = new ArrayList<medicineTimetables>();
    /**
     * constructor
     */
    Person(){

    }
    /**
     * 2nd constructor
     * @param id
     * @param name
     * @param surname
     * @param sex
     * @param telNumber
     */
    Person(int id, int idPassword,String name, String surname, String sex, String telNumber){
        this.ID = id;
        this.idPassword = idPassword;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.telNumber = telNumber;
    }

    /**
     *
     * @param id
     */
    public void setID(int id){
        this.ID = id;
    }

    /**
     *
     * @param idPassword
     */
    public void setIdPassword(int idPassword){
        this.idPassword = idPassword;
    }


    /**
     *
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     *
     * @param surname
     */
    public void setSurname(String surname){
        this.surname = surname;
    }

    /**
     *
     * @param sex
     */
    public void setSex(String sex){
        this.sex = sex;
    }

    /**
     *
     * @param telNumber
     */
    public void setTelNumber(String telNumber){
        this.telNumber = telNumber;
    }

    public void setContactPerson(Person contactPerson){
        this.contactPerson = contactPerson;
    }

    /**
     *
     * @return
     */
    public int returnID(){
        return this.ID;
    }

    /**
     *
     * @return
     */
    public int returnIdPassword(){
        return this.idPassword;
    }


    /**
     *
     * @return
     */
    public String returnName(){
        return this.name;
    }

    /**
     *
     * @return
     */
    public String returnSurname(){
        return this.surname;
    }

    /**
     *
     * @return
     */
    public String returnSex(){
        return this.sex;
    }

    /**
     *
     * @return
     */
    public String returnTelNumber(){
        return this.telNumber;
    }

    /**
     *
     * @return
     */
    public Person returnContactPerson(){
        return this.contactPerson;
    }

    /**
     *
     * @return
     */
    public medicineTimetables returnTimetable(int index){
        return this.timetable.get(index);
    }

    public ArrayList<medicineTimetables> returnArrayListOfTimetables(){
        return this.timetable;
    }
}
