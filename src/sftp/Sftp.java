package sftp;

import com.jcraft.jsch.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

public class Sftp {

    private Session session = null;
    private Channel channel1 = null;
    private Channel channel2 = null;
    private ChannelSftp channelSftp = null;
    private ChannelExec channelExec = null;

    /**
     * SFTP 초기화
     * */
    public void init () {
        JSch jsch = new JSch();
        Properties prop = CommonProps.getInstance();
        String usrNm = prop.getProperty("COM.USER_NAME");
        String host = prop.getProperty("COM.IP");
        String port = prop.getProperty("COM.PORT");
        String passwd = prop.getProperty("COM.PASSWORD");

        try {

            session = jsch.getSession(usrNm, host, Integer.parseInt(port));
            session.setPassword(passwd);
            session.setConfig("StrictHostKeyChecking", "no"); /* 이걸 안하면 왜 접속이 안될 까?? ==> 접속 키 확인에 대한 속성*/
            session.connect();

            channel1 = session.openChannel("sftp");
            channel1.connect();
            channelSftp = (ChannelSftp) channel1;

            channel2 = session.openChannel("exec");
            channel2.connect();
            channelExec = (ChannelExec) channel2;

        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    /**
     * 디렉토리변경, path 없으면 홈디렉토리
     * */
    public void changeDir(String path) {
        try {
            if (path.length() == 0) {
                path = channelSftp.getHome();
            }
            channelSftp.cd(path);
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    /**
     * 현재디렉토리확인
     * */
    public void getWorkDir() {
        try {
            System.out.println(channelSftp.pwd());
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    /**
     * path의 파일리스트 확인, 없으면 현재 경로의 파일 리스트
     * */
    public void getFileList(String path) {
        if (path.length() == 0) {
            try {
                path = channelSftp.pwd();
            } catch (SftpException e) {
                e.printStackTrace();
            }
        }
        try {
            Vector<ChannelSftp.LsEntry> fileList= channelSftp.ls(path);
            Iterator<ChannelSftp.LsEntry> it = fileList.iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    /**파일 생성*/
    public void makeFile (String pFile, String pPath) {
        String now = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File file = new File(pPath+now+"_"+pFile+".text");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(now +" 테스트입니다.");
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**파일 업로드*/
    public void uploadFile (String pPath, String pFile, String toPath) {
        File file = new File(pPath+pFile+".text");

    }

    /**파일 다운로드*/
    public void downloadFile (String pFile, String pPath) {
        File file = new File(pPath+pFile+".text");

    }

    /**파일 이동*/
    public void moveFile (File file, String bfPath, String afPath) {
    }
    /**파일 복사*/
    public void copyFile (File file, String bfPath, String afPath) {
    }
    /**파일 삭제*/
    public void removeFile (File file, String Path) {
    }

}
