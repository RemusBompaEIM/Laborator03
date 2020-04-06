package ro.pub.cs.systems.eim.lab03.phonedialer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PhoneDialerActivity extends AppCompatActivity {
    private final int PERMISSION_REQUEST_CALL_PHONE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_phone_dialer);

        Integer []ids = {R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7,
                R.id.b8, R.id.b9, R.id.b0, R.id.starb, R.id.Diez};
        for(int id : ids) {
            Button button = (Button) findViewById(id);
            button.setOnClickListener(new ButtonListener());
        }

        ((ImageButton) findViewById(R.id.CallB)).setOnClickListener(new CallListener());
        ((ImageButton) findViewById(R.id.HangUP)).setOnClickListener(new HangupListener());
        ((ImageButton) findViewById(R.id.Backspace)).setOnClickListener(new BackspaceListener());
    }

    class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Button button = (Button)v;
            TextView enter_phone = (TextView)findViewById(R.id.enter_phone);
            enter_phone.append(((Button) v).getText());
        }
    }

    class BackspaceListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView enter_phone = (TextView)findViewById(R.id.enter_phone);
            String text = enter_phone.getText().toString();
            enter_phone.setText(text.toCharArray(), 0, text.length() - 1);
        }
    }

    class CallListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView enter_phone = (TextView)findViewById(R.id.enter_phone);
            String text = enter_phone.getText().toString();
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + text));
                startActivity(intent);
            }
        }
    }

    class HangupListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }
}
