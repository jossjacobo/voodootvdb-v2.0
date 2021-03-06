package com.joss.voodootvdb.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.joss.voodootvdb.interfaces.VoodooClickListener;
import com.joss.voodootvdb.interfaces.VoodooItem;
import com.joss.voodootvdb.views.VoodooCardView;
import com.joss.voodootvdb.views.VoodooHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: jossayjacobo
 * Date: 3/5/15
 * Time: 5:53 PM
 */
public class HomeAdapter extends BaseAdapter {

    public static final String TAG = HomeAdapter.class.getSimpleName();

    Context context;
    List<List<VoodooItem>> items;
    VoodooClickListener voodooClickListener;

    public HomeAdapter(Context context, VoodooClickListener listener){
        this.context = context;
        this.items = new ArrayList<>();
        this.voodooClickListener = listener;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position){
        return items.get(position).get(0).getType();
    }

    @Override
    public int getViewTypeCount(){
        return VoodooHorizontalScrollView.TYPE_FEATURE + 1;
    }

    public void setContent(List<List<VoodooItem>> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VoodooHorizontalScrollView voodooHorizontalScrollView =
                convertView == null
                    ? new VoodooHorizontalScrollView(context,
                        items.get(position).get(0).getType(),
                        voodooClickListener)
                    : (VoodooHorizontalScrollView) convertView;

        voodooHorizontalScrollView.setItems(items.get(position).get(0).getSectionTitle(), items.get(position));

        return voodooHorizontalScrollView;
    }
}
