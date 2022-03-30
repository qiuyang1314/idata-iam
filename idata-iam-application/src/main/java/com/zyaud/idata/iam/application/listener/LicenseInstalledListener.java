package com.zyaud.idata.iam.application.listener;
//
//
//import cn.hutool.core.date.DateUtil;
//import com.zyaud.fzhx.license.notary.LicenseData;
//import com.zyaud.fzhx.license.notary.LicenseInstalledEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.core.annotation.Order;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class LicenseInstalledListener {
//
//    /**
//     * 证书有效期
//     */
//    private static String certExpire;
//
//    @Async
//    @Order
//    @EventListener(LicenseInstalledEvent.class)
//    public void setLicenseExpire(LicenseInstalledEvent event) {
//        LicenseData licenseData = (LicenseData) event.getSource();
//        setCertExpire(licenseData.getNotBefore(), licenseData.getNotAfter());
//    }
//
//    public static void setCertExpire(Date before, Date after) {
//        certExpire = String.format("%s-%s",
//                DateUtil.format(before, "yyyy.MM.dd"),
//                DateUtil.format(after, "yyyy.MM.dd"));
//        System.out.println("license expire:" + certExpire);
//    }
//}
