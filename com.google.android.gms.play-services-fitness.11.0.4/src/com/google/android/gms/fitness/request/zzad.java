package com.google.android.gms.fitness.request;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.fitness.data.BleDevice;

public interface zzad extends IInterface {
   void onDeviceFound(BleDevice var1) throws RemoteException;

   void onScanStopped() throws RemoteException;
}
