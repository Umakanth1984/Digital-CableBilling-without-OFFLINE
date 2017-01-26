package com.digitalrupay.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.digitalrupay.R;
import com.digitalrupay.adapters.view_holders.FeeChildParentViewHolder;
import com.digitalrupay.adapters.view_holders.FeeParentViewHolder;
import com.digitalrupay.datamodels.FeeDataModel;
import com.digitalrupay.datamodels.WardDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santosh on 7/5/2016.
 */
public class FeeListAdapter extends ExpandableRecyclerAdapter<FeeParentViewHolder, FeeChildParentViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    ArrayList<WardDataModel> wardDataModels;
    View.OnClickListener listener;
    private LayoutInflater mInflator;

    public FeeListAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        this.context = context;
        this.wardDataModels = wardDataModels;
        mInflator = LayoutInflater.from(context);
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    /*@Override
    public FeeListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        // create a new view
        ViewGroup feeView = (ViewGroup) inflater.inflate(R.layout.fee_item, parent, false);
        FeeListHolder feeListHolder = new FeeListHolder(feeView, listener);
        return feeListHolder;
    }

    @Override
    public void onBindViewHolder(FeeListHolder holder, int position) {
        holder.setWardData(wardDataModels.get(position));
    }

    @Override
    public int getItemCount() {
        return wardDataModels.size();
    }*/

    @Override
    public FeeParentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View parentAnimalView = mInflator.inflate(R.layout.fee_item, parentViewGroup, false);
        return new FeeParentViewHolder(parentAnimalView);
    }

    @Override
    public FeeChildParentViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View childAnimalView = mInflator.inflate(R.layout.fee_item_child, childViewGroup, false);
        return new FeeChildParentViewHolder(childAnimalView, listener);
    }

    @Override
    public void onBindParentViewHolder(FeeParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        WardDataModel parentAnimalObject = (WardDataModel) parentListItem;
        parentViewHolder.bind(parentAnimalObject);
    }

    @Override
    public void onBindChildViewHolder(FeeChildParentViewHolder childViewHolder, int position, Object childListItem) {
        FeeDataModel childAnimalObject = (FeeDataModel) childListItem;
        childViewHolder.bind(childAnimalObject);
    }
}
