package com.google.android.gms.ads.internal;

import android.os.Build.VERSION;
import com.google.android.gms.internal.zzacd;
import com.google.android.gms.internal.zzaew;
import com.google.android.gms.internal.zzafk;
import com.google.android.gms.internal.zzagz;
import com.google.android.gms.internal.zzahe;
import com.google.android.gms.internal.zzahj;
import com.google.android.gms.internal.zzahk;
import com.google.android.gms.internal.zzahl;
import com.google.android.gms.internal.zzahm;
import com.google.android.gms.internal.zzahn;
import com.google.android.gms.internal.zzaho;
import com.google.android.gms.internal.zzahw;
import com.google.android.gms.internal.zzaio;
import com.google.android.gms.internal.zzaip;
import com.google.android.gms.internal.zzajo;
import com.google.android.gms.internal.zzajv;
import com.google.android.gms.internal.zzakk;
import com.google.android.gms.internal.zzgv;
import com.google.android.gms.internal.zzhr;
import com.google.android.gms.internal.zzhs;
import com.google.android.gms.internal.zzmk;
import com.google.android.gms.internal.zzml;
import com.google.android.gms.internal.zzmm;
import com.google.android.gms.internal.zzmt;
import com.google.android.gms.internal.zzsa;
import com.google.android.gms.internal.zztl;
import com.google.android.gms.internal.zztt;
import com.google.android.gms.internal.zzuj;
import com.google.android.gms.internal.zzxx;
import com.google.android.gms.internal.zzzn;
import com.google.android.gms.internal.zzzo;

@zzzn
public final class zzbs {
   private static final Object zzuF = new Object();
   private static zzbs zzvk;
   private final com.google.android.gms.ads.internal.overlay.zza zzvl = new com.google.android.gms.ads.internal.overlay.zza();
   private final zzzo zzvm = new zzzo();
   private final com.google.android.gms.ads.internal.overlay.zzu zzvn = new com.google.android.gms.ads.internal.overlay.zzu();
   private final zzxx zzvo = new zzxx();
   private final zzagz zzvp = new zzagz();
   private final zzakk zzvq = new zzakk();
   private final zzahe zzvr;
   private final zzgv zzvs;
   private final zzafk zzvt;
   private final zzhr zzvu;
   private final zzhs zzvv;
   private final com.google.android.gms.common.util.zze zzvw;
   private final zzac zzvx;
   private final zzmt zzvy;
   private final zzahw zzvz;
   private final zzacd zzvA;
   private final zzmk zzvB;
   private final zzml zzvC;
   private final zzmm zzvD;
   private final zzajo zzvE;
   private final zztl zzvF;
   private final zztt zzvG;
   private final zzaio zzvH;
   private final com.google.android.gms.ads.internal.overlay.zzah zzvI;
   private final com.google.android.gms.ads.internal.overlay.zzai zzvJ;
   private final zzuj zzvK;
   private final zzaip zzvL;
   private final zzba zzvM;
   private final zzsa zzvN;
   private final zzajv zzvO;
   private final zzaew zzvP;

   protected zzbs() {
      int var1 = VERSION.SDK_INT;
      this.zzvr = (zzahe)(VERSION.SDK_INT >= 21 ? new zzaho() : (var1 >= 19 ? new zzahn() : (var1 >= 18 ? new zzahl() : (var1 >= 17 ? new zzahk() : (var1 >= 16 ? new zzahm() : new zzahj())))));
      this.zzvs = new zzgv();
      this.zzvt = new zzafk(this.zzvp);
      this.zzvu = new zzhr();
      this.zzvv = new zzhs();
      this.zzvw = com.google.android.gms.common.util.zzi.zzrY();
      this.zzvx = new zzac();
      this.zzvy = new zzmt();
      this.zzvz = new zzahw();
      this.zzvA = new zzacd();
      this.zzvB = new zzmk();
      this.zzvC = new zzml();
      this.zzvD = new zzmm();
      this.zzvE = new zzajo();
      this.zzvF = new zztl();
      this.zzvG = new zztt();
      this.zzvH = new zzaio();
      this.zzvI = new com.google.android.gms.ads.internal.overlay.zzah();
      this.zzvJ = new com.google.android.gms.ads.internal.overlay.zzai();
      this.zzvK = new zzuj();
      this.zzvL = new zzaip();
      this.zzvM = new zzba();
      this.zzvN = new zzsa();
      this.zzvO = new zzajv();
      this.zzvP = new zzaew();
   }

   private static zzbs zzbu() {
      Object var0 = zzuF;
      synchronized(zzuF) {
         return zzvk;
      }
   }

   public static zzzo zzbv() {
      return zzbu().zzvm;
   }

   public static com.google.android.gms.ads.internal.overlay.zza zzbw() {
      return zzbu().zzvl;
   }

   public static com.google.android.gms.ads.internal.overlay.zzu zzbx() {
      return zzbu().zzvn;
   }

   public static zzxx zzby() {
      return zzbu().zzvo;
   }

   public static zzagz zzbz() {
      return zzbu().zzvp;
   }

   public static zzakk zzbA() {
      return zzbu().zzvq;
   }

   public static zzahe zzbB() {
      return zzbu().zzvr;
   }

   public static zzgv zzbC() {
      return zzbu().zzvs;
   }

   public static zzafk zzbD() {
      return zzbu().zzvt;
   }

   public static zzhs zzbE() {
      return zzbu().zzvv;
   }

   public static com.google.android.gms.common.util.zze zzbF() {
      return zzbu().zzvw;
   }

   public static zzmt zzbG() {
      return zzbu().zzvy;
   }

   public static zzahw zzbH() {
      return zzbu().zzvz;
   }

   public static zzacd zzbI() {
      return zzbu().zzvA;
   }

   public static zzml zzbJ() {
      return zzbu().zzvC;
   }

   public static zzmk zzbK() {
      return zzbu().zzvB;
   }

   public static zzmm zzbL() {
      return zzbu().zzvD;
   }

   public static zzajo zzbM() {
      return zzbu().zzvE;
   }

   public static zztl zzbN() {
      return zzbu().zzvF;
   }

   public static zztt zzbO() {
      return zzbu().zzvG;
   }

   public static zzaio zzbP() {
      return zzbu().zzvH;
   }

   public static com.google.android.gms.ads.internal.overlay.zzah zzbQ() {
      return zzbu().zzvI;
   }

   public static com.google.android.gms.ads.internal.overlay.zzai zzbR() {
      return zzbu().zzvJ;
   }

   public static zzuj zzbS() {
      return zzbu().zzvK;
   }

   public static zzba zzbT() {
      return zzbu().zzvM;
   }

   public static zzaip zzbU() {
      return zzbu().zzvL;
   }

   public static zzac zzbV() {
      return zzbu().zzvx;
   }

   public static zzsa zzbW() {
      return zzbu().zzvN;
   }

   public static zzajv zzbX() {
      return zzbu().zzvO;
   }

   public static zzaew zzbY() {
      return zzbu().zzvP;
   }

   static {
      zzbs var0 = new zzbs();
      Object var1 = zzuF;
      synchronized(zzuF) {
         zzvk = var0;
      }
   }
}
