package com.bvc.ordering.ksnet

data class KSNETResponseData(
    val approvalNumber: String,
    val approvalDate: String,
    val approvalTime: String,
    val amount: String,
    val installment: String,
    val cardCompany: String,
    val cardBin: String,
    val cardType: String,
    val storeId: String,
    val storeName: String,
    val responseCode: String,
    val entryMode: String,
    val originalApprovalDate: String,
    val originalApprovalNumber: String,
    val pointAmount: String,
    val cashbackAmount: String,
    val cashReceiptNo: String,
    val vanTransactionNo: String,
) {
    companion object {
        fun from(recvByte: ByteArray): KSNETResponseData {
            fun bytes(
                start: Int,
                end: Int,
            ) = String(recvByte.copyOfRange(start, end)).trim()

            return KSNETResponseData(
                approvalNumber = bytes(94, 106),
                approvalDate = bytes(49, 55),
                approvalTime = bytes(55, 61),
                amount = bytes(109, 121).trimStart('0'),
                installment =
                    when (val i = bytes(45, 47)) {
                        "00" -> "일시불"
                        else -> "$i 개월"
                    },
                cardCompany = bytes(167, 187),
                cardBin = bytes(147, 157),
                cardType =
                    when (bytes(180, 181)) {
                        "C" -> "신용카드"
                        "D" -> "체크카드"
                        "P" -> "선불카드"
                        else -> "기타"
                    },
                storeId = bytes(13, 23),
                storeName = bytes(187, 227),
                responseCode =
                    when (val code = bytes(93, 94)) {
                        "O" -> "정상승인"
                        else -> "오류 ($code)"
                    },
                entryMode = bytes(33, 36),
                originalApprovalDate = bytes(121, 127),
                originalApprovalNumber = bytes(127, 139),
                pointAmount = bytes(231, 243).trimStart('0'),
                cashbackAmount = bytes(243, 255).trimStart('0'),
                cashReceiptNo = bytes(291, 313),
                vanTransactionNo = bytes(327, 349),
            )
        }
    }
}
