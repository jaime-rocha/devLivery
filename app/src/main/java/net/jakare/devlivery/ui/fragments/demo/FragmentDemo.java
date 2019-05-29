package net.jakare.devlivery.ui.fragments.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
        LinearLayoutManager cardLayoutManager = new LinearLayoutManager(context);
        cardLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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
        Producto almuerzo = new Producto();
        almuerzo.setCategoria("Comida");
        almuerzo.setNombre("Menú Bolivia (4 pasos)");
        almuerzo.setNombreEn("Menu Bolivia");
        almuerzo.setPrecio(102);
        almuerzo.setFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvssl6K0KrugHPc_VENKFiz44W4tJzvWKsWgoMDoVy3mU3g_75RA");
        almuerzo.setStats("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/FDA_Nutrition_Facts_Label_2006.jpg/220px-FDA_Nutrition_Facts_Label_2006.jpg");
        almuerzo.setIngredientes(new String[]{

        });
        productos.add(almuerzo);


        Producto saltena = new Producto();
        saltena.setCategoria("Comida");
        saltena.setNombre("Salteña de pollo picante");
        saltena.setNombreEn("Spice Chicken Salteña");
        saltena.setPrecio(15);
        saltena.setFoto("https://boliviaemprende.com/wp-content/uploads/2014/09/Gustu-singani_2.jpg");
        saltena.setStats("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/FDA_Nutrition_Facts_Label_2006.jpg/220px-FDA_Nutrition_Facts_Label_2006.jpg");
        almuerzo.setIngredientes(new String[]{
                "Anticucho",
                "Sudado de Surubí",
                "Granita de Sandía"
        });
        productos.add(saltena);


        Producto trucha = new Producto();
        trucha.setCategoria("Comida");
        trucha.setNombre("Trucha, Tumbo, Siracacha & Llullucha");
        trucha.setNombreEn("Trout, Tumbo, Siracacha & Llullucha");
        trucha.setPrecio(108);
        trucha.setFoto("http://www.lustermagazine.com/wp-content/uploads/2018/08/PESCADO-AMAZ%C3%B4NICO-SELLADO-A-LA-PLANCHA-CON-SONSO-DE-YUCA-SALSA-DE-COCO-MISO-Y-CILANTRO-FRESCO-by-PATRICIO-CROOKER_076.jpg");
        trucha.setStats("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/FDA_Nutrition_Facts_Label_2006.jpg/220px-FDA_Nutrition_Facts_Label_2006.jpg");
        almuerzo.setIngredientes(new String[]{
                "Trucha del lago Titicaca",
                "Tumbo de Sorata",
                "Siracacha",
                "Llullucha",
                "Verduras hervidas"
        });
        productos.add(trucha);

        Producto cordero = new Producto();
        cordero.setCategoria("Comida");
        cordero.setNombre("Cordero Braseado, Tunta, Camote");
        cordero.setNombreEn("Brased Lamb, Tunta, Achiote");
        cordero.setPrecio(108);
        cordero.setFoto("https://www.mensjournal.com/wp-content/uploads/2019/04/bolivia-meat.jpg?w=900");
        cordero.setStats("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/FDA_Nutrition_Facts_Label_2006.jpg/220px-FDA_Nutrition_Facts_Label_2006.jpg");
        almuerzo.setIngredientes(new String[]{
                "Cordero",
                "Tunta",
                "Camote",
                "Nuez"
        });
        productos.add(cordero);


    }
}