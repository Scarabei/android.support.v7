package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.Arrays;

public final class zzcmk extends zza {
   public static final Creator CREATOR = new zzcml();
   private final String zzbwG;

   public zzcmk(String var1) {
      this.zzbwG = var1;
   }

   public final int hashCode() {
      return Arrays.hashCode(new Object[]{this.zzbwG});
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 instanceof zzcmk) {
         zzcmk var2 = (zzcmk)var1;
         return zzbe.equal(this.zzbwG, var2.zzbwG);
      } else {
         return false;
      }
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var5 = zzd.zze(var1);
      zzd.zza(var1, 1, this.zzbwG, false);
      zzd.zzI(var1, var5);
   }
}
