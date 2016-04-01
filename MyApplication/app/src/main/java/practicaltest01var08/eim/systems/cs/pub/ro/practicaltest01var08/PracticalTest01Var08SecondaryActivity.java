package practicaltest01var08.eim.systems.cs.pub.ro.practicaltest01var08;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01Var08SecondaryActivity extends AppCompatActivity {

    Button button_ok = null;
    Button button_cancel = null;
    EditText SecondText = null;


    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ok:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancel:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var08_secondary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SecondText = (EditText)findViewById(R.id.total_acces);
        Intent intent = getIntent();
        if(intent != null && intent.getExtras().containsKey("TheText")) {
            String getContent = intent.getStringExtra("TheText");
            SecondText.setText(getContent);
        }

        button_ok = (Button)findViewById(R.id.ok);
        button_ok.setOnClickListener(buttonClickListener);
        button_cancel = (Button)findViewById(R.id.cancel);
        button_cancel.setOnClickListener(buttonClickListener);
    }

}
