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

package de.vette.idea.neos.config.yaml;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import de.vette.idea.neos.util.psi.ParentPathPatternCondition;
import org.jetbrains.yaml.YAMLLanguage;
import org.jetbrains.yaml.YAMLTokenTypes;
import org.jetbrains.yaml.psi.*;

public class YamlElementPatternHelper {

    public static ElementPattern<PsiElement> getWithFirstRootKey() {

        return PlatformPatterns.and(PlatformPatterns.or(
                // foo:
                //   <caret>
                PlatformPatterns
                        .psiElement().with(new ParentPathPatternCondition(
                        YAMLScalar.class, YAMLMapping.class,
                        YAMLKeyValue.class, YAMLMapping.class,
                        YAMLDocument.class
                ))
                        .withLanguage(YAMLLanguage.INSTANCE),

                // foo:
                //   <caret> (on incomplete)
                PlatformPatterns
                        .psiElement().afterLeaf(
                        PlatformPatterns.psiElement(YAMLTokenTypes.INDENT).with(
                                new ParentPathPatternCondition(YAMLKeyValue.class, YAMLMapping.class, YAMLDocument.class)
                        )
                )
                        .withLanguage(YAMLLanguage.INSTANCE),

                // foo:
                //   fo<caret>:
                PlatformPatterns.psiElement().with(new ParentPathPatternCondition(
                        YAMLKeyValue.class, YAMLMapping.class,
                        YAMLKeyValue.class, YAMLMapping.class,
                        YAMLDocument.class)
                )
        ));
    }

    /**
     * auto complete on
     *
     * {arrayName}: [refer|]
     *
     * {arrayName}:
     *   - refer|
     *
     * @param arrayName Name of the array key we want to be an item of
     */
    public static ElementPattern<PsiElement> getInArray(String arrayName) {
        return PlatformPatterns.or(
            PlatformPatterns.psiElement().withParent(
                PlatformPatterns.psiElement(YAMLScalar.class).withParent(
                    PlatformPatterns.psiElement(YAMLSequenceItem.class).withParent(
                        PlatformPatterns.psiElement(YAMLSequence.class).withParent(
                            PlatformPatterns.psiElement(YAMLKeyValue.class).withName(arrayName)
                        )
                    )
                )
            ),
            PlatformPatterns.psiElement().withParent(
                PlatformPatterns.psiElement(YAMLSequenceItem.class).withParent(
                    PlatformPatterns.psiElement(YAMLSequence.class).withParent(
                        PlatformPatterns.psiElement(YAMLKeyValue.class).withName(arrayName)
                    )
                )
            )
        );
    }
}
