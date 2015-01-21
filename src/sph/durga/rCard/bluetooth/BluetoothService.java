package sph.durga.rCard.bluetooth;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import sph.durga.rCard.Constants;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class BluetoothService 
{
	private String BT_NAME = "sphdurgarCard";
	private UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private Handler mHandler;

	private final BluetoothAdapter mAdapter;
	Constants.sockettype socketType; 
	AcceptThread mAcceptThread;
	ReceiverCardThread mreceiverCardThread;
	ConnectThread mConnectThread;
	SendrCardThread msendrCardThread;

	public BluetoothService(Context context, Handler handler, Constants.sockettype sockType) 
	{
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		mHandler = handler;
		socketType = sockType;
	}


	public synchronized void start()
	{

		if(socketType == Constants.sockettype.server)
		{

			// Start the thread to listen on a BluetoothServerSocket
			if (mAcceptThread == null)
			{
				mAcceptThread = new AcceptThread();
				mAcceptThread.start();
			}
		}
		else
		{
			if (mConnectThread != null)
			{
				mConnectThread.cancel();
				mConnectThread = null;
			}
			if (msendrCardThread != null) {
				msendrCardThread.cancel();
				msendrCardThread = null;
			}
		}
	}

	public synchronized void stop()
	{
		if (socketType == Constants.sockettype.server && mAcceptThread != null) 
		{
			mAcceptThread.cancel();
			mAcceptThread = null;
		}
		else
		{
			if(msendrCardThread != null)
			{
				msendrCardThread.cancel();
				msendrCardThread = null;
			}
			if(mConnectThread != null)
			{
				mConnectThread.cancel();
				mConnectThread = null;
			}
		}
	}


	private class AcceptThread extends Thread
	{
		private BluetoothServerSocket btServerSocket;

		public AcceptThread()
		{
			BluetoothServerSocket tmpServerSocket = null;
			try 
			{
				tmpServerSocket = mAdapter.listenUsingRfcommWithServiceRecord(BT_NAME, BT_UUID);

			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
			btServerSocket = tmpServerSocket;
		}

		public void run()
		{
			BluetoothSocket socket = null;

			while(true)
			{
				try 
				{
					socket = btServerSocket.accept();
				}
				catch (IOException e) 
				{
					e.printStackTrace();
					break;
				}
				if(socket != null)
				{
					try 
					{
						btServerSocket.close();
					} catch (IOException e)
					{
						e.printStackTrace();
						try 
						{
							socket.close();
						} catch (IOException ex) 
						{
							e.printStackTrace();
						}

					}
					break;
				}
			}
			if(socket != null) {
				synchronized (BluetoothService.this)
				{
					mreceiverCardThread = null;
				}
				ConnectedServerSocket(socket);
			}
		}

		public void cancel() 
		{
			try 
			{
				btServerSocket.close();
			}
			catch (IOException e) 
			{
			}
		}
	}

	public synchronized void ConnectedServerSocket(BluetoothSocket socket)
	{
		// Cancel the thread that completed the connection
		if (mAcceptThread != null) 
		{
			mAcceptThread.cancel();
			mAcceptThread = null;
		}
		// Cancel any thread currently running a connection
		if (mreceiverCardThread != null) 
		{
			mreceiverCardThread.cancel();
			mreceiverCardThread = null;
		}
		// Start the thread to manage the connection and perform transmissions
		mreceiverCardThread = new ReceiverCardThread(socket);
		mreceiverCardThread.start();
	}

	private class ReceiverCardThread extends Thread
	{
		BluetoothSocket mSocket;
		public ReceiverCardThread(BluetoothSocket socket)
		{
			mSocket = socket;
		}
		public void run()
		{
			try 
			{		
				InputStream min;
				min = mSocket.getInputStream();
				byte[] buffer = new byte[1024];
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				
				while (min.available() > 0)
				{
					try 
					{
						int count = min.read(buffer);
						out.write(buffer, 0, count);
						
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				
				JSONObject jsonObj = new JSONObject(out.toString());
				
				Message msg =  mHandler.obtainMessage(Constants.MESSAGE_RCARD_JSON_DATA);
				Bundle bundle = new Bundle();
				bundle.putString(Constants.RCARD_JSON_DATA, jsonObj.toString());
				msg.setData(bundle);
				mHandler.sendMessage(msg);
				mbReader.close();
				min.close();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			} catch (JSONException e) 
			{
				e.printStackTrace();
			}	

		}

		public void cancel()
		{
			try {
				mSocket.close();
			} 
			catch (IOException e)
			{
				//Log.e(TAG, "close() of connect socket failed", e);
			}
		}
	}

	public synchronized void connect(BluetoothDevice device, JSONObject jsonData) 
	{
		// Cancel any thread currently running a connection
		if (mConnectThread != null) 
		{
			mConnectThread.cancel();
			mConnectThread = null;
		}
		// Start the thread to connect with the given device
		mConnectThread = new ConnectThread(device, jsonData);
		mConnectThread.start();
	}


	private class ConnectThread extends Thread {
		private  BluetoothSocket mmSocket;
		private  BluetoothDevice mmDevice;
		private  JSONObject rCardjsonData;


		public ConnectThread(BluetoothDevice device, JSONObject jsonData) {
			mmDevice = device;
			rCardjsonData = jsonData;
			BluetoothSocket tmp = null;
			
			try {
				tmp = device.createRfcommSocketToServiceRecord(BT_UUID);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			mmSocket = tmp;
		}

		public void run() 
		{
			mAdapter.cancelDiscovery();
			try {
				mmSocket.connect();

			} catch (IOException e) {
				try 
				{
					mmSocket =(BluetoothSocket) mmDevice.getClass().getMethod("createRfcommSocketToServiceRecord", new Class[] {UUID.class}).invoke(mmDevice, (UUID) BT_UUID);
					mmSocket.connect();
				} 
				catch (IOException e2) 
				{
					try 
					{
						mmSocket.close();
					} catch (IOException e1) 
					{
						e1.printStackTrace();
					}
					connectionFailed();
					return;
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
				} catch (NoSuchMethodException e1) {
					e1.printStackTrace();
				}

			}
			synchronized (BluetoothService.this)
			{
				mConnectThread = null;
			}
			ConnectedClientSocket(mmSocket, mmDevice, rCardjsonData);
		}

		public void cancel()
		{
			try 
			{
				mmSocket.close();
			}
			catch (IOException e)
			{
				//Log.e(TAG, "close() of connect " + mSocketType + " socket failed", e);
			}
		}
	}

	public synchronized void ConnectedClientSocket(BluetoothSocket socket, BluetoothDevice
			device,  JSONObject jsonData) 
	{
		// Cancel the thread that completed the connection
		if (mConnectThread != null) 
		{
			mConnectThread.cancel();
			mConnectThread = null;
		}

		// Cancel any thread currently running a connection
		if (msendrCardThread != null) 
		{
			msendrCardThread.cancel();
			msendrCardThread = null;
		}

		// Start the thread to manage the connection and perform transmissions
		msendrCardThread = new SendrCardThread(socket, jsonData);
		msendrCardThread.start();

		// Send the name of the connected device back to the UI Activity
		Message msg = mHandler.obtainMessage(Constants.MESSAGE_DEVICE_NAME);
		Bundle bundle = new Bundle();
		bundle.putString(Constants.DEVICE_NAME, device.getName());
		msg.setData(bundle);
		mHandler.sendMessage(msg);
	}


	private class SendrCardThread extends Thread 
	{
		private final BluetoothSocket mmSocket;
		private final OutputStream mmOutStream;
		private final JSONObject rcardJSON;

		public SendrCardThread(BluetoothSocket socket, JSONObject rcarddata) 
		{
			mmSocket = socket;
			OutputStream tmpOut = null;
			rcardJSON = rcarddata;
			try 
			{
				tmpOut = socket.getOutputStream();
			} catch (IOException e)
			{

			}
			mmOutStream = tmpOut;
		}

		public void run() 
		{

			try
			{
				String stringToSend = rcardJSON.toString();
				mmOutStream.write(stringToSend.getBytes());
				mmOutStream.flush();
				// Share the sent message back to the UI Activity
				mHandler.obtainMessage(Constants.MESSAGE_JSON_DATA_WRITE, -1, -1, rcardJSON).sendToTarget();
			} 
			catch (IOException e) 
			{
				connectionFailed();
				// Start the service over to restart listening mode
				BluetoothService.this.start();
			}
		}

		public void cancel() 
		{
			try 
			{
				mmSocket.close();
				mmOutStream.close();
			} catch (IOException e)
			{
				//Log.e(TAG, "close() of connect socket failed", e);
			}
		}
	}

	public void connectionFailed()
	{
		Message msg = mHandler.obtainMessage(Constants.MESSAGE_TOAST);
		Bundle bundle = new Bundle();
		bundle.putString(Constants.TOAST, "Device connection was lost");
		msg.setData(bundle);
		mHandler.sendMessage(msg);
		// Start the service over to restart listening mode
		BluetoothService.this.start();
	}
}
