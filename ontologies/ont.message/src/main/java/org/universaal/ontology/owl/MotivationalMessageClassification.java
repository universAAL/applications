/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es - Universidad Politécnica de Madrid
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
package org.universaal.ontology.owl;

import org.universAAL.middleware.owl.ManagedIndividual;

public class MotivationalMessageClassification extends ManagedIndividual {
  public static final String MY_URI = MessageOntology.NAMESPACE
    + "MotivationalMessageClassification";

  public static final int EDUCATIONAL = 0;
  public static final int REMINDER = 1;
  public static final int REWARD = 2;
  public static final int PERSONALIZED_FEEDBACK = 3;
  public static final int TEST = 4;
  public static final int INQUIRY = 5;
  public static final int NOTIFICATION= 6;
  public static final int EMOTION= 7;

  private static final String[] names = {
    "educational","reminder","reward","personalizedFeedback", "test", "inquiry", "notification", "emotion" };

  public static final MotivationalMessageClassification educational = new MotivationalMessageClassification(EDUCATIONAL);
  public static final MotivationalMessageClassification reminder = new MotivationalMessageClassification(REMINDER);
  public static final MotivationalMessageClassification reward = new MotivationalMessageClassification(REWARD);
  public static final MotivationalMessageClassification personalizedFeedback = new MotivationalMessageClassification(PERSONALIZED_FEEDBACK);
  public static final MotivationalMessageClassification test = new MotivationalMessageClassification(TEST);
  public static final MotivationalMessageClassification inquiry = new MotivationalMessageClassification(INQUIRY);
  public static final MotivationalMessageClassification notification = new MotivationalMessageClassification(NOTIFICATION);
  public static final MotivationalMessageClassification emotion = new MotivationalMessageClassification(EMOTION);
  

  private int order;

  private MotivationalMessageClassification(int order) {
    super(MessageOntology.NAMESPACE + names[order]);
    this.order = order;
  }

  public int getPropSerializationType(String propURI) {
    return PROP_SERIALIZATION_OPTIONAL;
  }

  public boolean isWellFormed() {
    return true;
  }

  public String name() {
    return names[order];
  }

  public int ord() {
    return order;
  }

  public String getClassURI() {
    return MY_URI;
  }

  public static MotivationalMessageClassification getMotivationalMessageClassificationByOrder(int order) {
    switch (order) {
      case EDUCATIONAL:
        return educational;
      case REMINDER:
        return reminder;
      case REWARD:
        return reward;
      case PERSONALIZED_FEEDBACK:
        return personalizedFeedback;
      case TEST:
          return test;
      case INQUIRY:
          return inquiry;
      case NOTIFICATION:
          return notification;
      case EMOTION:
          return emotion;
    default:
      return null;    }
  }

  public static final MotivationalMessageClassification valueOf(String name) {
	if (name == null)
	    return null;

	if (name.startsWith(MessageOntology.NAMESPACE))
	    name = name.substring(MessageOntology.NAMESPACE.length());

	for (int i = EDUCATIONAL; i <= EMOTION; i++)
	    if (names[i].equals(name))
		return getMotivationalMessageClassificationByOrder(i);

	return null;
  }
}
