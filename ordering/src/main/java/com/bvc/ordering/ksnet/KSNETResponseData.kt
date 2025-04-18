package com.bvc.ordering.ksnet

data class KSNETResponseData(
    val storeId: String, // 가맹점 번호
    val terminalId: String, // 단말기 번호
    val transactionType: String, // 거래 구분
    val entryMode: String, // 입력방법
    val cardNumberMasked: String, // 마스킹 카드번호
    val installment: String, // 할부개월
    val approvalDate: String, // 승인일자 (YYMMDD)
    val approvalTime: String, // 승인시간 (HHMMSS)
    val approvalNumber: String, // 승인번호
    val amount: String, // 총금액
    val vat: String, // 부가세
    val serviceCharge: String, // 봉사료
    val taxFreeAmount: String, // 면세금액
    val originalApprovalDate: String, // 원거래 승인일자
    val originalApprovalNumber: String, // 원거래 승인번호
    val responseCode: String, // 응답 코드
    val cardBin: String, // 카드 BIN
    val cardType: String, // 카드 구분 (C:신용 D:체크 등)
    val cardCompany: String, // 카드사명
    val storeName: String, // 가맹점명
    val pointAmount: String, // 포인트 사용 금액
    val cashbackAmount: String, // 캐시백 사용 금액
    val cashReceiptNo: String, // 현금영수증 번호
    val vanTransactionNo: String, // VAN 거래고유번호
) {
    companion object {
        fun from(recvByte: ByteArray): KSNETResponseData {
            fun bytes(
                start: Int,
                end: Int,
            ): String = String(recvByte.copyOfRange(start, end)).trim()

            return KSNETResponseData(
                storeId = bytes(13, 23),
                terminalId = bytes(23, 33),
                transactionType = bytes(37, 39),
                entryMode = bytes(33, 36),
                cardNumberMasked = bytes(39, 45),
                installment =
                    when (val i = bytes(45, 47)) {
                        "00" -> "일시불"
                        else -> "$i 개월"
                    },
                approvalDate = bytes(49, 55),
                approvalTime = bytes(55, 61),
                approvalNumber = bytes(94, 106),
                amount = bytes(109, 121).trimStart('0'),
                vat = bytes(139, 151).trimStart('0'),
                serviceCharge = bytes(151, 163).trimStart('0'),
                taxFreeAmount = bytes(163, 175).trimStart('0'),
                originalApprovalDate = bytes(121, 127),
                originalApprovalNumber = bytes(127, 139),
                responseCode =
                    when (val code = bytes(93, 94)) {
                        "O" -> "정상승인"
                        else -> "오류 ($code)"
                    },
                cardBin = bytes(147, 157),
                cardType =
                    when (bytes(180, 181)) {
                        "C" -> "신용카드"
                        "D" -> "체크카드"
                        "P" -> "선불카드"
                        else -> "기타"
                    },
                cardCompany = bytes(167, 187),
                storeName = bytes(187, 227),
                pointAmount = bytes(231, 243).trimStart('0'),
                cashbackAmount = bytes(243, 255).trimStart('0'),
                cashReceiptNo = bytes(291, 313),
                vanTransactionNo = bytes(327, 349),
            )
        }
    }
}
