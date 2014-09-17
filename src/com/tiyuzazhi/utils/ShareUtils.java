package com.tiyuzazhi.utils;

import cn.sharesdk.douban.Douban;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.renren.Renren;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @author chris.xue
 *         分享模块
 */
public class ShareUtils {
    public static String[] iconName = {"QQ", "人人", "微信", "微博", "QQ空间", "豆瓣"};
    public static int[] iconArray = {};

    public static void share(int platformId, String content, PlatformActionListener platformActionListener) {
        switch (platformId) {
            case 0:
                QQ.ShareParams qqsp = new QQ.ShareParams();
                qqsp.setTitle("");
                qqsp.setText(content);
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.setPlatformActionListener(platformActionListener); // 设置分享事件回调
                qq.share(qqsp);
                break;
            case 1:
                Renren.ShareParams renrensp = new Renren.ShareParams();
                renrensp.setTitle("");
                renrensp.setText(content);
                Platform renren = ShareSDK.getPlatform(Renren.NAME);
                renren.setPlatformActionListener(platformActionListener); // 设置分享事件回调
                renren.share(renrensp);
                break;
            case 2:
                Wechat.ShareParams wechatsp = new Wechat.ShareParams();
                wechatsp.setTitle("");
                wechatsp.setText(content);
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                wechat.setPlatformActionListener(platformActionListener); // 设置分享事件回调
                wechat.share(wechatsp);
                break;
            case 3:
                SinaWeibo.ShareParams weibosp = new SinaWeibo.ShareParams();
                weibosp.setTitle("");
                weibosp.setText(content);
                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                weibo.setPlatformActionListener(platformActionListener); // 设置分享事件回调
                weibo.share(weibosp);
                break;
            case 4:
                QZone.ShareParams qzonesp = new QZone.ShareParams();
                qzonesp.setTitle("");
                qzonesp.setText(content);
                Platform qzone = ShareSDK.getPlatform(QZone.NAME);
                qzone.setPlatformActionListener(platformActionListener); // 设置分享事件回调
                qzone.share(qzonesp);
                break;
            case 5:
                Douban.ShareParams doubansp = new Douban.ShareParams();
                doubansp.setTitle("");
                doubansp.setText(content);
                Platform douban = ShareSDK.getPlatform(Douban.NAME);
                douban.setPlatformActionListener(platformActionListener); // 设置分享事件回调
                douban.share(doubansp);
                break;
            default:
                ToastUtils.show("未知的分享平台");
                break;
        }
    }


}
