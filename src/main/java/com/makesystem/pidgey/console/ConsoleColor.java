/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.console;

/**
 *
 * @author Richeli.vargas
 */
public enum ConsoleColor {
    
    // Reset
    //RESET("0m"), // Text Reset
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
    BLACK_BACKGROUND("\033[40m"), // BLACK
    RED_BACKGROUND("\033[41m"), // RED
    GREEN_BACKGROUND("\033[42m"), // GREEN
    YELLOW_BACKGROUND("\033[43m"), // YELLOW
    BLUE_BACKGROUND("\033[44m"), // BLUE
    PURPLE_BACKGROUND("\033[45m"), // PURPLE
    CYAN_BACKGROUND("\033[46m"), // CYAN
    WHITE_BACKGROUND("\033[47m"); // WHITE

    private static final String JRE_TAG = "\033[0;";
    private static final String JS_TAG = "\\x1b[";
    
    private final String color;

    private ConsoleColor(final String color) {
        this.color = color;
    }

    @Deprecated
    public String getColor(){
        return forJRE();
    }
    
    public String forJRE() {
        return JRE_TAG + color;
    }
    
    public String forBrowser() {
        return JS_TAG + color;
    }
}
