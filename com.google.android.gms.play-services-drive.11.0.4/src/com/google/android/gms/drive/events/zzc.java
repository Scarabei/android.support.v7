package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zzc implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzb[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = com.google.android.gms.common.internal.safeparcel.zzb.zzd(var1);
      String var4 = null;
      zze var5 = null;

      while(var2.dataPosition() < var3) {
         int var6;
         switch((var6 = var2.readInt()) & 65535) {
         case 2:
            var4 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var6);
            break;
         case 3:
            var5 = (zze)com.google.android.gms.common.internal.safeparcel.zzb.zza(var2, var6, zze.CREATOR);
            break;
         default:
            com.google.android.gms.common.internal.safeparcel.zzb.zzb(var2, var6);
         }
      }

      com.google.android.gms.common.internal.safeparcel.zzb.zzF(var2, var3);
      return new zzb(var4, var5);
   }
}
