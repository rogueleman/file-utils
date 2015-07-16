package org.leman.free.file.utils;

import java.util.ArrayList;
import java.util.List;

public enum RenamingType {
    LOWERCASE_ALL("lower"),
    UPPERCASE_ALL("upper"),
    SWITCH_POSITION_BETWEEN_FIRST_DASH("dash"),
    REPLACE_SPACES_WITH_UNDERSCORES("underscore"),
    REPLACE_UNDERSCORES_WITH_SPACES("space");

    private final String value;

    private RenamingType(String value) {
        this.value = value;
    }

    public static RenamingType getByValue(String userAttribute) {
        for (RenamingType t : RenamingType.values())
            if (t.getValue().equalsIgnoreCase(userAttribute))
                return t;
        throw new EnumConstantNotPresentException(RenamingType.class, userAttribute);
    }

    public String getValue() {
        return value;
    }

    public static List<RenamingType> getRenamingTypesConstants(String renamingType) {
        final List<RenamingType> renamingTypes = new ArrayList<>();
        renamingTypes.add(RenamingType.getByValue(renamingType));
        return renamingTypes;
    }


}
