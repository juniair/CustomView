package com.juniair.recodecardview.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juniair.recodecardview.R;

/**
 * Created by juniair on 2016-08-03.
 */
public class RecodeViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public ImageView thumbnail, overflow;
    private View itemView;

    public RecodeViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }


    /**
     * 해당 메소드는 UI를 설정 하는 메소드 이다.
     * @param title 동영상 제목 ID
     * @param thumbnail 동영상 썸네일 ID
     * @param overflow  동영상 삭제 혹은 보기 ID
     */
    public void setViewHolder(int title, int thumbnail, int overflow) {
        this.title = (TextView) itemView.findViewById(title);
        this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        this.overflow = (ImageView) itemView.findViewById(R.id.overflow);
    }
}
