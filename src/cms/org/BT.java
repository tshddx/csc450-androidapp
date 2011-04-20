package cms.org;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class BT extends Thread
{
	private BluetoothSocket socket;
	private  BluetoothDevice device;
	private InputStream inStream;
	private OutputStream outStream;
	private  Handler handler;
	private  BluetoothAdapter bt;			
	private final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	
	public BT(Context tContext, Handler tHandler, String mac)
	{
		this.bt=BluetoothAdapter.getDefaultAdapter();
		this.handler = tHandler;
		this.device = bt.getRemoteDevice(mac);
	}
	
	public void startBT()
	{
		BluetoothSocket tmp=null;
		
		try
		{
			tmp = device.createRfcommSocketToServiceRecord(myUUID);
			
		}
		catch(IOException e)
		{
			//throw error
		}
		
		socket = tmp;
	}
	
	public void connectBT()
	{
		bt.cancelDiscovery();
		
		try
		{
			Log.d("BTCON","Connecting socket....");
			socket.connect();
		}
		catch(IOException closeException)
		{
			//can't connect for whatever reason
			try
			{
				Log.d("BTCON", "Socked crapped out...trying to close");
				socket.close();
			}
			catch(IOException exceptionAgain)
			{
				//yet more error catching
				Log.d("BTCON", "Socked crashed closing....its sick");
			}
			
			//means it's happy so now lets do stuff with it
			//TODO start reading the socket called "socket"
		}
	}
	
		
	
	public void cancel() 
	{
		Log.d("BTCON", "Closing socket from cancel");
		try 
		{
			socket.close();
		}
		catch (IOException e) 
		{
			
		}
		
		Log.d("BTCON", "Closed socket called from function cancel");
			
	}
	
	public void connectStreams()
	{
		InputStream tmpIn = null;
		OutputStream tmpOut = null;
		
		try
		{
			tmpIn = socket.getInputStream();
			tmpOut = socket.getOutputStream();
		}
		catch(IOException e)
		{
			Log.d("BTCON", "Connecting Streams crashed");
		}
		
		inStream = tmpIn;
		outStream = tmpOut;
		
	}
		
	public void read()
	{
		byte[] buffer = new byte[1024];
		int bytes;
		
		while (true)
		{
			try
			{
				bytes = inStream.read(buffer);
				//Log.d("BTCON", bytes.toA);
				Log.d("BTCON","Read" + bytes + "bytes");
			}
			catch(IOException e)
			{
				break;
			}
		}
	}
	   
	public void write(byte[] bytes) 
	{
		try 
	    {
			outStream.write(bytes);
	    } 
	    catch (IOException e) 
	    { }
	}	   
}
