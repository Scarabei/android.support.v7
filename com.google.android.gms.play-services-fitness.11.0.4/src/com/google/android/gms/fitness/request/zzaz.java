package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.Session;

public final class zzaz implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzay[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = com.google.android.gms.common.internal.safeparcel.zzb.zzd(var1);
      int var4 = 0;
      Session var5 = null;
      IBinder var6 = null;

      while(var2.dataPosition() < var3) {
         int var7;
         switch((var7 = var2.readInt()) & 65535) {
         case 1:
            var5 = (Session)com.google.android.gms.common.internal.safeparcel.zzb.zza(var2, var7, Session.CREATOR);
            break;
         case 2:
            var6 = com.google.android.gms.common.internal.safeparcel.zzb.zzr(var2, var7);
            break;
         case 1000:
            var4 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(var2, var7);
            break;
         default:
            com.google.android.gms.common.internal.safeparcel.zzb.zzb(var2, var7);
         }
      }

      com.google.android.gms.common.internal.safeparcel.zzb.zzF(var2, var3);
      return new zzay(var4, var5, var6);
   }
}
