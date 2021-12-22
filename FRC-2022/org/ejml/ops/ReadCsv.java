/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.ops;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class ReadCsv {
    private boolean hasComment = false;
    private char comment;
    private final BufferedReader in;
    private int lineNumber = 0;

    public ReadCsv(InputStream in) {
        this.in = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    public void setComment(char comment) {
        this.hasComment = true;
        this.comment = comment;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public BufferedReader getReader() {
        return this.in;
    }

    @Nullable
    protected List<String> extractWords() throws IOException {
        String line;
        do {
            ++this.lineNumber;
            line = this.in.readLine();
            if (line != null) continue;
            return null;
        } while (this.hasComment && line.charAt(0) == this.comment);
        return this.parseWords(line);
    }

    protected List<String> parseWords(String line) {
        ArrayList<String> words = new ArrayList<String>();
        boolean insideWord = !this.isSpace(line.charAt(0));
        int last = 0;
        for (int i = 0; i < line.length(); ++i) {
            char c = line.charAt(i);
            if (insideWord) {
                if (!this.isSpace(c)) continue;
                words.add(line.substring(last, i));
                insideWord = false;
                continue;
            }
            if (this.isSpace(c)) continue;
            last = i;
            insideWord = true;
        }
        if (insideWord) {
            words.add(line.substring(last));
        }
        return words;
    }

    private boolean isSpace(char c) {
        return c == ' ' || c == '\t';
    }
}

