package com.konakart.app;

/**
 *  The KonaKart Custom Engine - DeleteCustomerTag - Generated by CreateKKCustomEng
 */
@SuppressWarnings("all")
public class DeleteCustomerTag
{
    KKEng kkEng = null;

    /**
     * Constructor
     */
     public DeleteCustomerTag(KKEng _kkEng)
     {
         kkEng = _kkEng;
     }

     public void deleteCustomerTag(String sessionId, String tagName) throws KKException
     {
         kkEng.deleteCustomerTag(sessionId, tagName);
     }
}
