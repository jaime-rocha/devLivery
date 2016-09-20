package net.jakare.devlivery.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import net.jakare.devlivery.R;
import net.jakare.devlivery.controller.data.SharedPreferencesData;
import net.jakare.devlivery.model.dbClasses.User;
import net.jakare.devlivery.ui.fragments.FragmentAcerca;
import net.jakare.devlivery.ui.fragments.restaurant.FragmentListaEntregas;
import net.jakare.devlivery.ui.fragments.restaurant.FragmentListaPedidos;
import net.jakare.devlivery.ui.fragments.usuarios.FragmentProductos;
import net.jakare.devlivery.ui.fragments.usuarios.FragmentCarrito;
import net.jakare.devlivery.ui.fragments.restaurant.FragmentGestionProductos;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    private static final int RC_SIGN_IN = 100;

    //Vista para el cliente
    public static final int DRAWER_ITEM_PRODUCTOS = 1;
    public static final int DRAWER_ITEM_CARRITO = 2;

    //Vista para el negocio
    public static final int DRAWER_ITEM_GESTION_PRODUCTOS = 3;
    public static final int DRAWER_ITEM_LISTA_PEDIDOS = 4;
    public static final int DRAWER_ITEM_LISTA_ENTREGAS = 5;

    //Sobre la App
    public static final int DRAWER_ITEM_ACERCA = 9;
    public static final int DRAWER_ITEM_LOG_OUT = 10;

    private Drawer drawer;
    private Context context;
    private SharedPreferences preferences;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        initViews();
        initToolbar();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null)
        {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            User user=new User();
            user.setIdUser(currentUser.getUid());
            user.setDisplayName(currentUser.getDisplayName());
            user.setEmail(currentUser.getEmail());

            try
            {
                user.setPhotoUrl(String.valueOf(currentUser.getPhotoUrl()));
            }
            catch (Exception ex){
                user.setPhotoUrl("");
            }

            SharedPreferencesData prefs=new SharedPreferencesData(context);
            prefs.setUserData(user);

            initNavegacionDrawer(savedInstanceState,user);
            selectItem(DRAWER_ITEM_PRODUCTOS);
        }
        else
        {
            // generamos la pantalla de logueo
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setLogo(R.drawable.logo)
                            .setProviders(
                                    AuthUI.GOOGLE_PROVIDER,
                                    AuthUI.EMAIL_PROVIDER)
                            .setTheme(R.style.devLiveryTheme)
                            .build(), RC_SIGN_IN);
        }
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void initToolbar() {
        // Handle Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initNavegacionDrawer(Bundle savedInstanceState, User user) {
        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(user.getDisplayName())
                                .withEmail(user.getEmail())
                                .withIcon(user.getPhotoUrl())
                )
                .withHeaderBackground(R.color.colorPrimaryDark)
                .build();

        //Navidation Drawer
        drawer = new DrawerBuilder(this)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .addDrawerItems(
                        new PrimaryDrawerItem().
                                withIdentifier(DRAWER_ITEM_PRODUCTOS).
                                withName(getResources().getString(R.string.drawer_productos)).
                                withTextColor(getResources().getColor(R.color.text)).
                                withIconColor(getResources().getColor(R.color.text)).
                                withSelectedTextColor(getResources().getColor(R.color.primary)).
                                withSelectedIconColor(getResources().getColor(R.color.primary)).
                                withIcon(FontAwesome.Icon.faw_cutlery),
                        new PrimaryDrawerItem().
                                withIdentifier(DRAWER_ITEM_CARRITO).
                                withName(getResources().getString(R.string.drawer_carrito)).
                                withTextColor(getResources().getColor(R.color.text)).
                                withIconColor(getResources().getColor(R.color.text)).
                                withSelectedTextColor(getResources().getColor(R.color.primary)).
                                withSelectedIconColor(getResources().getColor(R.color.primary)).
                                withIcon(FontAwesome.Icon.faw_shopping_cart).
                                withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.colorPrimaryDark)),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().
                                withIdentifier(DRAWER_ITEM_GESTION_PRODUCTOS).
                                withName(getResources().getString(R.string.drawer_gestion_productos)).
                                withTextColor(getResources().getColor(R.color.text)).
                                withIconColor(getResources().getColor(R.color.text)).
                                withSelectedTextColor(getResources().getColor(R.color.primary)).
                                withSelectedIconColor(getResources().getColor(R.color.primary)).
                                withIcon(FontAwesome.Icon.faw_cubes),
                        new PrimaryDrawerItem().
                                withIdentifier(DRAWER_ITEM_LISTA_PEDIDOS).
                                withName(getResources().getString(R.string.drawer_lista_pedidos)).
                                withTextColor(getResources().getColor(R.color.text)).
                                withIconColor(getResources().getColor(R.color.text)).
                                withSelectedTextColor(getResources().getColor(R.color.primary)).
                                withSelectedIconColor(getResources().getColor(R.color.primary)).
                                withIcon(FontAwesome.Icon.faw_list_ol),
                        new PrimaryDrawerItem().
                                withIdentifier(DRAWER_ITEM_LISTA_ENTREGAS).
                                withName(getResources().getString(R.string.drawer_lista_entregas)).
                                withTextColor(getResources().getColor(R.color.text)).
                                withIconColor(getResources().getColor(R.color.text)).
                                withSelectedTextColor(getResources().getColor(R.color.primary)).
                                withSelectedIconColor(getResources().getColor(R.color.primary)).
                                withIcon(FontAwesome.Icon.faw_truck)
                ).addStickyDrawerItems(
                        //Bottom of Navigation Drawer
                        new SecondaryDrawerItem()
                                .withName(getResources().getString(R.string.drawer_about))
                                .withIdentifier(DRAWER_ITEM_ACERCA)
                                .withIcon(FontAwesome.Icon.faw_info_circle)
                                .withTextColor(getResources().getColor(R.color.text))
                                .withIconColor(getResources().getColor(R.color.text))
                                .withSelectedTextColor(getResources().getColor(R.color.primary))
                                .withSelectedIconColor(getResources().getColor(R.color.primary))
                                .withSelectable(false),
                        new SecondaryDrawerItem()
                                .withName(getResources().getString(R.string.drawer_logout))
                                .withIdentifier(DRAWER_ITEM_LOG_OUT)
                                .withIcon(FontAwesome.Icon.faw_sign_out)
                                .withTextColor(getResources().getColor(R.color.colorPrimaryDark))
                                .withIconColor(getResources().getColor(R.color.colorPrimaryDark))
                                .withSelectedTextColor(getResources().getColor(R.color.primary))
                                .withSelectedIconColor(getResources().getColor(R.color.primary))
                                .withSelectable(false)
                )
                //Accion Click over items
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        selectItem((int)drawerItem.getIdentifier());
                        return false;
                    }
                })
                .withSelectedItem(1)
                .withSavedInstance(savedInstanceState)
                .build();

        selectItem(DRAWER_ITEM_PRODUCTOS);
    }

    public void selectItem(int idMenu) {
        String title = "";
        Fragment f = new FragmentProductos();
        Bundle args = new Bundle();

        switch (idMenu) {
            case DRAWER_ITEM_PRODUCTOS:
                title = getResources().getString(R.string.drawer_productos);
                f = new FragmentProductos();
                break;
            case DRAWER_ITEM_CARRITO:
                title = getResources().getString(R.string.drawer_carrito);
                f = new FragmentCarrito();
                break;
            case DRAWER_ITEM_GESTION_PRODUCTOS:
                title = getResources().getString(R.string.drawer_gestion_productos);
                f = new FragmentGestionProductos();
                break;
            case DRAWER_ITEM_LISTA_PEDIDOS:
                title = getResources().getString(R.string.drawer_lista_pedidos);
                f = new FragmentListaPedidos();
                break;
            case DRAWER_ITEM_LISTA_ENTREGAS:
                title = getResources().getString(R.string.drawer_lista_entregas);
                f = new FragmentListaEntregas();
                break;
            case DRAWER_ITEM_ACERCA:
                title = getResources().getString(R.string.drawer_about);
                f = new FragmentAcerca();
                break;
            case DRAWER_ITEM_LOG_OUT:
                signOut();
                break;
        }
        toolbar.setTitle(title);
        f.setArguments(args);

        FragmentManager fm = getSupportFragmentManager();
        Fragment oldFragment = getSupportFragmentManager().findFragmentById(R.id.flContainer);
        if (oldFragment != null) {
            fm.beginTransaction()
                    .remove(oldFragment)
                    .addToBackStack(f.getClass().getSimpleName())
                    .replace(R.id.flContainer, f)
                    .commit();
        } else
            fm.beginTransaction()
                    .addToBackStack(f.getClass().getSimpleName())
                    .replace(R.id.flContainer, f)
                    .commit();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (drawer != null) {
            outState = drawer.saveInstanceState(outState);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawer.isDrawerOpen())
                drawer.closeDrawer();
            else
                drawer.openDrawer();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
            //moveTaskToBack(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Log.e("LoginError","aca");
                try
                {
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    User user=new User();
                    user.setIdUser(currentUser.getUid());
                    user.setDisplayName(currentUser.getDisplayName());
                    user.setPhotoUrl(String.valueOf(currentUser.getPhotoUrl()));

                    SharedPreferencesData prefs=new SharedPreferencesData(context);
                    prefs.setUserData(user);

                    databaseReference.child("users").child(currentUser.getUid()).setValue(user);

                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                catch (Exception ex){
                    Log.e("LoginError",""+ex.getMessage());
                }
            } else {
                Log.e(TAG, "Ocurrió un error durante el login");
            }
        }
    }

    private void signOut() {
        AuthUI.getInstance().signOut(MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    int count=0;
    private void actualizarCarrito(){
        drawer.updateBadge(DRAWER_ITEM_CARRITO,new StringHolder(String.valueOf(count)));
        count++;
    }
}
