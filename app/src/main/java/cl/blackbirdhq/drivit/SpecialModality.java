package cl.blackbirdhq.drivit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SpecialModality extends AppCompatActivity {
    private String type;
    private static String MODALITY = "special";
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_modality);
        initializeComponent();
    }

    private void initializeComponent() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");

        listView = (ListView) findViewById(R.id.list_categories);
        List item = new ArrayList();
        item.add(getResources().getString(R.string.cate1));
        item.add(getResources().getString(R.string.cate2));
        item.add(getResources().getString(R.string.cate3));
        item.add(getResources().getString(R.string.cate4));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item_simple, item);
        listView.setAdapter(adapter);
    }


}
