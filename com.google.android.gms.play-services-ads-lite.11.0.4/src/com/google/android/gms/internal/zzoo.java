package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzoo implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzon[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = zzb.zzd(var1);
      int var4 = 0;
      boolean var5 = false;
      int var6 = 0;
      boolean var7 = false;
      int var8 = 0;
      zzlx var9 = null;

      while(var2.dataPosition() < var3) {
         int var10;
         switch((var10 = var2.readInt()) & 65535) {
         case 1:
            var4 = zzb.zzg(var2, var10);
            break;
         case 2:
            var5 = zzb.zzc(var2, var10);
            break;
         case 3:
            var6 = zzb.zzg(var2, var10);
            break;
         case 4:
            var7 = zzb.zzc(var2, var10);
            break;
         case 5:
            var8 = zzb.zzg(var2, var10);
            break;
         case 6:
            var9 = (zzlx)zzb.zza(var2, var10, zzlx.CREATOR);
            break;
         default:
            zzb.zzb(var2, var10);
         }
      }

      zzb.zzF(var2, var3);
      return new zzon(var4, var5, var6, var7, var8, var9);
   }
}
