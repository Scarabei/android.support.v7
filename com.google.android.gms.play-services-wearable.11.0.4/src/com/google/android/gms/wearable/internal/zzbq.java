package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.wearable.DataApi;

final class zzbq implements zzc {
   // $FF: synthetic field
   private IntentFilter[] zzbRX;

   zzbq(IntentFilter[] var1) {
      this.zzbRX = var1;
      super();
   }

   // $FF: synthetic method
   public final void zza(zzfw var1, zzbaz var2, Object var3, zzbdw var4) throws RemoteException {
      DataApi.DataListener var8 = (DataApi.DataListener)var3;
      var1.zza(var2, var8, var4, this.zzbRX);
   }
}
