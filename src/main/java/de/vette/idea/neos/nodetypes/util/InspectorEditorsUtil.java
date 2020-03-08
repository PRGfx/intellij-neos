package de.vette.idea.neos.nodetypes.util;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import de.vette.idea.neos.NeosProjectComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLUtil;
import org.jetbrains.yaml.psi.YAMLFile;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLMapping;

import java.util.HashMap;

public class InspectorEditorsUtil {
    public static HashMap<String, String> getEditorsInFile(@NotNull PsiFile psiFile) {
        YAMLKeyValue defaultContext = YAMLUtil.getQualifiedKeyInFile((YAMLFile) psiFile, "Neos", "Neos", "userInterface", "inspector", "editors");

        HashMap<String, String> result = new HashMap<>();
        if (defaultContext != null) {
            PsiElement mapping = defaultContext.getLastChild();
            if (mapping instanceof YAMLMapping) {
                for (PsiElement mappingElement : mapping.getChildren()) {
                    if (mappingElement instanceof YAMLKeyValue) {
                        YAMLKeyValue keyValue = (YAMLKeyValue) mappingElement;
                        if (keyValue.getValue() == null) {
                            continue;
                        }
                        result.put(keyValue.getKeyText(), keyValue.getKeyText());
                        NeosProjectComponent.getLogger().debug("inspector editor: " + keyValue.getKeyText());
                    }
                }
            }
        }

        return result;
    }
}
