package me.ledge.link.sdk.ui.widgets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.sdk.ui.R;

/**
 * Custom {@link ArrayAdapter} that displays DataPoints<br />
 * @author Adrian
 */
public class DataPointsArrayAdapter<T> extends ArrayAdapter<T> {

    private final DataPointVo.DataPointType[] mDataPointList;

    /**
     * @see ArrayAdapter#ArrayAdapter
     * @param context See {@link ArrayAdapter#ArrayAdapter}.
     * @param resource See {@link ArrayAdapter#ArrayAdapter}.
     * @param dataPointList
     */
    public DataPointsArrayAdapter(Context context, int resource, DataPointVo.DataPointType[] dataPointList) {
        super(context, resource);
        mDataPointList = dataPointList;
    }

    /** {@inheritDoc} */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("ADRIAN", "getView called for position: " + position);
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.lv_datapoint, parent, false);
        }


        TextView textViewKey = (TextView) rowView.findViewById(R.id.tv_datapoint_key);
        TextView textViewValue = (TextView) rowView.findViewById(R.id.tv_datapoint_value);
        textViewKey.setText(mDataPointList[position].name());
        textViewValue.setText("1234");

        return rowView;
    }

    @Override
    public int getCount() {
        return mDataPointList.length;
    }

    @Override
    public T getItem(int i) {
        return (T) mDataPointList[i];
    }
}
