package com.su.hresource.util;

import com.alibaba.fastjson.JSON;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 从CFX粘贴过来 好像暂时还用不着
 * httpUtil 发送http请求
 * @author tianyu
 * @date 2020年5月20日09:13:22
 * */
public class HttpClientUtil {

    /**
     * post
     * @param uri
     * @param charset
     * @return
     */
    public static String sendPost(String uri, String charset, String bodyData, Map<String, String> headerMap) {
        String result = null;
        PrintWriter out = null;
        InputStream in = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.setDoInput(true);
            urlcon.setDoOutput(true);
            urlcon.setUseCaches(false);
            urlcon.setRequestMethod("POST");
            if (!headerMap.isEmpty()){
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    System.out.println("headerMap内：===>"+entry.getKey()+"<-==->"+entry.getValue());
                    urlcon.setRequestProperty(entry.getKey(),entry.getValue());
                }
            }
            // 获取连接
            urlcon.connect();
            out = new PrintWriter(urlcon.getOutputStream());
            //请求体里的内容转成json用输出流发送到目标地址
            out.print(bodyData);
            out.flush();
            in = urlcon.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(in, charset));
            StringBuffer bs = new StringBuffer();
            String line = null;
            while ((line = buffer.readLine()) != null) {
                bs.append(line);
            }
            result = bs.toString();
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("[请求异常][地址：" + uri + "][错误信息："+ e.getMessage() + "]");
        } finally {
            try {
                if (null != in){
                    in.close();
                }
                if (null != out){
                    out.close();
                }
            } catch (Exception e2) {
                System.out.println("[关闭流异常][错误信息：" + e2.getMessage() + "]");
            }
        }
        return result;
    }

    public static String sendGet(String uri, String charset, String bodyData, Map<String, String> headerMap) {
        String result = null;
        PrintWriter out = null;
        InputStream in = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.setDoInput(true);
            urlcon.setDoOutput(true);
            urlcon.setUseCaches(false);
            urlcon.setRequestMethod("GET");
            if (!headerMap.isEmpty()){
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    System.out.println("headerMap内：===>"+entry.getKey()+"<-==->"+entry.getValue());
                    urlcon.setRequestProperty(entry.getKey(),entry.getValue());
                }
            }
            // 获取连接
            urlcon.connect();
            out = new PrintWriter(urlcon.getOutputStream());
            //请求体里的内容转成json用输出流发送到目标地址
            out.print(bodyData);
            out.flush();
            in = urlcon.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(in, charset));
            StringBuffer bs = new StringBuffer();
            String line = null;
            while ((line = buffer.readLine()) != null) {
                bs.append(line);
            }
            result = bs.toString();
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("[请求异常][地址：" + uri + "][错误信息："+ e.getMessage() + "]");
        } finally {
            try {
                if (null != in){
                    in.close();
                }
                if (null != out){
                    out.close();
                }
            } catch (Exception e2) {
                System.out.println("[关闭流异常][错误信息：" + e2.getMessage() + "]");
            }
        }
        return result;
    }

    /**
     * 封装一个getpost通用的 头参数就在url里一起拿过来 body 就outputStr
     * */
    public static String httpsAllRequest(String requestUrl, String requestMethod, String outputStr) {
        String result = "";
        try {
//            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
//            TrustManager[] tm = { new MyX509TrustManager() };
//            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
//            sslContext.init(null, tm, new java.security.SecureRandom());
//            // 从上述SSLContext对象中得到SSLSocketFactory对象
//            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            result = buffer.toString();

        } catch (ConnectException ce) {
            //log.error("连接超时：{}", ce);
            ce.printStackTrace();
        } catch (Exception e) {
            ///  log.error("https请求异常：{}", e);
            e.printStackTrace();
        }
    return result;
}

    public static String send(String uri, String charset, HashMap headerMap, HashMap bodyMap){
        String bodyData = JSON.toJSONString(bodyMap);
        System.out.println("bodyData-==>"+bodyData);
        return sendPost(uri,charset,bodyData,headerMap);
    }
}