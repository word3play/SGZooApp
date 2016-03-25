package com.konakart.app;

import com.konakart.appif.*;

/**
 *  The KonaKart Custom Engine - GetCouponPerId - Generated by CreateKKCustomEng
 */
@SuppressWarnings("all")
public class GetCouponPerId
{
    KKEng kkEng = null;

    /**
     * Constructor
     */
     public GetCouponPerId(KKEng _kkEng)
     {
         kkEng = _kkEng;
     }

     public CouponIf getCouponPerId(int couponId, CouponOptionsIf options) throws KKException
     {
         return kkEng.getCouponPerId(couponId, options);
     }
}
