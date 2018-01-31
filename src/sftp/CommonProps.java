package sftp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class CommonProps {

    private static CommonProps commonProps = new CommonProps();
    private static Properties props;

    private CommonProps () {
        String filePath = "D:\\allSharpFnc\\workspace\\FtpClient\\src\\sftp\\config.properties";
        File file = new File(filePath);
        try (BufferedInputStream bi = new BufferedInputStream(new FileInputStream(file))) {
            props = new Properties();
            props.load(bi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Properties getInstance () {
        return props;
    }

}
