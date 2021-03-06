package net.jakare.devlivery.ui.fragments.restaurant;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.jakare.devlivery.R;
import net.jakare.devlivery.controller.server.FirebasePedidosController;
import net.jakare.devlivery.model.dbClasses.Pedido;
import net.jakare.devlivery.ui.activities.MainActivity;
import net.jakare.devlivery.ui.adapters.PedidosAdapter;
import net.jakare.devlivery.utils.constants.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andresvasquez on 17/8/16.
 */

public class FragmentListaEntregas extends Fragment implements View.OnClickListener{

    private MainActivity act;
    private Context context;

    private PedidosAdapter adapter;
    private List<Pedido> items=new ArrayList<Pedido>();
    private RecyclerView rvLista;
    private TextView lblTitulo;

    public FragmentListaEntregas() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_lista_pedidos, container, false);
        context=getActivity();

        initViews(view);

        rvLista.setHasFixedSize(true);
        RecyclerView.LayoutManager cardLayoutManager = new LinearLayoutManager(context);
        rvLista.setLayoutManager(cardLayoutManager);


        adapter=new PedidosAdapter(context);
        rvLista.setAdapter(adapter);

        adapter.setOnItemClickListener(new PedidosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pedido item) {
                double lat=item.getLat();
                double lon=item.getLon();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+lat+","+lon+"?q="+lat+","+lon+"(Ubicación pedido)"));
                startActivity(intent);
            }
        });

        obtenerPedidos();
        return view;
    }

    private void obtenerPedidos() {
        FirebasePedidosController pedidosController=new FirebasePedidosController(act,
                new FirebasePedidosController.ResultadoLista() {
                    @Override
                    public void onResponse(List<Pedido> lstPedidos) {
                        adapter.swapCursor(lstPedidos);
                    }
                });
        pedidosController.ListasPedidos(AppConstants.ESTADO_DELIVERY);
    }

    private void initViews(View view) {
        rvLista=(RecyclerView) view.findViewById(R.id.rvLista);
        lblTitulo=(TextView)view.findViewById(R.id.lblTitulo);
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
    }
}
