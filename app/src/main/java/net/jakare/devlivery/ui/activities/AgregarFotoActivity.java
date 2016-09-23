package net.jakare.devlivery.ui.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import net.jakare.devlivery.R;
import net.jakare.devlivery.model.dbClasses.Producto;
import net.jakare.devlivery.utils.constants.AppConstants;
import net.jakare.devlivery.utils.global.GlobalFunctions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class AgregarFotoActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks{

    private static final String TAG=AgregarFotoActivity.class.getSimpleName();

    private Context context;
    private SharedPreferences preferences;

    private Producto producto;

    private Button btnAtras;
    private Button btnContinuar;
    private Button btnAdjuntar;

    private ImageView imgAdjuntar;
    private File fileImagen;

    private static final int RC_TAKE_PICTURE = 101;
    private Uri fileUri = null;
    public static final int REQUEST_CODE_ATTACH =103;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_foto);

        context=this;
        preferences= PreferenceManager.getDefaultSharedPreferences(context);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent=getIntent();
        if(intent.hasExtra(AppConstants.TAG_PRODUCTO)){
            producto=new Gson().fromJson(intent.getStringExtra(AppConstants.TAG_PRODUCTO),Producto.class);

            initViews();

            btnAtras.setOnClickListener(this);
            btnContinuar.setOnClickListener(this);
            btnAdjuntar.setOnClickListener(this);
            imgAdjuntar.setOnClickListener(this);

            //Imagen adjuntar icono
            imgAdjuntar.setImageDrawable(new IconicsDrawable(context, FontAwesome.Icon.faw_picture_o)
                    .color(Color.WHITE)
                    .backgroundColorRes(R.color.hint)
                    .sizePxX(640)
                    .sizePxY(480)
                    .paddingDp(40));
        }
        else {
            finish();
        }
    }

    private void initViews() {
        btnAtras=(Button)findViewById(R.id.btnAtras);
        btnContinuar=(Button)findViewById(R.id.btnContinuar);
        btnAdjuntar=(Button)findViewById(R.id.btnAdjuntar);

        imgAdjuntar=(ImageView)findViewById(R.id.imgAdjuntar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnContinuar:
                enviarDatos();
                break;
            case R.id.btnAtras:
                finish();
                break;
            case R.id.btnAdjuntar:
                adjuntarFoto();
                break;
            case R.id.imgAdjuntar:
                adjuntarFoto();
                break;
            default:
                break;
        }
    }

    private void enviarDatos() {
        if(fileImagen==null){
            Toast.makeText(context,getResources().getString(R.string.adjuntar),Toast.LENGTH_SHORT).show();
        }
        else {
            showProgressDialog();
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReferenceFromUrl(AppConstants.TAG_STORAGE);
            Uri fileUri=Uri.fromFile(fileImagen);

            final StorageReference photoReference = storageReference.child(AppConstants.TAG_IMAGENES_PRODUCTOS).child(fileUri.getLastPathSegment());
            photoReference.putFile(fileUri).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("Firebase", "uploadFromUri:onFailure", e);

                    Toast.makeText(context,getResources().getString(R.string.error_adjuntar),Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                }
            }).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("Firebase", "uploadFromUri:onSuccess");

                    producto.setFechaCreacion(GlobalFunctions.getDate());
                    producto.setFoto(taskSnapshot.getMetadata().getDownloadUrl().toString());

                    //Firebase upload object
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child(AppConstants.TAG_PRODUCTO).push().setValue(producto, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            hideProgressDialog();

                            Toast.makeText(context,getResources().getString(R.string.registro_correcto),Toast.LENGTH_SHORT).show();
                            Intent main=new Intent(context,MainActivity.class);
                            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            AgregarFotoActivity.this.finish();
                            startActivity(main);
                        }
                    });
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            AgregarFotoActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String strDate = GlobalFunctions.getDate("yyyyMMdd_hhmmss");

        if(resultCode == RESULT_OK)
        {
            if (requestCode == REQUEST_CODE_ATTACH && data != null && data.getData() != null) {
                Uri uri = data.getData();
                FileOutputStream out;
                String strPhotoName = strDate + "_attach.jpg";

                try
                {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    File folder = new File(Environment.getExternalStorageDirectory(),
                            AppConstants.DIRECTORY_NAME);
                    if(!folder.exists()){
                        folder.mkdirs();
                    }

                    File file = new File(Environment.getExternalStorageDirectory(),
                            AppConstants.DIRECTORY_NAME + File.separator + strPhotoName);
                    out = new FileOutputStream(file);
                    Matrix m = new Matrix();
                    m.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                            new RectF(0, 0, 640, 480), Matrix.ScaleToFit.CENTER);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), m, true);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                    //out.write(data);
                    out.flush();
                    out.close();

                    fileImagen=file;
                    Picasso.with(context).load(file).memoryPolicy(MemoryPolicy.NO_CACHE)
                            .skipMemoryCache().into(imgAdjuntar);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "IOException: " + e.getMessage());
                }
            }
        }
        else {
            Toast.makeText(this, "Error al obtener imagen.", Toast.LENGTH_SHORT).show();
        }
    }

    @AfterPermissionGranted(REQUEST_CODE_ATTACH)
    private void adjuntarFoto() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !EasyPermissions.hasPermissions(this, permissions)) {
            EasyPermissions.requestPermissions(this, getString(R.string.adjuntar),REQUEST_CODE_ATTACH, permissions);
            return;
        }

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, context.getResources().getString(R.string.adjuntar)), REQUEST_CODE_ATTACH);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    private ProgressDialog progressDialog;

    protected void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.progress_dialog_loading));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(true);
            progressDialog.setMax(100);
        }
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
