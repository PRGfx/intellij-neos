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

package de.vette.idea.neos.lang.yaml.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.util.ProcessingContext;
import com.intellij.util.indexing.FileBasedIndex;
import de.vette.idea.neos.NeosIcons;
import de.vette.idea.neos.indexes.NodeTypesYamlFileIndex;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class NodeTypeProvider extends CompletionProvider<CompletionParameters> {
    protected String format;

    /**
     * @param format Workaround to add e.g. quotes around the result.
     *               Can be used to set up the completion provider for different contexts.
     */
    public NodeTypeProvider(String format) {
        this.format = format;
    }
    public NodeTypeProvider() {
        this.format = null;
    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
        Project project = parameters.getPosition().getProject();
        Collection<String> nodeTypeNames = FileBasedIndex.getInstance()
            .getAllKeys(NodeTypesYamlFileIndex.KEY, project);

        for (String nodeTypeName : nodeTypeNames) {
            var text = nodeTypeName;
            if (this.format != null) {
                text = String.format(this.format, text);
            }
            result.addElement(LookupElementBuilder.create(text).withIcon(NeosIcons.NODE_TYPE));
        }
    }
}
