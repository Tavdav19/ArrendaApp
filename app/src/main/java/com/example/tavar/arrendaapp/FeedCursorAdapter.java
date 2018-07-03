package com.example.tavar.arrendaapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;

public class FeedCursorAdapter extends RecyclerView.Adapter<FeedCursorAdapter.FeedViewHolder> {
    private Context context;
    private Cursor cursor = null;
    private View.OnClickListener viewHolderClickListener = null;
    private  int lastHouseClicked = -1;

    public FeedCursorAdapter(Context context){ this.context = context; }


    public void refreshData (Cursor cursor){
        if(this.cursor != cursor){
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    public void setViewHolderClickListener(View.OnClickListener viewHolderClickListener) {
        this.viewHolderClickListener = viewHolderClickListener;
    }

    public int getLastHouseClicked() {
        return lastHouseClicked;
    }

    /**
     * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * . Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     */
    @NonNull
    @Override
    public FeedCursorAdapter.FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_house, parent, false);
        return new FeedViewHolder(item);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull FeedCursorAdapter.FeedViewHolder holder, int position) {
        cursor.moveToPosition(position);
        House house = DbTableHouse.getCurrentHouseFromCursor(cursor);
        holder.setHouse(house);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if(cursor == null) return 0;

        return cursor.getCount();
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        private TextView textViewLoc;
        private TextView textViewPrice;
        //private ImageView imageViewHouse;
        private int houseId;

        public FeedViewHolder(View itemView){
            super(itemView);

            textViewLoc = (TextView) itemView.findViewById(R.id.textViewLoc);
            textViewPrice = (TextView) itemView.findViewById(R.id.textViewPrice);
            //imageViewHouse = (ImageView) itemView.findViewById(R.id.imageHouse);

            itemView.setOnClickListener(this);
        }
        public void setHouse(House house){
            textViewLoc.setText(house.getLoc());
            textViewPrice.setText(String.format( "%d" ,house.getWeekPrice())+ "€");
            //imageViewHouse.setImageURI(Uri.parse(house.getImageHouse()));
            houseId = house.getId();
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if(position == RecyclerView.NO_POSITION){
          return;
        }
        if(viewHolderClickListener !=null){
                lastHouseClicked = houseId;
                viewHolderClickListener.onClick(v);
        }
        }
        }
    }

