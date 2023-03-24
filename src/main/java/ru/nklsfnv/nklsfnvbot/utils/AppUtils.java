package ru.nklsfnv.nklsfnvbot.utils;

import io.micrometer.common.util.StringUtils;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class AppUtils {

    public static String getFullName(String firstName, String lastName) {
        return Stream.of(firstName, lastName).filter(StringUtils::isNotEmpty).collect(Collectors.joining(" "));
    }

}
