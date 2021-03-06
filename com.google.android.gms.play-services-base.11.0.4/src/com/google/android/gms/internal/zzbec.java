package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.TransformedResult;
import java.util.concurrent.TimeUnit;

public final class zzbec extends OptionalPendingResult {
   private final zzbbe zzaEQ;

   public zzbec(PendingResult var1) {
      if (!(var1 instanceof zzbbe)) {
         throw new IllegalArgumentException("OptionalPendingResult can only wrap PendingResults generated by an API call.");
      } else {
         this.zzaEQ = (zzbbe)var1;
      }
   }

   public final boolean isDone() {
      return this.zzaEQ.isReady();
   }

   public final Result get() {
      if (this.isDone()) {
         return this.await(0L, TimeUnit.MILLISECONDS);
      } else {
         throw new IllegalStateException("Result is not available. Check that isDone() returns true before calling get().");
      }
   }

   public final Result await() {
      return this.zzaEQ.await();
   }

   public final Result await(long var1, TimeUnit var3) {
      return this.zzaEQ.await(var1, var3);
   }

   public final void cancel() {
      this.zzaEQ.cancel();
   }

   public final boolean isCanceled() {
      return this.zzaEQ.isCanceled();
   }

   public final void setResultCallback(ResultCallback var1) {
      this.zzaEQ.setResultCallback(var1);
   }

   public final void setResultCallback(ResultCallback var1, long var2, TimeUnit var4) {
      this.zzaEQ.setResultCallback(var1, var2, var4);
   }

   public final void zza(PendingResult.zza var1) {
      this.zzaEQ.zza(var1);
   }

   @NonNull
   public final TransformedResult then(@NonNull ResultTransform var1) {
      return this.zzaEQ.then(var1);
   }

   public final Integer zzpo() {
      return this.zzaEQ.zzpo();
   }
}
