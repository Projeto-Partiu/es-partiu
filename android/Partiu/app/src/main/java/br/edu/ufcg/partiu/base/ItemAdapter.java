package br.edu.ufcg.partiu.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by lucas on 26/07/17.
 */

public class ItemAdapter<T extends ItemAdapter.ItemHolder> extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<T> itemHolderList;

    private final SparseArray<ItemViewHolder.Factory> factoryByViewType = new SparseArray<>();
    private final SparseArray<OnItemClickedListener> listenerByViewType = new SparseArray<>();

    public List<T> getItemHolderList() {
        return itemHolderList;
    }

    public void setItemHolderList(List<T> itemHolderList) {
        if (itemHolderList != this.itemHolderList) {
            this.itemHolderList = itemHolderList;
            notifyDataSetChanged();
        }
    }

    public void addItem(int position, T itemHolder) {
        position = Math.min(position, itemHolderList.size());
        itemHolderList.add(position, itemHolder);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(itemHolderList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(itemHolderList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public void removeItem(T itemHolder) {
        int index = itemHolderList.indexOf(itemHolder);

        if (index >= 0) {
            removeItem(index);
        }
    }

    public void removeItem(int position) {
        itemHolderList.remove(position);
        notifyItemRemoved(position);
    }

    public ItemAdapter<T> withViewType(ItemViewHolder.Factory factory, int viewType) {
        return withViewType(factory, viewType, null);
    }

    public ItemAdapter<T> withViewType(ItemViewHolder.Factory factory, int viewType,
                                       OnItemClickedListener listener) {
        factoryByViewType.put(viewType, factory);
        listenerByViewType.put(viewType, listener);
        return this;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder.Factory factory = factoryByViewType.get(viewType);

        if (factory == null) {
            throw new IllegalArgumentException("Unsupported view type: " + viewType);
        }

        return factory.createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(itemHolderList.get(position));
        holder.setOnItemClickedListener(listenerByViewType.get(holder.getItemViewType()));
    }

    @Override
    public int getItemCount() {
        return itemHolderList == null ? 0 : itemHolderList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return itemHolderList.get(position).getItemViewType();
    }

    public static abstract class ItemHolder {
        public abstract int getItemViewType();
    }

    public static abstract class ItemViewHolder<T extends ItemHolder> extends RecyclerView.ViewHolder {
        private OnItemClickedListener onItemClickedListener;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bind(T itemHolder);

        public final void setOnItemClickedListener(OnItemClickedListener listener) {
            this.onItemClickedListener = listener;
        }

        public final void notifyItemClicked() {
            if (onItemClickedListener != null) {
                onItemClickedListener.onItemClicked(this);
            }
        }

        public interface Factory {
            ItemViewHolder createViewHolder(ViewGroup parent, int viewType);
        }
    }

    public interface OnItemClickedListener {

        void onItemClicked(ItemViewHolder<?> viewHolder);
    }
}