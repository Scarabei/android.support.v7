package com.google.android.gms.cast.framework.media;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zza implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new CastMediaOptions[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = com.google.android.gms.common.internal.safeparcel.zzb.zzd(var1);
      String var4 = null;
      String var5 = null;
      IBinder var6 = null;
      NotificationOptions var7 = null;

      while(var2.dataPosition() < var3) {
         int var8;
         switch((var8 = var2.readInt()) & 65535) {
         case 2:
            var4 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var8);
            break;
         case 3:
            var5 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var8);
            break;
         case 4:
            var6 = com.google.android.gms.common.internal.safeparcel.zzb.zzr(var2, var8);
            break;
         case 5:
            var7 = (NotificationOptions)com.google.android.gms.common.internal.safeparcel.zzb.zza(var2, var8, NotificationOptions.CREATOR);
            break;
         default:
            com.google.android.gms.common.internal.safeparcel.zzb.zzb(var2, var8);
         }
      }

      com.google.android.gms.common.internal.safeparcel.zzb.zzF(var2, var3);
      return new CastMediaOptions(var4, var5, var6, var7);
   }
}
