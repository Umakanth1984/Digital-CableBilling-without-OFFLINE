package com.digitalrupay.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.digitalrupay.R;
import com.digitalrupay.adapters.view_holders.EmployeeViewHolder;
import com.digitalrupay.adapters.view_holders.GroupViewHolder;
import com.digitalrupay.datamodels.GroupDataModel;
import com.digitalrupay.datamodels.TodayCollectionEmployeeDataModel;

import java.util.List;

/**
 * Created by Santosh on 7/5/2016.
 */
public class EmployeeListAdapter extends ExpandableRecyclerAdapter<EmployeeViewHolder, GroupViewHolder> {

    private Context context;
    View.OnClickListener listener;
    private LayoutInflater mInflator;

    public EmployeeListAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        this.context = context;
        mInflator = LayoutInflater.from(context);
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public EmployeeViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View employeeView = mInflator.inflate(R.layout.employee_item, parentViewGroup, false);
        return new EmployeeViewHolder(employeeView);
    }

    @Override
    public GroupViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View groupView = mInflator.inflate(R.layout.group_item, childViewGroup, false);
        return new GroupViewHolder(groupView);
    }

    @Override
    public void onBindParentViewHolder(EmployeeViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        TodayCollectionEmployeeDataModel employeeDataModel = (TodayCollectionEmployeeDataModel) parentListItem;
        parentViewHolder.bind(employeeDataModel);
    }

    @Override
    public void onBindChildViewHolder(GroupViewHolder childViewHolder, int position, Object childListItem) {
        GroupDataModel groupDataModel = (GroupDataModel) childListItem;
        childViewHolder.bind(groupDataModel);
    }
}
