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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.jakare.devlivery.R;
import net.jakare.devlivery.model.dbClasses.Pedido;
import net.jakare.devlivery.ui.activities.AgregarProductoActivity;
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

    private FloatingActionButton fabPagar;

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
        View view=inflater.inflate(R.layout.fragment_lista, container, false);
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

            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(AppConstants.TAG_PEDIDOS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Pedido> lstPedidos=new ArrayList<Pedido>();
                for(DataSnapshot dataPedido : dataSnapshot.getChildren()){
                    Pedido pedido=dataPedido.getValue(Pedido.class);
                    pedido.setKey(dataPedido.getKey());
                    lstPedidos.add(pedido);
                }
                adapter.swapCursor(lstPedidos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        fabPagar.setVisibility(View.VISIBLE);
        fabPagar.setOnClickListener(this);
        return view;
    }

    private void initViews(View view) {
        fabPagar=(FloatingActionButton)view.findViewById(R.id.fabPagar);
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
        if(view.getId()==R.id.fabPagar){
            Intent agregarProducto=new Intent(context, AgregarProductoActivity.class);
            startActivity(agregarProducto);
        }
    }
}
