package dalejan.nirmalkar.cropimage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by DALEJAN1 on 27-08-2017.
 */

public class AdapterRecycle extends RecyclerView.Adapter<AdapterRecycle.Photoos> {
public class Photoos extends RecyclerView.ViewHolder{
public ImageView pp;
    public Photoos(View itemView) {
        super(itemView);
        pp=itemView.findViewById(R.id.jjk);
    }
}

    @Override
    public AdapterRecycle.Photoos onCreateViewHolder(ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout, parent, false);
        return new Photoos(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecycle.Photoos holder, int position) {
    holder.pp.setImageBitmap(ImageAdapter.bitmaps.get(PhotoGrid.jj.get(position)));

    }

    @Override
    public int getItemCount() {
        return PhotoGrid.jj.toArray().length;

    }
}
