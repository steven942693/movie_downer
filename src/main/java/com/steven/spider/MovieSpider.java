package com.steven.spider;

import com.steven.view.TSDView;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MovieSpider {
    public static TreeMap<String, String> failure_map = new TreeMap<>();
    public static boolean is_PJ;
//    public static int already_complete = 0;
    public static File save_dir_file;
    public static String save_dir;
    public static String section_url;
    public static int current_page;
    public static int total_page;
    public static void spider() throws IOException, ExecutionException, InterruptedException {
        save_dir = "D:\\spider\\" + TSDView.video_name_field.getText();
        section_url = TSDView.textArea.getText();
        current_page = Integer.parseInt(TSDView.start_page.getText());
        total_page = Integer.parseInt(TSDView.end_page.getText());
        int nums_th = Integer.parseInt(TSDView.nums_th_input.getText());
        is_PJ = TSDView.radio_true.isSelected() ? true : false;
        save_dir_file = new File(save_dir);
        if (!save_dir_file.exists()) {
            save_dir_file.mkdirs();
        }
        ExecutorService pool = null;
        if (total_page < 100) {
            pool = Executors.newCachedThreadPool();
        }else {
            pool = Executors.newFixedThreadPool(nums_th);
        }
        TreeMap<String, String> url_map = createURL();
//        ExecutorService pool = Executors.newCachedThreadPool();
        ArrayList<Future<Boolean>> futures = new ArrayList<>();
        for (Map.Entry<String, String> entry : url_map.entrySet()) {
//            System.out.println(entry.getKey()+"\t"+entry.getValue());
            Future<Boolean> submit = pool.submit(new TSD(entry.getKey(), entry.getValue()));
            futures.add(submit);
        }

    }

    public static TreeMap<String, String> createURL() {
        TreeMap<String, String> url_outName = new TreeMap<>();
        int total_len = (total_page + "").length();
//            String section_url = "https://douban.donghongzuida.com/20200904/8949_4866155f/1000k/hls/50fe07cb477000000.ts";
        String pre = section_url.substring(0, section_url.length() - total_len - 3);
        for (; current_page <= total_page; ) {
            int current_len = (current_page + "").length();
            String prefix_0 = "";
            String complete_url = "";
            if (is_PJ) {
                for (int i = 1; i <= total_len - current_len; i++) {
                    prefix_0 += "0";
                }
                complete_url = pre + prefix_0 + current_page + ".ts";
            } else {
                complete_url = pre + prefix_0 + current_page + ".ts";
                for (int i = 1; i <= total_len - current_len; i++) {
                    prefix_0 += "0";
                }
            }

            String out_name = prefix_0 + current_page + ".ts";
            current_page++;
            url_outName.put(complete_url, out_name);
        }
        return url_outName;
    }
}
