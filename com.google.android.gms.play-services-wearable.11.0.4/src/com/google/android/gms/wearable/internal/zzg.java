package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zzg implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzf[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = com.google.android.gms.common.internal.safeparcel.zzb.zzd(var1);
      int var4 = 0;

      while(var2.dataPosition() < var3) {
         int var5;
         switch((var5 = var2.readInt()) & 65535) {
         case 2:
            var4 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(var2, var5);
            break;
         default:
            com.google.android.gms.common.internal.safeparcel.zzb.zzb(var2, var5);
         }
      }

      com.google.android.gms.common.internal.safeparcel.zzb.zzF(var2, var3);
      return new zzf(var4);
   }
}
