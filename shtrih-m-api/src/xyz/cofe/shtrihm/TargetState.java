/*
 * The MIT License
 *
 * Copyright 2017 user.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package xyz.cofe.shtrihm;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Описывает состояние (режим) в который перейдет аппарат
 * @author Kamnev Georgiy nt.gocha@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetState {
    /**
     * Перечень состояний.
     * <p>
     * "*" - в любой
     * <p>
     * "" - в любой
     * <p>
     * "!6" - в ECRMode.getMode()!=6
     * <p>
     * "7" - в ECRMode.getMode()=7
     * <p>
     * "8.1" - в ECRMode.getMode()=8 ECRMode.getMode8Status()=1
     * <p>
     * "*.2" - ECRMode.getMode8Status()=2
     * <p>
     * "!*.3" - ECRMode.getMode8Status()!=3
     * <p>
     * "*.*.4" - ECRMode.getAdvancedMode()=4
     * <p>
     * "!*.*.5" - ECRMode.getAdvancedMode()!=5
     * <p>
     * "?" - Куда-то переводит
     * <p>
     * "8.2|9|1" - в ECRMode.getMode()=8 ECRMode.getMode8Status()=2 или ECRMode.getMode()=9 или ECRMode.getMode()=1
     * <p>
     * "!8.2&amp;!9&amp;!1" - в !(mode=8 mode8status=2) и !(mode=9) и !(m=1)
     * @return перечень состояний
     */
    String state() default "";
}
