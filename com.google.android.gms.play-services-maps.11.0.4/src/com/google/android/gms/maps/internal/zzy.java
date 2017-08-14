package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzee;

public abstract class zzy extends zzee implements zzx {
   public zzy() {
      this.attachInterface(this, "com.google.android.gms.maps.internal.IOnGroundOverlayClickListener");
   }

   public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if (this.zza(var1, var2, var3, var4)) {
         return true;
      } else if (var1 == 1) {
         com.google.android.gms.maps.model.internal.zzg var5 = com.google.android.gms.maps.model.internal.zzh.zzac(var2.readStrongBinder());
         this.zza(var5);
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}