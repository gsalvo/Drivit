package cl.blackbirdhq.drivit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;
import cl.blackbirdhq.drivit.helpers.JSONParser;

public class SpecialModalityCategory extends AppCompatActivity {
    private String type;
    private static String MODALITY;
    private static String CATEGORY;
    private AdminSQLiteAPP data = new AdminSQLiteAPP(this);
    private SQLiteDatabase db;
    private ProgressDialog mDialog;
    private LoadQuestion loadQuestion;
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_modality_category);
        initializeComponent();
    }

    private void initializeComponent() {

        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");
        CATEGORY = bundle.getString("category");
        MODALITY = bundle.getString("modality");
        mDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                loadQuestion.cancel(true);
            }
        });
        TextView questionText = (TextView) findViewById(R.id.questionText);
        switch (CATEGORY){
            case "0":
                getSupportActionBar().setTitle(getString(R.string.cate1));
                questionText.setText(getString(R.string.specialText1));
                break;
            case "1":
                getSupportActionBar().setTitle(getString(R.string.cate2));
                questionText.setText(getString(R.string.specialText2));
                break;
            case "2":
                getSupportActionBar().setTitle(getString(R.string.cate3));
                questionText.setText(getString(R.string.specialText3));
                break;
            case "3":
                getSupportActionBar().setTitle(getString(R.string.cate4));
                questionText.setText(getString(R.string.specialText4));
                break;
        }
        if(type.equals("c")){
            //imagen
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.img_motorbike);
        }

    }

    public void goTest(View view){
        loadQuestion = new LoadQuestion();
        loadQuestion.execute();
    }

    class LoadQuestion extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute(){
            mDialog.setMessage("Cargando las preguntas.");
            mDialog.setIndeterminate(false);
            mDialog.setCancelable(true);
            mDialog.show();
        }
        @Override
        protected String doInBackground(String... params){
            String state = "go";
            db = data.getWritableDatabase();
            data.reloadDBTest(db);
            try{
                JSONParser jsonParser = new JSONParser();
                JSONArray jsonArray = null;
                if(type.equals("b")){
                    switch (CATEGORY){
                        case "0":
                            jsonArray = jsonParser.makeHttpRequest("http://blackbirdhq.cl/selectQuestionClassBSpecial0.php");
                            break;
                        case "1":
                            jsonArray = jsonParser.makeHttpRequest("http://blackbirdhq.cl/selectQuestionClassBSpecial1.php");
                            break;
                        case "2":
                            jsonArray = jsonParser.makeHttpRequest("http://blackbirdhq.cl/selectQuestionClassBSpecial2.php");
                            break;
                        case "3":
                            jsonArray = jsonParser.makeHttpRequest("http://blackbirdhq.cl/selectQuestionClassBSpecial3.php");
                            break;
                    }
                }else if (type.equals("c")){
                    switch (CATEGORY){
                        case "0":
                            jsonArray = jsonParser.makeHttpRequest("http://blackbirdhq.cl/selectQuestionClassCSpecial0.php");
                            break;
                        case "1":
                            jsonArray = jsonParser.makeHttpRequest("http://blackbirdhq.cl/selectQuestionClassCSpecial1.php");
                            break;
                        case "2":
                            jsonArray = jsonParser.makeHttpRequest("http://blackbirdhq.cl/selectQuestionClassCSpecial2.php");
                            break;
                        case "3":
                            jsonArray = jsonParser.makeHttpRequest("http://blackbirdhq.cl/selectQuestionClassCSpecial3.php");
                            break;
                    }
                }
                for(int i = 0; i < jsonArray.length();i++){

                    JSONObject JSONQuestion = jsonArray.getJSONObject(i);
                    db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (" + JSONQuestion.get("id") + ", '" + JSONQuestion.get("question") + "','" + JSONQuestion.get("image") + "'," + JSONQuestion.get("categories_id") + ")");
                    JSONArray JSONal = (JSONArray) JSONQuestion.get("alternatives");

                    for(int j = 0; j < JSONal.length(); j++){
                        JSONObject alternative = JSONal.getJSONObject(j);
                        db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (" + alternative.get("id") + ", '" + alternative.get("alternative") + "'," + alternative.get("right") + ", " + JSONQuestion.get("id") + ")");
                    }
                }
            }catch (Exception e){
                System.out.print(e);
                state = "stop";
            }

            db.execSQL("INSERT INTO categories(_id, name, special) values (1,'conocimientos legales y reglamentarias',0)");
            db.execSQL("INSERT INTO categories(_id, name, special) values (2,'conducta vial',0)");
            db.execSQL("INSERT INTO categories(_id, name, special) values (3,'conocimientos mecánica básica',0)");
            db.execSQL("INSERT INTO categories(_id, name, special) values (4,'seguridad vial',1)");


            return state;
        }
        @Override
        protected void onPostExecute(String result){
            if(result.equals("go")){
                Intent i = new Intent(SpecialModalityCategory.this, Question.class);
                i.putExtra("type", type);
                i.putExtra("modality", MODALITY);
                startActivity(i);
                mDialog.dismiss();
            }else{
                mDialog.dismiss();
                alertDialog.setTitle("Error con la descarga del examen")
                        .setMessage("No se ha podido descargar el examen, verifique su conexión a internet.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Cierra el dialogo
                            }
                        })
                        .show();
            }
        }
    }
}
