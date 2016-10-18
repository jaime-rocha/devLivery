package net.jakare.devlivery.ui.fragments.usuarios;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import net.jakare.devlivery.R;
import net.jakare.devlivery.model.appClasses.Carrito;
import net.jakare.devlivery.ui.activities.MainActivity;
import net.jakare.devlivery.ui.activities.ProcesarPedidoActivity;
import net.jakare.devlivery.ui.adapters.CarritoAdapter;
import net.jakare.devlivery.utils.constants.AppConstants;
import net.jakare.devlivery.utils.global.GlobalFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andresvasquez on 17/8/16.
 */

public class FragmentCarrito extends Fragment implements View.OnClickListener{

    private MainActivity act;
    private Context context;

    private CarritoAdapter adapter;
    private List<Carrito> items=new ArrayList<Carrito>();

    private RecyclerView rvLista;
    private TextView lblTotal;
    private Button btnVaciar;
    private Button btnProcesarCompra;

    public FragmentCarrito() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_carrito, container, false);
        context=getActivity();

        initViews(view);

        rvLista.setHasFixedSize(true);
        RecyclerView.LayoutManager cardLayoutManager = new LinearLayoutManager(context);
        rvLista.setLayoutManager(cardLayoutManager);


        adapter=new CarritoAdapter(context,true);
        rvLista.setAdapter(adapter);

        btnVaciar.setOnClickListener(this);
        btnProcesarCompra.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        actualizarCarrito();
    }

    private void initViews(View view) {
        rvLista=(RecyclerView) view.findViewById(R.id.rvLista);
        lblTotal=(TextView) view.findViewById(R.id.lblTotal);
        btnVaciar=(Button) view.findViewById(R.id.btnVaciar);
        btnProcesarCompra=(Button) view.findViewById(R.id.btnProcesarCompra);
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

    private void actualizarCarrito() {
        double total=0;

        items.clear();
        items.addAll(act.getLstCarrito());
        adapter.swapCursor(items);

        for (Carrito carrito : items){
            total+=carrito.getSubtotal();
        }
        lblTotal.setText(GlobalFunctions.format2Digits(total));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnVaciar:
                items.clear();
                adapter.swapCursor(items);
                act.getLstCarrito().clear();
                act.actualizarCarrito(0);
                lblTotal.setText(GlobalFunctions.format2Digits(0));
                break;
            case R.id.btnProcesarCompra:
                procesarCarrito();
                break;
            default:
                break;
        }
    }

    private void procesarCarrito() {
        Intent procesarIntent=new Intent(context, ProcesarPedidoActivity.class);
        procesarIntent.putExtra(AppConstants.TAG_ITEM_PEDIDO,new Gson().toJson(items));
        act.startActivityForResult(procesarIntent,MainActivity.RC_PROCESAR);
    }
}
