package sftp;

public class MyApp {
    public static void main(String[] args) {
        Sftp sftp = new Sftp();
        sftp.init();
//        sftp.printServerHomePath();
        sftp.getWorkDir();
        sftp.getFileList("");
        sftp.changeDir("uploadTemp");
        sftp.getWorkDir();
        sftp.getFileList("");
        sftp.makeFile("choi", "C:\\");
//        sftp.changeDir("");

    }
}
