package com.google.android.gms.dynamite;

import android.content.Context;

final class zzc implements DynamiteModule.zzd {
   public final zzi zza(Context var1, String var2, zzh var3) throws DynamiteModule.zzc {
      zzi var4;
      (var4 = new zzi()).zzaSU = var3.zzE(var1, var2);
      if (var4.zzaSU != 0) {
         var4.zzaSW = -1;
      } else {
         var4.zzaSV = var3.zzb(var1, var2, true);
         if (var4.zzaSV != 0) {
            var4.zzaSW = 1;
         }
      }

      return var4;
   }
}
