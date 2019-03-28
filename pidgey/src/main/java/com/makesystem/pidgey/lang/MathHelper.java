/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.lang;

/**
 *
 * @author Richeli.vargas
 */
public class MathHelper {

    public final static long divide(final long valueOne, final long valueTwo) {
        if (valueOne == 0 || valueTwo == 0) {
            return 0;
        }
        return valueOne / valueTwo;
    }

    public final static long calcDifference(final long valueOne, final long valueTwo) {
        return valueOne > valueTwo ? valueOne - valueTwo : valueTwo - valueOne;
    }

}
