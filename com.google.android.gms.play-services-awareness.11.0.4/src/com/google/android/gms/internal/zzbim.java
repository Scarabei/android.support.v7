package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.awareness.AwarenessOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzq;

final class zzbim extends zza {
   // $FF: synthetic method
   public final zze zza(Context var1, Looper var2, zzq var3, Object var4, ConnectionCallbacks var5, OnConnectionFailedListener var6) {
      AwarenessOptions var10 = (AwarenessOptions)var4;
      return new zzbka(var1, var2, var3, var10, var5, var6);
   }
}
