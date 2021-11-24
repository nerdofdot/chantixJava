package com.example.quitsmoking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.animation.Animator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class graph extends AppCompatActivity {
    BarChart barChart;
    BarChart barChart2;
    BarChart barChart3;
    ArrayList<graphdata> arrayList = new ArrayList<>();
    ArrayList<BarEntry> barEntries;
    ArrayList<String> strings;
    ArrayList<graphdata> arrayList2 = new ArrayList<>();
    ArrayList<BarEntry> barEntries2;
    ArrayList<String> strings2;
    ArrayList<graphdata> arrayList3 = new ArrayList<>();
    ArrayList<BarEntry> barEntries3;
    ArrayList<String> strings3;
    String months_name[]=new String[12];

    TextView year_display;
    TextView description_graph;
    TextView description_graph2;
    TextView description_graph3;
    TextView maximum_in_month_for_graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        BubbleNavigationLinearView graphselection = findViewById(R.id.graphselect);

        months_name[0]="Jan";
        months_name[1]="Feb";
        months_name[2]="Mar";
        months_name[3]="Apr";
        months_name[4]="May";
        months_name[5]="Jun";
        months_name[6]="Jul";
        months_name[7]="Aug";
        months_name[8]="Sep";
        months_name[9]="Oct";
        months_name[10]="Nov";
        months_name[11]="Dec";

        barChart= findViewById(R.id.bargraph);
        barChart2=findViewById(R.id.bargraph2);
        barChart3=findViewById(R.id.bargraph3);


        year_display = findViewById(R.id.yeardis);
        description_graph = findViewById(R.id.desc_graph);
        description_graph2 = findViewById(R.id.graphslidinginfo);
        description_graph3=findViewById(R.id.tapongrapghinfo);
        maximum_in_month_for_graph=findViewById(R.id.maximumdisplay);

        create_graph_for_life();
        create_graph_for_money();
        create_graph_for_intake();

        barChart.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.Pulse)
                .duration(2000)
                .repeat(3)
                .playOn(description_graph3);

        graphselection.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                if(position==0)
                {
                    display_first_graph();
                }
                else if(position==1)
                {
                    display_second_graph();
                }
                else
                {
                    display_third_graph();
                }
            }
        });

    }

    public void create_graph_for_life()
    {
        barEntries = new ArrayList<>();
        strings = new ArrayList<>();

        get_graph_values_for_life();

        for (int i = 0; i < arrayList.size(); i++)
        {
            String months = arrayList.get(i).getMonths();
            int values = arrayList.get(i).getValue();
            barEntries.add(new BarEntry(i, values));
            strings.add(months);
        }

        Typeface typeface = ResourcesCompat.getFont(this, R.font.comfarta);

        BarDataSet barDataSet = new BarDataSet(barEntries,"");
        barDataSet.setColors(new int[]{Color.parseColor("#FF202020"),Color.parseColor("#FF611212")});
        barChart.getDescription().setText(" ");
        BarData barData =new BarData(barDataSet);
        barData.setDrawValues(false);
        barData.setValueTextSize(20f);
        barData.setValueTypeface(typeface);
        barChart.setData(barData);
        //#FF611212
        //green #FF234A02


        //axis
        XAxis xAxis = barChart.getXAxis();
        YAxis yAxis = barChart.getAxisLeft();

        //x-axis
        xAxis.setTypeface(typeface);
        xAxis.setValueFormatter( new IndexAxisValueFormatter(strings));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setTextColor(Color.parseColor("#FFFFFF"));
        xAxis.setTextSize(12f);
        xAxis.setLabelRotationAngle(90f);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.parseColor("#000000"));
        xAxis.setAxisLineWidth(2f);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(strings.size());

        //y-axis
        yAxis.setEnabled(true);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextSize(12f);
        yAxis.setDrawGridLines(true);
        yAxis.setGridLineWidth(1f);
        yAxis.setGridColor(Color.parseColor("#E4E4E4"));
        yAxis.setDrawZeroLine(true);
        yAxis.setZeroLineWidth(2f);
        yAxis.setZeroLineColor(Color.parseColor("#000000"));
        yAxis.setDrawAxisLine(false);
        yAxis.setAxisLineWidth(1f);
        yAxis.setTypeface(typeface);
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisRight().setEnabled(false);

        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(1000f);

        //barchart
        barChart.setDrawMarkers(true);
        barChart.setDragXEnabled(true);
        barChart.setDragYEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDoubleTapToZoomEnabled(false);

        barChart.setHorizontalScrollBarEnabled(false);
        barChart.setVerticalScrollBarEnabled(false);
        barChart.setVisibleXRangeMaximum(6f);
        barChart.setVisibleXRangeMinimum(6f);
        barChart.moveViewToX(-1);

        Legend l = barChart.getLegend();
        l.setEnabled(false);
        barChart.setHighlightFullBarEnabled(false);
        barChart.setHighlightPerTapEnabled(false);

        barChart.animateY(2000);

        CustomBarChartRender barChartRender = new CustomBarChartRender(barChart,barChart.getAnimator(), barChart.getViewPortHandler());
        barChartRender.setRadius(50);
        barChart.setRenderer(barChartRender);

        barChart.invalidate();

    }

    public void create_graph_for_money()
    {
        barEntries2 = new ArrayList<>();
        strings2 = new ArrayList<>();

        get_graph_values_for_money();

        for (int i = 0; i < arrayList2.size(); i++)
        {
            String months = arrayList2.get(i).getMonths();
            int values = arrayList2.get(i).getValue();
            barEntries2.add(new BarEntry(i, values));
            strings2.add(months);
        }

        Typeface typeface = ResourcesCompat.getFont(this, R.font.comfarta);

        BarDataSet barDataSet2 = new BarDataSet(barEntries2,"");
        barDataSet2.setColors(new int[]{Color.parseColor("#FF202020"),Color.parseColor("#FF616161")});
        barChart2.getDescription().setText(" ");
        BarData barData2 =new BarData(barDataSet2);
        barData2.setDrawValues(false);
        barData2.setValueTextSize(20f);
        barData2.setValueTypeface(typeface);
        barChart2.setData(barData2);
        //#FF611212
        //green #FF234A02


        //axis
        XAxis xAxis = barChart2.getXAxis();
        YAxis yAxis = barChart2.getAxisLeft();

        //x-axis
        xAxis.setTypeface(typeface);
        xAxis.setValueFormatter( new IndexAxisValueFormatter(strings2));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setTextColor(Color.parseColor("#FFFFFF"));
        xAxis.setTextSize(12f);
        xAxis.setLabelRotationAngle(90f);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.parseColor("#000000"));
        xAxis.setAxisLineWidth(2f);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(strings2.size());

        //y-axis
        yAxis.setEnabled(true);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextSize(12f);
        yAxis.setDrawGridLines(true);
        yAxis.setGridLineWidth(1f);
        yAxis.setGridColor(Color.parseColor("#E4E4E4"));
        yAxis.setDrawZeroLine(true);
        yAxis.setZeroLineWidth(2f);
        yAxis.setZeroLineColor(Color.parseColor("#000000"));
        yAxis.setDrawAxisLine(false);
        yAxis.setAxisLineWidth(1f);
        yAxis.setTypeface(typeface);
        barChart2.getAxisLeft().setDrawAxisLine(false);
        barChart2.getAxisRight().setEnabled(false);

        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(1000f);

        //barchart
        barChart2.setDrawMarkers(true);
        barChart2.setDragXEnabled(true);
        barChart2.setDragYEnabled(false);
        barChart2.setPinchZoom(false);
        barChart2.setDoubleTapToZoomEnabled(false);

        barChart2.setHorizontalScrollBarEnabled(false);
        barChart2.setVerticalScrollBarEnabled(false);
        barChart2.setVisibleXRangeMaximum(6f);
        barChart2.setVisibleXRangeMinimum(6f);
        barChart2.moveViewToX(-1);

        Legend l = barChart2.getLegend();
        l.setEnabled(false);
        barChart2.setHighlightFullBarEnabled(false);
        barChart2.setHighlightPerTapEnabled(false);

        barChart2.animateY(2000);

        CustomBarChartRender barChartRender = new CustomBarChartRender(barChart2,barChart2.getAnimator(), barChart2.getViewPortHandler());
        barChartRender.setRadius(50);
        barChart2.setRenderer(barChartRender);

        barChart2.invalidate();

    }

    public void create_graph_for_intake()
    {
        barEntries3 = new ArrayList<>();
        strings3 = new ArrayList<>();

        get_graph_values_for_intake();

        for (int i = 0; i < arrayList3.size(); i++)
        {
            String months = arrayList3.get(i).getMonths();
            int values = arrayList3.get(i).getValue();
            barEntries3.add(new BarEntry(i, values));
            strings3.add(months);
        }

        Typeface typeface = ResourcesCompat.getFont(this, R.font.comfarta);

        BarDataSet barDataSet3 = new BarDataSet(barEntries3,"");
        barDataSet3.setColors(new int[]{Color.parseColor("#FF202020"),Color.parseColor("#FF234A02")});
        barChart3.getDescription().setText(" ");
        BarData barData3 =new BarData(barDataSet3);
        barData3.setDrawValues(false);
        barData3.setValueTextSize(20f);
        barData3.setValueTypeface(typeface);
        barChart3.setData(barData3);
        //
        //green #FF234A02


        //axis
        XAxis xAxis = barChart3.getXAxis();
        YAxis yAxis = barChart3.getAxisLeft();

        //x-axis
        xAxis.setTypeface(typeface);
        xAxis.setValueFormatter( new IndexAxisValueFormatter(strings3));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setTextColor(Color.parseColor("#FFFFFF"));
        xAxis.setTextSize(12f);
        xAxis.setLabelRotationAngle(90f);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.parseColor("#000000"));
        xAxis.setAxisLineWidth(2f);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(strings3.size());

        //y-axis
        yAxis.setEnabled(true);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextSize(12f);
        yAxis.setDrawGridLines(true);
        yAxis.setGridLineWidth(1f);
        yAxis.setGridColor(Color.parseColor("#E4E4E4"));
        yAxis.setDrawZeroLine(true);
        yAxis.setZeroLineWidth(2f);
        yAxis.setZeroLineColor(Color.parseColor("#000000"));
        yAxis.setDrawAxisLine(false);
        yAxis.setAxisLineWidth(1f);
        yAxis.setTypeface(typeface);
        barChart3.getAxisLeft().setDrawAxisLine(false);
        barChart3.getAxisRight().setEnabled(false);

        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(1000f);

        //barchart
        barChart3.setDrawMarkers(true);
        barChart3.setDragXEnabled(true);
        barChart3.setDragYEnabled(false);
        barChart3.setPinchZoom(false);
        barChart3.setDoubleTapToZoomEnabled(false);

        barChart3.setHorizontalScrollBarEnabled(false);
        barChart3.setVerticalScrollBarEnabled(false);
        barChart3.setVisibleXRangeMaximum(6f);
        barChart3.setVisibleXRangeMinimum(6f);
        barChart3.moveViewToX(-1);

        Legend l = barChart3.getLegend();
        l.setEnabled(false);
        barChart3.setHighlightFullBarEnabled(false);
        barChart3.setHighlightPerTapEnabled(false);

        barChart3.animateY(2000);

        CustomBarChartRender barChartRender = new CustomBarChartRender(barChart3,barChart3.getAnimator(), barChart3.getViewPortHandler());
        barChartRender.setRadius(50);
        barChart3.setRenderer(barChartRender);

        barChart3.invalidate();

    }


    private void get_graph_values_for_life() {
        arrayList.clear();
        for (int i =0;i<12;i++)
        {
            arrayList.add(new graphdata(months_name[i],500));
        }
    }
    private void get_graph_values_for_money() {
        arrayList2.clear();
        for (int i =0;i<12;i++)
        {
            arrayList2.add(new graphdata(months_name[i],700));
        }
    }
    private void get_graph_values_for_intake() {
        arrayList3.clear();
        for (int i =0;i<12;i++)
        {
            arrayList3.add(new graphdata(months_name[i],700));
        }
    }
    public void display_first_graph()
    {
        description_graph.setText("This graph shows life lost monthly (days lost).");
        maximum_in_month_for_graph.setTextColor(Color.parseColor("#FF611212"));
        YoYo.with(Techniques.FadeIn)
                .duration(4000)
                .playOn(description_graph);
        YoYo.with(Techniques.FadeIn)
                .duration(4000)
                .playOn(maximum_in_month_for_graph);
        YoYo.with(Techniques.FadeOut)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        barChart.setVisibility(View.VISIBLE);
                        barChart2.setVisibility(View.INVISIBLE);
                        barChart3.setVisibility(View.INVISIBLE);
                        YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .playOn(barChart);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(1000)
                .playOn(barChart2);
        YoYo.with(Techniques.FadeOut)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        barChart.setVisibility(View.VISIBLE);
                        barChart2.setVisibility(View.INVISIBLE);
                        barChart3.setVisibility(View.INVISIBLE);
                        YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .playOn(barChart);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(1000)
                .playOn(barChart3);

    }
    public void display_second_graph()
    {

        maximum_in_month_for_graph.setTextColor(Color.parseColor("#FF616161"));
        description_graph.setText("This graph shows the amount you spent on smoking.");
        YoYo.with(Techniques.FadeIn)
                .duration(4000)
                .playOn(description_graph);
        YoYo.with(Techniques.FadeIn)
                .duration(4000)
                .playOn(maximum_in_month_for_graph);
        YoYo.with(Techniques.FadeOut)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        barChart2.setVisibility(View.VISIBLE);
                        barChart.setVisibility(View.INVISIBLE);
                        barChart3.setVisibility(View.INVISIBLE);
                        YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .playOn(barChart2);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(1000)
                .playOn(barChart);
        YoYo.with(Techniques.FadeOut)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        barChart2.setVisibility(View.VISIBLE);
                        barChart.setVisibility(View.INVISIBLE);
                        barChart3.setVisibility(View.INVISIBLE);
                        YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .playOn(barChart2);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(1000)
                .playOn(barChart3);
    }
    public void display_third_graph()
    {
        maximum_in_month_for_graph.setTextColor(Color.parseColor("#FF234A02"));
        description_graph.setText("This graph shows the monthly intake on cigarettes.");
        YoYo.with(Techniques.FadeIn)
                .duration(4000)
                .playOn(description_graph);
        YoYo.with(Techniques.FadeIn)
                .duration(4000)
                .playOn(maximum_in_month_for_graph);
        YoYo.with(Techniques.FadeOut)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        barChart2.setVisibility(View.INVISIBLE);
                        barChart.setVisibility(View.INVISIBLE);
                        barChart3.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .playOn(barChart3);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(1000)
                .playOn(barChart);
        YoYo.with(Techniques.FadeOut)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        barChart2.setVisibility(View.INVISIBLE);
                        barChart.setVisibility(View.INVISIBLE);
                        barChart3.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .playOn(barChart3);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(1000)
                .playOn(barChart2);
    }


}