/*
 * Copyright 2022 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.linecorp.armeria.internal.common;

import com.linecorp.armeria.common.Flags;

/**
 * A utility class which determines if all static initializations in {@link Flags}
 * have been completed.
 */
public final class FlagsLoaded {

    private static boolean loaded;

    /**
     * Returns whether all flags defined in {@link Flags} have been loaded.
     */
    public static boolean get() {
        return loaded;
    }

    public static void set() {
        loaded = true;
    }

    private FlagsLoaded() {}
}
