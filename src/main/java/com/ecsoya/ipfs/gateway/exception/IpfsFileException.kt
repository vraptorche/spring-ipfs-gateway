package com.ecsoya.ipfs.gateway.exception

class IpfsFileException : RuntimeException {
    constructor() {}
    constructor(arg0: String?) : super(arg0) {}
    constructor(arg0: Throwable?) : super(arg0) {}
    constructor(arg0: String?, arg1: Throwable?) : super(arg0, arg1) {}
    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(
        message,
        cause,
        enableSuppression,
        writableStackTrace
    ) {
    }

    companion object {
        /**
         *
         */
        private const val serialVersionUID = -6639360221203906349L
    }
}