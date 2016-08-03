package com.juniair.recodecardview.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juniair.recodecardview.R;

/**
 * Created by juniair on 2016-08-03.
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public ImageView tumbnail, overflow;
    public ViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        tumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        overflow = (ImageView) itemView.findViewById(R.id.overflow);
    }
}
