/*
 * Copyright (c) 2012-2015 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.jberet.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Properties;
import java.util.StringTokenizer;

import org.wildfly.security.manager.WildFlySecurityManager;

public final class BatchUtil {
    public static final String NL = WildFlySecurityManager.getPropertyPrivileged("line.separator", "\n");
    private static final String keyValDelimiter = " = ";

    public static String propertiesToString(final Properties properties) {
        if (properties == null) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        for (final String key : properties.stringPropertyNames()) {
            sb.append(key).append(keyValDelimiter).append(properties.getProperty(key)).append(NL);
        }
        return sb.toString();
    }

    public static Properties stringToProperties(final String content) {
        final Properties result = new Properties();
        if (content == null || content.isEmpty()) {
            return result;
        }
        final StringTokenizer st = new StringTokenizer(content, NL);
        while (st.hasMoreTokens()) {
            final String line = st.nextToken();
            final int delimiterPos = line.indexOf(keyValDelimiter);
            if (delimiterPos > 0) {
                result.setProperty(line.substring(0, delimiterPos), line.substring(delimiterPos + keyValDelimiter.length()));
            }
        }
        return result;
    }

    public static byte[] objectToBytes(final Object obj) throws IOException {
        if (obj == null) {
            return null;
        }
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            return bos.toByteArray();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                bos.close();
            } catch (IOException e2) {
                //ignore
            }
        }
    }

    public static Serializable bytesToSerializableObject(final byte[] bytes, final ClassLoader classLoader)
            throws IOException, ClassNotFoundException {
        if (bytes == null) {
            return null;
        }
        final ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStreamWithClassloader(bis, classLoader);
            return (Serializable) in.readObject();
        } finally {
            try {
                bis.close();
                if (in != null) {
                    in.close();
                }
            } catch (IOException e2) {
                //ignore
            }
        }
    }
}
