/*******************************************************************************
 * Copyright 2012 , http://www.prosyst.com - ProSyst Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.universAAL.AALapplication.medication_manager.persistence.impl;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author George Fournadjiev
 */
public final class SoftCache<K, V> {

  private HashMap<K, SoftReference<V>> map = new HashMap<K, SoftReference<V>>();

  public V get(K key) {
    SoftReference<V> softRef = map.get(key);

    if (softRef == null)
      return null;

    return softRef.get();
  }

  public V put(K key, V value) {
    SoftReference<V> softRef = map.put(key, new SoftReference(value));

    if (softRef == null)
      return null;

    V oldValue = softRef.get();
    softRef.clear();

    return oldValue;
  }

  public V remove(K key) {
    SoftReference<V> softRef = map.remove(key);

    if (softRef == null)
      return null;

    V oldValue = softRef.get();
    softRef.clear();

    return oldValue;
  }

  public int size() {
    return map.size();
  }

  public boolean contains(K key) {
    return map.containsKey(key);
  }

  public Collection<V> values() {

    Collection<SoftReference<V>> values = map.values();

    Collection<V> collection = new ArrayList<V>(values.size());
    for (SoftReference<V> reference : values) {
      V e = reference.get();
      if (e != null) {
        collection.add(e);
      }
    }

    return collection;
  }

}
