package cl.blackbirdhq.drivit;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cl.blackbirdhq.drivit.helpers.AdminSQLiteAPP;


public class NavQuestion extends Fragment {
    //private SQLiteDatabase bd;
    //private AdminSQLiteAPP admin;
    private LinearLayout contentButtonNav;
    private int messageCurrentQuestion;
    OnSelectedQuestionListener onSelectedQuestion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageCurrentQuestion = getArguments().getInt("currentQuestion");
        onSelectedQuestion.selectedQuestionListener(messageCurrentQuestion);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_question, container, false);
        initializeComponents(view);

        return view;
    }

    private void initializeComponents(View view) {
        contentButtonNav = (LinearLayout) view.findViewById(R.id.contentButtonNav);

        AdminSQLiteAPP admin = new AdminSQLiteAPP(this.getActivity());
        SQLiteDatabase bd = admin.getReadableDatabase();
        Cursor questions = bd.rawQuery("select _id from questions order by _id", null);
        questions.moveToFirst();
        int cantQuestion = questions.getCount();
        int cantButtonLY = 5;
        double cantRow =Math.ceil((float)cantQuestion/(float)cantButtonLY);
        int numberQuestion = 1;
        for(int i = 0; i <cantRow; i++){
            LinearLayout linearLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.content_button_nav,contentButtonNav,false);
            contentButtonNav.addView(linearLayout);
            for(int y = 0; y < cantButtonLY; y++) {
                if(numberQuestion <= cantQuestion){
                    Cursor test = bd.rawQuery("select alternatives_id from test where questions_id="+questions.getInt(0),null);
                    test.moveToFirst();
                    final Button btnQuestion;
                    if (test.getCount() == 0){
                        if(messageCurrentQuestion == numberQuestion){
                            btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_no_answered_actual, linearLayout, false);
                        }else {
                            btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_no_answered, linearLayout, false);
                        }
                    }else if (test.getInt(0) == 0) {
                        if(messageCurrentQuestion == numberQuestion){
                            btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_no_answered_actual, linearLayout, false);
                        }else {
                            btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_no_answered, linearLayout, false);
                        }
                    }else{
                        if(messageCurrentQuestion == numberQuestion){
                            btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_answered_actual,linearLayout,false);
                        }else{
                            btnQuestion = (Button) getActivity().getLayoutInflater().inflate(R.layout.btn_nav_test_answered,linearLayout,false);
                        }
                    }
                    btnQuestion.setText(numberQuestion + "");
                    linearLayout.addView(btnQuestion);
                    final int finalNumberQuestion = numberQuestion;
                    btnQuestion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onSelectedQuestion.selectedQuestionListener(finalNumberQuestion);
                            getActivity().finish();
                        }
                    });
                    questions.moveToNext();
                    numberQuestion++;
                    test.close();
                }else{
                    break;
                }
            }
        }
        questions.close();
        bd.close();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        onSelectedQuestion =(OnSelectedQuestionListener) activity;
    }

    public interface OnSelectedQuestionListener{
        public void selectedQuestionListener(int question);
    }
}
