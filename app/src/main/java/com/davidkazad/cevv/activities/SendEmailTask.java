package com.davidkazad.cevv.activities;

import android.os.AsyncTask;
import android.util.Log;
public class SendEmailTask extends AsyncTask<Void, Void, Void> {

    private OnCompleteListener listener;
    private String subject;
    private String body;
    private String sendToEmail;

    public SendEmailTask addOnClompleteListener(OnCompleteListener listener) {
        this.listener = listener;
        return this;
    }

    public SendEmailTask(String subject, String body, String sendToEmail) {
        this.subject = subject;
        this.body = body;
        this.sendToEmail = sendToEmail;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i("Email sending", "sending start");
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            /*GmailSender sender = new GmailSender("ustawibtc@gmail.com", "ilovesmag");
            //subject, body, sender, to
            sender.sendMail(subject,
                    body,
                    "no-reply@okfood.com",
                    sendToEmail);*/

            Log.i("Email sending", "send");
            if (listener!=null){
                listener.onComplete();
            }
        } catch (Exception e) {
            if (listener!=null) {
                listener.onFail(e);
            }
            Log.i("Email sending", "cannot send");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        listener.onComplete();
    }

    public interface OnCompleteListener {
        void onComplete();
        void onFail(Exception e);
    }
}

