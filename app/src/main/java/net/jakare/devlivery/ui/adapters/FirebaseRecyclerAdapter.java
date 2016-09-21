package net.jakare.devlivery.ui.adapters;

import android.view.View;

import com.google.firebase.database.DatabaseReference;

import net.jakare.devlivery.R;
import net.jakare.devlivery.model.dbClasses.Producto;
import net.jakare.devlivery.ui.viewholders.ItemViewHolder;

/**
 * Created by armando on 7/15/16.
 */
public class FirebaseRecyclerAdapter extends com.firebase.ui.database.FirebaseRecyclerAdapter<Producto, ItemViewHolder> {

    DatabaseReference databaseReference;
    Response response;

    public FirebaseRecyclerAdapter(DatabaseReference ref, Response response){
        super(Producto.class, R.layout.layout_producto, ItemViewHolder.class, ref);
        databaseReference = ref;
        this.response=response;
    }

    @Override
    protected void populateViewHolder(ItemViewHolder viewHolder, final Producto model, int position) {
        viewHolder.setTitulo(model.getNombre());
        viewHolder.setPrecio(model.getPrecio());
        viewHolder.setImagen(model.getFoto());

        viewHolder.setClickItem(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                response.onClickItem(model);
            }
        });
    }

    public interface Response{
        public void onClickItem(Producto itemGlobal);
    }
}
