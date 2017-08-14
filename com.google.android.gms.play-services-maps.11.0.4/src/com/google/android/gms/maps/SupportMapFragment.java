package com.google.android.gms.maps;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.internal.MapLifecycleDelegate;
import com.google.android.gms.maps.internal.zzbw;
import com.google.android.gms.maps.internal.zzbx;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SupportMapFragment extends Fragment {
   private final SupportMapFragment.zzb zzbmW = new SupportMapFragment.zzb(this);

   public static SupportMapFragment newInstance() {
      return new SupportMapFragment();
   }

   public static SupportMapFragment newInstance(GoogleMapOptions var0) {
      SupportMapFragment var1 = new SupportMapFragment();
      Bundle var2;
      (var2 = new Bundle()).putParcelable("MapOptions", var0);
      var1.setArguments(var2);
      return var1;
   }

   public void onAttach(Activity var1) {
      super.onAttach(var1);
      this.zzbmW.setActivity(var1);
   }

   public void onInflate(Activity var1, AttributeSet var2, Bundle var3) {
      super.onInflate(var1, var2, var3);
      this.zzbmW.setActivity(var1);
      GoogleMapOptions var4 = GoogleMapOptions.createFromAttributes(var1, var2);
      Bundle var5;
      (var5 = new Bundle()).putParcelable("MapOptions", var4);
      this.zzbmW.onInflate(var1, var5, var3);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.zzbmW.onCreate(var1);
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      View var4;
      (var4 = this.zzbmW.onCreateView(var1, var2, var3)).setClickable(true);
      return var4;
   }

   public void onResume() {
      super.onResume();
      this.zzbmW.onResume();
   }

   public void onPause() {
      this.zzbmW.onPause();
      super.onPause();
   }

   public void onStart() {
      super.onStart();
      this.zzbmW.onStart();
   }

   public void onStop() {
      this.zzbmW.onStop();
      super.onStop();
   }

   public void onDestroyView() {
      this.zzbmW.onDestroyView();
      super.onDestroyView();
   }

   public void onDestroy() {
      this.zzbmW.onDestroy();
      super.onDestroy();
   }

   public void onLowMemory() {
      this.zzbmW.onLowMemory();
      super.onLowMemory();
   }

   public void onActivityCreated(Bundle var1) {
      if (var1 != null) {
         var1.setClassLoader(SupportMapFragment.class.getClassLoader());
      }

      super.onActivityCreated(var1);
   }

   public void onSaveInstanceState(Bundle var1) {
      if (var1 != null) {
         var1.setClassLoader(SupportMapFragment.class.getClassLoader());
      }

      super.onSaveInstanceState(var1);
      this.zzbmW.onSaveInstanceState(var1);
   }

   public final void onEnterAmbient(Bundle var1) {
      zzbo.zzcz("onEnterAmbient must be called on the main thread.");
      SupportMapFragment.zzb var2 = this.zzbmW;
      if (this.zzbmW.zztx() != null) {
         ((SupportMapFragment.zza)var2.zztx()).onEnterAmbient(var1);
      }

   }

   public final void onExitAmbient() {
      zzbo.zzcz("onExitAmbient must be called on the main thread.");
      SupportMapFragment.zzb var1 = this.zzbmW;
      if (this.zzbmW.zztx() != null) {
         ((SupportMapFragment.zza)var1.zztx()).onExitAmbient();
      }

   }

   public void getMapAsync(OnMapReadyCallback var1) {
      zzbo.zzcz("getMapAsync must be called on the main thread.");
      this.zzbmW.getMapAsync(var1);
   }

   public void setArguments(Bundle var1) {
      super.setArguments(var1);
   }

   static class zzb extends com.google.android.gms.dynamic.zza {
      private final Fragment zzaSE;
      private com.google.android.gms.dynamic.zzo zzbms;
      private Activity mActivity;
      private final List zzbmt = new ArrayList();

      zzb(Fragment var1) {
         this.zzaSE = var1;
      }

      protected final void zza(com.google.android.gms.dynamic.zzo var1) {
         this.zzbms = var1;
         this.zzwg();
      }

      private final void zzwg() {
         if (this.mActivity != null && this.zzbms != null && this.zztx() == null) {
            try {
               MapsInitializer.initialize(this.mActivity);
               IMapFragmentDelegate var1;
               if ((var1 = zzbx.zzbh(this.mActivity).zzH(com.google.android.gms.dynamic.zzn.zzw(this.mActivity))) == null) {
                  return;
               }

               this.zzbms.zza(new SupportMapFragment.zza(this.zzaSE, var1));
               Iterator var2 = this.zzbmt.iterator();

               while(var2.hasNext()) {
                  OnMapReadyCallback var3 = (OnMapReadyCallback)var2.next();
                  ((SupportMapFragment.zza)this.zztx()).getMapAsync(var3);
               }

               this.zzbmt.clear();
               return;
            } catch (RemoteException var4) {
               throw new RuntimeRemoteException(var4);
            } catch (GooglePlayServicesNotAvailableException var5) {
               ;
            }
         }

      }

      private final void setActivity(Activity var1) {
         this.mActivity = var1;
         this.zzwg();
      }

      public final void getMapAsync(OnMapReadyCallback var1) {
         if (this.zztx() != null) {
            ((SupportMapFragment.zza)this.zztx()).getMapAsync(var1);
         } else {
            this.zzbmt.add(var1);
         }
      }
   }

   static class zza implements MapLifecycleDelegate {
      private final Fragment zzaSE;
      private final IMapFragmentDelegate zzbmq;

      public zza(Fragment var1, IMapFragmentDelegate var2) {
         this.zzbmq = (IMapFragmentDelegate)zzbo.zzu(var2);
         this.zzaSE = (Fragment)zzbo.zzu(var1);
      }

      public final void onInflate(Activity var1, Bundle var2, Bundle var3) {
         GoogleMapOptions var4 = (GoogleMapOptions)var2.getParcelable("MapOptions");

         try {
            Bundle var5 = new Bundle();
            zzbw.zzd(var3, var5);
            this.zzbmq.onInflate(com.google.android.gms.dynamic.zzn.zzw(var1), var4, var5);
            zzbw.zzd(var5, var3);
         } catch (RemoteException var6) {
            throw new RuntimeRemoteException(var6);
         }
      }

      public final void onCreate(Bundle var1) {
         try {
            Bundle var2 = new Bundle();
            zzbw.zzd(var1, var2);
            Bundle var3;
            if ((var3 = this.zzaSE.getArguments()) != null && var3.containsKey("MapOptions")) {
               zzbw.zza(var2, "MapOptions", var3.getParcelable("MapOptions"));
            }

            this.zzbmq.onCreate(var2);
            zzbw.zzd(var2, var1);
         } catch (RemoteException var4) {
            throw new RuntimeRemoteException(var4);
         }
      }

      public final View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
         IObjectWrapper var4;
         try {
            Bundle var5 = new Bundle();
            zzbw.zzd(var3, var5);
            var4 = this.zzbmq.onCreateView(com.google.android.gms.dynamic.zzn.zzw(var1), com.google.android.gms.dynamic.zzn.zzw(var2), var5);
            zzbw.zzd(var5, var3);
         } catch (RemoteException var6) {
            throw new RuntimeRemoteException(var6);
         }

         return (View)com.google.android.gms.dynamic.zzn.zzE(var4);
      }

      public final void onStart() {
         try {
            this.zzbmq.onStart();
         } catch (RemoteException var2) {
            throw new RuntimeRemoteException(var2);
         }
      }

      public final void onResume() {
         try {
            this.zzbmq.onResume();
         } catch (RemoteException var2) {
            throw new RuntimeRemoteException(var2);
         }
      }

      public final void onPause() {
         try {
            this.zzbmq.onPause();
         } catch (RemoteException var2) {
            throw new RuntimeRemoteException(var2);
         }
      }

      public final void onStop() {
         try {
            this.zzbmq.onStop();
         } catch (RemoteException var2) {
            throw new RuntimeRemoteException(var2);
         }
      }

      public final void onDestroyView() {
         try {
            this.zzbmq.onDestroyView();
         } catch (RemoteException var2) {
            throw new RuntimeRemoteException(var2);
         }
      }

      public final void onDestroy() {
         try {
            this.zzbmq.onDestroy();
         } catch (RemoteException var2) {
            throw new RuntimeRemoteException(var2);
         }
      }

      public final void onLowMemory() {
         try {
            this.zzbmq.onLowMemory();
         } catch (RemoteException var2) {
            throw new RuntimeRemoteException(var2);
         }
      }

      public final void onSaveInstanceState(Bundle var1) {
         try {
            Bundle var2 = new Bundle();
            zzbw.zzd(var1, var2);
            this.zzbmq.onSaveInstanceState(var2);
            zzbw.zzd(var2, var1);
         } catch (RemoteException var3) {
            throw new RuntimeRemoteException(var3);
         }
      }

      public final void getMapAsync(OnMapReadyCallback var1) {
         try {
            this.zzbmq.getMapAsync(new zzaj(this, var1));
         } catch (RemoteException var3) {
            throw new RuntimeRemoteException(var3);
         }
      }

      public final void onEnterAmbient(Bundle var1) {
         try {
            Bundle var2 = new Bundle();
            zzbw.zzd(var1, var2);
            this.zzbmq.onEnterAmbient(var2);
            zzbw.zzd(var2, var1);
         } catch (RemoteException var3) {
            throw new RuntimeRemoteException(var3);
         }
      }

      public final void onExitAmbient() {
         try {
            this.zzbmq.onExitAmbient();
         } catch (RemoteException var2) {
            throw new RuntimeRemoteException(var2);
         }
      }
   }
}
