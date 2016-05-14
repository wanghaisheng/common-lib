package com.clear.dao;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by chengweisen on 2015/5/1.
 */
public class CreateNative {

    public static void createMdNativ(String filePath) {
        File mdfile = new File(filePath + File.separatorChar + "nav.md");
        if (!mdfile.exists()) {
            try {
                mdfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileUtils.write(mdfile, "[TOC]\n\n********\n", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(filePath);
        createList(file, mdfile);
    }

    private static void createList(File fileStr, File mdfile) {
        File[] files = fileStr.listFiles();
        for (File i : files) {
            if (i.isFile()) {
                try {
                    FileUtils.write(mdfile, "[" + i.getName() + "](" + i.getAbsolutePath() + ")\n", "utf-8", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    FileUtils.write(mdfile, "# " + i.getName() + "\n", "utf-8", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                createList(i, mdfile);
            }
        }
    }

}
