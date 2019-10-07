/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console;

import com.makesystem.pidgey.system.Environment;

/**
 *
 * @author Richeli.vargas
 */
public enum ConsoleColor {

    // Reset
    //RESET("0m"), // Text Reset, It does not work in js console
    RESET("30m"), // Text Reset

    // Regular Color
    BLACK("30m"), // BLACK
    RED("31m"), // RED
    GREEN("32m"), // GREEN
    YELLOW("33m"), // YELLOW
    BLUE("34m"), // BLUE
    PURPLE("35m"), // PURPLE
    CYAN("36m"), // CYAN
    WHITE("37m"), // WHITE

    // Background
    BLACK_BACKGROUND("40m"), // BLACK
    RED_BACKGROUND("41m"), // RED
    GREEN_BACKGROUND("42m"), // GREEN
    YELLOW_BACKGROUND("43m"), // YELLOW
    BLUE_BACKGROUND("44m"), // BLUE
    PURPLE_BACKGROUND("45m"), // PURPLE
    CYAN_BACKGROUND("46m"), // CYAN
    WHITE_BACKGROUND("47m"); // WHITE

    private static final String JRE_TAG = "\033[";
    private static final String JS_TAG = "\\x1b[";

    private final String code;
    private final String color;

    private ConsoleColor(final String code) {
        this.code = code;
        this.color = getTag() + code;
    }

    public String getCode() {
        return code;
    }

    public String getColor() {
        return color;
    }

    String getTag() {
        switch (Environment.TYPE) {
            case GWT:
                return JS_TAG;
            case JRE:
            default:
                return JRE_TAG;
        }
    }
}
