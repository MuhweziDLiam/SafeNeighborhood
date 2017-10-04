package com.example.muhwezidenisliam.safeneighborhoodalpha.dashboard;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.example.muhwezidenisliam.safeneighborhoodalpha.R;
import com.example.muhwezidenisliam.safeneighborhoodalpha.adapters.BaseObjectAdapter;
import com.example.muhwezidenisliam.safeneighborhoodalpha.adapters.Data_Model;
import com.example.muhwezidenisliam.safeneighborhoodalpha.adapters.RecyclerView_Adapter;
import com.example.muhwezidenisliam.safeneighborhoodalpha.utilities.DrawableUtil;
import com.example.muhwezidenisliam.safeneighborhoodalpha.widget.MenusLayout;
import android.view.ViewGroup.LayoutParams;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;


import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Muhwezi Denis Liam on 9/14/2017.
 */

public class Home extends AppCompatActivity{

    private static RecyclerView recyclerView;

    //String and Integer array for Recycler View Items
    public static final String[] TITLES= {"Hood","Full Sleeve Shirt"};
    public static final Integer[] IMAGES= {R.drawable.denis,R.drawable.riam};

    private MenusLayout mMenusLayout;

    public final static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec",};

    public final static String[] days = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun",};

    private LineChartView chartTop;
    private ColumnChartView chartBottom;

    private LineChartData lineData;
    private ColumnChartData columnData;

    private Menu msgMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_dash_board);
        ButterKnife.bind(this);

        initViews();
        populatRecyclerView();

        chartTop = (LineChartView) findViewById(R.id.chart_top);

        // Generate and set data for line chart
        generateInitialLineData();

        // *** BOTTOM COLUMN CHART ***

        chartBottom = (ColumnChartView) findViewById(R.id.chart_bottom);

        generateColumnData();

        mMenusLayout = (MenusLayout) findViewById(R.id.menus);

        ArrayList<Menu> list = new ArrayList<Menu>();
        list.add(new Menu(R.drawable.report, "Report"));
        list.add(new Menu(R.drawable.group_chats, "Chat"));
        list.add(new Menu(R.drawable.report, "View Reports"));
        list.add(new Menu(R.drawable.google_maps, "Location"));
        msgMenu = new Menu(R.drawable.burglar, "Robbery");
        list.add(msgMenu);
        list.add(new Menu(R.drawable.fire, "Fire"));
        list.add(new Menu(R.drawable.ambulance, "Ambulance"));
        list.add(new Menu(R.drawable.murder, "Murder"));
        MenusAdapter menusAdapter = new MenusAdapter(this, list);
        mMenusLayout.setAdapter(menusAdapter);

    }


    @Override
    public void onResume() {
        super.onResume();

            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle(R.string.app_name);

    }

    private class MenusAdapter extends BaseObjectAdapter<Menu> {

        public MenusAdapter(Context context, ArrayList<Menu> list) {
            super(context, list);
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            Menu menu = getItem(position);
            TextView txt = new TextView(mContext);
            txt.setTextColor(Color.BLACK);
            txt.setTextSize(16);
            txt.setGravity(Gravity.CENTER_HORIZONTAL);
            DrawableUtil.setTextDrawableTop(txt, DrawableUtil.dp2Px(mContext, 10), menu.resId);
            txt.setText(menu.text);
            final int size = LayoutParams.WRAP_CONTENT;
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, size, Gravity.CENTER);
            parent.addView(txt, params);
            menu.setCountListener(new CountListener() {
                @Override
                public void onCount(int count) {
                    TextView txt_count = (TextView) parent.getTag();
                    if (count > 0) {
                        if (txt_count == null) {
                            txt_count = new TextView(mContext);
                            int pad = DrawableUtil.dp2Px(mContext, 8);
                            txt_count.setPadding(pad, 0, pad, 0);
                            //txt_count.setBackgroundResource(R.drawable.bg_round_red_msg);
                            txt_count.setTextColor(Color.WHITE);
                            txt_count.setTextSize(12);
                            int height = DrawableUtil.dp2Px(mContext, 17);
                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, height, Gravity.RIGHT);
                            params.topMargin = pad;
                            params.rightMargin = pad;
                            parent.addView(txt_count, params);
                            parent.setTag(txt_count);
                        }
                        txt_count.setText("" + count);
                    } else {
                        if (txt_count != null) {
                            parent.removeView(txt_count);
                            parent.setTag(null);
                        }
                    }
                }
            });
            return parent;
        }
    }

    private class Menu {
        int resId;
        String text;

        Menu(int resId, String text) {
            this.resId = resId;
            this.text = text;
        }

        CountListener mCountListener;

        void setCountListener(CountListener l) {
            mCountListener = l;
        }

        void setCount(int count) {
            if (mCountListener != null) {
                mCountListener.onCount(count);
            }
        }
    }

    interface CountListener {
        void onCount(int count);
    }


    private void generateColumnData() {

        int numSubcolumns = 1;
        int numColumns = months.length;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
            }

            axisValues.add(new AxisValue(i).setLabel(months[i]));

            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
        }

        columnData = new ColumnChartData(columns);

        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(2));

        chartBottom.setColumnChartData(columnData);

        // Set value touch listener that will trigger changes for chartTop.
        chartBottom.setOnValueTouchListener(new ValueTouchListener());

        // Set selection mode to keep selected month column highlighted.
        chartBottom.setValueSelectionEnabled(true);

        chartBottom.setZoomType(ZoomType.HORIZONTAL);

        // chartBottom.setOnClickListener(new View.OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // SelectedValue sv = chartBottom.getSelectedValue();
        // if (!sv.isSet()) {
        // generateInitialLineData();
        // }
        //
        // }
        // });

    }

    /**
     * Generates initial data for line chart. At the begining all Y values are equals 0. That will change when user
     * will select value on column chart.
     */
    private void generateInitialLineData() {
        int numValues = 7;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<PointValue> values = new ArrayList<PointValue>();
        for (int i = 0; i < numValues; ++i) {
            values.add(new PointValue(i, 0));
            axisValues.add(new AxisValue(i).setLabel(days[i]));
        }

        Line line = new Line(values);
        line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        lineData = new LineChartData(lines);
        lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));

        chartTop.setLineChartData(lineData);

        // For build-up animation you have to disable viewport recalculation.
        chartTop.setViewportCalculationEnabled(false);

        // And set initial max viewport and current viewport- remember to set viewports after data.
        Viewport v = new Viewport(0, 110, 6, 0);
        chartTop.setMaximumViewport(v);
        chartTop.setCurrentViewport(v);

        chartTop.setZoomType(ZoomType.HORIZONTAL);
    }

    private void generateLineData(int color, float range) {
        // Cancel last animation if not finished.
        chartTop.cancelDataAnimation();

        // Modify data targets
        Line line = lineData.getLines().get(0);// For this example there is always only one line.
        line.setColor(color);
        for (PointValue value : line.getValues()) {
            // Change target only for Y value.
            value.setTarget(value.getX(), (float) Math.random() * range);
        }

        // Start new data animation with 300ms duration;
        chartTop.startDataAnimation(300);
    }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            generateLineData(value.getColor(), 100);
        }

        @Override
        public void onValueDeselected() {

            generateLineData(ChartUtils.COLOR_GREEN, 0);

        }
    }

    // Initialize the view
    private void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Set Back Icon on Activity

//        navigateFrom = getIntent().getStringExtra("navigateFrom");//Get Intent Value in String

        recyclerView = (RecyclerView)
                findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //Set RecyclerView type according to intent value
//        if (navigateFrom.equals("horizontal")) {
//            getSupportActionBar().setTitle("Horizontal Recycler View");
//            recyclerView
//                    .setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false));
//        } else {

            getSupportActionBar().setTitle("Staggered GridLayout Manager");
            recyclerView
                    .setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));// Here 2 is no. of columns to be displayed

//        }
    }


    // populate the list view by adding data to arraylist
    private void populatRecyclerView() {
        ArrayList<Data_Model> arrayList = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            arrayList.add(new Data_Model(TITLES[i],IMAGES[i]));
        }
        RecyclerView_Adapter adapter = new RecyclerView_Adapter(Home.this, arrayList);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview
        adapter.notifyDataSetChanged();// Notify the adapter

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
