package com.example.mazady.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mazady.R;
import com.example.mazady.data.network.response.CategoriesItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainCatAdapter extends ArrayAdapter {

    private List<CategoriesItem> dataList;

    private final ListFilter listFilter = new ListFilter();
    private List<CategoriesItem> dataListAllItems;



    public MainCatAdapter(Context context, int resource, List<CategoriesItem> storeDataLst) {
        super(context, resource, storeDataLst);
        dataList = storeDataLst;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public CategoriesItem getItem(int position) {

        return dataList.get(position) ;
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {


        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_item_viewshape, parent, false);
        }

        TextView strName = (TextView) view.findViewById(R.id.categoryValue);
        strName.setText(dataList.get(position).getName());
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter {
        private final Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (lock) {
                    dataListAllItems = new ArrayList<CategoriesItem>(dataList);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                ArrayList<CategoriesItem> matchValues = new ArrayList<CategoriesItem>();

                for (CategoriesItem dataItem : dataListAllItems) {
                    if (Objects.requireNonNull(dataItem.getName()).toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }
                }

                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                dataList = (ArrayList<CategoriesItem>) results.values;
            } else {
                dataList = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}
