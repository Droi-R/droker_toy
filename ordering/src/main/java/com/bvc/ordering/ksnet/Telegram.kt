package com.bvc.ordering.ksnet

import java.nio.ByteBuffer

object Telegram {
    fun makeTelegramIC(
        apprCode: String, // "1" : 승인, "0" : 취소
        mDeviceNo: String, // 단말기번호
        quota: String, // 할부개월
        totAmt: String, // 총금액
        orgApprNo: String, // 원거래 승인번호
        orgDate: String, // 원거래 승인일자
        taxFree: String, // 면세금액
    ): ByteArray {
        val bb = ByteBuffer.allocate(4096)

        bb.put(0x02.toByte()) // STX
        bb.put("IC".toByteArray()) // 거래구분
        // bb.put("MS".toByteArray());                                         // 거래구분
        bb.put("01".toByteArray()) // 업무구분

        if (apprCode == "1") {
            bb.put("0200".toByteArray()) // 전문구분
        } else if (apprCode == "0") {
            bb.put("0420".toByteArray()) // 전문구분
        }

        bb.put("N".toByteArray()) // 거래형태
        bb.put(mDeviceNo.toByteArray()) // 단말기번호
        for (i in 0..3) bb.put(" ".toByteArray()) // 업체정보

        for (i in 0..11) bb.put(" ".toByteArray()) // 전문일련번호

        // bb.put("K".toByteArray());                                          // POS Entry Mode   // MS
        bb.put("S".toByteArray()) // POS Entry Mode   // IC
        for (i in 0..19) bb.put(" ".toByteArray()) // 거래 고유 번호

        for (i in 0..19) bb.put(" ".toByteArray()) // 암호화하지 않은 카드 번호

        bb.put("9".toByteArray()) // 암호화여부
        bb.put("################".toByteArray())
        bb.put("################".toByteArray())
        for (i in 0..39) bb.put(" ".toByteArray()) // 암호화 정보

        // bb.put("4330280486944821=17072011025834200000".toByteArray());      // Track II - MS
        // bb.put("123456789012345612345=8911           ".toByteArray());      // Track II - App카드
        for (i in 0..36) bb.put(" ".toByteArray()) // Track II - IC

        bb.put(KsnetUtil.FS) // FS

        bb.put(quota.toByteArray()) // 할부개월

        val tax: KsnetUtil.CalcTax = KsnetUtil.CalcTax()
        tax.setConfig(totAmt.toLong(), 10.0, 0.0)

        bb.put(KsnetUtil.stringToAmount(totAmt, 12).toByteArray()) // 총금액
//        mTotAmt = KsnetUtil.stringToAmount(totAmt, 12)

        bb.put(
            KsnetUtil
                .stringToAmount(java.lang.String.valueOf(tax.getServiceAmt()), 12)
                .toByteArray(),
        ) // 봉사료
        bb.put(
            KsnetUtil.stringToAmount(java.lang.String.valueOf(tax.getVAT()), 12).toByteArray(),
        ) // 세금
//        mVat = KsnetUtil.stringToAmount(java.lang.String.valueOf(tax.getVAT()), 12)
        bb.put(
            KsnetUtil.stringToAmount(java.lang.String.valueOf(tax.getSupplyAmt()), 12).toByteArray(),
        ) // 공급금액
//        mSupAmt = KsnetUtil.stringToAmount(java.lang.String.valueOf(tax.getSupplyAmt()), 12)
        bb.put("000000000000".toByteArray()) // 면세금액  //TODO taxFree 면세금액 설정 문의 필요
        bb.put("AA".toByteArray()) // Working Key Index
        for (i in 0..15) bb.put("0".toByteArray()) // 비밀번호

        if (apprCode == "1") {
            bb.put("            ".toByteArray()) // 원거래승인번호
            bb.put("      ".toByteArray()) // 원거래승인일자
        } else {
//            val tv1: TextView = findViewById<TextView>(R.id.spet5)
//            val orgDate = tv1.text.toString()
//            val tv2: TextView = findViewById<TextView>(R.id.spet6)
//            val orgApprNo = String.format("%-12s", tv2.text.toString())

            bb.put(orgApprNo.toByteArray()) // 원거래승인번호
            bb.put(orgDate.toByteArray()) // 원거래승인일자
        }
        for (i in 0..162) bb.put(" ".toByteArray()) // 사용자정보 ~ DCC 환율조회 Data

        // EMV Data Length(4 bytes)
        // EMV Data
        // bb.put(" ".toByteArray());                                             // 전자서명 유무
        bb.put("N".toByteArray())

        // bb.put("S".toByteArray());                                              // 전자서명 유무
        // bb.put("83".toByteArray());                                          // 전자서명 암호화 Key Index

        // for(int i=0; i<16; i++) bb.put("0".toByteArray());                   // 제품코드 및 버전        // KN1512021C000002
        // bb.put("KSPS2SP210600051".toByteArray());
        // bb.put("0108".toByteArray());
        // bb.put(String.format("%04d",  encBmpData.length()).toByteArray());   // 전자서명 길이          // 0248
        // bb.put(encBmpData.toByteArray());                                    // 전자서명              // 716634346E5567636D7737777643756E7666596D797934554647657A38764A784B2F7744545657554F72586341586A6954365441594E6E6F692B69412B572F49316B6D7072326744716C4B4B2F75624D6C6E684E6F346F4B7A54413757314578774F5975746E5A726759547166357244466238356B37516D50484D3057416B59547153755959432F71326D414E6B613042543841555A4B795556544179685341464C327442565857772F396A4C34554F306574594C696B54596535794C486858437A4568756B6A434448766D6F4B3449694D6D32753570507739654B442F564D387A312F594D6A3966787A4D396A6753435A6F6B76773D3D

        // bb.put(KsnetUtil.toByte("716634346E5567636D7737777643756E7666596D797934554647657A38764A784B2F7744545657554F72586341586A6954365441594E6E6F692B69412B572F49316B6D7072326744716C4B4B2F75624D6C6E684E6F346F4B7A54413757314578774F5975746E5A726759547166357244466238356B37516D50484D3057416B59547153755959432F71326D414E6B613042543841555A4B795556544179685341464C327442565857772F396A4C34554F306574594C696B54596535794C486858437A4568756B6A434448766D6F4B3449694D6D32753570507739654B442F564D387A312F594D6A3966787A4D396A6753435A6F6B76773D3D"));
        // bb.put(KsnetUtil.toByte("74497A564939432B776A634E727144784C48574D74682F43756442764F5139304F5243514A47546B594B546B4A6446756E42634E513764492F61704F32564D314F43794B305352494E5A4747757333576D523774324C413277784D7337314B7954676470744F392F576C593D"));
        bb.put(0x03.toByte()) // ETX
        bb.put(0x0D.toByte()) // CR

        val telegram = ByteArray(bb.position())
        bb.rewind()
        bb[telegram]

        val mRequestTelegram = ByteArray(telegram.size + 4)
        val telegramLength = String.format("%04d", telegram.size)
        System.arraycopy(telegramLength.toByteArray(), 0, mRequestTelegram, 0, 4)
        System.arraycopy(telegram, 0, mRequestTelegram, 4, telegram.size)
        return mRequestTelegram
    }
}
