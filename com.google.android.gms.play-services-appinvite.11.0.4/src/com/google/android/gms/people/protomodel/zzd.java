package com.google.android.gms.people.protomodel;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public final class zzd implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new FetchBackUpDeviceContactInfoResponseEntity[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = com.google.android.gms.common.internal.safeparcel.zzb.zzd(var1);
      int var4 = 0;
      ArrayList var5 = null;

      while(var2.dataPosition() < var3) {
         int var6;
         switch((var6 = var2.readInt()) & 65535) {
         case 1:
            var4 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(var2, var6);
            break;
         case 2:
            var5 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(var2, var6, zzc.CREATOR);
            break;
         default:
            com.google.android.gms.common.internal.safeparcel.zzb.zzb(var2, var6);
         }
      }

      com.google.android.gms.common.internal.safeparcel.zzb.zzF(var2, var3);
      return new FetchBackUpDeviceContactInfoResponseEntity(var4, var5);
   }
}
