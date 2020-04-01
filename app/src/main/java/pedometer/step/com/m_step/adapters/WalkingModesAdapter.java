/*
    Privacy Friendly Pedometer is licensed under the GPLv3.
    Copyright (C) 2017  Tobias Neidig

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
package pedometer.step.com.m_step.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.secuso.privacyfriendlyactivitytracker.R;

import pedometer.step.com.m_step.models.WalkingMode;

import java.util.List;

/**
 * This adapter is used for walking modes.
 *
 * @author Tobias Neidig
 * @version 20160722
 */

public class WalkingModesAdapter extends RecyclerView.Adapter<WalkingModesAdapter.ViewHolder> {
    private List<WalkingMode> mItems;
    private OnItemClickListener mItemClickListener;

    /**
     * Creates a new Adapter for RecyclerView
     *
     * @param items The data displayed
     */
    public WalkingModesAdapter(List<WalkingMode> items) {
        mItems = items;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WalkingModesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_walking_mode, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WalkingMode item = mItems.get(position);
        holder.isActive = item.isActive();
        if (holder.mTextViewName != null) {
            String text = item.getName();
            if (item.isActive()) {
                text += holder.mTextViewName.getContext().getString(R.string.walking_mode_active_ext);
            }
            holder.mTextViewName.setText(text);
        }
        if (holder.mTextViewStepLength != null) {
            holder.mTextViewStepLength.setText(String.format(holder.itemView.getResources().getConfiguration().locale, "%.2f", item.getStepLength()));
        }
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (mItems != null) ? mItems.size() : 0;
    }

    public void setItems(List<WalkingMode> items) {
        this.mItems = items;
        this.notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.mItems.remove(position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onSetActiveClick(View view, int position);

        void onLearnClick(View view, int position);

        void onEditClick(View view, int position);

        void onRemoveClick(View view, int position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        // each data item is just a string in this case
        public TextView mTextViewName;
        public TextView mTextViewStepLength;
        public ImageButton mImageButton;
        public boolean isActive;
        private View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
            mTextViewName = (TextView) v.findViewById(R.id.name);
            mTextViewStepLength = (TextView) v.findViewById(R.id.step_length);
            mImageButton = (ImageButton) v.findViewById(R.id.card_menu);
            mImageButton.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        public void showPopup(View v, Context c) {
            PopupMenu popup = new PopupMenu(c, v);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_card_walking_mode, popup.getMenu());
            popup.getMenu().findItem(R.id.menu_set_active).setChecked(isActive);
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.card_motivation:
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, getLayoutPosition());
                    }
                    break;
                case R.id.card_menu:
                    showPopup(view, view.getContext());
                    break;
            }

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_set_active:
                    if (mItemClickListener != null) {
                        mItemClickListener.onSetActiveClick(view, getLayoutPosition());
                        return true;
                    }
                    break;
                case R.id.menu_learn:
                    if (mItemClickListener != null) {
                        mItemClickListener.onLearnClick(view, getLayoutPosition());
                        return true;
                    }
                    break;
                case R.id.menu_edit:
                    if (mItemClickListener != null) {
                        mItemClickListener.onEditClick(view, getLayoutPosition());
                        return true;
                    }
                    break;
                case R.id.menu_remove:
                    if (mItemClickListener != null) {
                        mItemClickListener.onRemoveClick(view, getLayoutPosition());
                        return true;
                    }
                    break;
            }
            return false;
        }
    }
}