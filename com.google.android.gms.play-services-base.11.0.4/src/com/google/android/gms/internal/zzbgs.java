package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzbgs implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzbgp[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = zzb.zzd(var1);
      int var4 = 0;
      String var5 = null;
      ArrayList var6 = null;

      while(var2.dataPosition() < var3) {
         int var7;
         switch((var7 = var2.readInt()) & 65535) {
         case 1:
            var4 = zzb.zzg(var2, var7);
            break;
         case 2:
            var5 = zzb.zzq(var2, var7);
            break;
         case 3:
            var6 = zzb.zzc(var2, var7, zzbgq.CREATOR);
            break;
         default:
            zzb.zzb(var2, var7);
         }
      }

      zzb.zzF(var2, var3);
      return new zzbgp(var4, var5, var6);
   }
}
