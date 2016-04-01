package practicaltest01var08.eim.systems.cs.pub.ro.practicaltest01var08;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Tiberiu on 4/1/2016.
 */
public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;

    String myText;



    public ProcessingThread(Context context, String TheText) {
        this.context = context;
        myText = TheText;
    }


    @Override
    public void run() {
        Log.d("[Processing Thread", "Thread was started!");
        while(isRunning) {
            sendMessage();
            sleep();
        }
        Log.d("[Processing Thread", "Thread has stopped");
    }

    private void sendMessage() {
        String[] resultString = myText.split(",");
        for(int i = 0; i < resultString.length; i++) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra("message", resultString[i]);
            context.sendBroadcast(intent);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}

