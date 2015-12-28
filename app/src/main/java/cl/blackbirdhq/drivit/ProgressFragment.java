package cl.blackbirdhq.drivit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ProgressFragment extends Fragment {
    int fragVal;

    // TODO: Rename and change types and number of parameters
    public static ProgressFragment init(int val) {
        ProgressFragment fragment = new ProgressFragment();
        Bundle args = new Bundle();
        args.putInt("val", val);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragVal = getArguments().getInt("val");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        TextView textView = (TextView) view.findViewById(R.id.testText);
        textView.setText("ESTE ES EL FRAGMENT: " + fragVal);
        return view;
    }

}
