/*
 * Copyright (c) 2017 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.jberet.spi;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;

import org.jberet.runtime.context.StepContextImpl;

public interface PartitionHandler {
    void setResourceTracker(BlockingQueue<Boolean> completedPartitionThreads);

    void setCollectorDataQueue(BlockingQueue<Serializable> collectorDataQueue);

    void submitPartitionTask(StepContextImpl partitionStepContext, int currentIndex, int numOfPartitions) throws Exception;

    default void close(StepContextImpl stepContext) {}
}
