package me.ledge.link.sdk.ui.widgets;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Custom {@link ArrayAdapter} that will display the first item as a hint.<br />
 * <br/>
 * <em>NOTE:</em> Make sure that the passed in {@code resource} contains a {@link TextView} with ID
 * {@code android.R.id.text1}! An example is {@code android.R.layout.simple_spinner_dropdown_item}.
 * @author wijnand
 */
public class HintArrayAdapter<T> extends ArrayAdapter<T> {

    private static final int HINT_INDEX = 0;

    /**
     * @see ArrayAdapter#ArrayAdapter
     * @param context See {@link ArrayAdapter#ArrayAdapter}.
     * @param resource See {@link ArrayAdapter#ArrayAdapter}.
     */
    public HintArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    /** {@inheritDoc} */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (position == HINT_INDEX) {
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            CharSequence text = textView.getText();

            textView.setText("");
            textView.setHint(text);
        }

        return view;
    }
}
