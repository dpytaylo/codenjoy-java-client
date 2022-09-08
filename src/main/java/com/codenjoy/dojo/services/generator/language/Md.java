package com.codenjoy.dojo.services.generator.language;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.services.generator.Template;

import java.util.List;

public class Md implements Template {

    @Override
    public String header(List<String> locales) {
        return "<meta charset=\"UTF-8\">\n" +
                "\n" +
                "## Symbol breakdown\n" +
                "| Sprite | Code | Description |\n" +
                "| -------- | -------- | -------- |\n";
    }

    @Override
    public String line() {
        return "|<img src=\"/codenjoy-contest/resources" +
                "/${game}/sprite/${element-lower}.png\" " +
                "style=\"height:100%;\" />" +
                " | `${element}('${char}')` | ${info} | \n";
    }

    @Override
    public boolean printComment() {
        return false;
    }

    @Override
    public boolean printNewLine() {
        return false;
    }

    @Override
    public String file() {
        return "../games/${game-canonical}/src/main/webapp/resources/${game}/help/${locale}/elements.md";
    }
}
