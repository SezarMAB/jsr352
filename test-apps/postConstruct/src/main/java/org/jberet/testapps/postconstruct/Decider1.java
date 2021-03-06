/*
 * Copyright (c) 2013 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.jberet.testapps.postconstruct;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.batch.api.BatchProperty;
import javax.batch.api.Decider;
import javax.batch.operations.BatchRuntimeException;
import javax.batch.runtime.StepExecution;
import javax.inject.Inject;
import javax.inject.Named;

import org.jberet.testapps.common.PostConstructPreDestroyBase;

@Named("postconstruct.Decider1")
public class Decider1 extends PostConstructPreDestroyBase implements Decider {
    @Inject
    @BatchProperty(name = "os.name")
    private String osName;

    @Override
    public String decide(final StepExecution[] executions) throws Exception {
        addToJobExitStatus("Decider1.decide");
        return jobContext.getExitStatus();
    }

    @PostConstruct
    public void ps() {
        System.out.printf("Decider1 PostConstruct of %s%n", this);
        if (osName == null) {
            throw new BatchRuntimeException("osNmae field has not been initialized when checking from PostConstruct method.");
        }
        addToJobExitStatus("Decider1.ps");
    }

    @PreDestroy
    public void pd() {
        System.out.printf("Decider1 PreDestroy of %s%n", this);
        addToJobExitStatus("Decider1.pd");
    }
}
