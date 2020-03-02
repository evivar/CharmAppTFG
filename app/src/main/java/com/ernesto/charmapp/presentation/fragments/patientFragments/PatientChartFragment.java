package com.ernesto.charmapp.presentation.fragments.patientFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ernesto.charmapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;


public class PatientChartFragment extends Fragment {

    private ArrayList<BarEntry> datosPrueba;
    private ArrayList<Entry> datosPrueba2;

    private BarDataSet dataset;

    private BarChart chartPrueba;

    private LineChart chartPrueba2;

    private LineDataSet dataSet2;

    public static PatientChartFragment create() {
        PatientChartFragment f = new PatientChartFragment();
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patient_chart, container, false);

        chartPrueba = v.findViewById(R.id.temperatureChart_Chart);
        chartPrueba2 = v.findViewById(R.id.lineChart_Chart);

        chartPrueba2.setDrawGridBackground(false);

        datosPrueba = new ArrayList<>();

        datosPrueba.add(new BarEntry(15, 0));
        datosPrueba.add(new BarEntry(10, 1));
        datosPrueba.add(new BarEntry(2, 2));
        datosPrueba.add(new BarEntry(5, 3));
        datosPrueba.add(new BarEntry(25, 4));
        datosPrueba.add(new BarEntry(10, 5));
        datosPrueba.add(new BarEntry(1, 6));
        datosPrueba.add(new BarEntry(24, 7));

        datosPrueba2 = new ArrayList<>();

        datosPrueba2.add(new Entry(0, 15));
        datosPrueba2.add(new Entry(1, 10));
        datosPrueba2.add(new Entry(2, 2));
        datosPrueba2.add(new Entry(3, 5));
        datosPrueba2.add(new Entry(4, 25));
        datosPrueba2.add(new Entry(5, 10));
        datosPrueba2.add(new Entry(6, 1));
        datosPrueba2.add(new Entry(7, 24));

        dataSet2 = new LineDataSet(datosPrueba2, "Datos de prueba 2");
        dataSet2.setDrawFilled(true);
        dataSet2.setFillColor(Color.CYAN);
        dataset = new BarDataSet(datosPrueba, "Datos de prueba");

        BarData datos = new BarData(dataset);

        LineData datos2 = new LineData(dataSet2);

        chartPrueba.setData(datos);

        chartPrueba2.setData(datos2);

        return v;
    }
}
