package cl.blackbirdhq.drivit;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;

public class Question extends AppCompatActivity {
    SQLiteDatabase bd;
    Cursor cursor;
    AdminSQLiteAPP admin = new AdminSQLiteAPP(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        //Bundle bundle = getIntent().getExtras();
        //TextView test = (TextView) findViewById(R.id.test);
        //test.setText(bundle.getString("type"));
        //System.out.println("el tipo de ensayo es "+ bundle.getString("type"));

        /*initializeComoponent();
        bd = admin.getReadableDatabase();
        cursor = bd.rawQuery("SELECT question FROM questions", null);
        System.out.println("la cantidad es "+cursor.getCount());
        TextView test = (TextView) findViewById(R.id.test);
        cursor.moveToFirst();
        test.setText(cursor.getString(0));*/
    }

    public void initializeComoponent(){
        ImageButton btnPrev, btnNext;
        btnPrev = (ImageButton) findViewById(R.id.btnPrev);
        btnNext = (ImageButton) findViewById(R.id.btnNext);


        StructureQuestion fragmentTest = new StructureQuestion();
        FragmentManager manager = getFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentFragment, fragmentTest);
        transaction.commit();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StructureQuestion Ft = new StructureQuestion();
                TextView text = (TextView) findViewById(R.id.text);
                text.setText("paso");
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.contentFragment, Ft, "A");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text = (TextView) findViewById(R.id.text);
                text.setText("volvio");
                FragmentManager manager = getFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                if(manager.getBackStackEntryCount() > 0) {
                    manager.popBackStack();
                    fragmentTransaction.commit();
                }else{
                    text.setText("esta en el ultimo");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
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
}
