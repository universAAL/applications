/**
	Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es
	Instituto Tecnologico de Aplicaciones de Comunicacion 
	Avanzadas - Grupo Tecnologias para la Salud y el 
	Bienestar (TSB)
	
	
	2012 Ericsson Nikola Tesla d.d., www.ericsson.com/hr
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.agenda.remote.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.agenda.remote.AgendaWebGUI;
import org.universAAL.agenda.remote.SCallee;
import org.universAAL.agenda.remote.SCaller;
import org.universAAL.agenda.remote.UIProvider;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.container.utils.LogUtils;

public class Activator implements BundleActivator {

    public static BundleContext context = null;
    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;
    public static SCallee sCallee = null;
    public static SCaller sCaller = null;
    public static UIProvider uIProvider = null;
    public static AgendaWebGUI webUI = null;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
     * )
     */
    public void start(BundleContext arg0) throws Exception {
	BundleContext[] bc = { arg0 };
	mcontext = uAALBundleContainer.THE_CONTAINER.registerModule(bc);
	context = arg0;
	webUI = new AgendaWebGUI();
	uIProvider = new UIProvider(mcontext, webUI);
	sCaller = new SCaller(mcontext);
	sCallee = new SCallee(mcontext);

	LogUtils.logInfo(mcontext, this.getClass(), "start",
		new Object[] { "agenda.remote bundle has started." }, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext arg0) throws Exception {
	webUI = null;
	uIProvider = null;
	sCaller = null;
	sCallee = null;
	context = null;

	LogUtils.logInfo(mcontext, this.getClass(), "stop",
		new Object[] { "agenda.remote bundle has stopped." }, null);
    }

    /**
     * @return the mcontext
     */
    public static ModuleContext getMcontext() {
        return mcontext;
    }

}
