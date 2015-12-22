package cl.blackbirdhq.drivit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;

public class TimeAttackModality extends AppCompatActivity {

    NumberPicker time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_attack_modality);
        Bundle bundle = getIntent().getExtras();
        System.out.println(bundle.getString("type"));
        time = (NumberPicker) findViewById(R.id.time);
        time.setMinValue(1);
        time.setMaxValue(35);

    }


}
