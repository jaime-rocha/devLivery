package net.jakare.devlivery.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import net.jakare.devlivery.R;
import net.jakare.devlivery.ui.activities.MainActivity;

/**
 * Created by andresvasquez on 17/8/16.
 */

public class FragmentAcerca extends Fragment {

    private MainActivity act;
    private Context context;

    private ImageView imgGithub;
    private CardView cardGithub;
    private TextView lblVersion;
    private TextView lblEmail;
    private TextView lblCelular;

    public FragmentAcerca() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_acerca, container, false);
        context=getActivity();

        initViews(view);
        imgGithub.setImageDrawable(new IconicsDrawable(context, FontAwesome.Icon.faw_github)
                .color(getResources().getColor(R.color.text)));

        return view;
    }

    private void initViews(View view) {
        imgGithub=(ImageView)view.findViewById(R.id.imgGithub);
        cardGithub=(CardView)view.findViewById(R.id.cardGithub);
        lblVersion=(TextView)view.findViewById(R.id.lblVersion);
        lblEmail=(TextView)view.findViewById(R.id.lblEmail);
        lblCelular=(TextView)view.findViewById(R.id.lblCelular);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity){
            act=(MainActivity)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
