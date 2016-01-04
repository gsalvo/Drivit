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

import java.io.File;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;

public class Progress extends AppCompatActivity {
    private AdminSQLiteAPP data = new AdminSQLiteAPP(this);
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        initializeComponent();
    }

    private void initializeComponent(){
        TabHost tabs = (TabHost) findViewById(R.id.tabHost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("tab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("", ContextCompat.getDrawable(this, R.drawable.ic_car_24dp));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("", ContextCompat.getDrawable(this, R.drawable.ic_motorbike_24dp));
        tabs.addTab(spec);
        tabs.setCurrentTab(0);

        TabWidget tabWidget = tabs.getTabWidget();
        for(int i=0; i < tabWidget.getChildCount(); i++) {
            tabWidget.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.actionBarColor));
            tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tab_selector);
        }


        try {
            System.out.println("hola");
            db = data.getReadableDatabase();
            //CLASS B
            String classB1 = "SELECT COUNT(*), time FROM tests WHERE achieved = 1 AND modality = 'real' AND class ='B'";
            String classB2 = "SELECT COUNT(*) FROM tests WHERE achieved = 0 AND modality = 'real' AND class ='B'";

            String classB3 = "SELECT COUNT(*), time FROM tests WHERE achieved = 1 AND modality = 'special' AND class ='B'";
            String classB4 = "SELECT COUNT(*) FROM tests WHERE achieved = 0 AND modality = 'special' AND class ='B'";

            String classB5 = "SELECT COUNT(*), time FROM tests WHERE achieved = 1 AND modality = 'timeAttack' AND class ='B'";
            String classB6 = "SELECT COUNT(*) FROM tests WHERE achieved = 0 AND modality = 'timeAttack' AND class ='B'";

            String classB7 = "SELECT COUNT(*), time FROM tests WHERE achieved = 1 AND modality = 'survival' AND class ='B'";
            String classB8 = "SELECT COUNT(*) FROM tests WHERE achieved = 0 AND modality = 'survival' AND class ='B'";

            String classB9 = "SELECT COUNT(*) FROM alternatives_tests INNER JOIN tests ON tests._id = alternatives_tests.tests_id WHERE alternatives_tests.right = 1 AND alternatives_tests.categories_id = 1 AND tests.class ='B'";
            String classB10 = "SELECT COUNT(*) FROM alternatives_tests INNER JOIN tests ON tests._id = alternatives_tests.tests_id WHERE alternatives_tests.right = 0 AND alternatives_tests.categories_id = 1 AND tests.class ='B'";

            String classB11 = "SELECT COUNT(*) FROM alternatives_tests INNER JOIN tests ON tests._id = alternatives_tests.tests_id WHERE alternatives_tests.right = 1 AND alternatives_tests.categories_id = 2 AND tests.class ='B'";
            String classB12 = "SELECT COUNT(*) FROM alternatives_tests INNER JOIN tests ON tests._id = alternatives_tests.tests_id WHERE alternatives_tests.right = 0 AND alternatives_tests.categories_id = 2 AND tests.class ='B'";

            String classB13 = "SELECT COUNT(*) FROM alternatives_tests INNER JOIN tests ON tests._id = alternatives_tests.tests_id WHERE alternatives_tests.right = 1 AND alternatives_tests.categories_id = 3 AND tests.class ='B'";
            String classB14 = "SELECT COUNT(*) FROM alternatives_tests INNER JOIN tests ON tests._id = alternatives_tests.tests_id WHERE alternatives_tests.right = 0 AND alternatives_tests.categories_id = 3 AND tests.class ='B'";

            String classB15 = "SELECT COUNT(*) FROM alternatives_tests INNER JOIN tests ON tests._id = alternatives_tests.tests_id WHERE alternatives_tests.right = 1 AND alternatives_tests.categories_id = 4 AND tests.class ='B'";
            String classB16 = "SELECT COUNT(*) FROM alternatives_tests INNER JOIN tests ON tests._id = alternatives_tests.tests_id WHERE alternatives_tests.right = 0 AND alternatives_tests.categories_id = 4 AND tests.class ='B'";

            Cursor cursorB [] = {db.rawQuery(classB1,null), db.rawQuery(classB2,null),db.rawQuery(classB3,null),db.rawQuery(classB4,null),
                                db.rawQuery(classB5,null),db.rawQuery(classB6,null),db.rawQuery(classB7,null),db.rawQuery(classB8,null),
                                db.rawQuery(classB9,null),db.rawQuery(classB10,null),db.rawQuery(classB11,null),db.rawQuery(classB12,null),
                                db.rawQuery(classB13,null),db.rawQuery(classB14,null),db.rawQuery(classB15,null),db.rawQuery(classB16,null)};
            File f = getApplicationContext().getDatabasePath("aplications.db");
            long dbSize = f.length();

            System.out.println("chao: " + (dbSize/1024) + "KB");


        }catch (Exception e){
            System.out.println("falla");


        }finally {
            db.close();
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


