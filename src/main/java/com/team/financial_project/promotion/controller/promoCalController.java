package com.team.financial_project.promotion.controller;

import com.team.financial_project.promotion.calculator.DepositCalculator;
import com.team.financial_project.promotion.calculator.LoanCalculator;
import com.team.financial_project.promotion.calculator.SavingsCalculator;
import com.team.financial_project.promotion.dto.*;
import com.team.financial_project.promotion.service.PromoCalService;
import com.team.financial_project.promotion.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/promotion")
public class promoCalController {
    @Autowired
    PromoCalService promoCalService;

    @Autowired
    PromotionService promotionService;

    // tab 화면전환 컨트롤러
//    @GetMapping("/CalDpst")
//    public String calculateFormDpst() {
//        return "/promotion/promotionCalDpst";
//    }
//
//    @GetMapping("/CalAcml")
//    public String calculateFormAcml() {
//        return "/promotion/promotionCalAcml";
//    }
//
//    @GetMapping("/CalLoan")
//    public String calculateFormLoan() {
//        return "/promotion/promotionCalLoan";
//    }

    // tab 화면전환 컨트롤러
    // 목돈 적금 페이지
    @GetMapping("/CalAcml")
    public String viewCalAcmlPage(
            @RequestParam(value = "codeCl", defaultValue = "430") String codeCl, // 필터 값 기본 430
            Model model) {
        // 페이지에 출력하기 위한 진행상태 조회
        List<CodeDto> progressStatusList = promotionService.getCodeListByCl(codeCl);
        model.addAttribute("progressStatusList", progressStatusList);
        return "promotion/promotionCalAcml";
    }

    // 예금 페이지
    @GetMapping("/CalDpst")
    public String viewCalDpstPage(
            @RequestParam(value = "codeCl", defaultValue = "430") String codeCl, // 필터 값 기본 430
            Model model) {
        // 페이지에 출력하기 위한 진행상태 조회
        List<CodeDto> progressStatusList = promotionService.getCodeListByCl(codeCl);
        model.addAttribute("progressStatusList", progressStatusList);
        return "promotion/promotionCalDpst";
    }

    // 대출 페이지
    @GetMapping("/CalLoan")
    public String viewCalLoanPage(
            @RequestParam(value = "codeCl", defaultValue = "430") String codeCl, // 필터 값 기본 430
            Model model) {
        // 페이지에 출력하기 위한 진행상태 조회
        List<CodeDto> progressStatusList = promotionService.getCodeListByCl(codeCl);
        model.addAttribute("progressStatusList", progressStatusList);
        return "promotion/promotionCalLoan";
    }

    // 설계 디테일 페이지 이동
    @GetMapping("/cal/detail")
    public String moveDetailPage(@RequestParam("dsgnSn") Long dsgnSn,
                                 @RequestParam(value = "codeCl", defaultValue = "430") String codeCl,
                                 Model model) {
        try {
            String dsTyCd = promoCalService.findDsTyCd(dsgnSn);
            System.out.println("===========" + dsTyCd);


            switch (dsTyCd) {
                case "1":
                    DsgnDetailDto dsgnDetailDto = promoCalService.findSavgDetails(dsgnSn);
                    System.out.println("=============================================================" + dsgnDetailDto);
                    List<CodeDto> progressStatusList1 = promotionService.getCodeListByCl(codeCl);
                    model.addAttribute("progressStatusList", progressStatusList1);
                    System.out.println("Result from findSavgDetails: " + dsgnDetailDto);
                    model.addAttribute("savgInfo", dsgnDetailDto);
                    model.addAttribute("dsgnSn", dsgnSn);
                    return "promotion/promotionDetailSavg";
                case "2":
                    DsgnDetailDto detailDto = promoCalService.findAcmlDetails(dsgnSn);
                    List<CodeDto> progressStatusList2 = promotionService.getCodeListByCl(codeCl);
                    model.addAttribute("progressStatusList", progressStatusList2);
                    System.out.println("Result from findSavgDetails: " + detailDto);
                    model.addAttribute("savgInfo",detailDto);
                    model.addAttribute("dsgnSn", dsgnSn);
                    return "promotion/promotionDetailAcml";
                case "3":
                    DsgnDetailDto detailDtos = promoCalService.findDpstDetails(dsgnSn);
                    List<CodeDto> progressStatusList3= promotionService.getCodeListByCl(codeCl);
                    model.addAttribute("progressStatusList", progressStatusList3);
                    System.out.println("Result from findSavgDetails: " + detailDtos);
                    model.addAttribute("savgInfo",detailDtos);
                    model.addAttribute("dsgnSn", dsgnSn);
                    return "promotion/promotionDetailDpst";
                case "4":
                    DsgnDetailDto dsgnDetailDtos = promoCalService.findLoanDetails(dsgnSn);
//                    System.out.println("=============================================================" + dsgnDetailDtos);
                    List<CodeDto> progressStatusList4 = promotionService.getCodeListByCl(codeCl);
                    model.addAttribute("progressStatusList", progressStatusList4);
//                    System.out.println("Result from findSavgDetails: " + dsgnDetailDtos);
                    model.addAttribute("savgInfo",dsgnDetailDtos);
                    model.addAttribute("dsgnSn", dsgnSn);
                    return "promotion/promotionDetailLoan";
                default:
                    model.addAttribute("errorMessage", "잘못된 설계유형코드입니다.");
                    return "promotion/list";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "처리 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/promotion/list";
        }
    }

    // 목돈적금 상품리스트 조회
    @GetMapping("/cal/AcmlProductList")
    @ResponseBody
    public List<ProductInfoDto> getACmlProductList(
            @RequestParam(value = "prodCd", required = false) String prodCd,
            @RequestParam(value = "prodNm", required = false) String prodNm) {
        System.out.println("==============="+ prodCd);
        System.out.println("==============="+ prodNm);
        return promoCalService.getAcmlProductList(prodCd, prodNm);
    }

    // 예금 상품리스트 조회
    @GetMapping("/cal/dpstProductList")
    @ResponseBody
    public List<ProductInfoDto> getDpstProductList(
            @RequestParam(value = "prodCd", required = false) String prodCd,
            @RequestParam(value = "prodNm", required = false) String prodNm) {
        return promoCalService.getDpstProductList(prodCd, prodNm);
    }

    // 대출 상품리스트 조회
    @GetMapping("/cal/loanProductList")
    @ResponseBody
    public List<ProductInfoDto> getLoanProductList(
            @RequestParam(value = "prodCd", required = false) String prodCd,
            @RequestParam(value = "prodNm", required = false) String prodNm) {
        return promoCalService.getLoanProductList(prodCd, prodNm);
    }

    // 대출 계산 컨트롤러
    // 원리금균등상환 계산
    @PostMapping("/cal/equalPrincipalAndInterest")
    public ResponseEntity<LoanCalDto> loanEqualPrincipal(@RequestBody LoanCalDto loanCalDto) {

        // 계산 서비스 호출
        LoanCalDto responseDto = LoanCalculator.calculateEqualPrincipalAndInterest(loanCalDto);
        return ResponseEntity.ok(responseDto);
    }
    // 원금균등상환 계산
    @PostMapping("/cal/equalPrincipal")
    public ResponseEntity<LoanCalDto> calculateEqualPrincipal(@RequestBody LoanCalDto loanCalDto) {
        LoanCalDto responseDto = LoanCalculator.calculateEqualPrincipal(loanCalDto);
        return ResponseEntity.ok(responseDto);
    }
    // 원금만기일시상환
    @PostMapping("/cal/lumpSum")
    public ResponseEntity<LoanCalDto> calculateBalloonPayment(@RequestBody LoanCalDto requestDto) {
        // 계산 서비스 호출
        LoanCalDto responseDto = LoanCalculator.calculateBalloonPayment(requestDto);
        return ResponseEntity.ok(responseDto);
    }



    // 예금 계산 컨트롤러
    @PostMapping("/cal/dpstCalculate")
    @ResponseBody
    public ResponseEntity<DepositCalDto> calculateSavings(@RequestBody DepositCalDto requestDto) {

        // 계산 서비스 호출
        DepositCalDto responseDto = DepositCalculator.calculateDeposit(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 목돈적금 저장 컨트롤러
    @PostMapping("/cal/acml/insert")
    @ResponseBody
    public ResponseEntity<String> saveSavings(@RequestBody SavingsSaveDto savingsSaveDto) {
        System.out.println("Received DTO: " + savingsSaveDto);

        // 서비스 호출
        promoCalService.save(savingsSaveDto);

        return ResponseEntity.ok("저장이 완료되었습니다.");
    }

    // 예금 저장 컨트롤러
    @PostMapping("/cal/dpst/insert")
    @ResponseBody
    public ResponseEntity<String> saveDpst(@RequestBody SavingsSaveDto savingsSaveDto) {
        System.out.println("Received DTO: " + savingsSaveDto);

        // 서비스 호출
        promoCalService.saveDpst(savingsSaveDto);

        return ResponseEntity.ok("저장이 완료되었습니다.");
    }

    // 대출 저장 컨트롤러
    @PostMapping("/cal/loan/insert")
    @ResponseBody
    public ResponseEntity<String> saveLoan(@RequestBody SavingsSaveDto savingsSaveDto) {
        System.out.println("Received DTO: " + savingsSaveDto);

        // 서비스 호출
        promoCalService.saveLoan(savingsSaveDto);

        return ResponseEntity.ok("저장이 완료되었습니다.");
    }

    // 목돈저축 설계수정
    @PostMapping("/cal/acml/update")
    @ResponseBody
    public ResponseEntity<String> updateAcml(@RequestBody SavingsSaveDto savingsSaveDto) {
        System.out.println("Received DTO: " + savingsSaveDto);

        // 서비스 호출
        promoCalService.updateAcml(savingsSaveDto);

        return ResponseEntity.ok("저장이 완료되었습니다.");
    }

    // 예금저축 설계수정
    @PostMapping("/cal/dpst/update")
    @ResponseBody
    public ResponseEntity<String> updateDpst(@RequestBody SavingsSaveDto savingsSaveDto) {
        System.out.println("Received DTO: " + savingsSaveDto);

        // 서비스 호출
        promoCalService.updateDpst(savingsSaveDto);

        return ResponseEntity.ok("저장이 완료되었습니다.");
    }

    // 예금저축 설계수정
    @PostMapping("/cal/savg/update")
    @ResponseBody
    public ResponseEntity<String> updateSavg(@RequestBody SavingsSaveDto savingsSaveDto) {
        System.out.println("Received DTO: " + savingsSaveDto);

        // 서비스 호출
        promoCalService.updateSavg(savingsSaveDto);

        return ResponseEntity.ok("저장이 완료되었습니다.");
    }

    // 대출 설계수정
    @PostMapping("/cal/loan/update")
    @ResponseBody
    public ResponseEntity<String> updateLoan(@RequestBody SavingsSaveDto savingsSaveDto) {
        System.out.println("Received DTO: " + savingsSaveDto);

        // 서비스 호출
        promoCalService.updateLoan(savingsSaveDto);

        return ResponseEntity.ok("저장이 완료되었습니다.");
    }

}

