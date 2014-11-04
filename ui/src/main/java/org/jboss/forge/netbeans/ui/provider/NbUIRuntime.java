/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.forge.netbeans.ui.provider;

import org.jboss.forge.addon.ui.UIRuntime;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.input.UIPrompt;
import org.jboss.forge.addon.ui.progress.UIProgressMonitor;
import org.jboss.forge.netbeans.ui.progress.NbUIProgressMonitor;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;

/**
 * Implementation of the UIRuntime interface
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public class NbUIRuntime implements UIRuntime {

    @Override
    public UIProgressMonitor createProgressMonitor(UIContext context) {
        ProgressHandle handle = ProgressHandleFactory.createHandle("JBoss Forge Task");
        return new NbUIProgressMonitor(handle);
    }

    @Override
    public UIPrompt createPrompt(UIContext context) {
        return new NbUIPrompt();
    }

}
