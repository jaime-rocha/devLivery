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
import net.jakare.devlivery.model.dbClasses.Producto;
import net.jakare.devlivery.ui.adapters.baseAdapters.ProductosRecyclerAdapter;

import java.util.List;

/**
 * Created by andresvasquez on 7/5/16.
 */

/***
 * Adapter to show bids in ListViews of fragments
 */
public class GestionProductosAdapter extends ProductosRecyclerAdapter
{
    private static final String TAG=GestionProductosAdapter.class.getSimpleName();

    private Context context;
    private List<Producto> items;
    private OnItemClickListener onItemClickListener;

    public GestionProductosAdapter(List<Producto> items,Context context) {
        this.items = items;
        this.context=context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView lyItem;
        public ImageView imgImagen;
        public TextView lblTitulo;
        public TextView lblPrecio;

        public ViewHolder(View v) {
            super(v);
            lyItem=(CardView)v.findViewById(R.id.lyItem);
            imgImagen=(ImageView)v.findViewById(R.id.imgImagen);
            lblTitulo = (TextView) v.findViewById(R.id.lblTitulo);
            lblPrecio = (TextView) v.findViewById(R.id.lblPrecio);
        }
    }

    @Override
    public Producto getItem(int position) {
        return items.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_gestion_producto, parent, false);
        return new GestionProductosAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder=(ViewHolder)holder;
        final Producto producto=items.get(position);

        viewHolder.lblTitulo.setText(producto.getNombre());
        viewHolder.lblPrecio.setText(String.valueOf(producto.getPrecio()));
        if (producto.getFoto() != null) {
            Glide.with(context)
                    .load(producto.getFoto())
                    .centerCrop()
                    .placeholder(R.drawable.img_no_disponible)
                    .crossFade()
                    .into(viewHolder.imgImagen);
        }
        viewHolder.lyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final OnItemClickListener listener = getOnItemClickListener();
                if(listener != null){
                    listener.onItemClick(producto);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void swapCursor(List<Producto> productos){
        clearItems();
        if(!productos.isEmpty()){
            items.addAll(productos);
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
        public void onItemClick(Producto item);
    }
}
