package finaltest.nhutlv.sbiker.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import finaltest.nhutlv.sbiker.R;

/**
 * Created by NhutDu on 14/05/2017.
 */

public class ApprovedActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved);
        TextView textView = (TextView) findViewById(R.id.txt_approved);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String message = extras.getString("message");
            textView.setText(message);
        }
    }
}
