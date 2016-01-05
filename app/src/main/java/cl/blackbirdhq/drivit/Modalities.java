package cl.blackbirdhq.drivit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class Modalities extends AppCompatActivity {

    ListView listView;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modalities);
        initializeComponent();

    }

    private void initializeComponent() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");
        if(type.equals("c")){
            getSupportActionBar().setTitle(getString(R.string.title_activity_modalities_classC));
        }
        this.listView = (ListView) findViewById(R.id.list_item);
        List item = new ArrayList();
        item.add(new ItemModality(R.drawable.ic_progress_modality, "Progreso y registro", "Podrás revisar el progreso de los examenes y preguntas realizados"));
        item.add(new ItemModality(R.drawable.ic_real_modality,"Real","Realizarás un ensayo simulando las mismas condiciones en cuanto al tiempo, cantidad de preguntas y puntaje que el examen real"));
        item.add(new ItemModality(R.drawable.ic_special_modality,"Especializado","Realizarás un ensayo sólo con preguntas de una temática en particular para que puedas reforzarla"));
        item.add(new ItemModality(R.drawable.ic_timer_modality, "Contra el tiempo", "Realizarás un ensayo con una duración en tiempo definida por ti"));
        item.add(new ItemModality(R.drawable.ic_dead_modality, "Muerte Súbita", "El ensayo terminará cuando te equivoques en alguna pregunta o las hayas respondido todas de forma correcta"));
        this.listView.setAdapter(new ItemModalityAdapter(this, item));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                switch (position) {
                    case 0:
                        i = new Intent(view.getContext(), Progress.class);
                        i.putExtra("type", type);
                        startActivity(i);
                        break;
                    case 1:
                        i = new Intent(view.getContext(), RealModality.class);
                        i.putExtra("type", type);
                        i.putExtra("modality", "real");
                        startActivity(i);
                        break;
                    case 2:
                        i = new Intent(view.getContext(), SpecialModality.class);
                        i.putExtra("type", type);
                        i.putExtra("modality", "special");
                        startActivity(i);
                        break;
                    case 3:
                        i = new Intent(view.getContext(), TimeAttackModality.class);
                        i.putExtra("type", type);
                        i.putExtra("modality", "timeAttack");
                        startActivity(i);
                        break;
                    case 4:
                        i = new Intent(view.getContext(), SurvivalModality.class);
                        i.putExtra("type", type);
                        i.putExtra("modality", "survival");
                        startActivity(i);
                        break;
                }
            }
        });
    }
}
