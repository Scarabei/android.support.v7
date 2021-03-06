package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.zzc;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzblg implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzblf[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = zzb.zzd(var1);
      DriveId var4 = null;
      MetadataBundle var5 = null;
      zzc var6 = null;
      Integer var7 = null;
      boolean var8 = false;
      String var9 = null;
      int var10 = 0;
      int var11 = 0;
      String var12 = null;

      while(var2.dataPosition() < var3) {
         int var13;
         switch((var13 = var2.readInt()) & 65535) {
         case 2:
            var4 = (DriveId)zzb.zza(var2, var13, DriveId.CREATOR);
            break;
         case 3:
            var5 = (MetadataBundle)zzb.zza(var2, var13, MetadataBundle.CREATOR);
            break;
         case 4:
            var6 = (zzc)zzb.zza(var2, var13, zzc.CREATOR);
            break;
         case 5:
            var7 = zzb.zzh(var2, var13);
            break;
         case 6:
            var8 = zzb.zzc(var2, var13);
            break;
         case 7:
            var9 = zzb.zzq(var2, var13);
            break;
         case 8:
            var10 = zzb.zzg(var2, var13);
            break;
         case 9:
            var11 = zzb.zzg(var2, var13);
            break;
         case 10:
            var12 = zzb.zzq(var2, var13);
            break;
         default:
            zzb.zzb(var2, var13);
         }
      }

      zzb.zzF(var2, var3);
      return new zzblf(var4, var5, var6, var7, var8, var9, var10, var11, var12);
   }
}
