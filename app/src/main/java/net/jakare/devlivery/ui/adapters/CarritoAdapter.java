package net.jakare.devlivery.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.jakare.devlivery.R;
import net.jakare.devlivery.model.appClasses.Carrito;
import net.jakare.devlivery.ui.adapters.baseAdapters.CarritoRecyclerAdapter;
import net.jakare.devlivery.utils.global.GlobalFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andresvasquez on 7/5/16.
 */

/***
 * Adapter to show bids in ListViews of fragments
 */
public class CarritoAdapter extends CarritoRecyclerAdapter
{
    private static final String TAG=CarritoAdapter.class.getSimpleName();

    private List<Carrito> items;
    private OnItemClickListener onItemClickListener;
    private boolean mostarImagen=true;

    private Context context;

    public CarritoAdapter(Context context, boolean mostarImagen) {
        this.items = new ArrayList<Carrito>();
        this.context=context;
        this.mostarImagen=mostarImagen;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView lyItem;
        public ImageView imgImagen;
        public TextView lblTitulo;
        public TextView lblPrecioUnitario;
        public TextView lblCantidad;
        public TextView lblSubtotal;

        public ViewHolder(View v) {
            super(v);
            lyItem=(CardView)v.findViewById(R.id.lyItem);
            imgImagen=(ImageView)v.findViewById(R.id.imgImagen);
            lblTitulo = (TextView) v.findViewById(R.id.lblTitulo);
            lblPrecioUnitario = (TextView) v.findViewById(R.id.lblPrecioUnitario);
            lblCantidad = (TextView) v.findViewById(R.id.lblCantidad);
            lblSubtotal = (TextView) v.findViewById(R.id.lblSubtotal);
        }
    }

    @Override
    public Carrito getItem(int position) {
        return items.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_carrito, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder=(ViewHolder)holder;
        final Carrito carrito=items.get(position);

        viewHolder.lblTitulo.setText(carrito.getProducto().getNombre());
        String precioUnitario=context.getResources().getString(R.string.carrito_precio_unitario)+
                GlobalFunctions.format2Digits(carrito.getProducto().getPrecio());
        viewHolder.lblPrecioUnitario.setText(precioUnitario);
        String cantidad=context.getResources().getString(R.string.carrito_cantidad)+
                carrito.getCantidad();
        viewHolder.lblCantidad.setText(cantidad);
        viewHolder.lblSubtotal.setText(GlobalFunctions.format2Digits(carrito.getSubtotal()));

        if(mostarImagen){
            if (carrito.getProducto().getFoto() != null) {
                Glide.with(context)
                        .load(carrito.getProducto().getFoto())
                        .centerCrop()
                        .placeholder(R.drawable.img_no_disponible)
                        .crossFade()
                        .into(viewHolder.imgImagen);
            }
        }

        viewHolder.lyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final OnItemClickListener listener = getOnItemClickListener();
                if(listener != null){
                    listener.onItemClick(carrito);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void swapCursor(List<Carrito> pedidos){
        clearItems();

        if(!pedidos.isEmpty()){
            items.addAll(pedidos);
            notifyItemRangeInserted(0,items.size());
        }
    }

    private void clearItems(){
        int itemsSize=items.size();
        items.clear();
        notifyItemRangeRemoved(0,itemsSize);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }
    public OnItemClickListener getOnItemClickListener(){
        return onItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(Carrito item);
    }
}
