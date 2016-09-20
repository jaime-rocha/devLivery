package net.jakare.devlivery.ui.fragments.restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.jakare.devlivery.R;
import net.jakare.devlivery.ui.activities.AgregarProductoActivity;
import net.jakare.devlivery.ui.activities.MainActivity;

/**
 * Created by andresvasquez on 17/8/16.
 */

public class FragmentGestionProductos extends Fragment implements View.OnClickListener{

    private MainActivity act;
    private Context context;

    private FloatingActionButton fabAgregar;

    public FragmentGestionProductos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_lista, container, false);
        context=getActivity();

        initViews(view);

        fabAgregar.setOnClickListener(this);

        return view;
    }


    private void initViews(View view) {
        fabAgregar=(FloatingActionButton)view.findViewById(R.id.fabAgregar);
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

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.fabAgregar){
            Intent agregarProducto=new Intent(context, AgregarProductoActivity.class);
            startActivity(agregarProducto);
        }
    }
}
