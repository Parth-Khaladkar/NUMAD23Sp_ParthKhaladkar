package edu.northeastern.numad23sp_parthkhaladkar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LinkCollectorAdapter extends RecyclerView.Adapter<LinkCollectorViewHolder> {

    private ArrayList<WebLink> link_list;
    private LinkClickListener linkClickListener;

    public LinkCollectorAdapter(ArrayList<WebLink> linkUnitList)
    {
        this.link_list = linkUnitList;
    }

    public void setOnLinkClickListener(LinkClickListener linkClickListener) {
        this.linkClickListener = linkClickListener;
    }

    @NonNull
    @Override
    public LinkCollectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // inflating the layout and giving look to each of our rows
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_weblink_unit, parent, false);
        return new LinkCollectorViewHolder(view, linkClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkCollectorViewHolder linkCollectorViewHolder, int position) {
        // assigning values to each of our rows as they come back to the screen
        // based on the position of the recycler view


        WebLink currentLinkItem = link_list.get(position);
        linkCollectorViewHolder.linkName.setText(currentLinkItem.getName());
        linkCollectorViewHolder.linkUrl.setText(currentLinkItem.getLinkUrl());
    }

    @Override
    //how many items do you have in total
    public int getItemCount()
    {
        return link_list.size();
    }


    public interface LinkClickListener {
        void onLinkClick(int position);
    }
}