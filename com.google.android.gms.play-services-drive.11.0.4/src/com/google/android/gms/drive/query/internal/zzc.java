package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzc implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzb[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = com.google.android.gms.common.internal.safeparcel.zzb.zzd(var1);
      zzx var4 = null;
      MetadataBundle var5 = null;

      while(var2.dataPosition() < var3) {
         int var6;
         switch((var6 = var2.readInt()) & 65535) {
         case 1:
            var4 = (zzx)com.google.android.gms.common.internal.safeparcel.zzb.zza(var2, var6, zzx.CREATOR);
            break;
         case 2:
            var5 = (MetadataBundle)com.google.android.gms.common.internal.safeparcel.zzb.zza(var2, var6, MetadataBundle.CREATOR);
            break;
         default:
            com.google.android.gms.common.internal.safeparcel.zzb.zzb(var2, var6);
         }
      }

      com.google.android.gms.common.internal.safeparcel.zzb.zzF(var2, var3);
      return new zzb(var4, var5);
   }
}
