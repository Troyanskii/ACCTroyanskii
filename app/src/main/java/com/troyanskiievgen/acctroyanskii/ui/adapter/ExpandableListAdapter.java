package com.troyanskiievgen.acctroyanskii.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.troyanskiievgen.acctroyanskii.R;
import com.troyanskiievgen.acctroyanskii.model.AccelerometerData;
import com.troyanskiievgen.acctroyanskii.model.AccelerometerSessionModel;
import com.troyanskiievgen.acctroyanskii.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Relax on 02.07.2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private List<AccelerometerSessionModel> sessionList;
    private Context mContext;

    public ExpandableListAdapter(Context context, List<AccelerometerSessionModel> groups){
        mContext = context;
        sessionList = groups;
    }

    @Override
    public int getGroupCount() {
        return sessionList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return sessionList.get(groupPosition).getAccelerometerDataList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return sessionList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return sessionList.get(groupPosition).getAccelerometerDataList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list_session, null);
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.date_session);
        textGroup.setText(DateUtils.millisecondsConverter(sessionList.get(groupPosition).getDate()));

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list_accelerometer_data, null);
        }
        AccelerometerData data = sessionList.get(groupPosition).getAccelerometerDataList().get(childPosition);

        TextView date = (TextView) convertView.findViewById(R.id.item_list_date);
        TextView x = (TextView) convertView.findViewById(R.id.o_x);
        TextView y = (TextView) convertView.findViewById(R.id.o_y);
        TextView z = (TextView) convertView.findViewById(R.id.o_z);
        date.setText(DateUtils.millisecondsConverter(data.getCreatedTime()));
        x.setText("X = " + data.getX());
        y.setText("Y = " + data.getY());
        z.setText("Z = " + data.getZ());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}