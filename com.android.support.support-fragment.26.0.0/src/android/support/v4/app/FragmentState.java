package android.support.v4.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;

final class FragmentState implements Parcelable {
   final String mClassName;
   final int mIndex;
   final boolean mFromLayout;
   final int mFragmentId;
   final int mContainerId;
   final String mTag;
   final boolean mRetainInstance;
   final boolean mDetached;
   final Bundle mArguments;
   final boolean mHidden;
   Bundle mSavedFragmentState;
   Fragment mInstance;
   public static final Creator CREATOR = new Creator() {
      public FragmentState createFromParcel(Parcel in) {
         return new FragmentState(in);
      }

      public FragmentState[] newArray(int size) {
         return new FragmentState[size];
      }
   };

   public FragmentState(Fragment frag) {
      this.mClassName = frag.getClass().getName();
      this.mIndex = frag.mIndex;
      this.mFromLayout = frag.mFromLayout;
      this.mFragmentId = frag.mFragmentId;
      this.mContainerId = frag.mContainerId;
      this.mTag = frag.mTag;
      this.mRetainInstance = frag.mRetainInstance;
      this.mDetached = frag.mDetached;
      this.mArguments = frag.mArguments;
      this.mHidden = frag.mHidden;
   }

   public FragmentState(Parcel in) {
      this.mClassName = in.readString();
      this.mIndex = in.readInt();
      this.mFromLayout = in.readInt() != 0;
      this.mFragmentId = in.readInt();
      this.mContainerId = in.readInt();
      this.mTag = in.readString();
      this.mRetainInstance = in.readInt() != 0;
      this.mDetached = in.readInt() != 0;
      this.mArguments = in.readBundle();
      this.mHidden = in.readInt() != 0;
      this.mSavedFragmentState = in.readBundle();
   }

   public Fragment instantiate(FragmentHostCallback host, FragmentContainer container, Fragment parent, FragmentManagerNonConfig childNonConfig) {
      if (this.mInstance == null) {
         Context context = host.getContext();
         if (this.mArguments != null) {
            this.mArguments.setClassLoader(context.getClassLoader());
         }

         if (container != null) {
            this.mInstance = container.instantiate(context, this.mClassName, this.mArguments);
         } else {
            this.mInstance = Fragment.instantiate(context, this.mClassName, this.mArguments);
         }

         if (this.mSavedFragmentState != null) {
            this.mSavedFragmentState.setClassLoader(context.getClassLoader());
            this.mInstance.mSavedFragmentState = this.mSavedFragmentState;
         }

         this.mInstance.setIndex(this.mIndex, parent);
         this.mInstance.mFromLayout = this.mFromLayout;
         this.mInstance.mRestored = true;
         this.mInstance.mFragmentId = this.mFragmentId;
         this.mInstance.mContainerId = this.mContainerId;
         this.mInstance.mTag = this.mTag;
         this.mInstance.mRetainInstance = this.mRetainInstance;
         this.mInstance.mDetached = this.mDetached;
         this.mInstance.mHidden = this.mHidden;
         this.mInstance.mFragmentManager = host.mFragmentManager;
         if (FragmentManagerImpl.DEBUG) {
            Log.v("FragmentManager", "Instantiated fragment " + this.mInstance);
         }
      }

      this.mInstance.mChildNonConfig = childNonConfig;
      return this.mInstance;
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.mClassName);
      dest.writeInt(this.mIndex);
      dest.writeInt(this.mFromLayout ? 1 : 0);
      dest.writeInt(this.mFragmentId);
      dest.writeInt(this.mContainerId);
      dest.writeString(this.mTag);
      dest.writeInt(this.mRetainInstance ? 1 : 0);
      dest.writeInt(this.mDetached ? 1 : 0);
      dest.writeBundle(this.mArguments);
      dest.writeInt(this.mHidden ? 1 : 0);
      dest.writeBundle(this.mSavedFragmentState);
   }
}