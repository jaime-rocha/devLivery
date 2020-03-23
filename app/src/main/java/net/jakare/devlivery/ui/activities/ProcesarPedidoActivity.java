package net.jakare.devlivery.ui.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.jakare.devlivery.R;
import net.jakare.devlivery.controller.data.SharedPreferencesData;
import net.jakare.devlivery.controller.server.FirebasePedidosController;
import net.jakare.devlivery.model.appClasses.Carrito;
import net.jakare.devlivery.model.dbClasses.Pedido;
import net.jakare.devlivery.model.dbClasses.User;
import net.jakare.devlivery.utils.constants.AppConstants;
import net.jakare.devlivery.utils.global.GlobalFunctions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ProcesarPedidoActivity extends AppCompatActivity implements View.OnClickListener{

    private Context context;

    private EditText txtNombreCompleto;
    private EditText txtDireccion;
    private EditText txtTelefono;
    private EditText txtObservaciones;

    private Button btnEnviar;
    private List<Carrito> lstCarrito;

    private double lat=0;
    private double lon=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procesar_pedido);

        context=this;
        initViews();

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.datos_pedido));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        btnEnviar.setOnClickListener(this);

        Intent intent=getIntent();
        if(intent.hasExtra(AppConstants.TAG_ITEM_PEDIDO)){
            Type listType = new TypeToken<ArrayList<Carrito>>(){}.getType();

            lstCarrito = new Gson().fromJson(intent.getStringExtra(AppConstants.TAG_ITEM_PEDIDO), listType);
        }
        else {
            finish();
        }
    }

    private void initViews(){
        txtNombreCompleto=(EditText)findViewById(R.id.txtNombreCompleto);
        txtDireccion=(EditText)findViewById(R.id.txtDireccion);
        txtTelefono=(EditText)findViewById(R.id.txtTelefono);
        txtObservaciones=(EditText)findViewById(R.id.txtObservaciones);

        btnEnviar=(Button)findViewById(R.id.btnEnviar);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnEnviar){
            String nombreCompleto=txtNombreCompleto.getText().toString().trim();
            String direccion=txtDireccion.getText().toString().trim();
            String telefono=txtTelefono.getText().toString().trim();
            String observaciones=txtObservaciones.getText().toString().trim();

            if(nombreCompleto.isEmpty()){
                txtNombreCompleto.setError(getResources().getString(R.string.error_nombre));
                return;
            }

            if(direccion.isEmpty()){
                txtDireccion.setError(getResources().getString(R.string.error_direccion));
                return;
            }

            if(telefono.isEmpty()){
                txtTelefono.setError(getResources().getString(R.string.error_telefono));
                return;
            }


            SharedPreferencesData prefs=new SharedPreferencesData(context);
            User user=prefs.getUserData();

            Pedido pedido=new Pedido();
            pedido.setUsuarioId(user.getIdUser());
            pedido.setUsuario(nombreCompleto);
            pedido.setDireccion(direccion);
            pedido.setCarrito(lstCarrito);
            pedido.setFechaPedido(GlobalFunctions.getDate());
            pedido.setObservaciones(observaciones);
            pedido.setTelefono(telefono);
            pedido.setEstado(AppConstants.ESTADO_PREPARACION);
            pedido.setLat(lat);
            pedido.setLon(lon);

            double monto=0;
            for (Carrito carrito : lstCarrito)
                monto+=carrito.getSubtotal();

            pedido.setMonto(monto);

            showProgressDialog();
            FirebasePedidosController pedidosController=new FirebasePedidosController(this,
                    new FirebasePedidosController.ResultadoGestion() {
                        @Override
                        public void onResponse(int codigoResultado, String mensaje) {
                            hideProgressDialog();

                            Toast.makeText(context,mensaje, Toast.LENGTH_SHORT).show();
                            if(codigoResultado==AppConstants.RESULTADO_CORRECTO){
                                strImprimir=CrearMensajeImpresion();
                                RecursivoImprimir();
                            }
                        }
                    });
            pedidosController.CrearPedido(pedido);
        }
    }

    private ProgressDialog progressDialog;

    protected void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.progress_dialog_loading));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            ProcesarPedidoActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }



    private void RecursivoImprimir()
    {
        try
        {
            if(findBT()) {
                openBT();
                sendData();
                closeBT();
            }
            else
            {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent main=new Intent(context,MainActivity.class);
                                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                ProcesarPedidoActivity.this.finish();
                                startActivity(main);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                RecursivoImprimir();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(ProcesarPedidoActivity.this);
                builder.setTitle("Problemas con la impresora Bluetooth");
                builder.setMessage("Desea finalizar el pedido sin imprimir la boleta?\nPosteriormente puede imprimirla desde el " +
                        "Historial de Pedidos")
                        .setPositiveButton("Si", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        } catch (IOException ex) {
        }
    }



    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;

    private String strImprimir="";
    private String nombreBT="";

    // This will find a bluetooth printer device
    private boolean findBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (mBluetoothAdapter == null) {
                Toast.makeText(context,"No se encontró Bluetooth en el dispositivo", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
                return false;
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                    .getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices)
                {
                    Log.e("BT device", device.getName());
                    // MP300 is the name of the bluetooth printer device
                    nombreBT=device.getName();
                    if (device.getName().equals("MP300")) {
                        mmDevice = device;
                        break;
                    }
                    else if(device.getName().equals("BMV2")) {
                        mmDevice = device;
                        break;
                    }
                    else if(device.getName().equals("P25_058036_01")) {
                        mmDevice = device;
                        break;
                    }
                }
                if(mmDevice!=null)
                    return true;
            }
            //Toast.makeText(context,"Impresora Bluetooth no disponible, asegúrese de vincularla antes. La clave por defecto es 1234", Toast.LENGTH_LONG).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Tries to open a connection to the bluetooth printer device
    private void openBT() throws IOException
    {
        try
        {
            if(nombreBT.compareTo("P25_058036_01")!=0) {
                // Standard SerialPortService ID
                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
                mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
                mmSocket.connect();
                mmOutputStream = mmSocket.getOutputStream();
                mmInputStream = mmSocket.getInputStream();

                Log.e("PC1","0");
                beginListenForData();
            }
            else
            {
                Log.e("PC1","11");
                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                Log.e("PC1","12");
                mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
                Log.e("PC1", "13");
                mmSocket.connect();
                Log.e("PC1", "14");
                mmOutputStream = mmSocket.getOutputStream();
                Log.e("PC1","15");
                mmInputStream = mmSocket.getInputStream();
                Log.e("PC1", "16");
                //beginListenForData();
                Log.e("PC1", "17");
            }
            //myLabel.setText("Bluetooth Opened");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("PC1","Error"+e.getMessage());
            e.printStackTrace();
        }
    }

    // After opening a connection to bluetooth printer device,
    // we have to listen and check if a data were sent to be printed.
    private void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // This is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {
                    while (!Thread.currentThread().isInterrupted()
                            && !stopWorker) {

                        try
                        {
                            int bytesAvailable = mmInputStream.available();
                            if (bytesAvailable > 0) {
                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);
                                for (int i = 0; i < bytesAvailable; i++) {
                                    byte b = packetBytes[i];
                                    if (b == delimiter) {
                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length);
                                        final String data = new String(
                                                encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        handler.post(new Runnable() {
                                            public void run() {
                                                //myLabel.setText(data);
                                            }
                                        });
                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            Log.e("PC1", "18");
            workerThread.start();
            Log.e("PC1", "19");
        } catch (NullPointerException e) {
            Log.e("PC2","Error"+e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PC3", "Error" + e.getMessage());
        }
    }

    /*
     * This will send data to be printed by the bluetooth printer
     */
    private  void sendData() throws IOException {
        try
        {
            Log.e("PC1", "20");
            // the text typed by the user
            String msg = "";
            msg += "\n";
            msg +=strImprimir;
            msg += "\n\n\n";
            Log.e("PC1", "21");
            mmOutputStream.write(msg.getBytes());
            Log.e("PC1", "22");
            // tell the user data were sent
            Toast.makeText(context,"Boleta impresa", Toast.LENGTH_SHORT).show();

            Intent main=new Intent(context,MainActivity.class);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            ProcesarPedidoActivity.this.finish();
            startActivity(main);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Close the connection to bluetooth printer.
    private  void closeBT() throws IOException {
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            Log.e("ResumenInfraccion", "Bluetooth Closed");
            //myLabel.setText();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String CrearMensajeImpresion()
    {
        String strImprimir="";
        strImprimir="  ************ - ***********  \n" +
                "PEDIDO\n" +
                "  ************ - ***********  \n" +
                "Cliente: "+txtNombreCompleto.getText().toString()+"\n" +
                "Observaciones: <b>"+txtObservaciones.getText().toString()+"</b>\n" +
                "Institucion: SEDEM\n" +
                "Piso: 3\n" +
                "Area: Informatica\n" +
                "Fecha: "+GlobalFunctions.getDate()+"\n" +
                "  ************ PEDIDO ***********  \n";
        double total=0;
        for(Carrito carrito : lstCarrito){
            strImprimir+="Producto: "+carrito.getProducto().getNombre()+"\n" +
                    "Cantidad: "+carrito.getCantidad()+"\n"+
                    "Monto: "+carrito.getSubtotal()+"bs\n";
            total+=carrito.getSubtotal();
        }
        strImprimir+="  ************ - ***********  \n" +
                "TOTAL: "+total+"\n"+
                "-------------------------------\n\n\n\n" +
                "-------------------------------\n" +
                "             Firma\n"+
                "-------------------------------\n" +
                "Vendedor: Juan Perez\n"+
                "Concesionarias Manqa\n";
        return strImprimir;
    }
}
