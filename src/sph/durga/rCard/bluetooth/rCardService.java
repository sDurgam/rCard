package sph.durga.rCard.bluetooth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import android.util.Log;

public class rCardService 
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

	public rCardService(Context context, Handler handler, Constants.sockettype sockType) 
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
		if (socketType == Constants.sockettype.server) 
		{
			if(mreceiverCardThread != null)
			{
				mreceiverCardThread.cancel();
				mreceiverCardThread = null;
			}
			if(mAcceptThread != null)
			{
				mAcceptThread.cancel();
				mAcceptThread = null;
			}
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
				synchronized (rCardService.this)
				{
					mreceiverCardThread = null;
				}
				ConnectedServerSocket(socket);
			}
		}

		public void cancel() 
		{
			//			try 
			//			{
			//				//btServerSocket.close();
			//			}
			//			catch (IOException e) 
			//			{
			//			}
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
		InputStream min;
		public ReceiverCardThread(BluetoothSocket socket)
		{
			mSocket = socket;
		}
		public void run()
		{
			try 
			{		
				Log.d("socket_server", "connected");
				InputStream tmp;
				tmp = mSocket.getInputStream();
				min = tmp;
				byte[] buffer = new byte[1024];
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				Thread.sleep(1000);
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
				out.flush();
				String json = out.toString();
				JSONObject jsonObj =  new JSONObject(json.toString());
				Bundle bundle = new Bundle();
				bundle.putString(Constants.RCARD_JSON_DATA, jsonObj.toString());
				Log.d("socket_server",jsonObj.toString() );
				
				Message msg =  mHandler.obtainMessage(Constants.MESSAGE_RCARD_JSON_DATA);
				msg.setData(bundle);
				mHandler.sendMessage(msg);
				//mHandler.obtainMessage(Constants.MESSAGE_RCARD_JSON_DATA, -1, -1, bundle).sendToTarget();

			}
			catch (IOException e) 
			{
				Log.d("socket_server", e.getMessage());
				e.printStackTrace();
			} catch (JSONException e) 
			{
				e.printStackTrace();
				Log.d("socket_server", e.getMessage());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	

		}

		public void cancel()
		{
			try {
				min.close();
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


	private class ConnectThread extends Thread 
	{
		private  BluetoothSocket mmSocket;
		private  BluetoothDevice mmDevice;
		private  JSONObject rCardjsonData;


		public ConnectThread(BluetoothDevice device, JSONObject jsonData) 
		{
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
			while(true)
			{
				try {

					if(!mmSocket.isConnected())
					{
						mmSocket.connect();
						break;
					}

				} catch (IOException e) {
					try 
					{
						mmSocket =(BluetoothSocket) mmDevice.getClass().getMethod("createRfcommSocketToServiceRecord", new Class[] {UUID.class}).invoke(mmDevice, (UUID) BT_UUID);
						if(!mmSocket.isConnected())
						{
							mmSocket.connect();
							break;
						}
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
			}
			synchronized (rCardService.this)
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
		//		if (mConnectThread != null) 
		//		{
		//			mConnectThread.cancel();
		//			mConnectThread = null;
		//		}

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
		//mHandler.obtainMessage(Constants.MESSAGE_TOAST, -1, -1, bundle).sendToTarget();
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
				Bundle bundle = new Bundle();
				bundle.putString(Constants.TOAST, "Sent rCard successfully");
				Message msg =  mHandler.obtainMessage(Constants.MESSAGE_JSON_DATA_WRITE);
				msg.setData(bundle);
				mHandler.sendMessage(msg);
				
				
			} 
			catch (IOException e) 
			{
				connectionFailed();
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

		Bundle bundle = new Bundle();
		bundle.putString(Constants.TOAST, "Device connection was lost");
		
		Message msg =  mHandler.obtainMessage(Constants.MESSAGE_TOAST);
		msg.setData(bundle);
		mHandler.sendMessage(msg);
		// Start the service over to restart listening mode
		rCardService.this.start();
	}
}
