package com.google.android.gms.vision.face.internal.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zzb implements Creator {
   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new FaceParcel[var1];
   }

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      Parcel var2 = var1;
      int var3 = com.google.android.gms.common.internal.safeparcel.zzb.zzd(var1);
      int var4 = 0;
      int var5 = 0;
      float var6 = 0.0F;
      float var7 = 0.0F;
      float var8 = 0.0F;
      float var9 = 0.0F;
      float var10 = 0.0F;
      float var11 = 0.0F;
      LandmarkParcel[] var12 = null;
      float var13 = 0.0F;
      float var14 = 0.0F;
      float var15 = 0.0F;

      while(var2.dataPosition() < var3) {
         int var16;
         switch((var16 = var2.readInt()) & 65535) {
         case 1:
            var4 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(var2, var16);
            break;
         case 2:
            var5 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(var2, var16);
            break;
         case 3:
            var6 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(var2, var16);
            break;
         case 4:
            var7 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(var2, var16);
            break;
         case 5:
            var8 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(var2, var16);
            break;
         case 6:
            var9 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(var2, var16);
            break;
         case 7:
            var10 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(var2, var16);
            break;
         case 8:
            var11 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(var2, var16);
            break;
         case 9:
            var12 = (LandmarkParcel[])com.google.android.gms.common.internal.safeparcel.zzb.zzb(var2, var16, LandmarkParcel.CREATOR);
            break;
         case 10:
            var13 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(var2, var16);
            break;
         case 11:
            var14 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(var2, var16);
            break;
         case 12:
            var15 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(var2, var16);
            break;
         default:
            com.google.android.gms.common.internal.safeparcel.zzb.zzb(var2, var16);
         }
      }

      com.google.android.gms.common.internal.safeparcel.zzb.zzF(var2, var3);
      return new FaceParcel(var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15);
   }
}
