package android.support.v7.widget;

import java.util.ArrayList;

class PositionMap implements Cloneable {
   private static final Object DELETED = new Object();
   private boolean mGarbage;
   private int[] mKeys;
   private Object[] mValues;
   private int mSize;

   PositionMap() {
      this(10);
   }

   PositionMap(int initialCapacity) {
      this.mGarbage = false;
      if (initialCapacity == 0) {
         this.mKeys = PositionMap.ContainerHelpers.EMPTY_INTS;
         this.mValues = PositionMap.ContainerHelpers.EMPTY_OBJECTS;
      } else {
         initialCapacity = idealIntArraySize(initialCapacity);
         this.mKeys = new int[initialCapacity];
         this.mValues = new Object[initialCapacity];
      }

      this.mSize = 0;
   }

   public PositionMap clone() {
      PositionMap clone = null;

      try {
         clone = (PositionMap)super.clone();
         clone.mKeys = (int[])this.mKeys.clone();
         clone.mValues = (Object[])this.mValues.clone();
      } catch (CloneNotSupportedException var3) {
         ;
      }

      return clone;
   }

   public Object get(int key) {
      return this.get(key, (Object)null);
   }

   public Object get(int key, Object valueIfKeyNotFound) {
      int i = PositionMap.ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
      return i >= 0 && this.mValues[i] != DELETED ? this.mValues[i] : valueIfKeyNotFound;
   }

   public void delete(int key) {
      int i = PositionMap.ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
      if (i >= 0 && this.mValues[i] != DELETED) {
         this.mValues[i] = DELETED;
         this.mGarbage = true;
      }

   }

   public void remove(int key) {
      this.delete(key);
   }

   public void removeAt(int index) {
      if (this.mValues[index] != DELETED) {
         this.mValues[index] = DELETED;
         this.mGarbage = true;
      }

   }

   public void removeAtRange(int index, int size) {
      int end = Math.min(this.mSize, index + size);

      for(int i = index; i < end; ++i) {
         this.removeAt(i);
      }

   }

   public void insertKeyRange(int keyStart, int count) {
   }

   public void removeKeyRange(ArrayList removedItems, int keyStart, int count) {
   }

   private void gc() {
      int n = this.mSize;
      int o = 0;
      int[] keys = this.mKeys;
      Object[] values = this.mValues;

      for(int i = 0; i < n; ++i) {
         Object val = values[i];
         if (val != DELETED) {
            if (i != o) {
               keys[o] = keys[i];
               values[o] = val;
               values[i] = null;
            }

            ++o;
         }
      }

      this.mGarbage = false;
      this.mSize = o;
   }

   public void put(int key, Object value) {
      int i = PositionMap.ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
      if (i >= 0) {
         this.mValues[i] = value;
      } else {
         i = ~i;
         if (i < this.mSize && this.mValues[i] == DELETED) {
            this.mKeys[i] = key;
            this.mValues[i] = value;
            return;
         }

         if (this.mGarbage && this.mSize >= this.mKeys.length) {
            this.gc();
            i = ~PositionMap.ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
         }

         if (this.mSize >= this.mKeys.length) {
            int n = idealIntArraySize(this.mSize + 1);
            int[] nkeys = new int[n];
            Object[] nvalues = new Object[n];
            System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
            System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
            this.mKeys = nkeys;
            this.mValues = nvalues;
         }

         if (this.mSize - i != 0) {
            System.arraycopy(this.mKeys, i, this.mKeys, i + 1, this.mSize - i);
            System.arraycopy(this.mValues, i, this.mValues, i + 1, this.mSize - i);
         }

         this.mKeys[i] = key;
         this.mValues[i] = value;
         ++this.mSize;
      }

   }

   public int size() {
      if (this.mGarbage) {
         this.gc();
      }

      return this.mSize;
   }

   public int keyAt(int index) {
      if (this.mGarbage) {
         this.gc();
      }

      return this.mKeys[index];
   }

   public Object valueAt(int index) {
      if (this.mGarbage) {
         this.gc();
      }

      return this.mValues[index];
   }

   public void setValueAt(int index, Object value) {
      if (this.mGarbage) {
         this.gc();
      }

      this.mValues[index] = value;
   }

   public int indexOfKey(int key) {
      if (this.mGarbage) {
         this.gc();
      }

      return PositionMap.ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
   }

   public int indexOfValue(Object value) {
      if (this.mGarbage) {
         this.gc();
      }

      for(int i = 0; i < this.mSize; ++i) {
         if (this.mValues[i] == value) {
            return i;
         }
      }

      return -1;
   }

   public void clear() {
      int n = this.mSize;
      Object[] values = this.mValues;

      for(int i = 0; i < n; ++i) {
         values[i] = null;
      }

      this.mSize = 0;
      this.mGarbage = false;
   }

   public void append(int key, Object value) {
      if (this.mSize != 0 && key <= this.mKeys[this.mSize - 1]) {
         this.put(key, value);
      } else {
         if (this.mGarbage && this.mSize >= this.mKeys.length) {
            this.gc();
         }

         int pos = this.mSize;
         if (pos >= this.mKeys.length) {
            int n = idealIntArraySize(pos + 1);
            int[] nkeys = new int[n];
            Object[] nvalues = new Object[n];
            System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
            System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
            this.mKeys = nkeys;
            this.mValues = nvalues;
         }

         this.mKeys[pos] = key;
         this.mValues[pos] = value;
         this.mSize = pos + 1;
      }
   }

   public String toString() {
      if (this.size() <= 0) {
         return "{}";
      } else {
         StringBuilder buffer = new StringBuilder(this.mSize * 28);
         buffer.append('{');

         for(int i = 0; i < this.mSize; ++i) {
            if (i > 0) {
               buffer.append(", ");
            }

            int key = this.keyAt(i);
            buffer.append(key);
            buffer.append('=');
            Object value = this.valueAt(i);
            if (value != this) {
               buffer.append(value);
            } else {
               buffer.append("(this Map)");
            }
         }

         buffer.append('}');
         return buffer.toString();
      }
   }

   static int idealByteArraySize(int need) {
      for(int i = 4; i < 32; ++i) {
         if (need <= (1 << i) - 12) {
            return (1 << i) - 12;
         }
      }

      return need;
   }

   static int idealBooleanArraySize(int need) {
      return idealByteArraySize(need);
   }

   static int idealShortArraySize(int need) {
      return idealByteArraySize(need * 2) / 2;
   }

   static int idealCharArraySize(int need) {
      return idealByteArraySize(need * 2) / 2;
   }

   static int idealIntArraySize(int need) {
      return idealByteArraySize(need * 4) / 4;
   }

   static int idealFloatArraySize(int need) {
      return idealByteArraySize(need * 4) / 4;
   }

   static int idealObjectArraySize(int need) {
      return idealByteArraySize(need * 4) / 4;
   }

   static int idealLongArraySize(int need) {
      return idealByteArraySize(need * 8) / 8;
   }

   static class ContainerHelpers {
      static final boolean[] EMPTY_BOOLEANS = new boolean[0];
      static final int[] EMPTY_INTS = new int[0];
      static final long[] EMPTY_LONGS = new long[0];
      static final Object[] EMPTY_OBJECTS = new Object[0];

      static int binarySearch(int[] array, int size, int value) {
         int lo = 0;
         int hi = size - 1;

         while(lo <= hi) {
            int mid = lo + hi >>> 1;
            int midVal = array[mid];
            if (midVal < value) {
               lo = mid + 1;
            } else {
               if (midVal <= value) {
                  return mid;
               }

               hi = mid - 1;
            }
         }

         return ~lo;
      }
   }
}
