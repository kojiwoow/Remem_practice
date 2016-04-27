package com.example.user1.remem_practice;

/**
 * Created by user1 on 2016-04-22.
 */
import java.util.ArrayList;

/**
 *
 */

/**
 * @author juraj
 *
 */
public class medicineTimetables {

    private int ID;
    private medicine medicine;
    private String dateFrom, dateUntil, timeFrom, timeUntil;

    private ArrayList<notification> notificationsList= new ArrayList<notification>();
    private String active;


    /**
     * constructor
     */
    medicineTimetables(){

    }

    /**
     *
     * @return
     */
    public int returnIdOfTimetable(){
        return this.ID;
    }


    public void setIDOfTimetable(int id){
        this.ID = id;
    }

    public ArrayList<notification> returnArrayListOfNotificaations(){
        return notificationsList;
    }
    public void setActive(String s){
        this.active = s;
    }

    public String returnActive(){
        return this.active;
    }

    public void setMedicine(medicine m){
        this.medicine = m;
    }
    /**
     *
     * @return
     */
    public medicine returnMedicine(){
        return this.medicine;
    }

    /**
     *
     * @return
     */
    public String returnDate(String s){
        if(s.equals("from"))
            return this.dateFrom;
        else if(s.equals("to"))
            return this.dateUntil;

        return null;
    }

    public String returnTime(String s){
        if(s.equals("from"))
            return this.timeFrom;
        else if(s.equals("to"))
            return this.timeUntil;

        return null;
    }

    public void setDate(String s, String date){
        if(s.equals("from"))
            this.dateFrom = date;
        else if(s.equals("to"))
            this.dateUntil = date;
    }

    public void setTime(String s, String time){
        if(s.equals("from"))
            this.timeFrom = time;
        else if(s.equals("to"))
            this.timeUntil = time;
    }



    public void setTimetableFromClient(String selectMedicine, int howOften, int numberOfPills, String Date, String time) {

        this.dateFrom = Date;
        this.timeFrom = time;
        String medicineName = this.divideSelecMedicine("name", selectMedicine);
        // System.out.println("medicine name is "+medicineName);
        String strength = this.divideSelecMedicine("strength", selectMedicine);
        // System.out.println("medicine strength is "+strength);
        OurDateClass ourTime = new OurDateClass();
        ourTime.setDateFromDateFormat(dateFrom);
        //System.out.println(dateFrom);
        ourTime.setTimeFromTimeformat(timeFrom);
        //System.out.println(timeFrom);
        this.countDatesToTakePills(howOften, numberOfPills, ourTime);
        this.dateUntil = ourTime.returnDate();
        this.timeUntil = ourTime.returnTime();


        medicine = new medicine();
        //medicine.setNameOfMedicine("medicina");
        medicine.setNameOfMedicine(medicineName);
        medicine.setStrength(strength);

    }

    private void countDatesToTakePills(int howOften, int numberOfPills, OurDateClass ourTime) {
        // TODO make notifications and count all times for notifications, save notifications to timetable

        for(int i =0; i < numberOfPills; i++){
            if(i==0)
                System.out.println(ourTime.returnAllDate());
            else{
                ourTime.plusHoursToDate(howOften);
                System.out.println(ourTime.returnAllDate());
                notificationsList.add(new notification(ourTime.returnDate(), ourTime.returnTime(), "new"));
            }
        }

    }

    private String divideSelecMedicine(String arg, String string) {
        StringBuilder s = new StringBuilder();

        if(arg.equals("name")){
            for(int i = 0;i < string.length(); i++)
                if(string.charAt(i) != '~'){
                    s.append(string.charAt(i));
                }
                else return s.toString();
        }
        else if(arg.equals("strength")){
            int c = 0;
            for(int i=0; i < string.length(); i++){
                if(string.charAt(i)== '~')
                    c++;
                else if(c==2)
                    s.append(string.charAt(i));
            }
        }
        return s.toString();
    }

}
