package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zzc implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new AdBreakStatus[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = com.google.android.gms.common.internal.safeparcel.zzb.zzd(var1);
      long var4 = 0L;
      long var6 = 0L;
      String var8 = null;
      String var9 = null;
      long var10 = 0L;

      while(var2.dataPosition() < var3) {
         int var12;
         switch((var12 = var2.readInt()) & 65535) {
         case 2:
            var4 = com.google.android.gms.common.internal.safeparcel.zzb.zzi(var2, var12);
            break;
         case 3:
            var6 = com.google.android.gms.common.internal.safeparcel.zzb.zzi(var2, var12);
            break;
         case 4:
            var8 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var12);
            break;
         case 5:
            var9 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var12);
            break;
         case 6:
            var10 = com.google.android.gms.common.internal.safeparcel.zzb.zzi(var2, var12);
            break;
         default:
            com.google.android.gms.common.internal.safeparcel.zzb.zzb(var2, var12);
         }
      }

      com.google.android.gms.common.internal.safeparcel.zzb.zzF(var2, var3);
      return new AdBreakStatus(var4, var6, var8, var9, var10);
   }
}
