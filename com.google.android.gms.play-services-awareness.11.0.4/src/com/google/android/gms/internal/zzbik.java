package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbik implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzbii[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = zzb.zzd(var1);
      byte[] var4 = null;

      while(var2.dataPosition() < var3) {
         int var5;
         switch((var5 = var2.readInt()) & 65535) {
         case 2:
            var4 = zzb.zzt(var2, var5);
            break;
         default:
            zzb.zzb(var2, var5);
         }
      }

      zzb.zzF(var2, var3);
      return new zzbii(var4);
   }
}
