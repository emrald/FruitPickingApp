package com.trivediinfoway.fruitpickingapp;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.Result;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    Realm realm;
    String current_time = "";
    ZXingScannerView mScannerView;
    ArrayList<DataClass> arraylist;
    ArrayList<DataClass> arraylist_demo;
    ArrayList<DataClass> all_arraylist;
    ArrayList<String> arrayList_fruits;
    Dialog dialog;
    String fruit_name = "", weight = "", number = "", time_of_record = "";
    RealmHelper helper;
    ArrayList<String> weightList;
    ArrayList<String> numberList;
    ArrayList<String> nameList;
    int totalNumber;
    float totalWeight;
    float totalAllWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        checkPermission();
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(config);

        createExcelSheet();

        mScannerView = (ZXingScannerView) findViewById(R.id.zxscan);
        mScannerView.setResultHandler(MainActivity.this);
        Camera.CameraInfo info = new Camera.CameraInfo();
        Log.e("info.....", info.orientation + "........" + "");

        /*helper = new RealmHelper(realm);

        arraylist = new ArrayList<DataClass>();
        weightList = new ArrayList<String>();
        numberList = new ArrayList<String>();
        nameList = new ArrayList<String>();

        if(arraylist.size()>0) {
            arraylist = helper.retrieve(str);

            if (arraylist.size() > 0) {
                for (int i = 0; i < arraylist.size(); i++) {
                    nameList.add(arraylist.get(i).getWorker_name());
                    Log.e("nameList", nameList.get(i) + ":SIZE");
                }
            }

            weightList = helper.retrieveWeight(str);
            for (int i = 0; i < weightList.size(); i++) {
                Log.e("weight : " + i, weightList.get(i) + "");
                totalWeight = totalWeight + Float.parseFloat(weightList.get(i));
            }
            Log.e("totalWeight", totalWeight + ":TOTAL Weight");

            numberList = helper.retrieveNumber(str);
            for (int i = 0; i < numberList.size(); i++) {
                Log.e("number : " + i, numberList.get(i) + "");
                totalNumber = totalNumber + Integer.parseInt(numberList.get(i));
            }
            Log.e("arraylist Size", arraylist.size() + ":SIZE");
            Log.e("totalNumber", totalNumber + ":TOTAL Number");
        }*/
        mScannerView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
    }

    @Override
    public void handleResult(Result result) {
        totalNumber = 0;
        DateFormat df = new SimpleDateFormat("EEE - hh:mm a");
        String date = df.format(Calendar.getInstance().getTime());

        DateFormat df1 = new SimpleDateFormat("HH:mm");

        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

        final String date1 = df2.format(Calendar.getInstance().getTime());
        try {
            Date dtStartOK = df2.parse(date1);
                  Log.e("Time........", Calendar.getInstance().getTime() + "()()()()()()()");
            System.out.println(DateFormat.getTimeInstance().format(dtStartOK));
            current_time = DateFormat.getTimeInstance().format(dtStartOK);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final String str = result.getText().trim();
        DateFormat df3 = new SimpleDateFormat("yyyy-MM-dd");
        final String date2 = df3.format(Calendar.getInstance().getTime());

        all_arraylist = new ArrayList<DataClass>();
        arraylist = new ArrayList<DataClass>();
        weightList = new ArrayList<String>();
        numberList = new ArrayList<String>();
        nameList = new ArrayList<String>();

        helper = new RealmHelper(realm);
        arraylist = helper.retrieve(str);
        all_arraylist = helper.retrieveAllData();

        if(arraylist.size()>0)
        {
            for(int i = 0;i<arraylist.size();i++) {
                nameList.add(arraylist.get(i).getWorker_name());
                arrayList_fruits.add(arraylist.get(i).getFruit());
                Log.e("nameList",nameList.get(i)+":SIZE");
            }
            Log.e("Total fruits by Name = ",arrayList_fruits.size()+":SIZE");
        }

        weightList = helper.retrieveWeight(str);
        for(int i = 0;i<weightList.size();i++) {
            Log.e("weight : "+i,weightList.get(i)+"");
            totalWeight = totalWeight+Float.parseFloat(weightList.get(i));
        }
        Log.e("totalWeight",totalWeight+":TOTAL Weight");

        numberList = helper.retrieveNumber(str);
        for(int i = 0;i<numberList.size();i++) {
           Log.e("number : "+i,numberList.get(i)+"");
            totalNumber = totalNumber+Integer.parseInt(numberList.get(i));
        }
        Log.e("arraylist Size",arraylist.size()+":SIZE");
        Log.e("totalNumber",str+"'fruits = "+totalNumber+":TOTAL Number");

        for(int i=0;i<all_arraylist.size();i++)
        {
            totalAllWeight = totalAllWeight+Float.parseFloat(all_arraylist.get(i).getWeight());
        }
        Log.e("All Fruites Weight = ",totalAllWeight+":TOTAL Weight");

        for(int i=0;i<all_arraylist.size();i++)
        {
            Log.e("getTime_of_record",all_arraylist.get(i).getTime_of_record());
        }

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog);
        Button btnsubmit = (Button) dialog.findViewById(R.id.btnsubmit);
        Button btnrecords = (Button)dialog.findViewById(R.id.btnrecords);
        final EditText edtname = (EditText) dialog.findViewById(R.id.edtname);
        final EditText edtweight = (EditText) dialog.findViewById(R.id.edtweight);
        final EditText edtnumber = (EditText) dialog.findViewById(R.id.edtnumber);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fruit_name = edtname.getText().toString();
                weight = edtweight.getText().toString();
                number = edtnumber.getText().toString();
            //    time_of_record = edttor.getText().toString();

                DataClass d = new DataClass();
                //          Log.e("str...i", str + "...");
                d.setWorker_name(str);
                d.setNumber(number);
                d.setTime_of_record(date1);
                d.setFruit(fruit_name);
                d.setWeight(weight);
                helper.save(d);
                dialog.dismiss();
                mScannerView.resumeCameraPreview(MainActivity.this);
            }
        });
        if(arraylist.size()>0)
        {
            btnrecords.setVisibility(View.VISIBLE);
           /* for(int i = 0;i<arraylist.size();i++) {
                Log.e("getWorker_name", arraylist.get(i).getWorker_name() + ":MMMMMMMMM");
                Log.e("getFruit", arraylist.get(i).getFruit() + ":MMMMMMMMM");
                Log.e("getNumber", arraylist.get(i).getNumber() + ":MMMMMMMMM");
                Log.e("getWeight", arraylist.get(i).getWeight() + ":MMMMMMMMM");
            }*/
        }
        else
        {
            btnrecords.setVisibility(View.GONE);
        }
        btnrecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RecordsActivity.class);
                intent.putParcelableArrayListExtra("arraylist",arraylist);
                startActivity(intent);
            Log.e("arraylist____",arraylist.size()+":IIIIIIII");
                dialog.dismiss();
            }
        });
        dialog.show();
       /* if (arraylist.size() == 0) {
            DataClass d = new DataClass();
            //    Log.e("str...0", str + "...");
            d.setWorker_name(str);
            d.setTime_of_record(date2);
            d.setNumber(date1);
            d.setCheck_status("1");
            d.setTotal_hours("00:00:00");
            d.setNote("late...");
            helper.save(d);
            final Dialog dialog1 = new Dialog(MainActivity.this);
            dialog1.setContentView(R.layout.design_dialog);
            TextView txtName = (TextView) dialog1.findViewById(R.id.txtName);
            TextView txtTime = (TextView) dialog1.findViewById(R.id.txtTime);
            TextView txtTotal = (TextView) dialog1.findViewById(R.id.txtTotal);
            txtName.setText(str + "");
            txtTime.setText(current_time + "");
            txtTotal.setText("2");
            dialog1.show();
            final Timer timer2 = new Timer();
            timer2.schedule(new TimerTask() {
                public void run() {
                    dialog1.dismiss();
                    timer2.cancel(); //this will cancel the timer of the system
                }
            }, 2000);
            mScannerView.resumeCameraPreview(MainActivity.this);
        }*/
    }
    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.resumeCameraPreview(MainActivity.this);
    } @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //     Log.e("result..", "result..");
                checkPermission();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot open camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void checkPermission() {

        //   Log.e("check...", "check..");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            /*Log.e("INTERNET ON", isNetworkAvailable() + "..INTERNET ON");
            if (isNetworkAvailable()) {

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            } else {
                Toast.makeText(this, "No internet connection.", Toast.LENGTH_LONG).show();
            }*/
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mScannerView.resumeCameraPreview(MainActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.resumeCameraPreview(MainActivity.this);
    }
    private void createExcelSheet()
    {
        arraylist_demo = new ArrayList<DataClass>();
        String Fnamexls="excelSheet"+System.currentTimeMillis()+ ".xls";
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File (sdCard.getAbsolutePath() + "/newfolder");
        directory.mkdirs();
        File file = new File(directory, Fnamexls);

        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("en", "EN"));

        WritableWorkbook workbook;
        try {
            int a = 1;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //workbook.createSheet("Report", 0);
            DataClass data = new DataClass();
            data.setWorker_name("Bhavna");
            data.setTime_of_record("04-05-2018");
            data.setWeight("10");
            data.setFruit("apple");
            data.setNumber("20");

            data.setWorker_name("Shruti");
            data.setTime_of_record("04-05-2018");
            data.setWeight("20");
            data.setFruit("apple");
            data.setNumber("30");

            data.setWorker_name("Meghana");
            data.setTime_of_record("04-05-2018");
            data.setWeight("10");
            data.setFruit("mango");
            data.setNumber("25");

            data.setWorker_name("Bhavna");
            data.setTime_of_record("04-05-2018");
            data.setWeight("15");
            data.setFruit("mango");
            data.setNumber("30");

            arraylist_demo.add(data);
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
           /* Label label = new Label(0, 2, "SECOND");
            Label label1 = new Label(0,1,"first");
            Label label0 = new Label(0,0,"HEADING");
            Label label3 = new Label(1,0,"Heading2");
            Label label4 = new Label(1,1,String.valueOf(a));*/
           Label[] labels = new Label[4];

               Label label = new Label(0, 0, "Worker Name");
               Label label1 = new Label(1, 0, "Fruit Name");
               Label label0 = new Label(2, 0, "Weight");
               Label label3 = new Label(3, 0, "Number");
               Label label4 = new Label(4, 0, "Date");
            for(int i = 0;i<arraylist_demo.size();i++) {
                labels[i] = new Label(i,1,arraylist_demo.get(i).getWorker_name());
                labels[i] = new Label(i,2,arraylist_demo.get(i).getFruit());
                labels[i] = new Label(i,3,arraylist_demo.get(i).getNumber());
                labels[i] = new Label(i,4,arraylist_demo.get(i).getTime_of_record());
                labels[i] = new Label(i,5,arraylist_demo.get(i).getWeight());
                Log.e("value","c = "+i+"r = "+1+"");
                sheet.addCell(labels[i]);
            }
               try {
                   sheet.addCell(label);
                   sheet.addCell(label1);
                   sheet.addCell(label0);
                   sheet.addCell(label4);
                   sheet.addCell(label3);
               } catch (RowsExceededException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               } catch (WriteException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               }


            workbook.write();
            try {
                workbook.close();
            } catch (WriteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //createExcel(excelSheet);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}
