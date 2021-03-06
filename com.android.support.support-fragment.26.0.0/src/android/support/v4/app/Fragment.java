package android.support.v4.app;

import android.animation.Animator;
import android.app.Activity;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.animation.Animation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

public class Fragment implements ComponentCallbacks, OnCreateContextMenuListener {
   private static final SimpleArrayMap sClassMap = new SimpleArrayMap();
   static final Object USE_DEFAULT_TRANSITION = new Object();
   static final int INITIALIZING = 0;
   static final int CREATED = 1;
   static final int ACTIVITY_CREATED = 2;
   static final int STOPPED = 3;
   static final int STARTED = 4;
   static final int RESUMED = 5;
   int mState = 0;
   Bundle mSavedFragmentState;
   SparseArray mSavedViewState;
   int mIndex = -1;
   String mWho;
   Bundle mArguments;
   Fragment mTarget;
   int mTargetIndex = -1;
   int mTargetRequestCode;
   boolean mAdded;
   boolean mRemoving;
   boolean mFromLayout;
   boolean mInLayout;
   boolean mRestored;
   boolean mPerformedCreateView;
   int mBackStackNesting;
   FragmentManagerImpl mFragmentManager;
   FragmentHostCallback mHost;
   FragmentManagerImpl mChildFragmentManager;
   FragmentManagerNonConfig mChildNonConfig;
   Fragment mParentFragment;
   int mFragmentId;
   int mContainerId;
   String mTag;
   boolean mHidden;
   boolean mDetached;
   boolean mRetainInstance;
   boolean mRetaining;
   boolean mHasMenu;
   boolean mMenuVisible = true;
   boolean mCalled;
   ViewGroup mContainer;
   View mView;
   View mInnerView;
   boolean mDeferStart;
   boolean mUserVisibleHint = true;
   LoaderManagerImpl mLoaderManager;
   boolean mLoadersStarted;
   boolean mCheckedForLoaderManager;
   Fragment.AnimationInfo mAnimationInfo;
   boolean mIsNewlyAdded;
   boolean mHiddenChanged;
   float mPostponedAlpha;
   LayoutInflater mLayoutInflater;

   public static Fragment instantiate(Context context, String fname) {
      return instantiate(context, fname, (Bundle)null);
   }

   public static Fragment instantiate(Context context, String fname, @Nullable Bundle args) {
      try {
         Class clazz = (Class)sClassMap.get(fname);
         if (clazz == null) {
            clazz = context.getClassLoader().loadClass(fname);
            sClassMap.put(fname, clazz);
         }

         Fragment f = (Fragment)clazz.getConstructor().newInstance();
         if (args != null) {
            args.setClassLoader(f.getClass().getClassLoader());
            f.setArguments(args);
         }

         return f;
      } catch (ClassNotFoundException var5) {
         throw new Fragment.InstantiationException("Unable to instantiate fragment " + fname + ": make sure class name exists, is public, and has an" + " empty constructor that is public", var5);
      } catch (java.lang.InstantiationException var6) {
         throw new Fragment.InstantiationException("Unable to instantiate fragment " + fname + ": make sure class name exists, is public, and has an" + " empty constructor that is public", var6);
      } catch (IllegalAccessException var7) {
         throw new Fragment.InstantiationException("Unable to instantiate fragment " + fname + ": make sure class name exists, is public, and has an" + " empty constructor that is public", var7);
      } catch (NoSuchMethodException var8) {
         throw new Fragment.InstantiationException("Unable to instantiate fragment " + fname + ": could not find Fragment constructor", var8);
      } catch (InvocationTargetException var9) {
         throw new Fragment.InstantiationException("Unable to instantiate fragment " + fname + ": calling Fragment constructor caused an exception", var9);
      }
   }

   static boolean isSupportFragmentClass(Context context, String fname) {
      try {
         Class clazz = (Class)sClassMap.get(fname);
         if (clazz == null) {
            clazz = context.getClassLoader().loadClass(fname);
            sClassMap.put(fname, clazz);
         }

         return Fragment.class.isAssignableFrom(clazz);
      } catch (ClassNotFoundException var3) {
         return false;
      }
   }

   final void restoreViewState(Bundle savedInstanceState) {
      if (this.mSavedViewState != null) {
         this.mInnerView.restoreHierarchyState(this.mSavedViewState);
         this.mSavedViewState = null;
      }

      this.mCalled = false;
      this.onViewStateRestored(savedInstanceState);
      if (!this.mCalled) {
         throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onViewStateRestored()");
      }
   }

   final void setIndex(int index, Fragment parent) {
      this.mIndex = index;
      if (parent != null) {
         this.mWho = parent.mWho + ":" + this.mIndex;
      } else {
         this.mWho = "android:fragment:" + this.mIndex;
      }

   }

   final boolean isInBackStack() {
      return this.mBackStackNesting > 0;
   }

   public final boolean equals(Object o) {
      return super.equals(o);
   }

   public final int hashCode() {
      return super.hashCode();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(128);
      DebugUtils.buildShortClassTag(this, sb);
      if (this.mIndex >= 0) {
         sb.append(" #");
         sb.append(this.mIndex);
      }

      if (this.mFragmentId != 0) {
         sb.append(" id=0x");
         sb.append(Integer.toHexString(this.mFragmentId));
      }

      if (this.mTag != null) {
         sb.append(" ");
         sb.append(this.mTag);
      }

      sb.append('}');
      return sb.toString();
   }

   public final int getId() {
      return this.mFragmentId;
   }

   public final String getTag() {
      return this.mTag;
   }

   public void setArguments(Bundle args) {
      if (this.mIndex >= 0 && this.isStateSaved()) {
         throw new IllegalStateException("Fragment already active and state has been saved");
      } else {
         this.mArguments = args;
      }
   }

   public final Bundle getArguments() {
      return this.mArguments;
   }

   public final boolean isStateSaved() {
      return this.mFragmentManager == null ? false : this.mFragmentManager.isStateSaved();
   }

   public void setInitialSavedState(Fragment.SavedState state) {
      if (this.mIndex >= 0) {
         throw new IllegalStateException("Fragment already active");
      } else {
         this.mSavedFragmentState = state != null && state.mState != null ? state.mState : null;
      }
   }

   public void setTargetFragment(Fragment fragment, int requestCode) {
      FragmentManager mine = this.getFragmentManager();
      FragmentManager theirs = fragment != null ? fragment.getFragmentManager() : null;
      if (mine != null && theirs != null && mine != theirs) {
         throw new IllegalArgumentException("Fragment " + fragment + " must share the same FragmentManager to be set as a target fragment");
      } else {
         for(Fragment check = fragment; check != null; check = check.getTargetFragment()) {
            if (check == this) {
               throw new IllegalArgumentException("Setting " + fragment + " as the target of " + this + " would create a target cycle");
            }
         }

         this.mTarget = fragment;
         this.mTargetRequestCode = requestCode;
      }
   }

   public final Fragment getTargetFragment() {
      return this.mTarget;
   }

   public final int getTargetRequestCode() {
      return this.mTargetRequestCode;
   }

   public Context getContext() {
      return this.mHost == null ? null : this.mHost.getContext();
   }

   public final FragmentActivity getActivity() {
      return this.mHost == null ? null : (FragmentActivity)this.mHost.getActivity();
   }

   public final Object getHost() {
      return this.mHost == null ? null : this.mHost.onGetHost();
   }

   public final Resources getResources() {
      if (this.mHost == null) {
         throw new IllegalStateException("Fragment " + this + " not attached to Activity");
      } else {
         return this.mHost.getContext().getResources();
      }
   }

   public final CharSequence getText(@StringRes int resId) {
      return this.getResources().getText(resId);
   }

   public final String getString(@StringRes int resId) {
      return this.getResources().getString(resId);
   }

   public final String getString(@StringRes int resId, Object... formatArgs) {
      return this.getResources().getString(resId, formatArgs);
   }

   public final FragmentManager getFragmentManager() {
      return this.mFragmentManager;
   }

   public final FragmentManager getChildFragmentManager() {
      if (this.mChildFragmentManager == null) {
         this.instantiateChildFragmentManager();
         if (this.mState >= 5) {
            this.mChildFragmentManager.dispatchResume();
         } else if (this.mState >= 4) {
            this.mChildFragmentManager.dispatchStart();
         } else if (this.mState >= 2) {
            this.mChildFragmentManager.dispatchActivityCreated();
         } else if (this.mState >= 1) {
            this.mChildFragmentManager.dispatchCreate();
         }
      }

      return this.mChildFragmentManager;
   }

   FragmentManager peekChildFragmentManager() {
      return this.mChildFragmentManager;
   }

   public final Fragment getParentFragment() {
      return this.mParentFragment;
   }

   public final boolean isAdded() {
      return this.mHost != null && this.mAdded;
   }

   public final boolean isDetached() {
      return this.mDetached;
   }

   public final boolean isRemoving() {
      return this.mRemoving;
   }

   public final boolean isInLayout() {
      return this.mInLayout;
   }

   public final boolean isResumed() {
      return this.mState >= 5;
   }

   public final boolean isVisible() {
      return this.isAdded() && !this.isHidden() && this.mView != null && this.mView.getWindowToken() != null && this.mView.getVisibility() == 0;
   }

   public final boolean isHidden() {
      return this.mHidden;
   }

   @RestrictTo({Scope.LIBRARY_GROUP})
   public final boolean hasOptionsMenu() {
      return this.mHasMenu;
   }

   @RestrictTo({Scope.LIBRARY_GROUP})
   public final boolean isMenuVisible() {
      return this.mMenuVisible;
   }

   public void onHiddenChanged(boolean hidden) {
   }

   public void setRetainInstance(boolean retain) {
      this.mRetainInstance = retain;
   }

   public final boolean getRetainInstance() {
      return this.mRetainInstance;
   }

   public void setHasOptionsMenu(boolean hasMenu) {
      if (this.mHasMenu != hasMenu) {
         this.mHasMenu = hasMenu;
         if (this.isAdded() && !this.isHidden()) {
            this.mHost.onSupportInvalidateOptionsMenu();
         }
      }

   }

   public void setMenuVisibility(boolean menuVisible) {
      if (this.mMenuVisible != menuVisible) {
         this.mMenuVisible = menuVisible;
         if (this.mHasMenu && this.isAdded() && !this.isHidden()) {
            this.mHost.onSupportInvalidateOptionsMenu();
         }
      }

   }

   public void setUserVisibleHint(boolean isVisibleToUser) {
      if (!this.mUserVisibleHint && isVisibleToUser && this.mState < 4 && this.mFragmentManager != null && this.isAdded()) {
         this.mFragmentManager.performPendingDeferredStart(this);
      }

      this.mUserVisibleHint = isVisibleToUser;
      this.mDeferStart = this.mState < 4 && !isVisibleToUser;
   }

   public boolean getUserVisibleHint() {
      return this.mUserVisibleHint;
   }

   public LoaderManager getLoaderManager() {
      if (this.mLoaderManager != null) {
         return this.mLoaderManager;
      } else if (this.mHost == null) {
         throw new IllegalStateException("Fragment " + this + " not attached to Activity");
      } else {
         this.mCheckedForLoaderManager = true;
         this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, true);
         return this.mLoaderManager;
      }
   }

   public void startActivity(Intent intent) {
      this.startActivity(intent, (Bundle)null);
   }

   public void startActivity(Intent intent, @Nullable Bundle options) {
      if (this.mHost == null) {
         throw new IllegalStateException("Fragment " + this + " not attached to Activity");
      } else {
         this.mHost.onStartActivityFromFragment(this, intent, -1, options);
      }
   }

   public void startActivityForResult(Intent intent, int requestCode) {
      this.startActivityForResult(intent, requestCode, (Bundle)null);
   }

   public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
      if (this.mHost == null) {
         throw new IllegalStateException("Fragment " + this + " not attached to Activity");
      } else {
         this.mHost.onStartActivityFromFragment(this, intent, requestCode, options);
      }
   }

   public void startIntentSenderForResult(IntentSender intent, int requestCode, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws SendIntentException {
      if (this.mHost == null) {
         throw new IllegalStateException("Fragment " + this + " not attached to Activity");
      } else {
         this.mHost.onStartIntentSenderFromFragment(this, intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
      }
   }

   public void onActivityResult(int requestCode, int resultCode, Intent data) {
   }

   public final void requestPermissions(@NonNull String[] permissions, int requestCode) {
      if (this.mHost == null) {
         throw new IllegalStateException("Fragment " + this + " not attached to Activity");
      } else {
         this.mHost.onRequestPermissionsFromFragment(this, permissions, requestCode);
      }
   }

   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
   }

   public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
      return this.mHost != null ? this.mHost.onShouldShowRequestPermissionRationale(permission) : false;
   }

   public LayoutInflater onGetLayoutInflater(Bundle savedInstanceState) {
      return this.getLayoutInflater(savedInstanceState);
   }

   public final LayoutInflater getLayoutInflater() {
      return this.mLayoutInflater == null ? this.performGetLayoutInflater((Bundle)null) : this.mLayoutInflater;
   }

   LayoutInflater performGetLayoutInflater(Bundle savedInstanceState) {
      LayoutInflater layoutInflater = this.onGetLayoutInflater(savedInstanceState);
      this.mLayoutInflater = layoutInflater;
      return this.mLayoutInflater;
   }

   /** @deprecated */
   @Deprecated
   @RestrictTo({Scope.LIBRARY_GROUP})
   public LayoutInflater getLayoutInflater(Bundle savedFragmentState) {
      if (this.mHost == null) {
         throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
      } else {
         LayoutInflater result = this.mHost.onGetLayoutInflater();
         this.getChildFragmentManager();
         LayoutInflaterCompat.setFactory2(result, this.mChildFragmentManager.getLayoutInflaterFactory());
         return result;
      }
   }

   @CallSuper
   public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
      this.mCalled = true;
      Activity hostActivity = this.mHost == null ? null : this.mHost.getActivity();
      if (hostActivity != null) {
         this.mCalled = false;
         this.onInflate(hostActivity, attrs, savedInstanceState);
      }

   }

   /** @deprecated */
   @Deprecated
   @CallSuper
   public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
      this.mCalled = true;
   }

   public void onAttachFragment(Fragment childFragment) {
   }

   @CallSuper
   public void onAttach(Context context) {
      this.mCalled = true;
      Activity hostActivity = this.mHost == null ? null : this.mHost.getActivity();
      if (hostActivity != null) {
         this.mCalled = false;
         this.onAttach(hostActivity);
      }

   }

   /** @deprecated */
   @Deprecated
   @CallSuper
   public void onAttach(Activity activity) {
      this.mCalled = true;
   }

   public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
      return null;
   }

   public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
      return null;
   }

   @CallSuper
   public void onCreate(@Nullable Bundle savedInstanceState) {
      this.mCalled = true;
      this.restoreChildFragmentState(savedInstanceState);
      if (this.mChildFragmentManager != null && !this.mChildFragmentManager.isStateAtLeast(1)) {
         this.mChildFragmentManager.dispatchCreate();
      }

   }

   void restoreChildFragmentState(@Nullable Bundle savedInstanceState) {
      if (savedInstanceState != null) {
         Parcelable p = savedInstanceState.getParcelable("android:support:fragments");
         if (p != null) {
            if (this.mChildFragmentManager == null) {
               this.instantiateChildFragmentManager();
            }

            this.mChildFragmentManager.restoreAllState(p, this.mChildNonConfig);
            this.mChildNonConfig = null;
            this.mChildFragmentManager.dispatchCreate();
         }
      }

   }

   @Nullable
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return null;
   }

   public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
   }

   @Nullable
   public View getView() {
      return this.mView;
   }

   @CallSuper
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      this.mCalled = true;
   }

   @CallSuper
   public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
      this.mCalled = true;
   }

   @CallSuper
   public void onStart() {
      this.mCalled = true;
      if (!this.mLoadersStarted) {
         this.mLoadersStarted = true;
         if (!this.mCheckedForLoaderManager) {
            this.mCheckedForLoaderManager = true;
            this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, false);
         } else if (this.mLoaderManager != null) {
            this.mLoaderManager.doStart();
         }
      }

   }

   @CallSuper
   public void onResume() {
      this.mCalled = true;
   }

   public void onSaveInstanceState(Bundle outState) {
   }

   public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
   }

   public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
   }

   @CallSuper
   public void onConfigurationChanged(Configuration newConfig) {
      this.mCalled = true;
   }

   @CallSuper
   public void onPause() {
      this.mCalled = true;
   }

   @CallSuper
   public void onStop() {
      this.mCalled = true;
   }

   @CallSuper
   public void onLowMemory() {
      this.mCalled = true;
   }

   @CallSuper
   public void onDestroyView() {
      this.mCalled = true;
   }

   @CallSuper
   public void onDestroy() {
      this.mCalled = true;
      if (!this.mCheckedForLoaderManager) {
         this.mCheckedForLoaderManager = true;
         this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, false);
      }

      if (this.mLoaderManager != null) {
         this.mLoaderManager.doDestroy();
      }

   }

   void initState() {
      this.mIndex = -1;
      this.mWho = null;
      this.mAdded = false;
      this.mRemoving = false;
      this.mFromLayout = false;
      this.mInLayout = false;
      this.mRestored = false;
      this.mBackStackNesting = 0;
      this.mFragmentManager = null;
      this.mChildFragmentManager = null;
      this.mHost = null;
      this.mFragmentId = 0;
      this.mContainerId = 0;
      this.mTag = null;
      this.mHidden = false;
      this.mDetached = false;
      this.mRetaining = false;
      this.mLoaderManager = null;
      this.mLoadersStarted = false;
      this.mCheckedForLoaderManager = false;
   }

   @CallSuper
   public void onDetach() {
      this.mCalled = true;
   }

   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
   }

   public void onPrepareOptionsMenu(Menu menu) {
   }

   public void onDestroyOptionsMenu() {
   }

   public boolean onOptionsItemSelected(MenuItem item) {
      return false;
   }

   public void onOptionsMenuClosed(Menu menu) {
   }

   public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
      this.getActivity().onCreateContextMenu(menu, v, menuInfo);
   }

   public void registerForContextMenu(View view) {
      view.setOnCreateContextMenuListener(this);
   }

   public void unregisterForContextMenu(View view) {
      view.setOnCreateContextMenuListener((OnCreateContextMenuListener)null);
   }

   public boolean onContextItemSelected(MenuItem item) {
      return false;
   }

   public void setEnterSharedElementCallback(SharedElementCallback callback) {
      this.ensureAnimationInfo().mEnterTransitionCallback = callback;
   }

   public void setExitSharedElementCallback(SharedElementCallback callback) {
      this.ensureAnimationInfo().mExitTransitionCallback = callback;
   }

   public void setEnterTransition(Object transition) {
      this.ensureAnimationInfo().mEnterTransition = transition;
   }

   public Object getEnterTransition() {
      return this.mAnimationInfo == null ? null : this.mAnimationInfo.mEnterTransition;
   }

   public void setReturnTransition(Object transition) {
      this.ensureAnimationInfo().mReturnTransition = transition;
   }

   public Object getReturnTransition() {
      if (this.mAnimationInfo == null) {
         return null;
      } else {
         return this.mAnimationInfo.mReturnTransition == USE_DEFAULT_TRANSITION ? this.getEnterTransition() : this.mAnimationInfo.mReturnTransition;
      }
   }

   public void setExitTransition(Object transition) {
      this.ensureAnimationInfo().mExitTransition = transition;
   }

   public Object getExitTransition() {
      return this.mAnimationInfo == null ? null : this.mAnimationInfo.mExitTransition;
   }

   public void setReenterTransition(Object transition) {
      this.ensureAnimationInfo().mReenterTransition = transition;
   }

   public Object getReenterTransition() {
      if (this.mAnimationInfo == null) {
         return null;
      } else {
         return this.mAnimationInfo.mReenterTransition == USE_DEFAULT_TRANSITION ? this.getExitTransition() : this.mAnimationInfo.mReenterTransition;
      }
   }

   public void setSharedElementEnterTransition(Object transition) {
      this.ensureAnimationInfo().mSharedElementEnterTransition = transition;
   }

   public Object getSharedElementEnterTransition() {
      return this.mAnimationInfo == null ? null : this.mAnimationInfo.mSharedElementEnterTransition;
   }

   public void setSharedElementReturnTransition(Object transition) {
      this.ensureAnimationInfo().mSharedElementReturnTransition = transition;
   }

   public Object getSharedElementReturnTransition() {
      if (this.mAnimationInfo == null) {
         return null;
      } else {
         return this.mAnimationInfo.mSharedElementReturnTransition == USE_DEFAULT_TRANSITION ? this.getSharedElementEnterTransition() : this.mAnimationInfo.mSharedElementReturnTransition;
      }
   }

   public void setAllowEnterTransitionOverlap(boolean allow) {
      this.ensureAnimationInfo().mAllowEnterTransitionOverlap = allow;
   }

   public boolean getAllowEnterTransitionOverlap() {
      return this.mAnimationInfo != null && this.mAnimationInfo.mAllowEnterTransitionOverlap != null ? this.mAnimationInfo.mAllowEnterTransitionOverlap.booleanValue() : true;
   }

   public void setAllowReturnTransitionOverlap(boolean allow) {
      this.ensureAnimationInfo().mAllowReturnTransitionOverlap = allow;
   }

   public boolean getAllowReturnTransitionOverlap() {
      return this.mAnimationInfo != null && this.mAnimationInfo.mAllowReturnTransitionOverlap != null ? this.mAnimationInfo.mAllowReturnTransitionOverlap.booleanValue() : true;
   }

   public void postponeEnterTransition() {
      this.ensureAnimationInfo().mEnterTransitionPostponed = true;
   }

   public void startPostponedEnterTransition() {
      if (this.mFragmentManager != null && this.mFragmentManager.mHost != null) {
         if (Looper.myLooper() != this.mFragmentManager.mHost.getHandler().getLooper()) {
            this.mFragmentManager.mHost.getHandler().postAtFrontOfQueue(new Runnable() {
               public void run() {
                  Fragment.this.callStartTransitionListener();
               }
            });
         } else {
            this.callStartTransitionListener();
         }
      } else {
         this.ensureAnimationInfo().mEnterTransitionPostponed = false;
      }

   }

   private void callStartTransitionListener() {
      Fragment.OnStartEnterTransitionListener listener;
      if (this.mAnimationInfo == null) {
         listener = null;
      } else {
         this.mAnimationInfo.mEnterTransitionPostponed = false;
         listener = this.mAnimationInfo.mStartEnterTransitionListener;
         this.mAnimationInfo.mStartEnterTransitionListener = null;
      }

      if (listener != null) {
         listener.onStartEnterTransition();
      }

   }

   public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
      writer.print(prefix);
      writer.print("mFragmentId=#");
      writer.print(Integer.toHexString(this.mFragmentId));
      writer.print(" mContainerId=#");
      writer.print(Integer.toHexString(this.mContainerId));
      writer.print(" mTag=");
      writer.println(this.mTag);
      writer.print(prefix);
      writer.print("mState=");
      writer.print(this.mState);
      writer.print(" mIndex=");
      writer.print(this.mIndex);
      writer.print(" mWho=");
      writer.print(this.mWho);
      writer.print(" mBackStackNesting=");
      writer.println(this.mBackStackNesting);
      writer.print(prefix);
      writer.print("mAdded=");
      writer.print(this.mAdded);
      writer.print(" mRemoving=");
      writer.print(this.mRemoving);
      writer.print(" mFromLayout=");
      writer.print(this.mFromLayout);
      writer.print(" mInLayout=");
      writer.println(this.mInLayout);
      writer.print(prefix);
      writer.print("mHidden=");
      writer.print(this.mHidden);
      writer.print(" mDetached=");
      writer.print(this.mDetached);
      writer.print(" mMenuVisible=");
      writer.print(this.mMenuVisible);
      writer.print(" mHasMenu=");
      writer.println(this.mHasMenu);
      writer.print(prefix);
      writer.print("mRetainInstance=");
      writer.print(this.mRetainInstance);
      writer.print(" mRetaining=");
      writer.print(this.mRetaining);
      writer.print(" mUserVisibleHint=");
      writer.println(this.mUserVisibleHint);
      if (this.mFragmentManager != null) {
         writer.print(prefix);
         writer.print("mFragmentManager=");
         writer.println(this.mFragmentManager);
      }

      if (this.mHost != null) {
         writer.print(prefix);
         writer.print("mHost=");
         writer.println(this.mHost);
      }

      if (this.mParentFragment != null) {
         writer.print(prefix);
         writer.print("mParentFragment=");
         writer.println(this.mParentFragment);
      }

      if (this.mArguments != null) {
         writer.print(prefix);
         writer.print("mArguments=");
         writer.println(this.mArguments);
      }

      if (this.mSavedFragmentState != null) {
         writer.print(prefix);
         writer.print("mSavedFragmentState=");
         writer.println(this.mSavedFragmentState);
      }

      if (this.mSavedViewState != null) {
         writer.print(prefix);
         writer.print("mSavedViewState=");
         writer.println(this.mSavedViewState);
      }

      if (this.mTarget != null) {
         writer.print(prefix);
         writer.print("mTarget=");
         writer.print(this.mTarget);
         writer.print(" mTargetRequestCode=");
         writer.println(this.mTargetRequestCode);
      }

      if (this.getNextAnim() != 0) {
         writer.print(prefix);
         writer.print("mNextAnim=");
         writer.println(this.getNextAnim());
      }

      if (this.mContainer != null) {
         writer.print(prefix);
         writer.print("mContainer=");
         writer.println(this.mContainer);
      }

      if (this.mView != null) {
         writer.print(prefix);
         writer.print("mView=");
         writer.println(this.mView);
      }

      if (this.mInnerView != null) {
         writer.print(prefix);
         writer.print("mInnerView=");
         writer.println(this.mView);
      }

      if (this.getAnimatingAway() != null) {
         writer.print(prefix);
         writer.print("mAnimatingAway=");
         writer.println(this.getAnimatingAway());
         writer.print(prefix);
         writer.print("mStateAfterAnimating=");
         writer.println(this.getStateAfterAnimating());
      }

      if (this.mLoaderManager != null) {
         writer.print(prefix);
         writer.println("Loader Manager:");
         this.mLoaderManager.dump(prefix + "  ", fd, writer, args);
      }

      if (this.mChildFragmentManager != null) {
         writer.print(prefix);
         writer.println("Child " + this.mChildFragmentManager + ":");
         this.mChildFragmentManager.dump(prefix + "  ", fd, writer, args);
      }

   }

   Fragment findFragmentByWho(String who) {
      if (who.equals(this.mWho)) {
         return this;
      } else {
         return this.mChildFragmentManager != null ? this.mChildFragmentManager.findFragmentByWho(who) : null;
      }
   }

   void instantiateChildFragmentManager() {
      if (this.mHost == null) {
         throw new IllegalStateException("Fragment has not been attached yet.");
      } else {
         this.mChildFragmentManager = new FragmentManagerImpl();
         this.mChildFragmentManager.attachController(this.mHost, new FragmentContainer() {
            @Nullable
            public View onFindViewById(int id) {
               if (Fragment.this.mView == null) {
                  throw new IllegalStateException("Fragment does not have a view");
               } else {
                  return Fragment.this.mView.findViewById(id);
               }
            }

            public boolean onHasView() {
               return Fragment.this.mView != null;
            }

            public Fragment instantiate(Context context, String className, Bundle arguments) {
               return Fragment.this.mHost.instantiate(context, className, arguments);
            }
         }, this);
      }
   }

   void performCreate(Bundle savedInstanceState) {
      if (this.mChildFragmentManager != null) {
         this.mChildFragmentManager.noteStateNotSaved();
      }

      this.mState = 1;
      this.mCalled = false;
      this.onCreate(savedInstanceState);
      if (!this.mCalled) {
         throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onCreate()");
      }
   }

   View performCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      if (this.mChildFragmentManager != null) {
         this.mChildFragmentManager.noteStateNotSaved();
      }

      this.mPerformedCreateView = true;
      return this.onCreateView(inflater, container, savedInstanceState);
   }

   void performActivityCreated(Bundle savedInstanceState) {
      if (this.mChildFragmentManager != null) {
         this.mChildFragmentManager.noteStateNotSaved();
      }

      this.mState = 2;
      this.mCalled = false;
      this.onActivityCreated(savedInstanceState);
      if (!this.mCalled) {
         throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onActivityCreated()");
      } else {
         if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchActivityCreated();
         }

      }
   }

   void performStart() {
      if (this.mChildFragmentManager != null) {
         this.mChildFragmentManager.noteStateNotSaved();
         this.mChildFragmentManager.execPendingActions();
      }

      this.mState = 4;
      this.mCalled = false;
      this.onStart();
      if (!this.mCalled) {
         throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onStart()");
      } else {
         if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchStart();
         }

         if (this.mLoaderManager != null) {
            this.mLoaderManager.doReportStart();
         }

      }
   }

   void performResume() {
      if (this.mChildFragmentManager != null) {
         this.mChildFragmentManager.noteStateNotSaved();
         this.mChildFragmentManager.execPendingActions();
      }

      this.mState = 5;
      this.mCalled = false;
      this.onResume();
      if (!this.mCalled) {
         throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onResume()");
      } else {
         if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchResume();
            this.mChildFragmentManager.execPendingActions();
         }

      }
   }

   void noteStateNotSaved() {
      if (this.mChildFragmentManager != null) {
         this.mChildFragmentManager.noteStateNotSaved();
      }

   }

   void performMultiWindowModeChanged(boolean isInMultiWindowMode) {
      this.onMultiWindowModeChanged(isInMultiWindowMode);
      if (this.mChildFragmentManager != null) {
         this.mChildFragmentManager.dispatchMultiWindowModeChanged(isInMultiWindowMode);
      }

   }

   void performPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
      this.onPictureInPictureModeChanged(isInPictureInPictureMode);
      if (this.mChildFragmentManager != null) {
         this.mChildFragmentManager.dispatchPictureInPictureModeChanged(isInPictureInPictureMode);
      }

   }

   void performConfigurationChanged(Configuration newConfig) {
      this.onConfigurationChanged(newConfig);
      if (this.mChildFragmentManager != null) {
         this.mChildFragmentManager.dispatchConfigurationChanged(newConfig);
      }

   }

   void performLowMemory() {
      this.onLowMemory();
      if (this.mChildFragmentManager != null) {
         this.mChildFragmentManager.dispatchLowMemory();
      }

   }

   boolean performCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      boolean show = false;
      if (!this.mHidden) {
         if (this.mHasMenu && this.mMenuVisible) {
            show = true;
            this.onCreateOptionsMenu(menu, inflater);
         }

         if (this.mChildFragmentManager != null) {
            show |= this.mChildFragmentManager.dispatchCreateOptionsMenu(menu, inflater);
         }
      }

      return show;
   }

   boolean performPrepareOptionsMenu(Menu menu) {
      boolean show = false;
      if (!this.mHidden) {
         if (this.mHasMenu && this.mMenuVisible) {
            show = true;
            this.onPrepareOptionsMenu(menu);
         }

         if (this.mChildFragmentManager != null) {
            show |= this.mChildFragmentManager.dispatchPrepareOptionsMenu(menu);
         }
      }

      return show;
   }

   boolean performOptionsItemSelected(MenuItem item) {
      if (!this.mHidden) {
         if (this.mHasMenu && this.mMenuVisible && this.onOptionsItemSelected(item)) {
            return true;
         }

         if (this.mChildFragmentManager != null && this.mChildFragmentManager.dispatchOptionsItemSelected(item)) {
            return true;
         }
      }

      return false;
   }

   boolean performContextItemSelected(MenuItem item) {
      if (!this.mHidden) {
         if (this.onContextItemSelected(item)) {
            return true;
         }

         if (this.mChildFragmentManager != null && this.mChildFragmentManager.dispatchContextItemSelected(item)) {
            return true;
         }
      }

      return false;
   }

   void performOptionsMenuClosed(Menu menu) {
      if (!this.mHidden) {
         if (this.mHasMenu && this.mMenuVisible) {
            this.onOptionsMenuClosed(menu);
         }

         if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchOptionsMenuClosed(menu);
         }
      }

   }

   void performSaveInstanceState(Bundle outState) {
      this.onSaveInstanceState(outState);
      if (this.mChildFragmentManager != null) {
         Parcelable p = this.mChildFragmentManager.saveAllState();
         if (p != null) {
            outState.putParcelable("android:support:fragments", p);
         }
      }

   }

   void performPause() {
      if (this.mChildFragmentManager != null) {
         this.mChildFragmentManager.dispatchPause();
      }

      this.mState = 4;
      this.mCalled = false;
      this.onPause();
      if (!this.mCalled) {
         throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onPause()");
      }
   }

   void performStop() {
      if (this.mChildFragmentManager != null) {
         this.mChildFragmentManager.dispatchStop();
      }

      this.mState = 3;
      this.mCalled = false;
      this.onStop();
      if (!this.mCalled) {
         throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onStop()");
      }
   }

   void performReallyStop() {
      if (this.mChildFragmentManager != null) {
         this.mChildFragmentManager.dispatchReallyStop();
      }

      this.mState = 2;
      if (this.mLoadersStarted) {
         this.mLoadersStarted = false;
         if (!this.mCheckedForLoaderManager) {
            this.mCheckedForLoaderManager = true;
            this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, false);
         }

         if (this.mLoaderManager != null) {
            if (this.mHost.getRetainLoaders()) {
               this.mLoaderManager.doRetain();
            } else {
               this.mLoaderManager.doStop();
            }
         }
      }

   }

   void performDestroyView() {
      if (this.mChildFragmentManager != null) {
         this.mChildFragmentManager.dispatchDestroyView();
      }

      this.mState = 1;
      this.mCalled = false;
      this.onDestroyView();
      if (!this.mCalled) {
         throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroyView()");
      } else {
         if (this.mLoaderManager != null) {
            this.mLoaderManager.doReportNextStart();
         }

         this.mPerformedCreateView = false;
      }
   }

   void performDestroy() {
      if (this.mChildFragmentManager != null) {
         this.mChildFragmentManager.dispatchDestroy();
      }

      this.mState = 0;
      this.mCalled = false;
      this.onDestroy();
      if (!this.mCalled) {
         throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroy()");
      } else {
         this.mChildFragmentManager = null;
      }
   }

   void performDetach() {
      this.mCalled = false;
      this.onDetach();
      this.mLayoutInflater = null;
      if (!this.mCalled) {
         throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDetach()");
      } else {
         if (this.mChildFragmentManager != null) {
            if (!this.mRetaining) {
               throw new IllegalStateException("Child FragmentManager of " + this + " was not " + " destroyed and this fragment is not retaining instance");
            }

            this.mChildFragmentManager.dispatchDestroy();
            this.mChildFragmentManager = null;
         }

      }
   }

   void setOnStartEnterTransitionListener(Fragment.OnStartEnterTransitionListener listener) {
      this.ensureAnimationInfo();
      if (listener != this.mAnimationInfo.mStartEnterTransitionListener) {
         if (listener != null && this.mAnimationInfo.mStartEnterTransitionListener != null) {
            throw new IllegalStateException("Trying to set a replacement startPostponedEnterTransition on " + this);
         } else {
            if (this.mAnimationInfo.mEnterTransitionPostponed) {
               this.mAnimationInfo.mStartEnterTransitionListener = listener;
            }

            if (listener != null) {
               listener.startListening();
            }

         }
      }
   }

   private Fragment.AnimationInfo ensureAnimationInfo() {
      if (this.mAnimationInfo == null) {
         this.mAnimationInfo = new Fragment.AnimationInfo();
      }

      return this.mAnimationInfo;
   }

   int getNextAnim() {
      return this.mAnimationInfo == null ? 0 : this.mAnimationInfo.mNextAnim;
   }

   void setNextAnim(int animResourceId) {
      if (this.mAnimationInfo != null || animResourceId != 0) {
         this.ensureAnimationInfo().mNextAnim = animResourceId;
      }
   }

   int getNextTransition() {
      return this.mAnimationInfo == null ? 0 : this.mAnimationInfo.mNextTransition;
   }

   void setNextTransition(int nextTransition, int nextTransitionStyle) {
      if (this.mAnimationInfo != null || nextTransition != 0 || nextTransitionStyle != 0) {
         this.ensureAnimationInfo();
         this.mAnimationInfo.mNextTransition = nextTransition;
         this.mAnimationInfo.mNextTransitionStyle = nextTransitionStyle;
      }
   }

   int getNextTransitionStyle() {
      return this.mAnimationInfo == null ? 0 : this.mAnimationInfo.mNextTransitionStyle;
   }

   SharedElementCallback getEnterTransitionCallback() {
      return this.mAnimationInfo == null ? null : this.mAnimationInfo.mEnterTransitionCallback;
   }

   SharedElementCallback getExitTransitionCallback() {
      return this.mAnimationInfo == null ? null : this.mAnimationInfo.mExitTransitionCallback;
   }

   View getAnimatingAway() {
      return this.mAnimationInfo == null ? null : this.mAnimationInfo.mAnimatingAway;
   }

   void setAnimatingAway(View view) {
      this.ensureAnimationInfo().mAnimatingAway = view;
   }

   void setAnimator(Animator animator) {
      this.ensureAnimationInfo().mAnimator = animator;
   }

   Animator getAnimator() {
      return this.mAnimationInfo == null ? null : this.mAnimationInfo.mAnimator;
   }

   int getStateAfterAnimating() {
      return this.mAnimationInfo == null ? 0 : this.mAnimationInfo.mStateAfterAnimating;
   }

   void setStateAfterAnimating(int state) {
      this.ensureAnimationInfo().mStateAfterAnimating = state;
   }

   boolean isPostponed() {
      return this.mAnimationInfo == null ? false : this.mAnimationInfo.mEnterTransitionPostponed;
   }

   boolean isHideReplaced() {
      return this.mAnimationInfo == null ? false : this.mAnimationInfo.mIsHideReplaced;
   }

   void setHideReplaced(boolean replaced) {
      this.ensureAnimationInfo().mIsHideReplaced = replaced;
   }

   static class AnimationInfo {
      View mAnimatingAway;
      Animator mAnimator;
      int mStateAfterAnimating;
      int mNextAnim;
      int mNextTransition;
      int mNextTransitionStyle;
      private Object mEnterTransition = null;
      private Object mReturnTransition;
      private Object mExitTransition;
      private Object mReenterTransition;
      private Object mSharedElementEnterTransition;
      private Object mSharedElementReturnTransition;
      private Boolean mAllowReturnTransitionOverlap;
      private Boolean mAllowEnterTransitionOverlap;
      SharedElementCallback mEnterTransitionCallback;
      SharedElementCallback mExitTransitionCallback;
      boolean mEnterTransitionPostponed;
      Fragment.OnStartEnterTransitionListener mStartEnterTransitionListener;
      boolean mIsHideReplaced;

      AnimationInfo() {
         this.mReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
         this.mExitTransition = null;
         this.mReenterTransition = Fragment.USE_DEFAULT_TRANSITION;
         this.mSharedElementEnterTransition = null;
         this.mSharedElementReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
         this.mEnterTransitionCallback = null;
         this.mExitTransitionCallback = null;
      }
   }

   interface OnStartEnterTransitionListener {
      void onStartEnterTransition();

      void startListening();
   }

   public static class InstantiationException extends RuntimeException {
      public InstantiationException(String msg, Exception cause) {
         super(msg, cause);
      }
   }

   public static class SavedState implements Parcelable {
      final Bundle mState;
      public static final Creator CREATOR = new Creator() {
         public Fragment.SavedState createFromParcel(Parcel in) {
            return new Fragment.SavedState(in, (ClassLoader)null);
         }

         public Fragment.SavedState[] newArray(int size) {
            return new Fragment.SavedState[size];
         }
      };

      SavedState(Bundle state) {
         this.mState = state;
      }

      SavedState(Parcel in, ClassLoader loader) {
         this.mState = in.readBundle();
         if (loader != null && this.mState != null) {
            this.mState.setClassLoader(loader);
         }

      }

      public int describeContents() {
         return 0;
      }

      public void writeToParcel(Parcel dest, int flags) {
         dest.writeBundle(this.mState);
      }
   }
}
