/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.forge.netbeans.ui;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UISelection;
import org.jboss.forge.addon.ui.controller.WizardCommandController;
import org.netbeans.api.progress.ProgressHandle;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.ChangeSupport;
import org.openide.util.Exceptions;

/**
 * The Wizard Descriptor Iterator
 *
 * @author ggastald
 */
public class ForgeWizardIterator implements WizardDescriptor.ProgressInstantiatingIterator<WizardDescriptor> {

    private final WizardCommandController controller;
    private final ChangeSupport changeSupport = new ChangeSupport(this);

    private ForgeWizardPanel current;

    public ForgeWizardIterator(WizardCommandController controller) {
        this.controller = controller;
    }

    @Override
    public ForgeWizardPanel current() {
        return current;
    }

    @Override
    public String name() {
        return controller.getInitialMetadata().getName();
    }

    @Override
    public boolean hasNext() {
        return controller.canMoveToNextStep();
    }

    @Override
    public boolean hasPrevious() {
        return controller.canMoveToPreviousStep();
    }

    @Override
    public void nextPanel() {
        try {
            controller.next().initialize();
            refreshCurrentPanel();
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void previousPanel() {
        try {
            controller.previous();
            refreshCurrentPanel();
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void refreshCurrentPanel() {
        //TODO: Create current panel
    }

    @Override
    public Set instantiate() throws IOException {
        return instantiate(null);
    }

    @Override
    public Set instantiate(ProgressHandle handle) throws IOException {
        UIContext context = controller.getContext();
        try {
            if (handle != null) {
                // see NbUIRuntime.createProgressMonitor
                Map<Object, Object> attributeMap = context.getAttributeMap();
                attributeMap.put(ProgressHandle.class, handle);
            }
            controller.execute();
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        Set<Node> nodes = new LinkedHashSet<>();
        for (Object resource : context.getSelection()) {
            if (resource instanceof Resource) {
                File file = (File) ((Resource) resource).getUnderlyingResourceObject();
                DataObject dataObject = FileUtil.toFileObject(file).getLookup().lookup(DataObject.class);
                if (dataObject != null) {
                    nodes.add(dataObject.getNodeDelegate());
                }
            }
        }
        return nodes;
    }

    @Override
    public void addChangeListener(ChangeListener cl) {
//        changeSupport.addChangeListener(cl);
    }

    @Override
    public void removeChangeListener(ChangeListener cl) {
//        changeSupport.removeChangeListener(cl);
    }

    @Override
    public void initialize(WizardDescriptor wizard) {
    }

    @Override
    public void uninitialize(WizardDescriptor wizard) {
    }

}
