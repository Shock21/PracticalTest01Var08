package practicaltest01var08.eim.systems.cs.pub.ro.practicaltest01var08;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var08MainActivity extends AppCompatActivity {

    private final static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;

    Button top_left = null;
    Button top_right = null;
    Button bottom_left = null;
    Button bottom_right = null;
    Button center = null;
    Button navigate = null;
    EditText Mytext = null;
    int numberOfTry;
    int number_succes;
    int number_reject;
    boolean isStarted = false;

    private IntentFilter intentFilter = new IntentFilter();

    ButtonClickListener listener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.top_left:
                    Mytext.setText(Mytext.getText().toString() + top_left.getText().toString() + ", ");
                    break;
                case R.id.top_right:
                    Mytext.setText(Mytext.getText().toString() + top_right.getText().toString() + ", ");
                    break;
                case R.id.bottom_left:
                    Mytext.setText(Mytext.getText().toString() + bottom_left.getText().toString() + ", ");
                    break;
                case R.id.bottom_right:
                    Mytext.setText(Mytext.getText().toString() + bottom_right.getText().toString() + ", ");
                    break;
                case R.id.center:
                    Mytext.setText(Mytext.getText().toString() + center.getText().toString() + ", ");
                    break;
                case R.id.navigate_button:
                    numberOfTry++;
                    if(numberOfTry > 3 && isStarted == false) {
                        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var08Service.class);
                        intent.putExtra("MyText", Mytext.getText().toString());
                        getApplicationContext().startService(intent);
                        isStarted = true;
                    }
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var08SecondaryActivity.class);
                    String Content = Mytext.getText().toString();
                    intent.putExtra("TheText", Content);
                    startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var08_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        top_left = (Button)findViewById(R.id.top_left);
        top_left.setOnClickListener(listener);
        top_right = (Button)findViewById(R.id.top_right);
        top_right.setOnClickListener(listener);
        bottom_left = (Button)findViewById(R.id.bottom_left);
        bottom_left.setOnClickListener(listener);
        bottom_right = (Button)findViewById(R.id.bottom_right);
        bottom_right.setOnClickListener(listener);
        center = (Button)findViewById(R.id.center);
        center.setOnClickListener(listener);
        navigate = (Button)findViewById(R.id.navigate_button);
        navigate.setOnClickListener(listener);


        intentFilter.addAction(Intent.ACTION_SEND);

        Mytext = (EditText)findViewById(R.id.MyText);

        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey("MyText")) {
                Mytext.setText(savedInstanceState.getString("MyText"));
            }
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("MyText", Mytext.getText().toString());
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState.containsKey("MyText")) {
            Mytext.setText(savedInstanceState.getString("left_count"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "This activity returned with result " + resultCode, Toast.LENGTH_SHORT).show();
            if(resultCode == RESULT_CANCELED)
                number_reject++;
            if(resultCode == RESULT_OK)
                number_succes++;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practical_test01_var08_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}
