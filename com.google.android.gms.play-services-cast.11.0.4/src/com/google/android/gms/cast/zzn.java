package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.images.WebImage;
import java.util.ArrayList;

public final class zzn implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new CastDevice[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = com.google.android.gms.common.internal.safeparcel.zzb.zzd(var1);
      String var4 = null;
      String var5 = null;
      String var6 = null;
      String var7 = null;
      String var8 = null;
      int var9 = 0;
      ArrayList var10 = null;
      int var11 = 0;
      int var12 = -1;
      String var13 = null;
      String var14 = null;
      int var15 = 0;

      while(var2.dataPosition() < var3) {
         int var16;
         switch((var16 = var2.readInt()) & 65535) {
         case 2:
            var4 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var16);
            break;
         case 3:
            var5 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var16);
            break;
         case 4:
            var6 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var16);
            break;
         case 5:
            var7 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var16);
            break;
         case 6:
            var8 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var16);
            break;
         case 7:
            var9 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(var2, var16);
            break;
         case 8:
            var10 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(var2, var16, WebImage.CREATOR);
            break;
         case 9:
            var11 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(var2, var16);
            break;
         case 10:
            var12 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(var2, var16);
            break;
         case 11:
            var13 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var16);
            break;
         case 12:
            var14 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var16);
            break;
         case 13:
            var15 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(var2, var16);
            break;
         default:
            com.google.android.gms.common.internal.safeparcel.zzb.zzb(var2, var16);
         }
      }

      com.google.android.gms.common.internal.safeparcel.zzb.zzF(var2, var3);
      return new CastDevice(var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15);
   }
}
