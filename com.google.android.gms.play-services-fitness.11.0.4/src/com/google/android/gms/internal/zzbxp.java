package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.fitness.request.zzbk;

final class zzbxp extends zzbus {
   // $FF: synthetic field
   private String zzaVA;

   zzbxp(zzbxk var1, GoogleApiClient var2, String var3) {
      this.zzaVA = var3;
      super(var2);
   }

   // $FF: synthetic method
   protected final void zza(zzb var1) throws RemoteException {
      zzbup var3 = (zzbup)var1;
      zzbzi var4 = new zzbzi(this);
      ((zzbwh)var3.zzrf()).zza(new zzbk(this.zzaVA, var4));
   }
}
