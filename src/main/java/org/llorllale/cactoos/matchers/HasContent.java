/*
 * The MIT License (MIT)
 *
 * Copyright (c) for portions of project cactoos-matchers are held by
 * Yegor Bugayenko, 2017-2018, as part of project cactoos.
 * All other copyright for project cactoos-matchers are held by
 * George Aristy, 2018-2020.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.llorllale.cactoos.matchers;

import org.cactoos.Input;
import org.cactoos.Text;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.hamcrest.Matcher;

/**
 * Matcher for the input.
 * @since 0.11
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class HasContent extends MatcherEnvelope<Input> {

    /**
     * Ctor.
     * @param text The text to match against
     */
    public HasContent(final String text) {
        this(new TextOf(text));
    }

    /**
     * Ctor.
     * @param text The text to match against
     * @todo #165:30min We need an InputMatcher similar to {@link TextMatcher} that would
     *  be in charge of caching the value of the {@link Input} so that it is ready only once
     *  per invocation of the {@link Matcher}.
     */
    public HasContent(final Text text) {
        this(
            new MatcherOf<>(
                input -> text.asString().equals(input),
                desc -> desc.appendText(
                    new FormattedText("\"%s\"", text).asString()
                ),
                (act, desc) -> desc
                    .appendText("has content ")
                    .appendValue(act)
            )
        );
    }

    /**
     * Ctor.
     * @param mtr Matcher of the text
     */
    public HasContent(final Matcher<String> mtr) {
        super(
            new MatcherOf<>(
                input -> mtr.matches(new TextOf(input).asString()),
                desc -> desc
                    .appendText("has content ")
                    .appendDescriptionOf(mtr),
                (input, desc) -> mtr.describeMismatch(
                    new TextOf(input).toString(),
                    desc
                )
            )
        );
    }
}
