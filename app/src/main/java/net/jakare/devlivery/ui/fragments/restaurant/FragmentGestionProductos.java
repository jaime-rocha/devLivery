package net.jakare.devlivery.ui.fragments.restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import net.jakare.devlivery.R;
import net.jakare.devlivery.controller.server.FirebaseProductosController;
import net.jakare.devlivery.model.dbClasses.Producto;
import net.jakare.devlivery.ui.activities.AgregarProductoActivity;
import net.jakare.devlivery.ui.activities.DetalleProductoActivity;
import net.jakare.devlivery.ui.activities.MainActivity;
import net.jakare.devlivery.ui.adapters.GestionProductosAdapter;
import net.jakare.devlivery.utils.constants.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andresvasquez on 17/8/16.
 */

public class FragmentGestionProductos extends Fragment implements View.OnClickListener{

    private MainActivity act;
    private Context context;

    private GestionProductosAdapter adapter;
    private List<Producto> items=new ArrayList<Producto>();
    private RecyclerView rvLista;

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

        rvLista.setHasFixedSize(true);
        RecyclerView.LayoutManager cardLayoutManager = new LinearLayoutManager(context);
        rvLista.setLayoutManager(cardLayoutManager);


        adapter=new GestionProductosAdapter(items,context);
        rvLista.setAdapter(adapter);

        adapter.setOnItemClickListener(new GestionProductosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Producto item) {
                Intent details=new Intent(context, DetalleProductoActivity.class);
                details.putExtra(AppConstants.TAG_ITEM_PRODUCTO,new Gson().toJson(item));
                startActivity(details);
            }
        });

        obtenerProductos();

        fabAgregar.setVisibility(View.VISIBLE);
        fabAgregar.setOnClickListener(this);

        return view;
    }

    private void obtenerProductos() {
        FirebaseProductosController productosController=new FirebaseProductosController(act,
                new FirebaseProductosController.ResultadoLista() {
                    @Override
                    public void onResponse(List<Producto> lstProductos) {
                        adapter.swapCursor(lstProductos);
                    }
                });
        productosController.ListasProductos("");
    }


    private void initViews(View view) {
        fabAgregar=(FloatingActionButton)view.findViewById(R.id.fabAgregar);
        rvLista=(RecyclerView) view.findViewById(R.id.rvLista);
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
