package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.nearby.connection.Payload;
import java.util.List;

final class zzclx extends zzcmj {
   // $FF: synthetic field
   private List zzbwZ;
   // $FF: synthetic field
   private byte[] zzbxj;

   zzclx(zzclm var1, GoogleApiClient var2, List var3, byte[] var4) {
      this.zzbwZ = var3;
      this.zzbxj = var4;
      super(var2, (zzcln)null);
   }

   // $FF: synthetic method
   protected final void zza(zzb var1) throws RemoteException {
      zzckm var3 = (zzckm)var1;
      var3.zza(this, (String[])this.zzbwZ.toArray(new String[0]), Payload.fromBytes(this.zzbxj), true);
   }
}
