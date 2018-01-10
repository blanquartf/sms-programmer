package fr.blanquartf.smsprogrammer.smsList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.blanquartf.smsprogrammer.R;
import fr.blanquartf.smsprogrammer.data.ContactWrapper;
import fr.blanquartf.smsprogrammer.data.Sms;
import fr.blanquartf.smsprogrammer.utils.Constants;

/**
 * Created by blanquartf on 03/01/2018.
 */

public class SmsAdapter extends BaseAdapter {

    private Context context;
    private List<Sms> smsList;

    public SmsAdapter(Context context, List<Sms> smsList)
    {
        this.smsList=smsList;
        this.context=context;
    }
    @Override
    public int getCount() {
        return smsList.size();
    }

    @Override
    public Object getItem(int position) {
        return smsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return smsList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SmsAdapter.FieldHolder viewHolder;
        if (convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.sms_layout, parent, false);
            viewHolder = new SmsAdapter.FieldHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (SmsAdapter.FieldHolder) convertView.getTag();
        }
        Sms sms = (Sms) getItem(position);
        viewHolder.tvDate.setText(Constants.DATE_FORMAT.format(sms.getDate().getTime()));
        viewHolder.tvContent.setText(sms.getSmsContent());
        List<ContactWrapper> contactList = sms.getContactList();
        String text ="";
        for (ContactWrapper wrapper : contactList)
        {
            if (!text.isEmpty())
            {
                text+=",";
            }
            text+=wrapper.getContactDisplayName();
        }
        viewHolder.tvContacts.setText(text);
        return convertView;
    }

    class FieldHolder{
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvContacts)
        TextView tvContacts;
        @BindView(R.id.tvContent)
        TextView tvContent;

        public FieldHolder(View v)
        {
            ButterKnife.bind(this,v);
        }
    }
}
