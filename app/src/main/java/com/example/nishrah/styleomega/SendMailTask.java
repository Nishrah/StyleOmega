package com.example.nishrah.styleomega;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@SuppressWarnings("rawtypes")
public class SendMailTask extends AsyncTask {

    private ProgressDialog statusDialog;
    private Activity sendMailActivity;

    Properties mailServerProperties;
    Session getMailSession;
    MimeMessage generateMailMessage;

    public SendMailTask(Activity activity) {
        sendMailActivity = activity;

    }

    protected void onPreExecute() {
        statusDialog = new ProgressDialog(sendMailActivity);
        statusDialog.setMessage("Getting ready...");
        statusDialog.setIndeterminate(false);
        statusDialog.setCancelable(false);
        statusDialog.show();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object doInBackground(Object... args) {
        try {
            publishProgress("Processing input....");

            String toEmail = args[0].toString();
            String firstName = args[1].toString();
            String emailSubject = args[2].toString();
            String emailMsg= args[3].toString();

            // setup Mail Server Properties..
            mailServerProperties = System.getProperties();
            mailServerProperties.put("mail.smtp.port", "587");
            mailServerProperties.put("mail.smtp.auth", "true");
            mailServerProperties.put("mail.smtp.starttls.enable", "true");

            publishProgress("Preparing mail message....");

            // get Mail Session..
            getMailSession = Session.getDefaultInstance(mailServerProperties, null);
            generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.setFrom(new InternetAddress("nishrah.work@gmail.com", "nishrah.work@gmail.com"));
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            generateMailMessage.setSubject(emailSubject);
            String emailBody = "Hey " + firstName + ",<br>"+ emailMsg +"<br><br>Regards,<br>StyleOmega team.";
            generateMailMessage.setContent(emailBody, "text/html");

            publishProgress("Sending email....");

            // get Session and Send mail"
            Transport transport = getMailSession.getTransport("smtp");
            transport.connect("smtp.gmail.com", "nishrah.work@gmail.com", "APIIT@6449");
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();

            publishProgress("Email Sent.");

        } catch (Exception e) {
            publishProgress(e.getMessage());
            Log.e("SendMailTask", e.getMessage(), e);
        }

        return null;
    }

    @Override
    public void onProgressUpdate(Object... values) {
        statusDialog.setMessage(values[0].toString());

    }

    @Override
    public void onPostExecute(Object result) {
        statusDialog.dismiss();
    }


}