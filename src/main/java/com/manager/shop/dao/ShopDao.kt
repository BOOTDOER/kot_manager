package com.manager.shop.dao

import com.manager.shop.model.Shop

interface ShopDao {

    fun get(shopId: Long): Shop

}
