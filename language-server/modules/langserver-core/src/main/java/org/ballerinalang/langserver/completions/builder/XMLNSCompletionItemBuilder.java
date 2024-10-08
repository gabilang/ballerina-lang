/*
 *  Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.ballerinalang.langserver.completions.builder;

import io.ballerina.compiler.api.symbols.XMLNamespaceSymbol;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;

/**
 * Completion item builder for the {@link XMLNamespaceSymbol}.
 *
 * @since 1.0
 */
public final class XMLNSCompletionItemBuilder {

    private XMLNSCompletionItemBuilder() {
    }

    /**
     * Build the constant {@link CompletionItem}.
     *
     * @param namespaceSymbol constant symbol
     * @return {@link CompletionItem} generated completion item
     */
    public static CompletionItem build(XMLNamespaceSymbol namespaceSymbol) {
        CompletionItem completionItem = new CompletionItem();
        completionItem.setLabel(namespaceSymbol.getName().orElse(""));
        completionItem.setInsertText(namespaceSymbol.getName().orElse(""));
        completionItem.setDetail(namespaceSymbol.kind().name().toLowerCase());
        completionItem.setDocumentation(namespaceSymbol.namespaceUri());
        completionItem.setKind(CompletionItemKind.Variable);

        return completionItem;
    }
}
