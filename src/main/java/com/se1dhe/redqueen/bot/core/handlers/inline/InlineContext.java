/*
 * Copyright (c) 2017 Rumen Nikiforov <unafraid89@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.se1dhe.redqueen.bot.core.handlers.inline;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author UnAfraid
 */
public class InlineContext {
    private final Map<Long, InlineUserData> usersData = new ConcurrentHashMap<>();

    /**
     * Returns user data by user id
     *
     * @param id user id
     * @return the inline user data
     */
    public InlineUserData getUserData(long id) {
        return usersData.computeIfAbsent(id, InlineUserData::new);
    }

    /**
     * Removes data by user id
     *
     * @param id the user id
     * @return whether the user data was removed or not
     */
    public boolean clear(long id) {
        return usersData.remove(id) != null;
    }
}