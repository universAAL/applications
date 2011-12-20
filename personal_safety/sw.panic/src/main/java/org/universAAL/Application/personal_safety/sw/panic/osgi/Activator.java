/*******************************************************************************
 * Copyright 2011 Universidad Politécnica de Madrid
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
package org.universAAL.Application.personal_safety.sw.panic.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.Application.personal_safety.sw.panic.ISubscriber;
import org.universAAL.Application.personal_safety.sw.panic.OPublisher;
import org.universAAL.Application.personal_safety.sw.panic.SCallee;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;

public class Activator implements BundleActivator{
	public static BundleContext context=null;
	public static SCallee scallee=null;
	public static ContextPublisher cpublisher=null;
	public static ISubscriber isubscriber;
	public static OPublisher opublisher;

	public void start(BundleContext context) throws Exception {
		Activator.context=context;
		scallee=new SCallee(context);
		cpublisher=new DefaultContextPublisher(context,null);
		isubscriber = new ISubscriber(context);
		opublisher = new OPublisher(context); 
	}

	public void stop(BundleContext arg0) throws Exception {
		scallee.close();
		cpublisher.close();
	}

}
