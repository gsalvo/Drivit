package cl.blackbirdhq.drivit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import javax.net.ssl.SSLContext;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;
import cl.blackbirdhq.drivit.helpers.JSONParser;

public class PresentationClassB extends AppCompatActivity {
    private AdminSQLiteAPP data = new AdminSQLiteAPP(this);
    private SQLiteDatabase db;
    private ProgressDialog mDialog;
    private LoadQuestion loadQuestion;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation_class_b);
        mDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                loadQuestion.cancel(true);
            }
        });

    }

    public void goTest(View view){
        loadQuestion = new LoadQuestion();
        loadQuestion.execute();
    }

    class LoadQuestion extends AsyncTask<String, String, String>{
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
                JSONArray jsonArray = jsonParser.makeHttpRequest("http://blackbirdhq.cl/selectQuestionClassB.php");
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
                Intent i = new Intent(PresentationClassB.this, Question.class);
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

