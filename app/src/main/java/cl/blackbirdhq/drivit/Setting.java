package cl.blackbirdhq.drivit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;
import cl.blackbirdhq.drivit.helpers.DrivitSingleton;


public class Setting extends AppCompatActivity {
    private Button download, deleteTest;
    private AdminSQLiteAPP data = new AdminSQLiteAPP(this);
    private SQLiteDatabase db;
    private LoadQuestion loadQuestion;
    private JSONArray jsonArray;
    private ProgressDialog mDialog;
    private AlertDialog.Builder alertDialog;
    private TextView offlineTitle;
    Context context;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initializeComponents();
    }

    private void initializeComponents() {

        db = data.getWritableDatabase();
        context = getApplicationContext();
        download = (Button) findViewById(R.id.btnDownload);
        deleteTest = (Button) findViewById(R.id.btnDelete);
        offlineTitle = (TextView) findViewById(R.id.title);
        mDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);
        checkData();
    }

    public void checkData(){
        Cursor countData = db.rawQuery("SELECT count(*) FROM questions_types", null);
        countData.moveToFirst();
        if(countData.getInt(0)> 0){
            offlineTitle.setText(getString(R.string.confTitle)+" "+getString(R.string.confText2));
            download.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_refresh_white_48dp, 0, 0, 0);
            download.setBackgroundResource(R.drawable.round_button);
            deleteTest.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete_white_48dp, 0, 0, 0);
            deleteTest.setBackgroundResource(R.drawable.round_button);
            deleteTest.setEnabled(true);
        }else{
            offlineTitle.setText(getString(R.string.confTitle)+" "+getString(R.string.confText3));
            download.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_cloud_download_white_48dp, 0, 0, 0);
            download.setBackgroundResource(R.drawable.round_button);
            deleteTest.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete_white_dis_48dp, 0, 0, 0);
            deleteTest.setBackgroundResource(R.drawable.round_button);
            deleteTest.setEnabled(false);
        }
        countData.close();
    }

    public void downloadData(View view){
        mDialog.setMessage(getString(R.string.msjeText2));
        mDialog.setIndeterminate(true);
        mDialog.setCancelable(false);
        mDialog.show();
        String url = "http://blackbirdhq.cl/offlineQuestions.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mDialog.setMessage(getString(R.string.msjeText3));
                        data.resetData(db);
                        jsonArray = response;
                        loadQuestion = new LoadQuestion();
                        loadQuestion.execute();
                        mDialog.setMessage(getString(R.string.msjeText4));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        download.setEnabled(true);
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
        DrivitSingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void deleteData(View view){
        data.resetData(db);
        int duration = Toast.LENGTH_SHORT;
        toast = Toast.makeText(context, getString(R.string.msjeText5), duration);
        toast.show();
        checkData();
    }

    private String parsingTest() {
        data.resetData(db);
        String result = "go";
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject JSONQuestion = jsonArray.getJSONObject(i);
                db.execSQL("INSERT INTO questions_types (questions_id, class) values ("+ JSONQuestion.get("id") +", '"+JSONQuestion.get("class")+"')");
                db.execSQL("INSERT OR IGNORE INTO offline_questions (_id, question, image, categories_id) values (" + JSONQuestion.get("id") + ", '" + JSONQuestion.get("question") + "','" + JSONQuestion.get("image") + "'," + JSONQuestion.get("categories_id") + ")");
                JSONArray JSONal = (JSONArray) JSONQuestion.get("alternatives");
                for (int j = 0; j < JSONal.length(); j++) {
                    JSONObject alternative = JSONal.getJSONObject(j);
                    db.execSQL("INSERT OR IGNORE INTO offline_alternatives (_id, alternative, right, questions_id) values (" + alternative.get("id") + ", '" + alternative.get("alternative") + "'," + alternative.get("right") + ", " + JSONQuestion.get("id")+")");
                }
            }
        }catch (Exception e){
            System.out.println(e);
            result = "stop";
        }finally {
            return result;
        }

    }

    class LoadQuestion extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params){
            return parsingTest();
        }
        @Override
        protected void onPostExecute(String result){
            if(result.equals("go")){
                checkData();
                mDialog.dismiss();
                int duration = Toast.LENGTH_SHORT;
                toast = Toast.makeText(context, getString(R.string.msjeText6), duration);
                toast.show();

            }else{
                checkData();
                mDialog.dismiss();
                alertDialog.setTitle(getString(R.string.msjeTitle7))
                        .setMessage(getString(R.string.msjeText7))
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
