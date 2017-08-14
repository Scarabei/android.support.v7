package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

@zzzn
public final class zzky extends zza {
   public static final Creator CREATOR = new zzkz();
   public final int zzAR;

   public zzky(int var1) {
      this.zzAR = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var5 = zzd.zze(var1);
      zzd.zzc(var1, 2, this.zzAR);
      zzd.zzI(var1, var5);
   }
}
