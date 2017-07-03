package com.troyanskiievgen.acctroyanskii.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.troyanskiievgen.acctroyanskii.R;
import com.troyanskiievgen.acctroyanskii.manager.UserManager;
import com.troyanskiievgen.acctroyanskii.model.AccelerometerData;
import com.troyanskiievgen.acctroyanskii.model.AccelerometerSessionModel;
import com.troyanskiievgen.acctroyanskii.presenter.GraphFragmentPresenter;
import com.troyanskiievgen.acctroyanskii.view.AcceletometerDataGraphView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Relax on 28.06.2017.
 */

public class AccelerometerDataGraphFragment extends MvpFragment implements AcceletometerDataGraphView{

    @BindView(R.id.data_graph)
    GraphView dataGraph;

    @InjectPresenter
    GraphFragmentPresenter graphPresenter;

    private List<AccelerometerSessionModel> dataList;
    private List<AccelerometerData> allAccelerometerData;
    private DataPoint[] xPoints;
    private DataPoint[] yPoints;
    private DataPoint[] zPoints;

    public static AccelerometerDataGraphFragment newInstance() {
        return new AccelerometerDataGraphFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_accelerometer_data_shedule, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
    }

    public void setupGraph() {
        dataList = UserManager.getInstance().getCurrentUser().getSessionsList();
        allAccelerometerData = new ArrayList<>();

        for (AccelerometerSessionModel session : dataList) {
            allAccelerometerData.addAll(session.getAccelerometerDataList());
        }

        xPoints = new DataPoint[allAccelerometerData.size()];
        yPoints = new DataPoint[allAccelerometerData.size()];
        zPoints = new DataPoint[allAccelerometerData.size()];

        for (int i = 0; i < allAccelerometerData.size(); ++i) {
            xPoints[i] = new DataPoint(i, allAccelerometerData.get(i).getX());
            yPoints[i] = new DataPoint(i, allAccelerometerData.get(i).getY());
            zPoints[i] = new DataPoint(i, allAccelerometerData.get(i).getZ());
        }

        LineGraphSeries<DataPoint> seriesX = new LineGraphSeries<>(xPoints);
        LineGraphSeries<DataPoint> seriesY = new LineGraphSeries<>(yPoints);
        LineGraphSeries<DataPoint> seriesZ = new LineGraphSeries<>(zPoints);

        seriesX.setColor(ResourcesCompat.getColor(getResources(), R.color.red, null));
        seriesY.setColor(ResourcesCompat.getColor(getResources(), R.color.green, null));
        seriesZ.setColor(ResourcesCompat.getColor(getResources(), R.color.blue, null));

        dataGraph.addSeries(seriesX);
        dataGraph.addSeries(seriesY);
        dataGraph.addSeries(seriesZ);
    }
}
