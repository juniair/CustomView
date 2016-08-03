package com.juniair.recodecardview.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.juniair.recodecardview.R;
import com.juniair.recodecardview.model.Recode;
import com.juniair.recodecardview.util.EventHandler;
import com.juniair.recodecardview.util.RecodeViewHolder;

import java.util.List;

/**
 * Created by juniair on 2016-08-03.
 */
public class RecodeAdapter extends RecyclerView.Adapter<RecodeViewHolder>{

    private Context mContext;
    private List<Recode> mRecodes;
    private int resouceTitleID, resouceThubnaiID, resouceOverflowID;
    private EventHandler eventHandler;
    /**
     *
     * @param mContext
     * @param mRecodes  List 객체
     * @param resouceTitleID 제목 리소스 ID
     * @param resouceThubnaiID  썸네일 리소스 ID
     * @param resouceOverflowID 속성 리소스 ID
     */
    public RecodeAdapter(Context mContext, List<Recode> mRecodes, int resouceTitleID, int resouceThubnaiID, int resouceOverflowID) {
        this.mContext = mContext;
        this.mRecodes = mRecodes;
        this.resouceTitleID = resouceTitleID;
        this.resouceThubnaiID = resouceThubnaiID;
        this.resouceOverflowID = resouceOverflowID;
    }



    @Override
    public RecodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recode_card, parent, false);
        return new RecodeViewHolder(itemView, resouceTitleID, resouceThubnaiID, resouceOverflowID);
    }

    @Override
    public void onBindViewHolder(final RecodeViewHolder holder, int position) {
        Recode recode = mRecodes.get(position);
        holder.title.setText(recode.getName());
        Glide.with(mContext).load(recode.getThumbnailURL()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eventHandler == null)
                    return;
                showPopupMenu(holder.overflow);
            }
        });

    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_reocode, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int i = item.getItemId();
                if (i == R.id.action_play) {

                    return true;
                } else if (i == R.id.action_delete) {

                    return true;
                } else {
                    return false;
                }
            }
        });
        popupMenu.show();
    }


    @Override
    public int getItemCount() {
        return mRecodes.size();
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }


}
