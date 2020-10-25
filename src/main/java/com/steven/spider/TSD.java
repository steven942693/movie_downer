package com.steven.spider;

import com.steven.view.TSDView;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;


public class TSD implements Callable<Boolean> {
    String section_url,  file_name;

    public TSD(String section_url, String file_name) {
        this.section_url = section_url;
        this.file_name = file_name;
    }

    public TSD() {
    }

    @Override
    public Boolean call() throws IOException {
        return tsDownLoad(section_url,file_name);
    }

    public boolean tsDownLoad(String section_url, String file_name) throws IOException {
        CloseableHttpClient movie_main_httpClient = HttpClients.createDefault();
        HttpGet movie_main_get = new HttpGet(section_url);
        movie_main_get.setHeader(":authority", "douban.donghongzuida.com");
        movie_main_get.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36 Edg/85.0.564.68");
        movie_main_get.setHeader("origin", "http://jx.qinian.cc");
        movie_main_get.setHeader("accept-encoding", "gzip, deflate, br");
        movie_main_get.setHeader(":method", "GET");
        movie_main_get.setHeader("accept", "*/*");
        movie_main_get.setHeader("accept-language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        movie_main_get.setHeader("sec-fetch-mode", "cors");
        movie_main_get.setHeader("sec-fetch-site", "cross-site");
//        movie_main_get.setHeader("sec-fetch-mode","cors");
        CloseableHttpResponse movie_main_response = null;
        try {
            movie_main_response = movie_main_httpClient.execute(movie_main_get);
        }catch (Exception e){
            System.err.println(section_url+"\t"+file_name);
//            MovieSpider.failure_map.put(section_url,file_name);
            System.out.println("========================="+file_name+"下载重试=========================");
            tsDownLoad(section_url,file_name);
//            return false;
        }

        CloseableHttpResponse finalMovie_main_response = movie_main_response;
        new Thread(){
                @Override
                public void run() {
                    try {
                        if (finalMovie_main_response.getStatusLine().getStatusCode() == 200) {
                            HttpEntity entity = finalMovie_main_response.getEntity();
                            FileOutputStream fos = new FileOutputStream(MovieSpider.save_dir + "\\" + file_name);
                            entity.writeTo(fos);
                            fos.flush();
                            fos.close();
                        }
                    }catch(IOException e){
//                            e.printStackTrace();
                        System.out.println("写出异常========================="+file_name+"下载重试=========================");
                        try {
                            tsDownLoad(section_url,file_name);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }

                        File[] downloaded_files = MovieSpider.save_dir_file.listFiles();
                        int percent = (int) (100.0 * downloaded_files.length / (MovieSpider.total_page + 1));
                        TSDView.bar.setValue(percent);
//                    System.out.println(Thread.currentThread().getName()+"============================" + file_name + "\t下载成功============================");
                    }
            }.start();

        return false;
    }
}
