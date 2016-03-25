package com.konakart.app;

import com.konakart.appif.*;

/**
 *  The KonaKart Custom Engine - GetDigitalDownloadById - Generated by CreateKKCustomEng
 */
@SuppressWarnings("all")
public class GetDigitalDownloadById
{
    KKEng kkEng = null;

    /**
     * Constructor
     */
     public GetDigitalDownloadById(KKEng _kkEng)
     {
         kkEng = _kkEng;
     }

     public DigitalDownloadIf getDigitalDownloadById(String sessionId, int digitalDownloadId) throws KKException
     {
         return kkEng.getDigitalDownloadById(sessionId, digitalDownloadId);
     }
}