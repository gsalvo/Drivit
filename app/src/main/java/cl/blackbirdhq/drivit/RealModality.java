package cl.blackbirdhq.drivit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;
import cl.blackbirdhq.drivit.helpers.DrivitSingleton;

public class RealModality extends AppCompatActivity {
    private String type;
    private static String MODALITY = "real";
    private AdminSQLiteAPP data = new AdminSQLiteAPP(this);
    private SQLiteDatabase db;
    private ProgressDialog mDialog;
    private LoadQuestion loadQuestion;
    private AlertDialog.Builder alertDialog;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_modality);
        initializeComponent();
    }

    private void initializeComponent(){
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");
        mDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                loadQuestion.cancel(true);
            }
        });
        if(type.equals("c")){
            //imagen
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.img_motorbike);
            //texto pregunta
            TextView QuestionText = (TextView) findViewById(R.id.questionText);
            QuestionText.setText(R.string.mod1c);
        }
    }

    public static boolean connectivity(Context ctx) {
        boolean state = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for (int i = 0; i < 2; i++) {
            if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                state = true;
            }
        }
        return state;
    }

    public void goTest(View view){
        mDialog.setMessage(getString(R.string.msjeText2));
        mDialog.setIndeterminate(false);
        mDialog.setCancelable(false);
        mDialog.show();
        if(connectivity(getApplicationContext())){
            String url = "";
            if(type.equals("b")){
                url = "http://blackbirdhq.cl/selectQuestionClassB.php";
            }else if (type.equals("c")){
                url = "http://blackbirdhq.cl/selectQuestionClassC.php";
            }
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            jsonArray = response;
                            loadQuestion = new LoadQuestion();
                            loadQuestion.execute();
                            mDialog.setMessage(getString(R.string.msjeText8));
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mDialog.dismiss();
                            alertDialog.setTitle(getString(R.string.msjeTitle1))
                                    .setMessage(getString(R.string.msjeText1))
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //Cierra el dialogo
                                        }
                                    })
                                    .show();
                        }
                    }
            );
            DrivitSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
        }else{
            int cant = 0;
            try{
                db = data.getReadableDatabase();
                Cursor countData = db.rawQuery("SELECT count(*) FROM questions_types", null);
                countData.moveToFirst();
                cant = countData.getInt(0);
            }catch (Exception e){
                System.out.println(e);
            }finally {
                db.close();
            }
            if(cant> 0) {
                mDialog.setMessage(getString(R.string.msjeText8));
                loadQuestion = new LoadQuestion(true);
                loadQuestion.execute();
            }else{
                mDialog.dismiss();
                alertDialog.setTitle(getString(R.string.msjeTitle10))
                        .setMessage(getString(R.string.msjeText10))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Cierra el dialogo
                            }
                        })
                        .show();
            }
        }

    }

    private String parsingTest(boolean localData) {
        String result = "stop";
        try{
            db = data.getWritableDatabase();
            data.reloadDBTest(db);
            mDialog.setCancelable(true);
            if (!localData){
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject JSONQuestion = jsonArray.getJSONObject(i);
                    db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (" + JSONQuestion.get("id") + ", '" + JSONQuestion.get("question") + "','" + JSONQuestion.get("image") + "'," + JSONQuestion.get("categories_id") + ")");
                    JSONArray JSONal = (JSONArray) JSONQuestion.get("alternatives");
                    db.execSQL("INSERT INTO test(right, alternatives_id, questions_id, categories_id) values (0,0,"+JSONQuestion.get("id")+","+JSONQuestion.get("categories_id")+")");
                    for (int j = 0; j < JSONal.length(); j++) {
                        JSONObject alternative = JSONal.getJSONObject(j);
                        db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (" + alternative.get("id") + ", '" + alternative.get("alternative") + "'," + alternative.get("right") + ", " + JSONQuestion.get("id") + ")");
                    }
                }
            }else{
                Cursor questionsCat1 = db.rawQuery("SELECT offline_questions._id, offline_questions.question, offline_questions.image, offline_questions.categories_id " +
                        "FROM offline_questions INNER JOIN questions_types ON offline_questions._id = questions_types.questions_id WHERE questions_types.class = '"+type.toUpperCase()+"' AND " +
                        "offline_questions.categories_id = 1 ORDER BY RANDOM() LIMIT  9", null);
                Cursor questionsCat2 = db.rawQuery("SELECT offline_questions._id, offline_questions.question, offline_questions.image, offline_questions.categories_id " +
                        "FROM offline_questions INNER JOIN questions_types ON offline_questions._id = questions_types.questions_id WHERE questions_types.class = '"+type.toUpperCase()+"' AND " +
                        "offline_questions.categories_id = 2 ORDER BY RANDOM() LIMIT  9", null);
                Cursor questionsCat3 = db.rawQuery("SELECT offline_questions._id, offline_questions.question, offline_questions.image, offline_questions.categories_id " +
                        "FROM offline_questions INNER JOIN questions_types ON offline_questions._id = questions_types.questions_id WHERE questions_types.class = '"+type.toUpperCase()+"' AND " +
                        "offline_questions.categories_id = 3 ORDER BY RANDOM() LIMIT  8", null);
                Cursor questionsCat4 = db.rawQuery("SELECT offline_questions._id, offline_questions.question, offline_questions.image, offline_questions.categories_id " +
                        "FROM offline_questions INNER JOIN questions_types ON offline_questions._id = questions_types.questions_id WHERE questions_types.class = '"+type.toUpperCase()+"' AND " +
                        "offline_questions.categories_id = 4 ORDER BY RANDOM() LIMIT  9", null);
                Cursor questions [] = {questionsCat1, questionsCat2, questionsCat3, questionsCat4};
                for (int i = 0; i < 4; i++) {
                    while (questions[i].moveToNext()) {
                        db.execSQL("INSERT INTO questions (_id, question, image, categories_id) values (" + questions[i].getInt(0) + ", '" + questions[i].getString(1) + "','" + questions[i].getString(2) + "'," + questions[i].getInt(3) + ")");

                        db.execSQL("INSERT INTO test(right, alternatives_id, questions_id, categories_id) values (0,0,"+questions[i].getInt(0)+","+questions[i].getInt(3)+")");
                        Cursor alternatives = db.rawQuery("SELECT * from offline_alternatives where questions_id = "+ questions[i].getInt(0) , null);
                        while (alternatives.moveToNext()){
                            db.execSQL("INSERT INTO alternatives (_id, alternative, right, questions_id) values (" + alternatives.getInt(0) + ",'" + alternatives.getString(1) + "' ," + alternatives.getInt(2) + "," + alternatives.getInt(3) + ")");
                        }
                    }
                }

            }
            result = "go";
        }catch (Exception e){
            System.out.println(e);
        }finally {
            db.close();
            return result;
        }
    }

    class LoadQuestion extends AsyncTask<String, String, String> {
        boolean localData;
        LoadQuestion (boolean connectivity){
            this.localData = connectivity;
        }
        LoadQuestion (){
            this.localData = false;
        }

        @Override
        protected String doInBackground(String... params){
            return parsingTest(localData);
        }
        @Override
        protected void onPostExecute(String result){
            if(result.equals("go")){
                Intent i = new Intent(RealModality.this, Question.class);
                i.putExtra("type", type);
                i.putExtra("modality", MODALITY);
                startActivity(i);
                mDialog.dismiss();
            }else{
                mDialog.dismiss();
                alertDialog.setTitle(getString(R.string.msjeTitle10))
                        .setMessage(getString(R.string.msjeText10))
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
