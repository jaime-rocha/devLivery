package net.jakare.devlivery.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.jakare.devlivery.R;
import net.jakare.devlivery.utils.global.GlobalFunctions;

/**
 * Created by armando on 7/15/16.
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {
    private View view;

    public ItemViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
    }

    public void setTitulo(String text) {
        TextView textViewName = (TextView) view.findViewById(R.id.lblTitulo);
        textViewName.setText(text);
    }

    public void setPrecio(double precio) {
        TextView textViewName = (TextView) view.findViewById(R.id.lblPrecio);
        textViewName.setText(GlobalFunctions.format2Digits(precio));
    }

    public void setImagen(String url) {
        if (url != null) {
            ImageView imageViewAvatar = (ImageView) view.findViewById(R.id.imgImagen);
            Glide.with(view.getContext())
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.img_no_disponible)
                    .crossFade()
                    .into(imageViewAvatar);
        }
    }

    public void setClickItem(View.OnClickListener callback) {
        RelativeLayout lyItem = (RelativeLayout) view.findViewById(R.id.lyItem);
        lyItem.setOnClickListener(callback);
    }
}
