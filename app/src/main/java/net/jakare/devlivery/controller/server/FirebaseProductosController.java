package net.jakare.devlivery.controller.server;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import net.jakare.devlivery.R;
import net.jakare.devlivery.model.dbClasses.Producto;
import net.jakare.devlivery.utils.constants.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andresvasquez on 24/9/16.
 */

public class FirebaseProductosController {

    private Activity activity;
    private ResultadoGestion callBackGestion;
    private ResultadoLista callBackLista;

    public FirebaseProductosController(Activity activity, ResultadoGestion callBackGestion) {
        this.activity = activity;
        this.callBackGestion = callBackGestion;
    }

    public FirebaseProductosController(Activity activity, ResultadoLista callBackLista) {
        this.activity = activity;
        this.callBackLista = callBackLista;
    }

    public void AdjuntarFoto(Uri uriFoto) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl(AppConstants.TAG_STORAGE);
        final StorageReference photoReference = storageReference.child(AppConstants.TAG_IMAGENES_PRODUCTOS).child(uriFoto.getLastPathSegment());
        photoReference.putFile(uriFoto).addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBackGestion.onResponse(AppConstants.RESULTADO_INCORRECTO, activity.getResources().getString(R.string.error_adjuntar));
            }
        }).addOnSuccessListener(activity, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                photoReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e("Photo", uri.toString());
                        callBackGestion.onResponse(AppConstants.RESULTADO_CORRECTO,
                                uri.toString());
                    }
                });
            }
        });
    }

    public void CrearProducto(Producto producto) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(AppConstants.TAG_PRODUCTO).push().setValue(producto, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    callBackGestion.onResponse(AppConstants.RESULTADO_INCORRECTO, databaseError.getMessage());
                } else {
                    callBackGestion.onResponse(AppConstants.RESULTADO_CORRECTO, activity.getResources().getString(R.string.registro_correcto));
                }
            }
        });
    }

    public void ListasProductos(String categoria) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        Query ref;
        if (!categoria.isEmpty()) {
            ref = databaseReference.child(AppConstants.TAG_PRODUCTO)
                    .orderByChild(AppConstants.TAG_CATEGORIA).equalTo(categoria);
        } else {
            ref = databaseReference.child(AppConstants.TAG_PRODUCTO);
        }

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Producto> lstProductos = new ArrayList<Producto>();
                for (DataSnapshot dataProducto : dataSnapshot.getChildren()) {
                    Producto producto = dataProducto.getValue(Producto.class);
                    producto.setKey(dataProducto.getKey());
                    lstProductos.add(producto);
                }

                Log.e("Categoria", new Gson().toJson(lstProductos));

                callBackLista.onResponse(lstProductos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    public interface ResultadoGestion {
        public void onResponse(int codigoResultado, String mensaje);
    }

    public interface ResultadoLista {
        public void onResponse(List<Producto> lstProductos);
    }
}
