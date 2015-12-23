package cl.blackbirdhq.drivit;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView title = (TextView)findViewById(R.id.textNamePresentation);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-MI.ttf");
        title.setTypeface(face);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch(item.getItemId()){
            case R.id.setting:
                i = new Intent(this, Setting.class);
                startActivity(i);
                return true;
            case R.id.progress:
                i = new Intent(this, Progress.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goPresentationClassB(View view){
        //Intent i = new Intent(this, PresentationClassB.class);
        Intent i = new Intent(this, Modalities.class);
        i.putExtra("type", "b");
        startActivity(i);
    }

    public void goPresentationClassC(View view){
        Intent i = new Intent(this, Modalities.class);
        i.putExtra("type", "c");
        startActivity(i);
    }

    public void goConditions(View view){
        Intent i = new Intent(this, Conditions.class);
        startActivity(i);
    }

    public void goGratitude(View view){
        Intent i = new Intent(this, Gratitude.class);
        startActivity(i);
    }
}
