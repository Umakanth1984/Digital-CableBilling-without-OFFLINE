package com.digitalrupay.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.digitalrupay.R;
import com.digitalrupay.adapters.view_holders.ComplaintsListHolder;
import com.digitalrupay.adapters.view_holders.EmpCollectionListHolder;
import com.digitalrupay.adapters.view_holders.EmployeeViewHolder;
import com.digitalrupay.adapters.view_holders.GroupViewHolder;
import com.digitalrupay.datamodels.ComplaintsDataModel;
import com.digitalrupay.datamodels.EmpCollectionDataModel;
import com.digitalrupay.datamodels.GroupDataModel;
import com.digitalrupay.datamodels.TodayCollectionEmployeeDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Audlink_sri on 12/26/2016.
 */

public class EmpCollectionListAdapter extends  RecyclerView.Adapter<EmpCollectionListHolder> {

    private Context context;
    private ArrayList<EmpCollectionDataModel> complaintsDataModelArrayList;
    private View.OnClickListener listener;
    private LayoutInflater mInflator;

    public EmpCollectionListAdapter(Context context, ArrayList<EmpCollectionDataModel> complaintsDataModelArrayList) {
        this.context = context;
        this.complaintsDataModelArrayList = complaintsDataModelArrayList;
        mInflator = LayoutInflater.from(context);
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public EmpCollectionListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View parentAnimalView = mInflator.inflate(R.layout.employee_item, parent, false);
        return new EmpCollectionListHolder(parentAnimalView, listener);
    }
    @Override
    public void onBindViewHolder(EmpCollectionListHolder holder, int position) {
        EmpCollectionListHolder complaintsListHolder = (EmpCollectionListHolder) holder;
        complaintsListHolder.setComplaintsData(complaintsDataModelArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return complaintsDataModelArrayList.size();
    }
}
