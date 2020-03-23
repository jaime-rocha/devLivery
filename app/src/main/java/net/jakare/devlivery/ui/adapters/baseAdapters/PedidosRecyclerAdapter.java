package net.jakare.devlivery.ui.adapters.baseAdapters;

import android.support.v7.widget.RecyclerView;

import net.jakare.devlivery.model.dbClasses.Pedido;

/**
 * Created by andresvasquez on 21/9/16.
 */

public  abstract class PedidosRecyclerAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    public abstract Pedido getItem(int position);
}
