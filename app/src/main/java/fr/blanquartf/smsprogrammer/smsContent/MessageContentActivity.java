package fr.blanquartf.smsprogrammer.smsContent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;
import fr.blanquartf.smsprogrammer.R;
import fr.blanquartf.smsprogrammer.SmsProgrammerApplication;
import fr.blanquartf.smsprogrammer.data.ContactWrapper;
import fr.blanquartf.smsprogrammer.data.Sms;
import fr.blanquartf.smsprogrammer.smsList.SmsListActivity;

public class MessageContentActivity extends AppCompatActivity implements SmsContentContract.View{

    @BindView(R.id.etMessage)
    EditText etMessage;

    @BindView(R.id.lvContactFields)
    ListView lvContactFields;

    @BindView(R.id.btValidate)
    Button btValidate;
    private FieldAdapter adapter;
    private SmsContentContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_content);
        ButterKnife.bind(this);
        adapter = new FieldAdapter(this, ContactWrapper.SUPPORTED_FIELDS);

        Sms sms = getIntent().getParcelableExtra("SMS");
        lvContactFields.setAdapter(adapter);
        presenter = new MessageContentPresenter(this,sms);
        ((SmsProgrammerApplication)getApplication()).getAppComponent().inject((MessageContentPresenter) presenter);
    }

    @OnTextChanged(R.id.etMessage)
    public void updateValidationButtonDisabledState()
    {
        boolean enabled = etMessage.getText().toString().length()>0;
        btValidate.setEnabled(enabled);
        btValidate.setBackgroundColor(getResources().getColor(enabled?R.color.colorAccent:R.color.colorDisabled));
    }

    @OnItemClick(R.id.lvContactFields)
    public void addPatternToField(AdapterView<?> parent,View view,int position,long id)
    {
        presenter.addPatternToField((String)adapter.getItem(position));
    }

    @OnClick(R.id.btValidate)
    public void programSmsSend() {
        presenter.programSmsSend();
    }

    @Override
    public String getSmsContent() {
        return etMessage.getText().toString();
    }

    @Override
    public void goToSmsList() {
        Intent intent = new Intent(MessageContentActivity.this,SmsListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void addTextToContent(String text) {
        etMessage.append(text);
    }


}
