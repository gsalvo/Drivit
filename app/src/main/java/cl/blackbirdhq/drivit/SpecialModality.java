package cl.blackbirdhq.drivit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent  i = new Intent(view.getContext(), SpecialModalityCategory.class);
                i.putExtra("type", type);
                i.putExtra("modality", "special");
                switch (position) {
                    case 0:
                        i.putExtra("category", "0");
                        break;
                    case 1:
                        i.putExtra("category", "1");
                        break;
                    case 2:
                        i.putExtra("category", "2");
                        break;
                    case 3:
                        i.putExtra("category", "3");
                        break;
                }
                startActivity(i);
            }
        });
    }


}
