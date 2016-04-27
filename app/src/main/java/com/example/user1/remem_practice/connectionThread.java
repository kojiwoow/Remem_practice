package com.example.user1.remem_practice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by user1 on 2016-04-12.
 */
public class connectionThread extends AsyncTask<Void, Void, Void>{
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    private static Socket client;
    private static ObjectOutputStream out;
    private static Person person;
    private static String[] MedicineList;
    ClientAnswersToServer answersToServersMessages = new ClientAnswersToServer(this);
    ObjectInputStream in;
    public static Context mContext = getContext();
    String message = null;
    ProgressDialog mProgress;
    String email,password;
    int ID;
    int id;



       public connectionThread(Context context){
           this.mContext = context;
         mProgress = new ProgressDialog(context);
        mProgress.setCancelable(false);
        mProgress.setTitle("Processing");
        mProgress.setMessage("Please wait...");

    }
    /*
    protected void onPreExecute() {
        super.onPreExecute();
        mProgress.show();
    }
*/

    @Override
    protected Void doInBackground(Void... params) {
        try{
            System.out.println("Connection to localhost in port 2004");
            out = new ObjectOutputStream(getSocket().getOutputStream());
            out.flush();
             in = new ObjectInputStream(getSocket().getInputStream());

            do{

               try{
                   message = (String)in.readObject();
                   answersToServersMessages.Answers(message);
                   person = answersToServersMessages.getPerson();
            //       if(message.equals("%medicineNames"))
                   MedicineList = answersToServersMessages.getMedicine();
                }catch(ClassNotFoundException classNot){
                    System.err.println("data received in unknown format");
                }
            }while(!message.equals("Log Out"));

        }catch(UnknownHostException unknownHost){
            System.err.println("You are trying to connect to an unknown host!");
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        finally{
			/*Closing connection*/
            try{
                in.close();
                out.close();
                getSocket().close();
            }catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
     //   mProgress.dismiss();
        super.onPostExecute(aVoid);

    }


    public void sendMessage(String msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    public static Socket getSocket() throws IOException {
        if(client == null){
            client = new Socket("10.0.2.2",2004);
            return client;
        }
        else {
            return client;
        }
    }
    private String[] divideMedicineNamesToList(String message2) {
		/* divide message where are list of name and save it into the list using in window*/
		/*firstly read count number of all names in message*/
        int count = 0;
        for(int i = 0; i < message2.length(); i++){
            if(message2.charAt(i) == ';')
                count++;
        }
		/*make list with the same size like count of names, and on position 0 give name "+add new medicine to DB"*/
        String [] list= new String[count+1];
        list[0] = "+add new medicine to DB";
        if(count > 1)
            for(int i =1; i < count; i++)
                list[i] = this.showStringNumber(message2, i);
        return list;

    }

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
    public static void setContext(Context mContext){
        mContext = mContext;
    }
    public void SignIn(String email,String password){
        mProgress.show();
        String sendMessage = "~SignMeIn;"+email+";"+password+";";
        sendMessage(sendMessage);
        mProgress.dismiss();
    }

    public void Register(String email,String password){

        String sendMessage = "$RegMe;"+email+";"+password+";";
        sendMessage(sendMessage);

    }

    public void Register_two(String name, String surname, String sex, String phoneNumber){
        id = answersToServersMessages.getIDpassword();
        String sendMessage ="#EditMe;"+id+";"+name+";"+surname+";"+sex+";"+phoneNumber+";";
        sendMessage(sendMessage);
    }

    public void searchMedicine(String medName){
        String sendMessage = "%SearchMedicine;"+medName;
        sendMessage(sendMessage);

    }

    public void requestMedicine(int id){
        this.ID = id;
        sendMessage("-infoTimetables;"+ID);
    }

    public static Context getContext() {
        return mContext;
    }

    public static Person getPerson(){
        return person;
    }

    public static String[] getMedicine(){
        return MedicineList;
    }
}


