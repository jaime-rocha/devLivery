package net.jakare.devlivery.ui.fragments.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.gson.Gson;

import net.jakare.devlivery.R;
import net.jakare.devlivery.controller.server.FirebaseProductosController;
import net.jakare.devlivery.model.dbClasses.Producto;
import net.jakare.devlivery.ui.activities.DetalleProductoActivity;
import net.jakare.devlivery.ui.activities.MainActivity;
import net.jakare.devlivery.ui.adapters.DemoAdapter;
import net.jakare.devlivery.ui.adapters.ImageAdapter;
import net.jakare.devlivery.ui.adapters.ProductosAdapter;
import net.jakare.devlivery.utils.constants.AppConstants;
import net.jakare.devlivery.utils.constants.PublicidadConstants;

import java.util.ArrayList;
import java.util.List;

public class FragmentDemo extends Fragment {

    private MainActivity act;
    private Context context;


    private DemoAdapter adapter;
    private RecyclerView rvLista;

    private List<Producto> productos = new ArrayList<>();

    public FragmentDemo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_demo, container, false);
        context = getActivity();
        initViews(view);

        //Setting up recycler view
        rvLista.setHasFixedSize(true);
        GridLayoutManager cardLayoutManager = new GridLayoutManager(context, 2);
        rvLista.setLayoutManager(cardLayoutManager);

        llenarProductos();
        adapter = new DemoAdapter(context);
        adapter.swapCursor(productos);
        rvLista.setAdapter(adapter);

        adapter.setOnItemClickListener(new ProductosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Producto item) {
                Intent details = new Intent(context, DetalleProductoActivity.class);
                details.putExtra(AppConstants.TAG_ITEM_PRODUCTO, new Gson().toJson(item));
                act.startActivityForResult(details, MainActivity.RC_CARRITO);
            }
        });

        return view;
    }

    private void initViews(View view) {
        rvLista = view.findViewById(R.id.rvLista);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            act = (MainActivity) context;
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

    private void llenarProductos() {

    }
}