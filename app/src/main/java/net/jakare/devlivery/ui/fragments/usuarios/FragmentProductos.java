package net.jakare.devlivery.ui.fragments.usuarios;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import net.jakare.devlivery.R;
import net.jakare.devlivery.model.dbClasses.Producto;
import net.jakare.devlivery.ui.activities.DetalleProductoActivity;
import net.jakare.devlivery.ui.activities.MainActivity;
import net.jakare.devlivery.ui.adapters.ProductosAdapter;
import net.jakare.devlivery.utils.constants.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andresvasquez on 17/8/16.
 */

public class FragmentProductos extends Fragment {

    private MainActivity act;
    private Context context;


    private ProductosAdapter adapter;
    private RecyclerView rvLista;

    private String categoria;
    private Spinner cmbCategoria;

    public FragmentProductos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_lista_productos, container, false);
        context=getActivity();
        initViews(view);

        //Setting up recycler view
        rvLista.setHasFixedSize(true);
        GridLayoutManager cardLayoutManager = new GridLayoutManager(context, 2);
        rvLista.setLayoutManager(cardLayoutManager);

        adapter=new ProductosAdapter(context);
        rvLista.setAdapter(adapter);

        cmbCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              categoria=adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        adapter.setOnItemClickListener(new ProductosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Producto item) {
                Intent details=new Intent(context, DetalleProductoActivity.class);
                details.putExtra(AppConstants.TAG_ITEM_PRODUCTO,new Gson().toJson(item));
                act.startActivityForResult(details,MainActivity.RC_CARRITO);
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(AppConstants.TAG_PRODUCTO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Received",dataSnapshot.toString());

                List<Producto> lstProductos=new ArrayList<Producto>();
                for(DataSnapshot dataProducto : dataSnapshot.getChildren()){
                    Producto producto=dataProducto.getValue(Producto.class);
                    producto.setKey(dataProducto.getKey());
                    lstProductos.add(producto);
                }
                adapter.swapCursor(lstProductos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return view;
    }

    private void initViews(View view) {
        rvLista=(RecyclerView) view.findViewById(R.id.rvLista);
        cmbCategoria=(Spinner)view.findViewById(R.id.cmbCategoria);
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
