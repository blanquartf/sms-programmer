package fr.blanquartf.smsprogrammer.smsContent;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.blanquartf.smsprogrammer.R;

/**
 * Created by blanquartf on 28/12/2017.
 */

public class FieldAdapter extends BaseAdapter {
    private Context context;
    private List<String> data;

    public FieldAdapter(Context context, Map<String,String> data) {
        super();
        this.context=context;
        this.data=new ArrayList<String>(data.keySet());
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FieldAdapter.FieldHolder viewHolder;
        if (convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.field_adapter, parent, false);
            viewHolder = new FieldAdapter.FieldHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (FieldAdapter.FieldHolder) convertView.getTag();
        }
        Resources res = context.getResources();
        viewHolder.tvFieldName.setText(res.getIdentifier((String)getItem(position), "string", context.getPackageName()));
        return convertView;
    }

    class FieldHolder{
        @BindView(R.id.tvFieldName)
        TextView tvFieldName;

        public FieldHolder(View v)
        {
            ButterKnife.bind(this,v);
        }
    }
}
