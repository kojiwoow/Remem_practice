package com.example.user1.remem_practice;

import android.content.Context;

/**
 * Created by user1 on 2016-04-22.
 */
public class ClientAnswersToServer {
    private static String[] MedicineList;
    connectionThread client;
    private static int ID;
    PersonLocalStore ppl;
    String sendMessage;
    public static Person user = new Person();
    public static Person usersContactPerson;
    Person UsersPerson;
    Person UsersContactPerson;
    PersonLocalStore personLocalStore;

    private String name;
    private String surname;
    private String sex;
    private String telNumber;
    private static Context context = getContext();
    private static int IdPassword;
    /**
     * Constructor
     * using argument Client to easier sending messages
     * inside constructor is initialization of contact person in user
     * @param c
     */

    ClientAnswersToServer(connectionThread c ){
        this.client = c;
        user.setContactPerson(new Person());
        usersContactPerson = user.returnContactPerson();
    }
    ClientAnswersToServer(){

    }
    public static Context getContext(){
        return context;
    }

    public static void setContext(Context mContext){
        context = mContext;
    }

    public static OnSearchMedicineResponse onSearchMedicineResponse;
    public interface OnSearchMedicineResponse {
        void onResponse(String[] MedicineList);
    }

    /**
     * method calling in class Client inside endless loop for making desicions about received messages from server
     * @param serverMessage
     */
    public void Answers(String serverMessage){

        if(serverMessage.contains("Sign In~id;") || serverMessage.contains("#EditContactPersonDone;")){
			/*sign in with my ID when I receive it from the database using my mail and password	*/ //DONE
            ID = Integer.parseInt(this.showStringNumber(serverMessage, 1));
            client.sendMessage("-infoUser;"+ID);
        }

        else if(serverMessage.contains("-infoUser;")){
            this.fillPersonDataFromMessage(serverMessage);
            client.sendMessage("-infoContactPerson;"+ID);
        }
        else if(serverMessage.equals("User name exists"))
            System.out.println("User name is already exists");

        else if(serverMessage.contains("%medicineNames;")){
				/* divide medicine to list and reset the window with new list */ //DONE
            System.out.print(serverMessage);
            MedicineList = this.divideMedicineNamesToList(serverMessage);

            if (this.onSearchMedicineResponse != null) {
                this.onSearchMedicineResponse.onResponse(MedicineList);
            }
            //TODO switch layout for add medicine
        }

        else if(serverMessage.contains("Sign In~id;") || serverMessage.contains("#EditContactPersonDone;")){
			/*sign in with my ID when I receive it from the database using my mail and password	*/ //DONE
            ID = Integer.parseInt(this.showStringNumber(serverMessage, 1));
            client.sendMessage("-infoUser;"+ID);
        }

        else if(serverMessage.contains("registered;")){
				/* sign in after registration, take mail and password from message and sign in with the data in message*/ //DONE
            String mail = this.showStringNumber(serverMessage, 1);
            String password = this.showStringNumber(serverMessage, 2);
            sendMessage = "~SignMeIn;"+mail+";"+password+";";
            client.sendMessage(sendMessage);
        }

        else if(serverMessage.contains("-infoUser;")){
            this.fillPersonDataFromMessage(serverMessage);
            UsersPerson = getPerson();
            PersonLocalStore save = new PersonLocalStore(getContext());
            save.storeUserData(UsersPerson);
            client.sendMessage("-infoContactPerson;"+ID);
        }

        else if(serverMessage.contains("-infoContactPerson;")){
            this.fillContactPersonDataFromMessage(serverMessage);
            UsersContactPerson = getContact();
            ContactPersonLocalStore saved = new ContactPersonLocalStore(getContext());
            saved.storeUserData(UsersContactPerson);
            client.sendMessage("-infoTimetables;"+ID);
        }

        else if(serverMessage.contains("-infoTimetables;")){
				/*message form "-infoTimetables;IDOftimetable;dateFrom;timeFrom;dateUntil;timeUntil;active;"*/
            this.fillTimetableFromMessage(serverMessage);


        }

        else if(serverMessage.contains("-infoMedicine;")){
				/*message form "-infoMedicine;IDOftimetable;IdOfmedicine;nemaOfMedicine;StrengthOfMedicine;dayLimitOfMedicine;daysLimitOfMedicine"*/
            this.fillMedicineAndAddToTimetable(serverMessage);
        }

        else if(serverMessage.contains("-infoNotifications;")){
				/*message form "-infoNotifications;IdOftimetable;notificationDate;notificationTime;notificationStatus;"*/
            this.fillNotificationsAndAddToTimetable(serverMessage);
        }

        else if(serverMessage.contains("-infoUserDone;"))
            //TODO switch layout to main layout
            ;
        else if(serverMessage.contains("#EditMeDone;")){
            //TODO switch layout to layout for Edit(fill) Contact person Profile during the registration
        }
        else if(serverMessage.equals("You should registered"))
            //TODO make notification that the user is not registered yet
            serverMessage = "Log Out";

        else if(serverMessage.contains("~idUser;")){
             IdPassword = Integer.parseInt(this.showStringNumber(serverMessage,1));


            //TODO switch layout for Edit my Profile
        }
        else if(serverMessage.contains("*timetableCreated;")){
				/*read all notification from the last timetable of user and send messages of all notifications
				 message form ("!addNotification;timetableId;notificationDate;notificationTime;notificationStatus;")*/
            medicineTimetables timetable = user.returnTimetable(user.returnArrayListOfTimetables().size()-1);
            timetable.setIDOfTimetable(Integer.parseInt(this.showStringNumber(serverMessage, 2)));
            for(notification n : timetable.returnArrayListOfNotificaations()){
                sendMessage = "!addNotification;"+timetable.returnIdOfTimetable()+";"+n.returnNotificationDate()+";"+n.returnNotificationTime()+";"+n.returnNotificationStatus()+";";
                client.sendMessage(sendMessage);
            }

        }

    }

    /**`
     * method for fill notification received messeges from server and add them to right timetable inside Person.class
     * @param message2
     */
    private void fillNotificationsAndAddToTimetable(String message2) {
			/*message form "-infoNotifications;IdOftimetable;notificationDate;notificationTime;notificationStatus;"*/
        int idTimetable = Integer.parseInt(this.showStringNumber(message2, 1));
        notification n = new notification();
        n.setNotificationDate(this.showStringNumber(message2, 2));
        n.setNotificationTime(this.showStringNumber(message2, 3));
        n.setNotificationStatus(this.showStringNumber(message2, 4));

        for(medicineTimetables mtt : user.returnArrayListOfTimetables())
            if(mtt.returnIdOfTimetable() == idTimetable)//{
                mtt.returnArrayListOfNotificaations().add(n);
    }

    /**
     * method for fill medicine inside timetables from received message from server
     * @param message
     */
    private void fillMedicineAndAddToTimetable(String message) {
			/*message form "-infoMedicine;IDOftimetable;IdOfmedicine;nemaOfMedicine;StrengthOfMedicine;dayLimitOfMedicine;daysLimitOfMedicine"*/
        int idTimetable = Integer.parseInt(this.showStringNumber(message, 1));
        medicine medi = new medicine();
        medi.setID(Integer.parseInt(this.showStringNumber(message, 2)));
        medi.setNameOfMedicine(this.showStringNumber(message, 3));
        medi.setStrength(this.showStringNumber(message, 4));
        medi.setDayLimit(this.showStringNumber(message, 5));
        medi.setDaysLimit(this.showStringNumber(message, 6));

        for(medicineTimetables mtt : user.returnArrayListOfTimetables())
            if(mtt.returnIdOfTimetable() == idTimetable)//{
                mtt.setMedicine(medi);
    }

    /**
     * method for fill timetable in Person.class
     * @param message
     */
    private void fillTimetableFromMessage(String message) {
			/*message form "-infoTimetables;IDOftimetable;dateFrom;timeFrom;dateUntil;timeUntil;active;"*/
        medicineTimetables mtt = new medicineTimetables();
        mtt.setIDOfTimetable(Integer.parseInt(this.showStringNumber(message, 1)));
        mtt.setDate("from", this.showStringNumber(message, 2));
        mtt.setTime("from", this.showStringNumber(message, 3));
        mtt.setDate("to", this.showStringNumber(message, 4));
        mtt.setTime("to", this.showStringNumber(message, 5));
        mtt.setActive(this.showStringNumber(message, 6));
        user.returnArrayListOfTimetables().add(mtt);

    }

    /**
     * method for divide message from server and make from message list of medicine
     * @param message2
     * @return
     */
    private String[] divideMedicineNamesToList(String message2) {
			/* divide message where are list of name and save it into the list using in window*/
			/*firstly read count number of all names in message*/
        int count = 0;
        for(int i = 0; i < message2.length(); i++){
            if(message2.charAt(i) == ';')
                count++;
        }
			/*make list with the same size like count of names, and on position 0 give name "+add new medicine to DB"*/
        String [] list= new String[count];
        list[0] = "+add new medicine to DB";
        if(count > 1)
            for(int i =1; i < count; i++)
                list[i] = this.showStringNumber(message2, i);
        return list;

    }

    /**
     * method for fill Person.class from received message from server
     * @param message
     */
    private void fillPersonDataFromMessage(String message){
			/*message form "-infoUser;id;name;surname;sex;tel.number;"*/
        user.setID(Integer.parseInt(this.showStringNumber(message, 1)));
        //TODO add password ID if will sometimes the function to change password
        user.setName(this.showStringNumber(message, 2));
        user.setSurname(this.showStringNumber(message, 3));
        user.setSex(this.showStringNumber(message, 4));
        user.setTelNumber(this.showStringNumber(message, 5));
        user.setIdPassword(ID);
        PersonLocalStore save = new PersonLocalStore(getContext());
        save.storeUserData(user);

    }

    /**
     * method for fill Person.class inside the user atribute
     * @param message
     */
    private void fillContactPersonDataFromMessage(String message){
			/*message form "-infoContactPerson;id;name;surname;sex;tel.number;"*/
        usersContactPerson.setID(Integer.parseInt(this.showStringNumber(message, 1)));
        //TODO add password ID if will sometimes the function to change password
        usersContactPerson.setName(this.showStringNumber(message, 2));
        usersContactPerson.setSurname(this.showStringNumber(message, 3));
        usersContactPerson.setSex(this.showStringNumber(message, 4));
        usersContactPerson.setTelNumber(this.showStringNumber(message, 5));
        ContactPersonLocalStore saved = new ContactPersonLocalStore(getContext());
        saved.storeUserData(usersContactPerson);
    }

    /**
     * method is looking for the number of string in the message received from the client
     * @param s = message from client
     * @param count = number of need string
     * @return
     */
    private String showStringNumber(String s, int count){
        int countOfSeparator =0;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length() ; i++){
            if(s.charAt(i) == ';')
                countOfSeparator++;
            else if(s.charAt(i) != ';' && countOfSeparator == count){

                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

    public int getIDpassword(){
        return this.IdPassword;
    }

    public Person getPerson(){
        return user;
    }

    public Person getContact(){
        return usersContactPerson;
    }

    public static String[] getMedicine(){
        return MedicineList;
    }


}


