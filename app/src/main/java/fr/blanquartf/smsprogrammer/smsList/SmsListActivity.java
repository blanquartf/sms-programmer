package fr.blanquartf.smsprogrammer.smsList;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.onegravity.contactpicker.contact.Contact;
import com.onegravity.contactpicker.contact.ContactDescription;
import com.onegravity.contactpicker.contact.ContactSortOrder;
import com.onegravity.contactpicker.core.ContactPickerActivity;
import com.onegravity.contactpicker.picture.ContactPictureType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemLongClick;
import fr.blanquartf.smsprogrammer.R;
import fr.blanquartf.smsprogrammer.SmsProgrammerApplication;
import fr.blanquartf.smsprogrammer.data.Sms;
import fr.blanquartf.smsprogrammer.smsContent.MessageContentActivity;

public class SmsListActivity extends AppCompatActivity implements SmsListContract.View {

    private static final int REQUEST_CONTACT = 1;

    public static final int PERMISSIONS_REQUEST_CONTACTS_SMS = 100;

    @BindView(R.id.lvSmsList)
    ListView lvSmsList;
    @BindView(R.id.rlEmptyMessage)
    RelativeLayout rlEmptyMessage;
    private SmsAdapter adapter;
    private SmsListContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new SmsListPresenter(this);
        ((SmsProgrammerApplication)getApplication()).getAppComponent().inject((SmsListPresenter) presenter);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @OnItemLongClick(R.id.lvSmsList)
    public boolean deleteSms(AdapterView<?> parent, View view, int position, long id)
    {
        Sms sms = (Sms) adapter.getItem(position);
        presenter.onSmsLongClicked(sms);
        return true;
    }

    public void showDeleteConfirmationDialog(Sms sms)
    {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.dialog_confirmation_delete_title)
                .setMessage(R.string.dialog_confirmation_delete_content)
                .setPositiveButton(R.string.dialog_confirmation_delete_yes, (dialog, which) -> presenter.deleteSms(sms))
                .setNegativeButton(R.string.dialog_confirmation_delete_no, null)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CONTACTS_SMS
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            presenter.programSms();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CONTACT && resultCode == Activity.RESULT_OK &&
                data != null && data.hasExtra(ContactPickerActivity.RESULT_CONTACT_DATA)) {

            List<Contact> contacts = (List<Contact>) data.getSerializableExtra(ContactPickerActivity.RESULT_CONTACT_DATA);
            presenter.onContactsSelected(contacts);

        }
    }
    public void launchMessageContentChooser(Sms smsToSend)
    {
        Intent intent = new Intent(this,MessageContentActivity.class);
        intent.putExtra("SMS",smsToSend);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.program_sms:
                presenter.programSms();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void showSmsList(List<Sms> sms) {
        if (sms.size()>0)
        {
            rlEmptyMessage.setVisibility(View.GONE);
            lvSmsList.setVisibility(View.VISIBLE);
            adapter = new SmsAdapter(SmsListActivity.this,sms);
            lvSmsList.setAdapter(adapter);
        }
        else
        {
            rlEmptyMessage.setVisibility(View.VISIBLE);
            lvSmsList.setVisibility(View.GONE);
        }
    }

    public void chooseContact()
    {
        Intent intent = new Intent(this, ContactPickerActivity.class)
                .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.PHONE.name())
                .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());
        startActivityForResult(intent, REQUEST_CONTACT);
    }

    public boolean hasPermissions()
    {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED);
    }

    public void requestPermissions()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.SEND_SMS}, PERMISSIONS_REQUEST_CONTACTS_SMS);
        }
    }

    @Override
    public void showDatePicker() {
        DateTimePicker picker = new DateTimePicker(this,presenter);
        picker.show();
    }
}
