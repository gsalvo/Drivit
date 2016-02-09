package cl.blackbirdhq.drivit;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;
import cl.blackbirdhq.drivit.helpers.DrivitSingleton;


public class StructureQuestion extends Fragment {
    private String messageQuestion, messageImage, messageQuestionId;
    private String density;
    private int messageGoToPosition, messagePosition, messageNumberQuestion;
    private boolean messageCheckTest = false;
    private int answered = 0;
    private Cursor alternative;
    private  ImageView imageQuestion;
    private  TextView question;
    private  RadioGroup groupAlternatives;
    private  RadioButton alternative1, alternative2, alternative3;
    OnSelectedAlternativeListener alternativeListener;
    OnChangeQuestionListener position;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        density = getResources().getDisplayMetrics().density + "";
        messageNumberQuestion = getArguments().getInt("numberQuestion");
        messageQuestion = getArguments().getString("question");
        messageImage = getArguments().getString("image");
        messageQuestionId = getArguments().getString("id_question");
        messageGoToPosition = getArguments().getInt("goToPosition");
        messagePosition = getArguments().getInt("position");
        messageCheckTest = getArguments().getBoolean("checkTest", false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_structure_question, container, false);
        initializeComponents(view);
        return view;
    }



    public void initializeComponents(View view){
        AdminSQLiteAPP admin = new AdminSQLiteAPP(this.getActivity());
        SQLiteDatabase bd = admin.getWritableDatabase();
        imageQuestion = (ImageView) view.findViewById(R.id.imageQuestion);
        question = (TextView) view.findViewById(R.id.question);
        groupAlternatives = (RadioGroup) view.findViewById(R.id.groupAlternatives);
        alternative1 = (RadioButton) view.findViewById(R.id.alternative1);
        alternative2 = (RadioButton) view.findViewById(R.id.alternative2);
        alternative3 = (RadioButton) view.findViewById(R.id.alternative3);
        question.setText(messageNumberQuestion + ".- " + messageQuestion);
        if(messageImage.equals("null")){
            imageQuestion.setVisibility(View.GONE);
        }else{

            String url = "";
            switch(density){
                case "0.75":
                    url = "http://drivit.blackbirdhq.cl/img/mdpi/"+messageImage;
                    break;
                case "1.0":
                    url = "http://drivit.blackbirdhq.cl/img/mdpi/"+messageImage;
                    break;
                case "1.5":
                    url = "http://drivit.blackbirdhq.cl/img/hdpi/"+messageImage;
                    break;
                case "2.0":
                    url = "http://drivit.blackbirdhq.cl/img/xhdpi/"+messageImage;
                    break;
                case "3.0":
                    url = "http://drivit.blackbirdhq.cl/img/xxhdpi/"+messageImage;
                    break;
                case "4.0":
                    url = "http://drivit.blackbirdhq.cl/img/xxxhdpi/"+messageImage;
                    break;
            }
            ImageRequest request = new ImageRequest(url,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            imageQuestion.setImageBitmap(bitmap);
                        }
                    }, 0, 0, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            //imageQuestion.setImageResource(R.drawable.image_load_error);
                        }
                    });
            // Access the RequestQueue through your singleton class.
            DrivitSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
        }
        alternative = bd.rawQuery("Select * from alternatives where questions_id = " + messageQuestionId, null);
        Cursor test = bd.rawQuery("Select alternatives_id from test where questions_id = "+ messageQuestionId, null);
        if(test.getCount() > 0){
            test.moveToFirst();
            answered = test.getInt(0);
            alternativeListener.selectedAlternative(answered);
        }else{
            answered = 0;
            alternativeListener.selectedAlternative(0);
        }
        alternative.moveToFirst();
        if(messageCheckTest){
            //first alternative
            alternative1.setText(alternative.getString(1));
            alternative1.setEnabled(false);
            if (alternative.getInt(2) ==0){
                alternative1.setTextColor(getResources().getColor(R.color.errorAnswer));
                alternative1.setPaintFlags(alternative1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            if(alternative.getInt(0)== answered){alternative1.setChecked(true);}

            //second alternative
            alternative.moveToNext();
            alternative2.setText(alternative.getString(1));
            alternative2.setEnabled(false);
            if (alternative.getInt(2) ==0){
                alternative2.setTextColor(getResources().getColor(R.color.errorAnswer));
                alternative2.setPaintFlags(alternative1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            if(alternative.getInt(0)== answered) {alternative2.setChecked(true);}

            //third alternative
            alternative.moveToNext();
            alternative3.setText(alternative.getString(1));
            alternative3.setEnabled(false);
            if (alternative.getInt(2) ==0){
                alternative3.setTextColor(getResources().getColor(R.color.errorAnswer));
                alternative3.setPaintFlags(alternative1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            if(alternative.getInt(0)== answered) {alternative3.setChecked(true);}
        }else{

            alternative1.setText(alternative.getString(1));
            if(alternative.getInt(0)== answered){alternative1.setChecked(true);}
            alternative.moveToNext();
            alternative2.setText(alternative.getString(1));
            if(alternative.getInt(0)== answered) {alternative2.setChecked(true);}
            alternative.moveToNext();
            alternative3.setText(alternative.getString(1));
            if(alternative.getInt(0)== answered) {alternative3.setChecked(true);}
            alternative.moveToNext();

            groupAlternatives.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (groupAlternatives.getCheckedRadioButtonId() == alternative1.getId()) {
                        alternative.moveToPosition(0);
                        alternativeListener.selectedAlternative(alternative.getInt(0));
                    } else if (groupAlternatives.getCheckedRadioButtonId() == alternative2.getId()) {
                        alternative.moveToPosition(1);
                        alternativeListener.selectedAlternative(alternative.getInt(0));
                    } else if (groupAlternatives.getCheckedRadioButtonId() == alternative3.getId()) {
                        alternative.moveToPosition(2);
                        alternativeListener.selectedAlternative(alternative.getInt(0));
                    }
                }
            });
        }
        position.goToQuestion(messageGoToPosition - messagePosition);
        bd.close();

    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        alternativeListener =(OnSelectedAlternativeListener) activity;
        position = (OnChangeQuestionListener) activity;
    }


    public interface OnChangeQuestionListener {
        public void goToQuestion(int position);
    }

    public interface OnSelectedAlternativeListener{
        public void selectedAlternative(int alternative);
    }


}
