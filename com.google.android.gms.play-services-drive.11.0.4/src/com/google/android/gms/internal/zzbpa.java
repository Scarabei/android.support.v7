package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.zzc;

public final class zzbpa implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzboz[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = zzb.zzd(var1);
      zzc var4 = null;
      boolean var5 = false;

      while(var2.dataPosition() < var3) {
         int var6;
         switch((var6 = var2.readInt()) & 65535) {
         case 2:
            var4 = (zzc)zzb.zza(var2, var6, zzc.CREATOR);
            break;
         case 3:
            var5 = zzb.zzc(var2, var6);
            break;
         default:
            zzb.zzb(var2, var6);
         }
      }

      zzb.zzF(var2, var3);
      return new zzboz(var4, var5);
   }
}
