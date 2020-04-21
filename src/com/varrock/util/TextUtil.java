package com.varrock.util;

import java.util.ArrayList;
import java.util.List;

import com.varrock.client.RSFontSystem;

/**
 * Created by Jonny on 5/15/2019
 **/
public class TextUtil {

    public static String mask(String password) {
        StringBuffer stringbuffer = new StringBuffer();
        for (int index = 0; index < password.length(); index++) {
            stringbuffer.append("*");
        }
        return stringbuffer.toString();
    }

    public static String getPluralS(int amount) {
        if (amount > 1) {
            return "s";
        }
        return "";
    }

    public static void print(String text) {
        System.out.println(text);
    }

    public static String[] split(RSFontSystem font, String string, int maxWidth) {
        int width = font.getTextWidth(string);
        if (width <= maxWidth)
            return new String[] { string };
        else {
            int columns = (int) Math.ceil(((double) width) / ((double) maxWidth));
            String[] contents = new String[columns];
            int col = 0;
            for (String word : string.split(" ")) {
                int word_width = font.getTextWidth(word);
                int current_width = font.getTextWidth(contents[col]);
                if ((word_width + current_width) > maxWidth) {
                    col++;
                }
                if (col >= contents.length)
                    break;
                if (contents[col] == null)
                    contents[col] = word;
                else
                    contents[col] += " " + word;
            }
            return contents;
        }
    }

    public static String[] wrapText(RSFontSystem font, String text, int maxWidth) {
        if (text == null) {
            return new String[]{};
        }
        if (maxWidth <= 0) {
            return new String[]{
                    text
            };
        }

        if (font.getTextWidth(text) <= maxWidth && !text.contains("\\n")) {
            return new String[]{
                    text
            };
        }
        if (text.contains("\\n")) {
            final String[] splits = text.split("\\\\n");
            List<String> lines = new ArrayList<String>();
            for (int split = 0; split < splits.length; split++) {
                String[] lineArray = wrapText(font, splits[split], maxWidth);
                for (String line : lineArray) {
                    if (line == null) {
                        continue;
                    }
                    lines.add(line);
                }
            }
            return lines.toArray(new String[lines.size()]);
        }
        char[] chars = text.toCharArray();
        List<String> lines = new ArrayList<String>();
        StringBuilder line = new StringBuilder();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            word.append(chars[i]);
            if (chars[i] == ' ') {
                if (font.getTextWidth(line.toString() + word.toString()) > maxWidth) {
                    lines.add(line.toString());
                    line.delete(0, line.length());
                }
                line.append(word);
                word.delete(0, word.length());
            }
        }
        if (font.getTextWidth(word.toString()) > 0) {
            if (font.getTextWidth(line.toString() + word.toString()) > maxWidth) {
                lines.add(line.toString());
                line.delete(0, line.length());
            }
            line.append(word);
        }
        if (font.getTextWidth(line.toString()) > 0) {
            lines.add(line.toString());
        }
        return lines.toArray(new String[lines.size()]);
    }

    public static String wrapLine(RSFontSystem font, String text, int maxWidth) {
        if (text == null) {
            return "";
        }
        if (maxWidth <= 0) {
            return text;
        }

        if (font.getTextWidth(text) <= maxWidth && !text.contains("\\n")) {
            return text;
        }

        char[] chars = text.toCharArray();

        StringBuilder lines = new StringBuilder();

        StringBuilder line = new StringBuilder();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            word.append(chars[i]);
            if (chars[i] == ' ') {
                if (font.getTextWidth(line.toString() + word.toString()) > maxWidth) {
                    lines.append(line.toString()+"\\n");
                    line.delete(0, line.length());
                }
                line.append(word);
                word.delete(0, word.length());
            }
        }
        if (font.getTextWidth(word.toString()) > 0) {
            if (font.getTextWidth(line.toString() + word.toString()) > maxWidth) {
                lines.append(line.toString()+"\\n");
                line.delete(0, line.length());
            }
            line.append(word);
        }
        if (font.getTextWidth(line.toString()) > 0) {
            lines.append(line.toString()+"\\n");
        }
        return lines.toString();
    }

}
