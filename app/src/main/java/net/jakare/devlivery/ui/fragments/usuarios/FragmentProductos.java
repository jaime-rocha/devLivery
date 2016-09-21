package net.jakare.devlivery.ui.fragments.usuarios;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
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

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    private ProductosAdapter adapter;
    private List<Producto> items=new ArrayList<Producto>();
    private ListView lstList;

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

        adapter=new ProductosAdapter(context,items);
        lstList.setAdapter(adapter);

        lstList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Producto selected = items.get(position);
                Intent details=new Intent(context, DetalleProductoActivity.class);
                details.putExtra("BidItem",new Gson().toJson(selected));
                startActivity(details);
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(AppConstants.TAG_PRODUCTO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Received",dataSnapshot.toString());

                List<Producto> lstOfertas=new ArrayList<Producto>();
                for(DataSnapshot oferta : dataSnapshot.getChildren()){
                    Producto bid=oferta.getValue(Producto.class);
                    lstOfertas.add(bid);
                }
                items.clear();
                items.addAll(lstOfertas);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return view;
    }

    private void initViews(View view) {
        lstList=(ListView)view.findViewById(R.id.lstList);
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
