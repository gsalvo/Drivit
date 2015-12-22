package cl.blackbirdhq.drivit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RealModality extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_modality);
        Bundle bundle = getIntent().getExtras();
        System.out.println(bundle.getString("type"));
    }

    
}
