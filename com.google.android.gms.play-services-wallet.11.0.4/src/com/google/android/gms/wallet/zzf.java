package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.identity.intents.model.UserAddress;

public final class zzf implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new FullWallet[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = com.google.android.gms.common.internal.safeparcel.zzb.zzd(var1);
      String var4 = null;
      String var5 = null;
      ProxyCard var6 = null;
      String var7 = null;
      zza var8 = null;
      zza var9 = null;
      String[] var10 = null;
      UserAddress var11 = null;
      UserAddress var12 = null;
      InstrumentInfo[] var13 = null;
      PaymentMethodToken var14 = null;

      while(var2.dataPosition() < var3) {
         int var15;
         switch((var15 = var2.readInt()) & 65535) {
         case 2:
            var4 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var15);
            break;
         case 3:
            var5 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var15);
            break;
         case 4:
            var6 = (ProxyCard)com.google.android.gms.common.internal.safeparcel.zzb.zza(var2, var15, ProxyCard.CREATOR);
            break;
         case 5:
            var7 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(var2, var15);
            break;
         case 6:
            var8 = (zza)com.google.android.gms.common.internal.safeparcel.zzb.zza(var2, var15, zza.CREATOR);
            break;
         case 7:
            var9 = (zza)com.google.android.gms.common.internal.safeparcel.zzb.zza(var2, var15, zza.CREATOR);
            break;
         case 8:
            var10 = com.google.android.gms.common.internal.safeparcel.zzb.zzA(var2, var15);
            break;
         case 9:
            var11 = (UserAddress)com.google.android.gms.common.internal.safeparcel.zzb.zza(var2, var15, UserAddress.CREATOR);
            break;
         case 10:
            var12 = (UserAddress)com.google.android.gms.common.internal.safeparcel.zzb.zza(var2, var15, UserAddress.CREATOR);
            break;
         case 11:
            var13 = (InstrumentInfo[])com.google.android.gms.common.internal.safeparcel.zzb.zzb(var2, var15, InstrumentInfo.CREATOR);
            break;
         case 12:
            var14 = (PaymentMethodToken)com.google.android.gms.common.internal.safeparcel.zzb.zza(var2, var15, PaymentMethodToken.CREATOR);
            break;
         default:
            com.google.android.gms.common.internal.safeparcel.zzb.zzb(var2, var15);
         }
      }

      com.google.android.gms.common.internal.safeparcel.zzb.zzF(var2, var3);
      return new FullWallet(var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14);
   }
}
