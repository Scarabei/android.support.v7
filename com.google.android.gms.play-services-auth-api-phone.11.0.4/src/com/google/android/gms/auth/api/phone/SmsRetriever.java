package com.google.android.gms.auth.api.phone;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.internal.zzasi;

public final class SmsRetriever {
   public static final String SMS_RETRIEVED_ACTION = "com.google.android.gms.auth.api.phone.SMS_RETRIEVED";
   public static final String EXTRA_SMS_MESSAGE = "com.google.android.gms.auth.api.phone.EXTRA_SMS_MESSAGE";
   public static final String EXTRA_STATUS = "com.google.android.gms.auth.api.phone.EXTRA_STATUS";

   public static SmsRetrieverClient getClient(@NonNull Context var0) {
      return new zzasi(var0);
   }

   public static SmsRetrieverClient getClient(@NonNull Activity var0) {
      return new zzasi(var0);
   }
}
