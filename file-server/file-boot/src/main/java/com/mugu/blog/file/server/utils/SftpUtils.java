package com.mugu.blog.file.server.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.mugu.blog.file.server.model.FtpProperties;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

public class SftpUtils {

    @SneakyThrows
    private static Session getSession(String host, int port, String username,
                                      String password) {
        JSch jsch = new JSch();
        Session sshSession = jsch.getSession(username, host, port);
        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.connect();
        return sshSession;
    }

    @SneakyThrows
    private static ChannelSftp getChannel(Session session) {
        Channel channel = session.openChannel("sftp");
        channel.connect();
        return (ChannelSftp) channel;
    }

    /**
     * 上传文件
     * @param stream  文件流
     * @param dest 目标文件
     */
    @SneakyThrows
    public static void upload(InputStream stream, String dest) {
        Session session=null;
        ChannelSftp sftp=null;
        try {
            FtpProperties ftpProperties = ApplicationContextUtil.getApplicationContext().getBean(FtpProperties.class);
            session = getSession(ftpProperties.getHost(), Optional.ofNullable(ftpProperties.getPort()).orElse(22), ftpProperties.getUsername(), ftpProperties.getPassword());
            sftp=getChannel(session);
            sftp.put(stream,dest);
        } finally {
            Objects.requireNonNull(sftp).exit();
            Objects.requireNonNull(session).disconnect();
        }
    }
}
