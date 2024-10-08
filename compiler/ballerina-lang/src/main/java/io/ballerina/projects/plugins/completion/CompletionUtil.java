/*
 * Copyright (c) 2023, WSO2 LLC. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.ballerina.projects.plugins.completion;

/**
 * Util class for completion providers.
 *
 * @since 2201.7.0
 */
public final class CompletionUtil {

    public static final String LINE_BREAK = System.lineSeparator();
    public static final String PADDING = "\t";

    private CompletionUtil() {
    }

    public static String getPlaceHolderText(int index, String defaultValue) {
        return "${" + index + ":" + defaultValue + "}";
    }

    public static String getPlaceHolderText(int index) {
        return "${" + index + "}";
    }
}
