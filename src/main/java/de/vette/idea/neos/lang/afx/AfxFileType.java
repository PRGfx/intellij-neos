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

package de.vette.idea.neos.lang.afx;

import com.intellij.openapi.fileTypes.LanguageFileType;
import de.vette.idea.neos.lang.fusion.FusionLanguage;
import de.vette.idea.neos.lang.fusion.icons.FusionIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Afx file type
 */
public class AfxFileType extends LanguageFileType {
    public static final LanguageFileType INSTANCE = new AfxFileType();
    public static final String DEFAULT_EXTENSION = "afx";

    private AfxFileType() {
        super(AfxLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Neos Fusion AFX";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Neos Fusion AFX file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return FusionIcons.FILE;
    }
}
