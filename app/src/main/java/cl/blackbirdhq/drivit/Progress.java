package cl.blackbirdhq.drivit;


import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;

public class Progress extends AppCompatActivity {
    private AdminSQLiteAPP data = new AdminSQLiteAPP(this);
    private SQLiteDatabase db;
    private static String TYPE;
    private TextView text3_1, text3_2, text3_3, text4_1, text4_2, text4_3, text5_1, text5_2, text5_3,
            text6_1, text6_2, text6_3, text1_1, text1_2, text1_3,text1_4, text2_2, text2_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        initializeComponent();
    }

    private void initializeComponent(){

        Bundle bundle = getIntent().getExtras();
        TYPE = bundle.getString("type").toUpperCase();
        if(TYPE.equals("C")){
            getSupportActionBar().setTitle(getString(R.string.title_activity_progress_clase_c));
        }
        try {
            db = data.getReadableDatabase();
            String class1 = "SELECT COUNT(*) FROM tests WHERE achieved = 1 AND modality = 'real' AND class ='" + TYPE + "'";
            String class2 = "SELECT COUNT(*) FROM tests WHERE achieved = 0 AND modality = 'real' AND class ='" + TYPE + "'";
            String class3 = "SELECT MAX(time) FROM tests WHERE achieved = 1 AND modality = 'real' AND class ='" + TYPE + "'";
            String class4 = "SELECT MIN(time) FROM tests WHERE achieved = 1 AND modality = 'real' AND class ='" + TYPE + "'";
            String class9 = "SELECT COUNT(*) FROM alternatives_tests INNER JOIN tests ON tests._id = alternatives_tests.tests_id WHERE alternatives_tests.right = 1 AND alternatives_tests.categories_id = 1 AND tests.class ='" + TYPE + "'";
            String class10 = "SELECT COUNT(*) FROM alternatives_tests INNER JOIN tests ON tests._id = alternatives_tests.tests_id WHERE alternatives_tests.right = 0 AND alternatives_tests.categories_id = 1 AND tests.class ='" + TYPE + "'";
            String class11 = "SELECT COUNT(*) FROM alternatives_tests INNER JOIN tests ON tests._id = alternatives_tests.tests_id WHERE alternatives_tests.right = 1 AND alternatives_tests.categories_id = 2 AND tests.class ='" + TYPE + "'";
            String class12 = "SELECT COUNT(*) FROM alternatives_tests INNER JOIN tests ON tests._id = alternatives_tests.tests_id WHERE alternatives_tests.right = 0 AND alternatives_tests.categories_id = 2 AND tests.class ='" + TYPE + "'";
            String class13 = "SELECT COUNT(*) FROM alternatives_tests INNER JOIN tests ON tests._id = alternatives_tests.tests_id WHERE alternatives_tests.right = 1 AND alternatives_tests.categories_id = 3 AND tests.class ='" + TYPE + "'";
            String class14 = "SELECT COUNT(*) FROM alternatives_tests INNER JOIN tests ON tests._id = alternatives_tests.tests_id WHERE alternatives_tests.right = 0 AND alternatives_tests.categories_id = 3 AND tests.class ='" + TYPE + "'";
            String class15 = "SELECT COUNT(*) FROM alternatives_tests INNER JOIN tests ON tests._id = alternatives_tests.tests_id WHERE alternatives_tests.right = 1 AND alternatives_tests.categories_id = 4 AND tests.class ='" + TYPE + "'";
            String class16 = "SELECT COUNT(*) FROM alternatives_tests INNER JOIN tests ON tests._id = alternatives_tests.tests_id WHERE alternatives_tests.right = 0 AND alternatives_tests.categories_id = 4 AND tests.class ='" + TYPE + "'";

            Cursor cursor [] = {db.rawQuery(class1,null), db.rawQuery(class2,null),db.rawQuery(class3,null),db.rawQuery(class4,null),
                    db.rawQuery(class9,null),db.rawQuery(class10,null),db.rawQuery(class11,null),db.rawQuery(class12,null),
                    db.rawQuery(class13,null),db.rawQuery(class14,null),db.rawQuery(class15,null),db.rawQuery(class16,null)};
            for (int i = 0; i < cursor.length; i ++){
                cursor[i].moveToFirst();
            }

            File f = getApplicationContext().getDatabasePath("aplications.db");
            long dbSize = f.length();
            System.out.println("accccccca" + (dbSize/1024) + "KB");

            text1_1 = (TextView) findViewById(R.id.text1_1);
            text1_1.setText(getString(R.string.progress2) + " " + (cursor[0].getInt(0) + cursor[1].getInt(0)));

            text1_2 = (TextView) findViewById(R.id.text1_2);
            text1_2.setText(getString(R.string.progress3) + " " + cursor[0].getInt(0));

            text1_3 = (TextView) findViewById(R.id.text1_3);
            text1_3.setText(getString(R.string.progress4) + " " + cursor[1].getInt(0));

            text1_4 = (TextView) findViewById(R.id.text1_4);
            int totalAux = 0;
            if(cursor[0].getInt(0) != 0){
                totalAux = cursor[0].getInt(0) * 100 / (cursor[0].getInt(0) + cursor[1].getInt(0));
            }
            text1_4.setText(totalAux + "%");
            if(totalAux <= 50){
                text1_4.setTextColor(getResources().getColor(R.color.errorAnswer));
            }
            /*tiempo*/

            if(cursor[3].getCount() > 0) {
                text2_1 = (TextView) findViewById(R.id.text2_1);
                long minutes = (TimeUnit.MILLISECONDS.toMinutes(cursor[3].getInt(0)) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(cursor[3].getInt(0))));
                long seconds = (TimeUnit.MILLISECONDS.toSeconds(cursor[3].getInt(0)) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(cursor[3].getInt(0))));
                double secondsToMinutes = seconds / 60.0;
                DecimalFormat df = new DecimalFormat("#0.#");
                text2_1.setText(getString(R.string.progress7) + " " + df.format(secondsToMinutes + minutes) + " " +getString(R.string.progress9));

                text2_2 = (TextView) findViewById(R.id.text2_2);
                minutes = (TimeUnit.MILLISECONDS.toMinutes(cursor[2].getInt(0)) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(cursor[2].getInt(0))));
                seconds = (TimeUnit.MILLISECONDS.toSeconds(cursor[2].getInt(0)) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(cursor[2].getInt(0))));
                secondsToMinutes = seconds / 60.0;
                text2_2.setText(getString(R.string.progress8) + " " + df.format(secondsToMinutes + minutes) + " " + getString(R.string.progress9));
            }


            /*Conocimientos Legales y reglamentarios*/
            text3_1 = (TextView) findViewById(R.id.text3_1);
            text3_2 = (TextView) findViewById(R.id.text3_2);
            text3_3 = (TextView) findViewById(R.id.text3_3);
            calcPercent(cursor[4], cursor[5], text3_1, text3_2, text3_3);
            /*Conducta vial */
            text4_1 = (TextView) findViewById(R.id.text4_1);
            text4_2 = (TextView) findViewById(R.id.text4_2);
            text4_3 = (TextView) findViewById(R.id.text4_3);
            calcPercent(cursor[6],cursor[7], text4_1, text4_2, text4_3);
            /* Conocimientos mecánica básica*/
            text5_1 = (TextView) findViewById(R.id.text5_1);
            text5_2 = (TextView) findViewById(R.id.text5_2);
            text5_3 = (TextView) findViewById(R.id.text5_3);
            calcPercent(cursor[8], cursor[9], text5_1, text5_2, text5_3);
            /*Seguridad Vial*/
            text6_1 = (TextView) findViewById(R.id.text6_1);
            text6_2 = (TextView) findViewById(R.id.text6_2);
            text6_3 = (TextView) findViewById(R.id.text6_3);
            calcPercent(cursor[10], cursor[11], text6_1, text6_2, text6_3);

        }catch (Exception e){
            System.out.println(e);
        }finally {
            db.close();
        }
    }

    public void calcPercent(Cursor correct, Cursor incorrect, TextView text1, TextView text2, TextView text3){
        if(correct.getInt(0) == 0 || incorrect.getInt(0) == 0){
            if(correct.getInt(0) == 0 && incorrect.getInt(0) != 0){
                text1.setText("100%");
                text2.setText("0%");
            }else if(correct.getInt(0) != 0 && incorrect.getInt(0) == 0){
                text1.setText("0%");
                text2.setText("100%");
            }else if(correct.getInt(0) == 0 && incorrect.getInt(0) == 0){
                text1.setText("0%");
                text2.setText("0%");
            }
            text3.setText((incorrect.getInt(0) + correct.getInt(0)) + "");
        }else{
            int total =incorrect.getInt(0)+correct.getInt(0);
            int percentIncorrect =incorrect.getInt(0)*100/total;
            text1.setText(percentIncorrect + "%");
            text2.setText((100 - percentIncorrect) + "%");
            text3.setText(total + "");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_progress, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch(item.getItemId()){
            case R.id.btnHelp:
                //i = new Intent(this, Setting.class);
                //startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }





}


