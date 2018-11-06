package com.manager.shop.model

import com.manager.core.model.SysUser
import java.io.Serializable

/**
 * @description 商铺
 * @author lin
 * @version 1.0
 */

data class Shop (
    var shopId: Long = 0,
    var shopName: String? = null,
    //起送价
    var shopInitialPrice: Double = 0.0,
    //快递费
    var shopCourierPrice: Double = 0.0,
    var shopImg: String? = null,
    var shopIntro: String? = null,
    //商品管理员
    var shopAdminId: Long = 0,
    var shopAdmin: SysUser? = null,
    var shopAddress: String? = null,
    //成交量
    var shopOrderCount: Long = 0,
    //店铺等级
    var shopGrade: Int = 0
) : Serializable
