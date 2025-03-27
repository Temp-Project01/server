package com.app.temp.controller.enterprise;


import com.app.temp.controller.exception.MypageSelectExcpetion;
import com.app.temp.domain.dto.*;
import com.app.temp.domain.vo.CompanyInquiryVO;
import com.app.temp.domain.vo.MemberVO;
import com.app.temp.service.CompanyMemberService;
import com.app.temp.service.CompanyService;
import com.app.temp.service.InquiryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Member;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class EnterpriseController {

    private final CompanyService companyService;
    private final CompanyMemberService companyMemberService;
    private final HttpSession session;
    private final CompanyMemberInfoAdminDTO companyMemberInfoAdminDTO;
    private final InquiryService inquiryService;

    @GetMapping("enterprise/header-footer")
    public String headerfooter(){
        return "enterprise/headerfooteredited";
    }

//
    @GetMapping("enterprise/account-info")
    public String accountInfo(Model model) {
        MemberDTO member = (MemberDTO) session.getAttribute("member");

        Optional<CompanyMemberInfoAdminDTO> companyMember = companyMemberService.selectCompanyMemberInfoById(member.getId());

        log.info("이건 출력: {}", companyMember);

        if(companyMember.isPresent()){
        model.addAttribute("companyMember", companyMember.orElseThrow(() -> new MypageSelectExcpetion("못찾음")));
        return "enterprise/account-info";

        }else{
            return "enterprise/account-info";
        }
    }

    // 기업 마이페이지 공고목록 조회
    @GetMapping("enterprise/program-list")
    public void goprogramlist() {;}

    @ResponseBody
    @PostMapping("enterprises/programs-list")
    public CompanyProgramListDTO programList(){
       CompanyMemberDTO companyMember = (CompanyMemberDTO) session.getAttribute("companyMember");

        return companyService.selectProgramsByCompanyId(21L);
    }

//    게시물 등록 페이지 이동
    @GetMapping("enterprise/program-insert-page")
    public String programEdit() {
        return "enterprise/program-insert-page";
    }

//    기업 공고 등록 임시저장
    @PostMapping("enterprise/program-pending-insert")
    public String programPendingInsert(ProgramInfoDTO programInfoDTO) {
        CompanyMemberDTO companyMember = (CompanyMemberDTO) session.getAttribute("companyMember");
        programInfoDTO.setCompanyId(companyMember.getCompanyId());
        companyService.pendingCompanyProgram(programInfoDTO);
        return "redirect:/enterprise/program-list";
    }

//    기업 공고 등록 승인요청
    @PostMapping("enterprise/program-insert")
    public String createPost(ProgramInfoDTO programInfoDTO) {
        CompanyMemberDTO companyMember = (CompanyMemberDTO) session.getAttribute("companyMember");
        programInfoDTO.setCompanyId(companyMember.getCompanyId());
        companyService.insertCompanyProgram(programInfoDTO);

        return "redirect:/enterprise/program-list";  // 등록 후 게시물 목록으로 리디렉션
    }


//    기업 공고 수정페이지로 이동
    @GetMapping("enterprise/program-edit")
    public String goProgramEdit(@RequestParam("id") Long id, Model model) {
        model.addAttribute("program", companyService.selectCompanyProgramById(id));
        return "enterprise/program-edit";
    }


//    기업 공고 수정 승인요청
    @PostMapping("enterprise/program-edit")
    public String updateCompanyProgram(@ModelAttribute ProgramInfoDTO programInfoDTO) {
        CompanyMemberDTO companyMember = (CompanyMemberDTO) session.getAttribute("companyMember");
        log.info(programInfoDTO.getId().toString());

        programInfoDTO.setCompanyId(companyMember.getCompanyId());

        companyService.updateCompanyProgram(programInfoDTO);
        return "redirect:/enterprise/program-list";
    }


//    기업 공고 수정 임시저장
    @PostMapping("enterprise/program-pending-update")
    public String programPendingUpdate(@ModelAttribute ProgramInfoDTO programInfoDTO) {
        CompanyMemberDTO companyMember = (CompanyMemberDTO) session.getAttribute("companyMember");
        programInfoDTO.setCompanyId(companyMember.getCompanyId());
        companyService.updatePendingProgramupdatePendingProgram(programInfoDTO);
        return "redirect:/enterprise/program-list";
    };

    
//    기업 공고 삭제
    @PostMapping("enterpirse/program-delete")
    public String programDelete(Long id) {
        companyService.deleteCompanyProgram(id);
        return "redirect:/enterprise/program-list";
    }

    @GetMapping("enterprise/applicant-info")
    public void applicantInfo(){

    }

    @GetMapping("enterprise/applicant-manage")
    public void applicantManage(){

    }

    @GetMapping("enterprise/company-image")
    public void companyImage(){
    }


//    기업 이미지 업로드
    @PostMapping("enterprise/company-post-images")
    public String getImages(@RequestParam("logo") MultipartFile logo, @RequestParam("main-image") List<MultipartFile> files){
        CompanyDTO company = (CompanyDTO) session.getAttribute("company");
        companyService.uploadCompanyImages(company.getId(), files);
        companyService.insertCompanyLogo(company.getId(), logo);


        return "redirect:/enterprise/company-image";
    }
    
    // 기업 이미지들 삭제 (그냥 전체삭제됨)
    @PostMapping("enterprise/company-delete-images")
    public String deleteImages() {
        CompanyDTO company = (CompanyDTO) session.getAttribute("company");
          companyService.deleteCompanyImages(company.getId());
          log.info("컨트롤러 왔음");
          return "redirect:/enterprise/company-image";
    }
    
    // 기업 로고 삭제
    @PostMapping("enterprise/company-delete-logo")
    public String deleteLogo() {
         CompanyDTO company = (CompanyDTO) session.getAttribute("company");
        companyService.deleteCompanyLogo(company.getId());
        return "redirect:/enterprise/company-image";
    }
    
    // 기업 로고 조회
//    @GetMapping("/")
//    public void mainPage(Model model) {
//        CompanyDTO company = (CompanyDTO) session.getAttribute("company");
//        String thumbnail = companyService.selectCompanyThumnail(company.getId());
//        String logo = companyService.selectCompanyThumnail(company.getId());
//
//        model.addAttribute("thumnail", thumbnail);
//        model.addAttribute("thumnail", companyService.selectCompanyThumnail(company.getId()));
//
//    }

    
    @GetMapping("enterprise/master-invite")
    public String masterInvite(Model model, HttpSession session, @RequestParam(name = "code") String code){
//        log.info("Received code from URL: {}", code);  // code 값 로그 출력
        model.addAttribute("code", code);


//        세션에서 member 정보 가져오기
        MemberVO member = (MemberVO) session.getAttribute("member");

//        로그인 정보가 없으면 로그인 페이지로
        if (member == null) {
            return "redirect:/member/login";
        }

//        세션에서 초대자 정보 가져오기
        String inviterName = (String) session.getAttribute("inviterName");
        String companyName = (String) session.getAttribute("companyName");

        String memberName = member.getMemberName();

        String role = (String) session.getAttribute("role");
        String token = (String) session.getAttribute("token");

        model.addAttribute("inviterName", inviterName);
        model.addAttribute("companyName", companyName);
        model.addAttribute("role", role);
        model.addAttribute("token", token);
        model.addAttribute("memberName", memberName);

        return "/enterprise/master-invite";
    }


    @ResponseBody
    @GetMapping("enterprise/display")
    public byte[] display(@RequestParam String path) throws IOException {
//        CompanyDTO company = (CompanyDTO) session.getAttribute("company");
//        log.info("들어옴");
//        log.info("들어옴");
//        log.info("들어옴");
//        log.info("들어옴");
//        log.info("들어옴");
//        log.info("path:{}", path);
        byte[] file = null;

        try {
            // 파일을 바이트 배열로 읽어옴
            file = FileCopyUtils.copyToByteArray(new File("/upload/" + path));
        }catch (NoSuchFileException e){
            throw new RuntimeException();
        }
        // 파일을 바이트 배열로 변환함

//        log.info("file {}", file);
        return file;
    }


    @GetMapping("enterprise/viewer-invite")
    public void viewerInvite(){

    }

    @GetMapping("enterprise/member-manage")
    public void memberManage(){

    }




    @GetMapping("enterprise/insert-inquiry")
    public String insert(HttpSession httpsession, @RequestParam("company-inquiry-type") String companyInquiryType, @RequestParam("company-inquiry-content") String companyInquiryContent){
        CompanyInquiryVO companyInquiryVO = new CompanyInquiryVO();
        CompanyMemberDTO companyMember = (CompanyMemberDTO) httpsession.getAttribute("companyMember");
        Long memberId = companyMember.getId();
//        Long companyId = companyMember.getCompanyId();
//        Long memberId = 3L;
        log.info("memberInquiryType:{}", companyInquiryType);
        log.info("memberInquiryContent:{}", companyInquiryContent);
        companyInquiryVO.setMemberId(memberId);
        companyInquiryVO.setCompanyInquiryType(companyInquiryType);
        companyInquiryVO.setCompanyInquiryDetail(companyInquiryContent);
        companyInquiryVO.setCompanyInquiryStatus("처리대기");
        inquiryService.setCompanyInquiry(companyInquiryVO);
        return "redirect:/";
    }


}
