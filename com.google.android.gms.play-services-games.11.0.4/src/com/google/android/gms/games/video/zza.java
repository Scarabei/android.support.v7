package com.google.android.gms.games.video;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zza implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new VideoCapabilities[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = com.google.android.gms.common.internal.safeparcel.zzb.zzd(var1);
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = false;
      boolean[] var7 = null;
      boolean[] var8 = null;

      while(var2.dataPosition() < var3) {
         int var9;
         switch((var9 = var2.readInt()) & 65535) {
         case 1:
            var4 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(var2, var9);
            break;
         case 2:
            var5 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(var2, var9);
            break;
         case 3:
            var6 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(var2, var9);
            break;
         case 4:
            var7 = com.google.android.gms.common.internal.safeparcel.zzb.zzv(var2, var9);
            break;
         case 5:
            var8 = com.google.android.gms.common.internal.safeparcel.zzb.zzv(var2, var9);
            break;
         default:
            com.google.android.gms.common.internal.safeparcel.zzb.zzb(var2, var9);
         }
      }

      com.google.android.gms.common.internal.safeparcel.zzb.zzF(var2, var3);
      return new VideoCapabilities(var4, var5, var6, var7, var8);
   }
}
