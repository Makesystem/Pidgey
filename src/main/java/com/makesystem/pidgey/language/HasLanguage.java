/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.language;

import java.io.Serializable;

/**
 *
 * @author Richeli Vargas
 */
public interface HasLanguage extends Serializable {

    public String[] getMonthsFull();

    public String[] getMonthsShort();

    public String[] getWeekdaysFull();

    public String[] getWeekdaysShort();

    public String[] getWeekdaysLetter();

}
