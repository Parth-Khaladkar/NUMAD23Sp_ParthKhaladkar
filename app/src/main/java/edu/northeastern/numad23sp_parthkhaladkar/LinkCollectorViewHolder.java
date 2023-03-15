package edu.northeastern.numad23sp_parthkhaladkar;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LinkCollectorViewHolder extends RecyclerView.ViewHolder {
    public TextView linkName;   // from weblink unit .xml
    public TextView linkUrl;

    // grabbing views from our recyler view row layout file, kinf of like the on create method
    public LinkCollectorViewHolder(@NonNull View itemView, final LinkCollectorAdapter.LinkClickListener linkClickListener) {
        super(itemView);

        // getting views from the xml
        linkName = itemView.findViewById(R.id.link_name_value_pair);
        linkUrl = itemView.findViewById(R.id.link_url_value_pair);

        itemView.setOnClickListener(v -> {
            int layoutPosition = getLayoutPosition();
            if (layoutPosition != RecyclerView.NO_POSITION) {
                linkClickListener.onLinkClick(layoutPosition);
            }
        });
    }
}
