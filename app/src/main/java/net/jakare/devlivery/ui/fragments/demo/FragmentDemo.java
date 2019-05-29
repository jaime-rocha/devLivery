package net.jakare.devlivery.ui.fragments.demo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
                mostrarProducto(item);
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
        almuerzo.setDescripcion("Menú típico de Bolivia con Entrada, plato de fondo y postre.");
        almuerzo.setNombreEn("Menu Bolivia");
        almuerzo.setPrecio(102);
        almuerzo.setFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvssl6K0KrugHPc_VENKFiz44W4tJzvWKsWgoMDoVy3mU3g_75RA");
        almuerzo.setStats("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/FDA_Nutrition_Facts_Label_2006.jpg/220px-FDA_Nutrition_Facts_Label_2006.jpg");
        almuerzo.setIngredientes(new String[]{
                "Anticucho",
                "Sudado de Surubí",
                "Granita de Sandía"
        });
        productos.add(almuerzo);


        Producto saltena = new Producto();
        saltena.setCategoria("Comida");
        saltena.setNombre("Salteña de pollo picante");
        saltena.setNombreEn("Spice Chicken Salteña");
        saltena.setDescripcion("Tentempié originario de La Paz");
        saltena.setPrecio(15);
        saltena.setFoto("https://boliviaemprende.com/wp-content/uploads/2014/09/Gustu-singani_2.jpg");
        saltena.setStats("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/FDA_Nutrition_Facts_Label_2006.jpg/220px-FDA_Nutrition_Facts_Label_2006.jpg");
        saltena.setIngredientes(new String[]{
                "Pechuga de pollo",
                "Papa",
                "Cebolla blanca",
        });
        productos.add(saltena);


        Producto trucha = new Producto();
        trucha.setCategoria("Comida");
        trucha.setNombre("Trucha, Tumbo, Siracacha & Llullucha");
        trucha.setNombreEn("Trout, Tumbo, Siracacha & Llullucha");
        trucha.setDescripcion("Deliciosa Trucha del Lago Titicaca");
        trucha.setPrecio(108);
        trucha.setFoto("https://diariodegastronomia.com/wp-content/uploads/2017/09/Milhojas-de-trucha-y-patatas-759x500.jpg");
        trucha.setStats("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/FDA_Nutrition_Facts_Label_2006.jpg/220px-FDA_Nutrition_Facts_Label_2006.jpg");
        trucha.setIngredientes(new String[]{
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
        cordero.setDescripcion("Espectacular cordero Braseado");
        cordero.setPrecio(124);
        cordero.setFoto("https://www.mensjournal.com/wp-content/uploads/2019/04/bolivia-meat.jpg?w=900");
        cordero.setStats("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/FDA_Nutrition_Facts_Label_2006.jpg/220px-FDA_Nutrition_Facts_Label_2006.jpg");
        cordero.setIngredientes(new String[]{
                "Cordero",
                "Tunta",
                "Camote",
                "Nuez"
        });
        productos.add(cordero);
    }

    public void mostrarProducto(final Producto producto) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_demo);

        final TextView titulo = dialog.findViewById(R.id.lblTitulo);
        final ImageView imagen = dialog.findViewById(R.id.imagen);
        final TextView description = dialog.findViewById(R.id.description);
        final TextView ingredientes = dialog.findViewById(R.id.ingredientes);
        final SwitchCompat idioma = dialog.findViewById(R.id.idioma);

        titulo.setText(producto.getNombre());
        description.setText(producto.getDescripcion());
        Glide.with(context)
                .load(producto.getFoto())
                .centerCrop()
                .placeholder(R.drawable.img_no_disponible)
                .crossFade()
                .into(imagen);

        String descripcion = "";
        if (producto.getIngredientes() != null) {
            for (String ingrediente : producto.getIngredientes()) {
                descripcion += "- " + ingrediente + "\n";
            }
        }
        ingredientes.setText(descripcion);

        idioma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    idioma.setText("English");
                    titulo.setText(producto.getNombreEn());
                } else {
                    idioma.setText("Español");
                    titulo.setText(producto.getNombre());
                }
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }
}