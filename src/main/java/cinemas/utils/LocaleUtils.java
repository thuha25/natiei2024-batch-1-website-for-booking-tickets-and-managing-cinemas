package cinemas.utils;

import org.springframework.context.i18n.LocaleContextHolder;

public class LocaleUtils {
    public static String getTextWithLocale(String textVi, String textEn) {
        var locale = LocaleContextHolder.getLocale();
        if (locale.getLanguage().equals("vi") || DataTypeUtils.isNullOrBlank(textEn)) {
            return textVi;
        }
        return textEn;
    }
}
