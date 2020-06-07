package com.infectdistrack.model;

import android.os.AsyncTask;
import android.util.Log;

import com.infectdistrack.presenter.NewUserController;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendingMailAsyncTask extends AsyncTask<String, Void, String> {

    private static final String TAG = "SendingMailAsyncTask";

    private NewUserController newUserController;
    private boolean isMailSent = true;

    public SendingMailAsyncTask(NewUserController newUserController) {
        this.newUserController = newUserController;
    }

    @Override
    protected String doInBackground(String... params) {
        //Creating properties
        Properties props = new Properties();
        props.put("mail.smtp.timeout", "30000");
        props.put("mail.smtp.connectiontimeout", "30000");

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        //Authenticating the passwordEntryField
        Session mSession = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the passwordEntryField
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Constants.EMAIL, Constants.PASSWORD);
                    }
                });

        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(mSession);
            //Setting sender address
            mm.setFrom(new InternetAddress(Constants.EMAIL));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(params[0]));
            //Adding subject
            mm.setSubject(params[1]);
            //Adding message
            mm.setText(params[2]);
            //Sending email
            Transport.send(mm);
            return "";
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: " + Log.getStackTraceString(e));
            isMailSent = false;
            return "Exception name : " + e.getClass().getName() + "\nException message : " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String excpetionInfo) {
        super.onPostExecute(excpetionInfo);
        newUserController.onMailSent(isMailSent);
    }
}