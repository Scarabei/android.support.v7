package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.zzs;
import java.util.ArrayList;

public final class zzbol implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzbok[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = zzb.zzd(var1);
      ArrayList var4 = null;
      int var5 = 0;

      while(var2.dataPosition() < var3) {
         int var6;
         switch((var6 = var2.readInt()) & 65535) {
         case 2:
            var4 = zzb.zzc(var2, var6, zzs.CREATOR);
            break;
         case 3:
            var5 = zzb.zzg(var2, var6);
            break;
         default:
            zzb.zzb(var2, var6);
         }
      }

      zzb.zzF(var2, var3);
      return new zzbok(var4, var5);
   }
}
