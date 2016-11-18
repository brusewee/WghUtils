package wgh.com.wghutils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/11/18.
 */
public class HttpUtils {

    /**
     *
     *
     * @param url  传入地址
     * @param content  传入post的内容
     * @param encoding  选择编码的方式
     * @return
     */
    public static String HttpPost(String url, String content, String encoding) {
        HttpURLConnection conn=null;
        String str="";
        try {
            conn=(HttpURLConnection)new URL(url).openConnection();
            conn.setDoInput(true);//打开输入流,以便从服务器获取数据
            conn.setDoOutput(true);//打开输出流,以便向服务器提交数据
            conn.setConnectTimeout(3000);//设置连接超时时间
            conn.setReadTimeout(3000);//设置返回超时时间,下面要对超时进行处理
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);// 使用Post方式不能使用缓存
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.connect();
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(content);
            out.flush();
            out.close();
            int response = conn.getResponseCode(); // 获得服务器的响应码
            if (response == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    line = new String(line.getBytes(), "UTF-8");
                    sb.append(line);
                }
                str = sb.toString();

            }
        } catch (IOException e) {
            e.printStackTrace();
            //里面会抛连接和返回超时java.net.SocketTimeoutException，还有IO异常
            return "faild";
        }finally {
            conn.disconnect();
            conn = null;
        }
        return str;

       
    }
}
