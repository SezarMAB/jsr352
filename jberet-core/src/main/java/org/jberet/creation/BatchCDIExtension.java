/*
 * Copyright (c) 2013-2018 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.jberet.creation;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;

public class BatchCDIExtension implements javax.enterprise.inject.spi.Extension {
    public void beforeBeanDiscovery(@Observes final BeforeBeanDiscovery beforeBeanDiscovery, final BeanManager beanManager) {
        final AnnotatedType<BatchBeanProducer> batchProducerAnnotatedType = beanManager.createAnnotatedType(BatchBeanProducer.class);
        beforeBeanDiscovery.addAnnotatedType(batchProducerAnnotatedType, BatchBeanProducer.class.getName());
    }

    public void addContext(@Observes final AfterBeanDiscovery afterBeanDiscovery) {
        afterBeanDiscovery.addContext(JobScopedContextImpl.INSTANCE);
        afterBeanDiscovery.addContext(StepScopedContextImpl.INSTANCE);
        afterBeanDiscovery.addContext(PartitionScopedContextImpl.INSTANCE);
    }
}
