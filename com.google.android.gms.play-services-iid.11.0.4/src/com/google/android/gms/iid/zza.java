package com.google.android.gms.iid;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public final class zza {
   public static KeyPair zzvJ() {
      try {
         KeyPairGenerator var0;
         (var0 = KeyPairGenerator.getInstance("RSA")).initialize(2048);
         return var0.generateKeyPair();
      } catch (NoSuchAlgorithmException var1) {
         throw new AssertionError(var1);
      }
   }
}
