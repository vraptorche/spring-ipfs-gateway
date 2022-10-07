package com.ecsoya.ipfs.gateway.util

object IpfsUtil {
    fun isEmpty(obj: Any?): Boolean {
        return obj == null || "" == obj
    }

    fun isNotEmpty(obj: Any?): Boolean {
        return !isEmpty(obj)
    }
}