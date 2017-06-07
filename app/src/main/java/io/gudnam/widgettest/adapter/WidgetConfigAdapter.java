package io.gudnam.widgettest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.gudnam.widgettest.R;

/**
 * Created by continueing on 15. 8. 27..
 */
public class WidgetConfigAdapter extends RecyclerView.Adapter<WidgetConfigAdapter.ViewHolder> {
    private static final String TAG = WidgetConfigAdapter.class.getSimpleName();
    private List<String> list;
    private OnItemClickListener listener;

    public WidgetConfigAdapter(List<String> list, OnItemClickListener aListener) {
        this.list = list;
        listener = aListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final int index = i;

        String item = list.get(index);

        viewHolder.rl_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(index);
            }
        });

        viewHolder.tv_info.setText(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(ArrayList<String> itmes) {
        list = itmes;
    }

    public String getItem(int position) {
        return list.get(position);
    }

    public void clear() {
        list.clear();
    }

    public void addItem(String item) {
        list.add(item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_container)
        RelativeLayout rl_container;
        @BindView(R.id.tv_info)
        TextView tv_info;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int i);
    }
}
