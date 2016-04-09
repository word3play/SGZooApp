/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.konakart.actions;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.konakart.al.KKAppEng;
import com.konakart.appif.ContentIf;
import com.konakart.util.KKConstants;
/**
 *
 * @author shihui
 */
public class AnalyticsAction extends BaseAction{
     private static final long serialVersionUID = 1L;
      private String aboutUsContent;

    public String execute()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        try
        {
            int custId;

            KKAppEng kkAppEng = this.getKKAppEng(request, response);

            custId = this.loggedIn(request, response, kkAppEng, null);

            // Force the user to login if configured to do so
            if (custId < 0 && kkAppEng.isForceLogin())
            {
                return KKLOGIN;
            }

            // Ensure we are using the correct protocol. Redirect if not.
            String redirForward = checkSSL(kkAppEng, request, custId, /* forceSSL */false);
            if (redirForward != null)
            {
                setupResponseForSSLRedirect(response, redirForward);
                return null;
            }

            // Get the content
            ContentIf[] content = null;

            if (kkAppEng.getContentMgr().isEnabled())
            {
                content = kkAppEng.getContentMgr().getContentForId(1,KKConstants.CONTENTID_ABOUT_US);
            }

            if (content != null && content.length > 0)
            {
                aboutUsContent = content[0].getDescription().getContent();
            } else
            {
                aboutUsContent = kkAppEng.getMsg("common.add.info");
            }

            kkAppEng.getNav().set(kkAppEng.getMsg("header.about.us"), request);
            return SUCCESS;

        } catch (Exception e)
        {
            return super.handleException(request, e);
        }
    }

    /**
     * @return the aboutUsContent
     */
    public String getAboutUsContent()
    {
        return aboutUsContent;
    }

    /**
     * @param aboutUsContent
     *            the aboutUsContent to set
     */
    public void setAboutUsContent(String aboutUsContent)
    {
        this.aboutUsContent = aboutUsContent;
    }
}

