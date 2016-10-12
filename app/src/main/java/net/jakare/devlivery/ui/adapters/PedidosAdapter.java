package net.jakare.devlivery.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.jakare.devlivery.R;
import net.jakare.devlivery.model.dbClasses.Pedido;
import net.jakare.devlivery.ui.adapters.baseAdapters.PedidosRecyclerAdapter;
import net.jakare.devlivery.utils.global.GlobalFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andresvasquez on 7/5/16.
 */

/***
 * Adapter to show bids in ListViews of fragments
 */
public class PedidosAdapter extends PedidosRecyclerAdapter
{
    private static final String TAG=PedidosAdapter.class.getSimpleName();

    private List<Pedido> items;
    private OnItemClickListener onItemClickListener;

    private Context context;

    public PedidosAdapter(Context context) {
        this.items = new ArrayList<Pedido>();
        this.context=context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView lyItem;
        public TextView lblNombrePersona;
        public TextView lblDireccion;
        public TextView lblTelefono;
        public TextView lblPrecio;

        public ViewHolder(View v) {
            super(v);
            lyItem=(CardView)v.findViewById(R.id.lyItem);
            lblNombrePersona = (TextView) v.findViewById(R.id.lblNombrePersona);
            lblDireccion = (TextView) v.findViewById(R.id.lblDireccion);
            lblTelefono = (TextView) v.findViewById(R.id.lblTelefono);
            lblPrecio = (TextView) v.findViewById(R.id.lblMonto);
        }
    }

    @Override
    public Pedido getItem(int position) {
        return items.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pedido, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder=(ViewHolder)holder;
        final Pedido pedido=items.get(position);

        viewHolder.lblNombrePersona.setText(pedido.getUsuario());
        viewHolder.lblDireccion.setText(pedido.getDireccion());
        viewHolder.lblTelefono.setText(pedido.getUsuario());
        viewHolder.lblNombrePersona.setText(pedido.getUsuario());
        viewHolder.lblPrecio.setText(GlobalFunctions.format2Digits(pedido.getMonto()));
        viewHolder.lyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final OnItemClickListener listener = getOnItemClickListener();
                if(listener != null){
                    listener.onItemClick(pedido);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void swapCursor(List<Pedido> pedidos){
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
        public void onItemClick(Pedido item);
    }
}
