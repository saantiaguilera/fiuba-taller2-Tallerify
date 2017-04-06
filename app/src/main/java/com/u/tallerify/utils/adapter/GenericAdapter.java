package com.u.tallerify.utils.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.u.tallerify.contract.ContractView;
import com.u.tallerify.presenter.Presenter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by saguilera on 3/12/17.
 */
public class GenericAdapter extends RecyclerView.Adapter<GenericAdapter.ItemViewHolder> {

    public static final int MAX_SPAN_SIZE = 4;

    @NonNull List<ItemSupplier> array;
    final @NonNull Map<Integer, ItemSupplier> typeFactory;

    public GenericAdapter() {
        array = new ArrayList<>();
        typeFactory = new HashMap<>();
    }

    public void data(@NonNull List<ItemSupplier> cards) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ItemComparator<>(array, cards));

        array = cards;

        diffResult.dispatchUpdatesTo(this);
    }

    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(final int position) {
                return position >= 0 && position < array.size() ?
                    MAX_SPAN_SIZE / array.get(position).spanSize() :
                    GridLayoutManager.DEFAULT_SPAN_COUNT;
            }
        };
    }

    @Override
    public ItemViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new ItemViewHolder(typeFactory.get(viewType).createView());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        if (holder.presenter != null) {
            holder.presenter.detach(holder.itemView);
        }

        ItemPresenter presenter = array.get(position).presenter();
        presenter.attach(holder.itemView);
        holder.presenter = presenter;
    }

    @Override
    public int getItemViewType(final int position) {
        ItemSupplier supplier = array.get(position);
        typeFactory.put(supplier.type(), supplier);
        return supplier.type();
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    /**
     * View for a card
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        @Nullable ItemPresenter presenter;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);
        }

    }

    /**
     * Presenter for a card
     * @param <VIEW> that is able to present
     */
    public static abstract class ItemPresenter<VIEW extends ContractView> extends Presenter<VIEW> {}

    /**
     * Class that is able to link generic presenters with views
     */
    public static abstract class ItemSupplier<VIEW extends View & ContractView> {

        private @NonNull WeakReference<Context> context;

        public ItemSupplier(@NonNull Context context) {
            this.context = new WeakReference<>(context);
        }

        protected @NonNull Context context() {
            return context.get();
        }

        private @Nullable ItemPresenter<VIEW> currentPresenter;

        /**
         * Each card should know how to withProvider its view holder and its presenter.
         * Of course it will delegate responsibility to each of them on how to draw or
         * react accordingly.
         */
        public abstract @NonNull VIEW createView();
        public abstract @NonNull ItemPresenter createPresenter();

        /**
         * Get the size of the span this view should have
         * Eg, if the view renders in a list like this:
         * A A A => Span size of A => 3
         * B B  => Span size of B => 2
         * C => Span size of C =>1
         * @return spanSize
         */
        public abstract int spanSize();

        /**
         * For applying new changes.
         *
         * @param supplier another supplier
         * @return if the item is the same. This does not mean the contents are equals.
         * Eg I have User "John" safe and sound. If I get again "John" but with a broken leg,
         * this returns 'true'. If I got "Joe", it is 'false'
         */
        public abstract boolean isSameItem(@NonNull ItemSupplier supplier);

        /**
         * For applying new changes
         *
         * @return true if they are the same item AND they have the same content, false otherwise
         */
        public abstract boolean isSameContent(@NonNull ItemSupplier supplier);

        /**
         * Presenter should be reusable since a supplier will always present
         * only one view
         * @return presenter
         */
        @SuppressWarnings("unchecked")
        @NonNull ItemPresenter<VIEW> presenter() {
            if (currentPresenter != null) {
                return currentPresenter;
            }

            return currentPresenter = createPresenter();
        }

        /**
         * Type of the supplier, this is used for knowing the type of the view in the recyclerview
         * Each supplier has a single view type in the recyclerview!
         */
        public int type() {
            return getClass().getName().hashCode();
        }

    }

    /**
     * Comparator for doing changes in the adapter
     */
    private static class ItemComparator<Object extends ItemSupplier> extends DiffUtil.Callback {

        private @NonNull List<Object> oldList;
        private @NonNull List<Object> newList;

        public ItemComparator(@NonNull List<Object> oldList, @NonNull List<Object> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
            return oldList.get(oldItemPosition).isSameItem(newList.get(newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
            return oldList.get(oldItemPosition).isSameContent(newList.get(newItemPosition));
        }

    }

}


