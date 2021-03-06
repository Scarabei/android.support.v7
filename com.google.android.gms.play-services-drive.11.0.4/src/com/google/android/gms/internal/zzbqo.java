package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.drive.DriveId;
import java.util.List;

public final class zzbqo extends zza {
   public static final Creator CREATOR = new zzbqp();
   private DriveId zzaPt;
   private List zzaPu;

   public final void writeToParcel(Parcel var1, int var2) {
      int var5 = zzd.zze(var1);
      zzd.zza(var1, 2, this.zzaPt, var2, false);
      zzd.zzc(var1, 3, this.zzaPu, false);
      zzd.zzI(var1, var5);
   }

   public zzbqo(DriveId var1, List var2) {
      this.zzaPt = var1;
      this.zzaPu = var2;
   }
}
