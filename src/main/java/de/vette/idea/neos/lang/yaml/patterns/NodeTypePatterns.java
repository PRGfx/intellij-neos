/*
 *  IntelliJ IDEA plugin to support the Neos CMS.
 *  Copyright (C) 2016  Christian Vette
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.vette.idea.neos.lang.yaml.patterns;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiFilePattern;
import com.intellij.psi.PsiElement;
import de.vette.idea.neos.config.yaml.YamlElementPatternHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLFile;
import org.jetbrains.yaml.psi.YAMLKeyValue;

public class NodeTypePatterns extends PlatformPatterns {
    /**
     * Matches NodeType.*.yaml files
     */
    protected static PsiFilePattern.@NotNull Capture<YAMLFile> nodeTypeFile() {
        return psiFile(YAMLFile.class)
                .withName(
                    string().startsWith("NodeTypes.")
                        .and(string().endsWith(".yaml"))
                );
    }

    /**
     * Matches root-level node-types in their definition file
     */
    public static ElementPattern<PsiElement> nodeTypeDefinition() {
        return psiElement()
            .inFile(nodeTypeFile())
            .andOr(
                psiElement().withSuperParent(3, YAMLDocument.class)
            );
    }

    /**
     * Matches array-key node-types as in
     * superTypes
     * or
     * constraints.nodeTypes
     */
    public static ElementPattern<PsiElement> nodeTypeKey() {
        return psiElement()
            .inFile(nodeTypeFile())
            .andOr(
                psiElement()
                    .withAncestor(3, psiElement(YAMLKeyValue.class).withName("superTypes")),
                psiElement()
                    .withAncestor(3, psiElement(YAMLKeyValue.class).withName("nodeTypes"))
                    .withAncestor(5, psiElement(YAMLKeyValue.class).withName("constraints"))
            );
    }

    /**
     * Matches scalar node-types as in
     * childNodes.*.type
     * or
     * (editorOptions.)nodeTypes
     */
    public static ElementPattern<PsiElement> nodeTypeReference() {
        return psiElement()
            .inFile(nodeTypeFile())
            .andOr(
                psiElement()
                    .withAncestor(2, psiElement(YAMLKeyValue.class).withName("type"))
                    .withAncestor(6, psiElement(YAMLKeyValue.class).withName("childNodes")),
                YamlElementPatternHelper.getInArray("nodeTypes")
            );
    }
}
