package ru.myitlesson.app.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ListAdapter<T, VB extends ListAdapter.ViewBinder<T>> extends RecyclerView.Adapter<ListAdapter.ViewBinder<T>> {

    public static abstract class ViewBinder<T> extends RecyclerView.ViewHolder {

        public ViewBinder(@NonNull ViewGroup view) {
            super(view);
        }

        public abstract void bind(T object);
    }

    private final List<T> list;

    private final Class<VB> binder;

    public ListAdapter(List<T> list, Class<VB> binder) {
        this.list = list;
        this.binder = binder;
    }

    @NonNull
    @Override
    public VB onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            return binder.getConstructor(ViewGroup.class).newInstance(parent);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBinder<T> holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
