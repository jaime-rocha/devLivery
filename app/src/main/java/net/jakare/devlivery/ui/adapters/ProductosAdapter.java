package net.jakare.devlivery.ui.adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.jakare.devlivery.R;
import net.jakare.devlivery.model.dbClasses.Producto;
import net.jakare.devlivery.ui.viewholders.ProductosHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andresvasquez on 7/5/16.
 */

/***
 * Adapter to show bids in ListViews of fragments
 */
public class ProductosAdapter extends ArrayAdapter<Producto>
{
    private LayoutInflater lf;
    private List<ProductosHolder> lstHolders;
    private Handler mHandler = new Handler();

    private Context context;

    public ProductosAdapter(Context context, List<Producto> objects) {
        super(context, 0, objects);
        this.context=context;
        lf = LayoutInflater.from(context);
        lstHolders = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductosHolder holder = null;
        if (convertView == null)
        {
            holder = new ProductosHolder();
            convertView = lf.inflate(R.layout.layout_producto, parent, false);
            holder.imgImagen=(ImageView)convertView.findViewById(R.id.imgImagen);
            holder.lblTitulo = (TextView) convertView.findViewById(R.id.lblTitulo);
            holder.lblPrecio = (TextView) convertView.findViewById(R.id.lblPrecio);

            convertView.setTag(holder);
            synchronized (lstHolders) {
                lstHolders.add(holder);
            }
        }
        else
        {
            holder = (ProductosHolder) convertView.getTag();
        }

        holder.setData(context,getItem(position));

        return convertView;
    }
}
