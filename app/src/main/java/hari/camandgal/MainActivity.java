package hari.camandgal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set listeners
        findViewById(R.id.select_from_gal).setOnClickListener(this);
        findViewById(R.id.select_with_cam).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.select_from_gal:
                break;
            case R.id.select_with_cam:
                break;
        }
    }
}
