package sph.durga.rCard.jobseeker;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import sph.durga.rCard.R;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ListDevicesActivity extends Activity {

	int REQUEST_ENABLE_BT = 1;
	int REQUEST_DEVICE_DISCOVERABLE = 2;
	String BT_NAME = "sph.durga.rCard";
	UUID BT_UUID = UUID.fromString("f05b4c80-9c41-11e4-bd06-0800200c9a66");
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	BluetoothAdapter btAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.jobseeker_list_device_activity);
		setResult(Activity.RESULT_CANCELED);
		Button scanButton = (Button) findViewById(R.id.scanBtn);
		scanButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v) {
				doDiscovery();
				v.setVisibility(View.GONE);
			}
		});

		ArrayAdapter<String> pairedDevicesArrayAdapter =
				new ArrayAdapter<String>(this, R.layout.device_name);
		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

		// Find and set up the ListView for paired devices
		ListView pairedListView = (ListView) findViewById(R.id.paireddevicesList);
		pairedListView.setAdapter(pairedDevicesArrayAdapter);
		pairedListView.setOnItemClickListener(mDeviceClickListener);
		//  pairedDevicesArrayAdapter.setNotifyOnChange(true);

		// Find and set up the ListView for newly discovered devices
		ListView newDevicesListView = (ListView) findViewById(R.id.newdevicesList);
		newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
		newDevicesListView.setOnItemClickListener(mDeviceClickListener);
		//  mNewDevicesArrayAdapter.setNotifyOnChange(true);

		// Register for broadcasts when a device is discovered
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);

		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);

		// Get the local Bluetooth adapter
		btAdapter = BluetoothAdapter.getDefaultAdapter();

		// Get a set of currently paired devices
		Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

		// If there are paired devices, add each one to the ArrayAdapter
		if (pairedDevices.size() > 0) 
		{
			findViewById(R.id.paireddevicesTxt).setVisibility(View.VISIBLE);
			for (BluetoothDevice device : pairedDevices) {
				pairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
			}
		} else {
			String noDevices = "No paired devices";
			pairedDevicesArrayAdapter.add(noDevices);
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (btAdapter != null) {
			btAdapter.cancelDiscovery();
		}
		this.unregisterReceiver(mReceiver);
	}


	private void doDiscovery() 
	{
		setProgressBarIndeterminateVisibility(true);
		setTitle("scanning");
		findViewById(R.id.newdevicesTxt).setVisibility(View.VISIBLE);
		if (btAdapter.isDiscovering()) {
			btAdapter.cancelDiscovery();
		}
		btAdapter.startDiscovery();
	}

	/**
	 * The on-click listener for all devices in the ListViews
	 */
	private AdapterView.OnItemClickListener mDeviceClickListener
	= new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {

			btAdapter.cancelDiscovery();

			String info = ((TextView) v).getText().toString();
			String address = info.substring(info.length() - 17);
			Intent intent = new Intent();
			intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	};

	HashSet<String> discoveredDevicesSet = new HashSet<String>();

	/**
	 * The BroadcastReceiver that listens for discovered devices and changes the title when
	 * discovery is finished
	 */
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			String deviceStr;
			String action = intent.getAction();
			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// If it's already paired, skip it, because it's been listed already
				if (device.getBondState() != BluetoothDevice.BOND_BONDED) 
				{
					deviceStr = device.getName() + "\n" + device.getAddress();
					if(!discoveredDevicesSet.contains(deviceStr))
					{
						mNewDevicesArrayAdapter.add(deviceStr);
						discoveredDevicesSet.add(deviceStr);
					}
				}
				// When discovery is finished, change the Activity title
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
			{
				setProgressBarIndeterminateVisibility(false);
				setTitle("selected device");
				if (mNewDevicesArrayAdapter.getCount() == 0) {
					String noDevices = "No new devices found";
					mNewDevicesArrayAdapter.add(noDevices);
				}
			}
		}
	};

}