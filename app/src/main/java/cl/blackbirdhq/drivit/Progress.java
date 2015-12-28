package cl.blackbirdhq.drivit;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabWidget;

public class Progress extends AppCompatActivity {


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


