package com.trivediinfoway.fruitpickingapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RecordsActivity extends AppCompatActivity {

    // LinearLayout llview,llviewname,llviewfruit,llviewweight,llviewnumber;
    Bundle bn;
    ArrayList<DataClass> myList;// = new ArrayList<DataClass>();
    Realm realm;
    RealmHelper helper;
    CustomAdapter adapter;
    ListView listview;
    ImageView imgcalendar;
    int year, month, day;
    static final int DATE_PICKER_ID = 1111;
    String formatedDate;
    ArrayList<String> date_list;
    int total_kg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
      /*  llview = (LinearLayout)findViewById(R.id.llview);
        llviewname = (LinearLayout)findViewById(R.id.llviewname);
        llviewfruit = (LinearLayout)findViewById(R.id.llviewfruit);
        llviewweight = (LinearLayout)findViewById(R.id.llviewweight);
        llviewnumber = (LinearLayout)findViewById(R.id.llviewnumber);*/
        listview = (ListView) findViewById(R.id.listview);
        imgcalendar = (ImageView) findViewById(R.id.imgcalendar);
        // llviewline = (LinearLayout)findViewById(R.id.llviewline);

        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(config);
        helper = new RealmHelper(realm);
        myList = helper.retrieveAllData();

        adapter = new CustomAdapter(myList, RecordsActivity.this);
        listview.setAdapter(adapter);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        imgcalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_PICKER_ID);
            }
        });

        /*bn = getIntent().getExtras();
        if(bn!=null)
        {
            myList  = bn.getParcelableArrayList("arraylist");
            Log.e("arraylist____",myList.size()+":MMMMMMMMM");
        }*/
        /*TextView fruit[] = new TextView[myList.size()];
        TextView Number[]= new TextView[myList.size()];
        TextView Weight[]= new TextView[myList.size()];
        TextView Worker_name[]= new TextView[myList.size()];
        TextView view[] = new TextView[myList.size()];

        for(int i = 0;i<myList.size();i++)
        {
            Log.e("getWorker_name",myList.get(i).getWorker_name()+":MMMMMMMMM");
            Log.e("getFruit",myList.get(i).getFruit()+":MMMMMMMMM");
            Log.e("getNumber",myList.get(i).getNumber()+":MMMMMMMMM");
            Log.e("getWeight",myList.get(i).getWeight()+":MMMMMMMMM");


            Worker_name[i] = new TextView(RecordsActivity.this);
            Worker_name[i].setGravity(Gravity.CENTER);
            Worker_name[i].setText(myList.get(i).getWorker_name());

            fruit[i] = new TextView(RecordsActivity.this);
            fruit[i].setGravity(Gravity.CENTER);
            fruit[i].setText(myList.get(i).getFruit());


            Number[i] = new TextView(RecordsActivity.this);
            Number[i].setGravity(Gravity.CENTER);
            Number[i].setText(myList.get(i).getNumber());


            Weight[i] = new TextView(RecordsActivity.this);
            Weight[i].setGravity(Gravity.CENTER);
            Weight[i].setText(myList.get(i).getWeight());


            view[i] = new TextView(RecordsActivity.this);
            view[i].setText("------------");*/
        //  view[i].setBackgroundColor(Color.parseColor("#000000"));
          /*  ViewGroup.LayoutParams params = view[i].getLayoutParams();
            params.height = 1;
            view[i].setLayoutParams(params);*/
         /* view[i].getLayoutParams().height=1;*/
          /*  int width = 60;
            int height = 1;
            Display display = getWindowManager().getDefaultDisplay();
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(display.getWidth(),height);
            view[i].setLayoutParams(parms);*/
        //    llviewweight.addView(view[i]);
        //     llviewline.addView(view[i]);
           /* llviewname.addView(view[i]);
            llviewfruit.addView(view[i]);
            llviewnumber.addView(view[i]);*/
        //  view[i].setM

          /*  llviewname.addView(Worker_name[i]);
            llviewfruit.addView(fruit[i]);
            llviewnumber.addView(Number[i]);
            llviewweight.addView(Weight[i]);
          //  llviewweight.addView(view[i]);
        }*/
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                //	OnDateSetListener pickerListener = null;
                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                DatePickerDialog dialog = new DatePickerDialog(this,
                        pickerListener,
                        year, month, day);
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            // Do Stuff


                            //       edtcheckin.setText("");
                            //     checkin = "";
                        }
                    }
                });
                return dialog;
        }
        return null;
    }

    DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            Calendar c = Calendar.getInstance();
            String m = day + "";

            if (m.length() < 2) {
                m = "0" + m;
            }

            int new_month = month+1;
            String month_with = new_month + "";

            if (month_with.length() < 2) {
                month_with = "0" + month_with;
            }
            Log.e("month_with...", month_with + "");
          //  String SelectDate = m + "/" + (month + 1) + "/" + year;
    //        int month_with_0 = Integer.parseInt(month_with);

            String SelectDate = year + "-" + month_with + "-" + m;
            Log.e("SelectDate...", SelectDate + "");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDateandTime = sdf.format(new Date());
            Log.e("currentDateandTime...", currentDateandTime + "");
           date_list = helper.retrieveByDate(SelectDate);
            Log.e("date_list...", date_list.size() + "");
           for(int i = 0;i<date_list.size();i++)
           {
               total_kg = total_kg + Integer.parseInt(date_list.get(i));
           }
        //    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
           /* DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
           formatedDate = df2.format(new Date(selectedYear - 1900, selectedMonth, selectedDay));
            Date strDate = null;
            try {
                strDate = df2.parse(formatedDate);
                Log.e("selected_date...", strDate + "");

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Date current_date = null;
            String formattedDate = df2.format(c.getTime());
            try {
                current_date = df2.parse(formattedDate);
                Log.e("current date...", current_date + "");
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/

            Dialog dialog = new Dialog(RecordsActivity.this);
            dialog.setContentView(R.layout.dialog_per_person);
            TextView tvkgofday = (TextView) dialog.findViewById(R.id.tvkgofday);
            TextView tvkgofweek = (TextView) dialog.findViewById(R.id.tvkgofweek);
            TextView tvkg_of_months_per_person = (TextView) dialog.findViewById(R.id.tvkg_of_months_per_person);
            tvkgofday.setText("Totla KG = "+total_kg+"");

           dialog.show();
        //     Toast.makeText(RecordsActivity.this, strDate + "", Toast.LENGTH_SHORT).show();
           /* if (strDate.before(current_date)) {
                Toast.makeText(getApplicationContext(),
                        "Please choose future date", Toast.LENGTH_SHORT).show();
                edtcheckin.setText("");
                checkin = "";
            } else {

                // Show selected date
                month = month + 1;
                String month_str = month + "";
                if (month_str.length() < 2) {
                    month_str = "0" + month;
                }
                edtcheckin.setText(new StringBuilder().append(month_str)
                        .append("/").append(day).append("/").append(year));
                checkin = edtcheckin.getText().toString();
            }*/
        }
    };
}
