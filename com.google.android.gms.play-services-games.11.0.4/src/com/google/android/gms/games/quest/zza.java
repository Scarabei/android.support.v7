package com.google.android.gms.games.quest;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zza implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new MilestoneEntity[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = com.google.android.gms.common.internal.safeparcel.zzb.zzd(var1);
      String var4 = null;
      long var5 = 0L;
      long var7 = 0L;
      byte[] var9 = null;
      int var10 = 0;
      String var11 = null;

      while(var2.dataPosition() < var3) {
         int var12;
         switch((var12 = var2.readInt()) & 65535) {
         case 1:
            var4 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var12);
            break;
         case 2:
            var5 = com.google.android.gms.common.internal.safeparcel.zzb.zzi(var2, var12);
            break;
         case 3:
            var7 = com.google.android.gms.common.internal.safeparcel.zzb.zzi(var2, var12);
            break;
         case 4:
            var9 = com.google.android.gms.common.internal.safeparcel.zzb.zzt(var2, var12);
            break;
         case 5:
            var10 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(var2, var12);
            break;
         case 6:
            var11 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var12);
            break;
         default:
            com.google.android.gms.common.internal.safeparcel.zzb.zzb(var2, var12);
         }
      }

      com.google.android.gms.common.internal.safeparcel.zzb.zzF(var2, var3);
      return new MilestoneEntity(var4, var5, var7, var9, var10, var11);
   }
}
