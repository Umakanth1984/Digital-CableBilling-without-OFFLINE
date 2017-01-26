package com.digitalrupay.activities;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalrupay.R;
import com.digitalrupay.datamodels.SessionData;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.UUID;

public class SettingsActivity extends Activity {

    private static final int REQUEST_ENABLE_BT = 1;
//    private String TAG = getPackageName().getClass().getName();

    ListView listDevicesFound;
    Button btnScanDevice, backBtnSET;
    TextView stateBluetooth, btaddressTv;
    BluetoothAdapter bluetoothAdapter;
    private BluetoothAdapter mBluetoothAdapter = null;
    static final UUID MY_UUID = UUID.randomUUID();

    String address = "";

    EditText bluetoothTXT;
    public final String DATA_PATH1 = Environment.getExternalStorageDirectory()
            + "/";

    ArrayAdapter<String> btArrayAdapter;


    private int PERMISSION_ALL = 0;
    /**
     * Permissions required to read and write storage and camera. Used by the {@link SettingsActivity}.
     */
    protected String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        btaddressTv = (TextView) findViewById(R.id.btaddTV);
        // ***********************************************************

        try {

            FileInputStream fstream = new FileInputStream(DATA_PATH1
                    + "BTaddress.txt");

            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null) {
                btaddressTv.setText(strLine);
            }

            in.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());

        }

        // ***********************************************************

        btnScanDevice = (Button) findViewById(R.id.scandevice);

        stateBluetooth = (TextView) findViewById(R.id.bluetoothstate);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        listDevicesFound = (ListView) findViewById(R.id.devicesfound);
        btArrayAdapter = new ArrayAdapter<String>(SettingsActivity.this,
                android.R.layout.simple_list_item_1);
        listDevicesFound.setAdapter(btArrayAdapter);

        bluetoothTXT = (EditText) findViewById(R.id.bluetoothAds);

        CheckBlueToothState();

        btnScanDevice.setOnClickListener(btnScanDeviceOnClickListener);

        registerReceiver(ActionFoundReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        listDevicesFound.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

				/*
                 * Toast.makeText(getApplicationContext(),
				 * ""+listDevicesFound.getCount(), Toast.LENGTH_SHORT).show();
				 */
                String selection = (String) (listDevicesFound
                        .getItemAtPosition(position));
                Toast.makeText(getApplicationContext(),
                        "BLUETOOTH ADDRESS IS SAVED SUCCESSFULLY",
                        Toast.LENGTH_SHORT).show();

                address = selection.substring(0, 17);
                bluetoothTXT.setText(address);

                try {
                    SessionData.saveBluetoothAddress(address);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                Alertmessage();

            }
        });

        // *********************back Button***********************************
        backBtnSET = (Button) findViewById(R.id.backBtnSET);
        backBtnSET.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                finish();
            }

        });

        // **************************************************************

    }


    private void CheckBlueToothState() {
        if (bluetoothAdapter == null) {
            stateBluetooth.setText("Bluetooth NOT support");
        } else {
            if (bluetoothAdapter.isEnabled()) {
                if (bluetoothAdapter.isDiscovering()) {
                    stateBluetooth
                            .setText("Bluetooth is currently in device discovery process.");
                } else {
                    stateBluetooth.setText("Bluetooth is Enabled.");
                    btnScanDevice.setEnabled(true);
                }
            } else {
                stateBluetooth.setText("Bluetooth is NOT Enabled!");
                Intent enableBtIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    private OnClickListener btnScanDeviceOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            btArrayAdapter.clear();
            bluetoothAdapter.startDiscovery();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == REQUEST_ENABLE_BT) {
            CheckBlueToothState();
        }
    }

    private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                btArrayAdapter.add(device.getAddress() + "\n"
                        + device.getName());
                btArrayAdapter.notifyDataSetChanged();
            }
        }
    };

    public void Alertmessage() {

        if (mBluetoothAdapter == null) {

            Toast.makeText(this, "Bluetooth is not available.",
                    Toast.LENGTH_LONG).show();
            //finish();
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(this,
                    "Please enable your BT and re-run this program.",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void checkPermission() {
        // Check if the Camera permission is already available.
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        } else {
            // Storage permissions is already available, write the content.
            Log.i("Permission",
                    "STORAGE permission has already been granted. Displaying.");
        }
    }


}