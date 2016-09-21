package net.jakare.devlivery.ui.viewholders;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.jakare.devlivery.R;
import net.jakare.devlivery.model.dbClasses.Producto;
import net.jakare.devlivery.utils.global.GlobalFunctions;

/**
 * Created by andresvasquez on 27/7/16.
 */

public class GestionProductosHolder {
    public RelativeLayout lyItem;
    public ImageView imgImagen;
    public TextView lblTitulo;
    public TextView lblPrecio;

    public Producto mBidItem;

    /**
     * Show data in the layout
     * @param context
     * @param item BidItem object
     */
    public void setData(Context context, Producto item) {
        mBidItem = item;


        lblTitulo.setText(mBidItem.getNombre());
        lblPrecio.setText(GlobalFunctions.format2Digits(mBidItem.getPrecio()));
        if (mBidItem.getFoto() != null) {
            Glide.with(context)
                    .load(mBidItem.getFoto())
                    .centerCrop()
                    .placeholder(R.drawable.img_no_disponible)
                    .crossFade()
                    .into(imgImagen);
        }
    }
}
