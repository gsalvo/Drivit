package cl.blackbirdhq.drivit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class PresentationClassB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation_class_b);
    }

    public void goTest(View view){
        Intent i = new Intent(this, Question.class);
        startActivity(i);
    }

}
