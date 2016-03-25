//
// (c) 2006 DS Data Systems UK Ltd, All rights reserved.
//
// DS Data Systems and KonaKart and their respective logos, are 
// trademarks of DS Data Systems UK Ltd. All rights reserved.
//
// The information in this document is free software; you can redistribute 
// it and/or modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// 
// This software is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
package com.konakart.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.konakart.al.KKAppEng;
import com.konakart.appif.ContentIf;
import com.konakart.util.KKConstants;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import javax.servlet.http.Part;

/**
 * Gets called when sending mail
 */
public class SendEmailAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private String host;
    private String port;
    private String toAddr;
    private String username;
    private String password;
    private String contactUsContent;
    private String from = "";
    private String subject = "";
    private String body = "";
    private List<File> upload = new ArrayList<File>();
    private List<String> uploadFileName = new ArrayList<String>();
    private List<String> uploadContentType = new ArrayList<String>();
    private String resultMessage = "";

    public String execute() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        System.out.println("IN1");

        try {
            int custId;

            KKAppEng kkAppEng = this.getKKAppEng(request, response);

            custId = this.loggedIn(request, response, kkAppEng, null);

            System.out.println("IN");
            // Force the user to login if configured to do so
            if (custId < 0 && kkAppEng.isForceLogin()) {
                return KKLOGIN;
            }

            // Ensure we are using the correct protocol. Redirect if not.
            String redirForward = checkSSL(kkAppEng, request, custId, /* forceSSL */ false);
            if (redirForward != null) {
                setupResponseForSSLRedirect(response, redirForward);
                return null;
            }

//            System.out.println("HERE YOOO");
//            System.out.println(ServletActionContext.getServletContext().getRealPath("/"));
            // Get the content
            ContentIf[] content = null;

            if (kkAppEng.getContentMgr().isEnabled()) {
                content = kkAppEng.getContentMgr().getContentForId(1, KKConstants.CONTENTID_CONTACT);
            }

            if (content != null && content.length > 0) {
                contactUsContent = content[0].getDescription().getContent();
            } else {
                contactUsContent = kkAppEng.getMsg("common.add.info");
            }

            kkAppEng.getNav().set(kkAppEng.getMsg("header.contact.us"), request);
            EmailUtility.sendEmailWithAttachment(getHost(), getPort(), getFrom(), getToAddr(),
                    getSubject(), getBody(), getUpload(), getUsername(), getPassword());

            System.out.println("HERE");
            resultMessage = "The e-mail was sent successfully";
            System.out.println(resultMessage);

            return SUCCESS;

        } catch (Exception e) {
            resultMessage = "Error: unable to send message....";
            System.out.println(resultMessage);
            return super.handleException(request, e);
//        } finally {
//            deleteUploadFiles(uploadedFiles);
        }
    }

    /**
     * @return the contactUsContent
     */
    public String getContactUsContent() {
        return contactUsContent;
    }

    /**
     * @param contactUsContent the contactUsContent to set
     */
    public void setContactUsContent(String contactUsContent) {
        this.contactUsContent = contactUsContent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getToAddr() {
        return toAddr;
    }

    public void setToAddr(String toAddr) {
        this.toAddr = toAddr;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<File> getUpload() {
        return upload;
    }

    public void setUpload(List<File> upload) {
        this.upload = upload;
    }

    public List<String> getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(List<String> uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public List<String> getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(List<String> uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    /**
     * Saves files uploaded from the client and return a list of these files
     * which will be attached to the e-mail message.
     */
//    private List<File> saveUploadedFiles(List<File> files, List<String> fileNames) throws IOException {
//        List<File> listFiles = new ArrayList<File>();
//        byte[] buffer = new byte[4096];
//        int bytesRead = -1;
//        if (files.size() > 0) {
//            for (int i = 0; i<files.size(); i++) {
//                // creates a file to be saved
//                String fileName = fileNames.get(i);
//                if (fileName == null || fileName.equals("")) {
//                    // not attachment part, continue
//                    continue;
//                }
//
//                File saveFile = new File(fileName);
//                System.out.println("saveFile: " + saveFile.getAbsolutePath());
//                FileOutputStream outputStream = new FileOutputStream(saveFile);
//
//                // saves uploaded file
//                InputStream inputStream = new FileInputStream(saveFile);
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//                outputStream.close();
//                inputStream.close();
//
//                listFiles.add(saveFile);
//
//                FileInputStream in = null;
//                FileOutputStream out = null;
//
//                File dir = new File(filesDirectory);
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
//                String targetPath = dir.getPath() + File.separator + fileName;
//                System.out.println("source file path ::" + file.getAbsolutePath());
//                System.out.println("saving file to ::" + targetPath);
//                File destinationFile = new File(targetPath);
//
//            }

//    /**
//     * Retrieves file name of a upload part from its HTTP header
//     */
//    private String extractFileName(Part part) {
//        String contentDisp = part.getHeader("content-disposition");
//        String[] items = contentDisp.split(";");
//        for (String s : items) {
//            if (s.trim().startsWith("filename")) {
//                return s.substring(s.indexOf("=") + 2, s.length() - 1);
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Deletes all uploaded files, should be called after the e-mail was sent.
//     */
//    private void deleteUploadFiles(List<File> listFiles) {
//        if (listFiles != null && listFiles.size() > 0) {
//            for (File aFile : listFiles) {
//                aFile.delete();
//            }
//        }
//    }
        }
