package cl.blackbirdhq.drivit;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView title = (TextView)findViewById(R.id.textNamePresentation);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-MI.ttf");
        title.setTypeface(face);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void goPresentationClassB(View view){
        Intent i = new Intent(this, presentationClassB.class);
        startActivity(i);
    }

    public void goPresentationClassC(View view){
        Intent i = new Intent(this, presentationClassC.class);
        startActivity(i);
    }

    public void goConditions(View view){
        Intent i = new Intent(this, conditions.class);
        startActivity(i);
    }

    public void goGratitude(View view){
        Intent i = new Intent(this, gratitude.class);
        startActivity(i);
    }
}
