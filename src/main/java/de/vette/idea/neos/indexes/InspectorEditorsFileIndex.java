package de.vette.idea.neos.indexes;

import com.intellij.psi.PsiFile;
import com.intellij.util.indexing.*;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import de.vette.idea.neos.NeosProjectComponent;
import de.vette.idea.neos.indexes.externalizer.StringDataExternalizer;
import de.vette.idea.neos.nodetypes.util.InspectorEditorsUtil;
import gnu.trove.THashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLFileType;

import java.util.Map;

public class InspectorEditorsFileIndex extends FileBasedIndexExtension<String, String> {
    private final KeyDescriptor<String> myKeyDescriptor = new EnumeratorStringDescriptor();
    public static final ID<String, String> KEY = ID.create("de.vette.idea.neos.inspector_editors");
    private static StringDataExternalizer EXTERNALIZER = new StringDataExternalizer();
    private static int MAX_FILE_BYTE_SIZE = 5242880;

    @NotNull
    @Override
    public FileBasedIndex.InputFilter getInputFilter() {
        return virtualFile -> virtualFile.getFileType() == YAMLFileType.YML;
    }

    @Override
    public boolean dependsOnFileContent() {
        return true;
    }

    @NotNull
    @Override
    public ID<String, String> getName() {
        return KEY;
    }

    @NotNull
    @Override
    public DataIndexer<String, String, FileContent> getIndexer() {
        return fileContent -> {
            Map<String, String> map = new THashMap<>();
            PsiFile psiFile = fileContent.getPsiFile();
            if (!NeosProjectComponent.isEnabledForIndex(psiFile.getProject())
                || !isValidForIndex(fileContent, psiFile)) {
                return map;
            }

            return InspectorEditorsUtil.getEditorsInFile(psiFile);
        };
    }

    public static boolean isValidForIndex(FileContent inputData, PsiFile psiFile) {

        if (inputData.getFile().getLength() > MAX_FILE_BYTE_SIZE) {
            return false;
        }

        return psiFile.getName().startsWith("Settings.");
    }

    @NotNull
    @Override
    public KeyDescriptor<String> getKeyDescriptor() {
        return this.myKeyDescriptor;
    }

    @NotNull
    @Override
    public DataExternalizer<String> getValueExternalizer() {
        return EXTERNALIZER;
    }

    @Override
    public int getVersion() {
        return 1;
    }
}
