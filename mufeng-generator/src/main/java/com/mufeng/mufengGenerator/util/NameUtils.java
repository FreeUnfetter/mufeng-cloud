package com.mufeng.mufengGenerator.util;


public class NameUtils {

    /**
     * 首字母小写，其他字符保持不变
     */
    public static String toLowerCaseFirstOne(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }

    /**
     * 首字母大写，其他字符保持不变
     */
    public static String toUpperCaseFirstOne(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    /**
     * 全部字母转换为小写
     */
    public static String toAllLowerCase(String s) {
        if (s == null) return null;
        return s.toLowerCase();
    }

    /**
     * 全部字母转换为大写
     */
    public static String toAllUpperCase(String s) {
        if (s == null) return null;
        return s.toUpperCase();
    }

    /**
     * 下划线命名转驼峰命名（首字母小写）
     */
    public static String underscoreToCamelCase(String s) {
        if (s == null || s.isEmpty()) return s;

        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(c));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }

        return result.toString();
    }

    /**
     * 下划线命名转驼峰命名（首字母大写）
     */
    public static String underscoreToPascalCase(String s) {
        if (s == null || s.isEmpty()) return s;

        StringBuilder result = new StringBuilder();
        boolean nextUpper = true;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(c));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }

        return result.toString();
    }

    /**
     * 驼峰命名转下划线命名（全部小写）
     */
    public static String camelCaseToUnderscore(String s) {
        if (s == null || s.isEmpty()) return s;

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    result.append('_');
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    /**
     * 智能转换：根据输入自动判断转换方式
     * 如果包含下划线 → 转驼峰（首字母小写）
     * 如果是驼峰命名 → 转下划线
     * 其他 → 首字母小写
     */
    public static String smartConvert(String s) {
        if (s == null || s.isEmpty()) return s;

        if (s.contains("_")) {
            return underscoreToCamelCase(s);
        } else if (hasUpperCase(s)) {
            return camelCaseToUnderscore(s);
        } else {
            return toLowerCaseFirstOne(s);
        }
    }

    /**
     * 检查字符串是否包含大写字母
     */
    private static boolean hasUpperCase(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }
}