package cl.blackbirdhq.drivit;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
    }

    private void initializeComponents() {
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
            case R.id.sendMessage:
                String[] TO = {"contacto@blackbirdhq.cl"};
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Comentarios aplicación Drivit");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Gracias por tus comentarios :)"));
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "Debe utilizar un cliente de correo electrónico :/", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goPresentationClassB(View view){
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
